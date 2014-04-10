/*
 * Copyright (C) 2011-2013 Kuropen.
 * 
 * This file is part of the Electricity Warning Crawler, Version 3.
 * 
 * The Electricity Warning Crawler is free software:
 * you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * The Electricity Warning Crawler is distributed in the hope that
 * it will be useful, but WITHOUT ANY WARRANTY; without even the implied 
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with The Electricity Warning Crawler.
 * If not, see <http://www.gnu.org/licenses/>.
 */

package org.kuropen.elecwarnv3;

import java.util.ArrayList;
import java.util.Calendar;

import org.kuropen.elecwarnv3.settings.DefaultConstants;
import org.kuropen.elecwarnv3.util.TwitterUtilv3;

import co.akabe.common.electricusage.ElectricUsageCSVParser;
import org.kuropen.elecwarnv3.tasks.TweetTask;
import org.kuropen.elecwarnv3.tasks.WebSendTask;

/**
 * Elecwarn Bot Ver.3のシェルからの起動クラス。
 * 基本的に、JavaコマンドでこのクラスをCron経由で呼び出して使う。
 */
public class ElecwarnBootstrap {
    public static void main(String[] args) {
        // 環境変数の取得
        String sendHost = System.getenv("WEBHOST");
        String consumerKey = System.getenv("CONSUMER_KEY");
        String consumerSecret = System.getenv("CONSUMER_SECRET");
        String userKey = System.getenv("USER_KEY");
        String userSecret = System.getenv("USER_SECRET");
        if (sendHost == null || consumerKey == null || consumerSecret == null || userKey == null || userSecret == null) {
            sendHost = DefaultConstants.WEBHOST;
            consumerKey = DefaultConstants.CONSUMER_KEY;
            consumerSecret = DefaultConstants.CONSUMER_SECRET;
            userKey = DefaultConstants.USER_KEY;
            userSecret = DefaultConstants.USER_SECRET;
        }

        //Twitterインスタンスの取得
        TwitterUtilv3 twUtil = new TwitterUtilv3(consumerKey, consumerSecret, userKey, userSecret);

        //現在時刻の取得
        Calendar cal = Calendar.getInstance();
        int min = cal.get(Calendar.MINUTE);

        //Webサイト送信アクションの定義
        ArrayList<AfterInfoGetTask> afterTasks = new ArrayList<>();
        afterTasks.add(new WebSendTask(sendHost));
        afterTasks.add(new TweetTask(sendHost, twUtil));
        GetInfoListener wsa = new AfterInfoGetTaskChain(afterTasks);

        //情報取得アクションの定義
        ArrayList<Action> actionList = new ArrayList<>();

        if (min % 5 == 0) {
            actionList.add(new GetInfoAction(ElectricUsageCSVParser.Format_Hokkaido, "hokkaido", wsa));
            actionList.add(new GetInfoAction(ElectricUsageCSVParser.Format_Tohoku, "tohoku", wsa));
            actionList.add(new GetInfoAction(ElectricUsageCSVParser.Format_Tokyo, "tokyo", wsa));
            actionList.add(new GetInfoAction(ElectricUsageCSVParser.buildKyushuFormat(), "kyushu", wsa));
        }
        if (min % 6 == 0) {
            actionList.add(new GetInfoAction(ElectricUsageCSVParser.Format_Chubu, "chubu", wsa));
            actionList.add(new GetInfoAction(ElectricUsageCSVParser.Format_Kansai, "kansai", wsa));
            actionList.add(new GetInfoAction(ElectricUsageCSVParser.Format_Shikoku, "shikoku", wsa));
        }
        if (min % 10 == 0) {
            actionList.add(new GetInfoAction(ElectricUsageCSVParser.Format_Hokuriku, "hokuriku", wsa));
            //actionList.add(new GetInfoAction(ElectricUsageCSVParser.Format_Chugoku, "chugoku", wsa));
        }
        if (min % 30 == 0) {
            //30分に1回は定期ツイート（現在の使用率案内）
            actionList.add(new PeriodicTweetAction(twUtil, cal));
        }

        if (actionList.size() > 0) {
            for (Action action : actionList) {
                action.doAction();
            }
        }

    }
}
