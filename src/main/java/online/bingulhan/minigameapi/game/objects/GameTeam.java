package online.bingulhan.minigameapi.game.objects;

import lombok.Getter;
import org.bukkit.OfflinePlayer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;

@Getter
public class GameTeam {

    private String name;
    private String prefix;
    private String suffix;


    private HashSet<OfflinePlayer> players = new HashSet<>();

    public GameTeam(String name, String prefix, String suffix) {
        this.name = name;
        this.prefix = prefix;
        this.suffix = suffix;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    public void delete() {
        players.stream().forEach(p -> players.remove(p));
    }
}
