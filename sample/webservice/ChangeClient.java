package com.bronzesoft.eai.client;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.bronzesoft.power.eai.ws.PowerWsService;
import com.bronzesoft.power.eai.ws.dto.RemoteChange;

public class ChangeClient {


    private static PowerWsService wsService = null;
    private static String server 
            = "http://127.0.0.1/pdm//webservice/RDMWsService";

    public ChangeClient() {
    }
    
    public void createChange(String token) {
        try{            
            RemoteChange change = new RemoteChange();
            
            change.setName("变更测试---webService");
            change.setProjectName("test");
            change.setConfigType("文档");
            change.setConfigName("--------dfd-----");
            change.setType("纠错");
            change.setOwner("ralap");
            change.setPriority("中");
            change.setReason("Test!!!!!!!!!!!!!");
            change.setEffectAna("nothing!!!");
            change.setCostEffectAna("time!!!");
            change.setSchedEffectAna("later one day");
            change.setCreator("ralap");
            change.setAuditing("ralap");
            change.setRemark("over");
            change.setWorkflowStatus("创建审核");
            
//            String extJson = "{\"A1\":\"AAAAAAAAA\",\"A2\":\"B2\",\"A3\":\"1000000\",\"A4\":\"2011-08-19\",\"A5\":\"George.Lu\",\"A6\":\"Eric.Liang,Kevin.Song\",\"A7\":\"C1,C2\"}";
            
//            change.setExtraJson(extJson);
            
            wsService.createChange(token, change);            
        }catch(Exception e){
            System.out.println("Cannot create requirement:" + e.getMessage());
        }
    }
    
    public void updateChange(String token) {
        try{            
            RemoteChange change = new RemoteChange();
            
            change.setConfigName("变更测试");
            
            wsService.updateChange(token, change, "33ce8bd7-8acd-4185-ad15-32b80d7e460c", "e24ce887-f386-4205-9010-eee6b1580b23");          
        }catch(Exception e){
            System.out.println("Cannot create requirement:" + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        JaxWsProxyFactoryBean factoryBean = new JaxWsProxyFactoryBean();
        factoryBean.setServiceClass(PowerWsService.class);
        factoryBean.setAddress(server);
        
        wsService = (PowerWsService) factoryBean.create();
        
        ChangeClient client = new ChangeClient();
        String userName = "ralap";
        String password = "1";
        String token = wsService.login(userName, password);
        
        if(!"NO_USER".equals(token)) {
//            client.createChange(token);
            client.updateChange(token);
            wsService.logout(token);
        }        
    }
}
