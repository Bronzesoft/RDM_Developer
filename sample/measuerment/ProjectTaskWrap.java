package com.bronzesoft.demo.measurement.index;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.bronzesoft.power.task.model.Task;
import com.bronzesoft.rdm.platform.PowerAPI;
import com.bronzesoft.rdm.platform.service.measuerment.impl.Calculator;
import com.bronzesoft.rdm.platform.service.measuerment.vo.CalculatorResult;
import com.bronzesoft.rdm.platform.util.DateUtil;
import com.bronzesoft.rdm.platform.util.LogUtil;

public class ProjectTaskWrap extends Calculator {

    @SuppressWarnings("unchecked")
    public CalculatorResult month(String objectId, String objectIdType) {
        CalculatorResult result = new CalculatorResult();
        try {
            // 查询所有归属为本项目的已完成（“关闭”，“已通过”）的任务
            StringBuffer hql = new StringBuffer();
            hql.append("from Task t where t.objectType = 'PT' and");
            hql.append(" t.objectId = ? and t.status in ('CL', 'PA')");
            hql.append(" order by t.realEndDate asc");

            List<Task> list = PowerAPI.rdmDao().getAllByHQL(hql.toString(),
                    objectId);

            String key;
            Map<String, Float> map = new LinkedHashMap<String, Float>();
            Map<String, List<Task>> taskMap = new LinkedHashMap<String, List<Task>>();
            
            // 根据任务的实际完成日期， 确定每月完成的任务
            for (Task t : list) {
                key = DateUtil.getDateTime("yyyy-MM", t.getRealEndDate());

                if (!taskMap.containsKey(key)) {
                    taskMap.put(key, new ArrayList<Task>());
                }

                taskMap.get(key).add(t);
            }

            int pDt = 0;
            int totalDts = 0;
            float wrap = 0f;
            
            // 计算每月任务的平均偏差率， ∑(偏差率 * (计划完成日期 - 计划开始日期)) / ∑(计划完成日期 - 计划开始日期)
            for (String d : taskMap.keySet()) {
                pDt = 0;
                wrap = 0f;
                totalDts = 0;
                for (Task t : taskMap.get(d)) {
                    pDt = Math.abs(DateUtil.getDays(t.getPlanStartDate(), t
                            .getPlanEndDate()));
                    totalDts += pDt;
                    wrap += pDt * t.getWarpRate() / 100;
                }

                map.put(d, Math.round(wrap * 100 / totalDts) * 1f);
            }

            result.setData(map);// key: 月份， value: 月度所投入的工作量总和
            result.setResult("Y"); // 执行结果，“Y”： 成功， “N”： 失败
            result.setRemark("项目开发周期内，每月所有已完成任务的平均偏差率。"); //计算描述
        }
        catch (Exception e) {
            LogUtil.error(getClass().getName() + ".month()\n", e);
        }
        return result;
    }
}
