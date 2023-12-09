package org.boardintelligence.utils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class StringEncodeDecodeUtils {

    public static String encode(String plainText) {
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] encodedText = encoder.encode(plainText.getBytes(StandardCharsets.UTF_8));
        return new String(encodedText, StandardCharsets.UTF_8);
    }

    public static String decode(String encodedText) {
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] decodedText = decoder.decode(encodedText);
        return new String(decodedText, StandardCharsets.UTF_8);
    }

}
