package online.bingulhan.minigameapi.game.objects;

import lombok.Getter;
import online.bingulhan.minigameapi.game.GameVariant;

@Getter
public class GameData {

    protected GameVariant game;

    public GameData(GameVariant game) {
        this.game=game;
    }

}
