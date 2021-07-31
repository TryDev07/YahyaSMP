package nl.trydev07.bettersmp.spigot.game.handler;

import lombok.Getter;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import nl.trydev07.bettersmp.spigot.SMP;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import static org.bukkit.Bukkit.getServer;

public class VaultHandler {

    @Getter private Economy econ = null;
    @Getter private Permission perms = null;
    @Getter private Chat chat = null;

    public VaultHandler(){
        setupEconomy();
        setupChat();
        setupPermissions();
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    private boolean setupChat() {
        RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
        chat = rsp.getProvider();
        return chat != null;
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
    }

    public String getRank(Player player, boolean def){
        String rank;

        if(SMP.getInstance().getVaultHandler().getPerms().getPrimaryGroup(player).equalsIgnoreCase("default")){
            if(def) {
                rank = "&7Player";
            }else{
                rank = "";
            }
        }else {
            rank = SMP.getInstance().getVaultHandler().getChat().getPlayerPrefix(player);
        }
        return rank;
    }
}
