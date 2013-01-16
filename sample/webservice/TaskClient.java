package com.bronzesoft.eai.client;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.bronzesoft.power.eai.ws.PowerWsService;
import com.bronzesoft.power.eai.ws.dto.RemoteTask;
import com.bronzesoft.power.eai.ws.dto.RemoteTaskDailyReport;

public class TaskClient {

	private static PowerWsService wsService = null;
    private static String server 
    = "http://127.0.0.1/pdm//webservice/RDMWsService";

	public TaskClient() {
    }
	
	public void createTask(String token) {
		try{            
		    RemoteTask task = new RemoteTask(); 
           
		    // required field   
		    task.setName("Task_WebService----");		    
		    task.setPlanStartDate("2011-12-28");			
		    task.setPlanEndDate("2011-12-28");
		    task.setPlanEffort("16");
		    task.setCreatorName("j");
		    
		    // Release task or node, owner is required.
		    task.setOwnerName("j");		    
		    
		    // optional field
		    task.setTypeName("方案设计");
		    task.setLevelName("重要 紧急");
		    task.setActorNames("j");
		    task.setAuditorNames("j");
		    task.setRemark("George.Lu,Kevin.Song,Peter.Li");
		    task.setAcceptCriteria("AAAAAAAAAAAAAAAAAAAA");
		    
		    // "IN","NR" for task, "IN","TC" for node. null - "IN" as default.
		    task.setStatus("NR");
		    task.setIsNode("Y"); // "Y" - node, "N" - task, null - task as default.
		    
		    task.setObjectType("PT"); // "PT" - project task, "DT" - department task, "OT" - ALL_IN_ONE
		    task.setObjectName("1"); // name of project or department.
		    task.setPlanName("");
		    task.setMilestoneName("");
		    
		    task.setParentName("");
		    task.setPredecessors("");
         
        // Custom field with JSON object   
//		    String extJson = "{\"A1\":\"AAAAAAAAA\",\"A2\":\"B2\",\"A3\":\"1000000\",\"A4\":\"2011-08-19\",\"A5\":\"George.Lu\",\"A6\":\"Eric.Liang,Kevin.Song\",\"A7\":\"C1,C2\"}";
//		    task.setExtraJson(extJson);
            
            wsService.createTask(token, task);            
        }catch(Exception e){
            System.out.println("Cannot create task:" + e.getMessage());
        }
	}
	
	public void updateTask(String token) {
	    try{            
            RemoteTask task = new RemoteTask(); 
	    
            task.setObjectName("1");
            
            wsService.updateTask(token, "fd595958-1cdf-4eb3-a3c7-12a43f2719a4", task);
	    }catch(Exception e){
            System.out.println("Cannot update task:" + e.getMessage());
        }
	}
	
	public void createTaskDailyReport(String token) {
	    try{ 
	    RemoteTaskDailyReport report = new RemoteTaskDailyReport();
	    
	    report.setReportDay("2011-12-12");
	    report.setEffort(2f);
	    report.setRate(1);
	    report.setCreator("J");
	    report.setForecastFinishDate("2011-12-31");
	    report.setForecastRemanetEffort(200f);
	    report.setIssueRemark("有点难！！");
	    report.setRemark("测试----webService");
	    
	    wsService.createTaskDailyReport(token, "fd595958-1cdf-4eb3-a3c7-12a43f2719a4", report);
	    }catch(Exception e){
            System.out.println("Cannot create taskDailyReport:" + e.getMessage());
        }
	}
		
	public static void main(String[] args) {
		JaxWsProxyFactoryBean factoryBean = new JaxWsProxyFactoryBean();
        factoryBean.setServiceClass(PowerWsService.class);
        factoryBean.setAddress(server);
        
        wsService = (PowerWsService) factoryBean.create();
        
        TaskClient client = new TaskClient();
        String userName = "ralap";
        String password = "1";
        String token = wsService.login(userName, password);
        
        if(!"NO_USER".equals(token)) {
//            client.createTask(token);
            
//            client.updateTask(token);
            
            client.createTaskDailyReport(token);
            
            wsService.logout(token);
        } 
	}
	
}
