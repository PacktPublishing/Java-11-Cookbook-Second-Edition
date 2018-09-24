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

public class AreaChartDemo extends Application{

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

		final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Month");
        yAxis.setLabel("Price");
        final AreaChart<String,Number> areaChart = 
            new AreaChart<>(xAxis,yAxis);

       	List<OilPrice> crudeOil = getOilData("crude-oil");

		List<OilPrice> brentOil = getOilData("brent-oil");

		areaChart.getData().add(getSeries(
        	"Crude Oil", crudeOil
        ));

        areaChart.getData().add(getSeries(
        	"Brent Oil", brentOil
        ));
		gridPane.add(areaChart, 1, 1);
		
		Scene scene = new Scene(gridPane, 800, 600);
		stage.setTitle("Area Charts");
		stage.setScene(scene);
		stage.show();
	}

	private XYChart.Series<String,Number> getSeries(
		String seriesName, List<OilPrice> data
	) throws IOException{
		XYChart.Series<String,Number>  series = new XYChart.Series<>();
        series.setName(seriesName);
        data.forEach(d -> {
        	series.getData().add(new XYChart.Data<String, Number>(
        		d.period, d.value
        	));
        });
        return series;
	}
	
	private List<OilPrice> getOilData(String oilType)
		throws IOException{
		Scanner reader = new Scanner(getClass()
			.getModule()
			.getResourceAsStream("com/packt/"+oilType)
		);

		List<OilPrice> data = new LinkedList<>();
		while(reader.hasNext()){
			String line = reader.nextLine();
			String[] elements = line.split("\t");
			OilPrice op = new OilPrice();
			op.period = elements[0];
			op.value = Double.parseDouble(elements[1]);
			data.add(op);	
		}
		Collections.reverse(data);
		return data;
	}
}