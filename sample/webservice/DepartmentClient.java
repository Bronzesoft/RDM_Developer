package com.bronzesoft.eai.client;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.bronzesoft.power.eai.ws.PowerWsService;
import com.bronzesoft.power.eai.ws.dto.RemoteDepartment;

public class DepartmentClient {

	private static PowerWsService wsService = null;
	private static String server 
            = "http://127.0.0.1/pdm//webservice/RDMWsService";

	public DepartmentClient() {
    }
	
	public void createDepartment(String token) {
		try{            
			RemoteDepartment department = new RemoteDepartment(); 
            
			department.setName("5");
			department.setNumber("555");
			department.setManagerName("j");
			department.setSecretaryName("j");			
			department.setTelephone("0755-26410182");
			department.setFax("0755-26412481");
			department.setAddress("Shenzhen Software Park");
			department.setRemark("Project Management Office");
            
            wsService.createDepartment(token, department);            
        }catch(Exception e){
            System.out.println("Cannot create department:" + e.getMessage());
        }
	}
	
	public void getDepartment(String token) {
		try{
			RemoteDepartment department = wsService.getDepartment(token, "PMO");
            
            if(department != null){
                System.out.println();
                System.out.println("Department information :");
                System.out.println("Department Name->" + department.getName()
                		+ ";Number->" +  department.getNumber()
                		+ ";Manager->" +  department.getManagerName()
                		+ ";Secretary->" +  department.getSecretaryName()
                		+ ";Telephone->" +  department.getTelephone()
                		+ ";Fax->" +  department.getFax()
                		+ ";Address->" +  department.getAddress()
                        + ";Description->" +  department.getRemark());
            }
        }catch(Exception e){
            System.out.println("Cannot obtain department information:" + e.getMessage());
        }
	}
	
	public void getAllDepartments(String token) {
		try{
			RemoteDepartment[] departments = wsService.getAllDepartments(token);
            
            if(departments != null){
                System.out.println();
                System.out.println("All department information :");
                
                for(int i = 0; i < departments.length; i++){
                    System.out.println((i + 1) 
                    		+ "Department Name->" + departments[i].getName()
                    		+ ";Number->" +  departments[i].getNumber()
                    		+ ";Manager->" +  departments[i].getManagerName()
                    		+ ";Secretary->" +  departments[i].getSecretaryName()
                    		+ ";Telephone->" +  departments[i].getTelephone()
                    		+ ";Fax->" +  departments[i].getFax()
                    		+ ";Address->" +  departments[i].getAddress()
                            + ";Description->" +  departments[i].getRemark());
                }
            }
        }catch(Exception e){
            System.out.println("Cannot obtain department information:" + e.getMessage());
        }
	}
	
	public void updateDepartment(String token) {
		try{            
			RemoteDepartment department = new RemoteDepartment(); 
            
			department.setName("CXO");
			department.setNumber("00001");
			department.setManagerName("George");
			department.setSecretaryName("Winny");			
			department.setTelephone("0755-26410182");
			department.setFax("0755-26412481");
			department.setAddress("Shenzhen Software Park");
			department.setRemark("Cheif Execute Office");
            
            wsService.updateDepartment(token, "PMO", department);            
        }catch(Exception e){
            System.out.println("Cannot update department:" + e.getMessage());
        }
	}
	
	public void deleteDepartment(String token) {
		try{            
            wsService.deleteDepartment(token, "CXO");            
        }catch(Exception e){
            System.out.println("Cannot delete department:" + e.getMessage());
        }
	}
	
	public static void main(String[] args) {
		JaxWsProxyFactoryBean factoryBean = new JaxWsProxyFactoryBean();
        factoryBean.setServiceClass(PowerWsService.class);
        factoryBean.setAddress(server);
        
        wsService = (PowerWsService) factoryBean.create();
        
        DepartmentClient departmentClient = new DepartmentClient();
        String userName = "admin";
        String password = "1";
        String token = wsService.login(userName, password);
        
        if(!"NO_USER".equals(token)) {
        	departmentClient.createDepartment(token);
//            departmentClient.getDepartment(token);
//            departmentClient.getAllDepartments(token);
//            departmentClient.updateDepartment(token);
//            departmentClient.deleteDepartment(token);
            
            wsService.logout(token);
        }        
	}
	
}
