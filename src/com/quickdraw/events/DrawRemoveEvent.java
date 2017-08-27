package com.quickdraw.events;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class DrawRemoveEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private Player player;
    private Block block;
    public DrawRemoveEvent(final Player player, Block block) {
        this.player = player;
        this.block = block;
    }
    public Player getPlayer() {
        return player;
    }
    public Block getBlock() {
    	return block;
    }
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
