package timeCounter;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TimeObject {

    private final String prefixFullTime = "FullTime: ";
    private final String prefixCurrentTime = "CurrentTime: ";

    private final StringProperty fullTimeForView = new SimpleStringProperty();
    private final StringProperty currentTimeForView = new SimpleStringProperty();

    private final Category category;

    public TimeObject(Category category) {
        this.category = category;
        FileOperationsService.readData(this);
    }

    public StringProperty getFullTimeForView() {
        return fullTimeForView;
    }

    public void setFullTimeForView(String fullTime) {
        this.fullTimeForView.set(prefixFullTime + fullTime);
    }

    public StringProperty getCurrentTimeForView() {
        return currentTimeForView;
    }

    public void setCurrentTimeForView(String currentTime) {
        this.currentTimeForView.set(prefixCurrentTime + currentTime);
    }

    public Category getCategory() {
        return category;
    }

    public void resetCurrentTime() {
        this.currentTimeForView.set(prefixCurrentTime + "00:00:00");
    }

    public String getFullTimeWithoutPrefix() {
        return fullTimeForView.getValue().replace(prefixFullTime, "");
    }

    public String getCurrentTimeWithoutPrefix() {
        return currentTimeForView.getValue().replace(prefixCurrentTime, "");
    }
}
