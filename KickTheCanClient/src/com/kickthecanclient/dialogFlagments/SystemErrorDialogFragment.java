package com.kickthecanclient.dialogFlagments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.kickthecanclient.activities.R;
import com.kickthecanclient.utils.DialogUtil;
import com.kickthecanclient.utils.MessageUtil;

/**
 * システムエラーダイアログ表示処理.
 *
 * @author ebihara
 */
public class SystemErrorDialogFragment extends DialogFragment {

	private Dialog dialog = null;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		dialog = new Dialog(getActivity());
		dialog.setContentView(R.layout.activity_error);

		TextView textView = (TextView)dialog.getWindow().findViewById(R.id.errorTextView);
		textView.setText(MessageUtil.getMessage("ERR001"));

		DialogUtil.setDefaultOptions(this, dialog);

		registListener();

		return dialog;
	}

	private void registListener() {
		dialog.getWindow().findViewById(R.id.errorOkButton).setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
		    	dialog.dismiss();
		    }
		});
	}
}
