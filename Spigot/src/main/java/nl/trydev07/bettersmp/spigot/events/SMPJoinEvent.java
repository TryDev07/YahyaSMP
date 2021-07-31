package nl.trydev07.bettersmp.spigot.events;

import nl.trydev07.bettersmp.spigot.game.handler.TeamHandler;
import nl.trydev07.bettersmp.spigot.game.handler.UserHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class SMPJoinEvent extends Event implements Cancellable {

    private static HandlerList handlerList = new HandlerList();
    private boolean cancel;
    private Player player;
    private TeamHandler teamHandler;
    private UserHandler userHandler;

    public SMPJoinEvent(Player player) {
        this.player = player;
        this.cancel = false;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    @Override
    public boolean isCancelled() {
        return cancel;
    }

    @Override
    public void setCancelled(boolean b) {
        this.cancel = b;
    }

    public Player getPlayer() {
        return player;
    }

    public void setTeamHandler(TeamHandler teamHandler) {
        this.teamHandler = teamHandler;
    }

    public TeamHandler getTeamHandler() {
        return teamHandler;
    }

    public UserHandler getUserHandler() {
        return userHandler;
    }

    public void setUserHandler(UserHandler userHandler) {
        this.userHandler = userHandler;
    }
}
