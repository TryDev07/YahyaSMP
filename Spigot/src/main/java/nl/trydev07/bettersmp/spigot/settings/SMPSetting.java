package nl.trydev07.bettersmp.spigot.settings;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Getter
@AllArgsConstructor
public enum SMPSetting {

    WHITELIST("settings.whitelist", false),
    HOSTNAME("settings.hostname", "localhost"),
    PORT("settings.port", 3306),
    USERNAME("settings.username", "root"),
    PASSWORD("settings.password", ""),
    DATABASE("settings.database", ""),
    CHAT_PREFIX("settings.chatprefix", "{rank} {team} {player_name} {suffix}: {message}");

    private String path;
    private Object defaultValue;

    private Object getValue() {
        return SMPSettings.getSetting(this);
    }

    public String getString() {
        return SMPSettings.getSetting(this).toString();
    }

    public boolean getBoolean() {
        return Boolean.parseBoolean(getString());
    }

    public int getInteger() {
        return Integer.parseInt(getString());
    }
}
