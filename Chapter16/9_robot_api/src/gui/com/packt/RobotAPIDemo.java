package com.packt;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.robot.Robot;
import java.time.format.DateTimeFormatter;

import java.awt.image.BufferedImage;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.layout.*;

import javafx.geometry.*;
import javafx.scene.shape.*;
import javafx.scene.effect.*;
import javafx.scene.control.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;
import javafx.scene.image.*;
import javafx.embed.swing.SwingFXUtils;
import java.io.*;
import javax.imageio.*;
import javax.imageio.stream.*;
import java.util.Iterator;
import javax.imageio.plugins.tiff.*;
import javax.imageio.metadata.*;
import javafx.scene.input.*;
import java.time.*;



public class RobotAPIDemo{

	static public TextField nameField;
	static public Button greeting;
	static public Robot robot;
	static public CountDownLatch appStartLatch = new CountDownLatch(1);
	static public CountDownLatch btnActionLatch = new CountDownLatch(1);
	static public Stage appStage;

	public static void typeName(){
		Platform.runLater(() -> {
			Bounds textBoxBounds = nameField.localToScreen(nameField.getBoundsInLocal());
			System.out.println(textBoxBounds);
			robot.mouseMove(textBoxBounds.getMinX(), textBoxBounds.getMinY());
			robot.mouseClick(MouseButton.PRIMARY);
			robot.keyType(KeyCode.CAPS);
			robot.keyType(KeyCode.S);
			robot.keyType(KeyCode.CAPS);
			robot.keyType(KeyCode.A);
			robot.keyType(KeyCode.N);
			robot.keyType(KeyCode.A);
			robot.keyType(KeyCode.U);
			robot.keyType(KeyCode.L);
			robot.keyType(KeyCode.L);
			robot.keyType(KeyCode.A);
		});
	}

	public static void clickButton(){
		Platform.runLater(() -> {
			//click the button
			Bounds greetBtnBounds = greeting.localToScreen(greeting.getBoundsInLocal());
			
			System.out.println(greetBtnBounds);
			robot.mouseMove(greetBtnBounds.getCenterX(), greetBtnBounds.getCenterY());
			robot.mouseClick(MouseButton.PRIMARY);
		});
	}

	public static void captureScreen(){
		Platform.runLater(() -> {
			try{
						
				WritableImage screenCapture = new WritableImage(Double.valueOf(appStage.getWidth()).intValue(), 
				Double.valueOf(appStage.getHeight()).intValue());
				//WritableImage screenCapture = robot.getScreenCapture()
				robot.getScreenCapture(screenCapture, appStage.getX(), appStage.getY(), appStage.getWidth(), appStage.getHeight());

				BufferedImage screenCaptureBI = SwingFXUtils.fromFXImage(screenCapture, null);
				String timePart = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-dd-M-m-H-ss"));
				ImageIO.write(screenCaptureBI, "png", new File("screenCapture-" + timePart +".png"));
				Platform.exit();
			}catch(Exception ex){
				ex.printStackTrace();
			}
		});
	}

	public static void main(String[] args) throws Exception{
		new Thread(() -> Application.launch(RobotApplication.class, args)).start();
		
		waitForOperation(appStartLatch, 10, "Timed out waiting for JavaFX Application to Start");
		typeName();
		clickButton();
		waitForOperation(btnActionLatch, 10, "Timed out waiting for Button to complete operation");
		Thread.sleep(1000);
		captureScreen();
	}

	public static void waitForOperation(CountDownLatch latchToWaitFor, int seconds, String errorMsg) {
        try {
            if (!latchToWaitFor.await(seconds, TimeUnit.SECONDS)) {
                System.out.println(errorMsg);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
	}
	
	public static class RobotApplication extends Application{
	
		@Override
		public void start(Stage stage) throws Exception{
			robot = new Robot();
			GridPane gridPane = new GridPane();
			gridPane.setAlignment(Pos.CENTER);
			gridPane.setHgap(10);
			gridPane.setVgap(10);
			gridPane.setPadding(new Insets(25, 25, 25, 25));
	
			Text appTitle = new Text("Robot Demo");
			appTitle.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
			gridPane.add(appTitle, 0, 0, 2, 1);
	
			Label nameLbl = new Label("Name");
			nameField = new TextField();
			gridPane.add(nameLbl, 0, 1);
			gridPane.add(nameField, 1, 1);
	
			greeting = new Button("Greet");
			gridPane.add(greeting, 1, 2);
	
			Text resultTxt = new Text();
			resultTxt.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
			gridPane.add(resultTxt, 0, 5, 2, 1);
	
			greeting.setOnAction((event) -> {
				
				String name = nameField.getText();
				StringBuilder resultBuilder = new StringBuilder();
				if ( name != null && name.length() > 0 ){
					resultBuilder.append("Hello, ").append(name).append("\n");
				}else{
					resultBuilder.append("Please enter the name");
				}
				resultTxt.setText(resultBuilder.toString());
				btnActionLatch.countDown();
			});
	
			
	
			Scene scene = new Scene(gridPane, 300, 250);
			
			stage.setTitle("Age calculator");
			stage.setScene(scene);
			stage.setAlwaysOnTop(true);
			stage.addEventHandler(WindowEvent.WINDOW_SHOWN, e -> 
				Platform.runLater(appStartLatch::countDown));
			stage.show();
			appStage = stage;
		}
	}


}

