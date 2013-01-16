package com.bronzesoft.eai.client;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.bronzesoft.power.eai.ws.PowerWsService;
import com.bronzesoft.power.eai.ws.dto.RemoteUser;

public class UserClient {

	private static PowerWsService wsService = null;
	private static String server 
            = "http://192.168.5.231/pdm//webservice/RDMWsService";
	
	public void createUser(String token){
        try{            
            RemoteUser user = new RemoteUser();
            
            user.setUserName("ralap");
            user.setPassword("111111");
            user.setDepartmentName("1");
            user.setPositionName("12");
            user.setTrueName("ralap");
            user.setEmail("support@bronzesoft.com");
            user.setTelephone("0755-26410182");
            user.setMobileTelephone("138010138000");
            user.setNumber("83000M0010");
            user.setSex("1");
            
            wsService.createUser(token, user);            
        }catch(Exception e){
            System.out.println("Cannot create user:" + e.getMessage());
        }
    }
	
	public void getAllUsers(String token){
        try{
            RemoteUser[] user = wsService.getAllUser(token);
            
            if(user != null){
                System.out.println();
                System.out.println("All user information :");
                
                for(int i = 0; i < user.length; i++){
                    System.out.println( (i + 1 ) + ".User Name->" + user[i].getUserName()
                            + ";Password->" +  user[i].getPassword()
                            + ";True name->" +  user[i].getTrueName()
                            + ";Sex->" +  user[i].getSex()
                            + ";Email->" +  user[i].getEmail()
                            + ";Telephone->" +  user[i].getTelephone()
                            + ";Mobile->" +  user[i].getMobileTelephone()
                            + ";Number->" +  user[i].getNumber()
                            + ";Position->" +  user[i].getPositionName()
                            + ";Department->" +  user[i].getDepartmentName()
                            + ";Birthday->" +  user[i].getBirthday());
                }
            }
        }catch(Exception e){
            System.out.println("Cannot obtain user information:" + e.getMessage());
        }
    }
	
	public void getUser(String token , String userName){
        try{
            RemoteUser user = wsService.getUser(token, userName);
            
            if(user != null){
                System.out.println();
                System.out.println("User information :");
                System.out.println("User Name->" + user.getUserName()
                        + ";Password->" +  user.getPassword()
                        + ";True name->" +  user.getTrueName()
                        + ";Sex->" +  user.getSex()
                        + ";Email->" +  user.getEmail()
                        + ";Telephone->" +  user.getTelephone()
                        + ";Mobile->" +  user.getMobileTelephone()
                        + ";Number->" +  user.getNumber()
                        + ";Position->" +  user.getPositionName()
                        + ";Department->" +  user.getDepartmentName()
                        + ";Birthday->" +  user.getBirthday());
            }
        }catch(Exception e){
            System.out.println("Cannot obtain user information:" + e.getMessage());
        }
    }

	public void updateUser(String token, String userName){
        try{            
            RemoteUser user = new RemoteUser();
            
            //user.setUserName("Bronzesoft22");
            //user.setPassword("44");
            user.setEmail("george.lu1@bronzesoft.com");
            user.setPositionName("12");
            //user.setTelephone("0898-26412681");
            //user.setMobileTelephone("15900000000");
            //user.setNumber("83M07000010");
            //user.setSex("0");
            
            wsService.updateUser(token, userName, user);
            
        }catch(Exception e){
            System.out.println("Cannot update user information:" + e.getMessage());
        }
    }
	
	public void updateUserPassword(String token, String userName) {
		try{
			wsService.updateUserPassword(token, userName, "1", "2");
		}catch(Exception e){
            System.out.println("Cannot update password information:" + e.getMessage());
        }		
	}
	
	public void deleteUser(String token, String userName){
        try{  
        	wsService.deteleUser(token, userName);
            
        }catch(Exception e){
            System.out.println("Cannot detele user information:" + e.getMessage());
        }
    }

	public static void main(String[] args) {
		JaxWsProxyFactoryBean factoryBean = new JaxWsProxyFactoryBean();
        factoryBean.setServiceClass(PowerWsService.class);
        factoryBean.setAddress(server);
        
        wsService = (PowerWsService) factoryBean.create();
        
        UserClient userClient = new UserClient();
        String userName = "mission";
        String password = "1";
        String token = wsService.login(userName, password);
        
        userClient.createUser(token);
        //userClient.updateUser(token, userName);
        //userClient.getAllUsers(token);
        //userClient.getUser(token, "eric");
        //userClient.updateUser(token, "Bronzesoft1");
        //userClient.updateUserPassword(token, userName);
        //userClient.deleteUser(token, "admin");
        
        wsService.logout(token);
	}

}
