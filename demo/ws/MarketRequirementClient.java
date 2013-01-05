package com.bronzesoft.eai.client;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.bronzesoft.power.eai.ws.PowerWsService;
import com.bronzesoft.power.eai.ws.dto.RemoteMarketRequirement;

public class MarketRequirementClient {


    private static PowerWsService wsService = null;
    private static String server 
            = "http://127.0.0.1/pdm//webservice/RDMWsService";

    public MarketRequirementClient() {
    }
    
    public void createMrq(String token) {
        try{            
            RemoteMarketRequirement mrq = new RemoteMarketRequirement(); 
            
            mrq.setAuditing("mission");
            mrq.setAuditingRemark("hello");
            mrq.setCompanyName("PDM");
            mrq.setCreator("j");
            mrq.setCustomerName("peter.li");
            mrq.setCustomerPos("部门经理");
            mrq.setDetectMethod("测试");
            mrq.setKeywords("产品");
            mrq.setName("需求");
            mrq.setPresenter("tank");
            mrq.setProductInfo("very good!");
            mrq.setProjectName("test");
            mrq.setPromisesRemark("0000");
            mrq.setRemark("888");
            mrq.setSecretLevel("秘密");
            mrq.setWishSolveTime("2011-12-12");
            mrq.setOwner("j");
            mrq.setWorkflowStatus("初始化");
            
            String extJson = "{\"A1\":\"AAAAAAAAA\",\"A2\":\"B2\",\"A3\":\"1000000\",\"A4\":\"2011-08-19\",\"A5\":\"George.Lu\",\"A6\":\"Eric.Liang,Kevin.Song\",\"A7\":\"C1,C2\"}";
            mrq.setExtraJson(extJson);
            
            wsService.createMarketRequirement(token, mrq);            
        }catch(Exception e){
            System.out.println("Cannot create requirement:" + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        JaxWsProxyFactoryBean factoryBean = new JaxWsProxyFactoryBean();
        factoryBean.setServiceClass(PowerWsService.class);
        factoryBean.setAddress(server);
        
        wsService = (PowerWsService) factoryBean.create();
        
        MarketRequirementClient client = new MarketRequirementClient();
        String userName = "ralap";
        String password = "1";
        String token = wsService.login(userName, password);
        
        if(!"NO_USER".equals(token)) {
            client.createMrq(token);
            wsService.logout(token);
        }        
    }

}
