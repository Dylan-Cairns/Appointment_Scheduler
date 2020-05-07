package utilities;

import model.Appointment;
import model.DataStorage;
import model.User;

import java.time.LocalDate;

public class Reports {

    public static String numberOfApptTypesbyMonth(int numofMonthsToPrint) {
        String report = "Appointment types by month for the next " + numofMonthsToPrint + " months: \n \n";

        for (int i = 0; i < numofMonthsToPrint; i++) {
            report += LocalDate.now().getMonth().plus(i) + ": \n";
            int scrumCount = 0;
            int consultationCount = 0;
            int presentationCount = 0;
            for (Appointment appointment : DataStorage.getAllAppointments()) {
                if (appointment.getStartTime().getMonth().equals(LocalDate.now().getMonth().plus(i))) {
                    String apptType = appointment.getApptType();
                    if (apptType.equals("Scrum")) {
                        scrumCount++;
                    } else if (apptType.equals("Consultation")) {
                        consultationCount++;
                    } else if (apptType.equals("Presentation")) {
                        presentationCount++;
                    }
                }
            }
            report += "Scrum: " + scrumCount + "\n"
                    + "Consultation: " + consultationCount + "\n"
                    + "Presentation: " + presentationCount + "\n \n";
        }
        return report;
    }

    public static String apptSchedulePerUser() {
        String report = "Complete appointment schedule for each user: \n";
        for (User user : DataStorage.getAllUsers()) {
            report += "Username: " + user.getUserName() + "\n" +
                    "Appointment time || Customer name \n" +
                    "--------------------------------- \n";
            for (Appointment appointment : DataStorage.getAllAppointments()) {
                if (appointment.getUserId() == user.getUserID()) {
                    report += appointment.getStartTime() + " || " + appointment.getCustomer().getCustomerName() + "\n";
                }
            }
        }
        return report;
    }
}
