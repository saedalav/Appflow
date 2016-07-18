package appFlow.formGeneration.POJOs;

import java.io.Serializable;

public class DbVendor implements Serializable {

/**
	 * 
	 */
	private static final long serialVersionUID = -6405869551863995575L;
String name, photo;
int id;

public DbVendor(int id,String name, String photo) {
	super();
	this.name = name;
	this.photo = photo;
	this.id=id;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public String getPhoto() {
	return photo;
}

public void setPhoto(String photo) {
	this.photo = photo;
}

public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
}
}
