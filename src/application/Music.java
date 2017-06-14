package application;

import java.io.File;

public class Music
{
	File music = null;
	public void loadFile(File file)
	{
		music = file;
	}

	public File getMusic(){
		return music;
	}
}
