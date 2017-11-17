package sample;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.apache.commons.io.FileUtils;

public class FileHandler {
	private String filePath;

	private FileHandler(String filePath) {
		this.filePath = filePath;
	}

	public String getFilePath() {
		try {
			Files.write(Paths.get("log.txt"), "FileHandler,getFilePath ".getBytes(),StandardOpenOption.APPEND);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return filePath;
	}

	public static FileHandler getFileHandler(String filePath) {
		try {
			Files.write(Paths.get("log.txt"), "FileHandler,getFileHandler ".getBytes(),StandardOpenOption.APPEND);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new FileHandler(filePath);
	}

	public void setFilePath(String filePath) {
		try {
			Files.write(Paths.get("log.txt"), "FileHandler,setFilePath ".getBytes(),StandardOpenOption.APPEND);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.filePath = filePath;
	}

	public String readFile() {
		try {
			Files.write(Paths.get("log.txt"), "FileHandler,readFile ".getBytes(),StandardOpenOption.APPEND);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File file = new File(filePath);
		try {
			return FileUtils.readFileToString(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
		return "";
	}

	public void writeFile(String content) {
		try {
			Files.write(Paths.get("log.txt"), "FileHandler,writeFile ".getBytes(),StandardOpenOption.APPEND);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File file = new File(filePath);
		try {
			FileUtils.writeStringToFile(file, content, true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
	}
}
