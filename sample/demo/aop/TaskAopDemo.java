package com.bronzesoft.demo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bronzesoft.power.task.model.Task;
import com.bronzesoft.rdm.platform.PowerAPI;
import com.bronzesoft.rdm.platform.dao.IExtendDao;
import com.bronzesoft.rdm.platform.dao.impl.ExtendDao;
import com.bronzesoft.rdm.platform.service.aop.impl.TaskAop;
import com.bronzesoft.rdm.platform.service.aop.vo.AopReturn;

/**
 * 
 * 配置任务操作拦截设置，具体配置参考rdm_platform_aop.xml配置信息。
 * 本demo以【发布】操作为例。
 * 
 * @author Administrator
 */
@SuppressWarnings("unchecked")
public class TaskAopDemo extends TaskAop {

	/**
	 * 任务发布前，可以用来校验页面表单信息，若校验不通过，则不能成功发布。
	 */
	public AopReturn before(Task task) {
		
		AopReturn r = new AopReturn();
		boolean flag = true;
		
		// 校验Name字段的值是否为空。
		if(task.getName() == null || "".equals(task.getName())) {
			System.out.println("task's name can't be null!!!");
			
			flag = false;
		}
		
		/** 通过其他系统校验。 **/
		/**
		 * 在jdbc.xml文件中配置所要执行数据库信息，例如：
		 * 
		 * <connection id="db2">
		 *		<driverclass>net.sourceforge.jtds.jdbc.Driver</driverclass>
		 *		<url>jdbc:jtds:sqlserver://192.168.5.5:1433;databaseName=ralap_test;SelectMethod=cursor</url>
		 *		<username>**</username>
		 *		<password>****</password>
		 *	</connection>
		 * 
		 * 以上配置为本例中的测试实例。
		 */
		
		IExtendDao dao = (ExtendDao)PowerAPI.extendDao("db2");
		
		/**
		 * 以判断系统中是否已存在名称为该任务名称的任务为例。
		 */
		try {
			List list = dao.search("select * from t_Tsk_Tsk where Name = ?", task.getName());
			
			if(list.size() > 0) {
				flag = false;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		r.setSuccess(flag);
		
		return r;
	}
	
	/**
	 * 任务保存到数据库之后，可以将信息保存到其他系统中。
	 */
	public AopReturn after(Task task) {
		
		AopReturn r = new AopReturn();
		
		/**
		 * 将任务ID和名称保存到其他系统。
		 * 
		 * 在jdbc.xml文件中对应的ID为db1数据库配置。
		 */
		IExtendDao dao = (ExtendDao)PowerAPI.extendDao("db1");
		
		List list = new ArrayList();
		
		// set id's value.
		list.add(task.getId());
		
		// set name's value.
		list.add(task.getName());
		
		// 将信息插入到其他系统中。
		try {
			dao.execute("insert into RDM_AOP(ID, NAME) values (?, ?)", list);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// 查询其他系统中信息.
		try {
			List<Object[]> result = dao.search("select * from RDM_AOP");
			
			if(result != null) {
				for(Object[] obj : result) {
					System.out.println(obj[0] + "---------" + obj[1]);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return r;
	}
}
