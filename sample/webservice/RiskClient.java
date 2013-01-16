package com.bronzesoft.eai.client;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.bronzesoft.power.eai.ws.PowerWsService;
import com.bronzesoft.power.eai.ws.dto.RemoteRisk;

public class RiskClient {


    private static PowerWsService wsService = null;
    private static String server 
            = "http://127.0.0.1/pdm//webservice/RDMWsService";

    public RiskClient() {
    }
    
    public void createRsk(String token) {
        try{            
            RemoteRisk rsk = new RemoteRisk(); 
            
            rsk.setName("风险测试");
            rsk.setProjectName("1");
            rsk.setActor("j,mission,peter.li");
            rsk.setAuditing("tank");
            rsk.setCreator("j");
            rsk.setEffectDegree("中");
            rsk.setEffectRemark("系统崩溃");
            rsk.setLevel("中");
            rsk.setNote("aaaaaaaaaaa");
            rsk.setOwner("j");
            rsk.setPresenterTime("2011/12/12");
            rsk.setProbability("中");
            rsk.setRemark("yyyyyyyyyy");
            rsk.setType("技术类");
            rsk.setWishSolveTime("2011/12/12");
            rsk.setWorkflowStatus("初始化");
            
            
            wsService.createRisk(token, rsk);            
        }catch(Exception e){
            System.out.println("Cannot create requirement:" + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        JaxWsProxyFactoryBean factoryBean = new JaxWsProxyFactoryBean();
        factoryBean.setServiceClass(PowerWsService.class);
        factoryBean.setAddress(server);
        
        wsService = (PowerWsService) factoryBean.create();
        
        RiskClient client = new RiskClient();
        String userName = "ralap";
        String password = "1";
        String token = wsService.login(userName, password);
        
        if(!"NO_USER".equals(token)) {
            client.createRsk(token);
            wsService.logout(token);
        }        
    }

}
