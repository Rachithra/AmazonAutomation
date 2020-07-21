package com.sf.project.Scripts;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.MediaEntityModelProvider;
import com.aventstack.extentreports.Status;
import com.sf.project.CommonRepository.CommonFunctions;
import com.sf.project.CommonRepository.Locators;
import com.sf.project.SeleniumCore.BaseExcelApachePOI;
import com.sf.project.SeleniumCore.BaseSeleniumClass;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author : Rachithra R
 *  *
 *  *  MOD	EMPID	  DATE		    DESCRIPTION
 *  * ---	-----     ------	    -----------
 *  * 001	XXX	    07.19.2020 	    Initial Development
 *  * 002   XXX     07.20.2020      a- Saved the Amazon.apk file in //test//resources and updated setdriver() it accepts the file path as parameter along with the existing parameter
 *                                  b- Added description for all the 3 methods
 *                                  c- replaced a static wait with explicit wait
 *                                  d- Implemeneted Log4j
 *  * PURPOSE : To validate the purschase functionality in the Amazon app (Test item- 65 inch TV)
 */

@Listeners(com.sf.project.SeleniumCore.ExtentListener.class)
public class Script_PurchaseTV extends BaseSeleniumClass{
    private ExtentReports extent;
    private static final Logger logger=LogManager.getLogger(Script_PurchaseTV.class);

    /**
     * This method
     * 1 - Set the driver instance for the appropriate platform and start a new session
     * 2 - Creates html file used for the Extent report generation
     * 3 - Assigns the TestData.xlsx file to the BaseExcelApachePOI instance
     *
     * @throws Exception
     * Author - Rachithra R
     */
    @BeforeTest
    public void beforeTest()throws Exception{
        String reportFilePath,apkFilepath;

        //Creating html extent report file
        reportFilePath = System.getProperty("user.dir") + "\\ExtentReport\\" + this.getClass().getSimpleName() + "_" +getCurrentTimeStamp() + ".html";
        createFile(reportFilePath);
        logger.info("Report File :"+reportFilePath);
        extent=setExtent(reportFilePath);                              //Passing the report file path to the Extent reports htmlReporter

        excel= new BaseExcelApachePOI(System.getProperty("user.dir")+ propKey.getValueofKey("TestData"));   //passing the test data file path to the BaseExcelApachePOI constructor

        PlatformType platformType= PlatformType.ANDROID;
        apkFilepath= System.getProperty("user.dir")+propKey.getValueofKey("APKFilePath");
        setDriver(platformType,apkFilepath);                           //Set driver
        try {
            WebDriverWait wait = new WebDriverWait(appiumDriver, 10);
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(Locators.txtSignin)));  //wait explicitly for 10 seconds until the Signin text appears
        }catch (TimeoutException e){
            logger.error("Execption_Wait until Signin appears:"+e.getMessage());
            e.printStackTrace();
            appiumDriver.quit();
        }
    }

    /**
     * This method will perform and validate various actions from login to purchase of the test item (65 inch TV)
     * @throws InterruptedException
     * @throws IOException
     * Author - Rachithra R
     */
    @Test
    public void validatePurchase() throws InterruptedException, IOException {
        String matchKey,tvNameDescription,descptionSelected,cartItem;
        test=extent.createTest("Purchase TV from Amazon");
        CommonFunctions functions= new CommonFunctions();

        functions.LogintoAmazon(excel.getCellData("SFTest","Value",5),excel.getCellData("SFTest","Value",6));
        test.log(Status.INFO,"Login to Amazon");                      //adding to report

        verifyIsElementExist(SearchType.XPATH,Locators.imgAmamzon);         //Verify the Amazon.in image on the screen
        clickElement(SearchType.XPATH,Locators.txtboxSearch);               //Click search box

        WebDriverWait wait = new WebDriverWait(appiumDriver, 15);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(Locators.viewSearchSuggestion)));  //wait explicitly for 10 seconds until the search suggestion screen is displayed
        typeTextbox(SearchType.XPATH,Locators.txtboxSearch,excel.getCellData("SFTest","Value",3),false,true);
        test.log(Status.INFO,"Enter the search criteria");            //adding to report

        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(Locators.txtResultCount)));  //wait explicitly for 10 seconds until the result count is displayed
        }catch(TimeoutException e){
            logger.error("Exception_Wait until Result count:"+e.getMessage());
            e.printStackTrace();
            appiumDriver.quit();
        }

        matchKey= excel.getCellData("SFTest","Value",4);
        appiumDriver.findElement(By.xpath("//android.widget.TextView[contains(@text,'"+matchKey+"')]")).click();        // Click on the matched product
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(Locators.txtSelectedItem)));  //wait explicitly for 10 seconds until the selected item description is displayed
        test.log(Status.INFO,"Select the matched item");               //adding to report


        tvNameDescription= excel.getCellData("SFTest","Value",7);
        descptionSelected= getElementText(SearchType.XPATH,Locators.txtSelectedItem);
        logger.info("Selected item description from screen:"+descptionSelected);
        logger.info("Text :"+descptionSelected);
        MediaEntityModelProvider picDescription = MediaEntityBuilder.createScreenCaptureFromPath(getscreenshot(appiumDriver,"SearchAcc.png")).build();
        if(!descptionSelected.equals(tvNameDescription)){                    //Validate the TV Name and Description matches the requirement
            test.log(Status.FAIL,"Validate New button for NA Asset",picDescription);
            Assert.fail("The product name and description do not match");    //Adding screenshot to report on Fail
        }

        //Scroll and click BuyNow
        TouchAction touchAction= new TouchAction(appiumDriver);
        Dimension dimension= appiumDriver.manage().window().getSize();      //find screen size
        int x=dimension.getWidth()/2;
        int starty= (int) (dimension.getHeight()*0.7);
        int endY= (int)(dimension.getHeight()*0.2);
        touchAction.press(PointOption.point(x,starty)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
                .moveTo(PointOption.point(x,endY)).release().perform();

        clickElement(SearchType.XPATH,Locators.btnAddToCart);               //Click Add to Cart
        test.log(Status.INFO,"Add item to cart");                     //adding screenshot to report

        clickElement(SearchType.XPATH,Locators.imgCart);                    //Navigate to cart
        test.log(Status.INFO,"Navigate to Cart");                     //adding screenshot to report

        cartItem=getElementText(SearchType.XPATH,Locators.txtCartItem);
        logger.info("Cart item description:"+cartItem);
        if(!cartItem.equals(descptionSelected)){                             //Validate the TV Name and Description matches the item selected after saerch
            MediaEntityModelProvider picCartDescription = MediaEntityBuilder.createScreenCaptureFromPath(getscreenshot(appiumDriver,"SearchAcc.png")).build();
            test.log(Status.FAIL,"Validate New button for NA Asset",picCartDescription);        //Adding screenshot to report on fail
            Assert.fail("Product description in the cart does not match with the item selected after search");
        }
        clickElement(SearchType.XPATH,Locators.btnProceedToBuy);             //Proceed to buy
    }

    /**
     * This method will terminate the driver instance
     * Author - Rachithra R
     */
    @AfterTest
    public void Teardown(){
        if(appiumDriver!=null) {
            logger.info("Quit Appium Driver");
            appiumDriver.quit();                                             //terminate the driver instance
        }
    }
}
