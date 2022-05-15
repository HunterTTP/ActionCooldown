package com.ActionCooldown;

public class CooldownSettings {

	private static MainClass mainClass = MainClass.getInstance();
	
	public static String block_place_cd_enabeled = mainClass.getConfig().getString("block-place-cooldown-enabled");
	public static long block_place_duration = Long.parseLong(mainClass.getConfig().getString("block-place-cooldown-duration"));
	
	public static String block_break_cd_enabeled = mainClass.getConfig().getString("block-break-cooldown-enabled");
	public static long block_break_duration = Long.parseLong(mainClass.getConfig().getString("block-break-cooldown-duration"));
	
}