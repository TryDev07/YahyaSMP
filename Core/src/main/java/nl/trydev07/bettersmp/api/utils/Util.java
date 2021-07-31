package nl.trydev07.bettersmp.api.utils;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.PaperCommandManager;
import com.google.common.collect.ImmutableList;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Util {

    @Getter private PaperCommandManager manager;
    private Plugin plugin;

    public Util(Plugin plugin) {
        this.plugin = plugin;
        manager = new PaperCommandManager(this.plugin);
    }

    public void RegisterEvent(Listener className) {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(className, plugin);
    }

    public void RegisterCommand(BaseCommand baseCommand, Plugin plugin) {
        manager.registerCommand(baseCommand);
    }

    public static List<Player> getOnline() {
        List<Player> list = new ArrayList<>();

        for (World world : Bukkit.getWorlds()) {
            list.addAll(world.getPlayers());
        }

        return Collections.unmodifiableList(list);
    }

    public static String generateUUID() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            builder.append(chars.charAt((int) (Math.random() * chars.length())));
        }
        return builder.toString();
    }

}
