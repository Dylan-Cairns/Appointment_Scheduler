package Utils;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class TimeFunctions {

    public static LocalDateTime DBTimeToLocalTime(Timestamp databaseTime) {
        //Get local time zone from system
        ZoneId newzid = ZoneId.systemDefault();
        //change variable type
        ZonedDateTime ZDTStart = databaseTime.toLocalDateTime().atZone(ZoneId.of("UTC"));
        //change time zone
        ZonedDateTime ZDTLocalStart = ZDTStart.withZoneSameInstant(newzid);
        //change variable type
        LocalDateTime ldtStart = ZDTLocalStart.toLocalDateTime();
        return ldtStart;
    }

    public static Timestamp localTimetoDBTime(LocalDateTime localTime) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdTime = localTime.atZone(zoneId);
        ZonedDateTime utcTime = zdTime.withZoneSameInstant(ZoneId.of("UTC"));
        LocalDateTime ldtTime = utcTime.toLocalDateTime();
        Timestamp DBtime = Timestamp.valueOf(ldtTime);
        return DBtime;
    }
}
