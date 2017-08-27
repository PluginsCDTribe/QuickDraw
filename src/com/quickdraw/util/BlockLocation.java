package com.quickdraw.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.quickdraw.Main;

public class BlockLocation {
static File targetFile;
static FileConfiguration config;
private static LocationLake blocks;
public static void loadLibrary() throws IOException {
	blocks = new LocationLake();
	File configFile = new File(Main.getFolder(), "Blocks.yml");
	config = loadItem(configFile);
	if (config.getString("Blocks", "nullArgs").equalsIgnoreCase("nullArgs")) {
		return;
	} else {
		for (String s : config.getStringList("Blocks")) {
			String[] strings = s.split(",");
		    blocks.put(new Location(Bukkit.getWorld(strings[1]),toInt(strings[2]),toInt(strings[3]),toInt(strings[4])), Bukkit.getPlayer(strings[0]));
		}
	}
}
public static LocationLake getLake() {
	return blocks;
}
private static FileConfiguration loadItem(File file) throws IOException {
	if (!file.exists()) {
		file.createNewFile();
	}
	targetFile = file;
	return YamlConfiguration.loadConfiguration(file);
}
public static void addBlock(Player player, Location loc) {
	blocks.put(loc,player);
	List<String> serialize = new ArrayList<>();
	for (Location locs : blocks.keySet()) {
		if (locs == null) {
			continue;
		}
		serialize.add(player.getName()+","+locs.getWorld().getName()+","+locs.getBlockX()+","+locs.getBlockY()+","+locs.getBlockZ());
	}
	config.set("Blocks", serialize);
	try {
		config.save(targetFile);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
public static boolean isBlock(Block target) {
	for(Location b : blocks.keySet()) {
		if (b.getBlockX() == target.getLocation().getBlockX() &&
				b.getBlockY() == target.getLocation().getBlockY() &&
					b.getBlockZ() == target.getLocation().getBlockX()) {
			return true;
		}
	}
	return false;
}
public static void remove(Block target) {
	blocks.remove(target.getLocation());
	List<String> serialize = new ArrayList<>();
	for (Location locs : blocks.keySet()) {
		if (locs == null) {
			continue;
		}
		if (blocks.get(locs) == null) {
			return;
		}
		//因为这里抛出空指针异常,debug的时候写成这样子，出错原因在上面那个Null的判断修复了，发现自己很蠢
		serialize.add
		(blocks.
				get(locs).
				getName()+","+
				locs.getWorld().
				getName()+","+
				locs.
				getBlockX()+
				","+
				locs.
				getBlockY()+
				","+locs.
				getBlockZ());
	}
	config.set("Blocks", serialize);
	try {
		config.save(targetFile);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
public static Player getOwner(Block target) {
	return blocks.get(target.getLocation());
}
public static String getOwnerName(Block target) {
	return blocks.get(target.getLocation()).getName();
}
public static int toInt(String target) {
	return Integer.valueOf(target);
}
}
