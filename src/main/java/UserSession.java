/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
public class UserSession {
    private static int userId;
    private static String username;

    public static void setUser(int id, String uname) {
        userId = id;
        username = uname;
    }

    public static int getUserId() {
        return userId;
    }

    public static String getUsername() {
        return username;
    }

    public static boolean isLoggedIn() {
        return userId > 0;
    }

    public static void clear() {
        userId = 0;
        username = null;
    }
    public static boolean isLogin() {
    return userId != 0;
}

}


/**
 *
 * @author Gunaw
 */

