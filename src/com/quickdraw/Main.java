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
	MsgManager.info("������ʹ��Mcbbs-PCDС�������Ĳ��:QuickDraw");
	MsgManager.info("����LocyDragon,QQ2424441676");
	MsgManager.fine("QuickDraw���ڹر�......");
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
		MsgManager.warn("��������ֵ��쳣,��鿴����̨!");
		e.printStackTrace();
	}
}
private void logMsg() {
	MsgManager.info("������ʹ��Mcbbs-PCDС�������Ĳ��:QuickDraw");
	MsgManager.info("����LocyDragon,QQ2424441676");
}
private void connectionToVault() {
	if (VaultManager.loadLibrary()) {
		MsgManager.fine("�ɹ����ؾ��ÿ�\"Vault\"");
	} else {
		MsgManager.warn("���ؾ��ÿ�ʧ��");
	}
}
private void registerEvents() {
	Bukkit.getPluginManager().registerEvents(new GuiManager(), this);
	Bukkit.getPluginManager().registerEvents(new DrawCreateListener(), this);
	Bukkit.getPluginManager().registerEvents(new DrawProtecter(), this);
	Bukkit.getPluginManager().registerEvents(new DrawListener(), this);
}
}
