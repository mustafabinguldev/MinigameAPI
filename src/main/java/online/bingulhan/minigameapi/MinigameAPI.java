package online.bingulhan.minigameapi;

import lombok.Getter;
import online.bingulhan.minigameapi.example.GameTest;
import online.bingulhan.minigameapi.game.GameVariant;
import org.bukkit.plugin.java.JavaPlugin;

public final class MinigameAPI extends JavaPlugin {

    @Getter
    private static MinigameAPI instance;


    @Override
    public void onEnable() {

        instance=this;

   //     GameVariant gameVariant = new GameTest("Test", this, true);

        getServer().getLogger().info("API is load");

    }

    @Override
    public void onDisable() {

    }

}
