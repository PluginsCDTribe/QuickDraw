package com.quickdraw.util;

import java.util.List;


import org.bukkit.Location;
import org.bukkit.entity.Player;

public class LocationLake{
private static ListMap<Location,Player> mainMap = new ListMap<>();
public void put(Location loc, Player player) {
	mainMap.put(loc, player);
}
public ListMap<Location,Player> getHandle() {
	return mainMap;
}
public void remove(Location loc) {
	List<Player> value = mainMap.valueList();
	List<Location> key = mainMap.keyList();
	int size = key.size();
	for (int i = 0;i < size;i++) {
		Location now = key.get(i);
		if (now.getBlockX() == loc.getBlockX() 
				&& now.getBlockY() == loc.getBlockY() 
					&& now.getBlockZ() == loc.getBlockZ()) {
			key.remove(i);
			value.remove(i);
		}
	}
	addInto(key,value);
}
public Player get(Location loc) {
	List<Player> value = mainMap.valueList();
	List<Location> key = mainMap.keyList();
	int size = key.size();
	for (int i = 0;i < size;i++) {
		Location now = key.get(i);
		if (now.getBlockX() == loc.getBlockX() 
				&& now.getBlockY() == loc.getBlockY() 
					&& now.getBlockZ() == loc.getBlockZ()
						&& now.getWorld().getName().equalsIgnoreCase(loc.getWorld().getName())) {
			return value.get(i);
		}
	}
	return null;
}
public List<Location> keySet() {
	return mainMap.keyList();
}
private void addInto(List<Location> list1, List<Player> list2) {
	mainMap = new ListMap<>();
	int size = list1.size();
	for (int i = 0;i < size;i++) {
		mainMap.put(list1.get(i), list2.get(i));
	}
}
}
