package gui;

import java.util.Arrays;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import transactions.TransactionLog;

public class MoneyChart extends Application {
	
	   @SuppressWarnings("unchecked")
	   @Override
	   public void start(Stage stage) { 
		  
	      //Defining the axes              
	      CategoryAxis xAxis = new CategoryAxis();  
	      xAxis.setCategories(FXCollections.<String>
	      observableArrayList(Arrays.asList("Pets", "Emergency", "Bills", "Leisure")));
	      xAxis.setLabel("category");
	       
	      NumberAxis yAxis = new NumberAxis();
	      yAxis.setLabel("Total");
	     
	      //Creating the Bar chart
	      BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis); 
	      barChart.setTitle("Spread among all transaction categories");
	        
	      //Create transactions series   
	      XYChart.Series<String, Number> transactions = new XYChart.Series<>();
	      
	      //Remove bar chart animations
	      barChart.setAnimated(false);  
	      //Creating a Group object 
	      Group root = new Group(barChart);
	        
	      //Creating a scene object
	      Scene scene = new Scene(root, 600, 400);

	      //Setting title to the Stage
	      stage.setTitle("Bar Chart");
	        
	      //Adding scene to the stage
	      stage.setScene(scene);
	        
	      //Displaying the contents of the stage
	      stage.show();
	      Timeline timeline = new Timeline(new KeyFrame(Duration.millis(60), ev ->
	      {
	    	  //Live-updates the transactions as they're added.
	    	  transactions.getData().add(new XYChart.Data<>("Pets", TransactionLog.totPets));
	    	  transactions.getData().add(new XYChart.Data<>("Emergency", TransactionLog.totEmerg));
	    	  transactions.getData().add(new XYChart.Data<>("Bills", TransactionLog.totBills));
	    	  transactions.getData().add(new XYChart.Data<>("Leisure", TransactionLog.totLeisure));
	      }));
	      
	      barChart.getData().addAll(transactions);
	      
	      timeline.setCycleCount(Animation.INDEFINITE);
	      timeline.play();
	      stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
	    	    @Override
	    	    public void handle(WindowEvent t) {
	    	        Platform.exit();
	    	        System.exit(0);
	    	    }
	    	});
	   }
	   
	   //Variables available to all methods
	   TransactionLog tsLog = new TransactionLog();
}
