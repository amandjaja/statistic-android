package com.amandjaja.statistic;

import java.security.MessageDigest;
import java.security.SecureRandom;

public class StatisticUtility {
    private static final String AN = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final String N = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final String A = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final String ANS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz~!@#$%^&*()_-+={}[]<>,.?/";
    private static SecureRandom rnd = new SecureRandom();

    /**
     * @param length The length of generated String
     * @return Random String Alphabet, Number, and Symbol (Acceptable Symbols : ~!@#$%^&*()_-+={}[]<>,.?/)
     */
    public static String random(int length){
        StringBuilder sb = new StringBuilder( length );
        for( int i = 0; i < length; i++ )
            sb.append(ANS.charAt(rnd.nextInt(ANS.length())));
        return sb.toString();
    }
    /**
     * @param length The length of generated String
     * @return Random String Alphabet, Number
     */
    public static String randomString(int length){
        StringBuilder sb = new StringBuilder( length );
        for( int i = 0; i < length; i++ )
            sb.append(AN.charAt(rnd.nextInt(AN.length())));
        return sb.toString();
    }
    /**
     * @param length The length of generated String
     * @return Random String Alphabet
     */
    public static String randomAlphabet(int length){
        StringBuilder sb = new StringBuilder( length );
        for( int i = 0; i < length; i++ )
            sb.append(A.charAt(rnd.nextInt(A.length())));
        return sb.toString();
    }
    /**
     * @param length The length of generated String
     * @return Random String Number
     */
    public static String randomNumber(int length){
        StringBuilder sb = new StringBuilder( length );
        for( int i = 0; i < length; i++ )
            sb.append(N.charAt(rnd.nextInt(N.length())));
        return sb.toString();
    }

    public static String bytesToHex(byte[] data) {
        StringBuilder buf = new StringBuilder();
        for (byte b : data) {
            int halfbyte = (b >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                buf.append((0 <= halfbyte) && (halfbyte <= 9) ? (char) ('0' + halfbyte) : (char) ('a' + (halfbyte - 10)));
                halfbyte = b & 0x0F;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }

    public static String SHA1(String text){
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] textBytes = text.getBytes("iso-8859-1");
            md.update(textBytes, 0, textBytes.length);
            byte[] sha1hash = md.digest();
            return bytesToHex(sha1hash);
        }catch (Exception e){
            return null;
        }
    }
    public static String MD5(String text){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] textBytes = text.getBytes("iso-8859-1");
            md.update(textBytes, 0, textBytes.length);
            byte[] md5hash = md.digest();
            return bytesToHex(md5hash);
        }catch (Exception e){
            return null;
        }
    }
}
