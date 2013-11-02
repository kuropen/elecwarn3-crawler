package org.kuropen.elecwarnv3.util;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public class TwitterUtilv3 {

	private Twitter twitter;
	
	/**
	 * テストするときはtrueにする。これにより「試験」とつけてツイートできる。
	 */
	public static boolean isTestMode = false;
	
	public TwitterUtilv3 (String cKey, String cSecret, String uKey, String uSecret) {
		twitter = new TwitterFactory().getInstance();
		twitter.setOAuthConsumer(cKey, cSecret);
		AccessToken aToken = new AccessToken(uKey, uSecret);
		twitter.setOAuthAccessToken(aToken);
	}
	
	/**
	 * ツイートする
	 */
	public void sendTweet(String content) {
		String status;
		if(isTestMode) {
			status = "【試験】" + content;
		}else{
			status = content;
		}
		try {
			twitter.updateStatus(status);
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
