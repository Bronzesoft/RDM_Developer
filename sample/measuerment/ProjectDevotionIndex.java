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

public class ProjectDevotionIndex extends Calculator {

    @SuppressWarnings("unchecked")
    public CalculatorResult month(String objectId, String objectIdType) {
        CalculatorResult result = new CalculatorResult();
        try {
            // 查询每月的所有人汇报的工作量
            StringBuffer hql = new StringBuffer();
            hql.append("select r.reportDay, r.effort, r.creatorId from");
            hql.append(" TaskDailyReport r, Task t where (t.objectType = 'PT'");
            hql.append(" or t.objectType = 'ST') and r.task.id = t.id");
            hql.append(" and t.objectId = ? order by r.reportDay asc");

            List<Object[]> list = PowerAPI.rdmDao().getAllByHQL(hql.toString(),
                    objectId);

            String key;
            Float count = 0f;
            Map<String, Float> map1 = new LinkedHashMap<String, Float>();
            Map<String, Float> map2 = new LinkedHashMap<String, Float>();

            Date min = null, max = null;
            
            // 计算每月所有人汇报的工作量总和
            for (Object[] o : list) {
                key = DateUtil.getDateTime("yyyy-MM", (Date) o[0]);

                if (min == null || max == null) {
                    min = (Date) o[0];
                    max = (Date) o[0];
                }
                else {
                    min = DateUtil.getDays(min, (Date) o[0]) < 0 ? (Date) o[0]
                            : min;
                    max = DateUtil.getDays(max, (Date) o[0]) > 0 ? (Date) o[0]
                            : max;
                }

                if (!map1.containsKey(key)) {
                    map1.put(key, 0f);
                }
                count = (Float) map1.get(key);
                map1.put(key, Math.round((count + (Float) o[1]) * 100) / 100f);
            }

            // 查询属于本项目的工作量汇报
            hql.delete(0, hql.capacity());
            hql.append("select r.reportDay, r.effort, r.creatorId from");
            hql.append(" TaskDailyReport r, Task t where r.task.id = t.id");
            hql.append(" and r.reportDay >= ? and r.reportDay <= ?");
            hql.append(" order by r.reportDay asc");

            list = PowerAPI.rdmDao().getAllByHQL(hql.toString(),
                    new Object[] { min, max });
            
            // 计算本项目每月所有人汇报的工作量总和
            for (Object[] o : list) {
                key = DateUtil.getDateTime("yyyy-MM", (Date) o[0]);

                if (min == null || max == null) {
                    min = (Date) o[0];
                    max = (Date) o[0];
                }
                else {
                    min = DateUtil.getDays(min, (Date) o[0]) < 0 ? (Date) o[0]
                            : min;
                    max = DateUtil.getDays(max, (Date) o[0]) > 0 ? (Date) o[0]
                            : max;
                }

                if (!map2.containsKey(key)) {
                    map2.put(key, 0f);
                }
                count = (Float) map2.get(key);
                map2.put(key, Math.round((count + (Float) o[1]) * 100) / 100f);
            }

            // 计算本项目每月所有人汇报的工作量总和占当月所有工作量的比例
            for (String d : map1.keySet()) {
                count = map2.get(d) == 0 ? 100 : 100 * map1.get(d)
                        / map2.get(d);
                map1.put(d, Math.round(count * 100) / 100f);
            }

            result.setData(map1);// key: 月份， value: 月度所投入的工作量总和
            result.setResult("Y"); // 执行结果，“Y”： 成功， “N”： 失败
            result.setRemark("项目开发周期内，每月投入工作量占当月投入的总工作量的比例。"); // 计算描述
        }
        catch (Exception e) {
            LogUtil.error(getClass().getName() + ".month()\n", e);
        }
        return result;
    }
}
