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

		Person p1 = new Person("�ȵ���̵�", "011-123-4567"); // ����Ʈ�� �߰��� ��ü�Դϴ�.
		Person p2 = new Person("����", "02-123-4567"); // ����Ʈ�� �߰��� ��ü�Դϴ�.

		m_orders.add(p1); // ����Ʈ�� ��ü�� �߰��մϴ�.
		m_orders.add(p2); // ����Ʈ�� ��ü�� �߰��մϴ�.

		PersonAdapter m_adapter = new PersonAdapter(this, R.layout.row,
				m_orders); // ����͸� �����մϴ�.
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
					bt.setText("��ȭ��ȣ: " + p.getNumber());
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