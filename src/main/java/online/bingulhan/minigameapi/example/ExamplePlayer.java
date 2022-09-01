package online.bingulhan.minigameapi.example;

import lombok.Getter;
import online.bingulhan.minigameapi.game.GameVariant;
import online.bingulhan.minigameapi.game.objects.GamePlayer;

public class ExamplePlayer extends GamePlayer {

    @Getter
    private int coin = 1000;

    public ExamplePlayer(String name, GameVariant game) {
        super(name, game);
    }


}
