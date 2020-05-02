package Utils;

import Model.Appointment;
import Model.DataStorage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;

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

    public static ObservableList<LocalTime> getTimeslots(LocalDate selectedDate) {
        ObservableList<LocalTime> availableTimeSlots = FXCollections.observableArrayList();
        ObservableList<LocalDateTime> nineToFiveSchedule = generate9to5Schedule(selectedDate);
        ObservableList<Appointment> existingAppts = DataStorage.getAllAppointments();

        for(LocalDateTime timeSlot: nineToFiveSchedule) {
            for(Appointment appt: existingAppts) {
                if(timeSlot == appt.getStartTime()){
                    nineToFiveSchedule.remove(timeSlot);
                }
                if(timeSlot.isAfter(appt.getStartTime()) && timeSlot.isBefore(appt.getEndTime())) {
                    nineToFiveSchedule.remove(timeSlot);
                }
            }
        }
        for(LocalDateTime ldt: nineToFiveSchedule) {
            availableTimeSlots.add(LocalTime.from(ldt));
        }
        return availableTimeSlots;
    }

    private static ObservableList<LocalDateTime> generate9to5Schedule(LocalDate selectedDate) {
        ObservableList<LocalDateTime> nineToFiveSchedule = FXCollections.observableArrayList();
        ObservableList<String> hourPeriods = FXCollections.observableArrayList();
        hourPeriods.addAll("09", "10", "11", "12", "13", "14", "15", "16");
        ObservableList<String> quarterHourPeriods = FXCollections.observableArrayList();
        quarterHourPeriods.addAll("00", "15", "30", "45");
        for (String hourPeriod : hourPeriods) {
            for (String quarterHourPeriod : quarterHourPeriods) {
                String timeBlockString = (selectedDate.toString() + " " + hourPeriod
                        + ":" + quarterHourPeriod + ":00.0");
                DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd kk:mm:ss.S");
                LocalDateTime timeBlock = LocalDateTime.parse(timeBlockString, df);
                nineToFiveSchedule.add(timeBlock);
            }
        }
        return nineToFiveSchedule;
    }
}
