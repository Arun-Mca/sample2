package com.apartmentpaymentmanagementapp;
/*
 * Create the houseService for get the properties for service Type and date
 */

public class HouseService 
{
   private String housenumber,servicetype,dateAvailed;

public HouseService(String housenumber, String servicetype, String dateAvailed) {
	super();
	this.housenumber = housenumber;
	this.servicetype = servicetype;
	this.dateAvailed = dateAvailed;
}

public String getHousenumber() {
	return housenumber;
}

public void setHousenumber(String housenumber) {
	this.housenumber = housenumber;
}

public String getServicetype() {
	return servicetype;
}

public void setServicetype(String servicetype) {
	this.servicetype = servicetype;
}

public String getDateAvailed() {
	return dateAvailed;
}

public void setDateAvailed(String dateAvailed) {
	this.dateAvailed = dateAvailed;
}
   
}
