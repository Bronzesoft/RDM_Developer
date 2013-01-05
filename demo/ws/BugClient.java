package com.bronzesoft.eai.client;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.bronzesoft.power.eai.ws.PowerWsService;
import com.bronzesoft.power.eai.ws.dto.RemoteBug;

public class BugClient {


    private static PowerWsService wsService = null;
    private static String server 
            = "http://127.0.0.1/pdm//webservice/RDMWsService";

    public BugClient() {
    }
    
    public void createBug(String token) {
        try{            
            RemoteBug bug = new RemoteBug();
            
            bug.setName("webService Bug");
            bug.setProjectName("test");
            bug.setType("功能类");
            bug.setDetectMethod("功能测试");
            bug.setPriority("高");
            bug.setLevel("一般");
            bug.setProbability("高");
            bug.setPresenter("ralap");
            
            bug.setOwner("ralap");
            bug.setCreator("ralap");
            bug.setRemark("ddddddddddddddddd");
            bug.setWishSolveTime("2011-12-31");
            bug.setWorkflowStatus("创建审核");
            
            //String extJson = "{\"A1\":\"AAAAAAAAA\",\"A2\":\"B2\",\"A3\":\"1000000\",\"A4\":\"2011-08-19\",\"A5\":\"George.Lu\",\"A6\":\"Eric.Liang,Kevin.Song\",\"A7\":\"C1,C2\"}";
            
            //bug.setExtraJson(extJson);
            
            wsService.createBug(token, bug);            
        }catch(Exception e){
            System.out.println("Cannot create requirement:" + e.getMessage());
        }
    }
    
    public void updateBug(String token) {
        try{            
            RemoteBug bug = new RemoteBug();
            
            String extJson = "{\"Fld_T_00001\":\"提交测试\"}";
            
            bug.setExtraJson(extJson);
            
            wsService.updateBug(token, bug,
                "37d86978-309e-43e1-bd3b-9041551e28a3",
                "e24ce887-f386-4205-9010-eee6b1580b23");           
        }catch(Exception e){
            System.out.println("Cannot update requirement:" + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        JaxWsProxyFactoryBean factoryBean = new JaxWsProxyFactoryBean();
        factoryBean.setServiceClass(PowerWsService.class);
        factoryBean.setAddress(server);
        
        wsService = (PowerWsService) factoryBean.create();
        
        BugClient client = new BugClient();
        String userName = "ralap";
        String password = "1";
        String token = wsService.login(userName, password);
        
        if(!"NO_USER".equals(token)) {
//            client.createBug(token);
            client.updateBug(token);
            wsService.logout(token);
        }        
    }
}
