package com.appium.pages;

import com.appium.base.TestBase;
import com.appium.util.ReusableFunctions;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;

import java.util.ArrayList;

public class SelendroidHome extends TestBase {

    private static Logger logger = Logger.getLogger(MainPage.class.getName());

    private static final By endActivity = By.xpath("//android.widget.Button[@content-desc='buttonTestCD']");
    private static final By chromeBtn = By.xpath("//android.widget.ImageButton[@content-desc='buttonStartWebviewCD']");
    private static final By registerPage = By.xpath("//android.widget.ImageButton[@content-desc='startUserRegistrationCD']");
    private static final By textField = By.xpath("//android.widget.EditText[@content-desc='my_text_fieldCD']");
    private static final By progressField = By.xpath("//android.widget.Button[@content-desc='waitingButtonTestCD']");
    private static final By textView = By.xpath("//android.widget.Button[@content-desc='visibleButtonTestCD']");
    private static final By toastBtn = By.xpath("//android.widget.Button[@content-desc='showToastButtonCD']");
    private static final By popUpDisplay = By.xpath("//android.widget.Button[@content-desc='showPopupWindowButtonCD']");
    private static final By errorBtn = By.xpath("//android.widget.Button[@content-desc='exceptionTestButtonCD']");
    private static final By typeforerror = By.xpath("//android.widget.EditText[@resource-id='io.selendroid.testapp:id/exceptionTestField']");
    private static final By displayText = By.xpath("//android.widget.Button[@resource-id='io.selendroid.testapp:id/topLevelElementTest']");
    private static final By addsBtn = By.xpath("//android.widget.CheckBox[@resource-id='io.selendroid.testapp:id/input_adds_check_box']");
    private static final By displayedText = By.xpath("//android.widget.TextView[@resource-id='io.selendroid.testapp:id/focusedText']");

    private static final By toastDisplay = By.xpath("//android.widget.Toast[1]");

    public static boolean verifyElements(){
        boolean flag = true;
        try{
            logger.info("Verifying if all elements are present in home.");

            ArrayList<By> elementsPresent = new ArrayList<By>();

            elementsPresent.add(endActivity);
            elementsPresent.add(chromeBtn);
            elementsPresent.add(registerPage);
            elementsPresent.add(textField);
            elementsPresent.add(progressField);
            elementsPresent.add(textView);
            elementsPresent.add(toastBtn);
            elementsPresent.add(popUpDisplay);
            elementsPresent.add(errorBtn);
            elementsPresent.add(typeforerror);
            elementsPresent.add(displayText);
            elementsPresent.add(addsBtn);

            if (ReusableFunctions.verifyElementsLocated(elementsPresent)){
                logger.info("All Main Page Elements Displayed.");
            }else {
                flag = false;
                logger.error("Main Page Elements Not Displayed.");
            }
        }
        catch (Exception e){
            flag = false;
            logger.error("Exception Occurred While Verifying Elements present in Home Page: "+e.getMessage());
        }
        return flag;
    }

    public static boolean verifyToast(){
        boolean flag = true;
        String text = null;
        try{
            logger.info("Locating the toast button");
            ReusableFunctions.waitForElementPresent(toastBtn);
            ReusableFunctions.mouseClick(toastBtn);
            logger.info("Clicked on the toast button");
            ReusableFunctions.waitForElementPresent(toastDisplay);
            if(ReusableFunctions.waitForElementPresent(toastDisplay)){
                logger.info("Trying to get toast message");
                text = ReusableFunctions.toastMessage(1);
                logger.info("text from toast is"+text);
                ReusableFunctions.verifyTextMatch(text, "Hello selendroid toast!");
            }
            else{
                logger.error("No element for toast");
                flag = false;
            }
        }
        catch(Exception e){
            logger.error("Failed to verify toast : "+ e);
        }
        return flag;
    }

    public static boolean clickRegister(){
        boolean flag = true;
        try{
            logger.info("Locating the register button.");
            ReusableFunctions.waitForElementPresent(registerPage);
            if(ReusableFunctions.waitForElementPresent(registerPage)) {
                ReusableFunctions.mouseClick(registerPage);
                logger.info("Clicked on register button");
            }
            else{
                logger.error("Could not click element");
                flag = false;
            }
        }
        catch(Exception e){
            logger.error("register button failed to locate"+e);
            flag = false;
        }
        return flag;
    }
}
