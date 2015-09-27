package com.kickthecanclient.dialogFlagments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.kickthecanclient.activities.R;
import com.kickthecanclient.enums.MessageId;
import com.kickthecanclient.utils.DialogUtil;
import com.kickthecanclient.utils.MessageUtil;

/**
 * エラーダイアログ表示処理.
 *
 * @author ebihara
 */
public class ErrorDialogFragment extends DialogFragment {

	private Dialog dialog = null;
	private MessageId messageId = null;

	public ErrorDialogFragment(MessageId messageId) {
		this.messageId = messageId;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		this.dialog = new Dialog(getActivity());
		this.dialog.setContentView(R.layout.activity_error);

		TextView textView = (TextView)this.dialog.getWindow().findViewById(R.id.errorTextView);
		textView.setText(MessageUtil.getMessage(this.messageId.name()));

		DialogUtil.setDefaultOptions(this, this.dialog);

		registListener();

		return this.dialog;
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
