package online.bingulhan.minigameapi.game.objects;

import lombok.Getter;
import lombok.Setter;
import online.bingulhan.minigameapi.game.objects.task.ScoreboardTask;
import online.bingulhan.minigameapi.game.status.StatusVariant;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public abstract class AbstractScoreboard {

    public abstract String getTitle();

    public abstract ArrayList<String> getLines();

    @Getter
    private StatusVariant statusVariant;

    @Getter
    private Scoreboard scoreboard;

    @Getter
    private Objective objective;

    @Getter
    private HashSet<OfflinePlayer> players;

    @Getter
    @Setter
    private boolean fronzee;

    @Getter
    @Setter
    private ScoreboardTask task;

    @Getter
    @Setter
    private HashSet<GameTeam> teams = new HashSet<>();


    public final void createScoreboard() {

        fronzee = false;

        this.scoreboard = statusVariant.getGameVariant().getPlugin().getServer().getScoreboardManager().getNewScoreboard();

        ArrayList<String> lines;
        String title;

        if ((getLines()!=null)) {
            lines = new ArrayList<>(getLines());
        }else lines = new ArrayList<>();

        if (getTitle()!=null) {
            title = getTitle();
        }else title = "Default";

        List<String> colorCodeOptions = new ArrayList<>();
        for (ChatColor color : ChatColor.values()) {
            if (color.isFormat()) {
                continue;
            }

            for (ChatColor secondColor : ChatColor.values()) {
                if (secondColor.isFormat()) {
                    continue;
                }

                String option = "" + secondColor;

                if (color != secondColor && !colorCodeOptions.contains(option)) {
                    colorCodeOptions.add(option);

                    if (colorCodeOptions.size() == 15) break;
                }
            }
        }

        objective = scoreboard.registerNewObjective(color(title), color(title));
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        int index = 0;
        for (String line : lines) {
            Team team = scoreboard.registerNewTeam("line"+index);

            team.addEntry(colorCodeOptions.get(index));
            objective.getScore(colorCodeOptions.get(index)).setScore(lines.size()-index);

            if (color(line).length()>16) {
                team.setPrefix(color(line).substring(0,16));
            }else {
                team.setPrefix(color(line));
            }

            index++;
        }


        task = new ScoreboardTask(this);
        task.runTaskTimer(getStatusVariant().getGameVariant().getPlugin(), 1L, 15L);

        onEnable();
    }

    public AbstractScoreboard(StatusVariant statusVariant) {
        this.statusVariant = statusVariant;

        players = new HashSet<>();

        createScoreboard();

    }

    public final void update() {

        for (GamePlayer player : getStatusVariant().getGameVariant().getPlayers()) {
            if (player.toPlayer().isPresent()) {
                players.add(player.toPlayer().get());
            }
        }

        for (OfflinePlayer player : getPlayers()) {
        //    player.getPlayer().sendMessage("scoreborad eklendi");
            player.getPlayer().setScoreboard(scoreboard);
        }

        for (GameTeam team : teams) {

            Team t;
            if (scoreboard.getTeam(team.getName())==null) {
                t = scoreboard.registerNewTeam(team.getName());
                t.setPrefix(color(team.getPrefix()));
                t.setSuffix(color(team.getSuffix()));
            }else{
                t = scoreboard.getTeam(team.getName());
            }
            for (OfflinePlayer player : getPlayers()) {
                if (!t.getPlayers().stream().anyMatch(p -> p.getName().equals(player.getName()))) {
                    t.addEntry(player.getName());
                }
            }
        }

        for (Team team : getScoreboard().getTeams()) {
            if (team.getName().contains("line")) {
                String t = getLines().get(Integer.parseInt(team.getName().substring(4)));

                if (color(t).length()>16) {
                    team.setPrefix(color(t).substring(0,16));
                }else {
                    team.setPrefix(color(t));
                }
            }
        }

        onUpdate();


}
    public final boolean isSameTeam(GamePlayer player, GamePlayer playerTwo) {
        for (GameTeam team : teams) {
            if (team.getPlayers().stream().anyMatch(p -> p.getName().equals(player.getName())) && team.getPlayers().stream().anyMatch(p -> p.getName().equals(playerTwo.getName()))) {
                return true;
            }
        }

        return false;
    }

    public final void stop() {

       // scoreboard = null;
        task.cancel();

        fronzee = true;

        scoreboard.getTeams().stream().forEach(t -> t.unregister());

        objective.unregister();

        setFronzee(true);

        for (OfflinePlayer player : getPlayers()) {
            //        player.getPlayer().sendMessage("stop cekildi!");
            getPlayers().remove(player);
        }

        onDisable();

        try {
            getTeams().stream().forEach(t -> {
                t.delete();
                getTeams().remove(t);
            });

        }catch (Exception exception ){

        }

    }


    public abstract void onEnable();
    public abstract void onDisable();
    public abstract void onUpdate();

    public final String color(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }



}
