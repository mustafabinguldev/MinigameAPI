package online.bingulhan.minigameapi.example.status.waiting;

import online.bingulhan.minigameapi.game.objects.GameScoreboard;
import online.bingulhan.minigameapi.game.status.StatusVariant;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class WaitingScoreboard extends GameScoreboard {

    public WaitingScoreboard(Player player, StatusVariant statusVariant) {
        super(player, statusVariant);
    }
    public WaitingScoreboard(StatusVariant statusVariant) {
        super(statusVariant);
    }

    @Override
    public void onEnable() {
        setTitle("Test");
    }

    @Override
    public void onDisable() {

    }

    @Override
    public GameScoreboard clone(Player player) {
        return new WaitingScoreboard(player, getStatus());
    }

    @Override
    public List<String> scores() {
        return Arrays.asList("Player Health: "+player.getHealth(), "Player Name: "+player.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(player.getName());
    }
}
