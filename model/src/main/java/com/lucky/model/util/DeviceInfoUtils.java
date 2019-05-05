package com.lucky.model.util;

import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.util.DisplayMetrics;


/**
 * 获取系统用户信息
 * Created by clf on 15/9/22.
 */
public class DeviceInfoUtils {


    enum INSTANCE {
        SINGLETON;
        DeviceInfoUtils instance = new DeviceInfoUtils();
    }

    public static void onCreate(Context context) {
        INSTANCE.SINGLETON.instance.context = context;
    }

    public static DeviceInfoUtils get() {
        return INSTANCE.SINGLETON.instance;
    }

    private Context context;

    /**
     * 获取操作系统版本
     *
     * @return
     */
    public String getOS() {
        return "Android" + Build.VERSION.RELEASE;
    }

    /**
     * 获取手机品牌
     *
     * @return
     */
    public String getManufacturer() {
        return Build.MANUFACTURER;
    }

    /**
     * 获取手机型号
     *
     * @return
     */
    public String getModel() {
        return Build.MODEL;
    }


    /**
     * 获取cpu型号
     *
     * @return
     */
    public String getCpuModel() {
        return Build.HARDWARE;
    }


    /**
     * 获取屏幕分辨率
     *
     * @return
     */
    public String getScreenRatio() {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.widthPixels + "X" + displayMetrics.heightPixels;
    }

    /**
     * 获取屏幕的宽
     */
    public float getScreenWidth() {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获取屏幕密度
     *
     * @return
     */
    public String getScreenDensity() {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.densityDpi + "DPI";
    }

    /**
     * 获取版本名称
     *
     * @return
     */
    public String getVersionName() {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    public int getVersionCode() {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }

    /**
     * 获取通讯录信息
     *
     * @param uri
     * @return
     */
    public String[] getPhoneContacts(Uri uri) {
        String[] contact = new String[2];
        //得到ContentResolver对象
        ContentResolver cr = context.getContentResolver();
        //取得电话本中开始一项的光标
        Cursor cursor = cr.query(uri, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            //取得联系人姓名
            int nameFieldColumnIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
            contact[0] = cursor.getString(nameFieldColumnIndex);
            //取得电话号码
            String contactid = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            Cursor phone = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactid, null, null);
            if (phone != null) {
                phone.moveToFirst();
                contact[1] = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            }
            phone.close();
            cursor.close();
        } else {
            return null;
        }
        return contact;
    }


}
