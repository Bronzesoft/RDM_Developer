package com.bronzesoft.eai.client;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.bronzesoft.power.eai.ws.PowerWsService;
import com.bronzesoft.power.eai.ws.dto.RemotePosition;
import com.bronzesoft.power.eai.ws.dto.RemotePositionLevel;
import com.bronzesoft.power.eai.ws.dto.RemotePositionType;

public class PositionClient {

	private static PowerWsService wsService = null;
	private static String server 
            = "http://127.0.0.1:2000//webservice/RDMWsService";
	
	public PositionClient() {		
	}

	public void getPositionTypes(String token) {
		try{
			RemotePositionType[] positionType = wsService.getPositionTypes(token);
            
            if(positionType != null){
                System.out.println();
                System.out.println("All position type information :");
                
                for(int i = 0; i < positionType.length; i++){
                    System.out.println( (i + 1 ) 
                    		+ ".Type Name->" + positionType[i].getName()
                            + ";Description->" +  positionType[i].getRemark());
                }
            }
        }catch(Exception e){
            System.out.println("Cannot obtain position type information:" + e.getMessage());
        }
	}
	
	public void getPositionLevels(String token) {
		try{
			RemotePositionLevel[] positionLevel = wsService.getPositionLevels(token);
            
            if(positionLevel != null){
                System.out.println();
                System.out.println("All position level information :");
                
                for(int i = 0; i < positionLevel.length; i++){
                    System.out.println( (i + 1 ) 
                    		+ ".Level Name->" + positionLevel[i].getName()
                            + ";Description->" +  positionLevel[i].getRemark());
                }
            }
        }catch(Exception e){
            System.out.println("Cannot obtain position level information:" + e.getMessage());
        }
	}
	
	public void createPosition(String token) {		
		try{            
			RemotePosition position = new RemotePosition(); 
            
			position.setName("SWE");
			position.setRemark("Software Engineer");
			position.setPositionTypeName("Senior");
            
            wsService.createPosition(token, position);            
        }catch(Exception e){
            System.out.println("Cannot create position:" + e.getMessage());
        }
	}
	
	public void getPosition(String token) {
		try{
			RemotePosition position = wsService.getPosition(token, "SWE");
            
            if(position != null){
                System.out.println();
                System.out.println("Position information :");
                System.out.println("Position Name->" + position.getName()
                		+ ";Position Type Name->" +  position.getPositionTypeName()
                        + ";Description->" +  position.getRemark());
            }
        }catch(Exception e){
            System.out.println("Cannot obtain position information:" + e.getMessage());
        }
	}
	
	public void getAllPositions(String token) {
		try{
			RemotePosition[] positions = wsService.getAllPositions(token);
            
            if(positions != null){
                System.out.println();
                System.out.println("All position information :");
                
                for(int i = 0; i < positions.length; i++){
                    System.out.println((i + 1) 
                    		+ ".Position Name->" + positions[i].getName()
                    		+ ";Position Type Name->" + positions[i].getPositionTypeName()
                            + ";Description->" +  positions[i].getRemark());
                }
            }
        }catch(Exception e){
            System.out.println("Cannot obtain position information:" + e.getMessage());
        }
	}
	
	public void updatePosition(String token) {
		try{            
			RemotePosition position = new RemotePosition(); 
            
			position.setName("HWE");
			position.setRemark("Hardware Engineer");
			position.setPositionTypeName("Junior");
            
            wsService.updatePosition(token, "SWE", position);            
        }catch(Exception e){
            System.out.println("Cannot update position:" + e.getMessage());
        }
	}
	
	public void deletePosition(String token) {
		try{            
            wsService.deletePosition(token, "HWE");            
        }catch(Exception e){
            System.out.println("Cannot delete position:" + e.getMessage());
        }
	}
	
	public static void main(String[] args) {
		JaxWsProxyFactoryBean factoryBean = new JaxWsProxyFactoryBean();
        factoryBean.setServiceClass(PowerWsService.class);
        factoryBean.setAddress(server);
        
        wsService = (PowerWsService) factoryBean.create();
        
        PositionClient positionClient = new PositionClient();
        String userName = "admin";
        String password = "1";
        String token = wsService.login(userName, password);
        
        if(!"NO_USER".equals(token)) {
        	positionClient.getPositionTypes(token);
            positionClient.getPositionLevels(token);
            positionClient.createPosition(token);
            positionClient.getPosition(token);
            positionClient.getAllPositions(token);
            positionClient.updatePosition(token);
            positionClient.deletePosition(token);
            
            wsService.logout(token);
        }        
	}
	
}
