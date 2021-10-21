package com.example.demo.error.util;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

@Slf4j
public class AESEncryptDecrypt {
    /**
     * This is done using AES Advanced Encryption System.
     * This is symmetric algorithm [ECB Electronic Code Book  CBC Cipher block chaining]
     * ECB dont use IV
     * CBC use IV
     * Here we are using CBC because
     * identical plain text blocks will be encrypted into dis-similar cipher text blocks in CBC
     */
    private static final String key = "fVTqgb@3zwDcs#Ry";
    private static final String initVector = "mNp0b#vLkgr!jyhT";
    private static final String transformation = "AES/CBC/PKCS5PADDING";
    private static final String slash = "Sl@SH";

    public static String encrypt(String value) {
        try {
            IvParameterSpec iv = getInitializationVector();
            SecretKeySpec skeySpec = getSecretKeySpec();
            Cipher cipher = Cipher.getInstance(transformation);
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            byte[] encrypted = cipher.doFinal(value.getBytes());
            String encryptedString = Base64.getEncoder().encodeToString(encrypted);
            String encryptedString1 = encryptedString.replaceAll("/",slash);
            return encryptedString1;
        } catch (Exception ex) {
            //ex.printStackTrace();
            log.debug("Error in AES encrypt ", ex.getMessage());
        }
        return null;
    }

    public static String decrypt(String encrypted) {
        try {
            IvParameterSpec iv = getInitializationVector();
            SecretKeySpec skeySpec = getSecretKeySpec();
            Cipher cipher = Cipher.getInstance(transformation);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            String encryptedString1 = encrypted.replaceAll(slash,"/");
            byte[] original = cipher.doFinal(Base64.getDecoder().decode(encryptedString1));
            return new String(original);
        } catch (Exception ex) {
            //ex.printStackTrace();
            log.debug("Error in AES decrypt ", ex.getMessage());
        }

        return null;
    }

    private static IvParameterSpec getInitializationVector() throws UnsupportedEncodingException {
        return new IvParameterSpec(initVector.getBytes("UTF-8"));
    }

    private static SecretKeySpec getSecretKeySpec() throws UnsupportedEncodingException {
        return new SecretKeySpec(key.getBytes("UTF-8"), "AES");
    }

    public static String encryptLong(Long value) {
        try {
            IvParameterSpec iv = getInitializationVector();
            SecretKeySpec skeySpec = getSecretKeySpec();
            Cipher cipher = Cipher.getInstance(transformation);
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            byte[] encrypted = cipher.doFinal(value.toString().getBytes());
            String encryptedString = Base64.getEncoder().encodeToString(encrypted);
            String encryptedString1 = encryptedString.replaceAll("/",slash);
            return encryptedString1;
        } catch (Exception ex) {
            //ex.printStackTrace();
            log.debug("Error in AES encrypt ", ex.getMessage());
        }
        return null;
    }

    public static Long decryptLong(String encrypted) {
        try {
            IvParameterSpec iv = getInitializationVector();
            SecretKeySpec skeySpec = getSecretKeySpec();
            Cipher cipher = Cipher.getInstance(transformation);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            String encryptedString1 = encrypted.replaceAll(slash,"/");
            byte[] original = cipher.doFinal(Base64.getDecoder().decode(encryptedString1));
            return Long.valueOf(new String(original));
        } catch (Exception ex) {
            //ex.printStackTrace();
            log.debug("Error in AES decrypt ", ex.getMessage());
        }

        return null;
    }

    public static String encryptInteger(Integer value) {
        try {
            IvParameterSpec iv = getInitializationVector();
            SecretKeySpec skeySpec = getSecretKeySpec();
            Cipher cipher = Cipher.getInstance(transformation);
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            byte[] encrypted = cipher.doFinal(value.toString().getBytes());
            String encryptedString = Base64.getEncoder().encodeToString(encrypted);
            String encryptedString1 = encryptedString.replaceAll("/",slash);
            return encryptedString1;
        } catch (Exception ex) {
            //ex.printStackTrace();
            log.debug("Error in AES encrypt ", ex.getMessage());
        }
        return null;
    }

    public static Integer decryptInteger(String encrypted) {
        try {
            IvParameterSpec iv = getInitializationVector();
            SecretKeySpec skeySpec = getSecretKeySpec();
            Cipher cipher = Cipher.getInstance(transformation);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            String encryptedString1 = encrypted.replaceAll(slash,"/");
            byte[] original = cipher.doFinal(Base64.getDecoder().decode(encryptedString1));
            return Integer.valueOf(new String(original));
        } catch (Exception ex) {
            //ex.printStackTrace();
            log.debug("Error in AES decrypt ", ex.getMessage());
        }

        return null;
    }

}