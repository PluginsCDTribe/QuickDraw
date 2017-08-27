package com.quickdraw.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Dispenser;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import com.quickdraw.Main;
import com.quickdraw.events.DrawRemoveEvent;
import com.quickdraw.exception.NoBlockFoundException;
import com.quickdraw.util.BlockLocation;
import com.quickdraw.util.Draw;
import com.quickdraw.util.MsgManager;

public class DrawProtecter implements Listener {
@EventHandler
public void DispenserProtecter(BlockBreakEvent e) {
	if (e.getBlock().getType() == Material.DISPENSER) {
	Block target = e.getBlock();
	Draw draw = new Draw((Dispenser) target.getState());
	draw.setBlock(target);
	try {
		if(draw.getOwner() == null) {
			return;
		}
	} catch (NoBlockFoundException e2) {
		MsgManager.warn(e2.getMessage());
		e2.printStackTrace();
		MsgManager.warn(e2.getWhy());
	}
	try {
		if (!draw.getOwner().trim().equalsIgnoreCase(e.getPlayer().getName())) {
			e.getPlayer().sendMessage(MsgManager.getColorString(Main.config.getString("BlockBreakProtect", "&7[&eQuickDraw&7]你无法拆除别人的抽奖机.")));
			e.setCancelled(true);
		} else if(draw.getOwner().trim().equalsIgnoreCase(e.getPlayer().getName())) {
			e.getPlayer().sendMessage(MsgManager.getColorString(Main.config.getString("DrawRemove", "&7[&eQuickDraw&7]成功移除了一个抽奖机.")));
			BlockLocation.remove(e.getBlock());
			Bukkit.getPluginManager().callEvent(new DrawRemoveEvent(e.getPlayer(), target));
			e.setCancelled(false);
		}
	} catch (NoBlockFoundException e1) {
		MsgManager.warn(e1.getMessage());
		e1.printStackTrace();
		MsgManager.warn(e1.getWhy());
	}
	}

}
@SuppressWarnings("deprecation")
@EventHandler
public void onInteractOnDispenser(PlayerInteractEvent e) {
	if (e.getAction() != Action.RIGHT_CLICK_BLOCK) {
		return;
	}
	if (e.getClickedBlock().getType() == Material.DISPENSER) {
		Block target = e.getClickedBlock();
		Draw draw = new Draw((Dispenser) target.getState());
		draw.setBlock(e.getClickedBlock());
		try {
			if (!draw.hasShopSign()) {
				return;
			}
		} catch (NoBlockFoundException e1) {
			MsgManager.warn(e1.getMessage());
			e1.printStackTrace();
			MsgManager.warn(e1.getWhy());
		}
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			e.getPlayer().sendMessage(MsgManager.getColorString(Main.config.getString("BlockInteractProtect", "&7[&eQuickDraw&7]请&bshift+右键&7点击牌子抽奖.")));
			e.setCancelled(true);
		}
		try {
			if (!draw.getOwner().equalsIgnoreCase(e.getPlayer().getName())) {
				e.getPlayer().sendMessage(MsgManager.getColorString(Main.config.getString("BlockInteractProtect", "&7[&eQuickDraw&7]请&bshift+右键&7点击牌子抽奖.")));
				e.setCancelled(true);
			}
		} catch (NoBlockFoundException e1) {
			MsgManager.warn(e1.getMessage());
			e1.printStackTrace();
			MsgManager.warn(e1.getWhy());
		}
	}
}
@EventHandler
public void onItemSignRemove(BlockBreakEvent e) {
	if (e.getBlock() instanceof Sign || e.getBlock().getType() == Material.WALL_SIGN) {
		Sign sign = (Sign) e.getBlock().getState();
		if (sign.getLine(0).equalsIgnoreCase(MsgManager.getColorString(Main.config.getString("SignHead","[抽奖]")))) {
			e.getPlayer().sendMessage(MsgManager.getColorString(Main.config.getString("BlockSignProtect", "&7[&eQuickDraw&7]你无法拆掉抽奖机上的牌子,如果想要拆掉抽奖机请直接挖掉机器!")));
			e.setCancelled(true);
		}
	}
}
}
