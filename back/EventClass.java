package back;

import java.sql.Date;
import java.sql.Time;

public class EventClass {
	
public Date date;
public int id;
public int idvehi;
public String Name;
public Time timeevent;

public Time getTimeevent() {
	return timeevent;
}
public void setTimeevent(Time timeevent) {
	this.timeevent = timeevent;
}
public Date getDate() {
	return date;
}
public void setDate(Date date) {
	this.date = date;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public int getIdvehi() {
	return idvehi;
}
public void setIdvehi(int idvehi) {
	this.idvehi = idvehi;
}
public String getName() {
	return Name;
}
public void setName(String name) {
	Name = name;
}
public EventClass(Date date, int id, int idvehi, String name, Time timeevent) {
	super();
	this.date = date;
	this.id = id;
	this.idvehi = idvehi;
	Name = name;
	this.timeevent = timeevent;
}



	
}
