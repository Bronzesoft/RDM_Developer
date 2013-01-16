package com.bronzesoft.demo.aop;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bronzesoft.power.platform.workflow.model.BaseData;
import com.bronzesoft.rdm.platform.PowerAPI;
import com.bronzesoft.rdm.platform.dao.IExtendDao;
import com.bronzesoft.rdm.platform.dao.impl.ExtendDao;
import com.bronzesoft.rdm.platform.exception.DBException;
import com.bronzesoft.rdm.platform.service.aop.impl.Aop;
import com.bronzesoft.rdm.platform.service.aop.vo.AopData;
import com.bronzesoft.rdm.platform.service.aop.vo.AopReturn;
import com.bronzesoft.rdm.platform.util.LogUtil;

@SuppressWarnings("unchecked")
public class AopDemo extends Aop {

	/**
	 *在流程进行操作时，初始化字段信息，
	 *在进入页面时，就可以看到该字段的信息。
	 */ 
	public void prepare(Map<String, Object[]> datamap) {
		
		String projectId = (String)datamap.get("ProjectID")[0];
		String memberIds = "";
		
		// 调用RDM接口，获取项目组成员
		try {
			memberIds = PowerAPI.project().getProjectMemberIds(projectId);
		} catch (DBException e) {
			LogUtil.error("获取项目组成员失败");
		}
		
		// 根据用户的ID获取名称
		String memberName = PowerAPI.user().getUserName(memberIds);
		
		// key值是字段名称，可以参考【系统管理】-【业务里程】-【具体流程】-【字段】
		// value值是Object[]，第0为ID值，第1位是页面显示值
		datamap.put("SolverID", new Object[] { memberIds, memberName });
	}
	
	/**
	 * 流程操作后的实体信息，在插入数据库之前进行校验。
	 * 若需要与其他系统或数据库中数据进行比较，则可以配置连接其他数据库查询数据。
	 */
	public AopReturn before(AopData data) {
		AopReturn r = new AopReturn();
		boolean flag = false;
		
		/*
		 *  获取系统字段的值
		 */
		
		// 情况1：该字段在页面已经配置
		String solverId = data.getProperty("SolverID");
		
		// 情况2：该字段未在页面配置
		BaseData base = (BaseData) data.getEntityData(); // 流程对象
		// LcBaseData base1 = (LcBaseData) data.getEntityData(); // 实体对象
		
		// BaseData 对象可能为具体流程对象，比如Bug,Risk,...
		// 如果需要获取指定某个属性(如严重性)，可以通过强转base对象 ((Bug)base).getLevelId() 获取.
		String projectId = base.getProjectId();
		
		/*
		 * 获取自定义字段
		 */
		String customTxt = data.getProperty("Fld_T_00001");
		
		if ("userid".equals(solverId) || "projectid".equals(projectId)
				|| "text".equals(customTxt)) {
			flag = true;
		} else {
			r.setMessage("校验失败，不满足业务规则");
		}
		
		r.setSuccess(flag);
		
		return r;
	}
	
	/**
	 * 实体操作，在提交数据之后，用来实现个性化动作，比如写入信息至其他系统或数据库。
	 */
	public AopReturn after(AopData data) {
		AopReturn r = new AopReturn();
		
		// 得到指定字段信息。
		String levelId = data.getProperty("LevelID");
		
		if ("id".equals(levelId)) { // 满足指定规则
			
			// 更新对象其他属性信息
			Map map = new HashMap();
			map.put("ProbabilityID", "pid");
			
			 // 流程对象
			BaseData base = (BaseData) data.getEntityData();
			try {
				PowerAPI.workflow().updateWorkflowData(base.getId(), "BUG", map);
			} catch (Exception e) {
				LogUtil.error("更新对象信息失败");
			}
			
			// 实体对象
			// LcBaseData base1 = (LcBaseData) data.getEntityData();
			// PowerAPI.entity().updateEntityData(base.getId(), "PJT", map);
			
			/**
			 * 如果需要握手其他系统，调用并更新信息。
			 * 
			 * 在jdbc.xml文件中配置所要执行数据库信息，例如：
			 * <connection id="crmdb">
			 *		<driverclass>net.sourceforge.jtds.jdbc.Driver</driverclass>
			 *		<url>jdbc:jtds:sqlserver://192.168.5.5:1433;databaseName=ralap_test_2;SelectMethod=cursor</url>
			 *		<username>**</username>
			 *		<password>****</password>
			 *	</connection>
			 * 
			 */
			IExtendDao dao = (ExtendDao)PowerAPI.extendDao("crmdb");
			if (dao == null) return null;
			
			try {
				List list = new ArrayList();
				list.add(base.getId());
				list.add(base.getName());
				
				// insert into other DB.
				dao.execute("insert into RDM_AOP(ID, NAME) values (?, ?)", list);
			} catch (SQLException e) {
				LogUtil.error("连接并更新其他数据库信息失败");
			}
		}
		
		return r;
	}
	
}
