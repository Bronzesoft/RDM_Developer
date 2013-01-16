package com.bronzesoft.appDemo;

import java.util.Map;

import com.bronzesoft.power.platform.workflow.util.ParameterUtil;

public class Demo {
	
	public String demoTest(String param1, String param2) {
		
		String html = "hello, this is a demo!!!";
		
		return html;
	}
	
	public void time() {
		
		// this is timer test, and you can write code here.
		
		return;
	}
	
	public String[] export(Map map) {

		// 取得导出时参数值。
		ParameterUtil.getParameterValue("param1", map);
		ParameterUtil.getParameterValue("param2", map);
		
		return new String[] {"fileName","filePath"};
	}
}
