package com.sf.project.SeleniumCore;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

/**
 * @author : Rachithra R
 *  *
 *  *  MOD	EMPID	  DATE		    DESCRIPTION
 *  * ---	-----     ------	    -----------
 *  * 001	XXX	    07.19.2020 	    Initial Development
 *  * 002    XXX     07.20.2020      Added description for the class and method
 *  *
 *  * PURPOSE : To fetch and return the value of the given key from the config.properties file
 */

public class BasePropertyClass {
    String valueofElementKey;
    Properties props=new Properties();
    FileInputStream fin =null;
    private static final Logger logger=LogManager.getLogger(BasePropertyClass.class);


    /**
     * This method will return the value the key passed to this method,from the config.properties
     * @param webElementKey - The key for which the value is to be fetched
     * @return              - Returns the value of the key in String format
     */
    public String getValueofKey(String webElementKey) {

        try{
            File f = new File("src\\test\\resources\\config.properties");
            fin = new FileInputStream(f);
            props.load(fin);
            valueofElementKey = props.getProperty(webElementKey);
        }catch(Exception e) {
            logger.error("Exception_getValueofKey:"+e.getMessage());
        }
        return valueofElementKey;
    }
}
