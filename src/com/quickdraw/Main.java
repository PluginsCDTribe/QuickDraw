package com.quickdraw;

import java.io.File;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.quickdraw.commands.Commands;
import com.quickdraw.gui.GuiManager;
import com.quickdraw.listeners.DrawCreateListener;
import com.quickdraw.listeners.DrawListener;
import com.quickdraw.listeners.DrawProtecter;
import com.quickdraw.util.BlockLocation;
import com.quickdraw.util.MsgManager;
import com.quickdraw.util.VaultManager;

public class Main extends JavaPlugin{
public static FileConfiguration config;
public static Main instance;
@Override
public void onEnable() {
	saveDefaultConfig();
	loadLibraries();
    logMsg();
	connectionToVault();
	registerEvents();
	getCommand("qdr").setExecutor(new Commands());
}
@Override
public void onDisable() {
	MsgManager.info("你正在使用Mcbbs-PCD小组制作的插件:QuickDraw");
	MsgManager.info("作者LocyDragon,QQ2424441676");
	MsgManager.fine("QuickDraw正在关闭......");
}
public static File getFolder() {
	return instance.getDataFolder();
}
private void loadLibraries() {
	config = getConfig();
	instance = this;
	MsgManager.loadLibrary();
	VaultManager.loadLibrary();
	try {
		BlockLocation.loadLibrary();
	} catch (IOException e) {
		MsgManager.warn("发生了奇怪的异常,请查看控制台!");
		e.printStackTrace();
	}
}
private void logMsg() {
	MsgManager.info("你正在使用Mcbbs-PCD小组制作的插件:QuickDraw");
	MsgManager.info("作者LocyDragon,QQ2424441676");
}
private void connectionToVault() {
	if (VaultManager.loadLibrary()) {
		MsgManager.fine("成功加载经济库\"Vault\"");
	} else {
		MsgManager.warn("加载经济库失败");
	}
}
private void registerEvents() {
	Bukkit.getPluginManager().registerEvents(new GuiManager(), this);
	Bukkit.getPluginManager().registerEvents(new DrawCreateListener(), this);
	Bukkit.getPluginManager().registerEvents(new DrawProtecter(), this);
	Bukkit.getPluginManager().registerEvents(new DrawListener(), this);
}
}
