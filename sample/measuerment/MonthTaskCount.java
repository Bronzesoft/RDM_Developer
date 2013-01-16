package com.bronzesoft.demo.measurement.index;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.bronzesoft.power.task.model.Task;
import com.bronzesoft.rdm.platform.PowerAPI;
import com.bronzesoft.rdm.platform.service.measuerment.impl.Calculator;
import com.bronzesoft.rdm.platform.service.measuerment.vo.CalculatorResult;
import com.bronzesoft.rdm.platform.util.DateUtil;
import com.bronzesoft.rdm.platform.util.LogUtil;

public class MonthTaskCount extends Calculator {

    @SuppressWarnings("unchecked")
    public CalculatorResult month(String objectId, String objectIdType) {
        CalculatorResult result = new CalculatorResult();
        try {
            String sql = "";
            if ("PRD".equals(objectIdType)) { // 任务归属为产品
                sql = " (b.objectType = 'DT' or b.objectType = 'ST') and ";
            }
            else if ("PJT".equals(objectIdType)) { // 任务归属为项目
                sql = " (b.objectType = 'PT' or b.objectType = 'ST') and ";
            }
            sql = " from Task b where " + sql
                    + " b.objectId = ? order by b.createdTime asc";
            List<Object> list = PowerAPI.rdmDao().getAllByHQL(sql, objectId);

            Task task;
            String key;
            int count = 0;
            Map<String, Object> map = new LinkedHashMap<String, Object>();
            
            // 根据任务创建时间，确定每月产生的任务数目
            for (Object o : list) {
                task = (Task) o;
                key = DateUtil.getDateTime("yyyy-MM", task.getCreatedTime());

                if (!map.containsKey(key)) {
                    map.put(key, 0);
                }
                count = (Integer) map.get(key);
                map.put(key, count + 1);
            }

            result.setData(map);// key: 月份， value: 每月产生的任务数
            result.setResult("Y"); // 执行结果，“Y”： 成功， “N”： 失败
            result.setRemark("项目或产品开发周期内，每月所产生的任务数。 "); //计算描述
        }
        catch (Exception e) {
            LogUtil.error(getClass().getName() + ".month()\n", e);
        }
        return result;
    }
}
