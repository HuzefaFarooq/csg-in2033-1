package ac.csg.pu.gui.util;

import ac.csg.pu.members.UserType;

public class SessionManager {
    public static class Pending {
        private static String message;

        // ---- Pending Message ----
        public static void setMessage(String message) { Pending.message = message; }
        public static boolean hasMessage() { return Pending.message != null; }
        public static String getMessage() {
            String msg = Pending.message;
            Pending.message = null;
            return msg;
        }
        public static void clearMessage() { Pending.message = null; }
    }

    public static class User {
        static String email;
        static UserType userType;
        static String guestEmail;

        public static void setEmail(String email) { User.email = email; }
        public static void setUserType(UserType userType) { User.userType = userType; }
        public static void setGuestEmail(String email) { User.guestEmail = email; }

        public static String getEmail() { return email; }
        public static UserType getType() { return userType; }
        public static String getGuestEmail() { return guestEmail; }

        public static boolean isLoggedIn() { return email != null; }
        public static boolean isGuest() { return userType == UserType.G; }
        public static boolean hasGuestEmail() { return guestEmail != null && !guestEmail.isBlank(); }

        public static void clear() {
            email = null;
            userType = null;
            guestEmail = null;
        }
    }

    // ---- Login / Logout ----
    public static void login(String email, UserType userType) {
        User.setEmail(email);
        User.setUserType(userType);
        User.setGuestEmail(null);
    }

    public static void logout() {
        User.clear();
        Pending.clearMessage();
    }
}