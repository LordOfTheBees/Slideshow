package application;

import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;

import java.awt.image.BufferedImage;
import java.io.File;


public class Video extends MediaFiles {
	File video = null;

	public void loadFile(File file) {
		video = new File(file.getAbsolutePath());
	}

	public File getFile() {
		return video;
	}

	public boolean isImage() {
		return false;
	}

	public BufferedImage getPreview() throws FrameGrabber.Exception {
		String filepath;
		filepath = video.getAbsolutePath();
		FFmpegFrameGrabber frameGrabber = new FFmpegFrameGrabber(filepath);
		frameGrabber.setFormat(filepath.substring(filepath.indexOf('.') + 1));
		frameGrabber.start();
		Java2DFrameConverter converter = new Java2DFrameConverter();
		try {
			BufferedImage bi = converter.convert(frameGrabber.grab());
			//ImageIO.write(bi, "png", new File("D:/Img.png"));
			frameGrabber.stop();
			return bi;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new BufferedImage(60,60,BufferedImage.TYPE_3BYTE_BGR);
	}

	public File getMP4(){
		//if(video == null)
			//throw new Exception("Video::getMP4: video file doesn't exist");
		return video;
	}
}


