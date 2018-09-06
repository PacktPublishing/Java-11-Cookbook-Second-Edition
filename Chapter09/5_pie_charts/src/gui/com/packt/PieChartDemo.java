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
import com.packt.processor.*;
public class PieChartDemo extends Application{

	
	@Override
	public void start(Stage stage) throws IOException {

		GridPane gridPane = new GridPane();
		gridPane.setAlignment(Pos.CENTER);
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		gridPane.setPadding(new Insets(25, 25, 25, 25));


		StudentDataProcessor sdp = new StudentDataProcessor();
		List<Student> students = sdp.loadStudent();
		System.out.println("students : " + students.size());

		PieChart motherOccupationBreakUp = 
			getStudentCountByOccupation(
				students,
				Student::getMotherJob
			);
		motherOccupationBreakUp.setTitle("Mother's Occupation");
		gridPane.add(motherOccupationBreakUp, 1,1);

		PieChart fatherOccupationBreakUp = 
			getStudentCountByOccupation(
				students,
				Student::getFatherJob
			);
		fatherOccupationBreakUp.setTitle("Father's Occupation");
		gridPane.add(fatherOccupationBreakUp, 2,1);

		Scene scene = new Scene(gridPane, 800, 600);
		stage.setTitle("Pie Charts");
		stage.setScene(scene);
		stage.show();
	}

	private PieChart getStudentCountByOccupation(
		List<Student> students,
		Function<Student, String> classifier
	){
		Map<String, Long> occupationBreakUp = 
        	students.stream().collect(
    			Collectors.groupingBy(
    				classifier,
    				Collectors.counting()
    			)
    		);
        List<PieChart.Data> pieChartData = 
        	new ArrayList<>();

        occupationBreakUp.forEach((k, v) -> {
        	pieChartData.add(new PieChart.Data(k.toString(), v));
        });

        PieChart chart = new PieChart(
        	FXCollections.observableList(pieChartData)
        );
        return chart;
	}

	public static void main(String[] args) {
		Application.launch(args);
	}

	
}