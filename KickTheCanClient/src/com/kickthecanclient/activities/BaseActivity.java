package com.kickthecanclient.activities;

import android.app.Activity;

import com.kickthecanclient.utils.ApplicationUtil;

/**
 * Activity基底クラス.
 *
 * @author ebihara
 */
public abstract class BaseActivity extends Activity {

	public BaseActivity () {
		ApplicationUtil.setInstance(this);
	}
}
