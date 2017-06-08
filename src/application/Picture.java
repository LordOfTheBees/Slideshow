package application;

import org.bytedeco.javacpp.avcodec;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.Frame;
import sun.rmi.runtime.Log;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.ImageObserver;
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

	public File getMP4(){
		//https://stackoverflow.com/questions/28721396/convert-images-to-video-in-android
		try {
			BufferedImage image = ImageIO.read(picture);
			//FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(Environment.getExternalStorageDirectory().toString()+"/test/video.mp4",400,400,audioFrames.getAudioChannels());
			FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(picture.getName() + "kek.mp4", image.getWidth(), image.getHeight());

			recorder.setFrameRate(30);
			recorder.setVideoCodec(avcodec.AV_CODEC_ID_MPEG4);
			recorder.setVideoBitrate(9000);
			recorder.setFormat("mp4");
			recorder.setAudioChannels(2);
			recorder.setSampleRate(44100);
			recorder.setVideoQuality(0); // maximum quality
			recorder.start();

			Frame frame=null;
			frame = new Java2DFrameConverter().convert(image);
			for (int i=0;i< 60;i++)
			{
				long t = 9000 * (System.currentTimeMillis());
				if (t > recorder.getTimestamp()) {
					recorder.setTimestamp(t);

					recorder.record(frame);
				}

			}
			recorder.stop();
			return new File(picture.getName() + "kek.mp4");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public void setTime(String from, String to)
	{

	}
}
