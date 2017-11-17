package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ProcessLogger {
	public static void printProcessOutputToFile(String filePath, Process process) throws IOException {
		BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getErrorStream()));
		String output = null;
		FileUtil fileUtil = FileUtil.getFileUtil(filePath);
		fileUtil.makeWholePath();
		while ((output = stdInput.readLine()) != null) {
			fileUtil.appendFile(output);
		}
	}
}
