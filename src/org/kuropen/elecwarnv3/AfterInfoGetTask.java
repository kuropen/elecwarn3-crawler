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

import co.akabe.common.electricusage.FiveMinDemand;
import co.akabe.common.electricusage.PeakSupply;
import java.util.Calendar;

/**
 * 使用率情報を取得した後のタスク。 {@link AfterInfoGetTaskChain}から呼び出される。
 */
public interface AfterInfoGetTask {

	/**
	 * タスクを実行する。
	 * 
	 * @param key
	 *            会社コード
	 * @param demand
	 *            使用量
	 * @param supply
	 *            供給量
	 * @param cal
	 *            観測日時を表す{@link Calendar}インスタンス
	 */
	public void doTask(String key, FiveMinDemand demand, PeakSupply supply,
			Calendar cal);

}
