package com.inventory.utility;

import java.security.MessageDigest;

public class SHA256Util {

    public static String generateSHA256(byte[] data)throws Exception {

        MessageDigest digest =MessageDigest.getInstance("SHA-256");

        byte[] hash = digest.digest(data);

        StringBuilder hexString = new StringBuilder();

        for (byte b : hash) {

            String hex =Integer.toHexString(0xff & b);

            if (hex.length() == 1) {
                hexString.append('0');
            }

            hexString.append(hex);
        }

        return hexString.toString();
    }
}