package online.bingulhan.minigameapi.game.status;

import lombok.Getter;
import online.bingulhan.minigameapi.game.GameVariant;
import online.bingulhan.minigameapi.game.objects.GamePlayer;
import online.bingulhan.minigameapi.game.objects.GameScoreboard;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@Getter
public abstract class StatusVariant {
    /**
     * This is variant name.
     * Example: Ingame, Waiting
     */
    protected final String name;

    protected ArrayList<BukkitTask> tasks = new ArrayList<>();

    protected HashSet<StatusListener> statusListeners = new HashSet<>();

    private final GameVariant gameVariant;

    public StatusVariant(String name, GameVariant variant, boolean init) {
        this.name=name;
        this.gameVariant=variant;

        if (init) init();
    }

    /**
     * list object
     */
    public StatusVariant(String name, GameVariant variant) {
        this.name=name;
        this.gameVariant=variant;

    }

    protected abstract void reset();

    protected abstract void onEnable();

    /**
     * Use in state only, use stop function in listener
     */
    protected abstract void onDisable();

    public abstract GameScoreboard getScoreboard();

    public final void injectScoreboard(boolean repeat) {
        if (getScoreboard() == null) return;

        for (GamePlayer playerData : gameVariant.getPlayers()) {
            if (playerData.getScoreboard() == null) {
                if (playerData.toPlayer().isPresent()) {
                    playerData.setScoreboard(getScoreboard().clone(playerData.toPlayer().get()));
                }
            }
        }
    }

    private final void injectScoreboard() {
        if (getScoreboard() == null) return;
        
        for (GamePlayer playerData : gameVariant.getPlayers()) {
            if (playerData.getScoreboard() == null) {
                if (playerData.toPlayer().isPresent()) {
                    playerData.setScoreboard(getScoreboard().clone(playerData.toPlayer().get()));
                }
            }else{
                playerData.getScoreboard().stop();
                playerData.setScoreboard(null);
            }
        }
        injectScoreboard(true);
    }

    public final void addListener(StatusListener listener) {
        statusListeners.add(listener);
    }

    public abstract StatusVariant clone(Boolean init);

    public final void init() {
        onEnable();
        getStatusListeners().stream().forEach(l -> getGameVariant().getPlugin().getServer().getPluginManager().registerEvents(l, getGameVariant().getPlugin()));
        injectScoreboard();
    }

    public final void stop() {
        getTasks().stream().forEach(t -> t.cancel());
        getStatusListeners().stream().forEach(l -> HandlerList.unregisterAll(l));
        onDisable();
    }

    /**
     * This is Timer adder
     */
    public void addTimer(Runnable task, int duracition) {
        tasks.add(getGameVariant().getPlugin().getServer().getScheduler().runTaskLater(getGameVariant().getPlugin(), task, 20 * duracition));
    }

    public void addTimer(Runnable task, long tick) {
        tasks.add(getGameVariant().getPlugin().getServer().getScheduler().runTaskLater(getGameVariant().getPlugin(), task, tick));
    }
    @Override
    public int hashCode() {
        return Objects.hashCode("state"+name);
    }

    public final List<Player> getAlivePlayers() {
        ArrayList<Player> players = new ArrayList<>();
        for (GamePlayer data : getGameVariant().getPlayers()) {
            OfflinePlayer offlinePlayer = getGameVariant().getPlugin().getServer().getOfflinePlayer(data.getName());
            if (offlinePlayer.isOnline()) {
                players.add(offlinePlayer.getPlayer());
            }
        }
        return players;
    }

    /**
     * Sends a message to all players in the game (Note: ChatColor is supported.)
     */
    public final void sendMessageAll(String text) {
        getAlivePlayers().stream().forEach(p -> p.sendMessage(ChatColor.translateAlternateColorCodes('&', text)));
    }
}
