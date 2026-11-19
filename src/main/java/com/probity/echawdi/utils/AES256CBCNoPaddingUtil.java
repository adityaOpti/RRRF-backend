package com.probity.echawdi.utils;


import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

public final class AES256CBCNoPaddingUtil {

    private static final String TRANSFORMATION = "AES/CBC/NoPadding";

    private AES256CBCNoPaddingUtil() {
    }

    public static String decrypt(
            String encryptedBase64,
            String bearerToken
    ) {
    	
    	    try {
    	        // 1️⃣ Clean input
    	        encryptedBase64 = encryptedBase64.replaceAll("\\s", "").trim();

    	        // 2️⃣ Decode Base64 (URL-safe first)
    	        byte[] decodedBytes;
    	        try {
    	            decodedBytes = Base64.getUrlDecoder().decode(encryptedBase64);
    	        } catch (IllegalArgumentException e) {
    	            decodedBytes = Base64.getDecoder().decode(encryptedBase64);
    	        }

    	        // 3️⃣ 🔴 IMPORTANT: check if it is STILL Base64 text
    	        if (isBase64Text(decodedBytes)) {
    	            // Decode AGAIN
    	            String innerBase64 = new String(decodedBytes, StandardCharsets.UTF_8)
    	                    .replaceAll("\\s", "")
    	                    .trim();

    	            try {
    	                decodedBytes = Base64.getUrlDecoder().decode(innerBase64);
    	            } catch (IllegalArgumentException e) {
    	                decodedBytes = Base64.getDecoder().decode(innerBase64);
    	            }
    	        }

    	        // 4️⃣ NOW this MUST be AES data
    	        if (decodedBytes.length % 16 != 0) {
    	            throw new IllegalArgumentException(
    	                    "Still not valid AES block size: " + decodedBytes.length
    	            );
    	        }

    	        // 5️⃣ Key + IV from bearer token
    	        byte[] tokenBytes = bearerToken.getBytes(StandardCharsets.UTF_8);

    	        byte[] keyBytes = Arrays.copyOfRange(
    	                tokenBytes, tokenBytes.length - 32, tokenBytes.length
    	        );

    	        byte[] ivBytes = Arrays.copyOfRange(
    	                tokenBytes, tokenBytes.length - 16, tokenBytes.length
    	        );

    	        // 6️⃣ Decrypt
    	        Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
    	        cipher.init(
    	                Cipher.DECRYPT_MODE,
    	                new SecretKeySpec(keyBytes, "AES"),
    	                new IvParameterSpec(ivBytes)
    	        );

    	        byte[] decryptedBytes = cipher.doFinal(decodedBytes);

    	        // 7️⃣ Remove zero padding
    	        int len = decryptedBytes.length;
    	        while (len > 0 && decryptedBytes[len - 1] == 0) {
    	            len--;
    	        }

    	        return new String(Arrays.copyOf(decryptedBytes, len), StandardCharsets.UTF_8);

    	    } catch (Exception e) {
    	        throw new RuntimeException("AES decryption failed", e);
    	    }
    	}

    	
    	
    	private static boolean isBase64Text(byte[] data) {
    	    for (byte b : data) {
    	        if (!(b >= 'A' && b <= 'Z') &&
    	            !(b >= 'a' && b <= 'z') &&
    	            !(b >= '0' && b <= '9') &&
    	            b != '+' && b != '/' && b != '=' &&
    	            b != '-' && b != '_') {
    	            return false;
    	        }
    	    }
    	    return true;
    	}

    	
    	
       /* try {
        	
        	
        	
        	// 1️⃣ Clean Base64 input (MANDATORY)
        	encryptedBase64 = encryptedBase64
        	        .replaceAll("\\s", "")
        	        .trim();

        	// 2️⃣ Bearer token → bytes
        	byte[] tokenBytes = bearerToken.getBytes(StandardCharsets.UTF_8);

        	if (tokenBytes.length < 32) {
        	    throw new IllegalArgumentException(
        	            "Bearer token must be at least 32 bytes"
        	    );
        	}

        	// 3️⃣ Key = last 32 bytes of bearer token
        	byte[] keyBytes = Arrays.copyOfRange(
        	        tokenBytes,
        	        tokenBytes.length - 32,
        	        tokenBytes.length
        	);

        	// 4️⃣ IV = last 16 bytes of bearer token
        	byte[] ivBytes = Arrays.copyOfRange(
        	        tokenBytes,
        	        tokenBytes.length - 16,
        	        tokenBytes.length
        	);

        	// 5️⃣ Decode Base64 (URL-safe first, fallback to standard)
        	byte[] encryptedBytes;
        	try {
        	    encryptedBytes = Base64.getUrlDecoder().decode(encryptedBase64);
        	} catch (IllegalArgumentException ex) {
        	    encryptedBytes = Base64.getDecoder().decode(encryptedBase64);
        	}

        	// 6️⃣ AES/CBC/NoPadding → length MUST be multiple of 16
        	if (encryptedBytes.length % 16 != 0) {
        	    throw new IllegalArgumentException(
        	            "Invalid encrypted length (not multiple of 16): "
        	                    + encryptedBytes.length
        	    );
        	}

        	// 7️⃣ Decrypt
        	Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        	cipher.init(
        	        Cipher.DECRYPT_MODE,
        	        new SecretKeySpec(keyBytes, "AES"),
        	        new IvParameterSpec(ivBytes)
        	);

        	byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

        	// 8️⃣ Remove ZERO padding
        	int length = decryptedBytes.length;
        	while (length > 0 && decryptedBytes[length - 1] == 0) {
        	    length--;
        	}

        	return new String(
        	        Arrays.copyOf(decryptedBytes, length),
        	        StandardCharsets.UTF_8
        	);

        	
        	/*  encryptedBase64 = encryptedBase64
                      .replaceAll("\\s", "")
                      .trim();
        	
            //  encryptedBase64 must be ONLY the Base64 string
            encryptedBase64 = encryptedBase64.trim();

            byte[] tokenBytes = bearerToken.getBytes(StandardCharsets.UTF_8);

            if (tokenBytes.length < 32) {
                throw new IllegalArgumentException(
                        "Bearer token must be at least 32 bytes"
                );
            }

            //  last 32 bytes of bearer token
            byte[] keyBytes = Arrays.copyOfRange(
                    tokenBytes,
                    tokenBytes.length - 32,
                    tokenBytes.length
            );

            // 🧂 IV = last 16 bytes of bearer token
            byte[] ivBytes = Arrays.copyOfRange(
                    tokenBytes,
                    tokenBytes.length - 16,
                    tokenBytes.length
            );
            
            
            System.out.println("middlle of the line------------------------ : ");

            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(
                    Cipher.DECRYPT_MODE,
                    new SecretKeySpec(keyBytes, "AES"),
                    new IvParameterSpec(ivBytes)
            );

            // 🔄 Base64 → bytes
           // byte[] encryptedBytes = Base64.getDecoder().decode(encryptedBase64);
            
            byte[] encryptedBytes = Base64.getUrlDecoder().decode(encryptedBase64);


            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

            // ✂ Remove ZERO padding
            int length = decryptedBytes.length;
            while (length > 0 && decryptedBytes[length - 1] == 0) {
                length--;
            }

            System.out.println("last of the line------------------------ : ");
            
            return new String(
                    Arrays.copyOf(decryptedBytes, length),
                    StandardCharsets.UTF_8
            );

        } catch (IllegalArgumentException e) {
            throw new RuntimeException(
                    "Invalid Base64 input (ensure no JSON or braces are present)",
                    e
            );
        } catch (Exception e) {
            throw new RuntimeException(
                    "AES-256-CBC NoPadding decryption failed",
                    e
            );
        }
    }*/
}


/*import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public final class AES256CBCNoPaddingUtil {

    private static final String TRANSFORMATION = "AES/CBC/NoPadding";

    private AES256CBCNoPaddingUtil() {
    }

    public static String decrypt(
            String encryptedHex,
            String bearerToken
    ) {
        try {
            byte[] tokenBytes = bearerToken.getBytes(StandardCharsets.UTF_8);

            if (tokenBytes.length < 32) {
                throw new IllegalArgumentException(
                        "Bearer token must be at least 32 bytes"
                );
            }

            // 🔑 KEY → last 32 bytes of bearer token
            byte[] keyBytes = Arrays.copyOfRange(
                    tokenBytes,
                    tokenBytes.length - 32,
                    tokenBytes.length
            );

            // 🧂 IV → last 16 bytes of bearer token
            byte[] ivBytes = Arrays.copyOfRange(
                    tokenBytes,
                    tokenBytes.length - 16,
                    tokenBytes.length
            );

            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(
                    Cipher.DECRYPT_MODE,
                    new SecretKeySpec(keyBytes, "AES"),
                    new IvParameterSpec(ivBytes)
            );

            // 🔄 HEX → BYTES
            byte[] encryptedBytes = hexToBytes(encryptedHex);

            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

            // ✂ Remove ZERO padding
            int length = decryptedBytes.length;
            while (length > 0 && decryptedBytes[length - 1] == 0) {
                length--;
            }

            return new String(
                    Arrays.copyOf(decryptedBytes, length),
                    StandardCharsets.UTF_8
            );

        } catch (Exception e) {
            throw new RuntimeException("AES-256-CBC NoPadding decryption failed", e);
        }
    }

    // ✅ HEX decoder
    private static byte[] hexToBytes(String hex) {
        if (hex.length() % 2 != 0) {
            throw new IllegalArgumentException("Invalid HEX string");
        }

        int len = hex.length();
        byte[] data = new byte[len / 2];

        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte)
                    ((Character.digit(hex.charAt(i), 16) << 4)
                     + Character.digit(hex.charAt(i + 1), 16));
        }
        return data;
    }
}
*/


