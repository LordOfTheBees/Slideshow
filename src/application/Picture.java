package application;

import javax.imageio.ImageIO;
import java.io.File;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Picture extends MediaFiles
{
	File picture = null;

	public void loadFile(File file)

	{
		picture = file;
	}

	public boolean isImage()
	{
		return true;
	}

	public File getFile()
	{
		return picture;
	}

	public BufferedImage getPreview(){
		BufferedImage buffImg = new BufferedImage(60, 60, BufferedImage.TYPE_INT_ARGB);

		try {
			buffImg = ImageIO.read(picture);
		}
		catch (IOException e) { }

		return buffImg;
	}

	public void setTime(String from, String to)
	{

	}
}
