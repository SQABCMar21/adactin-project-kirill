package com.adactin.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class LogoutPage
{
	private WebDriver driver;

	@FindBy(how = How.CSS, using = ".reg_success")
	private WebElement logoutMsg;

	public LogoutPage(WebDriver driver) throws Exception
	{
		this.driver = driver;
	}

	public String logoutSuccess()
	{
		return this.logoutMsg.getText();
	}
}
