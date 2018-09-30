package com.cszt.cloud.util;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * DES加密/解密规则,可自行定义秘钥
 * @ClassName: SecureBaseUtil   
 * @Description: DES加密/解密规则  
 * @author: tanjin  
 * @date:2018年9月26日 上午11:04:51
 */
public class DesUtil {
    public static final String key = "fdsafdsafdsafd97932104893174adganfbiewoqryeq33143432109m,.p-1=32195.";
    private static final String ENCODE = "UTF-8";

    /**
     * <pre>
     * DES加解密
     * 通过byte模式，返回也是byte[]，如需要对byte[]做base64请通过此方法。
     * </pre>
     *
     * @param plainText 要处理的byte[]
     * @param key       密钥
     * @param mode      模式
     * @return
     * @throws Exception
     */
    public static byte[] coderByDES(byte[] plainText, byte[] key, int mode) throws Exception {
        SecureRandom sr = new SecureRandom();
        DESKeySpec desSpec = new DESKeySpec(makeByte(key));
        SecretKey secretKey = SecretKeyFactory.getInstance("des").generateSecret(desSpec);
        Cipher cipher = Cipher.getInstance("des");
        cipher.init(mode, secretKey, sr);
        return cipher.doFinal(plainText);
    }

    private static byte[] makeByte(byte[] bytes) {
        byte[] bs = new byte[8];
        int lengh = bytes.length;
        int start = 0;
        byte temp = 0x0;
        for (int i = 0; i < bs.length; ) {
            if (start >= lengh) {
                break;
            } else {
                if (i >= lengh) {
                    bs[i] = 'a';
                } else {
                    if (bs[i] == 0x0) {
                        bs[i] = bytes[start];
                    } else {
                        temp = (byte) (((int) bs[i]) ^ ((int) bytes[start]));
                        bs[i] = temp;
                    }
                }
                start++;
                if (i == (bs.length - 1)) {
                    i = 0;
                } else {
                    i++;
                }
            }
        }
        return bs;
    }

    /**
     * <pre>
     * DES加密
     * 字符串会经过utf-8转的
     * </pre>
     *
     * @param text 加密的字符串
     * @param key  秘钥
     * @return
     * @throws Exception
     */
    public static String encoder(String text, String key) throws Exception {
        if (text == null || key == null) {
            throw new IllegalArgumentException("text is " + text + " key is " + key);
        }
        byte[] result = coderByDES(text.getBytes(ENCODE), key.getBytes(ENCODE), Cipher.ENCRYPT_MODE);
        return byteArr2HexStr(result);
    }

    /**
     * <pre>
     * DES解密
     * 秘钥会经过utf-8转成byte
     * </pre>
     *
     * @param secretText 密文
     * @param key        秘钥
     * @return
     * @throws Exception
     */
    public static String decoder(String secretText, String key) throws Exception {
        if (secretText == null || key == null) {
            throw new IllegalArgumentException("secretText is " + secretText + " key is " + key);
        }
        byte[] result = coderByDES(hexStr2ByteArr(secretText), key.getBytes(ENCODE), Cipher.DECRYPT_MODE);
        return new String(result, ENCODE);
    }

    /**
     * 将byte数组转换为表示16进制值的字符串， 如：byte[]{8,18}转换为：0813， 和public static byte[]
     * hexStr2ByteArr(String strIn) 互为可逆的转换过程
     *
     * @param arrB 需要转换的byte数组
     * @return 转换后的字符串
     */
    private static String byteArr2HexStr(byte[] arrB) {
        if (arrB == null || arrB.length <= 0) {
            return null;
        }
        int iLen = arrB.length;
        StringBuilder sb = new StringBuilder(iLen * 2);
        for (int i = 0; i < iLen; i++) {
            int intTmp = arrB[i];
            // 把负数转换为正数
            while (intTmp < 0) {
                intTmp = intTmp + 256;
            }
            // 小于0F的数需要在前面补0
            if (intTmp < 16) {
                sb.append("0");
            }
            sb.append(Integer.toString(intTmp, 16));
        }
        return sb.toString();
    }

    /**
     * 将表示16进制值的字符串转换为byte数组， 和public static String byteArr2HexStr(byte[] arrB)
     * 互为可逆的转换过程
     *
     * @param strIn 需要转换的字符串
     * @return 转换后的byte数组
     * @throws NumberFormatException
     */
    private static byte[] hexStr2ByteArr(String strIn) throws NumberFormatException {
        if (strIn == null) {
            return null;
        }
        byte[] arrB = strIn.getBytes();
        int iLen = arrB.length;
        byte[] arrOut = new byte[iLen / 2];
        for (int i = 0; i < iLen; i = i + 2) {
            String strTmp = new String(arrB, i, 2);
            arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
        }
        return arrOut;
    }

}
