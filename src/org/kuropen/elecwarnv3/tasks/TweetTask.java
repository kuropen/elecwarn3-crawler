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
		String twMsg = ud.toString() + " https://" + host + "/" + key + "?year="
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
