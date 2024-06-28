package ci.nkagou.parcauto.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateConvert {
    public static String getStringDate(LocalDateTime dateTime){



        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        return dateTime.format(formatter);

    }



    public static String getStringDate(Date dateTime){



        // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        return dateFormat.format(dateTime);

    }



    public static LocalDateTime getDateTime(String stringLocalDateTime) throws ParseException {



        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(stringLocalDateTime);

        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

    }
}
