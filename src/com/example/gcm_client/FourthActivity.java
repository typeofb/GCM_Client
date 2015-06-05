package com.example.gcm_client;

import java.util.ArrayList;
import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class FourthActivity extends ListActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		ArrayList<Person> m_orders = new ArrayList<Person>();

		Person p1 = new Person("안드로이드", "011-123-4567"); // 리스트에 추가할 객체입니다.
		Person p2 = new Person("구글", "02-123-4567"); // 리스트에 추가할 객체입니다.

		m_orders.add(p1); // 리스트에 객체를 추가합니다.
		m_orders.add(p2); // 리스트에 객체를 추가합니다.

		PersonAdapter m_adapter = new PersonAdapter(this, R.layout.row,
				m_orders); // 어댑터를 생성합니다.
		setListAdapter(m_adapter);
	}

	private class PersonAdapter extends ArrayAdapter<Person> {

		private ArrayList<Person> items;

		public PersonAdapter(Context context, int textViewResourceId,
				ArrayList<Person> items) {
			super(context, textViewResourceId, items);
			this.items = items;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			if (v == null) {
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.row, null);
			}
			Person p = items.get(position);
			if (p != null) {
				TextView tt = (TextView) v.findViewById(R.id.toptext);
				TextView bt = (TextView) v.findViewById(R.id.bottomtext);
				if (tt != null) {
					tt.setText(p.getName());
				}
				if (bt != null) {
					bt.setText("전화번호: " + p.getNumber());
				}
			}
			return v;
		}
	}

	/*class Person {

		private String Name;
		private String Number;

		public Person(String _Name, String _Number) {
			this.Name = _Name;
			this.Number = _Number;
		}

		public String getName() {
			return Name;
		}

		public String getNumber() {
			return Number;
		}
	}*/
}