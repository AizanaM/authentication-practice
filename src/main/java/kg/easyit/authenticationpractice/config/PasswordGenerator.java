package kg.easyit.authenticationpractice.config;

import org.springframework.context.annotation.Configuration;

import java.util.Random;

@Configuration
public class PasswordGenerator {

    public static String generatePassword(){
        String randomPassword = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefjhijklmnopqrstuvwxyz";
        int length = 10;
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; length++) {
            sb.append(randomPassword.charAt(rnd.nextInt(randomPassword.length())));
        }
        return sb.toString();
    }
}
