package com.sapient.models;

import java.util.List;

public class User {
	private List<Company> favorites;
	
	/******************Constructors*************************/
	public User(){
		
	}
	
	/*********************Getters*************************/
	public List<Company> getFavorites() {
		return favorites;
	}
	
	/*******************Methods*************************/
	public void addFav(Company comp) {
		this.favorites.add(comp);
	}




	

}
