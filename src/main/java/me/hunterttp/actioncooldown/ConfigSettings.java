package me.hunterttp.actioncooldown;

import java.util.Objects;

public class ConfigSettings {

    private static final MainClass mainClass = MainClass.getInstance();

    public static String notify_chat_limit = mainClass.getConfig().getString("notify-chat-on-remaining-uses");
    public static String notify_chat_cd = mainClass.getConfig().getString("notify-chat-on-cooldown-completion");
    public static String play_sound_notification = mainClass.getConfig().getString("play-sound-on-cooldown-completion");

    public static String block_place_cd_enabled = mainClass.getConfig().getString("block-place-cooldown-enabled");
    public static long block_place_cd_limit = Long.parseLong(Objects.requireNonNull(mainClass.getConfig().getString("block-place-cooldown-actionlimit")));
    public static long block_place_cd_duration = Long.parseLong(Objects.requireNonNull(mainClass.getConfig().getString("block-place-cooldown-duration")));
    public static String block_place_exempt = mainClass.getConfig().getString("block-place-exempt-blocks");

    public static String block_break_cd_enabled = mainClass.getConfig().getString("block-break-cooldown-enabled");
    public static long block_break_cd_limit = Long.parseLong(Objects.requireNonNull(mainClass.getConfig().getString("block-break-cooldown-actionlimit")));
    public static long block_break_cd_duration = Long.parseLong(Objects.requireNonNull(mainClass.getConfig().getString("block-break-cooldown-duration")));
    public static String block_break_exempt = mainClass.getConfig().getString("block-break-exempt-blocks");

    public static String attack_cd_enabled = mainClass.getConfig().getString("attack-cooldown-enabled");
    public static long attack_cd_limit = Long.parseLong(Objects.requireNonNull(mainClass.getConfig().getString("attack-cooldown-actionlimit")));
    public static long attack_cd_duration = Long.parseLong(Objects.requireNonNull(mainClass.getConfig().getString("attack-cooldown-duration")));
    public static String attack_exempt_mobs = mainClass.getConfig().getString("attack-cooldown-exempt-mobs");

    public static String drop_cd_enabled = mainClass.getConfig().getString("drop-item-cooldown-enabled");
    public static long drop_cd_limit = Long.parseLong(Objects.requireNonNull(mainClass.getConfig().getString("drop-item-cooldown-actionlimit")));
    public static long drop_cd_duration = Long.parseLong(Objects.requireNonNull(mainClass.getConfig().getString("drop-item-cooldown-duration")));
    public static String drop_exempt_mobs = mainClass.getConfig().getString("drop-item-cooldown-exempt-items");

    public static String pickup_cd_enabled = mainClass.getConfig().getString("pickup-item-cooldown-enabled");
    public static long pickup_cd_limit = Long.parseLong(Objects.requireNonNull(mainClass.getConfig().getString("pickup-item-cooldown-actionlimit")));
    public static long pickup_cd_duration = Long.parseLong(Objects.requireNonNull(mainClass.getConfig().getString("pickup-item-cooldown-duration")));
    public static String pickup_exempt_mobs = mainClass.getConfig().getString("pickup-item-cooldown-exempt-items");


}