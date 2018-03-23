package cs1.tc.framework;

import static cs1.tc.framework.UITestBase.*;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;

//import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import com.google.gson.JsonParser;
import com.relevantcodes.extentreports.ExtentTest;
import com.sun.mail.util.MailSSLSocketFactory;

import cs1.tc.framework.UITestBase;

/**
 * 
 * @author Abhinav
 *
 */
public class BasicUtil {
	private final static UITestBase BASE_CLASS = new UITestBase();

	
	/*
	 * Returns boolean for element is enabled or not
	 */
	public static Boolean isElementEnabled(String location,WebDriver driver){
		
		return (driver.findElement(By.xpath(location)).isEnabled());
		
	}
	
	/*
	 * It will clear the textbox
	 */
	public static void textClear(String location, WebDriver driver) {
		driver.findElement(By.xpath(location)).clear();
	}

	/*
	 * Press any browser key without depending upon any element
	 */
	public static void pressBrowserKey(Keys key, Actions builder) {
		builder.sendKeys(key).build().perform();
	}

	/*
	 * Press two browser key without depending upon any element
	 */
	public static void pressTwoBrowserKey(Keys firstKey, Keys secondKey, Actions builder) {
		builder.sendKeys(firstKey).sendKeys(secondKey).build().perform();
	}

	/*
	 * It will press the key for the particular element
	 */
	public static void pressKey(String location, Keys key, WebDriver driver) {
		driver.findElement(By.xpath(location)).sendKeys(key);
	}

	public static void uploadDownloadFile(String filePath,Boolean uploadFile,Boolean downloadFile) throws Exception {
		String autoItFilePath= null; 
		if(uploadFile){
			autoItFilePath= System.getProperty("user.dir")+"\\FileUpload.exe ";	
		}else if (downloadFile) {
			autoItFilePath= System.getProperty("user.dir")+"\\FileDownload.exe ";
		}
		String autoItCommand = autoItFilePath+filePath;
		Runtime.getRuntime().exec(autoItCommand);
	}
	/*
	 * It will upload the file for the window dialogue
	 */
	public static void uploadFile(String filepath,boolean delayReq) {
		try {
			StringSelection stringSelection = new StringSelection(filepath);
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			Thread.sleep(2000);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
			Thread.sleep(2000);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
			Thread.sleep(2000);
			if(delayReq){
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * It verify that json file is not empty
	 */
	public static Boolean isJsonFileEmpty(String filepath){
		Boolean flag=true;
		try {
			JsonParser parser = new JsonParser();
			Object obj = parser.parse(new FileReader(filepath));
			if(obj.toString()!=null){
				flag=false;
			}
		} catch (Exception e) {
		}
		return flag;
	}
	
	
	/*
	 * It will export the file for the windows dialogue box
	 */
	public static void exportFile(String filepath,boolean delayReq) {
		try {
			StringSelection stringSelection = new StringSelection(filepath);
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
			Robot robot = new Robot();
			
			//Clearing the text from the window dialogue box
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_A);
			robot.keyRelease(KeyEvent.VK_A);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			
			Thread.sleep(2000);
			//pasting the path of the file 
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			Thread.sleep(3000);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
			Thread.sleep(2000);
//			robot.keyPress(KeyEvent.VK_ENTER);
//			robot.keyRelease(KeyEvent.VK_ENTER);
			Thread.sleep(2000);
			if(delayReq){
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/*
	 * Enter text in any textbox/text area without help of any object and then
	 * pressing the any Special key in a single flow
	 */
	public static void sendTextPlusKey(String text, Keys key, Actions builder) {
		builder.sendKeys(text).sendKeys(key).build().perform();
	}

	/*
	 * It will compare the text value when an element is given
	 */
	public static boolean compareTxtValue(WebElement e, String compvalue) {
		return (e.getText().contains(compvalue));
	}
	
	/*
	 * It will return the Drop down element
	 */
	public static Select retDropdownElement(String location, WebDriver driver) {
		WebElement mySelectElement = driver.findElement(By.xpath(location));
		Select dropdown = new Select(mySelectElement);
		return dropdown;
	}

	/*
	 * It will set value in the Frame's text box
	 */
	public static void setValueInFrameTextBox(String frameLocation, String fieldLocation, String fieldVal,
			WebDriver driver, Actions builder) {

		driver.switchTo().frame(driver.findElement(By.xpath(frameLocation)));
		try {
			Wait(2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		driver.findElement(By.xpath(fieldLocation)).clear();
		pressBrowserKey(Keys.DELETE, builder);
		BASE_CLASS.setValueInTextBox(fieldVal, fieldLocation, driver);
		driver.switchTo().defaultContent();
	}
	
	//To add wait till the element is displayed
	public static void waitForElement(String elementPath, int waitDuration) throws InterruptedException {
		int holdtime=0;
		while(BASE_CLASS.retElementList(elementPath).isEmpty() && holdtime<=waitDuration){
			holdtime++;
			Wait(1);
		}
	}
	
	/*
	 * To verify that the pop up window is displayed
	 */
	public static boolean verifyPopupWindowIsDisplayed(String popupName) {
		
		String popupWindowXpath = uiElements.getProperty("PopupWindow")+"/*[contains(text(),'"+popupName+"')]";
		
		if(BASE_CLASS.isElementPresent(popupWindowXpath)){
			return true;
		}else {
			return false;
		}
	}	
	
	
	/*
	 * Giving step names to the Playbook's Steps/Actions
	 */
	public static void addPlaybookStepName(String stepnm, WebDriver driver) {
		driver.findElement(By.xpath(uiElements.getProperty("stepName"))).clear();
		driver.findElement(By.xpath(uiElements.getProperty("stepName"))).sendKeys(stepnm);
	}

	/*
	 * Add Action trigger step in the playbook and also moving the steps also
	 */
	public static void addActionTrigger(String actionName, String testCaseName, String stepnm, String inputRecord,
			int step_Xcord, int step_Ycord, WebDriver driver) throws InterruptedException {
		Wait(3);
		BASE_CLASS.click(uiElements.getProperty("PlaybookStep") + actionName + "']");
		Wait(3);
		addPlaybookStepName(stepnm, driver);
		driver.findElement(
				By.xpath(uiElements.getProperty("Action_trigger_InputRecord") + inputRecord + "']")).click();
		BASE_CLASS.click(uiElements.getProperty("stepSaveBtn"));
		Wait(4);
		String stepXpath = "//p[text()='" + stepnm + "']";
		WebElement step = driver.findElement(By.xpath(stepXpath));
		Wait(5);
		BASE_CLASS.moveElementXY(step, step_Xcord, step_Ycord, testCaseName, "Moving step name" + stepnm);
		BASE_CLASS.click(uiElements.getProperty("pbSaveBtn"));
		Wait(4);
	}

	/*
	 * Add Send mail step in the playbook and also moving the steps also
	 */
	public static void addSendMail(String actionName, String testCaseName, String stepnm, String emailId,
			String mailSubject, String mailContent, int step_Xcord, int step_Ycord, WebDriver driver, Actions builder)
			throws InterruptedException {
		Wait(4);
		BASE_CLASS.click(uiElements.getProperty("PlaybookStep") + actionName + "']");
		Wait(4);
		addPlaybookStepName(stepnm, driver);
		pressBrowserKey(Keys.TAB, builder);
		pressBrowserKey(Keys.TAB, builder);
		sendTextPlusKey(emailId, Keys.TAB, builder);
		BASE_CLASS.setValueInTextBox(mailSubject, uiElements.getProperty("mailSubjectText"), driver);
		BASE_CLASS.setValueInTextBox(mailContent, uiElements.getProperty("mailContent"), driver);
		BASE_CLASS.click(uiElements.getProperty("stepSaveBtn"));
		Wait(4);
		BASE_CLASS.click(uiElements.getProperty("pbSaveBtn"));
		Wait(4);
		String stepXpath = "//p[text()='" + stepnm + "']";
		WebElement step = driver.findElement(By.xpath(stepXpath));
		BASE_CLASS.moveElementXY(step, step_Xcord, step_Ycord, "Add Playbook", "Moving step name" + stepnm);
		BASE_CLASS.click(uiElements.getProperty("pbSaveBtn"));
		Wait(4);
	}

	/*
	 * Add No trigger step in the playbook and also moving the steps also
	 */
	public static void noTrigger(String actionName, String testCaseName, String stepnm, int step_Xcord, int step_Ycord,
			WebDriver driver) throws InterruptedException {
		Wait(2);
		BASE_CLASS.click(uiElements.getProperty("PlaybookStep") + actionName + "']");
		Wait(4);
		addPlaybookStepName(stepnm, driver);
		BASE_CLASS.click(uiElements.getProperty("stepSaveBtn"));
		Wait(4);
		BASE_CLASS.click(uiElements.getProperty("pbSaveBtn"));
		Wait(4);
		String stepXpath = "//p[text()='" + stepnm + "']";
		WebElement step = driver.findElement(By.xpath(stepXpath));
		BASE_CLASS.moveElementXY(step, step_Xcord, step_Ycord, testCaseName, "Moving step name" + stepnm);
		BASE_CLASS.click(uiElements.getProperty("pbSaveBtn"));
	}

	/*
	 * Add Reference Playbook Step and also moving the step also
	 */
	public static void referencePlaybookStep(String actionName, String testCaseName, String stepnm,
			String referencePlaybookName, int step_Xcord, int step_Ycord, WebDriver driver)
			throws InterruptedException {
		Wait(2);
		BASE_CLASS.click(uiElements.getProperty("PlaybookStep") + actionName + "']");
		Wait(4);
		addPlaybookStepName(stepnm, driver);
		BASE_CLASS.setValueInTextBox(referencePlaybookName, uiElements.getProperty("playbookReference"),
				driver);
		Wait(5);
		pressKey(uiElements.getProperty("playbookReference"), Keys.TAB, BASE_CLASS.getDriver());
		BASE_CLASS.click(uiElements.getProperty("stepSaveBtn"));
		Wait(4);
		BASE_CLASS.click(uiElements.getProperty("pbSaveBtn"));
		Wait(4);
		String stepXpath = "//p[text()='" + stepnm + "']";
		WebElement step = driver.findElement(By.xpath(stepXpath));
		BASE_CLASS.moveElementXY(step, step_Xcord, step_Ycord, testCaseName, "Moving step name" + stepnm);
		Wait(4);
		BASE_CLASS.click(uiElements.getProperty("pbSaveBtn"));
	}

	/*
	 * Add new collection in the application
	 */
	public static void addCollection(String collectionName, WebDriver driver) throws InterruptedException {
		Wait(3);
		BASE_CLASS.click(uiElements.getProperty("addNewCollection"));
		Wait(2);
		BASE_CLASS.setValueInTextBox(collectionName, uiElements.getProperty("Nametxt"), driver);
		BASE_CLASS.click(uiElements.getProperty("SaveNewPB"));
		Wait(2);
	}

	/*
	 * Add new Playbook to the collection only if the particular collection is already opened
	 */
	public static void addCollectionPB(String pbName, Boolean pbActiveFlag, WebDriver driver)
			throws InterruptedException {
		Wait(5);
		BASE_CLASS.click(uiElements.getProperty("CollAddNewPBbtn"));
		Wait(2);
		BASE_CLASS.setValueInTextBox(pbName, uiElements.getProperty("Nametxt"), driver);
		if (pbActiveFlag) {
			BASE_CLASS.click(uiElements.getProperty("ActiveCheckbox"));
		}
		BASE_CLASS.click(uiElements.getProperty("SaveNewPB"));
		Wait(4);
	}

	/*
	 * Add new Playbook from the Homepage only 
	 */
	public static void addHomePB(String pbName, Boolean pbActiveFlag, String collectionName, Actions builder,
			WebDriver driver) throws InterruptedException {
		Wait(2);
		BASE_CLASS.click(uiElements.getProperty("HomeAddNewPBbtn"));
		Wait(2);
		BASE_CLASS.setValueInTextBox(pbName, uiElements.getProperty("Nametxt"), driver);
		BASE_CLASS.setValueInTextBox(collectionName, uiElements.getProperty("CollectionName"), driver);
		Wait(4);
		pressBrowserKey(Keys.TAB, builder);
		Wait(4);
		pressBrowserKey(Keys.TAB, builder);
		if (pbActiveFlag) {
			BASE_CLASS.click(uiElements.getProperty("ActiveCheckbox"));
		}
		BASE_CLASS.click(uiElements.getProperty("SaveNewPB"));
		Wait(4);
	}

	/*
	 * Verify role permission 
	 */
	public static int verifyPermission(String location, boolean chkBoxStatus)	{
		String classValToVerify;
		int i;
		
		if(chkBoxStatus){
			classValToVerify = "ng-not-empty";//for checked
		}else{
			classValToVerify = "ng-empty";//for un-checked
		}
		List<WebElement> permissionTableElements = new ArrayList<WebElement>(
				BASE_CLASS.retElementList(location));
		Collections.copy(permissionTableElements, BASE_CLASS.retElementList(location));
		
		for (i = 0; i < permissionTableElements.size(); i++) {
			BASE_CLASS.getBuilder().moveToElement(permissionTableElements.get(i)).perform();
			if(!(permissionTableElements.get(i).getAttribute("class").contains(classValToVerify))){
				//Fail
				break;
			}
		}
					
		return i;
	
	}


	/*
	 * Verify role field permission 
	 */
	@SuppressWarnings("static-access")
	public static String verifyFieldPermission(String AccessType)	{
		String idValToVerify;
		int i;
		try{
			if(!(AccessType.equalsIgnoreCase("Full") || AccessType.equalsIgnoreCase("ReadOnly") || AccessType.equalsIgnoreCase("NoAccess"))){
				return null;
			}
			
			if(AccessType.equalsIgnoreCase("Full")){
				idValToVerify = "full";
				BASE_CLASS.click(BASE_CLASS.uiElements.getProperty("FullFieldPermissionApplyToAllLink"));
				BASE_CLASS.Wait(1);
			}else if(AccessType.equalsIgnoreCase("ReadOnly")){
				idValToVerify = "readOnly";
				BASE_CLASS.click(BASE_CLASS.uiElements.getProperty("ReadFieldPermissionApplyToAllLink"));
				BASE_CLASS.Wait(1);
			}else if(AccessType.equalsIgnoreCase("NoAccess")){
				idValToVerify = "noAccess";
				BASE_CLASS.click(BASE_CLASS.uiElements.getProperty("NoFieldPermissionApplyToAllLink"));
				BASE_CLASS.Wait(1);
			}else{
				idValToVerify = "full";
				BASE_CLASS.click(BASE_CLASS.uiElements.getProperty("FullFieldPermissionApplyToAllLink"));
				BASE_CLASS.Wait(1);
			}
			
			String locCheckedEle = "//*[@class='modal-body']//*[@class='ui-grid-canvas']//input[contains(@id, '"+idValToVerify+"')]";
			String locUncheckedEle = "//*[@class='modal-body']//*[@class='ui-grid-canvas']//input[not(contains(@id, '"+idValToVerify+"'))]";
			
			
	
			List<WebElement> fieldPermissionCheckedElements = new ArrayList<WebElement>(BASE_CLASS.retElementList(locCheckedEle));
			Collections.copy(fieldPermissionCheckedElements, BASE_CLASS.retElementList(locCheckedEle));
			
			for (i = 0; i < fieldPermissionCheckedElements.size(); i++) {
				BASE_CLASS.getBuilder().moveToElement(fieldPermissionCheckedElements.get(i)).perform();
				if(!(fieldPermissionCheckedElements.get(i).isSelected())){
					//Fail
					break;
				}
			}
			if(i < fieldPermissionCheckedElements.size()){
				return fieldPermissionCheckedElements.get(i).getAttribute("id");
			}
						
			List<WebElement> fieldPermissionUnCheckedElements = new ArrayList<WebElement>(
					BASE_CLASS.retElementList(locUncheckedEle));
			Collections.copy(fieldPermissionUnCheckedElements, BASE_CLASS.retElementList(locUncheckedEle));
			
			for (i = 0; i < fieldPermissionUnCheckedElements.size(); i++) {
				BASE_CLASS.getBuilder().moveToElement(fieldPermissionUnCheckedElements.get(i)).perform();
				if(fieldPermissionUnCheckedElements.get(i).isSelected()){
					//Fail
					break;
				}
			}
			if(i < fieldPermissionUnCheckedElements.size()){
				return fieldPermissionCheckedElements.get(i).getAttribute("id");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return AccessType;
	}

	
	/*
	 * Create new event 
	 */
	@SuppressWarnings("static-access")
	public static String createNewEvent(ExtentTest testCaseExtent, String className, String eventName, WebDriver driver) {
		
		String uuid = null;
		
		try {

			BASE_CLASS.clickLink("Incident Management");
			BASE_CLASS.Wait(1);

			BASE_CLASS.clickLink("Events");
			BASE_CLASS.Wait(2);

			BasicUtil.textClear(BASE_CLASS.uiElements.getProperty("EventSourceIdFilter"), driver);
			BASE_CLASS.Wait(1);
			
			BASE_CLASS.click(BASE_CLASS.uiElements.getProperty("AddNewBtn"));
			BASE_CLASS.Wait(2);
			ReportGenerator.logStatusInfo(testCaseExtent, "Clicked Add button on events page");
			BASE_CLASS.browserTitleArray = new String[]{"Events","Add Event"};
			ReportGenerator.verifyNavigation(driver, BASE_CLASS.browserTitleArray, testCaseExtent, className);

			BasicUtil.textClear(BASE_CLASS.uiElements.getProperty("AddEventName"), driver);
			BASE_CLASS.Wait(1);
			BASE_CLASS.setValueInTextBox(eventName, BASE_CLASS.uiElements.getProperty("AddEventName"), driver);
			BASE_CLASS.Wait(1);
			ReportGenerator.logStatusInfo(testCaseExtent, "Entered Event name as " + eventName);

			BasicUtil.textClear(BASE_CLASS.uiElements.getProperty("AddEventSourceId"), driver);
			BASE_CLASS.Wait(1);
			BASE_CLASS.setValueInTextBox(eventName, BASE_CLASS.uiElements.getProperty("AddEventSourceId"), driver);
			BASE_CLASS.Wait(1);
			ReportGenerator.logStatusInfo(testCaseExtent, "Entered Event Source Id as " + eventName);
			
			BASE_CLASS.click(BASE_CLASS.uiElements.getProperty("AddEventSaveBtn"));
			BASE_CLASS.Wait(5);
			ReportGenerator.logStatusInfo(testCaseExtent, "Clicked Save button on events | add page");
			BASE_CLASS.browserTitleArray = new String[]{"Events"};
			ReportGenerator.verifyNavigation(driver, BASE_CLASS.browserTitleArray, testCaseExtent, className);

			BASE_CLASS.setValueInTextBox(eventName, BASE_CLASS.uiElements.getProperty("EventSourceIdFilter"),
					driver);
			BASE_CLASS.Wait(2);
			ReportGenerator.logStatusInfo(testCaseExtent,
					"Entered Event Source Id as " + eventName + " in the source ID filter");

			List<WebElement> eventTableElements = new ArrayList<WebElement>(
					BASE_CLASS.retElementList(BASE_CLASS.uiElements.getProperty("EventTable")));
			Collections.copy(eventTableElements, BASE_CLASS.retElementList(BASE_CLASS.uiElements.getProperty("EventTable")));
			boolean eventflag = false;
			for (int i = 0; i < eventTableElements.size(); i = i + 5) {
				if (BasicUtil.compareTxtValue(eventTableElements.get(i), eventName)) {
					eventflag = true;
					eventTableElements.get(i).click();
					BASE_CLASS.Wait(5);
					String url = driver.getCurrentUrl();
					uuid = (url.substring(url.lastIndexOf('/') + 1))
							.substring((url.substring(url.lastIndexOf('/') + 1)).indexOf(""),
										(url.substring(url.lastIndexOf('/') + 1)).indexOf("?"));
					break;
				} else {
					eventflag = false;
				}
			}
			if (eventflag) {
				ReportGenerator.logStatusPass(testCaseExtent, "Event has been added successfully");
			} else {
				File makeErrorFolder = new File((BASE_CLASS.EXECUTION_REPORT_FILE_PATH + className + "_errorScreenshots"));
				makeErrorFolder.mkdir();
				ReportGenerator.logStatusFail(testCaseExtent, className,
						"Addition of Event '" + eventName + "' was unsuccessful", driver);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return uuid;
	}
	
	
	/*
	 * Create new role
	 */
	@SuppressWarnings("static-access")
	public static void createNewRole(ExtentTest testCaseExtent, String className, String roleName, WebDriver driver) {
		
		String[] arrColNames = {"Create","Read","Update","Delete"};
		int row,col, retCheckboxNo;
		String rowName;
		
		try{
			BASE_CLASS.click(BASE_CLASS.uiElements.getProperty("Administration"));
			BASE_CLASS.Wait(2);

			BASE_CLASS.click(BASE_CLASS.uiElements.getProperty("SecurityManagement"));
			BASE_CLASS.Wait(5);
			BASE_CLASS.browserTitleArray = new String[]{"Security","Team Hierarchy"};
			ReportGenerator.verifyNavigation(driver, BASE_CLASS.browserTitleArray, testCaseExtent, className);

			BASE_CLASS.click(BASE_CLASS.uiElements.getProperty("SecurityMgmtRoles"));
			BASE_CLASS.Wait(2);
			BASE_CLASS.browserTitleArray = new String[]{"Security","Roles"};
			ReportGenerator.verifyNavigation(driver, BASE_CLASS.browserTitleArray, testCaseExtent, className);

			//Click on add button and cancel
			BASE_CLASS.click(BASE_CLASS.uiElements.getProperty("AddNewBtn"));
			BASE_CLASS.Wait(1);
			ReportGenerator.logStatusInfo(testCaseExtent, "Clicked on Add new role button");

			// Verify cancel button works or not
			BASE_CLASS.setValueInTextBox(roleName, BASE_CLASS.uiElements.getProperty("NameTxtBox"), driver);
			ReportGenerator.logStatusInfo(testCaseExtent,"Entered the new role name as : '" + roleName + "'");
			BASE_CLASS.Wait(1);
			
			BASE_CLASS.setValueInTextBox("User_Description_" + BASE_CLASS.TIME_STAMP, BASE_CLASS.uiElements.getProperty("DescriptionTxtBox"), driver);
			ReportGenerator.logStatusInfo(testCaseExtent,"Entered the new role name as : 'User_Description_" + BASE_CLASS.TIME_STAMP + "'");
			BASE_CLASS.Wait(1);
			
			BASE_CLASS.mouseScrollDown();
			//Add permissions
			BASE_CLASS.click(BASE_CLASS.uiElements.getProperty("AllRolesAdd"));
			BASE_CLASS.Wait(1);
			ReportGenerator.logStatusInfo(testCaseExtent, "Click on '+' button for providing all permission to user");

			//Verify that all check boxes are checked
			retCheckboxNo = verifyPermission(BASE_CLASS.uiElements.getProperty("UserPermissionChkBoxTable"), true);
			if(retCheckboxNo < BASE_CLASS.retElementList(BASE_CLASS.uiElements.getProperty("UserPermissionChkBoxTable")).size()){
				row = retCheckboxNo/4;
				col = retCheckboxNo%4;
				rowName = driver.findElement(By.xpath("//*[contains(@id,'-"+row+"-uiGrid-')]/div")).getText();
				
				File makeErrorFolder = new File(
						(EXECUTION_REPORT_FILE_PATH + "Roles" + "_errorScreenshots"));
				makeErrorFolder.mkdir();
				ReportGenerator.logStatusFail(testCaseExtent, "Roles", "Failed to check checkbox for row : "+rowName+" and colume: "+arrColNames[col],
						driver);
				throw new Exception("Failed to check checkbox for row : "+rowName+" and colume: "+arrColNames[col]);
			}
			ReportGenerator.logStatusPass(testCaseExtent, "All checkboxes found as checked, which is expected");
			
			//Save the role
			driver.findElement(By.xpath("//body")).sendKeys(Keys.chord(Keys.CONTROL, Keys.HOME));
			BASE_CLASS.Wait(1);
			BASE_CLASS.click(BASE_CLASS.uiElements.getProperty("SaveBtn"));
			BASE_CLASS.Wait(1);
			ReportGenerator.logStatusInfo(testCaseExtent, "Clicked on save role button");
			BASE_CLASS.browserTitleArray = new String[]{"Security","Roles"};
			ReportGenerator.verifyNavigation(driver, BASE_CLASS.browserTitleArray, testCaseExtent, className);
			
			//Verify the role is added
			textClear(BASE_CLASS.uiElements.getProperty("FilterRoleNameTxtBox"), driver);
			BASE_CLASS.Wait(1);
			BASE_CLASS.setValueInTextBox(roleName, BASE_CLASS.uiElements.getProperty("FilterRoleNameTxtBox"), driver);
			BASE_CLASS.Wait(1);
			ReportGenerator.logStatusInfo(testCaseExtent, "Entered filter text as " + roleName);
			
            List<WebElement> roleTableElements = new ArrayList<WebElement>(
            		BASE_CLASS.retElementList(BASE_CLASS.uiElements.getProperty("RoleListTable")));
			Collections.copy(roleTableElements, BASE_CLASS.retElementList(BASE_CLASS.uiElements.getProperty("RoleListTable")));

			if (roleTableElements.get(1).getText().contentEquals(roleName)
					|| roleTableElements.get(2).getText().contentEquals(roleName)) {
				ReportGenerator.logStatusPass(testCaseExtent, "Successfully added role : "+roleName);
			} else {
				File makeErrorFolder = new File(
						(EXECUTION_REPORT_FILE_PATH + className + "_errorScreenshots"));
				makeErrorFolder.mkdir();
				ReportGenerator.logStatusFail(testCaseExtent, className,
						"Failed to add role : "+roleName, driver);
				throw new Exception("Failed to add role - "+roleName);
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/*
	 * select Role From Edit User Page
	 */
	@SuppressWarnings("static-access")
	public static boolean selectRoleFromEditUserPage(ExtentTest testCaseExtent, String roleName){
		Boolean flag = false;
		try{
			
			List<WebElement> rolesTableElements = new ArrayList<WebElement>(
					BASE_CLASS.retElementList(BASE_CLASS.uiElements.getProperty("UserRoleTable")));
			Collections.copy(rolesTableElements, BASE_CLASS.retElementList(BASE_CLASS.uiElements.getProperty("UserRoleTable")));
			
			BASE_CLASS.mouseScrollUp();
			BASE_CLASS.Wait(1);
			
			JavascriptExecutor js = (JavascriptExecutor) BASE_CLASS.getDriver();
			for (int i = 1; i < rolesTableElements.size(); i = i + 2) {
				BASE_CLASS.getBuilder().moveToElement(rolesTableElements.get(i)).perform();
				js.executeScript("arguments[0].scrollIntoView(true);", rolesTableElements.get(i));
				BASE_CLASS.Wait(1);
				
				if (BasicUtil.compareTxtValue(rolesTableElements.get(i), roleName)) {
					BASE_CLASS.mouseScrollUp();
					BASE_CLASS.mouseScrollUp();
	
					rolesTableElements.get(i).click();
					BASE_CLASS.Wait(1);
					
					flag = true;
					ReportGenerator.logStatusInfo(testCaseExtent, "Selected the User Role as '" + roleName + "'");
					break;
				}
			}
	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;

	}
	
	/*
	 * select Role From Edit User Page
	 */
	@SuppressWarnings("static-access")
	public static void createNewTaskFromQuickAdd(ExtentTest testCaseExtent, String className, String taskName, String assignedTo, WebDriver driver){
		try{
			BASE_CLASS.click(BASE_CLASS.uiElements.getProperty("QADropdown"));
			BASE_CLASS.Wait(2);
			
			//Click on task link 
			BASE_CLASS.click(BASE_CLASS.uiElements.getProperty("QATaskLink"));
			BASE_CLASS.Wait(2);
			
			//Enter task name 
			textClear(BASE_CLASS.uiElements.getProperty("QANameField"), driver);
			BASE_CLASS.Wait(1);
			BASE_CLASS.setValueInTextBox(taskName, BASE_CLASS.uiElements.getProperty("QANameField"), driver);
			BASE_CLASS.Wait(1);
			ReportGenerator.logStatusInfo(testCaseExtent, "Entered filter text as " + taskName);
			
			// Click on more options link
			BASE_CLASS.click(BASE_CLASS.uiElements.getProperty("QAMoreOptionsLink"));
			ReportGenerator.logStatusInfo(testCaseExtent, "Clicked on more options link on quick add wizard");
			BASE_CLASS.Wait(5);

			// Enter values in optional fields
			BASE_CLASS.click(BASE_CLASS.uiElements.getProperty("QAAssignedPersonField"));
			BASE_CLASS.Wait(1);
			sendTextPlusKey(assignedTo, Keys.ENTER, BASE_CLASS.getBuilder());
			BASE_CLASS.Wait(2);
			BASE_CLASS.retElementList(BASE_CLASS.uiElements.getProperty("LookupSearchLinkArray")).get(0).click();
//			pressBrowserKey(Keys.ARROW_DOWN, BASE_CLASS.getBuilder());
//			BASE_CLASS.Wait(1);
//			pressBrowserKey(Keys.ENTER, BASE_CLASS.getBuilder());
			BASE_CLASS.Wait(1);
//			BASE_CLASS.setValueInTextBox(assignedTo, BASE_CLASS.uiElements.getProperty("QAAssignedPersonField"), driver);
//			BASE_CLASS.Wait(5);
//			driver.findElement(By.xpath(BASE_CLASS.uiElements.getProperty("QAAssignedToName"))).click();
//			BASE_CLASS.Wait(1);
			ReportGenerator.logStatusInfo(testCaseExtent, "Enter value in name field : "+assignedTo);

			// Click on Add and Open button
			BASE_CLASS.click(BASE_CLASS.uiElements.getProperty("QAAddnOpenBtn"));
			ReportGenerator.logStatusInfo(testCaseExtent, "Clicked on Add n Open button on quick add wizard");
			BASE_CLASS.Wait(5);
			
			// Verify newly added module
			BASE_CLASS.browserTitleArray = new String[]{"Task",taskName};
			ReportGenerator.verifyNavigation(driver, BASE_CLASS.browserTitleArray, testCaseExtent, className);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * It will read the mail from the cybersponseautomation.hotmail.com
	 */ 
	@SuppressWarnings("static-access")
	public static String readMailFromGivenFolder(String host, String user, String password, String folderName, String emailSubject) {
		String messageContent = null;
		try {

			// create properties field
			Properties properties = new Properties();
			MailSSLSocketFactory socketFactory = new MailSSLSocketFactory();
			socketFactory.setTrustAllHosts(true);
			
			/* HotMail IMAP
			properties.put("mail.imap.ssl.checkserveridentity", "false");
			properties.setProperty("mail.imap.ssl.trust", "*");
			properties.put("mail.imap.host", host);
			properties.put("mail.imap.port", "993");
			properties.put("mail.imap.starttls.enable", "true");
			properties.put("mail.imap.ssl.socketFactory", socketFactory);
			Session emailSession = Session.getDefaultInstance(properties);

			// create the POP3 store object and connect with the pop server
			Store store = emailSession.getStore("imaps");*/
			
			//Gmail SMTP
			properties.put("mail.smtp.ssl.checkserveridentity", "false");
			properties.setProperty("mail.smtp.ssl.trust", "*");
			properties.put("mail.smtp.host", host);
			properties.put("mail.smtp.port", "465");
			properties.put("mail.smtp.starttls.enable", "true");
			properties.put("mail.smtp.ssl.socketFactory", socketFactory);
			Session emailSession = Session.getDefaultInstance(properties);

			// create the POP3 store object and connect with the pop server
			Store store = emailSession.getStore("imaps");

			// Connecting to the mail server
			store.connect(host, user, password);

			// create the folder object and open it
			Folder emailFolder = store.getFolder(folderName.toUpperCase());
			emailFolder.open(Folder.READ_WRITE);
			
			Message[] messages;
			int count = 0;

			// retrieve the messages from the folder in an array and print it
			do{
				messages = emailFolder.getMessages();
				for(int i=messages.length-1; i>=0; i--){
					if(messages[i].getSubject().equalsIgnoreCase(emailSubject)){
						messageContent = messages[i].getContent().toString();
			            // Delete the mail
						messages[i].setFlag(Flags.Flag.DELETED, true);
						break;
					}
				}
				BASE_CLASS.Wait(5);
				count++;
			}while(messageContent == null && count<=20);
			

			// close the store and folder objects
			emailFolder.expunge();
			// close the store and folder objects
			emailFolder.close(true);
			store.close();

		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return messageContent;
	}

	//To verify that given value is present in the dropdown list
	public static Boolean isDropdownValuePresent(String location,String compareValue,WebDriver driver){
		
		Boolean flag = false;
		
		WebElement e = driver.findElement(By.xpath(location));
		Select picklistdropdown = new Select(e);
		List<WebElement> allOptions = picklistdropdown.getOptions();
		
		for(int i=0; i<allOptions.size(); i++) {
		    if(allOptions.get(i).getText().contains(compareValue)) {
		        flag=true;
		        break;
		    }
		}
		return flag;
	}
	
	//To compare two date having format "MM/dd/yyyy hh:mm a
	public static Boolean compareDateTime(String beforeDate,String afterDate) {
		Boolean flag= false;
		
		SimpleDateFormat date = new SimpleDateFormat("MM/dd/yyyyhh:mm a");
		try {
			if (date.parse(beforeDate).before(date.parse(afterDate))
					|| date.parse(beforeDate).equals(date.parse(afterDate))) {
				flag=true;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
	
		return flag;
	}
	
}