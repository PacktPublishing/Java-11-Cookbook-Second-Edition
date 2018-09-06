package com.packt;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;
import javafx.scene.*;
import javafx.scene.media.*;
import javafx.scene.media.MediaPlayer.Status;
import javafx.beans.value.*;
import javafx.scene.control.*;
import javafx.stage.Screen;
import javafx.geometry.Rectangle2D;
import java.io.*;

public class EmbedAudioVideoDemo extends Application{
	
	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) {
		
		// Create the media source
		File file = new File("sample_video1.mp4");
		Media media = new Media(file.toURI().toString());

		MediaPlayer mediaPlayer = new MediaPlayer(media);
		//mediaPlayer.setAutoPlay(true);
		mediaPlayer.statusProperty().addListener(
	        new ChangeListener<Status>() {
	            public void changed(ObservableValue ov, Status oldStatus, Status newStatus) {
	            	System.out.println(oldStatus +"->" + newStatus);
	            }
        	}
        );

		Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

		MediaView mediaView = new MediaView(mediaPlayer);
		mediaView.setFitWidth(350);
		mediaView.setFitHeight(350);
		

		Button pauseB = new Button("Pause");
		pauseB.setOnAction(e -> {
			mediaPlayer.pause();
		});
		Button playB = new Button("Play");
		playB.setOnAction(e -> {
			mediaPlayer.play();
		});
		Button stopB = new Button("Stop");
		stopB.setOnAction(e -> {
			mediaPlayer.stop();
		});

		HBox controlsBox = new HBox(10);
		controlsBox.getChildren().addAll(pauseB, playB, stopB);

		VBox vbox = new VBox();
		vbox.getChildren().addAll(mediaView, controlsBox);
		
		// Create and set the Scene.
		Scene scene = new Scene(vbox);
		stage.setScene(scene);
		// Name and display the Stage.
		stage.setTitle("Media Demo");
		
		/*stage.setX(primaryScreenBounds.getMinX());
		stage.setY(primaryScreenBounds.getMinY());*/
		stage.setWidth(400);
		stage.setHeight(400);
		stage.show();
	}
	
}