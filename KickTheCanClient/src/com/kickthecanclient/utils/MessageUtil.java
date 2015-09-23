package com.kickthecanclient.utils;

/**
 * メッセージ関連の処理クラス.
 *
 * @author ebihara
 */
public class MessageUtil {

	public static String getMessage (String messageId) {
		return PropertyFileUtil.getProperty("message", messageId);
	}

	public static String getMessage (String messageId, String... replacementTexts) {
		String message = PropertyFileUtil.getProperty("message", messageId);
		for (int i = 0 ; i < replacementTexts.length ; i++) {
			message = message.replace(StringUtil.join("{", String.valueOf(i) , "}"), replacementTexts[i]);
		}
		return message;
	}
}
