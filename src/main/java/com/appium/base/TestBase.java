package com.appium.base;

//import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.io.*;
import java.lang.invoke.MethodHandles;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

public class TestBase {

    public static Properties prop;
    public static AndroidDriver driver;
    private static Logger logger;

    static{
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_hhmmss");
        System.setProperty("current.date", dateFormat.format(new Date()));
    }

    public TestBase() {
        try {
            prop = new Properties();
            FileInputStream inputStream = new FileInputStream(System.getProperty("user.dir") + "/src/main/java/com/appium/config/config.properties");
            prop.load(inputStream);

            PropertyConfigurator.configure(System.getProperty("user.dir") + "/src/main/resource/log4j.properties");
            logger = Logger.getLogger(MethodHandles.lookup().lookupClass());

        } catch (FileNotFoundException Ex) {
            logger.info("File not found: " + Ex.getMessage());
        } catch (IOException Ex) {
            logger.info("Exception occurred: " + Ex.getMessage());
        }
    }

    /**
     * This is singleton driver initialization method
     * @throws MalformedURLException - In case of invalid appium server url
     */
    public static void driverInitialization() throws MalformedURLException{
        if (driver == null) {
            switch (prop.getProperty("Platform")){
                case "android":
                    logger.info("Running Tests On Android Platform.");
                    androidSetup();
                    break;
                case "browser_stack":
                    logger.info("Running Tests On Browser Stack.");
                    browserStack();
                    break;
                case "ios":
                    logger.info("Running Tests On IOS Platform.");
                    iosSetup();
                    break;
                default:
                    logger.info("Running Tests On Browser Stack.");
                    browserStack();
            }
        }
    }

    /**
     * This method is used for android driver setup
     * @throws MalformedURLException - In case of invalid appium server url
     */
    private static void androidSetup() throws MalformedURLException {
       // DesiredCapabilities caps = new DesiredCapabilities();
        UiAutomator2Options options = new UiAutomator2Options();
        options.setCapability("automationName", prop.getProperty("AutomationName"));
        options.setCapability("deviceName", prop.getProperty("DeviceName"));
        options.setCapability("udid", prop.getProperty("UDID"));
        options.setCapability("platformName", prop.getProperty("Platform"));
        options.setCapability("platformVersion", prop.getProperty("PlatformVersion"));
        options.setCapability("appPackage", prop.getProperty("AppPackage"));
        options.setCapability("appActivity", prop.getProperty("AppActivity"));
//        This Option automatically installs the app on the Real device if the device doesn't contains the app
        options.setCapability("app", System.getProperty("user.dir") + "/Apps/" + prop.getProperty("AppName"));
        options.setCapability("noReset", prop.getProperty("NoReset"));
        options.setCapability("autoGrantPermissions", prop.getProperty("AutoGrantPermissions"));
        options.setCapability("ignoreHiddenApiPolicyError", true);
        driver = new AndroidDriver(new URL(prop.getProperty("AppiumServer")), options);
        logger.info("Starting Android Driver.");
    }

    /**
     * This method is used for browser stack driverInitialization
     * @throws MalformedURLException - In case of invalid browser stack server url
     */
    private static void browserStack() throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        String bs_url = "https://"+prop.getProperty("bsUserName")+":"+prop.getProperty("bsAccessKey")+"@hub-cloud.browserstack.com/wd/hub";
        caps.setCapability("device", prop.getProperty("CloudDeviceName"));
        caps.setCapability("os_version", prop.getProperty("CloudPlatformVersion"));
        caps.setCapability("app", prop.getProperty("bsAppHash"));
        driver = new AndroidDriver(new URL(bs_url), caps);
        logger.info("Starting Android Driver on BrowserStack.\nBrowser Stack Server Details:\n"+bs_url);
    }

    /**
     * This method is used for ios driver setup
     * @throws MalformedURLException - In case of invalid appium server url
     */
    private static void iosSetup() throws MalformedURLException{
        DesiredCapabilities caps = new DesiredCapabilities();
        // To be implemented
        //driver = new IOSDriver(new URL("http://127.0.0.1:4723/wd/hub"), caps);
        //logger.info("Starting IOS Driver.");
    }

    public static void resetApp(AndroidDriver driver) {
        String appPackage = prop.getProperty("AppPackage");
        if (driver.isAppInstalled(appPackage)) {
            driver.terminateApp(appPackage);
        }

        try {
            Map<String, Object> args = new HashMap<>();
            args.put("command", "pm");
            args.put("args", Arrays.asList("clear", appPackage));
            driver.executeScript("mobile: shell", args);
        } catch (Exception e) {
            System.out.println("Failed to clear app data: " + e.getMessage());
        }
        driver.activateApp(appPackage);
    }
}