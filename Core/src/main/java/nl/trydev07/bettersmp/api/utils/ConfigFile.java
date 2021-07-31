package nl.trydev07.bettersmp.api.utils;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

/**
 * Util from Itz_KiwiSap_
 */
public class ConfigFile extends YamlConfiguration {

    private final String name;
    private final File file;

    public ConfigFile(Plugin plugin, String name) {
        this(plugin, name, false, true);
    }

    public ConfigFile(Plugin plugin, String name, boolean resource) {
        this(plugin, name, false, resource);
    }

    public ConfigFile(Plugin plugin, String name, boolean folder, boolean resource) {
        this.name = name;
        this.file = new File(plugin.getDataFolder(), name);

        if (folder) {
            if (!this.file.getParentFile().exists()) this.file.getParentFile().mkdirs();
            if (!this.file.exists()) this.file.mkdirs();
        } else if (resource) {
            if (!this.file.getParentFile().exists()) this.file.getParentFile().mkdirs();
            if (!this.file.exists()) plugin.saveResource(name, true);
        } else {
            if (!this.file.getParentFile().exists()) this.file.getParentFile().mkdirs();
            if (!this.file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void load() {
        try {
            load(this.file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            save(this.file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return this.name;
    }

    public File getFile() {
        return this.file;
    }
}
