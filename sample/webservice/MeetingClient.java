package com.bronzesoft.eai.client;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.bronzesoft.power.eai.ws.PowerWsService;
import com.bronzesoft.power.eai.ws.dto.RemoteMeeting;

public class MeetingClient {


    private static PowerWsService wsService = null;
    private static String server 
            = "http://127.0.0.1/pdm//webservice/RDMWsService";

    public MeetingClient() {
    }
    
    public void createMeeting(String token) {
        try{            
            RemoteMeeting meeting = new RemoteMeeting();
            
            meeting.setName("会议测试----webService");
            meeting.setProjectName("1");
            meeting.setDepartmentName("1");
            
            meeting.setPlanStartDate("2010-12-12 08:00:20");
            meeting.setPlanEndDate("2011-11-24 08:20:00");
            
            meeting.setType("项目会议");
            
            meeting.setActor("j,mission,peter.li,eric");
            meeting.setRatifier("mission");
            meeting.setCompere("tank");
            meeting.setSecretary("peter");
            meeting.setCreator("j");
            meeting.setAuditing("kevin");
            meeting.setOwner("j");
            
            meeting.setMeetingRoom("T2");
            meeting.setEquipment("P1");
            meeting.setAddress("RDM");
            
            meeting.setWorkflowStatus("初始化");
            meeting.setRemark("hello, test!!!");
            meeting.setTopic("测试测试时");
            
            String extJson = "{\"A1\":\"AAAAAAAAA\",\"A2\":\"B2\",\"A3\":\"1000000\",\"A4\":\"2011-08-19\",\"A5\":\"George.Lu\",\"A6\":\"Eric.Liang,Kevin.Song\",\"A7\":\"C1,C2\"}";
            
            meeting.setExtraJson(extJson);
            
            wsService.createMeeting(token, meeting);            
        }catch(Exception e){
            System.out.println("Cannot create requirement:" + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        JaxWsProxyFactoryBean factoryBean = new JaxWsProxyFactoryBean();
        factoryBean.setServiceClass(PowerWsService.class);
        factoryBean.setAddress(server);
        
        wsService = (PowerWsService) factoryBean.create();
        
        MeetingClient client = new MeetingClient();
        String userName = "ralap";
        String password = "1";
        String token = wsService.login(userName, password);
        
        if(!"NO_USER".equals(token)) {
            client.createMeeting(token);
            wsService.logout(token);
        }        
    }

}
