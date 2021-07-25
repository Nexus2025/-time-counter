package timeCounter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class TimeCounterService {

    private final TimeObject timeObject;
    private Thread thread;

    public TimeCounterService(TimeObject timeObject) {
        this.timeObject = timeObject;
    }

    public void startTimer() {
        if (thread == null || thread.getState() == Thread.State.TERMINATED) {
            thread = createNewThread(timeObject);
            thread.setDaemon(true);
            thread.start();
        }
    }

    public void stopTimer() {
        if (thread != null) {
            thread.interrupt();
        }
    }

    private Thread createNewThread(TimeObject timeObject) {
        return new Thread() {
            private final SimpleDateFormat sp = new SimpleDateFormat("mm:ss");
            private final Calendar fullTimeCalendar = new GregorianCalendar();
            private final Calendar currentTimeCalendar = new GregorianCalendar();

            @Override
            public void run() {
                String fullTime = timeObject.getFullTimeWithoutPrefix();
                String[] fullTimeElements = fullTime.split(":");
                int fullHours = Integer.parseInt(fullTimeElements[0]);
                int fullMinutes = Integer.parseInt(fullTimeElements[1]);
                int fullSeconds = Integer.parseInt(fullTimeElements[2]);

                String currentTime = timeObject.getCurrentTimeWithoutPrefix();
                String[] currentTimeElements = currentTime.split(":");
                int currentHours = Integer.parseInt(currentTimeElements[0]);
                int currentMinutes = Integer.parseInt(currentTimeElements[1]);
                int currentSeconds = Integer.parseInt(currentTimeElements[2]);

                try {
                    while (!currentThread().isInterrupted()) {

                        Thread.sleep(1000);
                        fullSeconds++;
                        currentSeconds++;

                        if (fullSeconds == 60) {
                            fullSeconds = 0;
                            fullMinutes++;
                        }

                        if (currentSeconds == 60) {
                            currentSeconds = 0;
                            currentMinutes++;
                        }

                        if (fullMinutes == 60) {
                            fullMinutes = 0;
                            fullHours++;
                        }

                        if (currentMinutes == 60) {
                            currentMinutes = 0;
                            currentHours++;
                        }

                        String fullHoursText = Integer.toString(fullHours);
                        if (fullHoursText.length() < 2) {
                            fullHoursText = "0" + fullHoursText;
                        }

                        String currentHoursText = Integer.toString(currentHours);
                        if (currentHoursText.length() < 2) {
                            currentHoursText = "0" + currentHoursText;
                        }

                        fullTimeCalendar.set(Calendar.MINUTE, fullMinutes);
                        fullTimeCalendar.set(Calendar.SECOND, fullSeconds);

                        currentTimeCalendar.set(Calendar.MINUTE, currentMinutes);
                        currentTimeCalendar.set(Calendar.SECOND, currentSeconds);

                        timeObject.setFullTimeForView(fullHoursText + ":" + sp.format(fullTimeCalendar.getTime()));
                        timeObject.setCurrentTimeForView(currentHoursText + ":" + sp.format(currentTimeCalendar.getTime()));
                    }
                } catch (InterruptedException ignore) {
                }
            }
        };
    }
}
