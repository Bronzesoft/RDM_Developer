package com.bronzesoft.demo.app;

import java.util.Map;

import com.bronzesoft.app.core.AppService;

@SuppressWarnings("unchecked")
public class AppDemo extends AppService {

	public String demoTest(String param1, String param2) {
		// 返回页面HTML
		return "hello, RDM application!";
	}

	public void time() {
		// 定时器需要处理的业务
		return;
	}

	public String[] export(Map map) {
		/* 第一步：取得导出时参数值.*/
		
		/* 第二步：在指定路径生成文件*/
		
		// 返回文件名称和路径
		return new String[] { "fileName", "filePath" };
	}
}
