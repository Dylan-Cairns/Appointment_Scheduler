package utilities;

import model.DataStorage;

import java.io.*;
import java.time.LocalDateTime;

public class Logger {
    public static void logUserAccess() throws IOException {
        //check if a log file exist already.  If not, create it.
        String logFileName = "userlog.txt";
        File file = new File(logFileName);
        boolean firstTimeUsingLogFile = !file.exists();

        PrintWriter pw = new PrintWriter(new FileOutputStream(logFileName,true));
        //out.txt will appear in the project's root directory under NetBeans projects
        //Note that Notepad will not display the following lines on separate lines
        if(firstTimeUsingLogFile) {
            pw.append("USER ACCESS LOG\nlog file created: " + LocalDateTime.now() + "\n \n");
        }
        pw.append("Username: " + DataStorage.getStoredUser().getUserName()
                + " || Access time: " + LocalDateTime.now() + "\n");
        pw.close();
    }
}
