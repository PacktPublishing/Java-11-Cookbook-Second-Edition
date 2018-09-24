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

public class BubbleChartDemo extends Application{
	
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

		final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Hour");
        yAxis.setLabel("Visits");
        final BubbleChart<Number,Number> bubbleChart = 
            new BubbleChart<>(xAxis,yAxis);

       	List<StoreVisit> data = getData();
       	Integer maxSale = getMaxSale(data);
		XYChart.Series<Number,Number>  series = new XYChart.Series<>();
		series.setName("Store Visits");
		data.forEach(sv -> {
			series.getData().add(new XYChart.Data<Number, Number>(
        		sv.hour, sv.visits, (sv.sales/(maxSale  * 1d)) * 2
        	));
		});

		bubbleChart.getData().add(series);
		gridPane.add(bubbleChart, 1, 1);
		
		Scene scene = new Scene(gridPane, 600, 400);
		stage.setTitle("Bubble Charts");
		stage.setScene(scene);
		stage.show();
	}

	private Integer getMaxSale(List<StoreVisit> data){
		return data.stream()
			.mapToInt(StoreVisit::getSales)
			.max()
			.getAsInt();
	}

	private List<StoreVisit> getData()
		throws IOException{
		Scanner reader = new Scanner(getClass()
			.getModule()
			.getResourceAsStream("com/packt/store")
		);

		List<StoreVisit> data = new LinkedList<>();
		int id = 1;
		while(reader.hasNext()){
			String line = reader.nextLine();
			String[] elements = line.split(",");
			StoreVisit sv = new StoreVisit(elements);
			data.add(sv);
		}
		return data;
	}
}