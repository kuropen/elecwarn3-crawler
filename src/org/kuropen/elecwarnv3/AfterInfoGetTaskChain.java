/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.kuropen.elecwarnv3;

import co.akabe.common.electricusage.FiveMinDemand;
import co.akabe.common.electricusage.PeakSupply;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 複数の {@link AfterInfoGetTask} を実行する
 */
public class AfterInfoGetTaskChain implements GetInfoListener {

    private List<AfterInfoGetTask> tasks;
    
    /**
     * コンストラクタ
     * @param lst タスクの一覧
     */
    public AfterInfoGetTaskChain (List<AfterInfoGetTask> lst) {
        tasks = lst;
    }
    
    @Override
    public void onInfoGet(String key, FiveMinDemand demand, PeakSupply supply) {
        try {
            // 最終更新日時を取得する
            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("JST"));
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/M/d");
            String dateText = demand.getDate();
            Date date = sdf.parse(dateText);
            cal.setTime(date);
            cal.set(Calendar.HOUR_OF_DAY, demand.getHour());
            cal.set(Calendar.MINUTE, demand.getMinute());
            
            for (AfterInfoGetTask task : tasks) {
                task.doTask(key, demand, supply, cal);
            }
        } catch (ParseException ex) {
            Logger.getLogger(AfterInfoGetTaskChain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
