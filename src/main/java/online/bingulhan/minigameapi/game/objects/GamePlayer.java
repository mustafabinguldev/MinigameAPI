package online.bingulhan.minigameapi.game.objects;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import online.bingulhan.minigameapi.game.GameVariant;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.Optional;

@Getter
@Setter
public class GamePlayer {

    @Setter(AccessLevel.NONE)
    protected String name;

    protected GameVariant game;

    protected boolean isSpectator=false;

    protected GameScoreboard scoreboard;

    protected boolean isElemineted=false;

    public GamePlayer(String name, GameVariant game) {
        this.name=name;
        this.game=game;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode("player"+name);
    }


    public final Optional<Player> toPlayer() {
        OfflinePlayer offlinePlayer = getGame().getPlugin().getServer().getOfflinePlayer(name);

        if (offlinePlayer.isOnline()) {
            return Optional.of(offlinePlayer.getPlayer());
        }

        return Optional.empty();
    }
}
