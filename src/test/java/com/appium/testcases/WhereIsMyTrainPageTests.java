package com.appium.testcases;

import com.appium.base.TestBase;
import com.appium.listeners.ExtentManager;
import com.appium.pages.SelendroidHome;
import com.appium.pages.WhereIsMyTrainHomePage;
import com.appium.testdata.DataReader;
import com.appium.util.ReusableFunctions;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import org.apache.log4j.Logger;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;

public class WhereIsMyTrainPageTests extends TestBase {
    private static SoftAssert _softAssert;

    private static String testCaseName = null;
    private static ExtentTest extentLogger = null;
    private static ExtentReports extent = null;
    private static Logger logger = null;

//    private static HashMap<String, HashMap<String, String>> tcData = DataReader.testDataMappedToTestName(prop.getProperty("TestDataExcelFileName"), prop.getProperty("TestDataSheetName"));

//    private static XSSFRow

    @BeforeMethod
    public void setUp(Method method) throws IOException {
        if (driver == null) {
            driverInitialization();
        }
        resetApp(driver);
        _softAssert = new SoftAssert();
        testCaseName = method.getName();
        extent = ExtentManager.getReporter();
        extentLogger = ExtentManager.getLogger(testCaseName);
        logger = Logger.getLogger(WhereIsMyTrainPageTests.class.getName());
    }

    @Test(priority = 1)
    public void verifyHomeScreenElements() throws InterruptedException {
        logger.info("Running Step: verify_all_home_page_elements_are_present()");
        if (!WhereIsMyTrainHomePage.verifyHomeScreenElements()) {
            logger.error("Element not present on the page.");
            _softAssert.fail("Elements not present on the page");
        }
        _softAssert.assertAll();
        Thread.sleep(4000);
    }

    @Test(priority = 2)
    public void checkingFramework() throws InterruptedException {
        logger.info("Running Step: Checking the Framework()");
        WhereIsMyTrainHomePage.checking_the_xpaths();
    }

    @AfterMethod
    public void getResult(ITestResult result) throws Exception{

        if(result.getStatus() == ITestResult.FAILURE){
            String screenShotPath = ReusableFunctions.takeScreenShot("DemoApp");
            extentLogger.log(Status.FAIL, MarkupHelper.createLabel(result.getName()+" Test case FAILED due to below issues:", ExtentColor.RED));
            extentLogger.fail(result.getThrowable());
            extentLogger.fail("Snapshot below: " + extentLogger.addScreenCaptureFromPath(screenShotPath, testCaseName));

        }else if(result.getStatus() == ITestResult.SKIP){
            extentLogger.log(Status.SKIP, MarkupHelper.createLabel(result.getName()+" Test case SKIPPED due to below issues:", ExtentColor.GREY));
            extentLogger.skip(result.getThrowable());

        }else if(result.getStatus() == ITestResult.SUCCESS){
            extentLogger.log(Status.PASS, MarkupHelper.createLabel(result.getName()+" Test case PASSED.", ExtentColor.GREEN));
        }

//        if (driver != null) {
//            resetApp(driver);
//        }
    }
    @AfterTest
    public void tearDown(){
        extent.flush();
    }
}



