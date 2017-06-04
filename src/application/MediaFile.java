package application;

import java.io.File;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MediaFile
{

	public void loadImageFile()
	{
		FileChooser fileChooser = new FileChooser();
    	fileChooser.setTitle("Open Resource File");

        File file = null;

    	file = fileChooser.showOpenDialog(new Stage());
    	String filename = file.getAbsolutePath();
    	filename = filename.replace("\\", "/");
    	Image image = new Image(filename);
    	ImageView imageView = new ImageView(image);
	}

	public void loadVideoFile()
	{
		FileChooser fileChooser = new FileChooser();
    	fileChooser.setTitle("Open Resource File");

        File file = null;

    	file = fileChooser.showOpenDialog(new Stage());
    	String filename = file.getAbsolutePath();
    	filename = filename.replace("\\", "/");
    	Media media = new Media(new File(filename).toURI().toString());
    	MediaPlayer mediaPlayer = new MediaPlayer(media);

    	MediaView mediaView = new MediaView(mediaPlayer);
    	mediaView.setMediaPlayer(mediaPlayer);
	}
}
