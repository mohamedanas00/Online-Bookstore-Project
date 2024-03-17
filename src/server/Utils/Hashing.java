package server.Utils;

import java.security.MessageDigest;

public class Hashing {
    public static String doHashing(String password) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(password.getBytes());
            byte[] resultByte = messageDigest.digest();
            StringBuilder builder = new StringBuilder();
            for (byte b : resultByte) {
                builder.append(String.format("%02x", b));
            }
            return builder.toString();
        } catch (Exception e) {
            System.out.println(e);
        }
        return password;
    }

    public static boolean comparePassword(String Password, String hashedPassword) {
        String hashedEnteredPassword = doHashing(Password);
        return hashedEnteredPassword.equals(hashedPassword);
    }
}
