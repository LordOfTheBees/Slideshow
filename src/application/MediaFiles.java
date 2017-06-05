package application;

import java.awt.image.BufferedImage;
import java.io.File;

abstract class MediaFiles
{
	abstract void loadFile(File file);
	abstract boolean isImage();
	abstract File getFile();
	abstract BufferedImage getPreview();
}
