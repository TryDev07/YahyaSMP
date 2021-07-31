package nl.trydev07.bettersmp.api.utils.nametag;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public interface NameBuilder extends Listener {

    Map<UUID, Name> nameMap = new HashMap<>();

    String getPrefix(Player player);

    String getSuffix(Player player);

    boolean isJoinEvent();

    boolean isQuitEvent();



    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    default void on(PlayerLoginEvent event) {
        if (isJoinEvent()) {
            Player player = event.getPlayer();


        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    default void on(PlayerQuitEvent event) {
        if (isQuitEvent()) {
            if (nameMap.containsKey(event.getPlayer().getUniqueId()))
                nameMap.get(event.getPlayer().getUniqueId()).getTeam().unregister();
        }
    }

}
