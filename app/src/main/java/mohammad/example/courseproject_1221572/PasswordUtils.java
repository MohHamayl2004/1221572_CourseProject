package mohammad.example.courseproject_1221572;

import java.security.MessageDigest;

public class PasswordUtils {

    public static String encryptPassword(String password) {

        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

            byte[] bytes = messageDigest.digest(password.getBytes());

            StringBuilder stringBuilder = new StringBuilder();

            for (int i = 0; i < bytes.length; i++) {
                stringBuilder.append(Integer.toHexString((bytes[i] & 0xff) + 0x100).substring(1));
            }

            return stringBuilder.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return password;
    }
}