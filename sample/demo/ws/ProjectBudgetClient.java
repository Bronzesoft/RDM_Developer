package com.bronzesoft.eai.client;

import java.util.List;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.bronzesoft.power.eai.ws.PowerWsService;
import com.bronzesoft.power.eai.ws.dto.RemoteProjectBudget;
import com.bronzesoft.power.eai.ws.exception.WsRemoteException;

@SuppressWarnings("unchecked")
public class ProjectBudgetClient {


    private static PowerWsService wsService = null;
    private static String server 
            = "http://127.0.0.1/pdm//webservice/RDMWsService";

    public ProjectBudgetClient() {
    }
    
    public void createBudget(String token) {
        try{         
            RemoteProjectBudget budget = new RemoteProjectBudget();
            
            budget.setName("webService---预算测试003");
            budget.setCode("WS0000001");
            budget.setBudgetMoney(55d);
            budget.setCreator("J");
            budget.setStartDate("2011-12-06");
            budget.setEndDate("2011-12-18");
            budget.setProjectName("testsss");
            budget.setSubjectName("时间成本");
            budget.setRemark("web service test!!!");
            budget.setCreatedTime("2011-12-07");
            
            wsService.createProjectBudget(token, budget);
            
        }catch(Exception e){
            System.out.println("Cannot create budget:" + e.getMessage());
        }
    }
    
    public void updateBudget(String token) {
        
        try{         
            RemoteProjectBudget budget = new RemoteProjectBudget();
            
            budget.setName("webService---预算测试005");
            
            wsService.updateProjectBudget(token, "webService---预算测试006",
                "testsss", budget);
            
        }catch(Exception e){
            System.out.println("Cannot create budget:" + e.getMessage());
        }
    }
    
    public void getBudget(String token) {
        
        try {
           List<Object[]> object = wsService.getPlanBudgetByDate(token, "2011-01-01", "2011-12-12");
           
           if(object != null && !object.isEmpty()) {
               
               for(Object[] b : object) {
                   System.out.println("Name:" +b[0]+"---Code:"+b[1]+"----Money:"+b[2]+"----StartDate:"+b[3]+"----EndDate:"+b[4]);
               }
           }
            
        } catch (WsRemoteException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        JaxWsProxyFactoryBean factoryBean = new JaxWsProxyFactoryBean();
        factoryBean.setServiceClass(PowerWsService.class);
        factoryBean.setAddress(server);
        
        wsService = (PowerWsService) factoryBean.create();
        
        ProjectBudgetClient client = new ProjectBudgetClient();
        String userName = "ralap";
        String password = "1";
        String token = wsService.login(userName, password);
        
        if(!"NO_USER".equals(token)) {
//            client.createBudget(token);
            client.updateBudget(token);
//            client.getBudget(token);
            wsService.logout(token);
        }        
    }
}
