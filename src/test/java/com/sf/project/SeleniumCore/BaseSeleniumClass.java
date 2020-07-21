package com.sf.project.SeleniumCore;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.NoSuchElementException;

/**
 * @author : Rachithra R
 *  *
 *  *  MOD	EMPID	  DATE		    DESCRIPTION
 *  * ---	------    ------	    -----------
 *  * 001	XXX 	07.19.2020 	    Initial Development
 *  * 002   XXX     07.20.2020      Updated the comments
 *  *
 *  * PURPOSE : This class contains the core selenium methods that customised for scripting convenience and understandability
 */
public class BaseSeleniumClass {

    public static AppiumDriver<MobileElement> appiumDriver= null;
    private static final Logger logger=LogManager.getLogger(BaseSeleniumClass.class);

    String sTypeValue;

    protected BasePropertyClass propKey = new BasePropertyClass();
    protected BaseExcelApachePOI excel;
    protected ExtentHtmlReporter htmlReporter;
    protected ExtentReports extent;
    protected ExtentTest test;
    protected String sDateTime;

    public enum PlatformType{
        ANDROID, IOS}
    public enum SearchType {
        ID, XPATH, CSS, LINK, NAME
    }

    /***
     * This method is used to initiate the driver
     * @param platformType  - The type of platform to be used for testing
     * @return              - This method will return the initiated driver
     * @throws MalformedURLException
     * @throws InterruptedException
     * Author               - Rachithra R
     */
    public AppiumDriver setDriver(PlatformType platformType, String apkFilePath  ) throws MalformedURLException, InterruptedException {
        switch (platformType){
            case ANDROID:
                DesiredCapabilities androidCapabilities  = DesiredCapabilities.android();    // Create object of  DesiredCapabilities class and specify android platform details
                androidCapabilities.setCapability("deviceName","Samsung A30"); //Please update the device appropriately before execution
                androidCapabilities.setCapability("platformName","Android");
                androidCapabilities.setCapability("platformVersion", "10");
                androidCapabilities.setCapability("app", propKey.getValueofKey(apkFilePath));
                androidCapabilities.setCapability("unicodeKeyboard", "true");
                androidCapabilities.setCapability("resetKeyboard", "true");
                try {
                    appiumDriver = new AndroidDriver<MobileElement>(new URL("http://0.0.0.0:4723/wd/hub"), androidCapabilities);
                }catch (Exception e){
                    logger.error("Set driver exception:"+e.getMessage());
                    e.printStackTrace();
                }
                return appiumDriver;
            case IOS:
                DesiredCapabilities iosCapabilities= DesiredCapabilities.iphone();  // Create object of  DesiredCapabilities class and specify ios platform details
                iosCapabilities.setCapability("deviceName","iPhone X");     //Please update the device appropriately before execution
                iosCapabilities.setCapability("platformName","IOS");
                iosCapabilities.setCapability("platformVersion", "12.4");
                iosCapabilities.setCapability("automationName","XCuiTest");
                iosCapabilities.setCapability("app", propKey.getValueofKey(apkFilePath));
                try {
                    appiumDriver = new AndroidDriver<MobileElement>(new URL("http://0.0.0.0:4723/wd/hub"), iosCapabilities);
                }catch (Exception e){
                    logger.error("Exception_setDriver :"+e.getMessage());
                    e.printStackTrace();
                }
                return appiumDriver;
            default:
                throw new RuntimeException("Unsupported platform type");
        }

    }

    /***
     * This method is used to perform click action
     * @param searchType - The type of the locator passed to perform click action
     * @param locator    - The locator value
     * Author            - Rachithra R
     */
    public void clickElement(SearchType searchType, String locator){
        try	{
            switch(searchType) {
                case ID:
                    if(appiumDriver.findElements(By.id(locator)).size() == 1) {
                        appiumDriver.findElement(By.id(locator)).click();
                    }
                    else if(appiumDriver.findElements(By.id(locator)).size() == 0){
                        appiumDriver.quit();
                        throw new NoSuchElementException();
                    }
                    else {
                        Assert.fail("Multiple or no occurrence of the element");
                    }
                    break;
                case XPATH:
                    if(appiumDriver.findElements(By.xpath(locator)).size() == 1) {
                        appiumDriver.findElement(By.xpath(locator)).click();
                    }
                    else if(appiumDriver.findElements(By.id(locator)).size() == 0){
                        appiumDriver.quit();
                        throw new NoSuchElementException();
                    }
                    else {
                        Assert.fail("Multiple or no occurrence of the element");
                    }
                    break;
                case CSS:
                    if(appiumDriver.findElements(By.cssSelector(locator)).size() == 1) {
                        appiumDriver.findElement(By.cssSelector(locator)).click();
                    }
                    else if(appiumDriver.findElements(By.id(locator)).size() == 0){
                        appiumDriver.quit();
                        throw new NoSuchElementException();
                    }
                    else {
                        Assert.fail("Multiple or no occurrence of the element");
                    }
                    break;
                case NAME:
                    if(appiumDriver.findElements(By.name(locator)).size() == 1) {
                        appiumDriver.findElement(By.name(locator)).click();
                    }
                    else if(appiumDriver.findElements(By.id(locator)).size() == 0){
                        appiumDriver.quit();
                        throw new NoSuchElementException();
                    }
                    else {
                        Assert.fail("Multiple or no occurrence of the element");
                    }
                    break;
                case LINK:
                    if(appiumDriver.findElements(By.linkText(locator)).size() == 1) {
                        appiumDriver.findElement(By.linkText(locator)).click();
                    }
                    else if(appiumDriver.findElements(By.id(locator)).size() == 0){
                        appiumDriver.quit();
                        throw new NoSuchElementException();
                    }
                    else {
                        Assert.fail("Multiple or no occurrence of the element");
                    }
                    break;
                default:
                    throw new RuntimeException("Unsupported locator Type");
            }
        }catch(Exception e)	{
            logger.error("Exception_clickAction :"+e.getMessage());
            Assert.fail("ERROR: Performing click operation");
            appiumDriver.quit();
        }
    }

    /**
     * This method used to verify if the element is found
     * @param searchType   - The type of the locator passed to perform click action
     * @param locator      - The locator value
     * @return             - It returns Boolean value True or False.
     * Author              - Rachithra R
     */
    public boolean verifyIsElementExist(SearchType searchType, String locator)
    {
        try
        {
            MobileElement element = null;
            switch(searchType) {
                case ID:
                    element = appiumDriver.findElement(By.id(locator));
                    break;
                case XPATH:
                    element = appiumDriver.findElement(By.xpath(locator));
                    break;
                case CSS:
                    element = appiumDriver.findElement(By.cssSelector(locator));
                    break;
                case NAME:
                    element = appiumDriver.findElement(By.name(locator));
                    break;
                case LINK:
                    element = appiumDriver.findElement(By.linkText(locator));
                    break;
                default:
                    throw new RuntimeException("Unsupported locator type");
            }
            if(element != null ){
                return true;
            }
            else {
                appiumDriver.quit();
                Assert.fail("Element not found on the screen");
                return false;
            }
        }
        catch(Exception e){
            logger.error("Exception_verifyIsElementExist :"+e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    /**
     * This method used to Input Text on element present on screen By searchType. Optionally, the user
     * can specify to hit "TAB" or "ENTER" after the text entry.  Default is false for both.
     * @param searchType  - The type of the locator passed to perform the action
     * @param locator     - The locator value
     * @param text        - Input text
     * @param keyTab 	  - true/false
     * @param keyEnter	  - true/false
     * Author             - Rachithra R
     */
    public void typeTextbox(SearchType searchType, String locator, String text,boolean keyTab,boolean keyEnter)
    {
        boolean bTab = false;
        boolean bEnter = false;

        if(keyTab)
            bTab = true;
        if(keyEnter)
            bEnter = true;

        sTypeValue = "";
        MobileElement element = null;
        try	{
            switch(searchType) {
                case ID:
                    if(appiumDriver.findElements(By.id(locator)).size() == 1) {
                        element = appiumDriver.findElement(By.id(locator));
                    }
                    else {
                        appiumDriver.quit();
                        throw new NoSuchElementException();
                    }
                    break;
                case XPATH:
                    if(appiumDriver.findElements(By.xpath(locator)).size() == 1) {
                        element = appiumDriver.findElement(By.xpath(locator));
                    }
                    else {
                        appiumDriver.quit();
                        throw new NoSuchElementException();
                    }
                    break;
                case CSS:
                    if(appiumDriver.findElements(By.cssSelector(locator)).size() == 1) {
                        element = appiumDriver.findElement(By.cssSelector(locator));
                    }
                    else {
                        appiumDriver.quit();
                        throw new NoSuchElementException();
                    }
                    break;
                case NAME:
                    if(appiumDriver.findElements(By.name(locator)).size() == 1) {
                        element = appiumDriver.findElement(By.name(locator));
                    }
                    else {
                        appiumDriver.quit();
                        throw new NoSuchElementException();
                    }
                    break;
                case LINK:
                    if(appiumDriver.findElements(By.linkText(locator)).size() == 1) {
                        element = appiumDriver.findElement(By.linkText(locator));
                    }
                    else {
                        throw new NoSuchElementException();
                    }
                    break;
                default:
                    appiumDriver.quit();
                    throw new RuntimeException("Unsupported locator type");
            }
            element.sendKeys(text);
            if(bTab)
                ((AndroidDriver)appiumDriver).pressKey(new KeyEvent(AndroidKey.TAB));

            if(bEnter)
                ((AndroidDriver)appiumDriver).pressKey(new KeyEvent(AndroidKey.ENTER));
        }catch(Exception e)	{
            appiumDriver.quit();
            logger.error("Exception_typeTextbox :"+e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * This method is used to get the text of an element
     * @param searchType - The type of the loacator passer to perform the action
     * @param locator    - The locator value
     * @return           - Returns the text of the element
     * Author            - Rachithra R
     */
    public String getElementText(SearchType searchType,String locator){
        try
        {
            MobileElement element = null;
            switch(searchType) {
                case ID:
                    element = appiumDriver.findElement(By.id(locator));
                    break;
                case XPATH:
                    element = appiumDriver.findElement(By.xpath(locator));
                    break;
                case CSS:
                    element = appiumDriver.findElement(By.cssSelector(locator));
                    break;
                case NAME:
                    element = appiumDriver.findElement(By.name(locator));
                    break;
                case LINK:
                    element = appiumDriver.findElement(By.linkText(locator));
                    break;
                default:
                    throw new RuntimeException("Unsupported locator type");
            }
            if(element != null ){
                String textElement =element.getText();
                return textElement;
            }
            else {
                Assert.fail("Element not found on the screen");
                return null;
            }
        }
        catch(Exception e){
            logger.error("Exception_getElementText :"+e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Extent Report         -This method will set the configuration for the Extent Report
     * @param reportFilePath - Path of the report file
     * @throws Exception
     * Author                - Rachithra R
     */
    public ExtentReports setExtent(String reportFilePath) throws Exception{

        htmlReporter= new ExtentHtmlReporter(reportFilePath);
        htmlReporter.config().setDocumentTitle("Automation Report");        //Set the documentation title
        htmlReporter.config().setReportName("Functional Report");           //Set the report name
        //htmlReporter.config().setTheme(Theme.STANDARD);

        extent= new ExtentReports();
        extent.attachReporter(htmlReporter);
        extent.setSystemInfo("Tester",propKey.getValueofKey("QA")); //add as many details as required - os, hostname, browser etc

        return extent;
    }

    /**
     *
     * Extent Report         - This method will take screenshot and return the path to the MediaEntryBuilder
     * @param driver         - WebDriver instance
     * @param screenshotName - Name of the screenshot
     * @throws IOException
     * Author                - Rachithra R
     */
    public static String getscreenshot(WebDriver driver, String screenshotName) throws IOException {
        String dateName= new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        TakesScreenshot screenshot= (TakesScreenshot)driver;
        File source= screenshot.getScreenshotAs(OutputType.FILE);

        //After execution a folder "FailedTestsScreenshots" will be displayed under src
        String destination= System.getProperty("user.dir")+"/Screenshots/"+screenshotName + dateName+".png";
        File destinationFile=  new File(destination);
        FileUtils.copyFile(source,destinationFile);
        return destination;
    }

    /**
     * Extent Report    - This method will create a file
     * @param filePath  - The path in while the extent report will be generated
     * Author           - Rachithra R
     */
    public void createFile(String filePath){
        try {
            File newFile = new File(filePath);
            boolean fileStatus = newFile.createNewFile(); //Create report file
        } catch (IOException e) {
            logger.error("Exception_createFile:"+e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     *  This method gives the Current Time Stamp
     *  @return    - It return is the time stamp in string format
     *  Author     - Rachithra R
     */
    public String getCurrentTimeStamp() {
        sDateTime  = new SimpleDateFormat("yyyyMMMddHHmms").format(Calendar.getInstance().getTime());
        return sDateTime;
    }


}
