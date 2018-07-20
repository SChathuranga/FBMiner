package com.genesislab.seleniumbase;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class CoreBase {
	
	@SuppressWarnings("unused")
	private static URL chromeDriverUrl = CoreBase.class.getResource("/chromedriver.exe");
	User fbUser = new User();

	public User Facebook_Login(String fbid, String accountUsername, String accountPassword) throws InterruptedException 
	{
		//Set FaceBook User name
		fbUser.setFbid(fbid);
		System.out.println("FB ID: " + fbid);
		
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		options.addArguments("headless");
		System.setProperty("webdriver.chrome.driver", "/home/schathuranga/Softwares/GoogleChromeDriver/Linux/chromedriver"); // "E:/GenesisLab/GoogleChromeDriver/chromedriver.exe" "C:/chromedriver.exe"
		///media/schathuranga/My Stuff/GenesisLab/GoogleChromeDriver
		// GoogleChrome Driver
		WebDriver unitDriver = new ChromeDriver(options);
		
		String userid = "https://www.facebook.com/"+ fbid; //"s.chathuranga.jayaz"; //100003585110606
		// Declaring and initializing the HtmlUnitWebDriver
		// HtmlUnitDriver unitDriver = new
		// HtmlUnitDriver(BrowserVersion.CHROME);
		unitDriver.manage().window().maximize();
		unitDriver.get("https://www.facebook.com/");
		
		// enter credentials
		WebElement usernametextfield = unitDriver.findElement(By.id("email"));
		usernametextfield.sendKeys(accountUsername); //seeker19931@gmail.com
		WebElement passwordtextfield = unitDriver.findElement(By.id("pass"));
		passwordtextfield.sendKeys(accountPassword); //SeekAdmin19931
		WebElement loginbutton = unitDriver.findElement(By.xpath("//input[@value='Log In']"));
		loginbutton.click();

		unitDriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		JavascriptExecutor js = (JavascriptExecutor) unitDriver;
		js.executeScript("window.open('" + userid + "', '_blank');");
		unitDriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		ArrayList<String> tabs2 = new ArrayList<String>(unitDriver.getWindowHandles());
		unitDriver.switchTo().window(tabs2.get(1));

		WebElement element = unitDriver.findElement(By.xpath("//a[text()='About']"));
		JavascriptExecutor executor = (JavascriptExecutor) unitDriver;
		executor.executeScript("arguments[0].click();", element);

		//fetch and set user's name
		String username =unitDriver.findElement(By.xpath("//span[@id='fb-timeline-cover-name']/a")).getText();
		System.out.println("Name: " + username);
		fbUser.setName(username);
		
		//fetch date of birth if displayed and save
		/*WebElement dobElement = unitDriver.findElement(By.xpath("//ul[@class='uiList _4kg']/li/div/div/div/div[2]"));
		if (dobElement.isDisplayed())
		{
			String dob = unitDriver.findElement(By.xpath("//ul[@class='uiList _4kg']/li/div/div/div/div[2]")).getText();
			fbUser.setBasicInfo(fbUser.getBasicInfo() + "DOB: " + dob + " || ");
		}*/
		
		// Click Work & Education Tab
		WebElement educationlink = unitDriver.findElement(By.xpath("//ul[@class='uiList _4kg']/li[2]/a"));
		educationlink.click();

		// Get Work places
		List<WebElement> workplacesitems = unitDriver
				.findElements(By.xpath("//span[text()='Work']/../../ul/li"));

		for (WebElement workplaces : workplacesitems) 
		{
			String userWorkplaces = workplaces.getText();
			// call method for parse
			// data to another class
			fbUser.setWorkplaces(fbUser.getWorkplaces() + " || " + userWorkplaces);
			System.out.print("\nWorkPlaces: "+workplaces.getText());
		}

		// Get Education Institutes
		List<WebElement> educationitems = unitDriver.findElements(By.xpath("//span[text()='Education']/../../ul/li"));
		for (WebElement education : educationitems) { // call method for parse
														// data to another class
			String userEducation = education.getText();
			fbUser.setEducation(fbUser.getEducation() + " || " + userEducation);
			System.out.println("\nEducation: "+education.getText());
		}

		// Click Lived places
		WebElement livedlink = unitDriver.findElement(By.xpath("//ul[@class='uiList _4kg']/li[3]/a"));
		livedlink.click();

		// Get Lived Places
		List<WebElement> liveditems = unitDriver
				.findElements(By.xpath("//div[text()='Current city']/../span/a"));
		for (WebElement lived : liveditems) { // call method for parse data to
												// another class
			String userCurrentCity = lived.getText();
			fbUser.setCurrentCity(fbUser.getCurrentCity() + " || " + userCurrentCity);
			System.out.println("\nCurrent City: "+lived.getText());
		}
		
		List<WebElement> home = unitDriver
			    .findElements(By.xpath("//div[text()='Home Town']/../span/a"));
			  for (WebElement homename : home) 
			  { // call method for parse data to
			            // another class
				  String userHomeTown = homename.getText();
				  fbUser.setHomeTown(fbUser.getHomeTown() + " || " + userHomeTown);
				  System.out.print("\nHome: "+homename.getText());
			  }
		// Click Contact Info Tab
		WebElement contactinfolink = unitDriver.findElement(By.xpath("//ul[@class='uiList _4kg']/li[4]/a"));
		contactinfolink.click();

		// Get Contact Info
		List<WebElement> contactinfoitems = unitDriver
				.findElements(By.xpath("//span[text()='Contact Information']/../../ul/li"));
		for (WebElement contact : contactinfoitems) { // call method for parse
			
			// data to another class
			String userContact = contact.getText();
			fbUser.setContactInfo(fbUser.getContactInfo() + " || " + userContact);
			System.out.println("\nContact Info: "+contact.getText());
		}

		// Basic Info
		List<WebElement> basicinfoitems = unitDriver
				.findElements(By.xpath("//span[text()='Basic Information']/../../ul/li"));
		for (WebElement basicinfo : basicinfoitems) { // call method for parse
														// data to another class
			String userBasicInfo = basicinfo.getText();
			fbUser.setBasicInfo(fbUser.getEducation() + " || " + userBasicInfo);
			System.out.println("\nBasic Info: "+basicinfo.getText());
		}
		
		try
		{
			String dateOfBirth = unitDriver.findElement(By.xpath("//span[text()='Birthday']/../../div[2]/div/div")).getText();
			fbUser.setDateOfBirth(fbUser.getDateOfBirth() + " || " + dateOfBirth);
		}
		catch(Exception ex)
		{
			System.out.println("Date of Birth not available");
		}
		
		
		////span[text()='Birthday']/../../div[2]/div/div/span
		try
		{
			WebElement genderElement = unitDriver.findElement(By.xpath("//span[text()='Gender']/../../div[2]/div/div/span"));
			String gender = genderElement.getText();
			fbUser.setGender(fbUser.getGender() + "||" + gender);
		}catch(Exception ext)
		{
			System.out.println("gender not found for: " + fbid);
		}
		

		// Click Relationship
		WebElement relationshiplink = unitDriver.findElement(By.xpath("//ul[@class='uiList _4kg']/li[5]/a"));
		relationshiplink.click();

		// Get Relationship
		List<WebElement> relationshipitems = unitDriver
				.findElements(By.xpath("//span[text()='Relationship']/../../ul/li"));
		for (WebElement relationship : relationshipitems) {
			// call method for parse data to another class
			String userRelationships = relationship.getText();
			fbUser.setRelationships(fbUser.getRelationships() + " || " + userRelationships);
			System.out.println("\nrelationships: "+relationship.getText());
		}

		// Get Family Data
		List<WebElement> familyitems = unitDriver.findElements(By.xpath("//span[text()='Family Members']/../../ul/li"));
		for (WebElement family : familyitems) {
			// call method for parse data to another class
			String userFamily = family.getText();
			fbUser.setFamily(fbUser.getFamily() + " || " + userFamily);
			System.out.print("\nFamily: "+family.getText());
		}

		// click about you
		WebElement aboutyoulink = unitDriver.findElement(By.xpath("//ul[@class='uiList _4kg']/li[6]/a"));
		aboutyoulink.click();

		
		//Get About You
		List<WebElement> DetailsAboutitems = unitDriver.findElements(By.xpath("//div[@id='pagelet_bio']/div/ul/li"));
		for (WebElement details : DetailsAboutitems) {
			// call method for parse data to another class
			String userAboutDetails = details.getText();
			fbUser.setAbout(fbUser.getAbout() + " || " + userAboutDetails);
			System.out.print("\nAbout Details: "+details.getText());
		}
		
		//Get Other Name
		List<WebElement> othername = unitDriver.findElements(By.xpath("//div[text()='Nickname']/../div/span"));
		for (WebElement othernames : othername) {
			// call method for parse data to another class
			String userOtherNames = othernames.getText();
			fbUser.setOtherNames(fbUser.getOtherNames() + " || " + userOtherNames);
			System.out.print("\nOther names: "+othernames.getText());
		}
		
		//Get Favourite Quotes
		List<WebElement> Quotes = unitDriver.findElements(By.xpath("//span[text()='Favourite Quotes']/../../ul/li"));
		for (WebElement Quotesforyou : Quotes) {
			// call method for parse data to another class
			String userQuotes = Quotesforyou.getText();
			fbUser.setFavoriteQuotes(fbUser.getFavoriteQuotes() + " || " + userQuotes);
			System.out.print("\nFavorite Quotes: "+Quotesforyou.getText());
		}
		
		//Click Life Event
		WebElement lifeevent = unitDriver.findElement(By.xpath("//ul[@class='uiList _4kg']/li[7]/a"));
		lifeevent.click();
		
		List<WebElement> lifeevents = unitDriver.findElements(By.xpath("//span[text()='Life events']/../../ul/li"));
		for (WebElement events : lifeevents) {
			// call method for parse data to another class
			String userLifeEvents = events.getText();
			fbUser.setLifeEvents(fbUser.getLifeEvents() + " || " + userLifeEvents);
			System.out.print("\nLife Events: "+events.getText());
		}
		
		try
		{
			//click friends
			WebElement friendsLink = unitDriver.findElement(By.xpath("//a[text()='Friends']"));
			JavascriptExecutor executor1 = (JavascriptExecutor) unitDriver;
			executor1.executeScript("arguments[0].click();", friendsLink);
			
			List<WebElement> friends = unitDriver.findElements(By.xpath("//div[@class='uiProfileBlockContent']/div/div[2]/div/a"));
			for(WebElement friend : friends)
			{
				String friendlist = friend.getText();
				String profileLink = friend.getAttribute("href");
				String fbID = fetchFBID(profileLink);
				System.out.println("Facebook ID: " + fbID);
				fbUser.setFriends(fbUser.getFriends() + friendlist + " | FaceBook ID: " + fbID + " || ");
				System.out.println("\nFriend: " + friendlist);
				System.out.println("\n Link: " + friend.getAttribute("href")); 
				
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("No friends displayed!");
		}
		
		unitDriver.quit();
		return fbUser;
	}
	
	public String fetchFBID(String link)
	{
		String friendID=null;
		if(link.contains("profile.php"))
		{
			//https://www.facebook.com/profile.php?id=100008429190017&fref=pb&hc_location=friends_tab
			String[] arrayID = link.split("https://www.facebook.com/profile.php\\?id=");
			String[] arr2 = arrayID[1].split("\\&");
			friendID = arr2[0];
		}
		else
		{
			//https://www.facebook.com/coleenkeith.cariaga?fref=pb&hc_location=friends_tab
			String[] arr1 = link.split("\\?fref");
			String[] arr3 = arr1[0].split("https://www.facebook.com/");
			friendID = arr3[1];
		}
		return friendID;
	}
}
