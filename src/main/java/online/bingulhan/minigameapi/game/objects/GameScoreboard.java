package online.bingulhan.minigameapi.game.objects;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import online.bingulhan.minigameapi.MinigameAPI;
import online.bingulhan.minigameapi.game.objects.task.ScoreboardTask;
import online.bingulhan.minigameapi.game.status.StatusVariant;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Getter
@Setter
public abstract class GameScoreboard {

    @Getter(AccessLevel.NONE)
    private String ownerName;

    protected Player player;

    private Scoreboard scoreboard;

    private Objective objective;

    private ScoreboardTask task;

    private String title;
    /**
     * Status to which it is connected
     */
    private StatusVariant status;

    private List<String> lines = new ArrayList<>();


    public GameScoreboard(Player player, StatusVariant status) {
        this.ownerName=player.getName();
        this.player=player;
        this.status=status;


        start();
    }

    public GameScoreboard(StatusVariant status) {
        this.status=status;
    }

    public final void setTitle(String title) {
        this.title=title;
    }

    public abstract void onEnable();

    public abstract void onDisable();

    public final void onTick() {
        shower();
    }

    public abstract GameScoreboard clone(Player player);


    public final void start() {
        onEnable();

        this.task = new ScoreboardTask(this);
        this.task.runTaskTimer(getStatus().getGameVariant().getPlugin(), 1L, 25L);

    }

    public final void stop() {
        task.cancel();
        objective.unregister();
        player.setScoreboard(getStatus().getGameVariant().getPlugin().getServer().getScoreboardManager().getNewScoreboard());
    }

    private final void shower() {
        if (title == null) return;
        if (scores().size()<1) return;
        creator();

        player.setScoreboard(scoreboard);
    }

    public abstract List<String> scores();


    private final void creator() {
            if (this.scoreboard == null) {
                this.scoreboard = MinigameAPI.getInstance().getServer().getScoreboardManager().getNewScoreboard();
            }

            if (this.objective == null) {
                this.objective = scoreboard.registerNewObjective(title, title);
                this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
            }else {
                //Reset scores
                scoreboard.getEntries().stream().forEach(s -> {
                    scoreboard.resetScores(s);
                });
            }

            int index = 0;

            for (String l : scores()) {
                Score score = objective.getScore(l);
                score.setScore(index);
                index++;
            }
        }




}
