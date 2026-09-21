package com.appium.pages;

import com.appium.base.TestBase;
import com.appium.util.ReusableFunctions;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;

public class SelendroidRegister extends TestBase {
    private static Logger logger = Logger.getLogger(MainPage.class.getName());

    private static final By welcomeMsg = By.xpath("//android.widget.TextView[@text='Welcome to register a new User']");
    private static final By usrname = By.xpath("//android.widget.EditText[@resource-id='io.selendroid.testapp:id/inputUsername']");
    private static final By email = By.xpath("//android.widget.EditText[@content-desc='email of the customer']");
    private static final By password = By.xpath("//android.widget.EditText[@resource-id='io.selendroid.testapp:id/inputPassword']");
    private static final By xname = By.xpath("//android.widget.EditText[@resource-id='io.selendroid.testapp:id/inputName']");
    private static final By langDropdown = By.xpath("//android.widget.Spinner[@resource-id='io.selendroid.testapp:id/input_preferedProgrammingLanguage']");
    private static final By acceptAds = By.xpath("//android.widget.CheckBox[@resource-id='io.selendroid.testapp:id/input_adds']");
    private static final By regBtn = By.xpath("//android.widget.Button[@resource-id='io.selendroid.testapp:id/btnRegisterUser']");

    public static boolean register(String ussr,String em,String pass,String name,String lang,Boolean ads){
        boolean flag = true;
        try{
            logger.info("Verifying register page....");
            ReusableFunctions.waitForElementPresent(welcomeMsg);
            if(ReusableFunctions.waitForElementPresent(welcomeMsg)){
                ReusableFunctions.enterText(usrname,ussr);
                ReusableFunctions.enterText(email,em);
                ReusableFunctions.enterText(password,pass);
                ReusableFunctions.clearText(xname);
                ReusableFunctions.enterText(xname,name);
                driver.navigate().back();
                ReusableFunctions.dropdownSelect(langDropdown,lang);

                if(ReusableFunctions.selectionChecker(acceptAds)==ads){
                }
                else {
                    ReusableFunctions.mouseClick(acceptAds);
                }
                ReusableFunctions.mouseClick(regBtn);
                logger.info("Information submitted");
            }
            else {
                logger.error("Verification text not located");
                flag = false;
            }
        }
        catch (Exception e){
            logger.error("Could not enter data"+e);
            flag = false;
        }
        return flag;
    }

    private static final By vname = By.xpath("//android.widget.TextView[@resource-id='io.selendroid.testapp:id/label_name_data']");
    private static final By vusrname = By.xpath("//android.widget.TextView[@resource-id='io.selendroid.testapp:id/label_username_data']");
    private static final By vpass = By.xpath("//android.widget.TextView[@resource-id='io.selendroid.testapp:id/label_password_data']");
    private static final By vem = By.xpath("//android.widget.TextView[@resource-id='io.selendroid.testapp:id/label_email_data']");
    private static final By vlang = By.xpath("//android.widget.TextView[@resource-id='io.selendroid.testapp:id/label_preferedProgrammingLanguage_data']");
    private static final By vadds = By.xpath("//android.widget.TextView[@resource-id='io.selendroid.testapp:id/label_acceptAdds_data']");
    private static final By vregBtn = By.xpath("//android.widget.Button[@resource-id='io.selendroid.testapp:id/buttonRegisterUser']");

    public static boolean verifyReg(String ussr,String em,String pass,String name,String lang,String ads){
        boolean flag = true;
        try{
            logger.info("Trying to verify user...");
            ReusableFunctions.waitForElementPresent(vname);
            if(ReusableFunctions.waitForElementPresent(vname)){
                ReusableFunctions.verifyTextMatch(ReusableFunctions.getTextByAttributeText(vname),name);
                ReusableFunctions.verifyTextMatch(ReusableFunctions.getTextByAttributeText(vusrname),ussr);
                ReusableFunctions.verifyTextMatch(ReusableFunctions.getTextByAttributeText(vpass),pass);
                ReusableFunctions.verifyTextMatch(ReusableFunctions.getTextByAttributeText(vem),em);
                ReusableFunctions.verifyTextMatch(ReusableFunctions.getTextByAttributeText(vlang),lang);
                ReusableFunctions.verifyTextMatch(ReusableFunctions.getTextByAttributeText(vadds),ads);
                ReusableFunctions.mouseClick(vregBtn);
                logger.info("user verification done.");
            }
        }
        catch (Exception e){
            logger.error("Failed to verify user");
            flag = false;
        }
        return flag;
    }
}
