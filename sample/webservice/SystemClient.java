package com.bronzesoft.eai.client;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.bronzesoft.power.eai.ws.PowerWsService;
import com.bronzesoft.power.eai.ws.dto.RemoteCompany;
import com.bronzesoft.power.eai.ws.dto.RemoteRDM;
import com.bronzesoft.power.eai.ws.dto.RemoteRole;

public class SystemClient {

	private static PowerWsService wsService = null;
	private static String server 
            = "http://192.168.5.5:2000//webservice/RDMWsService";
	
	public SystemClient() {
    }
	
	public String login(String userName, String password){
        String token = wsService.login(userName, password);
        
        if("NO_USER".equals(token)){
            System.out.println("Login Unsuccessful!");
        }else{
            System.out.println("Login Successful!");
        }
        
        return token;
    }
	
	public void getServerInfo(String token) {
		try {
			RemoteRDM rdm = wsService.getServerInfo(token);
			
			if(rdm != null) {
				System.out.println();
                System.out.println("Server information :");
                System.out.println("Company Name->" + rdm.getCompanyName()
                        + ";Product Name->" +  rdm.getProductName()
                        + ";Version->" +  rdm.getVersion());
			}
		} catch (Exception e) {
			System.out.println("Cannot obtain server information:" + e.getMessage());
		}
	}
	
	public void getAllRoles(String token) {
		try {
			RemoteRole[] roles = wsService.getAllRoles(token);
			
			if(roles != null) {
				System.out.println();
                System.out.println("All role information :");
                for(int i = 0; i < roles.length; i++){
                    System.out.println( (i + 1 ) + ".Role Name->" + roles[i].getName()
                            + ";Description->" +  roles[i].getRemark());
                }
			}
		} catch (Exception e) {
			System.out.println("Cannot obtain role information:" + e.getMessage());
		}
	}
	
	public void getCompany(String token) {
		try {
			RemoteCompany company = wsService.getCompany(token);
			
			if(company != null) {
				System.out.println();
                System.out.println("Company information :");
                System.out.println("Company Full Name->" + company.getTotalName()
                        + ";Short Name->" +  company.getSimpleName()
                        + ";Website->" +  company.getWebAddress()
                        + ";Address->" +  company.getAddress()
                        + ";PostCode->" +  company.getPostCode());
			}
		} catch (Exception e) {
			System.out.println("Cannot obtain company information:" + e.getMessage());
		}
	}
	
	public void updateCompany(String token) {		
		try{            
			RemoteCompany company = new RemoteCompany();
            
			company.setTotalName("Bronzesoft System Inc.");
			company.setSimpleName("Bronzesoft");
			company.setWebAddress("www.bronzesoft.com");
			company.setAddress("Shenzhen City");
			company.setPostCode("518057");
            
            wsService.updateCompany(token, company);            
        }catch(Exception e){
            System.out.println("Cannot update user information:" + e.getMessage());
        }
	}
	
	public static void main(String[] args) {
		JaxWsProxyFactoryBean factoryBean = new JaxWsProxyFactoryBean();
        factoryBean.setServiceClass(PowerWsService.class);
        factoryBean.setAddress(server);
        
        wsService = (PowerWsService) factoryBean.create();
        
        SystemClient systemClient = new SystemClient();
        String userName = "admin";
        String token = systemClient.login(userName, "1");
        
        if(!"NO_USER".equals(token)) {
        	systemClient.getServerInfo(token);
            systemClient.getAllRoles(token);
            systemClient.getCompany(token);
            systemClient.updateCompany(token);
            
            wsService.logout(token);
        }        
	}
	
}
