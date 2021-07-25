package timeCounter;

import java.io.*;

public class FileOperationsService {
    private static final String path;

    static {
        path = new File("").getAbsolutePath()  + "\\saveTime.txt";
    }

    public static void readData(TimeObject timeObject) {
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(path));
            String contentOfFile = null;

            if (timeObject.getCategory() == Category.PROGRAMMING) {
                contentOfFile = reader.readLine();
            }

            if (timeObject.getCategory() == Category.ENGLISH) {
                reader.readLine(); //ignore first string
                contentOfFile = reader.readLine();
            }

            timeObject.setFullTimeForView(contentOfFile);

        } catch (Exception e) {
            timeObject.setFullTimeForView("00:00:00");

        } finally {
            try {
                if (reader != null) reader.close();
            } catch (IOException ignore) { }
        }

        timeObject.setCurrentTimeForView("00:00:00");
    }

    public static void writeData(TimeObject fullProgrammingTime, TimeObject fullEnglishTime) {
        BufferedWriter bufferedWriter = null;

        try {
            bufferedWriter = new BufferedWriter(new FileWriter(path));
            bufferedWriter.write(fullProgrammingTime.getFullTimeWithoutPrefix() + System.lineSeparator());
            bufferedWriter.write(fullEnglishTime.getFullTimeWithoutPrefix());

        } catch (IOException ignore) {
        } finally {
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.close();
                } catch (IOException ignore) {}
            }
        }
    }
}
