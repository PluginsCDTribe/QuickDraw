package com.quickdraw.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.quickdraw.util.VaultManager;

public class DrawEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private Player player;
    private double money;
    public DrawEvent(final Player player, double money) {
        this.player = player;
        this.money = money;
    }
    public double getHowMuch() {
    	return this.money;
    }
    public String getPlayerName() {
    	return this.player.getName();
    }
    public void takeMoney(double money) {
    	VaultManager.take(this.player.getName(), this.money);
    }
    public Player getPlayer() {
        return this.player;
    }
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
