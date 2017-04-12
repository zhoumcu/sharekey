package cn.zhiao.baselib.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

/**
 * SharedPrefrece工具类
 * Created by ZengXiaoPing on 2015/8/24.
 */
public class SharedPrefrecesUtils {

    public static final String SPFILENAME = "cheyupinShopping";

    //保存String类型
    public static void saveStrToSharedPrefrences(String key, String value, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SPFILENAME, Context.MODE_APPEND);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    //获取String类型
    public static String getStrFromSharedPrefrences(String key, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SPFILENAME, Context.MODE_APPEND);
        return sharedPreferences.getString(key, null);
    }

    //保存Boolean类型
    public static void saveBooleanToSharedPrefrences(String key, boolean value, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SPFILENAME, Context.MODE_APPEND);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    //获取Boolean类型
    public static Boolean getBooleanFromSharedPrefrences(String key, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SPFILENAME, Context.MODE_APPEND);
        return sharedPreferences.getBoolean(key, false);
    }

    //保存int类型
    public static void saveIntToSharedPrefrences(String key, int value, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SPFILENAME, Context.MODE_APPEND);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    //获取int类型
    public static int getIntFromSharedPrefrences(String key, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SPFILENAME, Context.MODE_APPEND);
        return sharedPreferences.getInt(key, -1);
    }

    //清理保存数据
    public static void clearStrToSharedPrefrences(String key, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SPFILENAME, Context.MODE_APPEND);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.clear();
        edit.commit();
    }

//    //清理Boolean类型
//    public static void clearBooleanToSharedPrefrences(String key, Context context) {
//        SharedPreferences sharedPreferences = context.getSharedPreferences(key, Context.MODE_APPEND);
//        SharedPreferences.Editor edit = sharedPreferences.edit();
//        edit.clear();
//    }
//
//    //清理Int类型
//    public static void clearIntToSharedPrefrences(String key, Context context) {
//        SharedPreferences sharedPreferences = context.getSharedPreferences(key, Context.MODE_APPEND);
//        SharedPreferences.Editor edit = sharedPreferences.edit();
//        edit.clear();
//    }
    /**
     * desc:保存对象

     * @param context
     * @param key
     * @param obj 要保存的对象，只能保存实现了serializable的对象
     * modified:
     */
    public void saveObject(Context context,String key ,Object obj){
        try {
            // 保存对象
            android.content.SharedPreferences sp = context.getSharedPreferences(SPFILENAME, Context.MODE_APPEND);
            SharedPreferences.Editor sharedata = sp.edit();
            //先将序列化结果写到byte缓存中，其实就分配一个内存空间
            ByteArrayOutputStream bos=new ByteArrayOutputStream();
            ObjectOutputStream os=new ObjectOutputStream(bos);
            //将对象序列化写入byte缓存
            os.writeObject(obj);
            //将序列化的数据转为16进制保存
            String bytesToHexString = bytesToHexString(bos.toByteArray());
            //保存该16进制数组
            sharedata.putString(key, bytesToHexString);
            sharedata.commit();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("", "保存obj失败");
        }
    }
    /**
     * desc:将数组转为16进制
     * @param bArray
     * @return
     * modified:
     */
    public static String bytesToHexString(byte[] bArray) {
        if(bArray == null){
            return null;
        }
        if(bArray.length == 0){
            return "";
        }
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }
    /**
     * desc:获取保存的Object对象
     * @param context
     * @param key
     * @return
     * modified:
     */
    public Object readObject(Context context,String key ){
        try {
            android.content.SharedPreferences sharedata = context.getSharedPreferences(SPFILENAME, Context.MODE_APPEND);
            if (sharedata.contains(key)) {
                String string = sharedata.getString(key, "");
                if(TextUtils.isEmpty(string)){
                    return null;
                }else{
                    //将16进制的数据转为数组，准备反序列化
                    byte[] stringToBytes = StringToBytes(string);
                    ByteArrayInputStream bis=new ByteArrayInputStream(stringToBytes);
                    ObjectInputStream is=new ObjectInputStream(bis);
                    //返回反序列化得到的对象
                    Object readObject = is.readObject();
                    return readObject;
                }
            }
        } catch (StreamCorruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //所有异常返回null
        return null;

    }
    /**
     * desc:将16进制的数据转为数组
     * <p>创建人：聂旭阳 , 2014-5-25 上午11:08:33</p>
     * @param data
     * @return
     * modified:
     */
    public static byte[] StringToBytes(String data){
        String hexString=data.toUpperCase().trim();
        if (hexString.length()%2!=0) {
            return null;
        }
        byte[] retData=new byte[hexString.length()/2];
        for(int i=0;i<hexString.length();i++) {
            int int_ch;  // 两位16进制数转化后的10进制数
            char hex_char1 = hexString.charAt(i); ////两位16进制数中的第一位(高位*16)
            int int_ch1;
            if(hex_char1 >= '0' && hex_char1 <='9')
                int_ch1 = (hex_char1-48)*16;   //// 0 的Ascll - 48
            else if(hex_char1 >= 'A' && hex_char1 <='F')
                int_ch1 = (hex_char1-55)*16; //// A 的Ascll - 65
            else
                return null;
            i++;
            char hex_char2 = hexString.charAt(i); ///两位16进制数中的第二位(低位)
            int int_ch2;
            if(hex_char2 >= '0' && hex_char2 <='9')
                int_ch2 = (hex_char2-48); //// 0 的Ascll - 48
            else if(hex_char2 >= 'A' && hex_char2 <='F')
                int_ch2 = hex_char2-55; //// A 的Ascll - 65
            else
                return null;
            int_ch = int_ch1+int_ch2;
            retData[i/2]=(byte) int_ch;//将转化后的数放入Byte里
        }
        return retData;
    }
}
