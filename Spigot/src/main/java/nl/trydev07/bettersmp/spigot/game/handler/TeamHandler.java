package nl.trydev07.bettersmp.spigot.game.handler;

import nl.trydev07.bettersmp.api.data.Teams;
import nl.trydev07.bettersmp.api.utils.ConfigFile;
import nl.trydev07.bettersmp.api.utils.Logger;
import nl.trydev07.bettersmp.api.utils.nametag.Name;
import nl.trydev07.bettersmp.api.utils.text.TextUtil;
import nl.trydev07.bettersmp.spigot.SMP;
import org.bukkit.Location;

import java.util.*;

public class TeamHandler {

    private final Map<String, Teams> teams;
    private final ConfigFile configFile;
    int prior = 0;

    public TeamHandler() {
        this.teams = new HashMap<>();
        this.configFile = SMP.getInstance().getResources().getTeams();
    }

    public void createTeam(String teamName) {
        if (!teams.containsKey(teamName))
            teams.putIfAbsent(teamName, new Teams());
    }

    public void removeTeam(String teamName) {
        if (teams.get(teamName) != null)
            teams.remove(teamName);
    }

    public void saveTeam(String teamName) {
        this.configFile.set(teamName + ".name", this.teams.get(teamName).getName());
        this.configFile.set(teamName + ".prefix", this.teams.get(teamName).getPrefix());
        this.configFile.set(teamName + ".colored_tab_name", this.teams.get(teamName).getColors());
        this.configFile.set(teamName + ".tab_prefix", this.teams.get(teamName).isTabPrefix());
        this.configFile.set(teamName + ".chat_prefix", this.teams.get(teamName).isChatPrefix());
        this.configFile.set(teamName + ".leader", this.teams.get(teamName).getLeader());
        this.configFile.set(teamName + ".moderators", this.teams.get(teamName).getModerators());
        this.configFile.set(teamName + ".members", this.teams.get(teamName).getMembers());
        this.configFile.save();
    }

    public void saveAllTeams() {
        this.teams.keySet().forEach((teamName) -> {
            this.configFile.set(teamName + ".name", this.teams.get(teamName).getName());
            this.configFile.set(teamName + ".prefix", this.teams.get(teamName).getPrefix());
            this.configFile.set(teamName + ".colored_tab_name", this.teams.get(teamName).getColors());
            this.configFile.set(teamName + ".tab_prefix", this.teams.get(teamName).isTabPrefix());
            this.configFile.set(teamName + ".chat_prefix", this.teams.get(teamName).isChatPrefix());
            this.configFile.set(teamName + ".leader", this.teams.get(teamName).getLeader());
            this.configFile.set(teamName + ".moderators", this.teams.get(teamName).getModerators());
            this.configFile.set(teamName + ".members", this.teams.get(teamName).getMembers());
        });
        this.configFile.save();
    }

    public void loadTeam(String teamName) {
        if (SMP.getInstance().getResources().fileExists("teams")) {
            Teams teams = new Teams();
            teams.setName(this.configFile.getString(teamName + ".name"));
            teams.setPrefix(this.configFile.getString(teamName + ".prefix"));
            teams.setColors(this.configFile.getString(teamName + ".colored_tab_name"));
            teams.setTabPrefix(this.configFile.getBoolean(teamName + ".tab_prefix"));
            teams.setChatPrefix(this.configFile.getBoolean(teamName + ".chat_prefix"));
            teams.setLeader(this.configFile.getString(teamName + ".leader"));
            teams.setModerators(this.configFile.getStringList(teamName + ".moderators"));
            teams.setMembers(this.configFile.getStringList(teamName + ".members"));
            //TODO change this
//            teams.getNameTag().registerTeam(prior, new Random().nextInt(99999) + "");

//            teams.getNameTag().setPrefix(this.configFile.getString(teamName + ".prefix"));
            this.teams.putIfAbsent(teamName, teams);
            prior++;
        }
    }


    public void loadAllTeams() {
        if (SMP.getInstance().getResources().fileExists("teams")) {
            configFile.getKeys(false).forEach(teamName -> {
                Teams teams = new Teams();
                teams.setName(this.configFile.getString(teamName + ".name"));
                System.out.println(this.configFile.getString(teamName + ".prefix"));
                teams.setPrefix(this.configFile.getString(teamName + ".prefix"));
                teams.setColors(this.configFile.getString(teamName + ".colored_tab_name"));
                teams.setTabPrefix(this.configFile.getBoolean(teamName + ".tab_prefix"));
                teams.setChatPrefix(this.configFile.getBoolean(teamName + ".chat_prefix"));
                teams.setLeader(this.configFile.getString(teamName + ".leader"));
                teams.setModerators(this.configFile.getStringList(teamName + ".moderators"));
                teams.setMembers(this.configFile.getStringList(teamName + ".members"));
                //TODO change this
//            teams.getNameTag().registerTeam(prior, new Random().nextInt(99999) + "");
//                teams.getNameTag().setPrefix(TextUtil.coloredText(this.configFile.getString(teamName + ".prefix")));
                this.teams.putIfAbsent(teamName, teams);
                prior++;
            });
        }
    }



    public Name getNametag(String teamName) {
        try {
            return teams.get(teamName).getNameTag();
        } catch (NullPointerException exception) {
            Logger.warning("team " + teamName + " could not be found, this could happen because you didn't make this team yet.");
            return null;
        }
    }

    /**
     * @param teamName define which team you want to get the colors from.
     * @return the name of the team you have given.
     * @throws NullPointerException when team doesn't exist and returns true.
     */
    public String[] getColors(String teamName) {
        try {
            return teams.get(teamName).getColors().split("\\|");
        } catch (NullPointerException exception) {
            Logger.warning("team " + teamName + " could not be found, this could happen because you didn't make this team yet.");
            return new String[]{"&f"};
        }
    }

    /**
     * @param teamName define which team you want to set the colors for.
     * @return the name of the team you have given.
     * @throws NullPointerException when team doesn't exist and returns true.
     */
    public void setColors(String teamName, String colors) {
        try {
            teams.get(teamName).setColors(colors);
        } catch (NullPointerException exception) {
            Logger.warning("team " + teamName + " could not be found, this could happen because you didn't make this team yet.");
        }
    }

    /**
     * @param teamName define which team you want to get the tabPrefix setting from.
     * @return the name of the team you have given.
     * @throws NullPointerException when team doesn't exist and returns true.
     */
    public boolean isTabPrefix(String teamName) {
        try {
            return teams.get(teamName).isTabPrefix();
        } catch (NullPointerException exception) {
            Logger.warning("team " + teamName + " could not be found, this could happen because you didn't make this team yet.");
            return true;
        }
    }

    /**
     * @param teamName define which team you want to get the tabChat setting from.
     * @return the name of the team you have given.
     * @throws NullPointerException when team doesn't exist and returns true.
     */
    public boolean isChatPrefix(String teamName) {
        try {
            return teams.get(teamName).isChatPrefix();
        } catch (NullPointerException exception) {
            Logger.warning("team " + teamName + " could not be found, this could happen because you didn't make this team yet.");
            return true;
        }
    }

    /**
     * @param teamName define which team you want to get the name from.
     * @return the name of the team you have given.
     * @throws NullPointerException when team doesn't exist and returns "n/a".
     */
    public String getName(String teamName) {
        try {
            return teams.get(teamName).getName();
        } catch (NullPointerException exception) {
            Logger.warning("team " + teamName + " could not be found, this could happen because you didn't make this team yet.");
            return "n/a";
        }
    }

    /**
     * @param teamName define which team you want to set the name for.
     * @param name     change the name of the selected team.
     * @throws NullPointerException when team doesn't exist.
     */
    public void setName(String teamName, String name) {
        try {
            teams.get(teamName).setName(name);
        } catch (NullPointerException exception) {
            Logger.warning("team " + teamName + " could not be found, this could happen because you didn't make this team yet.");
        }
    }

    /**
     * @param teamName define which team you want to get the tabPrefix from.
     * @return the prefix of the team you have given.
     * @throws NullPointerException when team doesn't exist and returns "n/a".
     */
    public String getPrefix(String teamName) {
        try {
            return teams.get(teamName).getPrefix();
        } catch (NullPointerException exception) {
            Logger.warning("team " + teamName + " could not be found, this could happen because you didn't make this team yet.");
            return "n/a";
        }
    }

    /**
     * @param teamName  define which team you want to set the tabPrefix for.
     * @param prefix change the prefix of the selected team.
     * @throws NullPointerException when team doesn't exist.
     */
    public void setPrefix(String teamName, String prefix) {
        try {
            teams.get(teamName).setPrefix(prefix);
        } catch (NullPointerException exception) {
            Logger.warning("team " + teamName + " could not be found, this could happen because you didn't make this team yet.");
        }
    }


    /**
     * @param teamName define which team you want to get the leader from.
     * @return the leader of the team you have given.
     * @throws NullPointerException when team doesn't exist and returns null.
     */
    public String getLeader(String teamName) {
        try {
            return teams.get(teamName).getLeader();
        } catch (NullPointerException exception) {
            Logger.warning("team " + teamName + " could not be found, this could happen because you didn't make this team yet.");
            return null;
        }
    }

    /**
     * @param teamName define which team you want to set the leader for.
     * @param leader   change the leader of the selected team.
     * @throws NullPointerException when team doesn't exist.
     */
    public void setLeader(String teamName, String leader) {
        try {
            teams.get(teamName).setLeader(leader);
        } catch (NullPointerException exception) {
            Logger.warning("team " + teamName + " could not be found, this could happen because you didn't make this team yet.");
        }
    }

    /**
     * @param teamName define which team you want to get the leader from.
     * @return the leader of the team you have given.
     * @throws NullPointerException when team doesn't exist and returns null.
     */
    public List<String> getModerators(String teamName) {
        try {
            return teams.get(teamName).getModerators();
        } catch (NullPointerException exception) {
            Logger.warning("team " + teamName + " could not be found, this could happen because you didn't make this team yet.");
            return null;
        }
    }

    /**
     * @param teamName  define which team you want to add a moderator for.
     * @param moderator add a moderator to the selected team.
     * @throws NullPointerException when team doesn't exist.
     */
    public void addModerators(String teamName, String moderator) {
        try {
            teams.get(teamName).getModerators().add(moderator);
        } catch (NullPointerException exception) {
            Logger.warning("team " + teamName + " could not be found, this could happen because you didn't make this team yet.");
        }
    }

    /**
     * @param teamName define which team you want to get the leader from.
     * @return the leader of the team you have given.
     * @throws NullPointerException when team doesn't exist and returns null.
     */
    public List<String> getMembers(String teamName) {
        try {
            return teams.get(teamName).getMembers();
        } catch (NullPointerException exception) {
            Logger.warning("team " + teamName + " could not be found, this could happen because you didn't make this team yet.");
            return null;
        }
    }

    /**
     * @param teamName define which team you want to set the chatPrefix for.
     * @param member   add a member to the selected team.
     * @throws NullPointerException when team doesn't exist.
     */
    public void addMembers(String teamName, String member) {
        try {
            teams.get(teamName).getMembers().add(member);
        } catch (NullPointerException exception) {
            Logger.warning("team " + teamName + " could not be found, this could happen because you didn't make this team yet.");
        }
    }

    /**
     * @param teamName define which team you want to get the location from.
     * @return the respawn location of the team you have given.
     * @throws NullPointerException when team doesn't exist and returns null.
     */
    public Location getSpawnLocation(String teamName) {
        try {
            return teams.get(teamName).getSpawnLocation();
        } catch (NullPointerException exception) {
            Logger.warning("team " + teamName + " could not be found, this could happen because you didn't make this team yet.");
            return null;
        }
    }

    /**
     * @param teamName      define which team you want to set the spawnLocation for.
     * @param spawnLocation set the default spawnLocation for a team.
     * @throws NullPointerException when team doesn't exist.
     */
    public void setSpawnLocation(String teamName, Location spawnLocation) {
        try {
            teams.get(teamName).setSpawnLocation(spawnLocation);
        } catch (NullPointerException exception) {
            Logger.warning("team " + teamName + " could not be found, this could happen because you didn't make this team yet.");
        }
    }

    public Set<String> getAllTeams(){
        return teams.keySet();
    }
}
