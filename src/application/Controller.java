package application;

import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Controller
{
	 @FXML private Button leftMoveObject, rightMoveObject, setTime;
	 @FXML private HBox musicBox, videoAndPictureBox;
	 @FXML private TextField fromText, toText;
	 @FXML private MenuItem openProject, saveProject, addNewProject, deleteElement, aboutMenu;
	 @FXML private Pane videoPane, picturePane;
	 @FXML private ImageView pictureThumbNail, videoThumbNail;
	 @FXML private Hyperlink importPicture;
	 @FXML private Hyperlink importVideo;
	 @FXML private Hyperlink importAudio;

	 Music music = new Music();
	 Video video = new Video();
	 Picture pic = new Picture();

	 @FXML
	    public void initialize()
	    {
		 importPicture.setOnAction(new EventHandler<ActionEvent>()
		 {
			    @Override
			    public void handle(ActionEvent e)
			    {
			    	pic.loadFile();
			    }
		});
		 importAudio.setOnAction(new EventHandler<ActionEvent>()
		 {
			    @Override
			    public void handle(ActionEvent e)
			    {

			    	music.loadFile();
			    }
		});
		 importVideo.setOnAction(new EventHandler<ActionEvent>()
		 {
			    @Override
			    public void handle(ActionEvent e)
			    {
			    	video.loadFile();
			    }
		});
		 openProject.setOnAction(new EventHandler<ActionEvent>()
		 {
			    @Override public void handle(ActionEvent e)
			    {
			    	FileChooser fileChooser = new FileChooser();
	            	fileChooser.setTitle("Open Resource File");

	                FileInputStream fis = null;
	                File file = null;
	                ObjectInputStream in = null;

	                //десериализация
	                try
	                {
	                	file = fileChooser.showOpenDialog(new Stage());
	                	String filename = file.getAbsolutePath();
	                	fis = new FileInputStream(filename);
	                    in = new ObjectInputStream(fis);

	                }
	                catch(IOException ex)
	                {
	                	alertWindowError("Input error");
	                }
			    }
		});

		 saveProject.setOnAction(new EventHandler<ActionEvent>()
		 {
			    @Override public void handle(ActionEvent e)
			    {
			    	FileChooser fileChooser = new FileChooser();
	            	fileChooser.setTitle("Save as...");
	            	File file = null;

	                FileOutputStream fos = null;
	                ObjectOutputStream out = null;

	                //сериализация
	                try
	                {
	                 	file = fileChooser.showSaveDialog(new Stage());
	                	String filename = file.getAbsolutePath();

	                    fos = new FileOutputStream(filename);
	                    out = new ObjectOutputStream(fos);

	                    out.close();
	                 }
	                 catch(IOException ex)
	                {
	                	 alertWindowError("Output error");
	                }
			    }
		});
		 addNewProject.setOnAction(new EventHandler<ActionEvent>()
		 {
			    @Override public void handle(ActionEvent e)
			    {

			    }
		});
		 deleteElement.setOnAction(new EventHandler<ActionEvent>()
		 {
			    @Override public void handle(ActionEvent e)
			    {

			    }
		});
		 aboutMenu.setOnAction(new EventHandler<ActionEvent>()
		 {
			    @Override public void handle(ActionEvent e)
			    {

			    }
		});
		 leftMoveObject.setOnMousePressed(new EventHandler<MouseEvent>()
	        {
	    		@Override
	            public void handle(MouseEvent e)
	            {

	            }
	        }
	        );

		 rightMoveObject.setOnMousePressed(new EventHandler<MouseEvent>()
	        {
	    		@Override
	            public void handle(MouseEvent e)
	            {

	            }
	        }
	        );
		 setTime.setOnMousePressed(new EventHandler<MouseEvent>()
	        {


	    		@Override
	            public void handle(MouseEvent e)
	            {
	    			System.out.println("Button clicked");
	    			if(fromText.getText().isEmpty() && toText.getText().isEmpty())
	            	{
	            		Toolkit.getDefaultToolkit().beep();
	            	}
	            	else
	            	{
	            		fromText.clear();
	            		toText.clear();
	            		String from = fromText.getText();
	            		String to = toText.getText();

	            	}
	            }
	        }
	        );
	    }


	 public void alertWindowError(String str)
	 {
	 	Alert alert = new Alert(Alert.AlertType.ERROR);
	 	Toolkit.getDefaultToolkit().beep();
	 	alert.setContentText(str);
	 	alert.showAndWait();
	 }
}



