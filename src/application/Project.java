package application;

public class Project
{
	Music music;
	MediaFile media;
	public void loadMusic()
	{
		music.loadFile();
	}

	public void  loadVideo()
	{
		media.loadVideoFile();
	}
	public void  loadImage()
	{
		media.loadImageFile();
	}
}
