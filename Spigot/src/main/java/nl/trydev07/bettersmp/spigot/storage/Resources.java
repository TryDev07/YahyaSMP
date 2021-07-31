package nl.trydev07.bettersmp.spigot.storage;


import lombok.Getter;
import nl.trydev07.bettersmp.api.utils.ConfigFile;
import nl.trydev07.bettersmp.spigot.SMP;

import java.io.File;

public class Resources {

    private final SMP plugin;
    @Getter ConfigFile settings;
    @Getter ConfigFile teams;

    public Resources(SMP plugin) {
        this.plugin = plugin;
        this.settings = new ConfigFile(this.plugin, "settings.yml");
        this.teams = new ConfigFile(this.plugin, "teams.yml", false);
    }

    public void load() {
        this.settings.load();
        this.teams.load();
    }

    public void save() {
        this.teams.save();
    }

    public Boolean fileExists(String filname) {
        return new File(plugin.getDataFolder(), filname + ".yml").exists();
    }

}
