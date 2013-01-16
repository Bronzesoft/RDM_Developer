package com.bronzesoft.demo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bronzesoft.rdm.platform.PowerAPI;
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
public class EntityTabDemo extends Tab{

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
		 * 模板页面中直接使用${userId}取得值。
		 */
		data.put("userId", loginUserId);
		IExtendDao dao = (ExtendDao)PowerAPI.extendDao("db2");
		
		/**
		 * 查询ID为objectId的数据信息。
		 */
		try {
			List<Object[]> list = dao.search("select ID, Name from t_Doc_Doc where ID = ?",objectId);
			
			if(list != null && !list.isEmpty()) {
				
				/**
				 * 将结果信息保存到list中，在模板页面中直接使用<#list entity as item>${item}</#list>可以取得相应的值，具体开发方式请参考FreeMarker开发文档。
				 * 
				 * 注意：list中不能有null值。
				 */
				List listData = new ArrayList();
				
				listData.add(list.get(0)[0]);
				listData.add(list.get(0)[1]);
				
				listData.add("");
				
				listData.add("aaa");
				listData.add("bbb");
				
				data.put("entity", listData);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return data;
	}
	
	
	
}
