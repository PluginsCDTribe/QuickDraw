package com.quickdraw.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class ItemGui {
private String ownerName;
private Player owner;
private Inventory gui;
public ItemGui(Player owner, Inventory gui) {
	this.owner = owner;
	this.gui = gui;
	this.ownerName = this.owner.getName();
	assert owner == null;
	assert gui == null;
	assert !owner.isOnline();
	assert ownerName.equalsIgnoreCase("");
}
public String getOwnerName() {
	return this.ownerName;
}
public Player getOwner() {
	return Bukkit.getPlayer(ownerName);
}
public Inventory getGui() {
	return this.gui;
}
public void openInventory() {
	Player player = Bukkit.getPlayer(ownerName);
	player.openInventory(gui);
}
public void openInventory(Player anotherPlayer) {
	assert anotherPlayer == null;
	assert !anotherPlayer.isOnline();
	anotherPlayer.openInventory(gui);
}
public void setGui(Inventory gui) {
	assert gui == null;
	this.gui = gui;
}
public void setPlayer(Player owner) {
	assert owner == null;
	assert !owner.isOnline();
	this.owner = owner;
	this.ownerName = this.owner.getName();
}
@Override
public boolean equals(Object obj) {
	if (!(obj instanceof ItemGui)) {
		return false;
	}
	ItemGui another = (ItemGui) obj;
	if (another.getGui().equals(this.gui) && another.getOwnerName().equalsIgnoreCase(another.getOwnerName())) {
		return true;
	}
	return false;
}
}
