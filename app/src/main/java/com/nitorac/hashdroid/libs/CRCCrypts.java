package com.nitorac.hashdroid.libs;

/**
 * Created by Nitorac on 26/02/2015.
 */
public class CRCCrypts {
       public static String cryptCRC8(String text)
       {
           text.getBytes();
           String finalString = "0x" + CRC8Helper.calc(text.getBytes(), 1);
          return  finalString;
       }
}