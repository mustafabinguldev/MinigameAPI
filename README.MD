Owner [a link](https://github.com/BingulHan)
Support 

What is this?
Simple handy mini game api

How can I create a game? 
For Example [a link](https://github.com/BingulHan/MinigameAPI/blob/master/src/main/java/online/bingulhan/minigameapi/example/GameTest.java)

How do I create status ?
For example: [a link](https://github.com/BingulHan/MinigameAPI/blob/master/src/main/java/online/bingulhan/minigameapi/example/status/waiting/WaitingStatus.java)

How do I create scoreboard ? 
For example: [a link](https://github.com/BingulHan/MinigameAPI/blob/master/src/main/java/online/bingulhan/minigameapi/example/status/waiting/WaitingScoreboard.java)

How do I create gui? 
For example: 
```
      new GameGui.GuiBuilder().addItem(new ItemBuilder(Material.STONE).setDisplayName(ChatColor.RED+"Click me").build(), 4, (p) -> {
                p.sendMessage(ChatColor.RED+"You clicked stone!");
            }).setTimer(10).setPlayer(player).setTitle("Test Gui").build().onEnable();
            
```

How do I Listener in Status?
For example: [a link](https://github.com/BingulHan/MinigameAPI/blob/master/src/main/java/online/bingulhan/minigameapi/example/status/waiting/WaitingListener.java)

