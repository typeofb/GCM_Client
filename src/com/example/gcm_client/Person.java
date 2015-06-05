package com.example.gcm_client;

public class Person {
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
}
