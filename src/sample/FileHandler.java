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
		GlobalInstrumentationString.contentBufferWithCost.append("FileHandler,getFilePath ");
		long start=System.nanoTime();
		GlobalInstrumentationString.contentBuffer.append("exit-FileHandler,getFilePath ");
		
		GlobalInstrumentationString.contentBufferWithCost.append("exit-FileHandler,getFilePath:");
		GlobalInstrumentationString.contentBufferWithCost.append((System.nanoTime()-start)+" ");
		return filePath;
	}

	public static FileHandler getFileHandler(String filePath) {
		GlobalInstrumentationString.contentBuffer.append("FileHandler,getFileHandler ");
		GlobalInstrumentationString.contentBufferWithCost.append("FileHandler,getFileHandler:");
		long start=System.nanoTime();
		GlobalInstrumentationString.contentBuffer.append("exit-FileHandler,getFileHandler ");
		
		GlobalInstrumentationString.contentBufferWithCost.append("exit-FileHandler,getFileHandler:");
		GlobalInstrumentationString.contentBufferWithCost.append((System.nanoTime()-start)+" ");
		return new FileHandler(filePath);
	}

	public void setFilePath(String filePath) {
		GlobalInstrumentationString.contentBuffer.append("FileHandler,setFilePath ");
		GlobalInstrumentationString.contentBufferWithCost.append("FileHandler,setFilePath ");
		long start=System.nanoTime();
		this.filePath = filePath;
		GlobalInstrumentationString.contentBuffer.append("exit-FileHandler,setFilePath ");
		
		GlobalInstrumentationString.contentBufferWithCost.append("exit-FileHandler,setFilePath:");
		GlobalInstrumentationString.contentBufferWithCost.append((System.nanoTime()-start)+" ");
	}

	public String readFile() {
		GlobalInstrumentationString.contentBuffer.append("FileHandler,readFile ");
		GlobalInstrumentationString.contentBufferWithCost.append("FileHandler,readFile ");
		long start=System.nanoTime();
		File file = new File(filePath);
		try {
			GlobalInstrumentationString.contentBuffer.append("exit-FileHandler,readFile ");
			GlobalInstrumentationString.contentBufferWithCost.append("exit-FileHandler,readFile:");
			GlobalInstrumentationString.contentBufferWithCost.append((System.nanoTime()-start)+" ");
			return FileUtils.readFileToString(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
		GlobalInstrumentationString.contentBuffer.append("exit-FileHandler,readFile ");
		
		GlobalInstrumentationString.contentBufferWithCost.append("exit-FileHandler,readFile:");
		GlobalInstrumentationString.contentBufferWithCost.append((System.nanoTime()-start)+" ");
		return "";
	}

	public void writeFile(String content) {
		GlobalInstrumentationString.contentBuffer.append("FileHandler,writeFile ");
		GlobalInstrumentationString.contentBufferWithCost.append("FileHandler,writeFile ");
		long start=System.nanoTime();
		File file = new File(filePath);
		try {
			FileUtils.writeStringToFile(file, content, true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
		GlobalInstrumentationString.contentBuffer.append("exit-FileHandler,writeFile ");
		GlobalInstrumentationString.contentBufferWithCost.append("exit-FileHandler,writeFile:");
		GlobalInstrumentationString.contentBufferWithCost.append((System.nanoTime()-start)+" ");
	}
}
