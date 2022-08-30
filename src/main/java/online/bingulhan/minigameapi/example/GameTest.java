package online.bingulhan.minigameapi.example;

import online.bingulhan.minigameapi.example.status.starting.StartingStatus;
import online.bingulhan.minigameapi.example.status.waiting.WaitingStatus;
import online.bingulhan.minigameapi.game.GameVariant;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class GameTest extends GameVariant {
    /**
     * Create a new Game
     *
     * @param gameName
     * @param plugin
     * @param init
     */
    public GameTest(String gameName, JavaPlugin plugin, boolean init) {
        super(gameName, plugin, init);
    }

    @Override
    protected void reset() {

    }

    @Override
    protected void onEnable() {
        getPlugin().getLogger().info("Game is ready.");
        addStatus(new WaitingStatus("Waiting", this));
        addStatus(new StartingStatus("Starting", this));
        setStatus(0);
    }

    @Override
    protected void onDisable() {
        getCurrentStatus().getAlivePlayers().stream().forEach(p -> p.sendMessage(ChatColor.RED+"Game stop"));
        getPlugin().getServer().broadcastMessage(ChatColor.GREEN+"Game stop.");
    }


}
