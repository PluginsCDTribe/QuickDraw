package com.quickdraw.events;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Dispenser;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.quickdraw.util.Draw;


public class DrawCreateEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private Player player;
    private Block dispenser;
    private Draw draw;
    public DrawCreateEvent(final Player player, Block dispenser) {
        this.player = player;
        this.dispenser = dispenser;
    }
    public Draw getDraw() {
    	if (this.draw == null) {
    		createDraw();
    		return this.draw;
    	}
    	return this.draw;
    }
    public Block getDrawBlock() {
    	return this.dispenser;
    }
    public Location getDrawLocation() {
    	return this.dispenser.getLocation();
    }
    public Dispenser getDrawDispenser() {
    	return (Dispenser) dispenser.getState();
    }
    public Player getPlayer() {
        return this.player;
    }
    public String getPlayerName() {
    	return this.player.getName();
    }
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
    private void createDraw() {
    	Dispenser dispenser = (Dispenser) this.dispenser.getState();
    	Draw draw = new Draw(dispenser);
    	draw.setBlock(this.dispenser);
    	this.draw = draw;
    }
}
