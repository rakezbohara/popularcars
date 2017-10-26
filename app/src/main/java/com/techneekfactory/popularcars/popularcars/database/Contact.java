package com.techneekfactory.popularcars.popularcars.database;

public class Contact {
	
	//private variables
	int _id;
	String _name;

	
	// Empty constructor
	public Contact(){
		
	}
	// constructor
	public Contact(int id, String name){
		this._id = id;
		this._name = name;

	}
	
	// constructor
	public Contact(String name, String _phone_number){
		this._name = name;

	}
	// getting ID
	public int getID(){
		return this._id;
	}
	
	// setting id
	public void setID(int id){
		this._id = id;
	}
	
	// getting name
	public String getName(){
		return this._name;
	}
	
	// setting name
	public void setName(String name){
		this._name = name;
	}
	


}
