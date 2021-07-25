package timeCounter;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class TimeCounterApplication extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) {

        TimeObject programmingTime = new TimeObject(Category.PROGRAMMING);
        TimeObject englishTime = new TimeObject(Category.ENGLISH);

        TimeCounterService programmingTimeCounterService = new TimeCounterService(programmingTime);
        TimeCounterService englishTimeCounterService = new TimeCounterService(englishTime);

        Text textProgramming = new Text(100, 25, "Programming");
        textProgramming.setFont(new Font("Roboto", 17));
        textProgramming.setStroke(Color.BLUE);

        Text textProgrammingFullTime = new Text(10, 60, "");
        textProgrammingFullTime.setFont(new Font("Roboto", 22));
        textProgrammingFullTime.textProperty().bind(programmingTime.getFullTimeForView());

        Text textProgrammingCurrentTime = new Text(10, 90, "");
        textProgrammingCurrentTime.setFont(new Font("Roboto", 22));
        textProgrammingCurrentTime.setStroke(Color.ORANGERED);
        textProgrammingCurrentTime.textProperty().bind(programmingTime.getCurrentTimeForView());

        Button buttonStartStopProgramming = new Button("Start");
        buttonStartStopProgramming.setLayoutX(240);
        buttonStartStopProgramming.setLayoutY(12);
        buttonStartStopProgramming.setMinSize(50, 20);

        Button buttonPauseProgramming = new Button("Pause");
        buttonPauseProgramming.setLayoutX(240);
        buttonPauseProgramming.setDisable(true);
        buttonPauseProgramming.setLayoutY(50);
        buttonPauseProgramming.setMinSize(50, 20);



        Text textEng = new Text(100, 135, "English");
        textEng.setFont(new Font("Roboto", 17));
        textEng.setStroke(Color.GREEN);

        Text textEngFullTime = new Text(10, 170, "");
        textEngFullTime.setFont(new Font("Roboto", 22));
        textEngFullTime.textProperty().bind(englishTime.getFullTimeForView());

        Text textEngCurrentTime = new Text(10, 200, "");
        textEngCurrentTime.setFont(new Font("Roboto", 22));
        textEngCurrentTime.setStroke(Color.ORANGERED);
        textEngCurrentTime.textProperty().bind(englishTime.getCurrentTimeForView());

        Button buttonStartStopEng = new Button("Start");
        buttonStartStopEng.setLayoutX(240);
        buttonStartStopEng.setLayoutY(122);
        buttonStartStopEng.setMinSize(50, 20);

        Button buttonPauseEng = new Button("Pause");
        buttonPauseEng.setLayoutX(240);
        buttonPauseEng.setDisable(true);
        buttonPauseEng.setLayoutY(160);
        buttonPauseEng.setMinSize(50, 20);



        buttonStartStopProgramming.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (buttonStartStopProgramming.getText().equals("Start")) {
                    programmingTimeCounterService.startTimer();
                    buttonStartStopProgramming.setText("Stop");
                    buttonPauseProgramming.setDisable(false);
                    buttonStartStopEng.setDisable(true);
                } else {
                    programmingTimeCounterService.stopTimer();
                    programmingTime.resetCurrentTime();
                    buttonStartStopProgramming.setText("Start");
                    buttonPauseProgramming.setText("Pause");
                    buttonPauseProgramming.setDisable(true);
                    buttonStartStopEng.setDisable(false);
                    FileOperationsService.writeData(programmingTime, englishTime);
                }
            }
        });

        buttonPauseProgramming.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (buttonPauseProgramming.getText().equals("Pause")) {
                    programmingTimeCounterService.stopTimer();
                    buttonPauseProgramming.setText("Continue");
                } else {
                    programmingTimeCounterService.startTimer();
                    buttonPauseProgramming.setText("Pause");
                }
            }
        });


        buttonStartStopEng.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (buttonStartStopEng.getText().equals("Start")) {
                    englishTimeCounterService.startTimer();
                    buttonStartStopEng.setText("Stop");
                    buttonPauseEng.setDisable(false);
                    buttonStartStopProgramming.setDisable(true);
                } else {
                    englishTimeCounterService.stopTimer();
                    englishTime.resetCurrentTime();
                    buttonStartStopEng.setText("Start");
                    buttonPauseEng.setText("Pause");
                    buttonPauseEng.setDisable(true);
                    buttonStartStopProgramming.setDisable(false);
                    FileOperationsService.writeData(programmingTime, englishTime);
                }
            }
        });

        buttonPauseEng.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (buttonPauseEng.getText().equals("Pause")) {
                    englishTimeCounterService.stopTimer();
                    buttonPauseEng.setText("Continue");
                } else {
                    englishTimeCounterService.startTimer();
                    buttonPauseEng.setText("Pause");
                }
            }
        });

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                //when window is closed
                FileOperationsService.writeData(programmingTime, englishTime);
            }
        });


        Group root = new Group();
        Scene scene = new Scene(root, 330, 220);
        root.getChildren().addAll(textProgrammingFullTime, buttonStartStopProgramming,
                textProgramming, textProgrammingCurrentTime, buttonPauseProgramming,
                textEngCurrentTime, textEngFullTime, textEng, buttonPauseEng, buttonStartStopEng);

        primaryStage.setTitle("TimeCounter");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
