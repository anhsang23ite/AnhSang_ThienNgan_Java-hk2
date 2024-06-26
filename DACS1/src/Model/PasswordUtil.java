package Model;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

    // Hàm mã hóa mật khẩu
    public static String hashPassword(String plainTextPassword) {
        String hashedPassword = BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
        return hashedPassword;
    }

    // Hàm kiểm tra mật khẩu
    public static boolean checkPassword(String plainTextPassword, String hashedPassword) {
        return BCrypt.checkpw(plainTextPassword, hashedPassword);
    }
}
