package nl.trydev07.bettersmp.api.data;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class User {

    String team;
    Roles Role;
    Set<String> invitedList;

    public User() {
        this.team = "teamless";
        this.Role = Roles.NEWBIE;
        this.invitedList = new HashSet<>();
    }

    public enum Roles {

        NEWBIE("&7Newbie"),
        MEMBER("&7Member"),
        MODERATOR("&aModerator"),
        LEADER("&cLeader");

        String color;

        Roles(String color){
            this.color = color;
        }

        public String getColored() {
            return color;
        }

        public static Roles getRole(String name){
            return Arrays.stream(Roles.values()).filter(roles -> roles.name().equals(name)).findFirst().orElse(MEMBER);
        }
    }
}
