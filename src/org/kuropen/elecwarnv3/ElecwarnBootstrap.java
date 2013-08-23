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

import co.akabe.common.electricusage.ElectricUsageCSVParser;

public class ElecwarnBootstrap {
	public static void main (String[] args) {
		if(args.length == 0) {
			System.out.println("Missing web host name.");
			System.exit(1);
			return;
		}
		String sendHost = args[0];
		
		Calendar cal = Calendar.getInstance();
		int min = cal.get(Calendar.MINUTE);
		
		WebSendAction wsa = new WebSendAction(sendHost);
		
		ArrayList<GetInfoAction> actionList = new ArrayList<GetInfoAction>();
		
		if(min % 5 == 0) {
			actionList.add(new GetInfoAction(ElectricUsageCSVParser.Format_Hokkaido, "hokkaido", wsa));
			actionList.add(new GetInfoAction(ElectricUsageCSVParser.Format_Tohoku, "tohoku", wsa));
			actionList.add(new GetInfoAction(ElectricUsageCSVParser.Format_Tokyo, "tokyo", wsa));
			actionList.add(new GetInfoAction(ElectricUsageCSVParser.buildKyushuFormat(), "kyushu", wsa));
		}
		if(min % 6 == 0) {
			actionList.add(new GetInfoAction(ElectricUsageCSVParser.Format_Chubu, "chubu", wsa));
			actionList.add(new GetInfoAction(ElectricUsageCSVParser.Format_Kansai, "kansai", wsa));
			actionList.add(new GetInfoAction(ElectricUsageCSVParser.Format_Shikoku, "shikoku", wsa));
		}
		if(min % 10 == 0) {
			actionList.add(new GetInfoAction(ElectricUsageCSVParser.Format_Hokuriku, "hokuriku", wsa));
			actionList.add(new GetInfoAction(ElectricUsageCSVParser.Format_Chugoku, "chugoku", wsa));
		}
		
		if(actionList.size() > 0) {
			for(GetInfoAction action : actionList) {
				new Thread(action).start();
			}
		}
		
	}
}
