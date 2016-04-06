package com.adactin.pageobjects;

import org.openqa.selenium.WebDriver;

public class SelectHotelPage
{
	private WebDriver driver;

	public SelectHotelPage(WebDriver driver)
	{
		this.driver = driver;
		driver.get("http://adactin.com/HotelApp/SelectHotel.php");
	}
}
