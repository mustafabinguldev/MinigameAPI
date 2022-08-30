package online.bingulhan.minigameapi.game.status;

import lombok.Getter;
import online.bingulhan.minigameapi.game.objects.GamePlayer;
import org.bukkit.event.Listener;

import java.util.Objects;
import java.util.Optional;

@Getter
public class StatusListener implements Listener {


    protected String name;
    protected StatusVariant status;


    public StatusListener(StatusVariant state, String name) {
        this.status=state;
        this.name=name;
    }


    protected final boolean isGamePlayer(String playerName){
        return getStatus().getGameVariant().getPlayers().stream().anyMatch(p -> playerName.equals(p.getName()));
    }

    protected final Optional<GamePlayer> getPlayerData(String playerName){
        if (isGamePlayer(playerName)) {
            return getStatus().getGameVariant().getPlayers().stream().filter(p -> playerName.equals(p.getName())).findAny();
        }else {
            return Optional.empty();
        }
    }

    @Override
    public int hashCode() {
        return Objects.hashCode("listenerv"+1+name);
    }
}
