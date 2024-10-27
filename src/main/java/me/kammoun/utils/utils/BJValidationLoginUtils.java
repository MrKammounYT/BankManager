package me.kammoun.utils.utils;

public class BJValidationLoginUtils {
    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    public static boolean isValidPhoneNumber(String phone) {
        String phoneRegex = "^\\+?[0-9. ()-]{7,25}$";
        return phone.matches(phoneRegex);
    }
}
