package com.kickthecanclient.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.kickthecanclient.utils.MessageUtil;

/**
 * エラー処理用Activity.
 *
 * @author ebihara
 */
public class ErrorActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_error);
		TextView textView = (TextView)findViewById(R.id.errTxtView);
		textView.setText(MessageUtil.getMessage("ERR001"));
	}
}
