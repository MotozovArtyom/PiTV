package ru.miem.hse.pitv.activity.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import ru.miem.hse.pitv.R;

/**
 *
 */
public class MainActivity extends AppCompatActivity {

	/**
	 *
	 */
	private static final String TAG = MainActivity.class.getName();

	/**
	 * @param savedInstanceState
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		String action = intent.getAction();
		String type = intent.getType();

		Log.i(TAG, "Receive action " + action);
		Log.i(TAG, "Receive type " + type);

		if (Intent.ACTION_SEND.equals(action) && type != null) {
			if (type.equals("text/plain")) {
				handleTextIntent(intent);
			}
		}

		setContentView(R.layout.activity_main);
	}

	/**
	 * @param intent
	 */
	private void handleTextIntent(Intent intent) {
		String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
		Log.i(TAG, "Shared text = " + sharedText);

//		if (sharedText != null) {
//			Log.i(TAG,"Shared text = " + sharedText);
//		}
	}
}
