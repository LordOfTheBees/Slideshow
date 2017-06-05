package application;

import java.io.File;

abstract class MediaFiles
{
	abstract void loadFile(File file);
	abstract boolean isImage();
	abstract File getFile();
}
