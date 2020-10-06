package util;

public class LocalFile {
	private String fileName;
	private String directory;

	public String getDirectory() {
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
		return this.directory + this.fileName;
	}
}
