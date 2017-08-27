package com.quickdraw.util;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Dispenser;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.quickdraw.Main;
import com.quickdraw.events.DrawEvent;
import com.quickdraw.exception.NoBlockFoundException;



public class Draw {
public static HashMap<Location,Sign> locationMap = new HashMap<>();
Dispenser d;
Block toBlock;
public Draw (Dispenser dispenser) {
	this.d = dispenser;
}
public void setBlock(Block block) {
	this.toBlock = block;
}
public static HashMap<Location,Sign> getHandle() {
	return locationMap;
}
public Block getDrawBlock() {
	return this.toBlock;
}
public Dispenser getDrawDispenser() {
	return this.d;
}
@Deprecated
public Sign getShopSign() throws NoBlockFoundException {
	if (toBlock == null) {
		throw new NoBlockFoundException("Please use method setBlock(Block block) first");
	}
    Block sign = toBlock.getRelative(BlockFace.SOUTH, -1);
    String drawHead = MsgManager.getColorString(Main.config.getString("SignHead"));
    if (!(sign instanceof Sign)) {
    	return null;
    }
    Sign signBlock = (Sign) sign.getState();
    if (signBlock.getLine(0).equals(drawHead)) {
    	return signBlock;
    }
    return null;
}
@Deprecated
public boolean hasShopSign() throws NoBlockFoundException {
	if (toBlock == null) {
		throw new NoBlockFoundException("Please use method setBlock(Block block) first");
	}
	if (getShopSign() == null) {
		return false;
	}
	return true;
}
public String getOwner() throws NoBlockFoundException {
	if (toBlock == null) {
		throw new NoBlockFoundException("Please use method setBlock(Block block) first");
	}
	LocationLake lake = BlockLocation.getLake();
	if (lake.get(toBlock.getLocation()) == null) {
		return null;
	}
	return lake.get(toBlock.getLocation()).getName();
}
@Deprecated
public int getMoney() throws NoBlockFoundException {
	if (toBlock == null) {
		throw new NoBlockFoundException("Please use method setBlock(Block block) first");
	}
	if (hasShopSign() == false) {
		return 0;
	}
	Sign sign = getShopSign();
	String line2 = sign.getLine(2);
	return Integer.valueOf(line2.split(":")[1].split("/")[0].trim());
}
public void draw(Player target, int money, String anotherPlayer) {
	if (money <= 0) {
		this.freeDraw(target, 0);
		String drawEventMsg = Main.config.getString("DrawEvent", "&7[&eQuickDraw&7]你进行了一次抽奖，花费%money%元");
		if (drawEventMsg.contains("%money%")) {
			drawEventMsg = drawEventMsg.replace("%money%", String.valueOf(0));
		}
		target.sendMessage(MsgManager.getColorString(drawEventMsg));
	} else {
		if (VaultManager.getBalance(target.getName()) < money) {
			target.sendMessage(MsgManager.getColorString(Main.config.getString("NoMoneyEnough", "&7[&eQuickDraw&7]你没有足够的金币进行一次抽奖!")));
		} else {
			boolean hasItem = false;
			Inventory inventory = d.getInventory();
			for (ItemStack item : inventory) {
				try {
				if (item != null) {
					hasItem = true;
				}
				} catch(NullPointerException exc) { 
					continue;
				}
			}
			if (hasItem == false) {
				String noEnoughItemMsg = Main.config.getString("NoItemEnough", "&7[&eQuickDraw&7]抽奖机里空空如也!");
				target.sendMessage(MsgManager.getColorString(noEnoughItemMsg));
				return;
			}
			Inventory playerInventory = target.getInventory();
			boolean hasNullPlace = false;
			for (ItemStack item : playerInventory) {
				try {
				if (item == null || item.getType() == Material.AIR) {
					hasNullPlace = true;
				}
				} catch(NullPointerException exc) {
					continue;
				}
			}
			if (hasNullPlace == false) {
				target.sendMessage(MsgManager.getColorString(Main.config.getString("NoEmptyPlace", "&7[&eQuickDraw&7]你的背包里没有空位了!")));
				return;
			}
			String drawEventMsg = Main.config.getString("DrawEvent", "&7[&eQuickDraw&7]你进行了一次抽奖，花费%money%元");
			if (drawEventMsg.contains("%money%")) {
				drawEventMsg = drawEventMsg.replace("%money%", String.valueOf(money));
			}
			VaultManager.take(target.getName(), money);
			VaultManager.give(anotherPlayer, money);
			target.sendMessage(MsgManager.getColorString(drawEventMsg));
			this.freeDraw(target, money);
		}
	}
}
public void freeDraw(Player target, double howMuch) {
	Bukkit.getPluginManager().callEvent(new DrawEvent(target, howMuch));
	double randomNum = Math.random()*9;
	int random = (int) randomNum;
	while (random == 9) {
		randomNum = Math.random()*9;
		random = (int) randomNum;
	}
	ItemStack item = this.d.getInventory().getItem(random);
	while (item == null) {
		randomNum = Math.random()*9;
		random = (int) randomNum;
		item = this.d.getInventory().getItem(random);
	}
	ItemStack removeOneItem = removeOneItem(item);
	this.d.getInventory().setItem(random, removeOneItem);
	this.d.update();
	item.setAmount(1);
	target.getInventory().addItem(item);
	try {
	target.updateInventory();
	} catch(Exception exc) {}
}
public ItemStack removeOneItem(ItemStack item) {
	if (item.getAmount() == 1) {
		return new ItemStack(Material.AIR);
	}
	ItemStack itemSave = item;
	int itemAmount = itemSave.getAmount();
	itemSave.setAmount(itemAmount-1);
	return itemSave;
}
}
