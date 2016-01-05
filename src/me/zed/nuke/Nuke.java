package me.zed.nuke;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import me.AdityaTD.ClusterAPI.Titles;

public class Nuke extends JavaPlugin implements Listener {

	public void onEnable() {
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
	}

	public void openShop(Player p) {
		Inventory shop = Bukkit.createInventory(null, 9, ChatColor.GREEN + "Shop");
		ItemStack nuke = new ItemStack(Material.BLAZE_POWDER);
		ItemMeta nukemeta = nuke.getItemMeta();
		nukemeta.setDisplayName(ChatColor.GOLD + "Nuke");
		nuke.setItemMeta(nukemeta);
		shop.addItem(nuke);
		p.openInventory(shop);
	}

	@EventHandler
	public void interact(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK
				|| e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (p.getItemInHand().getType() == Material.GOLD_NUGGET) {
				openShop(p);
			}
		}
	}

	@EventHandler
	public void invclick(InventoryClickEvent e) {
		Entity p = e.getWhoClicked();
		if (p instanceof Player) {
			if (e.getInventory().getTitle() == ChatColor.GREEN + "Shop" || !(e.getInventory() == ((HumanEntity) p).getInventory())) {
				e.setCancelled(true);
				switch (e.getCurrentItem().getType()) {
				case BLAZE_POWDER:
					new BukkitRunnable() {
						int t = 6;
						double t2 = 6.5;

						public void run() {
							t--;
							t2--;
							for (final Player online : Bukkit.getOnlinePlayers()) {
								Titles.sendFullTitle(online, 20, 20, 20,
										ChatColor.RED + "Nuke activating in " + ChatColor.GOLD + t,
										ChatColor.GOLD + "Nuke Activated by " + p.getName());
								online.playSound(online.getLocation(), Sound.NOTE_STICKS, 4f, 4f);
								if (t2 == 5.5) {
									online.playSound(online.getLocation(), Sound.NOTE_BASS, 4f, 4f);
								}
								if (t2 == 4.5) {
									online.playSound(online.getLocation(), Sound.NOTE_BASS, 4f, 4f);
								}
								if (t2 == 3.5) {
									online.playSound(online.getLocation(), Sound.NOTE_BASS, 4f, 4f);
								}
								if (t2 == 2.5) {
									online.playSound(online.getLocation(), Sound.NOTE_BASS, 4f, 4f);
								}
								if (t2 == 1.5) {
									online.playSound(online.getLocation(), Sound.NOTE_BASS, 4f, 4f);
								}
								if (t2 == 0.5) {
									online.playSound(online.getLocation(), Sound.NOTE_BASS, 4f, 4f);
								}
							}
							if (t <= 0) {
								this.cancel();
								for (Player online : Bukkit.getOnlinePlayers()) {
									Titles.sendFullTitle(online, 20, 20, 20,
											ChatColor.RED.toString() + ChatColor.BOLD + "Nuke Activated.",
											ChatColor.GOLD + "Nuke Activated by " + p.getName());
									online.playSound(online.getLocation(), Sound.EXPLODE, 2f, 2f);
									if (online != p) {
										online.damage(1);
										String kills = ChatColor.GRAY + "[" + ChatColor.RED + "PB"+ ChatColor.GRAY + "]" + online.getName() + ChatColor.GOLD + " Has fallen casualty to "
												+ ChatColor.RESET + p.getName() + ChatColor.GOLD + "'s Nuke.";
										Bukkit.broadcastMessage(kills);
									}
								}
							}
						}
					}.runTaskTimer(this, 20, 20);
				}
			}
		}
	}
}
