package application;

import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.embed.swing.SwingFXUtils;

public class Controller
{
	 @FXML private Button leftMoveObject, rightMoveObject, setTime;
	 @FXML private HBox musicBox, videoAndPictureBox;
	 @FXML private TextField fromText, toText;
	 @FXML private MenuItem openProject, saveProject, addNewProject, deleteElement, aboutMenu;
	 @FXML private Hyperlink importPicture;
	 @FXML private Hyperlink importVideo;
	 @FXML private Hyperlink importAudio;
	 @FXML private BorderPane player;
	 @FXML private Pane insertedElement;
	 @FXML private ImageView insertedPreview;

	private ArrayList<ImageView> preview_image = new ArrayList<ImageView>();
	 Project project = new Project();
	 public int active_element;
	 public ArrayList<MediaFiles> files  = new ArrayList<MediaFiles>();
	 @FXML
	    public void initialize()
	    {
		 importPicture.setOnAction(new EventHandler<ActionEvent>()
		 {
			    @Override
			    public void handle(ActionEvent e)
			    {
			    	FileChooser fileChooser = new FileChooser();
			    	fileChooser.setTitle("Open Resource File");

			        File file = null;
			    	file = fileChooser.showOpenDialog(new Stage());
			    	project.loadImage(file);

					files = project.getContent();

					ImageView image = new ImageView();
					image.setFitHeight(60);
					image.setFitWidth(60);
					image.setId("previewImage" + preview_image.size());

					Image tmp_image = new Image(file.toURI().toString());
					image.setImage(tmp_image);

					videoAndPictureBox.getChildren().add(image);
					preview_image.add(image);
			    }
		});
		 importAudio.setOnAction(new EventHandler<ActionEvent>()
		 {
			    @Override
			    public void handle(ActionEvent e)
			    {
			    	FileChooser fileChooser = new FileChooser();
			    	fileChooser.setTitle("Open Resource File");

			        File file = null;

			    	file = fileChooser.showOpenDialog(new Stage());
			    	String filename = file.getAbsolutePath();
			    	filename = filename.replace("\\", "/");
			    	project.loadMusic(file);
			    }
		});
		 importVideo.setOnAction(new EventHandler<ActionEvent>()
		 {
			    @Override
			    public void handle(ActionEvent e)
			    {
			    	FileChooser fileChooser = new FileChooser();
			    	fileChooser.setTitle("Open Resource File");

			    	File file = fileChooser.showOpenDialog(new Stage());
			    	String filename = file.getAbsolutePath();
			    	project.loadVideo(file);

					ImageView image = new ImageView();
					image.setFitHeight(60);
					image.setFitWidth(60);
					image.setId("previewImage" + preview_image.size());

					files = project.getContent();

					WritableImage tmp_image = new WritableImage(60, 60);
					tmp_image = SwingFXUtils.toFXImage(files.get(files.size() - 1).getPreview(), tmp_image);

					image.setImage(tmp_image);
					videoAndPictureBox.getChildren().add(image);
					preview_image.add(image);
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

		 leftMoveObject.setOnMousePressed(new EventHandler<MouseEvent>() {
	    		@Override
	            public void handle(MouseEvent e)
	            {
					files = project.getContent();
					files.size();
	            }
	        });

		 rightMoveObject.setOnMousePressed(new EventHandler<MouseEvent>() {
	    		@Override
	            public void handle(MouseEvent e)
	            {

	            }
	        });

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
	            		int index = 0; //индекс кликнутой картинки
	            		project.setTime(from, to, index);
	            	}
	            }
	        }
	        );

		 //показывать слайдшоу на экране
	    }


	 public void alertWindowError(String str)
	 {
	 	Alert alert = new Alert(Alert.AlertType.ERROR);
	 	Toolkit.getDefaultToolkit().beep();
	 	alert.setContentText(str);
	 	alert.showAndWait();
	 }

	 private HBox addToolBar( MediaPlayer mediaPlayer)
	 {
		 HBox toolBar = new HBox();
		 toolBar.setPadding(new Insets(20));
		 toolBar.setAlignment(Pos.CENTER);
		 toolBar.alignmentProperty().isBound();
		 toolBar.setSpacing(5);

		 Image playButtonImage = new Image(getClass().getResourceAsStream("Play.png"));
		 Button playButton = new Button();
		 playButton.setGraphic(new ImageView(playButtonImage));
		 playButton.setStyle("-fx-background-color: Black");

		 playButton.setOnAction((ActionEvent e) ->
		 {
			 mediaPlayer.play();
		 });
		 playButton.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) ->
		 {
			 playButton.setStyle("-fx-background-color: Black");
			 playButton.setStyle("-fx-body-color: Black");
		 });
		 playButton.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) ->
		 {
			 playButton.setStyle("-fx-background-color: Black");
		 });

		 Image pausedButtonImage = new Image(getClass().getResourceAsStream("Pause.png"));
		 Button pauseButton = new Button();
		 pauseButton.setGraphic(new ImageView(pausedButtonImage));
		 pauseButton.setStyle("-fx-background-color: Black");

		 pauseButton.setOnAction((ActionEvent e) ->
		 {
			 mediaPlayer.pause();
		 });

		 pauseButton.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) ->
		 {
			 pauseButton.setStyle("-fx-background-color: Black");
			 pauseButton.setStyle("-fx-body-color: Black");
		 });
		 pauseButton.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) ->
		 {
			 pauseButton.setStyle("-fx-background-color: Black");
		 });


		 toolBar.getChildren().addAll(playButton, pauseButton);
		 return toolBar;
	}

	private Image pictureToPreview(Picture picture){
		String filename = picture.getFile().getAbsolutePath();
		filename = filename.replace("\\", "/");
		Image image = new Image(filename);
		return image;
	}
}



