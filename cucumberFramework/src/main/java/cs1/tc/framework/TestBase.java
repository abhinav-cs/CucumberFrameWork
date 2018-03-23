package cs1.tc.framework;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class TestBase {
	public static Properties environmentVariables = null;
	public static Logger log = null;
	public static final String TIME_STAMP = new SimpleDateFormat("yyyy.MM.dd.HH.mm").format(new Date());
	public static final String EXECUTION_REPORT_FILE_PATH = System.getProperty("user.dir") + "//ExecutionReports//";
	public static final ExtentReports REPORTS = new ExtentReports(
			EXECUTION_REPORT_FILE_PATH + "CyOps AutomationReport.html",true);
	
	
	/*
	 * Initialized the parameter/variables which are going to be used during the
	 * execution
	 */
	static {
		try {

			
			
			Thread.currentThread().getContextClassLoader();
//			PropertyConfigurator.configure("log4j.properties");

			File EnvironmentVariablestemp = new File(System.getProperty("user.dir"), "//ResourceFiles//EnvironmentVariables.properties");
			environmentVariables = new Properties();
			InputStream EnvironmentVariablesReader = new FileInputStream(EnvironmentVariablestemp);
			environmentVariables.load(EnvironmentVariablesReader);

			//get the ip of the system
//			InetAddress ip;
//			ip=InetAddress.getLocalHost();
//			
//			
//			REPORTS.addSystemInfo("Source IP (Jenkins Slave Node)",ip.getHostAddress());
//			REPORTS.addSystemInfo("CyOps Target URL", environmentVariables.getProperty("URL"));
//			REPORTS.addSystemInfo("CyOps Build Number", environmentVariables.getProperty("BuildNumber"));
//			REPORTS.loadConfig(new File(System.getProperty("user.dir")+"\\ExtentReportConfig.xml"));
			
			
//			PropertyConfigurator.configure("log4j.properties");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	/*
	 * It adds delay in the execution
	 */
	public static void Wait(int timeSeconds) throws InterruptedException {
		Thread.sleep((timeSeconds * 1000));
	}
	
	public static void waitTime(char timeSpan) throws InterruptedException {
		
		switch (timeSpan) {
		case 's':
			Wait(1);
			break;
		case 'm':
			Wait(2);
			break;
		case 'l':
			Wait(5);
			break;	
		default:
			Wait(3);
			break;
		}
		
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ExtentTest testCase(Class className, String methodName, String tcName) {

		String tcNumber = null;
		String tpLink = null;
		Method method = null;
		try {
			method = className.getMethod(methodName);
		} catch (NoSuchMethodException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			TargetProcess tpAnno = method.getAnnotation(TargetProcess.class);
			tcNumber = tpAnno.tcNumber();
			tpLink = tpAnno.tpLink();
		} catch (SecurityException e1) {
			e1.printStackTrace();
		}
		
		ExtentTest testcase = REPORTS.startTest(tcName,
				"<a target='_blank' href='" + tpLink + tcNumber + "'>TC : " + tcNumber + "</a>");
		return testcase;
	}
	

}
