package application;

import org.bytedeco.javacpp.avcodec;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.Frame;
import sun.rmi.runtime.Log;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.File;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static org.bytedeco.javacpp.avutil.AV_PIX_FMT_YUV420P;

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

	private static BufferedImage convertToType(BufferedImage sourceImage, int targetType){
		BufferedImage image;

		// if the source image is already the target type, return the source image

		if (sourceImage.getType() == targetType)
			image = sourceImage;

			// otherwise create a new image of the target type and draw the new
			// image

		else
		{
			image = new BufferedImage(sourceImage.getWidth(),
					sourceImage.getHeight(), targetType);
			image.getGraphics().drawImage(sourceImage, 0, 0, null);
		}

		return image;
	}

	public File getMP4(){
		String fileName = picture.getName();
		fileName = fileName.substring(0, fileName.lastIndexOf('.')) + ".mp4";
		File file = new File(fileName);
		if(file.exists())
			return file;
		//https://stackoverflow.com/questions/28721396/convert-images-to-video-in-android
		try {
			BufferedImage image = ImageIO.read(picture);
			image = convertToType(image, BufferedImage.TYPE_3BYTE_BGR);
			//FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(Environment.getExternalStorageDirectory().toString()+"/test/video.mp4",400,400,audioFrames.getAudioChannels());
			FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(fileName, image.getWidth(), image.getHeight(), 1);

			recorder.setFrameRate(60);
			recorder.setVideoOption("preset", "veryfast");
			recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);
			recorder.setVideoBitrate(10000);
			recorder.setFormat("mp4");

			recorder.setPixelFormat(AV_PIX_FMT_YUV420P);

			recorder.setAudioCodec(avcodec.AV_CODEC_ID_AAC);
			recorder.setAudioChannels(2);
			recorder.setSampleRate(48000);
			recorder.setVideoQuality(15); // maximum quality
			recorder.start();

			Frame frame=null;
			frame = new Java2DFrameConverter().convert(image);
			for (int i=0;i< 10 * recorder.getFrameRate();i++) {
				recorder.record(frame);
			}
			recorder.stop();
			return new File(fileName);
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
