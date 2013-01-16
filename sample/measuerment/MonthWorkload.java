package com.bronzesoft.demo.measurement.index;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.bronzesoft.rdm.platform.PowerAPI;
import com.bronzesoft.rdm.platform.service.measuerment.impl.Calculator;
import com.bronzesoft.rdm.platform.service.measuerment.vo.CalculatorResult;
import com.bronzesoft.rdm.platform.util.DateUtil;
import com.bronzesoft.rdm.platform.util.LogUtil;

public class MonthWorkload extends Calculator {

    @SuppressWarnings("unchecked")
    public CalculatorResult month(String objectId, String objectIdType) {
        CalculatorResult result = new CalculatorResult();
        try {
            StringBuffer hql = new StringBuffer();
            hql.append("select r.reportDay, r.effort, r.creatorId ");
            hql.append(" from TaskDailyReport r, Task t where");
            hql.append(" (t.objectType = 'PT' or t.objectType = 'ST')");
            hql.append(" and r.task.id = t.id and t.objectId = ?");
            List<Object[]> list = PowerAPI.rdmDao().getAllByHQL(hql.toString(),
                    objectId);

            String key;
            Float count = 0f;
            Map<String, Object> map = new LinkedHashMap<String, Object>();
            
            // 根据工作量的汇报时间，确定每月产生的工作量总和
            for (Object[] o : list) {
                key = DateUtil.getDateTime("yyyy-MM", (Date) o[0]);

                if (!map.containsKey(key)) {
                    map.put(key, 0f);
                }
                count = (Float) map.get(key);
                map.put(key, Math.round((count + (Float) o[1]) * 100) / 100f);
            }

            result.setData(map);// key: 月份， value: 月度所投入的工作量总和
            result.setResult("Y"); // 执行结果，“Y”： 成功， “N”： 失败
            result.setRemark("项目开发周期内，每月投入工作量。"); //计算描述
        }
        catch (Exception e) {
            LogUtil.error(getClass().getName() + ".month()\n", e);
        }
        return result;
    }
}
