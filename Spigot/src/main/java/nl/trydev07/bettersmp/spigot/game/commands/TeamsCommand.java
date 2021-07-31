package nl.trydev07.bettersmp.spigot.game.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import nl.trydev07.bettersmp.api.data.User;
import nl.trydev07.bettersmp.api.utils.UUIDFetcher;
import nl.trydev07.bettersmp.api.utils.text.TextUtil;
import nl.trydev07.bettersmp.spigot.SMP;
import nl.trydev07.bettersmp.spigot.game.handler.TeamHandler;
import nl.trydev07.bettersmp.spigot.game.handler.UserHandler;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;
import java.util.UUID;

@CommandAlias("team")
public class TeamsCommand extends BaseCommand {


    @Default
    public void help(Player player) {
        player.sendMessage(TextUtil.coloredText("&7--------------------[&eSMP&7]--------------------"));
        player.sendMessage(TextUtil.coloredText("&a/team create <teamName> &7| Create your own team using this command"));
        player.sendMessage(TextUtil.coloredText("&a/team remove <teamName> &7| Remove a existing team."));
        player.sendMessage(TextUtil.coloredText("&a/team setPrefix <teamName> <prefix> &7| change the Prefix of your team"));
        player.sendMessage(TextUtil.coloredText("&a/team setLeader <teamName> <player> &7| set a leader for the team."));
        player.sendMessage(TextUtil.coloredText("&a/team setModerator <teamName> <player> &7| add a moderator for the team."));
        player.sendMessage(TextUtil.coloredText("&a/team invite <player> &7| Invite a player to your own team."));
        player.sendMessage(TextUtil.coloredText("&a/team accept <teamName> &7| Accept invites from your invited teams"));
        player.sendMessage(TextUtil.coloredText("&a/team setColor <teamName> <colors> &7| Accept invites from your invited teams"));
        player.sendMessage(TextUtil.coloredText("&7--------------------[&eSMP&7]--------------------"));
    }

    @Subcommand("create")
    @CommandPermission("yahyacraft.smp.create")
    public void create(Player player, String[] args) {
        if (args.length > 0) {
            TeamHandler teamHandler = SMP.getInstance().getTeamHandler();
            if (teamHandler.getAllTeams().contains(args[0].toLowerCase())) {
                player.sendMessage("&cThis team already exist");
                return;
            }
            SMP.getInstance().getTeamHandler().createTeam(args[0].toLowerCase());
            player.sendMessage(TextUtil.coloredText("&aTeam &7" + args[0].toLowerCase() + " &ahas been created"));

        } else {
            player.sendMessage(TextUtil.coloredText("&cUsage /team create <teamName>"));
        }
    }

    @Subcommand("remove")
    @CommandPermission("yahyacraft.smp.remove")
    @CommandCompletion("@teams")
    public void remove(Player player, @Single @Values("@teams") @Syntax("<teamName>") String team) {
        SMP.getInstance().getTeamHandler().removeTeam(team.toLowerCase());
        player.sendMessage(TextUtil.coloredText("&aTeam &7" + team.toLowerCase() + " &ahas been removed"));
    }

    @Subcommand("setPrefix")
    @CommandPermission("yahyacraft.smp.prefix")
    @CommandCompletion("@teams")
    public void tabPrefix(Player player, @Single @Values("@teams") @Syntax("<teamName>") String team, String prefix) {
        SMP.getInstance().getTeamHandler().setPrefix(team, prefix);
        player.sendMessage(TextUtil.coloredText("&aChanged the tab prefix for team &7" + team.toLowerCase()));
    }

    @Subcommand("setColor")
    @CommandPermission("yahyacraft.smp.color")
    @CommandCompletion("@teams")
    public void color(Player player, @Single @Values("@teams") @Syntax("<teamName>") String team, String colors) {
        SMP.getInstance().getTeamHandler().setColors(team, colors);
        player.sendMessage(TextUtil.coloredText("&aChanged the color for team &7" + team.toLowerCase()));
    }

    @Subcommand("setLeader")
    @CommandPermission("yahyacraft.smp.leader")
    @CommandCompletion("@teams @players")
    public void setLeader(Player player, @Single @Values("@teams") @Syntax("<teamName>") String team, @Single String selectedPlayer) {
        UserHandler user = SMP.getInstance().getUserHandler();
        TeamHandler teamHandler = SMP.getInstance().getTeamHandler();
        OfflinePlayer players = Bukkit.getOfflinePlayer(Objects.requireNonNull(UUIDFetcher.getUUID(selectedPlayer)));
        user.loadUser(players.getUniqueId());
        if (teamHandler.getLeader(team) != null && teamHandler.getLeader(team).contains(players.getUniqueId().toString())) {
            player.sendMessage(TextUtil.coloredText("&cThe user is already leader of this team."));
            return;
        }

        if (teamHandler.getLeader(team) != null) {
            OfflinePlayer oldLeader = Bukkit.getOfflinePlayer(UUID.fromString(teamHandler.getLeader(team)));
            user.loadUser(oldLeader.getUniqueId());
            user.setTeam(oldLeader.getUniqueId(), "teamless");
            user.setRole(oldLeader.getUniqueId(), User.Roles.NEWBIE);
            if (Bukkit.getPlayer(selectedPlayer) == null)
                user.saveUser(players.getUniqueId());
        }

        teamHandler.setLeader(team, players.getUniqueId().toString());
        user.setTeam(players.getUniqueId(), team);
        user.setRole(players.getUniqueId(), User.Roles.LEADER);
        if (Bukkit.getPlayer(selectedPlayer) == null)
            user.saveUser(players.getUniqueId());
        player.sendMessage(TextUtil.coloredText("&asetted&7 " + players.getName() + " &aas leader for team &7" + team));

    }

    @Subcommand("setModerator")
    @CommandCompletion("@teams @players")
    public void setModerator(Player player, @Single @Values("@teams") @Syntax("<teamName>") String team, @Single String selectedPlayer) {
        if (player.hasPermission("yahyacraft.smp.setModerator") || SMP.getInstance().getUserHandler().getRole(player.getUniqueId()).equals(User.Roles.LEADER)) {
            UserHandler user = SMP.getInstance().getUserHandler();
            TeamHandler teamHandler = SMP.getInstance().getTeamHandler();
            OfflinePlayer players = Bukkit.getOfflinePlayer(Objects.requireNonNull(UUIDFetcher.getUUID(selectedPlayer)));
            user.loadUser(players.getUniqueId());
            if (user.getRole(players.getUniqueId()).equals(User.Roles.LEADER)) {
                player.sendMessage(TextUtil.coloredText("&cThe user is already Leader of of a team. Assign a new leader before making him moderator"));
                return;
            }
            if (teamHandler.getModerators(team).contains(player.getUniqueId().toString())) {
                player.sendMessage(TextUtil.coloredText("&cThe user is already moderator of this team."));
                return;
            }
            if (!user.getTeam(player.getUniqueId()).equalsIgnoreCase("teamless")) {
                teamHandler.getModerators(user.getTeam(player.getUniqueId())).remove(player.getUniqueId().toString());
            }
            teamHandler.addModerators(team, players.getUniqueId().toString());
            user.setTeam(players.getUniqueId(), team);
            user.setRole(players.getUniqueId(), User.Roles.MODERATOR);
            player.sendMessage(TextUtil.coloredText("&asetted&7 " + players.getName() + " &aas a moderator for team &7" + players.getName()));
            if (Bukkit.getPlayer(selectedPlayer) == null)
                user.saveUser(players.getUniqueId());
        }
    }

    @Subcommand("invite")
    @CommandCompletion("@players @teams")
    public void inviteMembers(Player player, @Single @Optional String selectedPlayer, @Single @Optional @CommandPermission("yahyacraft.smp.invite") @Values("@teams") String team) {
        UserHandler user = SMP.getInstance().getUserHandler();
        OfflinePlayer player1 = Bukkit.getOfflinePlayer(Objects.requireNonNull(UUIDFetcher.getUUID(selectedPlayer)));
        user.loadUser(player1.getUniqueId());
        if (team == null) {
            if (player.hasPermission("yahyacraft.smp.inviteMembers") || user.getRole(player.getUniqueId()).equals(User.Roles.MODERATOR) || SMP.getInstance().getUserHandler().getRole(player.getUniqueId()).equals(User.Roles.LEADER)) {
                if (user.getTeam(player.getUniqueId()).equals("teamless")) {
                    player.sendMessage(TextUtil.coloredText("&cYou must be in a team yourself"));
                    return;
                }
                user.addInvite(player1.getUniqueId(), user.getTeam(player.getUniqueId()));
                player.sendMessage(TextUtil.coloredText("&aSuccessfully invited player &7" + player1.getName() + " &ato your team"));
                System.out.println(Bukkit.getPlayer(selectedPlayer) == null);
                if (Bukkit.getPlayer(selectedPlayer) == null)
                    user.saveUser(player1.getUniqueId());
            } else {
                player.sendMessage(TextUtil.coloredText("&cYou do not have the moderator role in your team"));
            }
        } else {
            user.addInvite(player1.getUniqueId(), team);
            if (Bukkit.getPlayer(selectedPlayer) == null)
                user.saveUser(player1.getUniqueId());
            player.sendMessage(TextUtil.coloredText("&aSuccessfully invited player &7" + player1.getName() + " &ato team &7" + team));
        }
    }

    @Subcommand("accept")
    @CommandCompletion("@invites")
    public void acceptInvite(Player player, @Values("@invites") @Syntax("<teamName>") String team) {
        UserHandler user = SMP.getInstance().getUserHandler();
        TeamHandler teamHandler = SMP.getInstance().getTeamHandler();
        if (!user.getTeam(player.getUniqueId()).equals("teamless")) {
            player.sendMessage(TextUtil.coloredText("&cYou are already in a team."));
            return;
        }
        if (!user.getInvites(player.getUniqueId()).contains(team)) {
            player.sendMessage(TextUtil.coloredText("&cYou do not have a invite for this team."));
            return;
        }
        if (!user.getTeam(player.getUniqueId()).equalsIgnoreCase("teamless")) {
            teamHandler.getMembers(user.getTeam(player.getUniqueId())).remove(player.getUniqueId().toString());
        }
        user.setTeam(player.getUniqueId(), team);
        user.setRole(player.getUniqueId(), User.Roles.MEMBER);
        teamHandler.addMembers(team, player.getUniqueId().toString());
        player.sendMessage(TextUtil.coloredText("&aYou joined team &7" + team));
    }


}
