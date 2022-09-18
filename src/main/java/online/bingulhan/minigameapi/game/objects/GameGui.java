package online.bingulhan.minigameapi.game.objects;

import lombok.Getter;
import online.bingulhan.minigameapi.MinigameAPI;
import online.bingulhan.minigameapi.game.objects.lambs.RunInteraction;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.logging.Handler;

@Getter
public class GameGui {


   private String title;

  private  int timer = -1;

   private OfflinePlayer player;

    private int maxSlot;

    private Listener listener = new GuiListener(this);

    private Inventory gui;

    private int id;

    private  boolean isClose = false;

    private  boolean isCancelledClick = false;

    private HashSet<GuiItem> items = new HashSet<>();

    public GameGui(OfflinePlayer player, String title, HashSet<GuiItem> items, int timer, int slot, boolean isCancelled) {
        this.player=player;
        this.title=title;
        this.items=items;
        this.maxSlot=slot;
        this.timer=timer;
        this.isCancelledClick=isCancelled;
    }


    public final void onEnable() {
        id = new Random().nextInt(100000);

        gui = MinigameAPI.getInstance().getServer().createInventory(null, maxSlot, ChatColor.translateAlternateColorCodes('&', title));

        for (GuiItem item : items) {
            ItemStack i = item.getItem();
            gui.setItem(item.getSlot(), item.getItem());
        }

        gui.setMaxStackSize(id);

        if (player.getPlayer().isOnline()){
            player.getPlayer().closeInventory();

            MinigameAPI.getInstance().getServer().getScheduler().runTaskLater(MinigameAPI.getInstance(), () -> {
                player.getPlayer().openInventory(gui);
            }, 4);
        }

        MinigameAPI.getInstance().getServer().getPluginManager().registerEvents(this.listener, MinigameAPI.getInstance());

    }

    public final void onDisable() {

        if (isClose) return;
        isClose=true;

        if (player.getPlayer().isOnline()){
            player.getPlayer().closeInventory();
        }

        HandlerList.unregisterAll(this.listener);
    }

    public static class GuiItem {

        @Getter
        private ItemStack item;


        @Getter
        private int id;

        @Getter
        private int slot = 0;

        RunInteraction interaction;

        public GuiItem(ItemStack item, int slot, RunInteraction runInteraction) {
            this.item=item;
            this.slot=slot;
            this.interaction=runInteraction;

            id = new Random().nextInt(5000);
        }


    }
    public class GuiListener implements Listener {

        @Getter
        private GameGui gui;


        public GuiListener(GameGui gui) {
            this.gui=gui;

        }
        @EventHandler
        public void onOpen(InventoryOpenEvent e) {
            if (gui.getId()==e.getInventory().getMaxStackSize()) {
                if (gui.getTimer() != -1) {
                    MinigameAPI.getInstance().getServer().getScheduler().runTaskLater(MinigameAPI.getInstance(), () -> {
                        gui.onDisable();
                    }, 20 * gui.getTimer());
                }

            }
        }

        @EventHandler
        public void onClose(InventoryCloseEvent e) {
            if (gui.getId()==e.getInventory().getMaxStackSize()) {
                gui.onDisable();
            }
        }

        @EventHandler
        public void onClick(InventoryClickEvent e) {
            if (gui.getId()==e.getInventory().getMaxStackSize()) {
                if (gui.isCancelledClick) e.setCancelled(true);
                if (e.getCurrentItem()==null) return;
                if (e.getCurrentItem().getItemMeta()==null) return;

        
                for (GuiItem item : getGui().getItems()) {
                    ItemStack clickedItem = e.getCurrentItem();
                    if (clickedItem.getType().equals(item.getItem().getType()) && clickedItem.getItemMeta().getDisplayName().equals(item.getItem().getItemMeta().getDisplayName())) {
                
                        Player p = (Player) e.getWhoClicked();
                        item.interaction.run(p);
                    }
                }

            }
        }

        @EventHandler
        public void onQuit(PlayerQuitEvent e) {
            if (e.getPlayer().getName().equals(gui.getPlayer().getName())) {
                gui.onDisable();
            }
        }

    }

    public static class GuiBuilder{

        private String title;
        private HashSet<GuiItem> items = new HashSet<>();
        private int timer = -1;
        private OfflinePlayer player;

        private int maxSlot = 9;

        private boolean isClickCancelled = true;

        public GuiBuilder setTitle(String title) {
            this.title=title;
            return this;
        }

        public GuiBuilder setPlayer(Player player) {
            this.player=player;
            return this;
        }

        public GuiBuilder addItem(ItemStack item, Integer slot, RunInteraction interaction) {

            items.add(new GuiItem(item, slot, interaction));

            return this;
        }

        public GuiBuilder setTimer(int timer) {
            this.timer = timer;
            return this;
        }

        public GuiBuilder setClickCancelled(boolean cancel) {
            this.isClickCancelled = cancel;
            return this;
        }

        /**
         * ExAMPLE 9, 18, 27, 36
         */
        public GuiBuilder setMaxSlot(int slot) {
            this.maxSlot=slot;
            return this;
        }
        public GameGui build() {

            return new GameGui(player, title, items,timer,maxSlot, isClickCancelled);
        }


    }
}
