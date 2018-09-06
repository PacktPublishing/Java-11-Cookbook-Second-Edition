package com.packt;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.shape.*;
import javafx.scene.effect.*;
import javafx.scene.control.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;

public class EffectsDemo extends Application{
	
	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) {
		GridPane gridPane = new GridPane();
		gridPane.setAlignment(Pos.CENTER);
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		gridPane.setPadding(new Insets(25, 25, 25, 25));

		Rectangle r1 = new Rectangle(100,25, Color.BLUE);
		Rectangle r2 = new Rectangle(100,25, Color.RED);
		Rectangle r3 = new Rectangle(100,25, Color.ORANGE);
        r1.setEffect(new BoxBlur(10,10,3));
		r2.setEffect(new MotionBlur(90, 15.0));
		r3.setEffect(new GaussianBlur(15.0));

		gridPane.add(r1,1,1);
		gridPane.add(r2,2,1);
		gridPane.add(r3,3,1);

		Circle c1 = new Circle(20, Color.BLUE);
		c1.setEffect(new DropShadow(0, 4.0, 0, Color.YELLOW));
		Circle c2 = new Circle(20, Color.RED);
		c2.setEffect(new InnerShadow(0, 4.0, 4.0, Color.ORANGE));
		Circle c3 = new Circle(20, Color.GREEN);

		gridPane.add(c1,1,2);
		gridPane.add(c2,2,2);
		gridPane.add(c3,3,2);

		Text t = new Text("Reflection Sample");
		t.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		t.setFill(Color.BLUE);
		Reflection reflection = new Reflection();
		reflection.setFraction(0.8);
		t.setEffect(reflection);
		gridPane.add(t, 1, 3, 3, 1);

		Scene scene = new Scene(gridPane, 500, 300);
		stage.setScene(scene);
		stage.setTitle("Effects Demo");
		stage.show();
        
	}
	
}