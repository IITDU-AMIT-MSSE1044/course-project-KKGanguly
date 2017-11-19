package sample;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class FileHandler {
	private String filePath;

	private FileHandler(String filePath) {
		this.filePath = filePath;
	}

	public String getFilePath() {
		GlobalInstrumentationString.contentBuffer.append("FileHandler,getFilePath ");
		GlobalInstrumentationString.contentBuffer.append("exit-FileHandler,getFilePath ");
		return filePath;
	}

	public static FileHandler getFileHandler(String filePath) {
		GlobalInstrumentationString.contentBuffer.append("FileHandler,getFileHandler ");
		GlobalInstrumentationString.contentBuffer.append("exit-FileHandler,getFileHandler ");
		return new FileHandler(filePath);
	}

	public void setFilePath(String filePath) {
		GlobalInstrumentationString.contentBuffer.append("FileHandler,setFilePath ");
		this.filePath = filePath;
		GlobalInstrumentationString.contentBuffer.append("exit-FileHandler,setFilePath ");
	}

	public String readFile() {
		GlobalInstrumentationString.contentBuffer.append("FileHandler,readFile ");
		File file = new File(filePath);
		try {
			GlobalInstrumentationString.contentBuffer.append("exit-FileHandler,readFile ");
			return FileUtils.readFileToString(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
		GlobalInstrumentationString.contentBuffer.append("exit-FileHandler,readFile ");
		return "";
	}

	public void writeFile(String content) {
		GlobalInstrumentationString.contentBuffer.append("FileHandler,writeFile ");
		File file = new File(filePath);
		try {
			FileUtils.writeStringToFile(file, content, true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
		GlobalInstrumentationString.contentBuffer.append("exit-FileHandler,writeFile ");
	}
}
