package com.sf.project.SeleniumCore;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.MediaEntityModelProvider;
import com.aventstack.extentreports.Status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.IOException;
/**
 * @author : Rachithra R
 *  *
 *  *  MOD	EMPID	  DATE		    DESCRIPTION
 *  * ---	-----     ------	    -----------
 *  * 001	XXX	    07.19.2020 	    Initial Development
 *  * 002   XXX     07.20.2020      Added comments to the method
 *  *
 *  * PURPOSE : This class will make the entry into the report file based on the status of the steps
 */

public class ExtentListener extends BaseSeleniumClass implements ITestListener {
    public ExtentTest test;
    private static final Logger logger=LogManager.getLogger(ExtentListener.class);


    /**
     * This method will make entry into the extent report based on the status of the executed step
     * @param result - Status of the executed step
     * @throws IOException
     * Author        - Rachithra R
     */
    public void tearDown(ITestResult result) throws IOException {
        if(result.getStatus()==ITestResult.FAILURE){
            try {
                MediaEntityModelProvider screenshot = MediaEntityBuilder.createScreenCaptureFromPath(getscreenshot(appiumDriver, result.getName())).build(); //Adds screenshot to the step
                test.log(Status.FAIL, "EXCEPTION OCCURRED :" + result.getThrowable().getMessage(), screenshot); //to add exception details in the Extent report
            }catch (IOException e){
                logger.error("Exception_tearDown_Failure_captureScreenshot :"+e.getMessage());

            }
        }
        else if(result.getStatus()==ITestResult.SKIP){
            test.log(Status.SKIP,"TEST CASE SKIPPED :"+result.getName());
        }
        else if(result.getStatus()==ITestResult.SUCCESS){
            try {
                MediaEntityModelProvider screenshot = MediaEntityBuilder.createScreenCaptureFromPath(getscreenshot(appiumDriver,result.getName())).build(); //Adds screenshot to the step
                test.log(Status.PASS, "TEST CASE PASSED :" + result.getName(),screenshot);
            }catch (IOException e){
                logger.error("Exception_tearDown_Success_captureScreenshot :"+e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
