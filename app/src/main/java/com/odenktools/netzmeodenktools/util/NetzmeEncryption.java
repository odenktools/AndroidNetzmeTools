package com.odenktools.netzmeodenktools.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.util.Base64;

import timber.log.Timber;

public class NetzmeEncryption {

    static final String characterEncoding = "UTF-8";
    static final String cipherTransformation = "AES/CBC/PKCS5PADDING";
    static final String aesEncryptionAlgorithem = "AES";
    static final String charsetName = "UTF8";

    /**
     * @param encryptionKey
     * @param plainText
     * @return
     */
    public static String EncryptAES128(final String encryptionKey, final String plainText) {
        String encryptedText = "";
        try {
            final Cipher cipher = Cipher.getInstance(cipherTransformation);
            final byte[] key = encryptionKey.getBytes(characterEncoding);
            final SecretKeySpec secretKey = new SecretKeySpec(key, aesEncryptionAlgorithem);
            final IvParameterSpec ivparameterspec = new IvParameterSpec(key);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivparameterspec);
            final byte[] cipherText = cipher.doFinal(plainText.getBytes(charsetName));
            byte[] encodedBytes = org.apache.commons.codec.binary.Base64.encodeBase64(cipherText);
            return new String(encodedBytes);
        } catch (Exception ex) {
            Timber.e(ex, "Encryption Exception");
        }
        return encryptedText;
    }

    public static String DecryptAES128(final String encryptionKey, String encryptedText) {
        String decryptedText = "";
        try {
            final Cipher cipher = Cipher.getInstance(cipherTransformation);
            final byte[] key = encryptionKey.getBytes(characterEncoding);
            final SecretKeySpec secretKey = new SecretKeySpec(key, aesEncryptionAlgorithem);
            final IvParameterSpec ivparameterspec = new IvParameterSpec(key);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivparameterspec);
            byte[] decodedBytes = org.apache.commons.codec.binary.Base64.decodeBase64(encryptedText);
            decryptedText = new String(cipher.doFinal(decodedBytes), charsetName);

        } catch (Exception ex) {
            Timber.e(ex, "Decrypt Exception");
        }
        return decryptedText;
    }
}
