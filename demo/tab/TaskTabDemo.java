package com.bronzesoft.demo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bronzesoft.power.rdstatus.model.Project;
import com.bronzesoft.power.rdsys.model.Department;
import com.bronzesoft.rdm.platform.PowerAPI;
import com.bronzesoft.rdm.platform.api.IDeptCoreService;
import com.bronzesoft.rdm.platform.api.IProjectCoreService;
import com.bronzesoft.rdm.platform.api.IWorkflowCoreService;
import com.bronzesoft.rdm.platform.dao.IExtendDao;
import com.bronzesoft.rdm.platform.dao.impl.ExtendDao;
import com.bronzesoft.rdm.platform.service.tab.impl.Tab;


/**
 * 通过objectId,得到其他系统数据库中信息。
 * 
 * 一般情况下，objectId由其他系统保存，并关联一个其他系统的ID，通过其他系统的ID查询与之相关联的数据信息。
 * 并将最后的数据信息保存到map中，使得在自定义页面中可以调用。
 * 
 * loginUserId 为判断登录用户权限时使用。
 * 
 * @author Administrator
 *
 */
public class TaskTabDemo extends Tab {

	@SuppressWarnings("unchecked")
	public Map buildData(String objectId, String loginUserId) {

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
		 */
		
		Map data = new HashMap();
		
		/**
		 * 模板页面中直接使用${userId}取得值；
		 */
		data.put("user", loginUserId);
		IExtendDao dao = (ExtendDao)PowerAPI.extendDao("db2");
		
		/**
		 * 查询ID为objectId的数据信息。
		 */
		try {
			List<Object[]> list = dao.search("select ObjectID, ObjectType from t_Tsk_Tsk where ID = ?",objectId);
			
			if(list != null && !list.isEmpty()) {
				
				/**
				 * 将结果信息保存到list中，在模板页面中直接使用<#list entity as item>${item}</#list>可以取得相应的值，具体开发方式请参考FreeMarker开发文档。
				 * 
				 * 注意：list中不能有null值。
				 */
				List listData = new ArrayList();
				
				if("DT".equals(list.get(0)[1])) {
					
					IDeptCoreService service = PowerAPI.department();
					Department d = service.getMyDepartment(loginUserId);
					
					listData.add(d.getName());
					
				} else if("PT".equals(list.get(0)[1])) {
					
					IProjectCoreService service = PowerAPI.project();
					
					List numberList = service.getAllProjects(loginUserId, true);
					
					for(Object obj : numberList) {
						
						Project p = (Project)obj;
						
						listData.add(p.getName());
					}
				}
				data.put("task", listData);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		IWorkflowCoreService service = PowerAPI.workflow();
		
		data.put("meetingPresence", service.getMeetingPresence());
		
		data.put("meetingRecord", service.getMeetingRecord("4f2ec7d7-04cd-47df-b14b-6764f5b39d40"));
		
		data.put("reviewExpertAction", service.getReivewExpert("a260e6eb-0002-4cc2-a844-96550f488759"));
		
		data.put("refrence", service.getReferences("MET", "288f36ae-1aa3-4c9d-aaf1-1952fce3a052"));
		
		data.put("array", new Object[] {"a", "b"});
		
		List arrayList = new ArrayList();
		
		arrayList.add(new Object[] {"c", "d"});
		
		data.put("arrayList", arrayList);
		
		Map map1 = new HashMap();

		map1.put("key", "value");
		map1.put("key2", "value2");
		
		data.put("map", map1);
		
		Map map2 = new HashMap();
		
		map2.put("key", new Object[] {"value3", "value4"});
		map2.put("key2", new Object[] {"value5", "value6"});
		
		data.put("map2", map2);
		
		data.put("date", new Date());
		
		data.put("method", new TemplateMethodDemo());
		
		return data;
	}
}
