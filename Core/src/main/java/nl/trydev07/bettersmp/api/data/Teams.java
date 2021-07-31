package nl.trydev07.bettersmp.api.data;

import lombok.Getter;
import lombok.Setter;
import nl.trydev07.bettersmp.api.utils.nametag.Name;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.*;

@Getter
@Setter
public class Teams {

    private String name;
    private String prefix;
    private String colors;
    private boolean tabPrefix;
    private boolean chatPrefix;
    private String leader;
    private List<String> moderators;
    private List<String> members;
    private Location spawnLocation;
    private Name nameTag;

    public Teams() {
        this.name = "n/a";
        this.prefix = "";
        this.colors = "&f";
        this.tabPrefix = true;
        this.chatPrefix = true;
        this.leader = null;
        this.moderators = new ArrayList<>();
        this.members = new ArrayList<>();
        this.spawnLocation = null;
        this.nameTag = new Name();
    }

}
