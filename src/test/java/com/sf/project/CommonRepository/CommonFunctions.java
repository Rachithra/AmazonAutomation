package com.sf.project.CommonRepository;

import com.sf.project.SeleniumCore.BaseSeleniumClass;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


/**
 * @author : Rachithra R
 *  *
 *  *  MOD	EMPID	  DATE		    DESCRIPTION
 *  * ---	-----     ------	    -----------
 *  * 001	XXX	    07.19.2020 	    Initial Development
 *  *
 *  * PURPOSE : This class consists of the common functions used in the Amamzon app
 */

public class CommonFunctions extends BaseSeleniumClass {

    /**
     * This method will enable the user to login to Amazon app
     * @param username  - Email of the user
     * @param password  - Password that was previously set for the user account
     * @throws InterruptedException
     * Author           - Rachithra R
     */
    public void LogintoAmazon(String username,String password) throws InterruptedException {
        clickElement(BaseSeleniumClass.SearchType.XPATH,Locators.btnLogin);
        WebDriverWait wait = new WebDriverWait(appiumDriver, 20);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(Locators.txtUsername)));  //wait explicitly for 20 seconds until the username appears
        typeTextbox(BaseSeleniumClass.SearchType.XPATH,Locators.txtUsername,username,false,false);
        clickElement(BaseSeleniumClass.SearchType.XPATH,Locators.btnContinuelogin);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(Locators.txtboxPassword)));  //wait explicitly for 20 seconds until the password appears
        typeTextbox(BaseSeleniumClass.SearchType.XPATH,Locators.txtboxPassword,password,false,false);
        clickElement(BaseSeleniumClass.SearchType.XPATH,Locators.btnLoggin);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(Locators.radiobtnLanguage)));  //wait explicitly for 20 seconds until the language selection appears
        clickElement(BaseSeleniumClass.SearchType.XPATH,Locators.radiobtnLanguage);
        clickElement(BaseSeleniumClass.SearchType.XPATH,Locators.btnSaveChangesLanguage);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(Locators.txtboxSearch)));       //wait explicitly for 20 seconds until search box appears
    }

    /**
     * This method will enable a guest user to set the pin code of their location
     * @param pinCode - The users' pin code
     * Author         - Rachithra R
     */
    public void setPincode(String pinCode) throws Exception {
        clickElement(SearchType.XPATH,Locators.linkLocation);                       //Click on Location layout
        clickElement(SearchType.XPATH,Locators.txtEnterPincode);                    //Click Enter pincode
        appiumDriver.findElement(By.xpath(Locators.txtboxEnterPincode)).clear();    //Clear the default pin code
        typeTextbox(SearchType.XPATH,Locators.txtboxEnterPincode,pinCode,false,false);
        clickElement(SearchType.XPATH,Locators.btnApplyPincode);                    //Click apply Pin Code

    }
}
