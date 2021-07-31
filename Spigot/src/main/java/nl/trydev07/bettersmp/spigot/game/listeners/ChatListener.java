package nl.trydev07.bettersmp.spigot.game.listeners;

import nl.trydev07.bettersmp.spigot.game.handler.VaultHandler;
import nl.trydev07.bettersmp.api.utils.text.TextUtil;
import nl.trydev07.bettersmp.spigot.SMP;
import nl.trydev07.bettersmp.spigot.settings.SMPSetting;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void on(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        VaultHandler vaultHandler = SMP.getInstance().getVaultHandler();
        if (SMP.getInstance().getUserHandler().getTeam(player.getUniqueId()).equalsIgnoreCase("teamless")) {
            event.setFormat(TextUtil.coloredText(SMPSetting.CHAT_PREFIX.getString()
                    .replace("{rank}", vaultHandler.getChat().getPlayerPrefix(player))
                    .replace("{team}", "")
                    .replace("{player_name}", "%s")
                    .replace("{suffix}", vaultHandler.getChat().getPlayerSuffix(player))
                    .replace("{message}", "%2$s")));
        } else {
            event.setFormat(TextUtil.coloredText(SMPSetting.CHAT_PREFIX.getString()
                    .replace("{rank}", vaultHandler.getChat().getPlayerPrefix(player))
                    .replace("{team}", SMP.getInstance().getTeamHandler().getPrefix(SMP.getInstance().getUserHandler().getTeam(player.getUniqueId())))
                    .replace("{player_name}", "%s")
                    .replace("{suffix}", vaultHandler.getChat().getPlayerSuffix(player))
                    .replace("{message}", "%2$s")));
        }


    }
}
