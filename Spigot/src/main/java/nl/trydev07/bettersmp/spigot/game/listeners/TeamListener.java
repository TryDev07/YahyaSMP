package nl.trydev07.bettersmp.spigot.game.listeners;

import nl.trydev07.bettersmp.api.data.User;
import nl.trydev07.bettersmp.api.utils.text.TextUtil;
import nl.trydev07.bettersmp.spigot.SMP;
import nl.trydev07.bettersmp.spigot.game.handler.UserHandler;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerInteractEvent;

public class TeamListener implements Listener {

    @EventHandler
    public void on(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player) {
            if (event.getDamager() instanceof Player) {
                UserHandler userHandler = SMP.getInstance().getUserHandler();
                Player player = (Player) event.getEntity();
                Player damaged = (Player) event.getDamager();
                if (userHandler.getTeam(player.getUniqueId()).equalsIgnoreCase("teamless") || userHandler.getTeam(damaged.getUniqueId()).equalsIgnoreCase("teamless"))
                    event.setCancelled(true);

                if (userHandler.getTeam(player.getUniqueId()).equalsIgnoreCase(userHandler.getTeam(damaged.getUniqueId()))) {
                    damaged.sendMessage(TextUtil.coloredText("&cYou can't damage your own team"));
                    event.setCancelled(true);
                }
            } else if (event.getDamager() instanceof Arrow) {
                UserHandler userHandler = SMP.getInstance().getUserHandler();
                Player player = (Player) event.getEntity();
                Arrow arrow = (Arrow) event.getDamager();
                if(arrow.getShooter() instanceof Player) {
                    Player damaged = (Player) arrow.getShooter();
                    if (userHandler.getTeam(player.getUniqueId()).equalsIgnoreCase("teamless") || userHandler.getTeam(damaged.getUniqueId()).equalsIgnoreCase("teamless"))
                        event.setCancelled(true);

                    if (userHandler.getTeam(player.getUniqueId()).equalsIgnoreCase(userHandler.getTeam(damaged.getUniqueId()))) {
                        damaged.sendMessage(TextUtil.coloredText("&cYou can't damage your own team"));
                        event.setCancelled(true);
                    }
                }
            }
        }
    }


    @EventHandler
    public void on(BlockBreakEvent event) {
        UserHandler userHandler = SMP.getInstance().getUserHandler();
        Player player = event.getPlayer();
        if (userHandler.getRole(player.getUniqueId()).equals(User.Roles.NEWBIE)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void on(BlockPlaceEvent event) {
        UserHandler userHandler = SMP.getInstance().getUserHandler();
        Player player = event.getPlayer();
        if (userHandler.getRole(player.getUniqueId()).equals(User.Roles.NEWBIE)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void on(PlayerInteractEvent event) {
        UserHandler userHandler = SMP.getInstance().getUserHandler();
        Player player = event.getPlayer();
        if (userHandler.getRole(player.getUniqueId()).equals(User.Roles.NEWBIE)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void on(EntityExplodeEvent event) {
        event.setCancelled(true);
    }

}
