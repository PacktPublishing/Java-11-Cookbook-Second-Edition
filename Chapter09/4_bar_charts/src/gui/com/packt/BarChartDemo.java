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
public class BarChartDemo extends Application{

	
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
		

		BarChart<String, Number> avgGradeByMotherEdu = 
			getAvgGradeByEducationBarChart(
				students, 
				Student::getMotherEducation
			);
		avgGradeByMotherEdu.setTitle("Average grade by Mother's Education");
		gridPane.add(avgGradeByMotherEdu, 1,1);

		BarChart<String, Number> avgGradeByFatherEdu = 
			getAvgGradeByEducationBarChart(
				students, 
				Student::getFatherEducation
			);
		avgGradeByFatherEdu.setTitle("Average grade by Father's Education");
		gridPane.add(avgGradeByFatherEdu, 2,1);

		Scene scene = new Scene(gridPane, 800, 600);
		stage.setTitle("Bar Charts");
		stage.setScene(scene);
		stage.show();
	}

	private BarChart<String, Number> getAvgGradeByEducationBarChart(
		List<Student> students,
		Function<Student, ParentEducation> classifier
	){
    
 		final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String,Number> bc = 
            new BarChart<>(xAxis,yAxis);
        xAxis.setLabel("Education");
        yAxis.setLabel("Grade");

        bc.getData().add(getSeries(
        	"G1", 
        	summarize(students, classifier, Student::getFirstTermGrade)
        ));
        bc.getData().add(getSeries(
        	"G2", 
        	summarize(students, classifier, Student::getSecondTermGrade)
        ));
        bc.getData().add(getSeries(
        	"Final", 
        	summarize(students, classifier, Student::getFinalGrade)
        ));
     
        return bc;
	}

	private Map<ParentEducation, IntSummaryStatistics> summarize(
		List<Student> students,
		Function<Student, ParentEducation> classifier,
		ToIntFunction<Student> mapper
	){
		Map<ParentEducation, IntSummaryStatistics> statistics = 
        	students.stream().collect(
    			Collectors.groupingBy(
    				classifier,
    				Collectors.summarizingInt(mapper)
    			)
    		);
    	return statistics;
	}
	private XYChart.Series<String,Number> getSeries(
		String seriesName,
		Map<ParentEducation, IntSummaryStatistics> statistics
	){
		XYChart.Series<String,Number>  series = new XYChart.Series<>();
        series.setName(seriesName);
        statistics.forEach((k, v) -> {
        	series.getData().add(new XYChart.Data<String, Number>(k.toString(),v.getAverage()));
        });
        return series;
	}


	public static void main(String[] args) {
		Application.launch(args);
	}

	
}