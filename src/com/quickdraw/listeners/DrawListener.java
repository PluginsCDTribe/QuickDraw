package com.quickdraw.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Dispenser;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.quickdraw.Main;
import com.quickdraw.util.Draw;
import com.quickdraw.util.MsgManager;

public class DrawListener implements Listener {
@EventHandler
public void onInteract(PlayerInteractEvent e) {
	if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
		if (!e.getPlayer().isSneaking()) {
			return;
		}
		if (e.getClickedBlock().getType() == Material.WALL_SIGN) {
			Sign sign = (Sign) e.getClickedBlock().getState();
			String drawHead = MsgManager.getColorString(Main.config.getString("SignHead"));
			if (sign.getLine(0).equalsIgnoreCase(drawHead)) {
				try {
					World atWorld = e.getPlayer().getWorld();
					String[] line4 = sign.getLine(3).split(" ");
					Location blockLoc = new Location(atWorld, Integer.valueOf(line4[0]), Integer.valueOf(line4[1]), Integer.valueOf(line4[2]));
					if (blockLoc.getBlock().getType() == Material.DISPENSER) {
						Dispenser dispenser = (Dispenser) blockLoc.getBlock().getState();
						Draw draw = new Draw(dispenser);
						draw.setBlock(blockLoc.getBlock());
						String line3 = sign.getLine(2);
						int money = Integer.valueOf(line3.split(":")[1].split("/")[0].trim());
						String owner = sign.getLine(1).split(":")[1].trim();
						draw.draw(e.getPlayer(), money, owner);
						if (Bukkit.getPlayer(owner) != null) {
							Player player = Bukkit.getPlayer(owner);
							player.sendMessage(MsgManager.getColorString(Main.config.getString("OwnerDrawEvent", "&7[&eQuickDraw&7]有人在你的抽奖机里尝试抽奖!")));
						}
					} else {
						return;
					}
				} catch(Exception exc) {
					exc.printStackTrace();
				}
			}
		}
	}
}
}
