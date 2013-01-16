package com.bronzesoft.demo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bronzesoft.rdm.platform.PowerAPI;
import com.bronzesoft.rdm.platform.dao.IExtendDao;
import com.bronzesoft.rdm.platform.dao.impl.ExtendDao;
import com.bronzesoft.rdm.platform.service.aop.impl.TimerAop;

/**
 * 当需要刷新或统计某段时间内的数据时，需要使用定时器去刷新。
 * 
 * 第一次由系统优化时间调用，以后都是根据rdm_platform_aop.xml配置文件中对timer的配置时间进行调用。
 * 
 * 例：将RDM系统中的任务保存到其他系统中。
 * 
 * @author Administrator
 *
 */
public class TimerAopDemo extends TimerAop{

	public void run() {
		execute();
	}

	/**
	 * 
	 * 将数据库db2中的数据插入到db1中的数据表
	 * 
	 * 
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
	@SuppressWarnings("unchecked")
	private void execute() {
		
		IExtendDao dao1 = (ExtendDao)PowerAPI.extendDao("db1");
		IExtendDao dao2 = (ExtendDao)PowerAPI.extendDao("db2");
		
		try {
			List<Object[]> list = dao2.search("select ID, Name from t_Tsk_Tsk");
			
			if(list != null && !list.isEmpty()) {
			
				String sql = "insert into RDM_AOP(ID, NAME) values (?, ?)";
				List list2 = new ArrayList();
				
				for(Object[] obj : list) {
					list2.clear();
					
					list2.add(obj[0]);
					list2.add(obj[1]);
					
					dao1.execute(sql, list2);
					
					System.out.println("--------------success------------");
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
