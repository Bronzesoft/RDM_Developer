package com.bronzesoft.eai.client;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.bronzesoft.power.eai.ws.PowerWsService;
import com.bronzesoft.power.eai.ws.dto.RemoteReview;

public class ReviewClient {


    private static PowerWsService wsService = null;
    private static String server 
            = "http://127.0.0.1/pdm//webservice/RDMWsService";

    public ReviewClient() {
    }
    
    public void createRew(String token) {
        try{            
            RemoteReview rew = new RemoteReview(); 
            
            rew.setName("评审测试");
            rew.setProjectName("1");
            rew.setAuditing("j");
            rew.setCreator("j");
            rew.setPresenter("j");
            rew.setOwner("j");
            rew.setPoint("TR1");
            rew.setRemark("webService Test");
            rew.setWorkflowStatus("初始化");
            
            String extJson = "{\"A1\":\"AAAAAAAAA\",\"A2\":\"B2\",\"A3\":\"1000000\",\"A4\":\"2011-08-19\",\"A5\":\"George.Lu\",\"A6\":\"Eric.Liang,Kevin.Song\",\"A7\":\"C1,C2\"}";
            rew.setExtraJson(extJson);
            
            wsService.createReview(token, rew);            
        }catch(Exception e){
            System.out.println("Cannot create requirement:" + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        JaxWsProxyFactoryBean factoryBean = new JaxWsProxyFactoryBean();
        factoryBean.setServiceClass(PowerWsService.class);
        factoryBean.setAddress(server);
        
        wsService = (PowerWsService) factoryBean.create();
        
        ReviewClient client = new ReviewClient();
        String userName = "ralap";
        String password = "1";
        String token = wsService.login(userName, password);
        
        if(!"NO_USER".equals(token)) {
            client.createRew(token);
            wsService.logout(token);
        }        
    }

}
