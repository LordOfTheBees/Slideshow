package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class Main extends Application
{

	 private Stage primaryStage;
	@Override
	public void start(Stage primaryStage)
	{
		try
		{
			this.primaryStage = primaryStage;
	        this.primaryStage.setTitle("Slideshow");
	        initRootLayout();
	    
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void initRootLayout()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("work.fxml"));
            AnchorPane rootLayout = (AnchorPane) loader.load();

            Scene scene = new Scene(rootLayout);

            primaryStage.setScene(scene);
            primaryStage.show();
            
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
	public static void main(String[] args)
	{
		launch(args);
	}
}
