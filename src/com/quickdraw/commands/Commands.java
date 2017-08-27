package com.quickdraw.commands;

import java.util.Set;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.quickdraw.Main;
import com.quickdraw.util.MsgManager;

public class Commands implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
        if (args.length <= 0) {
        	return false;
        }
        if (!(sender instanceof Player)) {
        	sender.sendMessage("这个指令只有玩家才能使用");
        	return false;
        }
        Player p = (Player) sender;
        if (args[0].equalsIgnoreCase("price")) {
        	if (args.length == 2) {
        		if (isInt(args[1])) {
        			Block signBlock = p.getTargetBlock((Set<Material>)null, 5);
        			if (signBlock.getType() == Material.WALL_SIGN) {
        				Sign sign = (Sign) signBlock.getState();
        				String drawHead = MsgManager.getColorString(Main.config.getString("SignHead"));
        				if (sign.getLine(0).equalsIgnoreCase(drawHead)) {
        					try {
        						String owner = sign.getLine(1).split(":")[1].trim();
        						if (owner.equalsIgnoreCase(p.getName())) {
        						String line3 = "价格:"+args[1]+"/次";
        						String signLine3 = sign.getLine(2);
        						int beforeMoney = Integer.valueOf(signLine3.split(":")[1].split("/")[0].trim());
        						sign.setLine(2, line3);
        						sign.update();
        						String msg = MsgManager.getColorString(Main.config.getString("PriceChange", "&7[&eQuickDraw&7]&c抽奖机的价格已经从%before%修改成了%after%"));
        						if (msg.contains("%before%")) {
        							msg = msg.replace("%before%", String.valueOf(beforeMoney));
        						}
        						if (msg.contains("%after%")) {
        							msg = msg.replace("%after%", args[1]);
        						}
        						p.sendMessage(msg);
        						} else {
        							p.sendMessage(MsgManager.getColorString(Main.config.getString("SignWarn", "&7[&eQuickDraw&7]&c请面向一个抽奖机上的牌子")));
        						}
        					} catch(Exception exc) {
                				p.sendMessage(MsgManager.getColorString(Main.config.getString("SignWarn", "&7[&eQuickDraw&7]&c请面向一个抽奖机上的牌子")));
                				return false;
        					}
        				} else {
            				p.sendMessage(MsgManager.getColorString(Main.config.getString("SignWarn", "&7[&eQuickDraw&7]&c请面向一个抽奖机上的牌子")));
            				return false;
        				}
        			} else {
        				p.sendMessage(MsgManager.getColorString(Main.config.getString("SignWarn", "&7[&eQuickDraw&7]&c请面向一个抽奖机上的牌子")));
        				return false;
        			}
        		} else {
        			p.sendMessage(MsgManager.getColorString(Main.config.getString("NumberWarn", "&7[&eQuickDraw&7]&c请输入一个正确的数字")));
        			return false;
        		}
        	} else {
        		p.sendMessage(MsgManager.getColorString(Main.config.getString("PirceUsage", "&7[&eQuickDraw&7]&b请使用/qdr price [价格] &7来修改一个抽奖机价格")));
        		return false;
        	}
        }
		return false;
	}
     public boolean isInt(String string) {
    	 try {
    		 int num = Integer.valueOf(string);
    		 if (num < 0) {
    			 return false;
    		 }
    		 return true;
    	 } catch(Exception e) {
    		 return false;
    	 }
     }
}
