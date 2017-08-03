package cm.app.it.gasmyr.letsay.core.app;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import cm.app.it.gasmyr.letsay.core.User;
import cm.app.it.gasmyr.letsay.util.AppConstants;

/**
 * Created by gasmyr.mougang on 7/31/17.
 */

public class LetsayApp extends Application {
    private static User user;
    private  static  Context context;
    @Override
    public void onCreate() {
        super.onCreate();
    }
    public User getCurrentUser(){
        if(user!=null){
            return user;
        }else{
            user=new User();
            SharedPreferences sharedPreferences=context.getSharedPreferences(AppConstants.SHARE_PREF,MODE_PRIVATE);
            user.setPhone(sharedPreferences.getString(AppConstants.USERID,"5555"));
            user.setName(sharedPreferences.getString(AppConstants.USERID,"5555"));
            return user;
        }
    }

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        LetsayApp.context = context;
    }
}
