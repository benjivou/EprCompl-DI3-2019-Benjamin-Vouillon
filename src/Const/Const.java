package Const;

public class Const {

    public static final int PORT = 8080;    // The LocalHost port for connexion

    public static final int WAIT = 60000000;   // waiting time for the first TCP connexion until disconnexion
    public static final int TRY = 3;        // NUmber of restart without connexion

    public static final int BT_WAIT = 100;  // the time when the button is deactivated after onClick event

    /**
     * Constant defining the app name.
     */
    public static final String APP_NAME = "Bar Les Flots Bleus", PRODUCT = "Product:", QUANTITY = "Quantity:",
            UNIT_PRICE = "Unit price (€): ", OK = "Confirm Order", CANCEL = "Cancel Order",
            DEFAULT_UNIT_PRICE = "0", DEFAULT_QUANTITY = "1";

}