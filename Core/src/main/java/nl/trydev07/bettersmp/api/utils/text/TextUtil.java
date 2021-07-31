package nl.trydev07.bettersmp.api.utils.text;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.libs.it.unimi.dsi.fastutil.Hash;
import org.bukkit.entity.Player;

import java.util.*;

public class TextUtil {

    public static String coloredText(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    public static List<String> coloredText(String[] msg) {
        List<String> strings = new ArrayList<>();
        Arrays.stream(msg).forEach(m -> strings.add(ChatColor.translateAlternateColorCodes('&', m)));
        return strings;
    }

    public static Set<String> stringToList(String str) {
        return new HashSet<>(Arrays.asList(str.split(":")));
    }


    public static String listToString(Set<String> str) {
        return str.toString().replaceAll("[,\\[\\]]", "").replace(" ", ":");
    }


    public static String centerToScoreboard(String string, String... array) {
        int maxLength = 0;
        String longestString = null;
        for (String s : array) {
            if (s.replaceAll("(&.)", "").length() > maxLength) {
                maxLength = s.replaceAll("(&.)", "").length();
                longestString = s.replaceAll("(&.)", "");
            }
        }
        if (longestString == null) return string;
        return spacesToAdd(longestString, string, 21, 25, 29, 33, 37, 40, 45) + string;
    }

    public static String spacesToAdd(String text, String str, int... lessThen) {
        int i = text.length();
        int defaultFactor = 2;
        int spacesToAdd = (text.length() - str.replaceAll("(&.)", "").length()) / 2;
        for (int ints = 0; ints < lessThen.length; ints++) {
            if (i < lessThen[ints]) {
                defaultFactor += ints;
                return String.join("", Collections.nCopies((spacesToAdd + defaultFactor), " "));
            }
        }
        return String.join("", Collections.nCopies((spacesToAdd + 9), " "));
    }

    public static String splitMessage(String name, int in) {
        if (name.length() < 16) return name;
        if (name.length() > 48) throw new ArrayIndexOutOfBoundsException(name.length() + " > 48");

        int stepSize;
        if ((int) Math.floor((double) name.length() / (double) in) < 2) {
            stepSize = (int) Math.ceil((double) name.length() / (double) in);
        } else {
            stepSize = (int) Math.floor((double) name.length() / (double) in);
        }

        String str = "";
        int times = 0;
        for (int i = 0; i < in; i++) {
            if (i == (name.length() / stepSize - 1) && name.length() != (stepSize * (i + 1))) {
                str = str + ";" + name.substring(times);
            } else {
                if (i == 0) {
                    str = name.substring(0, stepSize);
                    times += stepSize;
                } else {
                    str = str + ";" + name.substring(times, (stepSize * (i + 1)));
                    times += stepSize;
                }
            }
        }
        return str;
    }

}
