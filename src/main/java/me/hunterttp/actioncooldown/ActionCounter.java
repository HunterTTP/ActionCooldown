package me.hunterttp.actioncooldown;

import java.util.HashMap;
import java.util.Map;

public class ActionCounter {

    private final Map<String, Integer> actiontracker = new HashMap<>();

    public int checkCount(String playerevent) {

            return actiontracker.getOrDefault(playerevent, 0);

    }

    public void resetCount(String playerevent) {

        actiontracker.put(playerevent, 0);

    }

    public void addCount(String playerevent) {

        actiontracker.put(playerevent, actiontracker.getOrDefault(playerevent, 0) + 1);

    }

    public void subtractCount(String playerevent) {

        actiontracker.put(playerevent, actiontracker.getOrDefault(playerevent, 0) - 1);

    }

}
