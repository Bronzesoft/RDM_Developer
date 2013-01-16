package com.bronzesoft.eai.client;

import java.util.List;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.bronzesoft.power.eai.ws.PowerWsService;
import com.bronzesoft.power.eai.ws.exception.WsRemoteException;

public class ProjectEffortClient {

	private static PowerWsService wsService = null;
	private static String server = "http://127.0.0.1/pdm//webservice/RDMWsService";
	
	public ProjectEffortClient() {
    }

	public List<Object[]> getEffort(String token) {

        try {
            return wsService.getEffortByProject(token, "2011-07-07",
                "2011-12-05", "小呆应用测试项目");
        } catch (WsRemoteException e) {
            e.printStackTrace();
        }
        return null;
    }
	
	public static void main(String[] args) {
        JaxWsProxyFactoryBean factoryBean = new JaxWsProxyFactoryBean();
        factoryBean.setServiceClass(PowerWsService.class);
        factoryBean.setAddress(server);
        
        wsService = (PowerWsService) factoryBean.create();
        
        ProjectEffortClient client = new ProjectEffortClient();
        String userName = "ralap";
        String password = "1";
        String token = wsService.login(userName, password);
        
        if(!"NO_USER".equals(token)) {
            
            List<Object[]> effortList = client.getEffort(token);
            
            if(effortList != null && !effortList.isEmpty()) {
                
                for(Object[] effort : effortList) {
                    
                    System.out.println(effort[0]+ "-------------" + effort[1]);
                }
            }
        }        
	}
}
