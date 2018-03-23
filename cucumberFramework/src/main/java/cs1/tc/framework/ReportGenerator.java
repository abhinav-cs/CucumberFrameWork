package cs1.tc.framework;

import static cs1.tc.framework.UITestBase.*;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class ReportGenerator {

	public ReportGenerator() {
	}

	public static ExtentTest initializeParentTest(String testName, String description) {
		return REPORTS.startTest(testName, description);
	}

	/*
	 * It adds a step to HTML report as a passed step
	 */
	public static void logStatusPass(ExtentTest testExtent, String message) {
		testExtent.log(LogStatus.PASS, message);
	}

	/*
	 * It adds a step to HTML report and gives the Info regarding the step
	 */
	public static void logStatusInfo(ExtentTest testExtent, String message) {
		testExtent.log(LogStatus.INFO, message);
	}

	/*
	 * It adds a step to HTML report as a failed step and also takes a screen shot of the webpage
	 */
	public static void logStatusFail(ExtentTest testExtent, String usName, String message, WebDriver driver) {
		if(driver!=null){
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			File destFile = new File(EXECUTION_REPORT_FILE_PATH + usName + "_errorScreenshots" + "\\"+message.replace(" ", "").replace("'", "").replace("#", "").replace("(", "").replace(")", "").replace("\"", "").replace(".", "").replace(",", "") + ".jpg");
			
			try {
				FileUtils.copyFile(scrFile, destFile);
			} catch (IOException e) {
				System.out.println("Not able to take an error screenshot for: " + e.getStackTrace());
			}
			try {
				testExtent.log(LogStatus.FAIL, message + testExtent.addScreenCapture(usName + "_errorScreenshots" + "\\"+message.replace(" ", "").replace("'", "").replace("#", "").replace("(", "").replace(")", "").replace("\"", "").replace(".", "").replace(",", "") + ".jpg"));
			}catch(Exception e){
				e.printStackTrace();
			}finally {
				System.out.println("Screenshot added for the failure of the Test Step");
			}	
		}else if(driver==null){
			testExtent.log(LogStatus.FAIL, message);
		}
	}

	/*
	 * It adds a step to HTML report as a skipped step and also takes a screen shot of the web page
	 */
	public static void logStatusSkip(ExtentTest testExtent, String usName, String message, WebDriver driver) {
		if(driver!=null){
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			File destFile = new File( EXECUTION_REPORT_FILE_PATH + usName + "_errorScreenshots" + "\\"+message.replace(" ", "").replace("'", "").replace("#", "") + ".jpg");
			
			try {
				FileUtils.copyFile(scrFile, destFile);
			} catch (IOException e) {
				System.out.println("Not able to take an error screenshot for: " + e.getStackTrace());
			}
			try {
				testExtent.log(LogStatus.SKIP, message + testExtent.addScreenCapture(usName + "_errorScreenshots" + "\\"+message.replace(" ", "").replace("'", "").replace("#", "") + ".jpg"));
			}catch(Exception e){
				e.printStackTrace();
			}finally {
				System.out.println("Screenshot added for skipped test case");
			}	
		}else if(driver==null){
			testExtent.log(LogStatus.SKIP, message);
		}
	}

	/*
	 * Closes the report for the particular test case
	 */
	public static void flushReportToDisk(ExtentTest parentTest) {
		 REPORTS.endTest(parentTest);
		 REPORTS.flush();
	}

	/*
	 * Verify the navigation of the page is succesfull through browser title
	 */
	public static void verifyNavigation(WebDriver driver, String[] browserTitleArr, ExtentTest testExtent,
			String usName) throws InterruptedException {
		 Wait(5);
		String browserTitle =  constructBrowserTitle(browserTitleArr);
		if ((driver.getTitle().contains(browserTitle))) {
			logStatusPass(testExtent, "Navigated successfully to " + browserTitle);
		} else {
			File makeErrorFolder = new File(
					( EXECUTION_REPORT_FILE_PATH + usName + "_errorScreenshots"));
			makeErrorFolder.mkdir();
			logStatusFail(testExtent, usName, "Navigation failed to the " + browserTitle.replace("|", ""), driver);
//			 appendLogError(usName, "Navigation failed to the " + browserTitle);
			throw new InterruptedException("Navigation failed to the " + browserTitle);
		}
	}

	/*
	 * It will verify that an object is enabled
	 */
	public static void isEnabled(String location, String usName, String fieldName, ExtentTest testExtent,
			WebDriver driver) {
		try {
			Assert.assertTrue(driver.findElement(By.xpath(location)).isEnabled());
			logStatusPass(testExtent, "The field" + fieldName + " is enabled");
			 appendLogInfo(usName, "The field " + fieldName + " is enabled");
		} catch (AssertionError e) {
			File makeErrorFolder = new File(
					( EXECUTION_REPORT_FILE_PATH + usName + "_errorScreenshots"));
			makeErrorFolder.mkdir();
			logStatusFail(testExtent, usName, "The field " + fieldName + " is disabled", driver);
			 appendLogError(usName, "The field " + fieldName + " is disabled");
		}
	}

	/*
	 * It will verify that an object is disabled
	 */
	public static void isDisabled(String location, String usName, String fieldName, ExtentTest testExtent,
			WebDriver driver) {
		try {
			Assert.assertTrue(!driver.findElement(By.xpath(location)).isEnabled());
			logStatusPass(testExtent, "The field " + fieldName + " is disabled");
//			 appendLogInfo(usName, "The field " + fieldName + " is disabled");
		} catch (AssertionError e) {
			File makeErrorFolder = new File(
					( EXECUTION_REPORT_FILE_PATH + usName + "_errorScreenshots"));
			makeErrorFolder.mkdir();
			logStatusFail(testExtent, usName, "The field " + fieldName + " is enabled", driver);
//			 appendLogError(usName, "The field " + fieldName + " is enabled");
		}
	}

	/*
	 * Takes screenshot of the application 
	 */
	public static String screenShot(ExtentTest testExtent, String usName, String imagename, WebDriver driver) {
		File makeErrorFolder = new File( EXECUTION_REPORT_FILE_PATH + usName + "\\");
		makeErrorFolder.mkdir();
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		File destFile = new File( EXECUTION_REPORT_FILE_PATH+usName + "\\" + imagename + ".jpg");
		try {
			FileUtils.copyFile(scrFile, destFile);
		} catch (IOException e) {
			System.out.println("Not able to take an error screenshot for: " + e.getStackTrace());
		}
		return destFile.getAbsoluteFile().toString();
	}

}