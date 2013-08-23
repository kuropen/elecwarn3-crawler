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

import java.util.Vector;

import co.akabe.common.electricusage.ElectricUsageCSVParser;
import co.akabe.common.electricusage.FiveMinDemand;
import co.akabe.common.electricusage.HourlyDemand;
import co.akabe.common.electricusage.PeakSupply;
import co.akabe.common.electricusage.SupplyDataFormat;

public class GetInfoAction implements Runnable {

	private ElectricUsageCSVParser parser;
	private String companyKey;
	private GetInfoListener listener;
	
	public GetInfoAction (SupplyDataFormat format, String key, GetInfoListener listener) {
		parser = new ElectricUsageCSVParser(format);
		companyKey = key;
		this.listener = listener;
	}
	
	@Override
	public void run() {
		Vector<FiveMinDemand> demands = parser.get5MinDemand();
		PeakSupply supply = parser.getPeakSupply();
		FiveMinDemand demand = HourlyDemand.seekNearestHistory(demands);
		if(supply != null && demand != null) {
			listener.onInfoGet(companyKey, demand, supply);
		}
	}
}
