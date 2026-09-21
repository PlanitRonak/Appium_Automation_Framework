package com.appium.testcases;

import com.appium.base.TestBase;
import com.appium.listeners.ExtentManager;
import com.appium.pages.MainPage;
import com.appium.pages.SelendroidHome;
import com.appium.pages.SelendroidRegister;
import com.appium.testdata.DataReader;
import com.appium.util.ReusableFunctions;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;

public class SelendroidTest extends TestBase {

    private static SoftAssert _softAssert;

    private static String testCaseName = null;
    private static ExtentTest extentLogger = null;
    private static ExtentReports extent = null;
    private static Logger logger = null;

    private static HashMap<String, HashMap<String, String>> tcData = DataReader.
            testDataMappedToTestName(prop.getProperty("TestDataExcelFileName"), prop.getProperty("TestDataSheetName"));

//    private static XSSFRow

    @BeforeMethod
    public static void setUp(Method method) throws IOException {
        driverInitialization();
        resetApp(driver);
        _softAssert = new SoftAssert();
        testCaseName = method.getName();
        extent = ExtentManager.getReporter();
        extentLogger = ExtentManager.getLogger(testCaseName);
        logger = Logger.getLogger(MainPageTests.class.getName());
    }

    @Test(priority = 1)
    public void verifyMainScreenElements(){
        logger.info("Running Step: verify_all_main_page_elements_are_present()");
        if (!SelendroidHome.verifyElements()) {
            logger.error("Element not present on the page.");
            _softAssert.fail("Elements not present on the page");
        }
        _softAssert.assertAll();
    }

    @Test(priority = 2)
    public void verifyToastMessage(){
        logger.info("Running Step: verifyToastMessage()");

        if (!SelendroidHome.verifyToast()) {
            logger.error("Toast verification failed");
            _softAssert.fail("Toast verification failed");
        }
        _softAssert.assertAll();
    }

    @Test(priority = 3)
    public void beginRegistration(){
        logger.info("Running step: beginRegistration()");

        if(!SelendroidHome.clickRegister()){
            logger.error("Failed to click register");
            _softAssert.fail("Failed to click register");
        }
        _softAssert.assertAll();
    }

    @Test(priority = 4)
    public void getRegistered(){
        logger.info("Running step: getRegistered()");
        SelendroidHome.clickRegister();
        String ussr = tcData.get(testCaseName).get("Username");
        String em = tcData.get(testCaseName).get("Email");
        String pass = tcData.get(testCaseName).get("Password");
        String name = tcData.get(testCaseName).get("Name");
        String lang = tcData.get(testCaseName).get("Language");
        Boolean ads = Boolean.valueOf(tcData.get(testCaseName).get("AcceptAds"));
        String adsStr = tcData.get(testCaseName).get("AcceptAds").toLowerCase();

        if(!SelendroidRegister.register(ussr,em,pass,name,lang,ads)){
            logger.error("Failed to register user");
            _softAssert.fail("Failed to register user");
        }
        else if(!SelendroidRegister.verifyReg(ussr,em,pass,name,lang,adsStr)){
            logger.error("Verification of values failed");
            _softAssert.fail("Verification of values failed");
        }
        _softAssert.assertAll();
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

        if (driver != null) {
            resetApp(driver);
        }
    }
    @AfterTest
    public void tearDown(){
        extent.flush();
    }
}
