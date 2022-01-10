package kg.easyit.authenticationpractice.config;

import java.util.Random;

public class PasswordGenerator {

    public static String generatePassword(int len){
        String randomPassword = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefjhijklmnopqrstuvwxyz";
        len = 10;
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(len);
        for(int i =0; i < len ; len ++){
            sb.append(randomPassword.charAt(rnd.nextInt(randomPassword.length())));
        }
        return sb.toString();
    }
    //String randomPass = passwordEncoder.encode(alphaNumericString(10))
}
