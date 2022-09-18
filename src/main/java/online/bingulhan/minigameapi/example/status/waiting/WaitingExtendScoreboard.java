package online.bingulhan.minigameapi.example.status.waiting;

import online.bingulhan.minigameapi.game.objects.AbstractScoreboard;
import online.bingulhan.minigameapi.game.status.StatusVariant;

import java.util.ArrayList;

public class WaitingExtendScoreboard extends AbstractScoreboard {

    public WaitingExtendScoreboard(StatusVariant statusVariant) {
        super(statusVariant);
    }

    @Override
    public String getTitle() {
        return "&e&lEXTEND";
    }

    @Override
    public ArrayList<String> getLines() {
        ArrayList<String> lines = new ArrayList<>();

        lines.add("");
        lines.add("&dPlayers: &f"+getStatusVariant().getAlivePlayers().size());
        lines.add("");
        lines.add("&edarkland.xyz");

        return lines;
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @Override
    public void onUpdate() {

    }
}
