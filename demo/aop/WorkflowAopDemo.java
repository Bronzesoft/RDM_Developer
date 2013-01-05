package com.bronzesoft.demo;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.bronzesoft.power.platform.workflow.model.BaseData;
import com.bronzesoft.rdm.platform.PowerAPI;
import com.bronzesoft.rdm.platform.dao.IExtendDao;
import com.bronzesoft.rdm.platform.dao.impl.ExtendDao;
import com.bronzesoft.rdm.platform.service.aop.impl.Aop;
import com.bronzesoft.rdm.platform.service.aop.vo.AopData;
import com.bronzesoft.rdm.platform.service.aop.vo.AopReturn;


/**
 * 
 * 该demo以【缺陷流程】为例,所有操作和配置都基于缺陷流程，其他流程同样操作。
 * 
 * @author Administrator
 *
 */
@SuppressWarnings("unchecked")
public class WorkflowAopDemo extends Aop{

	/**
	 *在流程进行操作时，初始化操作信息。
	 *例如：在【指定修改】操作中配置外部处理类，点击该操作时为【描述和建议】字段设置“二次开发测试”值。
	 *在进入页面时，就可以看到该字段的信息。
	 */ 
	public void prepare(Map<String, Object[]> datamap) {
		
		datamap.put("REMARK", new Object[] {"remark", "二次开发测试"});
	}
	
	/**
	 * 流程操作后的实体信息，在插入数据库之前进行校验。
	 * 若需要与其他系统或数据库中数据进行比较，则可以配置连接其他数据库查询数据。
	 */
	public AopReturn before(AopData data) {
		
		AopReturn r = new AopReturn();
		boolean flag = false;
		
		// 校验Remark字段的值。
		String value = data.getProperty("Remark");
		
		if("二次开发测试".equals(value)) {
			flag = true;
		}
		//得到流程实体
		BaseData base = (BaseData)data.getEntityData();
		
		if("二次开发测试".equals(base.getName())) {
			flag = true;
		}
		
		// 与其他数据库中信息比较。
		/**
		 * 在jdbc.xml文件中配置所要执行数据库信息，例如：
		 * 
		 * <connection id="db1">
		 *		<driverclass>net.sourceforge.jtds.jdbc.Driver</driverclass>
		 *		<url>jdbc:jtds:sqlserver://192.168.5.5:1433;databaseName=ralap_test_2;SelectMethod=cursor</url>
		 *		<username>**</username>
		 *		<password>****</password>
		 *	</connection>
		 * 
		 * 以上配置为本例中的测试实例。
		 */
		
		// 得到执行Dao.
		IExtendDao dao = (ExtendDao)PowerAPI.extendDao("db1");
		
		//查询数据表中数据。在ralap_test_2数据库中有RDM_AOP表。
		try {
			List<Object[]> list = dao.search("select * from RDM_AOP");
			
			if(list != null) {
				
				r.setMessage(String.valueOf(list.size()));
				if(list.size() == 4) {
					flag = true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// if flag, check success and save information to database.
		r.setSuccess(flag);
		
		return r;
	}
	
	/**
	 * 实体操作，在提交数据之后，用来实现个性化动作，比如写入信息至其他系统或数据库。
	 */
	public AopReturn after(AopData data) {
		
		AopReturn r = new AopReturn();
		
		// 得到【描述和建议】字段信息。
		String value = data.getProperty("Remark");
		System.out.println(value);
		
		BaseData base = (BaseData)data.getEntityData();
		
		// 得到流程实体类名全称。(如得到：com.bronzesoft.power.rdbug.model.Bug)
		System.out.println(base.getClass().getName());
		
		// 得到流程实体类名简称。（如：Bug）
		System.out.println(base.getClass().getSimpleName());
		
		/**  向其他系统写入数据.(数据库配置与before方法的配置一致为例。) **/
		
		IExtendDao dao = (ExtendDao)PowerAPI.extendDao("db1");
		
		try {
			List list = new ArrayList();
			// set id's value.
			list.add(base.getId());
			
			// set name's value.
			list.add(base.getName());
			
			// insert into other DB.
			dao.execute("insert into RDM_AOP(ID, NAME) values (?, ?)", list);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			List<String> list = dao.search("select NAME from RDM_AOP where ID = ?", "test");
			
			if(list != null) {
				System.out.println(list.get(0));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return r;
	}
	
}
