package com.bronzesoft.eai.client;

import java.util.List;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.bronzesoft.power.eai.ws.PowerWsService;
import com.bronzesoft.power.eai.ws.dto.RemoteProjectExpend;
import com.bronzesoft.power.eai.ws.exception.WsRemoteException;

@SuppressWarnings("unchecked")
public class ProjectExpendClient {


    private static PowerWsService wsService = null;
    private static String server 
            = "http://127.0.0.1/pdm//webservice/RDMWsService";

    public ProjectExpendClient() {
    }
    
    public void createExpend(String token) {
        try{         
            
            RemoteProjectExpend expend = new RemoteProjectExpend();
            
            expend.setName("webService测试---支出005");
            expend.setProjectName("testsss");
            expend.setCode("WS00002");
            expend.setBudgetName("webService---预算测试");
            expend.setCreator("J");
            expend.setExpendDate("2011-12-07");
            expend.setHandleUser("J");
            expend.setMoney(55d);
            expend.setSubjectName("时间成本");
            expend.setModifyTime("2011-12-07");
            expend.setRemark("web service test!!!");
            
            wsService.createProjectExpend(token, expend);
            
        }catch(Exception e){
            System.out.println("Cannot create expend:" + e.getMessage());
        }
    }
    
    public void updateExpend(String token) {
        
        try{         
            RemoteProjectExpend expend = new RemoteProjectExpend();
            
            expend.setCode("WS55555555555555");
            
            wsService.updateProjectExpend(token, "webService测试---支出005",
                "testsss", expend);
            
        }catch(Exception e){
            System.out.println("Cannot create budget:" + e.getMessage());
        }
    }
    
    public void getExpend(String token) {
        
        try {
           List<Object[]> object = wsService.getExpendByDate(token, "2011-01-01", "2011-12-12");
           
           if(object != null && !object.isEmpty()) {
               
               for(Object[] b : object) {
                   System.out.println("Name:" +b[0]+"---Code:"+b[1]+"----Money:"+b[2]+"----ExpendDate:"+b[3]);
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
        
        ProjectExpendClient client = new ProjectExpendClient();
        String userName = "ralap";
        String password = "1";
        String token = wsService.login(userName, password);
        
        if(!"NO_USER".equals(token)) {
            client.createExpend(token);
            client.updateExpend(token);
            client.getExpend(token);
            wsService.logout(token);
        }        
    }
}
