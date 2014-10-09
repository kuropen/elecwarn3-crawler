/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
	 * @param usage
	 *            使用量
	 * @param capacity
	 *            供給量
	 * @param cal
	 *            観測日時を表す{@link Calendar}インスタンス
	 */
	public void doTask(String key, FiveMinDemand demand, PeakSupply supply,
			Calendar cal);

}
