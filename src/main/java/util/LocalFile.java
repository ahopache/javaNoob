package util;

public class LocalFile {
	private String fileName = null;
	private String directory = null;

	public String getDirectory() {
		if (directory == null)
			return "";
		return directory;
	}

	public void setDirectory(String directory) {
		this.directory = directory;
	}

	public LocalFile(String file) {
		this.fileName = file;
	}

	public LocalFile() {
		this.fileName = null;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getFileFullName() {
		if (this.directory == null)
			return this.fileName;
		else
			return this.directory + this.fileName;
	}
}
