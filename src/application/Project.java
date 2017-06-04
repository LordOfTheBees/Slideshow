package application;

import java.util.ArrayList;

//TODO: сделать метод для времени показа
//TODO: Сделать методы для распихивания загруженного
public class Project
{
	Music music;
	Video video;
	Picture picture;
	public ArrayList<MediaFiles> files  = new ArrayList<MediaFiles>();

	public void loadMusic()
	{
		music.loadFile();
	}

	public void  loadVideo()
	{
		video.loadFile();
		files.add(video);
	}
	public void  loadImage()
	{
		picture.loadFile();
		files.add(picture);
	}
}
