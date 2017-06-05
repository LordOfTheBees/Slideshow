package application;

import java.io.File;

public class Picture extends MediaFiles
{
	File picture = null;

	public void loadFile(File file)

	{

		picture = file;
    	//filename = filename.replace("\\", "/");
    	//Image image = new Image(filename);
    	//ImageView imageView = new ImageView(image);

	}

	public boolean isImage()
	{
		return true;
	}

	public File getFile()
	{
		return picture;
	}

	public void setTime(String from, String to)
	{

	}
}
