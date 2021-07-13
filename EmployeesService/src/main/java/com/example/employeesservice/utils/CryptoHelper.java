package com.example.employeesservice.utils;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Statement;
import java.util.Base64;

@Component
public class CryptoHelper {

    private static final String HMAC_SHA512 = "HmacSHA512";

    @Setter
    private static String secretKey;

    public static String HMAC(String password) {
        Mac sha512Hmac;
        String result = null;

        try {
            final byte[] byteKey = secretKey.getBytes(StandardCharsets.UTF_8);
            sha512Hmac = Mac.getInstance(HMAC_SHA512);
            SecretKeySpec keySpec = new SecretKeySpec(byteKey, HMAC_SHA512);
            sha512Hmac.init(keySpec);
            byte[] macData = sha512Hmac.doFinal(password.getBytes(StandardCharsets.UTF_8));

            // Can either base64 encode or put it right into hex
            result =  Base64.getEncoder().encodeToString(macData);
            //result = bytesToHex(macData);
        } catch (InvalidKeyException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return result;
    }
}
