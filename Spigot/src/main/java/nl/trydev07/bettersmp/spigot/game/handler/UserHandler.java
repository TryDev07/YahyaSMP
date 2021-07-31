package nl.trydev07.bettersmp.spigot.game.handler;

import nl.trydev07.bettersmp.api.data.User;
import nl.trydev07.bettersmp.api.utils.Logger;
import nl.trydev07.bettersmp.api.utils.text.TextUtil;
import nl.trydev07.bettersmp.spigot.SMP;
import nl.trydev07.bettersmp.spigot.database.DatabaseMethods;
import org.bukkit.Bukkit;

import java.util.*;

public class UserHandler {

    Map<UUID, User> users;

    public UserHandler() {
        this.users = new HashMap<>();
    }

    public void saveUser(UUID uuid) {
        try {
            if (DatabaseMethods.existUser(SMP.getInstance().getDatabase(), uuid)) {
                User user = users.get(uuid);
                DatabaseMethods.updateUser(SMP.getInstance().getDatabase(), uuid, user.getTeam(), user.getRole().toString(), TextUtil.listToString(user.getInvitedList()));
                users.remove(uuid);
            } else {
                User user = users.get(uuid);
                DatabaseMethods.insertUser(SMP.getInstance().getDatabase(), uuid);
                DatabaseMethods.updateUser(SMP.getInstance().getDatabase(), uuid, user.getTeam(), user.getRole().toString(), TextUtil.listToString(user.getInvitedList()));
            }
        } catch (Exception exception) {
            Logger.warning("user " + uuid + " could not be saved. Please ask the developer to check this.");
        }
    }

    public void saveAllOnlineUsers() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            UUID uuid = player.getUniqueId();
            try {
                if (DatabaseMethods.existUser(SMP.getInstance().getDatabase(), uuid)) {
                    User user = users.get(uuid);
                    DatabaseMethods.updateUser(SMP.getInstance().getDatabase(), uuid, user.getTeam(), user.getRole().toString(), TextUtil.listToString(user.getInvitedList()));
                } else {
                    DatabaseMethods.insertUser(SMP.getInstance().getDatabase(), uuid);

                }
            } catch (Exception exception) {
                Logger.warning("user " + uuid + " could not be saved. Please ask the developer to check this.");
            }
        });
    }

    public void loadUser(UUID uuid) {
        this.users.putIfAbsent(uuid, DatabaseMethods.loadUser(SMP.getInstance().getDatabase(), uuid));
        System.out.println(getTeam(uuid));
        System.out.println(getInvites(uuid));
    }

    public void loadAllOnlineUsers() {
        Bukkit.getOnlinePlayers().forEach(Player -> {
            UUID uuid = Player.getUniqueId();
            this.users.putIfAbsent(uuid, DatabaseMethods.loadUser(SMP.getInstance().getDatabase(), uuid));
        });
    }

    public boolean isLoaded(UUID uuid) {
        return this.users.containsKey(uuid);
    }

    /**
     * @param uuid define which user you want to receive the role from.
     * @return role of the user.
     * @throws NullPointerException when user is not properly loaded/found.
     */
    public User.Roles getRole(UUID uuid) {
        try {
            return users.get(uuid).getRole();
        } catch (NullPointerException exception) {
            Logger.warning("user " + uuid + " could not be found, this could happen because it isn't loaded properly.");
        }
        return User.Roles.NEWBIE;
    }

    /**
     * @param uuid define which user you want to receive the role from.
     * @param role define which role you want to give the selected user.
     * @return role of the user.
     * @throws NullPointerException when user is not properly loaded/found.
     */
    public void setRole(UUID uuid, User.Roles role) {
        try {
            users.get(uuid).setRole(role);
        } catch (NullPointerException exception) {
            Logger.warning("user " + uuid + " could not be found, this could happen because it isn't loaded properly.");
        }
    }

    /**
     * @param uuid define which user you want to receive the team from.
     * @return team of the user.
     * @throws NullPointerException when user is not properly loaded/found.
     */
    public String getTeam(UUID uuid) {
        try {
            return users.get(uuid).getTeam();
        } catch (NullPointerException exception) {
            Logger.warning("user " + uuid + " could not be found, this could happen because it isn't loaded properly.");
        }
        return null;
    }

    /**
     * @param uuid define which user you want to receive the role from.
     * @param team define which team you want to give the selected user.
     * @return role of the user.
     * @throws NullPointerException when user is not properly loaded/found.
     */
    public void setTeam(UUID uuid, String team) {
        try {
            users.get(uuid).setTeam(team);
        } catch (NullPointerException exception) {
            Logger.warning("user " + uuid + " could not be found, this could happen because it isn't loaded properly.");
        }
    }

    /**
     * @param uuid define which user you want to receive the role from.
     * @return a Set<String> of the invited the user received.
     * @throws NullPointerException when user doesn't exist and returns null.
     */
    public Set<String> getInvites(UUID uuid) {
        try {
            return users.get(uuid).getInvitedList();
        } catch (NullPointerException exception) {
            Logger.warning("user " + uuid + " could not be found, this could happen because it isn't loaded properly.");
            return null;
        }
    }

    /**
     * @param uuid     define which user you want to receive the role from.
     * @param teamName define which team you want to add a moderator for.
     * @throws NullPointerException when user doesn't exist.
     */
    public void addInvite(UUID uuid, String teamName) {
        try {
            users.get(uuid).getInvitedList().add(teamName);
        } catch (NullPointerException exception) {
            Logger.warning("user " + uuid + " could not be found, this could happen because it isn't loaded properly.");
        }
    }

    /**
     * @param uuid     define which user you want to receive the role from.
     * @param teamName define which team you want to add a moderator for.
     * @throws NullPointerException when user doesn't exist.
     */
    public void removeInvite(UUID uuid, String teamName) {
        try {
            users.get(uuid).getInvitedList().remove(teamName);
        } catch (NullPointerException exception) {
            Logger.warning("user " + uuid + " could not be found, this could happen because it isn't loaded properly.");
        }
    }

}
