package com.quickdraw.util;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

public class VaultManager{
  private static Economy economy;
  private static boolean supportVault = false;
  public static boolean loadLibrary() {
	  RegisteredServiceProvider<Economy> economyProvider = Bukkit.getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
	  if (economyProvider != null) {
		  economy = economyProvider.getProvider();
      }
      supportVault = economy != null;
      return supportVault;
  }
  @SuppressWarnings("deprecation")
  public static void give(String player, double money) {
	  economy.depositPlayer(player, money);
  }

  @SuppressWarnings("deprecation")
  public static void take(String player, double money) {
	  economy.withdrawPlayer(player, money);
  }

  @SuppressWarnings("deprecation")
  public static double getBalance(String player) {
	  return economy.getBalance(player);
  }

  public static boolean has(String player, double money) {
	  return getBalance(player) >= money;
  }
}
