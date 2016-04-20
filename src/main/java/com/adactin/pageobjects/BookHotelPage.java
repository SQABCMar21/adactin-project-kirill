package com.adactin.pageobjects;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BookHotelPage
{
	private WebDriver driver;
	private WebDriverWait wait;
	private Select selector;

	// Hotel location locator
	@FindBy(how = How.ID, using = "location_dis")
	private WebElement bookLocation;

	// Hotel name locator
	@FindBy(how = How.ID, using = "hotel_name_dis")
	private WebElement bookHotel;

	// Hotel room type locator
	@FindBy(how = How.ID, using = "room_type_dis")
	private WebElement bookRoomType;

	// Number of rooms in Hotel locator
	@FindBy(how = How.ID, using = "room_num_dis")
	private WebElement bookNumberOfRooms;

	// Total days locator
	@FindBy(how = How.ID, using = "total_days_dis")
	private WebElement bookTotalDays;

	// Price per Night locator
	@FindBy(how = How.ID, using = "price_night_dis")
	private WebElement bookPriceNight;

	// Total Price locator
	@FindBy(how = How.ID, using = "total_price_dis")
	private WebElement bookTotalPrice;

	// Guest(GST) locator
	@FindBy(how = How.ID, using = "gst_dis")
	private WebElement bookGuestPrice;

	// Final Billed Price locator
	@FindBy(how = How.ID, using = "final_price_dis")
	private WebElement bookBilledPrice;

	// First Name field locator
	@FindBy(how = How.ID, using = "first_name")
	private WebElement bookFirstName;

	// Last Name field locator
	@FindBy(how = How.ID, using = "last_name")
	private WebElement bookLastName;

	// Billing Address field locator
	@FindBy(how = How.ID, using = "address")
	private WebElement bookBillingAddress;

	// Credit card number field locator
	@FindBy(how = How.ID, using = "cc_num")
	private WebElement bookCCNum;

	// Credit Card Type list locator
	@FindBy(how = How.ID, using = "cc_type")
	private WebElement bookCCType;

	// Expiry Date month list locator
	@FindBy(how = How.ID, using = "cc_exp_month")
	private WebElement bookCCExpMonth;

	// Expiry Date year list locator
	@FindBy(how = How.ID, using = "cc_exp_year")
	private WebElement bookCCExpYear;

	// CVV Number field locator
	@FindBy(how = How.ID, using = "cc_cvv")
	private WebElement bookCCCVV;

	// "Book Now" button locator
	@FindBy(how = How.ID, using = "book_now")
	private WebElement bookBookHotel;

	// "Cancel" button locator
	@FindBy(how = How.ID, using = "cancel")
	private WebElement bookBookCancel;

	public BookHotelPage(WebDriver driver)
	{
		this.driver = driver;
		this.wait = new WebDriverWait(driver, 15);
	}

	public BookHotelPage billingAddress(String address)
	{
		this.bookBillingAddress.sendKeys(address);
		return this;
	}

	public List<String> bookHotelVals(String... vals)
	{
		List<String> bookedVals = new ArrayList<String>();
		for (int i = 0; i < vals.length; i++)
		{
			if (vals[i].equalsIgnoreCase("hotel name"))
			{
				vals[i] = this.bookHotel.getAttribute("value").toString();
				bookedVals.add(i, vals[i]);
			}
			else if (vals[i].equalsIgnoreCase("location"))
			{
				vals[i] = this.bookLocation.getAttribute("value").toString();
				bookedVals.add(i, vals[i]);
			}
			else if (vals[i].equalsIgnoreCase("room type"))
			{
				vals[i] = this.bookRoomType.getAttribute("value").toString();
				bookedVals.add(i, vals[i]);
			}

			else if (vals[i].equalsIgnoreCase("number of rooms"))
			{
				vals[i] = this.bookNumberOfRooms.getAttribute("value").toString();
				vals[i] = vals[i].replace("(s)", "s");
				bookedVals.add(i, vals[i]);
			}

			else if (vals[i].equalsIgnoreCase("total days"))
			{
				vals[i] = this.bookTotalDays.getAttribute("value").toString();
				vals[i] = vals[i].replace("(s)", "s");
				bookedVals.add(i, vals[i]);
			}

			else if (vals[i].equalsIgnoreCase("price per night"))
			{
				vals[i] = this.bookPriceNight.getAttribute("value").toString();
				bookedVals.add(i, vals[i]);
			}
			else if (vals[i].equalsIgnoreCase("total price"))
			{
				vals[i] = this.bookTotalPrice.getAttribute("value").toString();
				bookedVals.add(i, vals[i]);
			}
			else if (vals[i].equalsIgnoreCase("GST"))
			{
				vals[i] = this.bookGuestPrice.getAttribute("value").toString();
				bookedVals.add(i, vals[i]);
			}
			else if (vals[i].equalsIgnoreCase("final billed price"))
			{
				vals[i] = this.bookBilledPrice.getAttribute("value").toString();
				bookedVals.add(i, vals[i]);
			}
			else if (vals[i].equalsIgnoreCase("first name"))
			{
				vals[i] = this.bookFirstName.getAttribute("value").toString();
				bookedVals.add(i, vals[i]);
			}
			else if (vals[i].equalsIgnoreCase("last name"))
			{
				vals[i] = this.bookLastName.getAttribute("value").toString();
				bookedVals.add(i, vals[i]);
			}
			else if (vals[i].equalsIgnoreCase("billing address"))
			{
				vals[i] = this.bookBillingAddress.getAttribute("value").toString();
				bookedVals.add(i, vals[i]);
			}
			else if (vals[i].equalsIgnoreCase("credit card number"))
			{
				vals[i] = this.bookCCNum.getAttribute("value").toString();
				bookedVals.add(i, vals[i]);
			}
			else if (vals[i].equalsIgnoreCase("credit card type"))
			{
				vals[i] = this.bookCCType.getAttribute("value").toString();
				bookedVals.add(i, vals[i]);
			}
			else if (vals[i].equalsIgnoreCase("expire date month"))
			{
				vals[i] = this.bookCCExpMonth.getAttribute("value").toString();
				bookedVals.add(i, vals[i]);
			}
			else if (vals[i].equalsIgnoreCase("expire date year"))
			{
				vals[i] = this.bookCCExpYear.getAttribute("value").toString();
				bookedVals.add(i, vals[i]);
			}
			else if (vals[i].equalsIgnoreCase("cvv number"))
			{
				vals[i] = this.bookCCCVV.getAttribute("value").toString();
				bookedVals.add(i, vals[i]);
			}
		}
		return bookedVals;
	}

	public BookingConfirmPage bookNow()
	{

		this.bookBookHotel.click();
		this.wait.until(ExpectedConditions.titleIs("AdactIn.com - Hotel Booking Confirmation"));
		return PageFactory.initElements(this.driver, BookingConfirmPage.class);
	}

	public double calcTotalBilledPrice()
	{
		double guestPrice = calcTotalBookPrice() / 10;
		double totalBilled = calcTotalBookPrice() + guestPrice;
		return totalBilled;
	}

	public double calcTotalBookPrice()
	{
		double np = Double.parseDouble(this.bookPriceNight.getAttribute("value").toString().replaceAll("[^0-9]", ""));
		double nr = Double
				.parseDouble(this.bookNumberOfRooms.getAttribute("value").toString().replaceAll("[^0-9]", ""));
		double td = Double.parseDouble(this.bookTotalDays.getAttribute("value").toString().replaceAll("[^0-9]", ""));
		double totalPrice = np * nr * td;
		return totalPrice;
	}

	public BookHotelPage ccExpMonth(String month)
	{
		this.selector = new Select(this.bookCCExpMonth);
		this.selector.selectByVisibleText(month);
		return this;
	}

	public BookHotelPage ccExpYear(String year)
	{
		this.selector = new Select(this.bookCCExpYear);
		this.selector.selectByVisibleText(year);
		return this;
	}

	public BookHotelPage ccNumber(String ccNum)
	{
		this.bookCCNum.sendKeys(ccNum);
		return this;
	}

	public BookHotelPage ccType(String type)
	{
		this.selector = new Select(this.bookCCType);
		this.selector.selectByVisibleText(type);
		return this;
	}

	public BookHotelPage cvvNum(String cvv)
	{
		this.bookCCCVV.sendKeys(cvv);
		return this;
	}

	public BookHotelPage firstName(String fName)
	{
		this.bookFirstName.sendKeys(fName);
		return this;
	}

	public double foundBilledPrice()
	{
		double found = Double.parseDouble(this.bookBilledPrice.getAttribute("value").toString()
				.replaceAll("[^0-9]", ""));
		return found;
	}

	public double foundTotalBookPrice()
	{
		double found = Double
				.parseDouble(this.bookTotalPrice.getAttribute("value").toString().replaceAll("[^0-9]", ""));
		return found;
	}

	public BookHotelPage lastName(String lName)
	{
		this.bookLastName.sendKeys(lName);
		return this;
	}
}
