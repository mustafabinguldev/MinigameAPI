package online.bingulhan.minigameapi.example.status.starting;

import online.bingulhan.minigameapi.game.objects.GameScoreboard;
import online.bingulhan.minigameapi.game.status.StatusVariant;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class StartingScoreboard extends GameScoreboard {

    public StartingScoreboard(Player player, StatusVariant status) {
        super(player, status);
    }

    public StartingScoreboard(StatusVariant status) {
        super(status);
    }

    @Override
    public void onEnable() {
        setTitle("Starting...");
    }

    @Override
    public void onDisable() {

    }

    @Override
    public GameScoreboard clone(Player player) {
        return new StartingScoreboard(player, getStatus());
    }

    @Override
    public List<String> scores() {
        return Arrays.asList("Bekleyiniz..");
    }
}
