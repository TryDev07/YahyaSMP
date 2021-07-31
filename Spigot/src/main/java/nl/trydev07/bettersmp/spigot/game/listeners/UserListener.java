package nl.trydev07.bettersmp.spigot.game.listeners;

import nl.trydev07.bettersmp.api.data.User;
import nl.trydev07.bettersmp.api.utils.nametag.Name;
import nl.trydev07.bettersmp.spigot.SMP;
import nl.trydev07.bettersmp.spigot.events.SMPJoinEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class UserListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void on(PlayerLoginEvent event) {
        SMP.getInstance().getUserHandler().loadUser(event.getPlayer().getUniqueId());
        System.out.println("Is loaded: " + SMP.getInstance().getUserHandler().isLoaded(event.getPlayer().getUniqueId()));
        if (SMP.getInstance().getUserHandler().isLoaded(event.getPlayer().getUniqueId())) {
            SMPJoinEvent smpJoinEvent = new SMPJoinEvent(event.getPlayer());
            smpJoinEvent.setUserHandler(SMP.getInstance().getUserHandler());
            smpJoinEvent.setTeamHandler(SMP.getInstance().getTeamHandler());
            Bukkit.getPluginManager().callEvent(smpJoinEvent);
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void on(PlayerQuitEvent event) {
        if (SMP.getInstance().getUserHandler().getRole(event.getPlayer().getUniqueId()).equals(User.Roles.NEWBIE)) {
            return;
        }
//        Name name = SMP.getInstance().getTeamHandler().getNametag(SMP.getInstance().getUserHandler().getTeam(event.getPlayer().getUniqueId()));
//        name.getTeam().removeEntry(event.getPlayer().getName());
        SMP.getInstance().getNameHandler().unregisterUser(event.getPlayer());
        SMP.getInstance().getUserHandler().saveUser(event.getPlayer().getUniqueId());
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void on(SMPJoinEvent event) {
        Player player = event.getPlayer();


        SMP.getInstance().getNameHandler().registerUser(player);
        /*if(SMP.getInstance().getUserHandler().getRole(player.getUniqueId()).equals(User.Roles.NEWBIE))
            return;
        Name name = SMP.getInstance().getTeamHandler().getNametag(SMP.getInstance().getUserHandler().getTeam(player.getUniqueId()));
        name.getTeam().addEntry(player.getName());*/
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void on(PlayerJoinEvent event) {
        event.setJoinMessage(null);
    }


}
