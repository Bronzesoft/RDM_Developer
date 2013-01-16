package com.bronzesoft.eai.client;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.bronzesoft.power.eai.ws.PowerWsService;
import com.bronzesoft.power.eai.ws.dto.RemoteProject;

public class ProjectClient {

	private static PowerWsService wsService = null;
	private static String server = "http://127.0.0.1/pdm//webservice/RDMWsService";

	public ProjectClient() {
    }
	
	public void createProject(String token) {
		try{            
			RemoteProject project = new RemoteProject(); 
         
			// required field   
			project.setGroupName("PDM"); 
			project.setName("Project5");
			project.setSimpleName("Demo5");
			project.setCode("55555");
			project.setTypeName("光明顶");
			project.setLevelName("高");
			project.setDepartmentName("5");
			project.setCreatorName("j"); // full name
			project.setOwnerName("J");
			
			// optional field
			project.setPopName("");
			project.setRemark("Project Description");
			
			project.setClientName("");
			project.setAddress("");
			
			// Custom field with JSON object
//			String extJson = "{\"A1\":\"AAAAAAAAA\",\"A2\":\"B12\",\"A3\":\"1000000\",\"A4\":\"2011-08-19\",\"A5\":\"George.Lu\",\"A6\":\"Eric.Liang,Kevin.Song\",\"A7\":\"C1,C2\"}";
//			project.setExtraJson(extJson);
            
            wsService.createProject(token, project);            
        }catch(Exception e){
            System.out.println("Cannot create project:" + e.getMessage());
        }
	}
		
	public static void main(String[] args) {
		JaxWsProxyFactoryBean factoryBean = new JaxWsProxyFactoryBean();
        factoryBean.setServiceClass(PowerWsService.class);
        factoryBean.setAddress(server);
        
        wsService = (PowerWsService) factoryBean.create();
        
        ProjectClient client = new ProjectClient();
        String userName = "ralap";
        String password = "1";
        String token = wsService.login(userName, password);
        
        if(!"NO_USER".equals(token)) {
            client.createProject(token);
            
            wsService.logout(token);
        }        
	}
	
}
