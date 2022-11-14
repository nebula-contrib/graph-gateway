package com.lpz.graph.gateway.common.util;

import org.apache.commons.codec.binary.Base64;

import java.nio.charset.StandardCharsets;

/**
 * Base64工具类
 */
public class Base64Util {

    /**
     * Encode base 64. 加密
     *
     * @param str the str
     * @return the string
     */
    public static String encodeBase64(String str) {
        byte[] strByte = str.getBytes(StandardCharsets.UTF_8);
        return encode(strByte);
    }

    /**
     * 将byte数组经base64加密后转为String字符串
     *
     * @param bytes 待加密byte数组
     * @return 编码之后的字符串
     */
    public static String encode(byte[] bytes) {
        String result = null;
        byte[] encodeBase64 = Base64.encodeBase64(bytes);
        if (encodeBase64 != null) {
            result = new String(encodeBase64);
        }
        return result;
    }

    /**
     * Decode base 64. 解密
     *
     * @param str the str
     * @return the string
     */
    public static String decodeBase64(String str) {
        byte[] decodeBase64 = decode(str);
        return decodeBase64 != null ? new String(decodeBase64, StandardCharsets.UTF_8) : null;
    }

    /**
     * 将字符串base64之后解密为byte数组
     *
     * @param str 字符串
     * @return byte数组
     */
    public static byte[] decode(String str) {
        if (str == null) {
            return null;
        }
        byte[] strByte = str.getBytes(StandardCharsets.UTF_8);
        return Base64.decodeBase64(strByte);
    }


    /**
     * @param args
     */
    public static void main(String[] args) {
        // signStr= MD5(${apiKey} + ${secretKey} +${ time})；
//        long nowTime = System.currentTimeMillis();
//        String md5 = Md5Util.getMD5(CommonConstant.API_KEY + CommonConstant.SECRET_KEY + nowTime);
//
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("apiKey", CommonConstant.API_KEY);
//        jsonObject.put("time", nowTime);
//        jsonObject.put("sign", md5);
//        String tokenUn = jsonObject.toString();
//        String token = Base64Util.encodeBase64(tokenUn);
//        System.out.println(token);

//        String str = Base64Util.decodeBase64(token);
//        System.out.println(str);

    }

}
