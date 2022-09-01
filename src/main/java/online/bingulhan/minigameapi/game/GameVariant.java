package online.bingulhan.minigameapi.game;

import lombok.Getter;
import online.bingulhan.minigameapi.game.objects.GamePlayer;
import online.bingulhan.minigameapi.game.status.StatusVariant;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;

/**
 * @author BingulHan
 */
@Getter
public abstract class GameVariant {

    /**
     * This is game name.
     * Example: Duel, Bedwars
     */
    protected final String name;


    /**
     * This is active plugin.
     */
    protected final JavaPlugin plugin;

    /**
     * This is all state
     */
    protected ArrayList<StatusVariant> statusVariants = new ArrayList<>();

    /**
     * Current State
     */
    protected StatusVariant currentStatus;

    /**
     * Current State index
     */
    protected int currentStatusIndex = 0;

    /**
     * This is player list
     */
    protected HashSet<GamePlayer> players = new HashSet<>();

    /**
     * Create a new Game
     * @param gameName
     * @param plugin
     */
    public GameVariant(String gameName, JavaPlugin plugin, boolean init) {
        this.name=gameName;
        this.plugin=plugin;
        if (init) init();
    }

    public final void init() {
        onEnable();
    }

    public final void stop() {
        reset();

        getPlayers().stream().forEach(g -> g.getScoreboard().stop());
        onDisable();

    }

    protected abstract void reset();


    public final void nextState() {
          //  currentStateIndex = currentStateIndex+1;
    //        getPlugin().getLogger().info("New Index: "+ currentStatusIndex);
            setStatus(currentStatusIndex +1);
    }

    public final void setStatus(int index) {
        if (index> statusVariants.size()-1) {
            stop();
        }else{
            currentStatusIndex = statusVariants.indexOf(statusVariants.get(index));
       //    getPlugin().getLogger().info("New State: "+ currentStatusIndex);

            currentStatus =  statusVariants.get(index).clone(true);
        }
    }

    protected abstract void onEnable();

    protected abstract void onDisable();

    @Override
    public int hashCode() {
        return Objects.hashCode("game"+name);
    }

    public final boolean addPlayer(Player player, Class<? extends GamePlayer> clazz) {
        try {
            return players.add((GamePlayer) clazz.getConstructors()[0].newInstance(player.getName(), this));
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public final boolean removePlayer(Player player) {
        if (players.stream().anyMatch(p -> p.getName().equals(player.getName()))) {
            return players.remove(players.stream().filter(p -> p.getName().equals(player.getName())).findAny());
        }

        return false;
    }

    public final void addStatus(StatusVariant variant) {
        statusVariants.add(variant);
    }


}
