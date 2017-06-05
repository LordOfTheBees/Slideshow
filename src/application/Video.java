package application;

import java.io.File;

public class Video extends MediaFiles
{
	 File video = null;
	public void loadFile(File file)
	{
		video = file;
	}


	public File getFile()
	{
		return video;
	}

	public boolean isImage()
	{
		return false;
	}


}
