package com.packt;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import java.util.*;
import java.util.function.*;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.scene.chart.*;
import java.util.stream.*;

public class ScatterChartDemo extends Application{
	
	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) throws IOException {

		GridPane gridPane = new GridPane();
		gridPane.setAlignment(Pos.CENTER);
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		gridPane.setPadding(new Insets(25, 25, 25, 25));

		Map<String, List<FallOfWicket>> fow = getFallOfWickets();

		final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Age");
        yAxis.setLabel("Marks");
        final ScatterChart<Number,Number> scatterChart = 
            new ScatterChart<>(xAxis,yAxis);
        scatterChart.getData().add(getSeries(
        	fow.get("NZ"),
        	"NZ"
        ));
        scatterChart.getData().add(getSeries(
        	fow.get("IND"),
        	"IND"
        ));
        
		gridPane.add(scatterChart, 1, 1);
		
		Scene scene = new Scene(gridPane, 600, 400);
		stage.setTitle("Bubble Charts");
		stage.setScene(scene);
		stage.show();
	}

	private XYChart.Series<Number, Number> getSeries(
		List<FallOfWicket> data,
		String seriesName
	){
		XYChart.Series<Number,Number> series = 
		  new XYChart.Series<>();
		series.setName(seriesName);
		data.forEach(s -> {
			series.getData().add(new XYChart.Data<Number, Number>(
        		s.over, s.score
        	));
		});
		return series;
	}

	private Map<String, List<FallOfWicket>> getFallOfWickets()
		throws IOException{
		Scanner reader = new Scanner(getClass()
			.getModule()
			.getResourceAsStream("com/packt/wickets")
		);
		Map<String, List<FallOfWicket>> data = 
			new HashMap<>();
		while(reader.hasNext()){
			String line = reader.nextLine();
			String[] elements = line.split(",");
			String country = elements[0];
			if ( !data.containsKey(country)){
				data.put(country, 
				  new ArrayList<FallOfWicket>());
			}
			data.get(country).add(new FallOfWicket(elements));
		}
		return data;
	}

	
}