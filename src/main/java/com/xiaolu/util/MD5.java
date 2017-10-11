package com.xiaolu.util;

import java.security.MessageDigest;

/**
 * Created by chinaD on 2017/10/11.
 */
public class MD5 {

    public static String addMD5(String str){
        MessageDigest md5 = null;

        try {
            md5 = MessageDigest.getInstance("MD5");
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }

        char[] charArray = str.toCharArray();
        byte[] bytes = new byte[charArray.length];

        for (int i = 0; i < bytes.length; i++){
            bytes[i] = (byte) charArray[i];
        }

        byte[] md5Byte = md5.digest(bytes);
        StringBuffer hexValue = new StringBuffer();

        for (int i = 0; i < md5Byte.length; i++) {
            int val = ((int) md5Byte[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }

        return hexValue.toString();
    }

    public static void main(String[] args) {
        System.out.println(MD5.addMD5("a"));
    }
}
