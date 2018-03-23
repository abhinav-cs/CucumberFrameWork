package cs1.tc.framework;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import cs1.tc.framework.BasicUtil;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

@SuppressWarnings("unused")
public class UITestBase extends TestBase{
	public static Properties uiElements = null;
	public static Properties appSpecs = null;
	public String[] browserTitleArray;
	private static WebDriver driver = null;
	private static Actions builder = null;
	
	/*
	 * Opens the URL
	 */
	public void navigateToURL(String TestUrl) throws Exception {
		driver.get(TestUrl);
		driver.manage().window().maximize();
		Wait(3);
	}

	/*
	 * Initialized the parameter/variables which are going to be used during the
	 * execution
	 */
	static {
		try {

			File appSpecstemp = new File(System.getProperty("user.dir"), "//ResourceFiles//AppSpecs.properties");
			appSpecs = new Properties();
			InputStream appSpecsReader = new FileInputStream(appSpecstemp);
			appSpecs.load(appSpecsReader);

			File uiElementstemp = new File(System.getProperty("user.dir"), "//ResourceFiles//UIElements.properties");
			uiElements = new Properties();
			InputStream uiElementsReader = new FileInputStream(uiElementstemp);
			uiElements.load(uiElementsReader);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public UITestBase() {

	}

	/*
	 * Initializes driver at the time of execution
	 */
	public void initialiseDriver() {
//		FirefoxProfile profile = new FirefoxProfile(how);
		ChromeOptions profile = new ChromeOptions();
		HashMap<String, Object> prefs = new HashMap<String, Object>();
		prefs.put("download.prompt_for_download", true);
		HashMap<String, Object> options = new HashMap<String, Object>();
		options.put("prefs", prefs);
		
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		capabilities.setCapability("chromeOptions", options);
//        profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/csv, text/csv, text/plain,application/octet-stream doc xls pdf txt ,application/json");		
//		profile.setPreference("browser.download.useDownloadDir", false);
		
		System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"//WebDrivers//chromedriver.exe");
//		driver = new FirefoxDriver(profile);
		driver = new ChromeDriver(capabilities);
		builder = new Actions(driver);
	}

	/*
	 * Enter text in any textbox/text area
	 */
	public void setValueInTextBox(String value, String location, WebDriver driver) {
		driver.findElement(By.xpath(location)).sendKeys(value);
	}

	/*
	 * Click any link by its link text
	 */
	public void clickLink(String linkText) {
		WebElement e = driver.findElement(By.linkText(linkText));
		getBuilder().moveToElement(e).click().perform();
	}

	/*
	 * Click any object (eg. link,button,textbox etc) by its Xpath
	 */
	public void click(String location) {
		WebElement e = driver.findElement(By.xpath(location));
		getBuilder().moveToElement(e).click().perform();
	}

	/*
	 * It will drag and drop the Web Element from its current position by
	 * X-Offset coordinate and Y-Offset Coordinate
	 */
	public void moveElementXY(WebElement e, int xCord, int yCord, String testCaseName, String message) {
		builder.clickAndHold(e).moveByOffset(xCord, yCord).release(e).build().perform();
//		appendLogInfo(testCaseName, message);
	}

	/*
	 * It will pick the element and will drop to an other element specified
	 */
	public void moveElementTo(WebElement PickElement, WebElement DropElement, String testCaseName, String message) {
		builder.clickAndHold(PickElement).moveToElement(DropElement).release().build().perform();
//		appendLogInfo(testCaseName, message);
	}

	/*
	 * It will append the logs and will mark that as error
	 */
	public static void appendLogError(String Classname, String message) {
		log = Logger.getLogger(Classname);
		log.error(message);
	}

	/*
	 * It will append the logs and will mark that as general info
	 */
	public static void appendLogInfo(String Classname, String message) {
		log = Logger.getLogger(Classname);
		log.info(message);
	}

	/*
	 * It will hover on any element
	 */
	public void hoverToElement(String location) {
		builder.moveToElement(driver.findElement(By.xpath(location))).perform();
	}

	/*
	 * It will call the method to login to the page
	 */
	public void userLogin(String userName, String password) {
		try{
		BasicUtil.textClear(uiElements.getProperty("UserName"), getDriver());
		Wait(1);
		setValueInTextBox(userName, uiElements.getProperty("UserName"), getDriver());
		Wait(1);
		BasicUtil.textClear(uiElements.getProperty("Password"), getDriver());
		Wait(1);
		setValueInTextBox(password, uiElements.getProperty("Password"), getDriver());
		Wait(1);
		click(uiElements.getProperty("LoginButton"));
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/*
	 * It will logout the user from the application
	 */
	public void userLogout() throws InterruptedException {
		Wait(1);
		click(uiElements.getProperty("UserProfileMenu"));
		Wait(3);
		click(uiElements.getProperty("Logout"));
	}

	/*
	 * It will return a boolean that whether the element is selected or not only for checkboxes(not in the table ) and radio buttons
	 */
	/*public boolean isElementSelected(String location,String elementType) throws Exception {
		switch (elementType.toLowerCase()) {
		case "checkbox":
			return getDriver().findElement(By.xpath(location)).getAttribute("class").contains("ng-not-empty");
		case "radiobutton":
			return getDriver().findElement(By.xpath(location)).getAttribute("class").contains("ng-valid-parse");
		default:
				throw new Exception("Parsed element type in isElementSelected() method is wrong");
		}	
	}*/

	/*
	 * It verifies that the element is present or not
	 */
	public boolean isElementPresent(String location) {
		try {
			driver.findElement(By.xpath(location));
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	/*
	 * It verifies that the element is visible or not
	 */
	public boolean isElementVisible(String location) {
		try {
			if(driver.findElement(By.xpath(location)).isDisplayed())
				return true;
			else
				return false;
//			ExpectedConditions.elementToBeClickable(locator);
//			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	/*
	 * It will return the list of the element having same attributes values
	 * (esp. for the webtables)
	 */
	public List<WebElement> retElementList(String location) {
		List<WebElement> ElementList = driver.findElements(By.xpath(location));
		return ElementList;
	}

	/*
	 * It will perform the mouse scroll action
	 */
	public void mouseScrollDown() {
		JavascriptExecutor Scroll = (JavascriptExecutor) driver;
		Scroll.executeScript("window.scrollBy(0,300)", "");
	}

	public void mouseScrollUp() {
		JavascriptExecutor Scroll = (JavascriptExecutor) driver;
		Scroll.executeScript("window.scrollBy(0,-300)", "");
	}
	/*
	 * It will verify in the LHN(left hand navigation) whether the particular
	 * module is expanded or not
	 */
	public boolean isLHNModuleActive(String moduleName) {
		try {
			driver.findElement(
					By.xpath("//li[@class='ng-scope active' and @aria-hidden='false']/a[@title='" + moduleName + "']"));
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}

	}

	public boolean compareBrowserTitle(String[] browserTitleArr, WebDriver driver) {
		String browserTitle = constructBrowserTitle(browserTitleArr);
		return driver.getTitle().contains(browserTitle);
	}

	/*
	 * It will select the dropdown on the basis of its visible text
	 */
	public void selectDropdown(String location, String selectedValue) {
		WebElement e = driver.findElement(By.xpath(location));
		getBuilder().moveToElement(e).click().perform();
		BasicUtil.retDropdownElement(location, driver).selectByVisibleText(selectedValue);
	}

	/*
	 * It will return the driver object
	 */
	public WebDriver getDriver() {
		return driver;
	}

	public static void setDriver(WebDriver driver) {
		UITestBase.driver = driver;
	}

	/*
	 * It will return the builder object
	 */
	public Actions getBuilder() {
		return builder;
	}

	/*
	 * It will return the builder object
	 */
	public String findRowId(String elementOR) {
		String row = null;
		//elementName = "New_Team_" + TIME_STAMP;
		
		//Find OR
//		String elementRowOR = "//*[contains(@id,'grid-container')]//*[text()='" + elementName + "']";		
		
		WebElement rowToBeUpdated = getDriver().findElement(By.xpath(elementOR));
		String idWithRowNo = rowToBeUpdated.findElement(By.xpath("..")).getAttribute("id");
		String[] strTemp = idWithRowNo.split("-");
		row = strTemp[0] + "-" + strTemp[1];

		return row;
	}

	public boolean isAttributePresent(WebElement element, String attribute) {
	    Boolean result = false;
	    try {
	        String value = element.getAttribute(attribute);
	        if (value != null){
	            result = true;
	        }
	    } catch (Exception e) {}

	    return result;
	}
	
	//@BeforeClass
	public void openurl() {
		try {
			initialiseDriver();
			navigateToURL(environmentVariables.getProperty("URL"));
			
			Wait(7);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	}

	//@AfterClass(alwaysRun = true)
	public void tearDown() {
		getDriver().quit();
		setDriver(null);
	}
	
	/*
	 * It will create the browser title as per the parameters passed by adding '|' separator
	 */
	public static String constructBrowserTitle(String[] titleStrArray) {
		String browsertitle = null;
		String seperator = " | ";
		for(int i=titleStrArray.length-1;i>=0;i--){
			if (browsertitle==null) {
				browsertitle = titleStrArray[i];
			} else {
				browsertitle = browsertitle + seperator + titleStrArray[i];
			}
		}
		return browsertitle;
	}
}