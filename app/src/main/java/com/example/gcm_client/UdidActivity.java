package com.example.gcm_client;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UdidActivity extends Activity {
	
	private Button btnTerminate;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.popup_udid);
		
		btnTerminate = (Button)findViewById(R.id.terminateActivity);
		btnTerminate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
}