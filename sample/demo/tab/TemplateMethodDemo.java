package com.bronzesoft.demo;

import java.util.List;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

public class TemplateMethodDemo implements TemplateMethodModel {

	public TemplateModel exec(List args) throws TemplateModelException {
		
		if(args == null || args.size() == 0) {
			throw new TemplateModelException("Wrong arguments");
		}
		double total = 0d;
		
		for(int i = 0; i < args.size(); i++) {
			total += Double.valueOf(String.valueOf(args.get(i)));
		}
		
		return new freemarker.template.SimpleNumber(total / args.size());
	}
}
