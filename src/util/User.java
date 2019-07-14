package util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class User {
    private static ArrayList<UUID> uuids= new ArrayList<>();
    private static HashMap<UUID,pojo.User> users = new HashMap<>();
    public static boolean isLogin(UUID uuid)
    {
        for (UUID u : uuids) {
            if(u.equals(uuid))
            {
                return true;
            }
        }
        return false;
    }
    public static pojo.User getUserInfo(UUID uuid)
    {
        return users.get(uuid);
    }

    public static void userLogin(UUID uuid,pojo.User user)
    {
        uuids.add(uuid);
        users.put(uuid,user);
    }
    public static void userExit(UUID uuid)
    {
        uuids.remove(uuid);
        users.remove(uuid);
    }
}
