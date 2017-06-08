package application;

import java.awt.Toolkit;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.embed.swing.SwingFXUtils;

public class Controller
{
	 @FXML private Button leftMoveObject, rightMoveObject, setTime, deleteButton, playButton;
	 @FXML private HBox musicBox, videoAndPictureBox;
	 @FXML private TextField fromText, toText;
	 @FXML private MenuItem openProject, saveProject, addNewProject, deleteElement, aboutMenu;
	 @FXML private Hyperlink importPicture;
	 @FXML private Hyperlink importVideo;
	 @FXML private Hyperlink importAudio;
	 @FXML private BorderPane player;

	@FXML private ArrayList<ImageView> preview_image = new ArrayList<ImageView>();
	@FXML private ArrayList<BorderPane> border_pane_for_preview = new ArrayList<BorderPane>();
	@FXML private ArrayList<BorderPane> borderPanesAudio = new ArrayList<BorderPane>();
	private Project project = new Project();
	private int activeMediaElement = -1;
	private int activeAudioElement = -1;
	private ArrayList<MediaFiles> files  = new ArrayList<MediaFiles>();
	private ArrayList<Music> musicFiles  = new ArrayList<Music>();

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
					//files.get(files.size() - 1).getMP4();

					ImageView imageView = new ImageView();
					imageView.setFitHeight(50);
					imageView.setFitWidth(50);
					imageView.setId("" + preview_image.size());

					Image tmp_image = new Image(file.toURI().toString());
					imageView.setImage(tmp_image);

					BorderPane borderPane = new BorderPane();
					borderPane.setCenter(imageView);
					borderPane.setPrefWidth(60);
					borderPane.setPrefHeight(60);
					borderPane.setId("" + preview_image.size());

					videoAndPictureBox.getChildren().addAll(borderPane);

					border_pane_for_preview.add(borderPane);
					preview_image.add(imageView);
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
			    	project.loadMusic(file);

			    	Media media = new Media(new File(filename).toURI().toString());
			    	MediaPlayer mediaPlayer = new MediaPlayer(media);

			    	MediaView mediaView = new MediaView(mediaPlayer);
			    	mediaView.setMediaPlayer(mediaPlayer);
			    	BorderPane borderPane = new BorderPane();
					borderPane.setCenter(mediaView);

					MediaControl med = new MediaControl(mediaPlayer, borderPane);

					borderPane.setId("" + borderPanesAudio.size());
					borderPanesAudio.add(borderPane);
					musicBox.getChildren().addAll(borderPane);
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

					try
					{
						ImageView imageView = new ImageView();
						imageView.setFitHeight(50);
						imageView.setFitWidth(50);
						imageView.setId("" + preview_image.size());

						files = project.getContent();

						WritableImage tmp_image = new WritableImage(50, 50);
						tmp_image = SwingFXUtils.toFXImage(files.get(files.size() - 1).getPreview(), tmp_image);
						imageView.setImage(tmp_image);

						BorderPane borderPane = new BorderPane();
						borderPane.setCenter(imageView);
						borderPane.setPrefWidth(60);
						borderPane.setPrefHeight(60);
						borderPane.setId("" + preview_image.size());

						videoAndPictureBox.getChildren().addAll(borderPane);

						border_pane_for_preview.add(borderPane);
						preview_image.add(imageView);
					} catch (Exception ex) {
						// TODO Auto-generated catch block
						ex.printStackTrace();
					}
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

	                XMLDecoder decoder=null;

	                //десериализация
	                try
	                {
	                	file = fileChooser.showOpenDialog(new Stage());
	                	String filename = file.getAbsolutePath();
	                	fis = new FileInputStream(filename);
	                    decoder=new XMLDecoder(new BufferedInputStream(fis));
	                }
	                catch(IOException ex)
	                {
	                	alertWindowError("Input error");
	                }

	            	Project project=(Project)decoder.readObject();

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
	                XMLEncoder encoder = null;
	                //сериализация

	                try
	                {
	                 	file = fileChooser.showSaveDialog(new Stage());
	                	String filename = file.getAbsolutePath();

	                    fos = new FileOutputStream(filename);
	                    encoder=new XMLEncoder(new BufferedOutputStream(fos));

	                    encoder.writeObject(project);
	            		encoder.close();
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

		 leftMoveObject.setOnMousePressed(new EventHandler<MouseEvent>()
		 {
			 @Override
			 public void handle(MouseEvent e)
			 {
				 if((activeMediaElement > 0) && (activeMediaElement < preview_image.size())){
					 int first = activeMediaElement - 1;
					 int second = activeMediaElement;

					 //swap
					 swapPreviewImageView(first, second);
					 swapBorderPaneForPreview(first, second);
					 project.swap(first, second);
					 files = project.getContent();

					 videoAndPictureBox.getChildren().set(first + 1, border_pane_for_preview.get(first));
					 videoAndPictureBox.getChildren().set(second + 1, border_pane_for_preview.get(second));

					 border_pane_for_preview.get(first).setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));

					 activeMediaElement = activeMediaElement - 1;
				 } else if((activeAudioElement > 0) && (activeAudioElement < borderPanesAudio.size())){
					 int first = activeAudioElement - 1;
					 int second = activeAudioElement;

					 swapBorderPaneForAudio(first, second);

					 musicBox.getChildren().set(first + 1, borderPanesAudio.get(first));
					 musicBox.getChildren().set(second + 1, borderPanesAudio.get(second));

					 borderPanesAudio.get(first).setBackground(new Background(new BackgroundFill(Color.AQUA, CornerRadii.EMPTY, Insets.EMPTY)));

					 activeAudioElement = activeAudioElement - 1;
				 }
			 }
	        });

		 rightMoveObject.setOnMousePressed(new EventHandler<MouseEvent>()
		 {
			 @Override
			 public void handle(MouseEvent e)
			 {
				 if((activeMediaElement >= 0) && (activeMediaElement < preview_image.size() - 1)){
					 int first = activeMediaElement;
					 int second = activeMediaElement + 1;

					 //swap
					 swapPreviewImageView(first, second);
					 swapBorderPaneForPreview(first, second);
					 project.swap(first, second);
					 files = project.getContent();

					 videoAndPictureBox.getChildren().set(first + 1, border_pane_for_preview.get(first));
					 videoAndPictureBox.getChildren().set(second + 1, border_pane_for_preview.get(second));

					 border_pane_for_preview.get(second).setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));

					 activeMediaElement = activeMediaElement + 1;
				 } else if((activeAudioElement >= 0) && (activeAudioElement < borderPanesAudio.size() - 1)){
					 int first = activeAudioElement;
					 int second = activeAudioElement + 1;

					 swapBorderPaneForAudio(first, second);

					 musicBox.getChildren().set(first + 1, borderPanesAudio.get(first));
					 musicBox.getChildren().set(second + 1, borderPanesAudio.get(second));

					 borderPanesAudio.get(second).setBackground(new Background(new BackgroundFill(Color.AQUA, CornerRadii.EMPTY, Insets.EMPTY)));

					 activeAudioElement = activeAudioElement + 1;
				 }
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
	            		project.setTime(from, to, activeMediaElement);
	            	}
	            }
	        }
	        );

		 deleteButton.setOnMousePressed(new EventHandler<MouseEvent>()
		 {
			 @Override
			 public void handle(MouseEvent event) {
				 if((activeMediaElement >= 0) && (activeMediaElement < preview_image.size())) {
					 project.remove(activeMediaElement);
					 removeElementInPreviewImages(activeMediaElement);
					 removeElementInBorderPaneForPreviewImages(activeMediaElement);

					 videoAndPictureBox.getChildren().remove(activeMediaElement + 1);

					 activeMediaElement = -1;

					 files = project.getContent();
				 } else if((activeAudioElement >= 0) && (activeAudioElement < borderPanesAudio.size())){
					 removeElementInBorderPaneForAudio(activeAudioElement);
					 musicBox.getChildren().remove(activeAudioElement + 1);
				 }
			 }
		 });


		 videoAndPictureBox.setOnMouseClicked(new EventHandler<MouseEvent>()
		 {
			 @Override
			 public void handle(MouseEvent event)
			 {
				 for(int i = 0; i < preview_image.size(); ++i)
				 {
					 ImageView tmp_image = preview_image.get(i);

					 tmp_image.setOnMousePressed(new EventHandler<MouseEvent>()
					 {
						 @Override
						 public void handle(MouseEvent event)
						 {
							 if(activeAudioElement != -1){
								 borderPanesAudio.get(activeAudioElement).setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
								 activeAudioElement = -1;
							 }
							 int tmpInt = Integer.parseInt(tmp_image.getId());
							 if(activeMediaElement == tmpInt)
							 {
								 BorderPane tmpBorderPane = border_pane_for_preview.get(activeMediaElement);
								 tmpBorderPane.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
								 activeMediaElement = -1;
							 }
							 else if(activeMediaElement != -1)
							 {
								 BorderPane tmpBorderPane = border_pane_for_preview.get(activeMediaElement);
								 tmpBorderPane.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

								 activeMediaElement = tmpInt;
								 tmpBorderPane = border_pane_for_preview.get(activeMediaElement);
								 tmpBorderPane.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));
							 }
							 else
							 {
								 activeMediaElement = Integer.parseInt(tmp_image.getId());

								 BorderPane tmpBorderPane = border_pane_for_preview.get(activeMediaElement);
								 tmpBorderPane.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));
								 //TODO ????????? ?????
							 }
						 }
					 });
				 }
			 }
		 });

			musicBox.setOnMouseClicked(new EventHandler<MouseEvent>()
			{
				@Override
				public void handle(MouseEvent event) {
					for(int i = 0; i < borderPanesAudio.size(); ++i){
						BorderPane borderPane = borderPanesAudio.get(i);

						borderPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
							@Override
							public void handle(MouseEvent event) {
								if(activeMediaElement != -1){
									border_pane_for_preview.get(activeMediaElement).setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
									activeMediaElement = -1;
								}
								int tmpInt = Integer.parseInt(borderPane.getId());
								if(activeAudioElement == tmpInt)
								{
									borderPane.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
									activeAudioElement = -1;
								}
								else if(activeAudioElement != -1)
								{
									borderPanesAudio.get(activeAudioElement).setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

									activeAudioElement = tmpInt;
									borderPane.setBackground(new Background(new BackgroundFill(Color.AQUA, CornerRadii.EMPTY, Insets.EMPTY)));
								}
								else
								{
									activeAudioElement = tmpInt;

									borderPane.setBackground(new Background(new BackgroundFill(Color.AQUA, CornerRadii.EMPTY, Insets.EMPTY)));
									//TODO ????????? ?????
								}
							}
						});
					}
				}
			});

		 player.setStyle("-fx-background-color: #bfc2c7;");
		playButton.setOnMousePressed(new EventHandler<MouseEvent>()
		 {
	    		@Override
	            public void handle(MouseEvent e)
	            {
	    			files = project.getContent();
	    			//for(int i = 0; i < files.size(); i++)
	    			//{

	    		 	if(files.size() != 0)
	    		 	{
	    		 		File file = files.get(0).getFile();
	    				String filename;
	    				filename = file.getAbsolutePath();
	    				//filename = filename.replace("\\", "/");
	    				Media media = new Media(new File(filename).toURI().toString());
	    				MediaPlayer mediaPlayer = new MediaPlayer(media);
	    				MediaView mediaView = new MediaView(mediaPlayer);
	    				//mediaView.setMediaPlayer(mediaPlayer);
	    				Pane mvPane = new Pane() {  };
	    				mvPane.setStyle("-fx-background-color: black;");
	    			    mvPane.getChildren().add(mediaView);
	    				player.setCenter(mvPane);
	    				//MediaControl mkl = new MediaControl(mediaPlayer, player);
	    				//player.setBottom(addToolBar(mediaPlayer));
	    		 	}
	            }
	        });


		 //показывать слайдшоу на экране


			//}
			//MediaControl mkl = new MediaControl(mediaPlayer, player);

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

	private void swapBorderPaneForAudio(int first, int second){
		BorderPane borderPaneFirst = new BorderPane();
		BorderPane borderPaneSecond = new BorderPane();

		borderPaneFirst.setCenter(borderPanesAudio.get(second).getCenter());
		borderPaneSecond.setCenter(borderPanesAudio.get(first).getCenter());

		borderPaneFirst.setBottom(borderPanesAudio.get(second).getBottom());
		borderPaneSecond.setBottom(borderPanesAudio.get(first).getBottom());
		//borderPaneFirst.setPrefWidth(borderPanesAudio.get(second).getPrefWidth());
		//borderPaneFirst.setPrefHeight(borderPanesAudio.get(second).getPrefHeight());

		//borderPaneSecond.setPrefWidth(borderPanesAudio.get(first).getPrefWidth());
		//borderPaneSecond.setPrefHeight(borderPanesAudio.get(first).getPrefHeight());

		borderPaneFirst.setId("" + first);
		borderPaneSecond.setId("" + second);

		borderPanesAudio.set(first, borderPaneFirst);
		borderPanesAudio.set(second, borderPaneSecond);
	}

	private void swapBorderPaneForPreview(int first, int second){
		BorderPane borderPaneFirst = new BorderPane();
		BorderPane borderPaneSecond = new BorderPane();

		borderPaneFirst.setCenter(border_pane_for_preview.get(second).getCenter());
		borderPaneSecond.setCenter(border_pane_for_preview.get(first).getCenter());

		borderPaneFirst.setPrefWidth(border_pane_for_preview.get(second).getPrefWidth());
		borderPaneFirst.setPrefHeight(border_pane_for_preview.get(second).getPrefHeight());

		borderPaneSecond.setPrefWidth(border_pane_for_preview.get(first).getPrefWidth());
		borderPaneSecond.setPrefHeight(border_pane_for_preview.get(first).getPrefHeight());

		borderPaneFirst.setId("" + first);
		borderPaneSecond.setId("" + second);

		border_pane_for_preview.set(first, borderPaneFirst);
		border_pane_for_preview.set(second, borderPaneSecond);


	}

	private void swapPreviewImageView(int first, int second){
		ImageView imageViewFirst = preview_image.get(first);
		ImageView imageViewSecond = preview_image.get(second);

		imageViewFirst.setId("" + second);
		imageViewSecond.setId("" + first);

		preview_image.set(first, imageViewSecond);
		preview_image.set(second, imageViewFirst);
	}

	private void removeElementInPreviewImages(int number){
		preview_image.remove(number);

		for(int i = number; i < preview_image.size(); ++i){
			preview_image.get(i).setId("" + i);
		}
	}

	private void removeElementInBorderPaneForPreviewImages(int number){
		border_pane_for_preview.remove(number);

		for(int i = number; i < border_pane_for_preview.size(); ++i){
			border_pane_for_preview.get(i).setId("" + i);
		}
	}

	private void removeElementInBorderPaneForAudio(int number){
		borderPanesAudio.remove(number);

		for(int i = number; i < borderPanesAudio.size(); ++i){
			borderPanesAudio.get(i).setId("" + i);
		}
	}
}



