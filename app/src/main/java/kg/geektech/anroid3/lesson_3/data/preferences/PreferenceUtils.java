package kg.geektech.anroid3.lesson_3.data.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import kg.geektech.anroid3.lesson_3.data.model.User;

public class PreferenceUtils {

    private static SharedPreferences preferences;

    private static final String USER_PREFERENCE = "preference_user";
    private static final String USER_NAME = "name_user";
    private static final String USER_TOKEN = "token_user";

    public static void init(Context context) {
        preferences = context.getSharedPreferences(USER_PREFERENCE, Context.MODE_PRIVATE);
    }

    public static void saveUser(User user) {
        preferences.edit().putString(USER_NAME, user.getName()).apply();
        preferences.edit().putString(USER_TOKEN, user.getToken()).apply();
    }

    public static User getUser() {
        String userName = preferences.getString(USER_NAME, "");
        String userToken = preferences.getString(USER_TOKEN, "");
        return new User(userName, userToken);
    }
}
