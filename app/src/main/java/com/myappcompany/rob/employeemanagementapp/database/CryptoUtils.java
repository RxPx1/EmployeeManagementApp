package com.myappcompany.rob.employeemanagementapp.database;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class CryptoUtils {

    private static final String KEY_ALGORITHM = "DESede"; // Triple DES
    private static final String CIPHER_ALGORITHM = "DESede/ECB/PKCS5Padding"; // ECB mode with PKCS5 padding

    private static final String SECRET_KEY = "ABCDE123456ABCDE"; // Replace with your secret key

    public static String encryptToBase64(String plaintext) {
        try {
            SecretKey secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), KEY_ALGORITHM);
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String decryptBase64ToString(String base64) {
        try {
            SecretKey secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), KEY_ALGORITHM);
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(base64));
            return new String(decryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

