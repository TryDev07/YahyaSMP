package nl.trydev07.bettersmp.api.utils.nametag;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import java.util.Objects;

public class Name {

    @Getter private Team team;
    private Scoreboard scoreboard;

    public Name() {
        this.scoreboard = Objects.requireNonNull(Bukkit.getServer().getScoreboardManager()).getMainScoreboard();
    }

    public void registerTeam(String teamName) {
        this.team = scoreboard.registerNewTeam(teamName);
    }

    public void unregisterTeam() {
        team.unregister();
        System.out.println("unregistered");
    }

    public void setPrefix(String prefix) {
        team.setPrefix(prefix);
    }

    public void setSuffix(String suffix) {
        team.setSuffix(suffix);
    }

    public Team getTeam() {
        return team;
    }



}
