package application;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import com.xuggle.xuggler.*;
import com.xuggle.xuggler.video.ConverterFactory;
import com.xuggle.xuggler.video.IConverter;

public class Video extends MediaFiles
{	File video = null;
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

	public BufferedImage getPreview() {
		//возможно неправильное формирование filename
		String filename = video.getAbsolutePath();

		File outdir = new File("c:/temp/pictures");
		IContainer container = IContainer.make();
		IConverter converter = ConverterFactory.createConverter(new BufferedImage(800, 800, BufferedImage.TYPE_3BYTE_BGR), IPixelFormat.Type.YUV420P);

		if (container.open(filename, IContainer.Type.READ, null) < 0)
			throw new IllegalArgumentException("could not open file: " + filename);

		int numStreams = container.getNumStreams();
		int videoStreamId = -1;
		IStreamCoder videoCoder = null;

		// нужно найти видео поток
		for (int i = 0; i < numStreams; i++) {
			IStream stream = container.getStream(i);
			IStreamCoder coder = stream.getStreamCoder();
			if (coder.getCodecType() == ICodec.Type.CODEC_TYPE_VIDEO) {
				videoStreamId = i;
				videoCoder = coder;
				break;
			}
		}

		if (videoStreamId == -1)
			// кажись не нашли
			throw new RuntimeException("could not find video stream in container: " + filename);

		// пытаемся открыть кодек
		if (videoCoder.open() < 0)
			throw new RuntimeException("could not open video decoder for container: " + filename);

		IPacket packet = IPacket.make();
		// с 3-ей по 5-ую микросекунду
		//long start = 6 * 1000 * 1000;
		long start = 0;
		long end = 12 * 1000 * 1000;

		END: while (container.readNextPacket(packet) >= 0) {
			if (packet.getStreamIndex() == videoStreamId) {
				IVideoPicture picture = IVideoPicture.make(videoCoder.getPixelType(), videoCoder.getWidth(), videoCoder.getHeight());
				int offset = 0;
				while (offset < packet.getSize()) {
					int bytesDecoded = videoCoder.decodeVideo(picture, packet, offset);
					// Если что-то пошло не так
					if (bytesDecoded < 0)
						throw new RuntimeException("got error decoding video in: " + filename);
					offset += bytesDecoded;
					// В общем случае, нужно будет использовать Resampler. См.
					// tutorials!
					if (picture.isComplete()) {
						IVideoPicture newPic = picture;
						// в микросекундах
						long timestamp = picture.getTimeStamp();
						if (timestamp > start) {
							// Получаем стандартный BufferedImage
							BufferedImage javaImage = converter.toImage(newPic);
							return javaImage;
						}
						if (timestamp > end) {
							break END;
						}
					}
				}
			}
		}
		if (videoCoder != null) {
			videoCoder.close();
			videoCoder = null;
		}
		if (container != null) {
			container.close();
			container = null;
		}
		return new BufferedImage(800,800,BufferedImage.TYPE_3BYTE_BGR);
	}
}
