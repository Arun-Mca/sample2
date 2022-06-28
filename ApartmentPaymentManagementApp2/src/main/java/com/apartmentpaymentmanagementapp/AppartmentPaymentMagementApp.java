/*
 * Program for Create a Apartment payment management app.
 * @authorPrabu M
 * @since Dec 01 2021
 * 
 */ 
package com.apartmentpaymentmanagementapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AppartmentPaymentMagementApp 
{
	
// To validate mobileNumber fix the min and max length
private static final int minPhoneNumLentgh=10;
private static final int maxPhoneNumLength=15;

/*
 * Check whether the table already available or not in database 
 */
 public static boolean checkTable(String tableName, DatabaseMetaData dataBase)
 {
		 try 
		 {
			 ResultSet tables = dataBase.getTables(null, null, "Android.tableName", null);
			if(tables.next())
			{
				return true;
			}
			else
			{
				return false;
			}
		 } 
		 catch (SQLException exception) 
		 {
			System.out.println("Unable to acces due to exception");
		}
	 return false;
 }

//Validate rental user details 
 
  public static boolean checkUserDetails(String houseNumber,String ownerName,String userhouseType,String phoneNumber)
  {
	  if(houseNumber!=null&&ownerName!=null&&userhouseType!=null&&phoneNumber!=null)
	  {
		  if(phoneNumber.length()>=minPhoneNumLentgh && phoneNumber.length()<=maxPhoneNumLength)
		  {
			  return true;
		  }
		  return false;
	  }
	  return false;
  }
  
  // check the user entered valid houseNumber or Stored housenumber from db
  public static boolean checkHouseNumber(String houseNumber, Connection connect, String selectQuery)
  {
	  String storedHosueNumber=null;
	  try {
		  Statement newstate = connect.createStatement();
		  ResultSet tables=newstate.executeQuery(selectQuery); 
		while(tables.next()) 
			{ 
				storedHosueNumber=tables.getString("houseNumber"); 
			}
		if(storedHosueNumber.equals(houseNumber))
		{
			return true;
		}
	} 
	  catch (SQLException e) 
	  {
		
		System.out.println("Exception occured unable to acces");
	} 
	  
	  return false;
  }
  
  
//Validate Ownhouse user details 
  public static boolean checkUserDetails(String tenantName, String tenantPhone, double houseRent) {
	
	  if(tenantName!=null&&tenantPhone!=null && (houseRent!=0 || houseRent<0))
	  {
		  return true;
	  }
		return false;
	}
  
  public static void main(String[] args) throws IOException
  {
	  //To get userChoice cretaed the BuffereReader reference
	  BufferedReader userChoice = new BufferedReader (new InputStreamReader (System.in));
	  
	  //To connect with oracle daatabse get the url,userName and Password
	  String url,userName,password,query;
	  userName="Arun952";
	  password="Arun952";
	  url="jdbc:oracle:thin:@localhost:1521:XE";
	  
	  //Create a properties for store and check in database
	  String[] houseTypes = {"Type A: 3bhk with carparking","Type B: 2bhk with carparking","Type C: 3bhk without carparking"," Type D: 2bhk without carparking"};
	  int userNeededService;
	  char wantToContine='N';
	  String houseNumber,ownerName,phoneNumber,houseType=null;
	  String tenantName, tenantPhone;
	  String selectQuery;
	  double houseRent;
	  
	  // to check the error occured or not used try&catch
	  try
	    {
			Class.forName("oracle.jdbc.OracleDriver");
			Connection connect = DriverManager.getConnection(url,userName,password);
			DatabaseMetaData dataBase = connect.getMetaData();
			ResultSet tables ;
			Statement newstate = connect.createStatement();
			do
			{
				System.out.println("Dear Admin ,Please choose what kind of service you need\n1.Add House\n2.Avail a sophisticated service\n3.Calculate Charges\n4.Generate report\nEnter your choice in number");
				userNeededService=Integer.parseInt(userChoice.readLine());
				System.out.println(userNeededService);
				switch(userNeededService)
				{
					case 1:
						System.out.println("Dear user ,Please choose the correct deatils\n1.Own house\n2.Rental house\nEnter the choice in number");
						int userHouse=Integer.parseInt(userChoice.readLine()) ;
						//depend upon user choice we perform the access
						if (userHouse==1)
						{
							int userhouseType;
							System.out.println("Please enter your house number ");
							houseNumber=userChoice.readLine().toUpperCase().replaceAll("\\s","");
							System.out.println("Please enter your Owner Name ");
							ownerName=userChoice.readLine();
							System.out.println("Please enter your House type\n1."+
							houseTypes[0]+"\n2."+houseTypes[1]+"\n3."+houseTypes[2]+
							 "\n4."+ houseTypes[3]+
							"\nplease your choice in number");
							userhouseType=Integer.parseInt(userChoice.readLine());
							System.out.println("Please enter your Phonenumber");
						    for(int i=0;i<houseTypes.length;i++)
						    {
						    	if((userhouseType-1)==i)
						    	{
						    		houseType=houseTypes[i];
						    		break;
						    	}
						    }
							phoneNumber=userChoice.readLine();
							boolean isUserDetailsAreValid =checkUserDetails(houseNumber,ownerName,houseType,phoneNumber);
							
							// check the user details valid or not
							if(isUserDetailsAreValid)
							{
								House ownHouse= new House(houseNumber,ownerName,houseType,phoneNumber);
								if(checkTable("House",dataBase))
								{
									query = "INSERT INTO Android.House(HouseNumber,ownerName,houseType,phoneNumber) VALUES ( ?,?,?,?)";
							        PreparedStatement preStatement = connect.prepareStatement(query);
							        preStatement.setString(1, ownHouse.getHouseNumber());
							        preStatement.setString(2, ownHouse.getOwnerName());
							        preStatement.setString(3, ownHouse.getHouseType());
							        preStatement.setString(4, ownHouse.getPhoneNumber());
							        preStatement.executeUpdate();
								}
								else 
								{
									String createQuery = "create table Android.House(HouseNumber varchar2(5) PRIMARY KEY,ownerName varchar2(30),houseType varchar2(255),phoneNumber varchar2(15)))";
									newstate.execute(createQuery);
								}

							}
							else
							{
								System.out.println("Please enter the valid details");
							}
						
						}
						else if(userHouse==2)
						{
							int userhouseType;
							System.out.println("Please enter your house number ");
							houseNumber=userChoice.readLine().toUpperCase().replaceAll("\\s","");
							System.out.println("Please enter your Owner Name ");
							ownerName=userChoice.readLine();
							System.out.println("Please enter your House type\n1."+
							houseTypes[0]+"\n2."+houseTypes[1]+"\n3."+houseTypes[2]+
							 "\n4."+ houseTypes[3]+
							"\nplease your choice in number");
							userhouseType=Integer.parseInt(userChoice.readLine());
						    for(int i=0;i<houseTypes.length;i++)
						    {
						    	if((userhouseType-1)==i)
						    	{
						    		houseType=houseTypes[i];
						    		break;
						    	}
						    }
							System.out.println("Please enter your Phonenumber");
							phoneNumber=userChoice.readLine();
							System.out.println("Please enter tenant Name ");
							tenantName= userChoice.readLine();
							System.out.println("Please enter tenantp phone number ");
							tenantPhone=userChoice.readLine();
							System.out.println("Please enter house rent ");
							houseRent =Double.valueOf(userChoice.readLine());
							boolean isUserDetailsAreValid =checkUserDetails(houseNumber,ownerName,houseType,phoneNumber);
						    boolean isRentalUSerDetailsAreValid = checkUserDetails(tenantName,tenantPhone,houseRent);
						
						    if(isUserDetailsAreValid&&isRentalUSerDetailsAreValid)
						    {
						    	RentalHouse rentHouse= new RentalHouse(houseNumber,ownerName,houseType,phoneNumber,tenantName,tenantPhone,houseRent);
						    	if(checkTable("RentalHouse",dataBase))
								{
						    		query = "INSERT INTO Android.RentalHouse(HouseNumber,ownerName,houseType,phoneNumber,tenantName,tenantPhone,houseRent)  VALUES ( ?,?,?,?,?,?,?)";
							        PreparedStatement preStatement = connect.prepareStatement(query);
							        preStatement.setString(1, rentHouse.getHouseNumber());
							        preStatement.setString(2, rentHouse.getOwnerName());
							        preStatement.setString(3, rentHouse.getHouseType());
							        preStatement.setString(4, rentHouse.getPhoneNumber());
							        preStatement.setString(5, rentHouse.getTenantName());
							        preStatement.setString(6, rentHouse.getTenantPhone());
							        preStatement.setDouble(7, rentHouse.getHouseRent());
							        preStatement.executeUpdate();
								}
						    	else
						    	{
									String createQuery = "create table Android.RentalHouse(HouseNumber varchar2(5) PRIMARY KEY,ownerName varchar2(30),houseType varchar2(255),phoneNumber varchar2(15),tenantName varchar2(30),tenantPhone varchar2(30),houseRent number))";
									newstate.execute(createQuery);
						    	}
						    }
						    else
						    {
						    	System.out.println("Please Enter the valid details");
						    }
						}
						else
						{
							System.out.println("please choose the correct choice");
						}
						System.out.println("Do you want to continue Yes/No");
						 wantToContine=userChoice.readLine().charAt(0);
						break;
					case 2:
						
						System.out.println("Enter house number");
						houseNumber=userChoice.readLine().toUpperCase().replaceAll("\\s","");
						selectQuery="select * from Android.house"; 
						String serviceType,dataAvailable="";
						if (checkHouseNumber(houseNumber,connect,selectQuery))
						{
					    	System.out.println("Please choose what kind of service you need\n1.Mineral Water \n2.Car wash\n please choose choice in number"); 
					    	userNeededService=Integer.parseInt(userChoice.readLine()); 
					    	if(userNeededService==1) 
					    	{ 
					    		serviceType="Mineral Water";
					    		System.out.println("Enter number of cans you need\n each can contains 20 liters"); 
					    		int noOfcans=Integer.parseInt(userChoice.readLine());
					    		if(checkTable("HouseService",dataBase))
					    		{
					    			query = "INSERT INTO Android.HouseService(HouseNumber,servicetype,dateavailable)  VALUES ( ?,?,?,)";
							        HouseService houseService= new HouseService(houseNumber,serviceType,dataAvailable);
					    			PreparedStatement preStatement = connect.prepareStatement(query);
							        preStatement.setString(1, houseService.getHousenumber());
							        preStatement.setString(2, houseService.getServicetype());
							        preStatement.setString(2, houseService.getDateAvailed());
							        
					    		}
					    		else
					    		{
					    			String createQuery = "create table Android.HouseService(HouseNumber varchar2(5) PRIMARY KEY,servicetype varchar2(30),dateavailable varchar2(255))";
									newstate.execute(createQuery);
					    		}
					    	} 
					    		
					    	} 
					    	else if(userNeededService==2)
					    	{ 
					    		System.out.println("Enter number of cars to be washed\nplease choose choice in number"); 
					    		int numberOfCarsToWash = Integer.parseInt(userChoice.readLine());
					    		if(numberOfCarsToWash<2)
					    		{
						    		if(checkTable("HouseService",dataBase))
						    		{
						    			serviceType="Car Wash";
						    			query = "INSERT INTO Android.HouseService(HouseNumber,servicetype,dateavailable)  VALUES ( ?,?,?,)";
								        HouseService houseService= new HouseService(houseNumber,serviceType,dataAvailable);
						    			PreparedStatement preStatement = connect.prepareStatement(query);
								        preStatement.setString(1, houseService.getHousenumber());
								        preStatement.setString(2, houseService.getServicetype());
								        preStatement.setString(2, houseService.getDateAvailed());
								        
						    		}
						    		else
						    		{
						    			String createQuery = "create table Android.HouseService(HouseNumber varchar2(5) PRIMARY KEY,servicetype varchar2(30),dateavailable varchar2(255))";
										newstate.execute(createQuery);
						    		}
					    		}
					    		else
					    		{
					    			throw new MaxCarWashesExceeded ("max limit of 2 on the number of cars washes a house can avail per month.\n so kindly apply this one for next month");
					    		}
					    	} 
						
					    	else
					    	{
					    		throw new InvalidHouseNumberException("InvalidHouseNumber");
					    	}
						System.out.println("Do you want to continue Yes/No");
						 wantToContine=userChoice.readLine().charAt(0);
				    	break; 
				    	

					case 3:
						
						System.out.println("Caluclaute Charges");
						houseNumber=userChoice.readLine().toUpperCase().replaceAll("\\s","");
						selectQuery="select * from Android.House"; 
						if (checkHouseNumber(houseNumber,connect,selectQuery))
						{
						}
						
					    else
					    {
					       throw new InvalidHouseNumberException("InvalidHouseNumber");
					    }
						System.out.println("Do you want to continue Yes/No");
						 wantToContine=userChoice.readLine().charAt(0);
						break;
					case 4:
						System.out.println("Do you want to continue Yes/No");
						 wantToContine=userChoice.readLine().charAt(0);
						break;
				}
				if(wantToContine!='y'||wantToContine!='Y')
				 {
					 System.out.println("ThankYou for visiting");
				 }
			}while(wantToContine=='Y' || wantToContine=='y');
	    }
	  catch(Exception exception)
	  {
		  System.out.println("unable to connect your database due to "+exception);
	  }
	  finally
	  {
		  userChoice.close();
	  }
  }

}
