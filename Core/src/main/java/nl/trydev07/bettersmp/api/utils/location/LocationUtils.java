package nl.trydev07.bettersmp.api.utils.location;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.*;

public class LocationUtils {

    public static List<String> serializeLocationList(List<Location> locationList) {
        List<String> stringList = new ArrayList<>();
        locationList.forEach(loc -> {
            stringList.add(loc.getWorld().getName() + ":" + loc.getX() + ":" + loc.getY() + ":" + loc.getZ() + ":" + loc.getYaw() + ":" + loc.getPitch());
        });
        return stringList;
    }

    public static List<Location> deserializeLocationList(List<String> stringList) {
        List<Location> locationList = new ArrayList<>();
        stringList.forEach(string -> {
            String[] s = string.split(":");
            locationList.add(new Location(Bukkit.getWorld(s[0]), Double.parseDouble(s[1]), Double.parseDouble(s[2]), Double.parseDouble(s[3]), Float.parseFloat(s[4]), Float.parseFloat(s[5])));
        });
        return locationList;
    }

    public static String serializeLocation(Location location) {
        return location.getWorld().getName() + ":" + location.getX() + ":" + location.getY() + ":" + location.getZ() + ":" + location.getYaw() + ":" + location.getPitch();
    }

    public static Location deserializeLocation(String location) {
        String[] s = location.split(":");
        return new Location(Bukkit.getWorld(s[0]), Double.parseDouble(s[1]), Double.parseDouble(s[2]), Double.parseDouble(s[3]), Float.parseFloat(s[4]), Float.parseFloat(s[5]));
    }

    public static List<EasyLocation> getDiagonal(Location location, int addTo) {
        int add = addTo / 2;
        Location location1 = location.getBlock().getLocation().add(add, 0, -add);
        Location location2 = location.getBlock().getLocation().add(-add, 0, add);

        Cuboid cuboid = new Cuboid(location1, location2);
        List<EasyLocation> locations = new ArrayList<>();
        cuboid.forEach(block -> locations.add(new EasyLocation(block.getWorld(), block.getX(), block.getY(), block.getZ())));
        return locations;
    }
}
