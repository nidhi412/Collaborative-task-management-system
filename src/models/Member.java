package models;

import javafx.beans.property.SimpleStringProperty;


public class Member {	
SimpleStringProperty Name;//
SimpleStringProperty Phone;
SimpleStringProperty Email;
SimpleStringProperty Action;


public Member(String name, String phone, String email,
		String action) {
	super();
	 this.Name = new SimpleStringProperty (name);
	this.Phone = new SimpleStringProperty (phone);
	this.Email = new SimpleStringProperty (email);
	this.Action = new SimpleStringProperty (action);
	
}

public String getName() {
	return Name.get();
}

public void setName(SimpleStringProperty name) {
	Name = name;
}

public String getPhone() {
	return Phone.get();
}

public void setPhone(SimpleStringProperty phone) {
	Phone = phone;
}

public String getEmail() {
	return Email.get();
}

public void setEmail(SimpleStringProperty email) {
	Email = email;
}

public String getAction() {
	return Action.get();
}

public void setAction(SimpleStringProperty action) {
	Action = action;
}


}