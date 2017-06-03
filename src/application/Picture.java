package application;

import java.io.File;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Picture extends MediaFiles
{
	public void loadFile()
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
}
