package com.packt;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;
import java.io.IOException;

public class FxmlGuiDemo extends Application{

	@Override
	public void start(Stage stage) throws IOException{

		FXMLLoader loader = new FXMLLoader();
		Pane pane = (Pane)loader.load(getClass()
			.getModule()
			.getResourceAsStream("com/packt/fxml_age_calc_gui.fxml")
		);
        Scene scene = new Scene(pane,300, 250);
		stage.setTitle("Age calculator");
		stage.setScene(scene);
		stage.show();
	}
	public static void main(String[] args) {
		Application.launch(args);
	}

	
}