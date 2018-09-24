package com.packt;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.time.*;

public class CssJavaFxDemo extends Application{

	
	@Override
	public void start(Stage stage) {
		
		GridPane gridPane = new GridPane();
		gridPane.setAlignment(Pos.CENTER);
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		gridPane.setPadding(new Insets(25, 25, 25, 25));

		Button primaryBtn = new Button("Primary");
		primaryBtn.getStyleClass().add("btn");
		primaryBtn.getStyleClass().add("btn-primary");
		gridPane.add(primaryBtn, 0, 1);

		Button successBtn = new Button("Sucess");
		successBtn.getStyleClass().add("btn");
		successBtn.getStyleClass().add("btn-success");
		gridPane.add(successBtn, 1, 1);

		Button dangerBtn = new Button("Danger");
		dangerBtn.getStyleClass().add("btn");
		dangerBtn.getStyleClass().add("btn-danger");
		gridPane.add(dangerBtn, 2, 1);
		
		Label label = new Label("Default Label");
		label.getStyleClass().add("badge");
		gridPane.add(label, 0, 2);

		Label infoLabel = new Label("Info Label");
		infoLabel.getStyleClass().add("badge");
		infoLabel.getStyleClass().add("badge-info");
		gridPane.add(infoLabel, 1, 2);

		TextField bigTextField = new TextField();
		bigTextField.getStyleClass().add("big-input");
		gridPane.add(bigTextField, 0, 3, 3, 1);


		ToggleGroup group = new ToggleGroup();
	    RadioButton bigRadioOne = new RadioButton("First");
	    bigRadioOne.getStyleClass().add("big-radio");
	    bigRadioOne.setToggleGroup(group);
	    bigRadioOne.setSelected(true);
	    gridPane.add(bigRadioOne, 0, 4);

	    RadioButton bigRadioTwo = new RadioButton("Second");
	    bigRadioTwo.setToggleGroup(group);
	    bigRadioTwo.getStyleClass().add("big-radio");
		gridPane.add(bigRadioTwo, 1, 4);

		Scene scene = new Scene(gridPane, 600, 500);
		scene.getStylesheets().add("com/packt/stylesheet.css");
		stage.setTitle("Age calculator");
		stage.setScene(scene);
		stage.show();
	}
	public static void main(String[] args) {
		Application.launch(args);
	}

	
}