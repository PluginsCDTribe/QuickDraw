package com.quickdraw.gui;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.quickdraw.Main;
import com.quickdraw.util.MsgManager;



public class GuiManager implements Listener {
private static HashMap<ItemGui,Runnable> runnable = new HashMap<ItemGui,Runnable>();
public static void addListener(ItemGui gui,Runnable onGuiCloseEvent) {
	runnable.put(gui, onGuiCloseEvent);
}
@EventHandler
public void onGuiClose(InventoryCloseEvent e) {
	for (ItemGui gui : runnable.keySet()) {
		if (gui.getOwnerName().equalsIgnoreCase(e.getPlayer().getName()) && gui.getGui().equals(e.getInventory())) {
			runnable.get(gui).run();
			runnable.remove(gui);
		}
	}
}
@EventHandler
public void onPlayerQuit(PlayerQuitEvent e) {
	for (ItemGui gui : runnable.keySet()) {
		if (gui.getOwnerName().equalsIgnoreCase(e.getPlayer().getName())) {
			runnable.remove(gui);
		}
	}
}@EventHandler
public void onPlayerKick(PlayerQuitEvent e) {
	for (ItemGui gui : runnable.keySet()) {
		if (gui.getOwnerName().equalsIgnoreCase(e.getPlayer().getName())) {
			runnable.remove(gui);
		}
	}
}
public Inventory getGuiOfOwner(Player owner) {
	for (ItemGui gui : runnable.keySet()) {
		if (gui.getOwnerName().equalsIgnoreCase(owner.getName())) {
			return gui.getGui();
		}
	}
	return null;
}
public static void copy(Inventory firstInv, Inventory inv2, Player target) {
	    int lock = 0;
		for (ItemStack item : firstInv) {
			if (item == null) {
				continue;
			}
			inv2.addItem(item);
			firstInv.setItem(lock, new ItemStack(Material.AIR));
			lock++;
			if (lock == 9) {
				break;
			}
		}
		boolean hasMoreItem = false;
		for (ItemStack item : firstInv) {
			if(item == null) {
				continue;
			}
			target.getWorld().dropItem(target.getLocation(), item);
			hasMoreItem = true;
		}
		if (hasMoreItem) {
			target.sendMessage(MsgManager.getColorString(Main.config.getString("HasMoreItem","&7[&eQuickDraw&7]检测到你有超出九个格子的物品，已经全部丢出来了.")));
		}
}
}
