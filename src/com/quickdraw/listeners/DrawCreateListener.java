package com.quickdraw.listeners;

import org.bukkit.Bukkit;


import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Dispenser;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import com.quickdraw.Main;
import com.quickdraw.events.DrawCreateEvent;
import com.quickdraw.gui.GuiManager;
import com.quickdraw.gui.ItemGui;
import com.quickdraw.util.BlockLocation;
import com.quickdraw.util.MsgManager;

public class DrawCreateListener implements Listener {
@EventHandler
public void onInteract(PlayerInteractEvent e) {
	if (!(e.getAction() == Action.LEFT_CLICK_BLOCK)) {
		return;
	}
	if (e.getClickedBlock().getType() == Material.DISPENSER) {
		if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
			if (e.getPlayer().isSneaking() && isRight(e.getBlockFace())) {
				ItemGui gui = new ItemGui(e.getPlayer(), Bukkit.createInventory(null, 9, MsgManager.getColorString(Main.config.getString("GuiTitle","&a�������������Ʒ!"))));
				gui.openInventory();
				GuiManager.addListener(gui, new Runnable() {

					@Override
					public void run() {
						Block target = e.getClickedBlock();
						BlockState state = e.getClickedBlock().getState();
						Dispenser dispenser = (Dispenser)state;
						Inventory inv = dispenser.getInventory();
						GuiManager.copy(gui.getGui(), inv, e.getPlayer());
						BlockLocation.addBlock(e.getPlayer(), e.getClickedBlock().getLocation());
						Block signBlock = e.getClickedBlock().getRelative(BlockFace.SOUTH, -1);
						signBlock.setType(Material.WALL_SIGN);
						Sign sign = (Sign) signBlock.getState();
						sign.setLine(0, MsgManager.getColorString(Main.config.getString("SignHead","[�齱]")));
						sign.setLine(1, MsgManager.getColorString("����: "+e.getPlayer().getName()));
						sign.setLine(2, MsgManager.getColorString("�۸�:100/��"));
						sign.setLine(3, target.getLocation().getBlockX()+" "+target.getLocation().getBlockY()+" "+target.getLocation().getBlockZ());
						sign.update();
						e.getPlayer().sendMessage(MsgManager.getColorString(Main.config.getString("DrawCreate", "&7[&eQuickDraw&7]�齱���������.")));
						Bukkit.getPluginManager().callEvent(new DrawCreateEvent(e.getPlayer(), e.getClickedBlock()));
					}				
				});
			} else {
				e.getPlayer().sendMessage(MsgManager.getColorString(Main.config.getString("ClickNotice","&7[&eQuickDraw&7]ʹ��&bshift+���&7���&b������&7��&b������&7����һ�� �齱��!")));
			}
		}
	}
}
public boolean isRight(BlockFace face) {
	if (face == BlockFace.WEST || face == BlockFace.EAST || face == BlockFace.SOUTH || face == BlockFace.NORTH) {
		return true;
	}
	return false;
}
}
