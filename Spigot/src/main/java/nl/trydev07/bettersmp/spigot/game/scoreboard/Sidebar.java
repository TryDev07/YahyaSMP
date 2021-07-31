package nl.trydev07.bettersmp.spigot.game.scoreboard;

import nl.trydev07.bettersmp.spigot.SMP;
import nl.trydev07.bettersmp.api.utils.TimeUtil;
import nl.trydev07.bettersmp.api.utils.scoreboard.Scoreboard;
import nl.trydev07.bettersmp.api.utils.scoreboard.ScoreboardBuilder;
import nl.trydev07.bettersmp.api.utils.text.TextUtil;
import org.bukkit.plugin.Plugin;

import java.util.*;

public class Sidebar implements ScoreboardBuilder {

    public Sidebar(Plugin plugin) {
        run(plugin);
    }

    @Override
    public void updateBoard(Scoreboard scoreboard) {
        List<String> list = new ArrayList<>();

        list.add("&7" + TimeUtil.formatDate(new Date()));
        list.add("");
        list.add(" &fName: &7" + scoreboard.getPlayer().getName());
        list.add(" &fRank: " + SMP.getInstance().getVaultHandler().getRank(scoreboard.getPlayer(), true));
        list.add("");
        list.add(" &fTeam: " + SMP.getInstance().getUserHandler().getTeam(scoreboard.getPlayer().getUniqueId()));
        list.add(" &fRole: " + SMP.getInstance().getUserHandler().getRole(scoreboard.getPlayer().getUniqueId()).getColored());
        list.add("");
        list.add("&7play.yahyacraft.nl");

        list.set(0, centerText(list, 0));
        list.set(8, centerText(list, 8));

        scoreboard.updateLines(list.toArray(new String[0]));
    }


    @Override
    public String getTitle() {
        return TextUtil.coloredText("&a&lYahyaCraft");
    }

    @Override
    public boolean isJoinEvent() {
        return true;
    }

    @Override
    public boolean isQuitEvent() {
        return true;
    }


}
