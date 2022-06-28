/*
 * create the rental class with the properties of super class (House) it also 
 * contains the own properties
 */
package com.apartmentpaymentmanagementapp;

public class RentalHouse extends House
{
	private String tenantName, tenantPhone;
	private double houseRent;
	
	public RentalHouse() {
		super();
	}
	public RentalHouse(String houseNumber, String ownerName, String houseType, String phoneNumber,String tenantName, 
			String tenantPhone,double houseRent) 
	{
		super(houseNumber, ownerName, houseType,phoneNumber);
		this.tenantName = tenantName;
		this.tenantPhone = tenantPhone;
		this.houseRent = houseRent;
	}
	public String getTenantName() {
		return tenantName;
	}
	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}
	public String getTenantPhone() {
		return tenantPhone;
	}
	public void setTenantPhone(String tenantPhone) {
		this.tenantPhone = tenantPhone;
	}
	public double getHouseRent() {
		return houseRent;
	}
	public void setHouseRent(double houseRent) {
		this.houseRent = houseRent;
	}
	
}
