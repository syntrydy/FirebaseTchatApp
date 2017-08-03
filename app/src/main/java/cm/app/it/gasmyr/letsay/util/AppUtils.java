package cm.app.it.gasmyr.letsay.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;

import es.dmoral.toasty.Toasty;

/**
 * Created by gasmyr.mougang on 7/16/17.
 */

public class AppUtils {

    public static void showError(Context context,String message){
        Toasty.error(context,message).show();
    }
    public static void showInfo(Context context,String message){
        Toasty.info(context,message).show();
    }
    public static void showSuccess(Context context,String message){
        Toasty.success(context,message).show();
    }
    public static void showWarning(Context context,String message){
        Toasty.warning(context,message).show();
    }

    public static String getContactName(@NonNull Context context, String phoneNumber) {
            return getName(context, phoneNumber);
    }

    private static String getName(@NonNull Context context, String phoneNumber) {
        ContentResolver cr = context.getContentResolver();
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNumber));
        Cursor cursor = cr.query(uri, new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME}, null, null, null);
        if (cursor == null) {
            return phoneNumber;
        }
        String contactName = phoneNumber;
        if (cursor.moveToFirst()) {
            contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return contactName;
    }

}
