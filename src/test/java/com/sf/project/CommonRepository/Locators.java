package com.sf.project.CommonRepository;
/**
 * @author : Rachithra R
 *  *
 *  *  MOD	EMPID	  DATE		    DESCRIPTION
 *  * ---	-----     ------	    -----------
 *  * 001	XXX	    07.19.2020 	    Initial Development
 *  *
 *  * PURPOSE : This class contains the locators used in this projects
 */
public class Locators {

    //Amazon Login screen
    public final static String txtSignin=           "//android.widget.TextView[@text='Sign in to your account']";
    public final static String btnSkipSignin=       "//android.widget.Button[@resource-id='com.amazon.mShop.android.shopping:id/skip_sign_in_button']";
    public final static String btnLogin=            "//android.widget.Button[@text='Already a customer? Sign in']";
    public final static String txtUsername=         "//android.widget.EditText[@resource-id='ap_email_login']";
    public final static String btnContinuelogin=    "//android.widget.Button[@text='Continue']";
    public final static String txtboxPassword=      "//android.widget.EditText[@text='Amazon password']";
    public final static String btnLoggin=           "//android.widget.Button[@text='Login']";

    //Amazon home screen
    public final static String imgAmamzon=          "//android.widget.ImageView[@content-desc='Home']";
    public final static String radiobtnLanguage=    "//android.widget.RadioButton[@text='English - EN']";
    public final static String btnSaveChangesLanguage=  "//android.widget.Button[@text='Save Changes']";
    public final static String linkLocation=      "//android.widget.TextView[@text='Select a location to see product availability']";
    public final static String txtEnterPincode=    "//android.widget.TextView[@text='Enter a pincode']";
    public final static String txtboxEnterPincode=  "//android.widget.EditText[@resource-id='com.amazon.mShop.android.shopping:id/loc_ux_pin_code_text_pt1']";
    public final static String btnApplyPincode=     "//android.widget.Button[@text='Apply']";

    //Search screen
    public final static String txtboxSearch=        "//android.widget.EditText[@text='Search']";
    public final static String viewSearchSuggestion="//android.widget.ListView[@resource-id='com.amazon.mShop.android.shopping:id/iss_search_suggestions_list_view']";
    public final static String txtSelectedItem=     "//android.view.View[contains(@text,'Wondertainment')]";
    public final static String txtResultCount=      "//android.widget.TextView[@resource-id='com.amazon.mShop.android.shopping:id/rs_results_count_text']";
    public final static String txtPrice=            "//android.widget.EditText[contains(@text,'rupees')]";
    public final static String btnBuyNow=           "//android.view.View[@resource-id='a-autoid-3']";
    public final static String btnAddToCart=         "//android.widget.Button[@text='Add to Cart']";

    //Cart screen
    public final static String imgCart=             "//android.widget.ImageView[@content-desc='Cart']";
    public final static String txtCartItem=         "//android.widget.TextView[contains(@text,'Wondertainment')]";
    public final static String btnProceedToBuy=     "//android.widget.Button[@text='Proceed to Buy']";

}