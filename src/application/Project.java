package application;

import java.io.File;
import java.util.ArrayList;

//TODO: сделать метод для времени показа
//TODO: Сделать методы для распихивания загруженного
public class Project
{
	Music music;
	Video video;
	Picture picture;
	public ArrayList<MediaFiles> files  = new ArrayList<MediaFiles>();

	public void loadMusic(File file)
	{
		music = new Music();
		music.loadFile(file);
	}

	public void  loadVideo(File file)
	{
		video = new Video();
		video.loadFile(file);
		files.add(video);
	}

	public void  loadImage(File file)
	{
		picture = new Picture();
		picture.loadFile(file);
		files.add(picture);
	}

	public void setTime(String from, String to, int i)
	{
		//и индекс выделенного объекта передается

		if(files.get(i).isImage())
		{
			Picture current_picture  = (Picture)files.get(i);
			current_picture.setTime(from, to);
		}
	}

	public  ArrayList<MediaFiles> getContent()
	{
		return files;
	}
}
