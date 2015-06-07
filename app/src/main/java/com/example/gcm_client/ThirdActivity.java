package com.example.gcm_client;

import java.util.ArrayList;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

public class ThirdActivity extends ListActivity {
	private ArrayList<String> list;
	private ArrayAdapter<String> adapter;  // ArrayList와 ListView연결
	private EditText inputText;
	private Button inputButton;
	private Button btnCstAdt;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		inputText = (EditText)findViewById(R.id.inputText);
		inputButton = (Button)findViewById(R.id.inputButton);
		btnCstAdt = (Button)findViewById(R.id.btnCstAdt);
		list = new ArrayList<String>();

		// simple_list_item_1 : 리스트 한 줄에 한 줄의 텍스트만이 표시
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
		setListAdapter(adapter);

		inputButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				list.add(inputText.getText().toString());
				inputText.setText("");
				adapter.notifyDataSetChanged();
			}
		});

		btnCstAdt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ThirdActivity.this, FourthActivity.class);
				startActivity(intent);
			}
		});
	}
}
