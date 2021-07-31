package nl.trydev07.bettersmp.spigot;

import lombok.Getter;
import nl.trydev07.bettersmp.spigot.game.commands.RegisterCommands;
import nl.trydev07.bettersmp.spigot.game.handler.NameHandler;
import nl.trydev07.bettersmp.spigot.game.handler.TeamHandler;
import nl.trydev07.bettersmp.spigot.game.handler.UserHandler;
import nl.trydev07.bettersmp.spigot.game.handler.VaultHandler;
import nl.trydev07.bettersmp.spigot.game.listeners.RegisterListeners;
import nl.trydev07.bettersmp.spigot.settings.SMPSettings;
import nl.trydev07.bettersmp.spigot.storage.Resources;
import org.bukkit.plugin.java.JavaPlugin;

public class SMP extends JavaPlugin {

    @Getter private static SMP instance;
    @Getter private Resources resources;
    @Getter private TeamHandler teamHandler;
    @Getter private UserHandler userHandler;
    @Getter private RegisterListeners registerListeners;
    @Getter private RegisterCommands registerCommands;
    @Getter private VaultHandler vaultHandler;
    @Getter private NameHandler nameHandler;

    @Override
    public void onEnable() {
        init();
    }

    @Override
    public void onDisable() {
        this.teamHandler.saveAllTeams();
    }

    public void init() {
        instance = this;
        this.resources = new Resources(this);
        this.teamHandler = new TeamHandler();
        this.userHandler = new UserHandler();
        this.registerListeners = new RegisterListeners(this);
        this.registerCommands = new RegisterCommands(this);
        this.vaultHandler = new VaultHandler();
        this.nameHandler = new NameHandler();

        this.resources.load();
        SMPSettings.load();
        this.teamHandler.loadAllTeams();
        this.registerListeners.register();
        this.registerCommands.register();

    }
}
