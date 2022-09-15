Owner [@BingulHan](https://github.com/BingulHan)
Support [Discord](discord.gg/NUR9k9H6ab)

What is this?
Simple handy mini game api

Maven:
Repository:
```
<repository>
           <id>jitpack.io</id>
           <url>https://jitpack.io</url>
</repository>
```

Dependecy:
```
<dependency>
	    <groupId>com.github.BingulHan</groupId>
	    <artifactId>MinigameAPI</artifactId>
	    <version>0.1.4</version>
</dependency>
```

How can I create a game? 
For Example [GameTest.java](https://github.com/BingulHan/MinigameAPI/blob/master/src/main/java/online/bingulhan/minigameapi/example/GameTest.java)

How do I create status ?
For example: [WaitingStatus.java](https://github.com/BingulHan/MinigameAPI/blob/master/src/main/java/online/bingulhan/minigameapi/example/status/waiting/WaitingStatus.java)

How do I create scoreboard ? 
For example: [WaitingScoreboard.java](https://github.com/BingulHan/MinigameAPI/blob/master/src/main/java/online/bingulhan/minigameapi/example/status/waiting/WaitingScoreboard.java)

How do I create gui? 
For example: 
```
      new GameGui.GuiBuilder().addItem(new ItemBuilder(Material.STONE).setDisplayName(ChatColor.RED+"Click me").build(), 4, (p) -> {
                p.sendMessage(ChatColor.RED+"You clicked stone!");
            }).setTimer(10).setPlayer(player).setTitle("Test Gui").build().onEnable();
            
```

How do I Listener in Status?
For example: [WaitingListener.java](https://github.com/BingulHan/MinigameAPI/blob/master/src/main/java/online/bingulhan/minigameapi/example/status/waiting/WaitingListener.java)

