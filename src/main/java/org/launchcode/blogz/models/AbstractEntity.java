package org.launchcode.blogz.models;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

@MappedSuperclass//pushes uid down into the table for post and user in the database
public abstract class AbstractEntity {
//creates unique user id for all the classes that will be stored in the database
	private int uid;
	
	@Id//sets it as a primary key 
    @GeneratedValue//hibernate generates a value for me
    @NotNull
    @Column(name = "uid", unique = true)
	public int getUid() {
		return this.uid;
	}
	
	protected void setUid(int uid) {
        this.uid = uid;
    }
	
}
