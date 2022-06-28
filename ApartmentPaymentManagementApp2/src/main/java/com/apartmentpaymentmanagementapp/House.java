package com.apartmentpaymentmanagementapp;

/*
 * Create the parent class as House with the properties of houseNumber,ownerName,phoneNumber,houseType;
 */
public class House 
{
  
	private String houseNumber,ownerName,phoneNumber,houseType;
	public House() 
	{
	}
	
	public House(String houseNumber, String ownerName,String houseType,String phoneNumber) {
		this.houseNumber = houseNumber;
		this.ownerName = ownerName;
		this.phoneNumber = phoneNumber;
		this.houseType = houseType;
	}
	
	public String getHouseNumber() {
		return houseNumber;
	}
	
	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}
	
	public String getOwnerName() {
		return ownerName;
	}
	
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String getHouseType() {
		return houseType;
	}
	
	public void setHouseType(String houseType) {
		this.houseType = houseType;
	}
	  
  
  
}
