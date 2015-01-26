package sirfireys.rana.verifiem;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by rana on 27/9/14.
 */


public class ConnectivityCheck {
    public static int TYPE_WIFI = 1;
    public static int TYPE_NETWORK = 2;
    public static int TYPE_NOT_CONNECTED = 0;


    public int connectionStatus(Context context) {
        int status = 0;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {

            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                status = TYPE_WIFI;
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {

                status = TYPE_NETWORK;
            } else {
                status = TYPE_NOT_CONNECTED;
            }

        }

        return status;
    }

}






