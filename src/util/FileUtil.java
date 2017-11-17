package util;
import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

public class FileUtil {
	private String filePath;

	private FileUtil(String filePath) {
		this.filePath = filePath;
	}

	public String getFilePath() {
		return filePath;
	}

	public static FileUtil getFileUtil(String filePath) {
		return new FileUtil(filePath);
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String readFile() {

		File file = new File(filePath);
		try {
			return FileUtils.readFileToString(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
		return "";
	}
	public void makeWholePath() {
		File file = new File(filePath);
		if(!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
	}
	public void appendFile(String content) {
		File file = new File(filePath);
		try {
			FileUtils.writeStringToFile(file, content + "\n", true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void writeFile(String content) {
		File file = new File(filePath);
		try {
			FileUtils.writeStringToFile(file, content, true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
	}
	public void write(String content) {
		File file = new File(filePath);
		try {
			FileUtils.writeStringToFile(file, content, false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
	}
	public boolean isFileEmpty() {
		File file = new File(filePath);
		return !file.exists() || file.length() == 0;
	}

	public void clearFile() {
		File file = new File(filePath);
		try {
			FileUtils.writeStringToFile(file, "", false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
	}
	public static void copy(File src, File dest) throws IOException {
		FileUtils.copyFileToDirectory(src, dest);
	}
	public static void move(File src, File dest) throws IOException {
		FileUtils.moveFileToDirectory(src, dest, true);
	}
	public boolean delete() throws IOException {
		File file = new File(filePath);
		return file.delete();
	}
	public boolean isFileAvailable() {
		return new File(filePath).exists();
	}

	public void createDirectory() {
		new File(filePath).mkdirs();
	}

	public String removeExtension() {
		String fileName = FilenameUtils.getName(filePath);
		return FilenameUtils.removeExtension(fileName);
	}

}