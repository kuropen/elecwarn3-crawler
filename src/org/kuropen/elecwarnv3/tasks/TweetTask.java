/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuropen.elecwarnv3.tasks;

import co.akabe.common.electricusage.FiveMinDemand;
import co.akabe.common.electricusage.PeakSupply;
import co.akabe.elecwarn.ElectricityUsageData;
import java.util.Calendar;
import org.kuropen.elecwarnv3.AfterInfoGetTask;
import org.kuropen.elecwarnv3.util.TwitterUtilv3;

/**
 * 
 * @author Hirochika
 */
public class TweetTask implements AfterInfoGetTask {

	private static final boolean TESTFLAG = false;
	private TwitterUtilv3 tu;
	private String host;

	public TweetTask(String host, TwitterUtilv3 util) {
		tu = util;
		this.host = host;
	}

	@Override
	public void doTask(String key, FiveMinDemand demand, PeakSupply supply,
			Calendar cal) {
		ElectricityUsageData ud = new ElectricityUsageData(key,
				demand.getDemandToday(), supply.getAmount(), cal);
		String twMsg = ud.toString() + " http://" + host + "/" + key + "?year="
				+ cal.get(Calendar.YEAR) + "&month="
				+ (cal.get(Calendar.MONTH) + 1) + "&date="
				+ cal.get(Calendar.DATE);
		System.out.println(twMsg);
		// Avoid tweeting if infinity
		if (ud.getPercentage() >= 90 && ud.getPercentage() <= 100) {
			tu.sendTweet(twMsg, TESTFLAG);
		}
	}

}
