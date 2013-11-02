package org.kuropen.elecwarnv3;

import java.util.Calendar;

import org.kuropen.elecwarnv3.util.TwitterUtilv3;

public class PeriodicTweetAction implements Action {

	private Calendar calendar;
	private TwitterUtilv3 tu;
	
	public PeriodicTweetAction (TwitterUtilv3 twUtil, Calendar cal) {
		calendar = cal;
		tu = twUtil;
	}
	
	@Override
	public void doAction() {
		tu.sendTweet("各地の最新の電力使用率の情報は、 http://elecwarn.kuropen.org/ をご覧ください。 (" + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + ")");
	}

}
