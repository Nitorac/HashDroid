package com.nitorac.hashdroid.libs;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.salt.FixedStringSaltGenerator;

import java.security.GeneralSecurityException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Nitorac.
 */
public class CipherCrypts {

    @SuppressWarnings("deprecation")
    public static String cryptdecrypt(String text, String pwd, boolean crypt, String algorithm){
        StandardPBEStringEncryptor myFirstEncryptor = new StandardPBEStringEncryptor();
        myFirstEncryptor.setProvider(new BouncyCastleProvider());

        myFirstEncryptor.setAlgorithm(algorithm);
        FixedStringSaltGenerator generator = new FixedStringSaltGenerator();
        generator.setSalt("NitoracCodedThisApp!");

        myFirstEncryptor.setSaltGenerator(generator);

        myFirstEncryptor.setKeyObtentionIterations(1);
        myFirstEncryptor.setPassword(pwd);

        String myFirstEncryptedText = myFirstEncryptor.encrypt(text);

        if (crypt) {
            return myFirstEncryptedText;
        } else {
            try {
                return myFirstEncryptor.decrypt(text);
            } catch (Exception e) {
                return "Fail";
            }
        }
    }

    public static String cryptdecryptBlowFish(String text, String pwd, boolean crypt)
    {
        if(crypt){
            try{
               return bytesToHex(encryptBlowFish(text, pwd));
            } catch (Exception e){
                e.printStackTrace();
            }
        }else{
            try{
               return decryptBlowFish(text, pwd.getBytes());
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return "ERROR";
    }

    private static byte[] encryptBlowFish(String key, String plainText) throws GeneralSecurityException {

        SecretKey secret_key = new SecretKeySpec(key.getBytes(), "BlowFish");

        Cipher cipher = Cipher.getInstance("BlowFish");
        cipher.init(Cipher.ENCRYPT_MODE, secret_key);

        return cipher.doFinal(plainText.getBytes());
    }

    private static String decryptBlowFish(String key, byte[] encryptedText) throws GeneralSecurityException {

        SecretKey secret_key = new SecretKeySpec(key.getBytes(), "BlowFish");

        Cipher cipher = Cipher.getInstance("BlowFish");
        cipher.init(Cipher.DECRYPT_MODE, secret_key);

        byte[] decrypted = cipher.doFinal(encryptedText);

        return new String(decrypted);
    }

    public static String bytesToHex(byte[] data) {

        if (data == null)
            return null;

        String str = "";

        for (byte aData : data) {
            if ((aData & 0xFF) < 16)
                str = str + "0" + Integer.toHexString(aData & 0xFF);
            else
                str = str + Integer.toHexString(aData & 0xFF);
        }

        return str;

    }
}
