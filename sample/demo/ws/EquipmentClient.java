package com.bronzesoft.eai.client;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.bronzesoft.power.eai.ws.PowerWsService;
import com.bronzesoft.power.eai.ws.dto.RemoteEquipment;

public class EquipmentClient {


    private static PowerWsService wsService = null;
    private static String server 
            = "http://127.0.0.1/pdm//webservice/RDMWsService";

    public EquipmentClient() {
    }
    
    public void createEquipment(String token) {
        try{            
            RemoteEquipment eqp = new RemoteEquipment();
            
            eqp.setName("设备维护测试----webService");
            eqp.setDepartmentName("1");
            eqp.setSpecification("07225");
            eqp.setType("工具仪器");
            eqp.setUseStatus("在用");
            eqp.setRank("重点设备");
            eqp.setRemark("jjjjjjjjjjjjjjjjjjjj");
            eqp.setInstallAddress("RDM");
            eqp.setOwner("j");
            eqp.setOperator("peter");
            eqp.setCreator("j");
            eqp.setPreCheckDate("2011/12/12");
            eqp.setNextCheckDate("2012/12/21");
            eqp.setManufacturer("IBM");
            eqp.setMfrPhone("123456789");
            eqp.setMfrPostCode("518000");
            eqp.setMfrAddress("shenzhen");
            eqp.setDealer("RDM");
            eqp.setDealerPhone("987654321");
            eqp.setDealerPostCode("518000");
            eqp.setDealerAddress("shenzhen");
            eqp.setCostHour(5.5);
            eqp.setCostTimes(555.5);
            eqp.setPurchaseDate("2011/12/12");
            eqp.setDepreciationLife(5l);
            eqp.setNetSalvage(555l);
            eqp.setOriginalValue(55.5);
            eqp.setWorkflowStatus("维护中");
            
            String extJson = "{\"A1\":\"AAAAAAAAA\",\"A2\":\"B2\",\"A3\":\"1000000\",\"A4\":\"2011-08-19\",\"A5\":\"George.Lu\",\"A6\":\"Eric.Liang,Kevin.Song\",\"A7\":\"C1,C2\"}";
            
            eqp.setExtraJson(extJson);
            
            wsService.createEquipment(token, eqp);            
        }catch(Exception e){
            System.out.println("Cannot create requirement:" + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        JaxWsProxyFactoryBean factoryBean = new JaxWsProxyFactoryBean();
        factoryBean.setServiceClass(PowerWsService.class);
        factoryBean.setAddress(server);
        
        wsService = (PowerWsService) factoryBean.create();
        
        EquipmentClient client = new EquipmentClient();
        String userName = "ralap";
        String password = "1";
        String token = wsService.login(userName, password);
        
        if(!"NO_USER".equals(token)) {
            client.createEquipment(token);
            wsService.logout(token);
        }        
    }

}
