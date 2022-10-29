package com.odenktools.netzmeodenktools.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class AppDigestUtil {

    /**
     * Algoritma odenktools untuk securing content injection.
     */
    public static String signHash(String hashKey, String externalId, String orderId) throws Exception {
        String data = hashKey + externalId + orderId;
        String hash = new String(Hex.encodeHex(DigestUtils.sha256(data)));
        return hash;
    }

    public static String signHash(String hashKey, String orderId) throws Exception {
        String data = hashKey + orderId;
        String hash = new String(Hex.encodeHex(DigestUtils.sha256(data)));
        return hash;
    }

    /**
     * MyModel model = new MyModel("what is your name?", "odenktools");
     * String uriToSign = "api/simulate/create";
     * Timber.d("HASH %s", EncryptionTools.hashData(uriToSign, model));
     */
    public static String hashData(String uriToSign, String secret, Object model) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        String json = gson.toJson(model);
        String jsonClean = canonicalized(json);
        String scramble = new String(Hex.encodeHex(DigestUtils.sha256(jsonClean)));
        String stringToSign = uriToSign + ":" + scramble;

        return encodeData(stringToSign, secret);
    }

    public static String encodeData(String str, String secret) {
        try {
            Mac sha256Hmac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8),
                    "HmacSHA256");
            sha256Hmac.init(secretKey);
            return new String(Hex.encodeHex(sha256Hmac.doFinal(str.getBytes(StandardCharsets.UTF_8))));
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String hashHmac(String str, String secret) throws Exception {

        Mac sha256Hmac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
        sha256Hmac.init(secretKey);

        final byte[] macData = sha256Hmac.doFinal(str.getBytes());
        byte[] hex = new Hex().encode(macData);

        return new String(hex, StandardCharsets.ISO_8859_1);
    }

    private static String canonicalized(String json) {
        return json.replaceAll(" ", "")
                .replaceAll("\r", "")
                .replaceAll("\n", "")
                .replaceAll("\t", "");
    }
}
