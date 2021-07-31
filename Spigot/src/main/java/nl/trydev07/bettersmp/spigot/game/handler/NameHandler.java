package nl.trydev07.bettersmp.spigot.game.handler;

import nl.trydev07.bettersmp.api.utils.nametag.Name;
import nl.trydev07.bettersmp.api.utils.text.TextUtil;
import nl.trydev07.bettersmp.spigot.SMP;
import org.bukkit.entity.Player;

import java.util.*;

public class NameHandler {

    Map<UUID, Name> names = new HashMap<>();

    String[] alfabet = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
    List<String> groups;

    public NameHandler() {
        SMP.getInstance().getResources().getSettings().load();
        this.groups = SMP.getInstance().getResources().getSettings().getStringList("Groups");
    }


    public void registerUser(Player player) {
        VaultHandler vaultHandler = SMP.getInstance().getVaultHandler();
        Name name = new Name();
        int i = 0;

        if (names.containsKey(player.getUniqueId())) return;

        for (int k = 0; k < groups.size(); k++) {
            if (vaultHandler.getPerms().getPrimaryGroup(player).equals(groups.get(k))) {
                if (i == 10) i = 90;
                if (i == 100) i = 990;
                if (i == 1000) i = 9990;
                if ((Math.abs(i) % 10) == 0) i++;
                i++;

                name.registerTeam(i + alfabet[k] + new Random().nextInt(9999));
                String team = SMP.getInstance().getUserHandler().getTeam(player.getUniqueId());

                if (team.equals("teamless")) {
                    name.setPrefix(TextUtil.coloredText(vaultHandler.getRank(player, false)));
                } else {
                    name.setPrefix(TextUtil.coloredText(vaultHandler.getRank(player, false) + SMP.getInstance().getTeamHandler().getPrefix(team)) + " ");
                }

                name.getTeam().addEntry(player.getName());
                names.putIfAbsent(player.getUniqueId(), name);

            }
        }
        if (names.containsKey(player.getUniqueId())) return;

        for (String team : SMP.getInstance().getTeamHandler().getAllTeams()) {
            if (SMP.getInstance().getUserHandler().getTeam(player.getUniqueId()).equals(team)) {
                name.registerTeam(99999 + team + new Random().nextInt(9999));
                name.setPrefix(TextUtil.coloredText(SMP.getInstance().getTeamHandler().getPrefix(SMP.getInstance().getUserHandler().getTeam(player.getUniqueId()))) + " ");
                name.getTeam().addEntry(player.getName());
                names.putIfAbsent(player.getUniqueId(), name);
                return;
            }
        }
    }

    public void unregisterUser(Player player) {
        if (names.containsKey(player.getUniqueId())) {
            names.get(player.getUniqueId()).unregisterTeam();
            names.remove(player.getUniqueId());
        }
    }

}
