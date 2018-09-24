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

public class CreateGuiDemo extends Application{

	@Override
	public void start(Stage stage) {
		
		GridPane gridPane = new GridPane();
		gridPane.setAlignment(Pos.CENTER);
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		gridPane.setPadding(new Insets(25, 25, 25, 25));

		Text appTitle = new Text("Age calculator");
		appTitle.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
		gridPane.add(appTitle, 0, 0, 2, 1);

		Label nameLbl = new Label("Name");
		TextField nameField = new TextField();
		gridPane.add(nameLbl, 0, 1);
		gridPane.add(nameField, 1, 1);

		Label dobLbl = new Label("Date of birth");
		gridPane.add(dobLbl, 0, 2);
		DatePicker dateOfBirthPicker = new DatePicker();
		gridPane.add(dateOfBirthPicker, 1, 2);

		Button ageCalculator = new Button("Calculate");
		gridPane.add(ageCalculator, 1, 3);

		Text resultTxt = new Text();
		resultTxt.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
		gridPane.add(resultTxt, 0, 5, 2, 1);

		
		ageCalculator.setOnAction((event) -> {
			String name = nameField.getText();
			LocalDate dob = dateOfBirthPicker.getValue();
			if ( dob != null ){
				LocalDate now = LocalDate.now();
				Period period = Period.between(dob, now);
				StringBuilder resultBuilder = new StringBuilder();

				if ( name != null && name.length() > 0 ){
					resultBuilder.append("Hello, ").append(name).append("\n");
				}
				resultBuilder.append(
					String.format("Your age is %d years %d months %d days", 
						period.getYears(), period.getMonths(), period.getDays())
				);
				resultTxt.setText(resultBuilder.toString());
			}
			
		});

		

		Scene scene = new Scene(gridPane, 300, 250);

		stage.setTitle("Age calculator");
		stage.setScene(scene);
		stage.show();
	}
	public static void main(String[] args) {
		Application.launch(args);
	}
}