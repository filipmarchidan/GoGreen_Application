package API.Users;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
public class SecurePassword {

    // Encryte Password with BCryptPasswordEncoder
    public static String securepassword(String password) {
        BCryptPasswordEncoder sPass = new BCryptPasswordEncoder();
        return sPass.encode(password);
    }

    public static void main(String[] args) {
        String password = "123";
        String securePassword = securepassword(password);

        System.out.println("Encryted Password: " + securePassword);
    }
}
