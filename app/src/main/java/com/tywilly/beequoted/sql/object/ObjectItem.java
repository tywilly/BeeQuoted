package com.tywilly.beequoted.sql.object;

public class ObjectItem {

	public String quote = null;
	public String author = null;
	
	public int ID = 0;
	
	public ObjectItem(String quote, String author, int ID){
		this.quote = quote;
		this.author = author;
		this.ID = ID;
	}
	
}
