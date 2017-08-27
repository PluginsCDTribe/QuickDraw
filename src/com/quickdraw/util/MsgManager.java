package com.quickdraw.util;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class MsgManager {
private static Logger logger;
public static void loadLibrary() {
	logger = Bukkit.getLogger();
	assert logger == null;
}
public static void warn(String msg) {
	if (isNull(msg)) {
		return;
	}
	logger.log(Level.WARNING, msg);
}
public static void info(String msg) {
	if (isNull(msg)) {
		return;
	}
	logger.info(ChatColor.WHITE+msg);
}
public static void fine(String msg) {
	if (isNull(msg)) {
		return;
	}
	logger.info(ChatColor.GREEN+msg);
}
private static boolean isNull(String msg) {
	if(msg == null || msg.equalsIgnoreCase("") || msg.trim().equalsIgnoreCase("")) {
		return true;
	}
	return false;
}
public static String getColorString(String msg) {
	if (isNull(msg)) {
		return msg;
	}
	return ChatColor.translateAlternateColorCodes('&', msg);
}
private MsgManager() {}
}
