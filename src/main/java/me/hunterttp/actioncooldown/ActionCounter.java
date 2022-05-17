package me.hunterttp.actioncooldown;

import java.util.HashMap;
import java.util.Map;

public class ActionCounter {


    private static final Map<String, Integer> actiontracker = new HashMap<>();


    public static int checkCount(String playerevent) {

            return actiontracker.getOrDefault(playerevent, 0);

    }

    public static void resetCount(String playerevent) {

        actiontracker.put(playerevent, 1);

    }

    public static void addCount(String playerevent) {

        actiontracker.put(playerevent, actiontracker.getOrDefault(playerevent, 0) + 1);

    }

    public static void removeCount(String playerevent) {

        actiontracker.put(playerevent, actiontracker.get(playerevent) - 1);

    }

}
