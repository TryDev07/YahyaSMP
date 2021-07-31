package nl.trydev07.bettersmp.spigot.game.listeners;

import nl.trydev07.bettersmp.api.utils.Util;
import nl.trydev07.bettersmp.spigot.game.scoreboard.Sidebar;
import org.bukkit.plugin.Plugin;

public class RegisterListeners {

    Plugin plugin;

    public RegisterListeners(Plugin plugin) {
        this.plugin = plugin;
    }

    public void register() {
        Util util = new Util(this.plugin);
        util.RegisterEvent(new UserListener());
        util.RegisterEvent(new TeamListener());
        util.RegisterEvent(new Sidebar(plugin));
        util.RegisterEvent(new ChatListener());
    }
}
