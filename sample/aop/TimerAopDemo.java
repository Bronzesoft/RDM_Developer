package com.bronzesoft.demo.aop;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bronzesoft.power.entity.model.Project;
import com.bronzesoft.power.lifecycle.model.Phase;
import com.bronzesoft.rdm.platform.PowerAPI;
import com.bronzesoft.rdm.platform.api.IEntityCoreService;
import com.bronzesoft.rdm.platform.exception.DBException;
import com.bronzesoft.rdm.platform.service.aop.impl.TimerAop;
import com.bronzesoft.rdm.platform.util.LogUtil;
import com.bronzesoft.rdm.platform.util.ModelUtil;

@SuppressWarnings("unchecked")
public class TimerAopDemo extends TimerAop {

	/**
	 * 获取所有进行中的项目信息，并根据指定属性值更新特定字段值
	 * 
	 * platform/config/rdm_platform_aop.xml中的配置如下：
	 * 
	 * <aop>
	 *     <module>TIMER</module>
	 *     <operation>86400000</operation>
	 *     <class>com.bronzesoft.demo.aop.TimerAopDemo</class>
	 * </aop>
	 */
	public void run() {
		/* 获取指定某些项目 */
		String projectModule = ModelUtil.getBaseDataModelName("PJT");
		
		try {
			// 使用 hibernate HQL 查询 
			List pjtList = PowerAPI.rdmDao().getAllByHQL(
					"from " + projectModule + " where isValid = 'Y'");
			
			if (pjtList != null && !pjtList.isEmpty()) {
				IEntityCoreService service = PowerAPI.entity();
				
				Project p = null;
				for (Object o : pjtList) {
					p = (Project)o;
					Phase phase = service.getEntityStatus(p.getId(), "PJT");

					// 符合特定条件的项目
					if (phase != null && "进行中".equals(phase.getName())) {
						Map map = new HashMap();
						map.put("Fld_T_00001", "txt");
						
						// 更新项目信息
						try {
							service.updateEntityData(p.getId(), "PJT", map);
						} catch (Exception e) {
							LogUtil.error("更新实体失败");
						}
					}
				}
			}
		} catch (Exception e) {
			LogUtil.error("获取项目列表失败");
		}
	}

}
