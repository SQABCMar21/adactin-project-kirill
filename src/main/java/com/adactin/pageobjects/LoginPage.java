package com.adactin.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class LoginPage
{
	private WebDriver driver;

	@FindBy(how = How.ID, using = "username")
	private WebElement usernameField;

	@FindBy(how = How.ID, using = "password")
	private WebElement passwordField;

	@FindBy(how = How.ID, using = "login")
	private WebElement loginButton;

	public LoginPage(WebDriver driver) throws Exception
	{
		this.driver = driver;
	}

	public boolean correctTitle(String val)
	{
		if (this.driver.getTitle().equals(val))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public SearchHotelPage with(String username, String password)
	{
		this.usernameField.sendKeys(username);
		this.passwordField.sendKeys(password);
		this.loginButton.click();
		return PageFactory.initElements(this.driver, SearchHotelPage.class);
	}
}
