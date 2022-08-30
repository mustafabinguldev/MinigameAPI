package online.bingulhan.minigameapi.game.objects.task;

import online.bingulhan.minigameapi.game.objects.GameScoreboard;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;

public class ScoreboardTask extends BukkitRunnable {

    private GameScoreboard scoreboard;

    public ScoreboardTask(GameScoreboard linkedScoreboard) {
        this.scoreboard=linkedScoreboard;
    }

    @Override
    public void run() {
        scoreboard.onTick();
    }
}
