package nl.trydev07.bettersmp.spigot.game.commands;

import nl.trydev07.bettersmp.api.utils.Util;
import nl.trydev07.bettersmp.spigot.SMP;
import org.bukkit.plugin.Plugin;

public class RegisterCommands {

    private Util util;
    Plugin plugin;

    public RegisterCommands(Plugin plugin) {
        this.plugin = plugin;
        util = new Util(this.plugin);
    }

    public void register() {
        util.RegisterCommand(new TeamsCommand(), this.plugin);
        updateTeamTabCompletion();
    }

    public void updateTeamTabCompletion() {
        util.getManager().getCommandCompletions().registerCompletion("teams", c -> SMP.getInstance().getTeamHandler().getAllTeams());
        util.getManager().getCommandCompletions().registerCompletion("invites", c -> SMP.getInstance().getUserHandler().getInvites(c.getPlayer().getUniqueId()));
    }


}
