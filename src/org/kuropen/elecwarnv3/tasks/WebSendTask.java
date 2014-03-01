/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.kuropen.elecwarnv3.tasks;

import co.akabe.common.electricusage.FiveMinDemand;
import co.akabe.common.electricusage.PeakSupply;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.kuropen.elecwarnv3.AfterInfoGetTask;

/**
 * elecwarn v3のアーカイブサイトに送信を行うタスク
 */
public class WebSendTask implements AfterInfoGetTask {

    private String host;

    public WebSendTask(String host) {
        this.host = host;
    }
    
    @Override
    public void doTask(String key, FiveMinDemand demand, PeakSupply supply, Calendar cal) {
        try {
            int usage = demand.getDemandToday();
            int capacity = supply.getAmount();
            String query = "http://" + host + "/dataregist?company=" + key
                    + "&consume=" + usage + "&capacity=" + capacity
                    + "&recorddate=" + getTime(cal);

            System.out.println(query);

            ArrayList<String> result = readFromURL(query);
            for (String line : result) {
                //TODO ログの出し方
                System.out.println(line);
            }
        } catch (IOException ex) {
            Logger.getLogger(WebSendTask.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * UNIXタイムスタンプを得る
     *
     * @param cal カレンダーインスタンス
     * @return UNIXタイムスタンプ
     */
    private long getTime(Calendar cal) {
        return cal.getTime().getTime() / 1000;
    }

    /**
     * URLにアクセスして情報を取得する
     *
     * @param addr URL
     * @return 取得した文字列
     * @throws IOException
     */
    private static ArrayList<String> readFromURL(String addr) throws IOException {
        System.out.println(addr);
        URL url = new URL(addr);
        URLConnection connection = url.openConnection();
        connection.setDoInput(true);
        InputStream inStream = connection.getInputStream();

        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(
                    inStream));

            String line = "";
            ArrayList<String> ret = new ArrayList<>();
            while ((line = input.readLine()) != null)
                ret.add(line);
            return ret;
        } finally {
            inStream.close();
        }
    }
    
}
