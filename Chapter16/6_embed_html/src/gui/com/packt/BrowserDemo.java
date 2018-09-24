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
import javafx.scene.web.*;
import javafx.concurrent.Worker.State;
import javafx.beans.value.*;
import javafx.scene.control.*;
import javafx.stage.Screen;
import javafx.geometry.Rectangle2D;

public class BrowserDemo extends Application{
	
	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) {

		WebView webView = new WebView();
		WebEngine webEngine = webView.getEngine();
		webEngine.load("http://www.google.com/");
		TextField webAddress = new TextField("http://www.google.com/");
		webEngine.getLoadWorker().stateProperty().addListener(
	        new ChangeListener<State>() {
	            public void changed(ObservableValue ov, State oldState, State newState) {
	            	System.out.println(newState);
	                if (newState == State.SUCCEEDED) {
	                	stage.setTitle(webEngine.getTitle());
	                	webAddress.setText(webEngine.getLocation());
	                }
	            }
        	}
        );

		
		Button goButton = new Button("Go");
		goButton.setOnAction((event) -> {
			String url = webAddress.getText();
			if ( url != null && url.length() > 0){
				webEngine.load(url);
			}
		});
		Button prevButton = new Button("Prev");
		prevButton.setOnAction(e -> {
			webEngine.executeScript("history.back()");
		});

		Button nextButton = new Button("Next");
		nextButton.setOnAction(e -> {
			WebHistory wh = webEngine.getHistory();
			Integer historySize = wh.getEntries().size();
			Integer currentIndex = wh.getCurrentIndex();
			if ( currentIndex < (historySize - 1)){
				wh.go(1);
			}
		});

		Button reloadButton = new Button("Refresh");
		reloadButton.setOnAction(e -> {
			webEngine.reload();

		});

		HBox addressBar = new HBox(10);

		addressBar.setPadding(new Insets(10, 5, 10, 5));
		addressBar.setHgrow(webAddress, Priority.ALWAYS);
		addressBar.getChildren().addAll(prevButton, nextButton, reloadButton, 
			webAddress, goButton);
		
		Label websiteLoadingStatus = new Label();

		webEngine.getLoadWorker().workDoneProperty().addListener(
        	new ChangeListener<Number>(){
 				public void changed(ObservableValue ov, Number oldState, Number newState) {
	            	if (newState.doubleValue() != 100.0){
	            		websiteLoadingStatus.setText("Loading " + webAddress.getText());
	            	}else{
	            		websiteLoadingStatus.setText("Done");
	            	}
	            }
        	}
        );
		
		VBox root = new VBox();
		root.getChildren().addAll(addressBar, webView, websiteLoadingStatus);
		Scene scene = new Scene(root);
		
		Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
		stage.setTitle("Web Browser");
		stage.setScene(scene);
		stage.setX(primaryScreenBounds.getMinX());
		stage.setY(primaryScreenBounds.getMinY());
		stage.setWidth(primaryScreenBounds.getWidth());
		stage.setHeight(primaryScreenBounds.getHeight());

		stage.show();
        
	}
	
}