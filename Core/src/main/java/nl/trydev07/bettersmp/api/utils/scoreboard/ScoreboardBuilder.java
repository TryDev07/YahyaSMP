package nl.trydev07.bettersmp.api.utils.scoreboard;

import nl.trydev07.bettersmp.api.utils.text.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import java.util.*;

public interface ScoreboardBuilder extends Listener {

    Map<UUID, Scoreboard> scoreboards = new HashMap<>();

    void updateBoard(Scoreboard scoreboard);

    String getTitle();

    boolean isJoinEvent();

    boolean isQuitEvent();

    default void run(Plugin plugin) {
        Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(plugin, () -> {
            for (Scoreboard scoreboard : scoreboards.values()) {
                updateBoard(scoreboard);
            }
        }, 0, 20);
    }

    default String centerText(List<String> input, int row) {
        return TextUtil.centerToScoreboard(input.get(row), input.toArray(new String[0]));
    }

    default void registerPlayer(Player player) {
        Scoreboard scoreboard = new Scoreboard(player);
        scoreboard.updateTitle(getTitle());
        scoreboards.put(player.getUniqueId(), scoreboard);
    }

    @EventHandler
    default void onJoin(PlayerJoinEvent event) {
        System.out.println(isJoinEvent());
        if (isJoinEvent()) {
            Player player = event.getPlayer();
            Scoreboard scoreboard = new Scoreboard(player);
            scoreboard.updateTitle(getTitle());
            scoreboards.put(player.getUniqueId(), scoreboard);
            System.out.println(scoreboard);

        }
    }

    @EventHandler
    default void onLeave(PlayerQuitEvent event) {
        if (isQuitEvent()) {
            Player player = event.getPlayer();

            Scoreboard board = scoreboards.remove(player.getUniqueId());
            if (board != null) {
                board.delete();
            }
        }
    }

}
