package com.nitorac.hashdroid.libs;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Nitorac on 26/02/2015.
 */
public class HashCrypts {

    /****************MD5****************/
    public static String cryptMD5(String input) {
        String md5 = null;
        if(null == input) return null;
        try {
            //Create MessageDigest object for MD5
            MessageDigest digest = MessageDigest.getInstance("MD5");
            //Update input string in message digest
            digest.update(input.getBytes(), 0, input.length());
            //Converts message digest value in base 16 (hex)
            md5 = new BigInteger(1, digest.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return md5;
    }
    /****************Byte to Hex convert****************/
    private static String convertToHex(byte[] data) {
        StringBuilder buf = new StringBuilder();
        for (byte aData : data) {
            int halfbyte = (aData >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                if ((0 <= halfbyte) && (halfbyte <= 9))
                    buf.append((char) ('0' + halfbyte));
                else
                    buf.append((char) ('a' + (halfbyte - 10)));
                halfbyte = aData & 0x0F;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }

    /****************Base method SHA1****************/
    private static String SHA1(String text)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md;
        md = MessageDigest.getInstance("SHA-1");
        byte[] sha1hash;
        md.update(text.getBytes("iso-8859-1"), 0, text.length());
        sha1hash = md.digest();
        return convertToHex(sha1hash);
    }

    /****************SHA1 public method (handle exceptions)****************/
    public static String cryptSHA1(String text)
    {
        String returnText;
        try{
            returnText = SHA1(text);
        } catch (Exception e)
        {
            e.printStackTrace();
            returnText = "ERROR";
        }

        return returnText;
    }

    /****************Base method SHA256****************/
    private static String SHA256(String text)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md;
        md = MessageDigest.getInstance("SHA-256");
        byte[] sha256hash;
        md.update(text.getBytes("iso-8859-1"), 0, text.length());
        sha256hash = md.digest();
        return convertToHex(sha256hash);
    }

    /****************SHA256 public method (handle exceptions)****************/
    public static String cryptSHA256(String text)
    {
        String returnText;
        try{
            returnText = SHA256(text);
        } catch (Exception e)
        {
            e.printStackTrace();
            returnText = "ERROR";
        }

        return returnText;
    }

    /****************Base method SHA384****************/
    private static String SHA384(String text)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md;
        md = MessageDigest.getInstance("SHA-384");
        byte[] sha384hash;
        md.update(text.getBytes("iso-8859-1"), 0, text.length());
        sha384hash = md.digest();
        return convertToHex(sha384hash);
    }

    /****************SHA384 public method (handle exceptions)****************/
    public static String cryptSHA384(String text)
    {
        String returnText;
        try{
            returnText = SHA384(text);
        } catch (Exception e)
        {
            e.printStackTrace();
            returnText = "ERROR";
        }

        return returnText;
    }

    /****************Base method SHA512****************/
    private static String SHA512(String text)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md;
        md = MessageDigest.getInstance("SHA-512");
        byte[] sha512hash;
        md.update(text.getBytes("iso-8859-1"), 0, text.length());
        sha512hash = md.digest();
        return convertToHex(sha512hash);
    }

    /****************SHA512 public method (handle exceptions)****************/
    public static String cryptSHA512(String text)
    {
        String returnText;
        try{
            returnText = SHA512(text);
        } catch (Exception e)
        {
            e.printStackTrace();
            returnText = "ERROR";
        }

        return returnText;
    }

    /****************MD4****************/
    public static String cryptMD4(String input) {
        String md5 = null;
        if(null == input) return null;
        try {
            //Create MessageDigest object for MD5
            MessageDigest digest = MessageDigest.getInstance("MD4");
            //Update input string in message digest
            digest.update(input.getBytes(), 0, input.length());
            //Converts message digest value in base 16 (hex)
            md5 = new BigInteger(1, digest.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return md5;
    }

    public static String cryptWhirlpool(String text) {
        WhirlpoolHelper w = new WhirlpoolHelper();
        byte[] digest = new byte[WhirlpoolHelper.DIGESTBYTES];
        w.NESSIEinit();
        w.NESSIEadd(text);
        w.NESSIEfinalize(digest);
        return WhirlpoolHelper.display(digest);
    }
}
