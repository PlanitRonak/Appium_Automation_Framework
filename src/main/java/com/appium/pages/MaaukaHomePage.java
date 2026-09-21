package com.appium.pages;

import com.appium.base.TestBase;
import com.appium.util.ReusableFunctions;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MaaukaHomePage extends TestBase {
    private static Logger logger = Logger.getLogger(MainPage.class.getName());

//    Locators
    private static final By logo = By.xpath("//android.widget.Button[@text=\"Dream Cruise Logo\"]");
    private static final By signin = By.xpath("//android.widget.Button[@text=\"Sign In\"]");
    private static final By emailInput = By.xpath("//android.view.ViewGroup/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View[2]/android.view.View/android.view.View/android.view.View[3]/android.view.View[1]/android.view.View/android.view.View");
    private static final By passInput = By.xpath("//android.view.ViewGroup/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View[2]/android.view.View/android.view.View/android.view.View[3]/android.view.View[2]/android.view.View[2]/android.view.View");
    private static final By signInBtn = By.xpath("//android.widget.Button[@text=\"Sign In\"]");

//    Actions
    public static boolean verifyHomeScreenElements() {
        boolean flag = true;
        try {
            logger.info("Verifying if all elements are present in home.");
            if(ReusableFunctions.waitForElementVisible(logo)){
                flag = ReusableFunctions.verifyElementsLocated(new ArrayList<>(Arrays.asList(logo)));
                logger.info("All Main Page Elements Displayed.");
            } else {
                flag = false;
                logger.error("Main Page Elements Not Displayed.");
            }
        } catch (Exception e) {
            flag = false;
            logger.error("Exception Occurred While Verifying Elements present in Home Page: " + e.getMessage());
        }
        return flag;
    }

    public static boolean verifySignInBtnDisabled() {
        boolean flag = true;
        try {
            logger.info("Verify Sign in Button is Disabled or Not");
            if(ReusableFunctions.waitForElementVisible(signin)) {
                driver.findElement(signin).click();
                Thread.sleep(5000);
                flag = !driver.findElement(signInBtn).isEnabled();
            } else {
                flag = false;
                logger.error("Sign In Button is Not Disabled.");
            }
        } catch (Exception e) {
            flag = false;
            logger.error("Exception Occurred While Verifying Elements present in Home Page: " + e.getMessage());
        }
        return flag;
    }
}
