package cm.app.it.gasmyr.letsay.firebase;

import android.content.Context;
import android.content.SharedPreferences;

import cm.app.it.gasmyr.letsay.core.Discussion;
import cm.app.it.gasmyr.letsay.core.Message;
import cm.app.it.gasmyr.letsay.core.Room;
import cm.app.it.gasmyr.letsay.core.User;
import cm.app.it.gasmyr.letsay.util.AppConstants;

/**
 * Created by gasmyr.mougang on 7/16/17.
 */

public class Tchat {
    public static void registerNewUser(User user){
        if(user!=null || !user.equals(new User())){
            FirebaseFactory.getDatabase();
            FirebaseFactory.getUsersReference(user.getPhone()).setValue(user);
        }
    }
    public static void registerNewDiscussion(Discussion destination){
        if(destination!=null){
            FirebaseFactory.getDatabase();
            FirebaseFactory.getDiscussionsReference(destination.getKey()).setValue(destination);
        }
    }

    public static void addMessageToDiscussion(Message message, String key) {
        if(message!=null && key!=null){
            FirebaseFactory.getDatabase();
            String messageId=FirebaseFactory.getDiscussionsMessagesReference(key).push().getKey();
            FirebaseFactory.getDiscussionsMessagesReference(key).child(messageId).setValue(message);
        }
    }
}
