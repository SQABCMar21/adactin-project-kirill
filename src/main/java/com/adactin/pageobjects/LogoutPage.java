package com.adactin.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class LogoutPage
{
	private WebDriver driver;

	@FindBy(how = How.CSS, using = ".reg_success")
	private WebElement logoutMsg;

	public LogoutPage(WebDriver driver) throws Exception
	{
		this.driver = driver;
		driver.get("http://adactin.com/HotelApp/Logout.php");
		PageFactory.initElements(driver, this);
	}

	public String logoutSuccess()
	{
		return this.logoutMsg.getText();
	}
}
