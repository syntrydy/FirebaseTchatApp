package cm.app.it.gasmyr.letsay.firebase;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import cm.app.it.gasmyr.letsay.core.Message;

/**
 * Created by gasmyr.mougang on 7/16/17.
 */

public class FirebaseFactory {
    Context context;
    public static final String FIREBASE_USERS = "/USERS/";
    public static final String FIREBASE_MESSAGES = "/messsages/";
    public static final String FIREBASE_DISCUSSIONS = "/DISCUSSIONS/";

    private static FirebaseDatabase database;

    public static FirebaseDatabase getDatabase() {
        if (database == null) {
            database = FirebaseDatabase.getInstance();
            database.setPersistenceEnabled(true);
        }
        return database;
    }

    public static DatabaseReference getUsersReference(String userPhone) {
        return database.getReference(FIREBASE_USERS + userPhone);
    }
    public static DatabaseReference getDiscussionsReference(String key) {
        return database.getReference(FIREBASE_DISCUSSIONS+key);
    }
    public static DatabaseReference getBaseDiscussionsReference() {
        return database.getReference(FIREBASE_DISCUSSIONS);
    }

    public static DatabaseReference getBaseUsersReference() {
        return database.getReference(FIREBASE_USERS);
    }

    public static DatabaseReference getDiscussionsMessagesReference(String key) {
        return database.getReference(FIREBASE_DISCUSSIONS+key+FIREBASE_MESSAGES);
    }
}
