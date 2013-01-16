package com.bronzesoft.eai.client;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.bronzesoft.power.eai.ws.PowerWsService;
import com.bronzesoft.power.eai.ws.dto.RemoteIssue;

public class IssueClient {


    private static PowerWsService wsService = null;
    private static String server 
            = "http://127.0.0.1/pdm//webservice/RDMWsService";

    public IssueClient() {
    }
    
    public void createIsu(String token) {
        try{            
            RemoteIssue isu = new RemoteIssue(); 
            
            isu.setName("问题测试");
            isu.setProjectName("1");
            isu.setActor("j,mission,peter.li");
            isu.setAuditing("tank");
            isu.setCreator("j");
            isu.setLevel("致命");
            isu.setNote("aaaaaaaaaaa");
            isu.setOwner("j");
            isu.setPresenter("ross");
            isu.setPresenterTime("2011/12/12");
            isu.setPriority("中");
            isu.setRemark("yyyyyyyyyy");
            isu.setType("技术类");
            isu.setWishSolveTime("2011/12/12");
            isu.setWorkflowStatus("初始化");
            
            String extJson = "{\"A1\":\"AAAAAAAAA\",\"A2\":\"B2\",\"A3\":\"1000000\",\"A4\":\"2011-08-19\",\"A5\":\"George.Lu\",\"A6\":\"Eric.Liang,Kevin.Song\",\"A7\":\"C1,C2\"}";
            isu.setExtraJson(extJson);
            
            wsService.createIssue(token, isu);            
        }catch(Exception e){
            System.out.println("Cannot create requirement:" + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        JaxWsProxyFactoryBean factoryBean = new JaxWsProxyFactoryBean();
        factoryBean.setServiceClass(PowerWsService.class);
        factoryBean.setAddress(server);
        
        wsService = (PowerWsService) factoryBean.create();
        
        IssueClient client = new IssueClient();
        String userName = "ralap";
        String password = "1";
        String token = wsService.login(userName, password);
        
        if(!"NO_USER".equals(token)) {
            client.createIsu(token);
            wsService.logout(token);
        }        
    }

}
