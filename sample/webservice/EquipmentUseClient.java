package com.bronzesoft.eai.client;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.bronzesoft.power.eai.ws.PowerWsService;
import com.bronzesoft.power.eai.ws.dto.RemoteEquipmentUse;

public class EquipmentUseClient {


    private static PowerWsService wsService = null;
    private static String server 
            = "http://127.0.0.1/pdm//webservice/RDMWsService";

    public EquipmentUseClient() {
    }
    
    public void createEquipmentUse(String token) {
        try{            
            RemoteEquipmentUse equ = new RemoteEquipmentUse();
            
            equ.setName("设备使用申请测试----webService");
            equ.setProjectName("1");
            equ.setDepartmentName("1");
            equ.setEquipment("a");
            equ.setAppUser("j");
            equ.setAppTime("2011/12/12");
            equ.setPlanStartDate("2011/12/12");
            equ.setPlanEndDate("2011/12/23 18:00:00");
            equ.setAuditing("mission");
            equ.setCreator("j");
            equ.setNote("test,test----------");
            equ.setRemark("1234567890-----------------------------------");
            equ.setOwner("j");
            equ.setWorkflowStatus("初始化");
            
            String extJson = "{\"A1\":\"AAAAAAAAA\",\"A2\":\"B2\",\"A3\":\"1000000\",\"A4\":\"2011-08-19\",\"A5\":\"George.Lu\",\"A6\":\"Eric.Liang,Kevin.Song\",\"A7\":\"C1,C2\"}";
            
            equ.setExtraJson(extJson);
            
            wsService.createEquipmentUse(token, equ);            
        }catch(Exception e){
            System.out.println("Cannot create requirement:" + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        JaxWsProxyFactoryBean factoryBean = new JaxWsProxyFactoryBean();
        factoryBean.setServiceClass(PowerWsService.class);
        factoryBean.setAddress(server);
        
        wsService = (PowerWsService) factoryBean.create();
        
        EquipmentUseClient client = new EquipmentUseClient();
        String userName = "ralap";
        String password = "1";
        String token = wsService.login(userName, password);
        
        if(!"NO_USER".equals(token)) {
            client.createEquipmentUse(token);
            wsService.logout(token);
        }        
    }

}
