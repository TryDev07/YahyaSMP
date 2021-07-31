package nl.trydev07.bettersmp.api.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {

    public static String formatDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(date);
    }

}
