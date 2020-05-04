package Utils;

import Model.Appointment;
import Model.DataStorage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.xml.crypto.Data;
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
        //get a list of potential timeslots from the generate9to5Schedule fn.
        //check if each timeslot falls at the same time as the start of an existing appt, or during
        // and existing appt. If so, remove it from our results.
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
        //strip the date from each timeslot to prepare it for use in the combobox.
        for(LocalDateTime ldt: nineToFiveSchedule) {
            availableTimeSlots.add(LocalTime.from(ldt));
        }
        return availableTimeSlots;
    }

    private static ObservableList<LocalDateTime> generate9to5Schedule(LocalDate selectedDate) {
        //create a list of LocalDateTime objects in 15 minute intervals starting from 09:00
        // and with the last one at 16:45
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
        //remove the last item in the list, which is the 16:45 timeslot.
        //this time is too late to schedule an appointment.
        nineToFiveSchedule.remove(nineToFiveSchedule.size() -1);
        //return generated list
        return nineToFiveSchedule;
    }

    //using the new appointment start time as input, check what appt lengths are available
    public static ObservableList<String> generateApptLengths(LocalDateTime apptStartTime) {
        //create localdatetime objects of varying lengths
        ObservableList<LocalDateTime> apptLengths = FXCollections.observableArrayList();
        apptLengths.addAll(apptStartTime.plusMinutes(15),
                apptStartTime.plusMinutes(30), apptStartTime.plusMinutes(45));
        //check if they fall within the start and finish of existing appts. If so, remove from the list.
        for(LocalDateTime newApptfinish: apptLengths) {
            for(Appointment appt: DataStorage.getAllAppointments()) {
                if(appt.getStartTime().isBefore(newApptfinish) && appt.getEndTime().isAfter(newApptfinish)) {
                    apptLengths.remove(newApptfinish);
                }
            }
        }
        //convert the remaining LDT objects to strings formatted to displayed in the combobox
        ObservableList<String> apptLengthStrings = FXCollections.observableArrayList();
        for(LocalDateTime apptFinishLDT: apptLengths) {
            String apptLengthString = ((Duration.between(apptStartTime, apptFinishLDT).toString().substring(2, 4)) + " minutes");
        }
        return apptLengthStrings;
    }
}
