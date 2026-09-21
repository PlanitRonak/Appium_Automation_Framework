package com.appium.pages;

import com.appium.base.TestBase;
import com.appium.util.ReusableFunctions;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;

import java.util.ArrayList;

public class WhereIsMyTrainHomePage extends TestBase {
    private static Logger logger = Logger.getLogger(MainPage.class.getName());

    private static final By homeText = By.xpath("//android.widget.TextView[@text=\"Where is My Train\"]");
    private static final By findBtn = By.xpath("//android.widget.ScrollView/android.view.View[1]/android.view.View[7]/android.widget.Button");

    public static boolean verifyHomeScreenElements() {
        boolean flag = true;
        try {
            logger.info("Verifying if all elements are present in home.");

            if (driver.findElement(homeText).isDisplayed()) {
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

    public static void checking_the_xpaths () throws InterruptedException {
        driver.findElement(findBtn).click();
        Thread.sleep(4000);
    }
}