package com.example.what.smartmeeting.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.widget.Toast;

/**
 * Created by what on 04/04/2016.
 */
public class InternetReceiver extends BroadcastReceiver {
    static ChangeInternetState changeInternetState2;

    public static void setChangeInternetState(ChangeInternetState changeInternetState){
        changeInternetState2 =changeInternetState;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"TEST",Toast.LENGTH_SHORT).show();
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo  = manager.getActiveNetworkInfo();
        if (networkInfo!=null&&networkInfo.isConnected()){
            changeInternetState2.onChageInternetState(true);
        } else {
            changeInternetState2.onChageInternetState(false);
        }
    }
    public interface ChangeInternetState{
        public void onChageInternetState(boolean isOffline);
    }
}
