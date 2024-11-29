package Backend;

public class Validation {

    public static boolean isValidEmail(String email) {
        return email.matches("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$");
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("^01[0-9]{9}$") && phoneNumber.length() == 11;
    }


}
