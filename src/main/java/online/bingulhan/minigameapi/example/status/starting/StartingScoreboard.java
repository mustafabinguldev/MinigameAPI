package online.bingulhan.minigameapi.example.status.starting;

import online.bingulhan.minigameapi.game.objects.AbstractScoreboard;
import online.bingulhan.minigameapi.game.objects.GameTeam;
import online.bingulhan.minigameapi.game.status.StatusVariant;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;

public class StartingScoreboard extends AbstractScoreboard {
    public StartingScoreboard(StatusVariant statusVariant) {
        super(statusVariant);
    }

    @Override
    public String getTitle() {
        return "&e&lSTARTING!";
    }

    @Override
    public ArrayList<String> getLines() {

        ArrayList<String> lines = new ArrayList<>();

        lines.add("");
        lines.add("&fPlayers: &c"+getStatusVariant().getAlivePlayers().size());
        lines.add("  ");
        lines.add("&e@BingulHan");
        return lines;
    }

    @Override
    public void onEnable() {
        GameTeam team = new GameTeam("Player", "&c", "&c");

        for (Player player : getStatusVariant().getAlivePlayers()) {
            team.getPlayers().add(player);
        }

        getTeams().add(team);


    }

    @Override
    public void onDisable() {

    }

    @Override
    public void onUpdate() {

    }
}
