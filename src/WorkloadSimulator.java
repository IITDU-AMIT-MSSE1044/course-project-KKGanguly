import java.io.IOException;

import sample.Executor;
import sample.GlobalInstrumentationString;
import util.FileUtil;

public class WorkloadSimulator {
	public static void simulate() {
		FileUtil fileUtil = FileUtil.getFileUtil("log.txt");
		FileUtil fileUtilWorkload = FileUtil.getFileUtil("workload.txt");
		FileUtil fileUtilTime = FileUtil.getFileUtil("logTime.txt");
		fileUtil.clearFile();
		fileUtilWorkload.clearFile();
		fileUtilTime.clearFile();
		for (int i = 10; i < 300; i += 10) {
			new Executor(i).run();
			
			GlobalInstrumentationString.contentBuffer.append("\n");
			GlobalInstrumentationString.contentBufferWithCost.append("\n");
			if(i%20==0) {
				try {
					fileUtil.apppendLargeFile(GlobalInstrumentationString.contentBuffer.toString());
					fileUtilTime.apppendLargeFile(GlobalInstrumentationString.contentBufferWithCost.toString());
					GlobalInstrumentationString.contentBuffer=new StringBuffer();
					GlobalInstrumentationString.contentBufferWithCost=new StringBuffer();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			try {
				fileUtilWorkload.apppendLargeFile(Integer.toString(i) + "\n");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		try {
			fileUtil.apppendLargeFile(GlobalInstrumentationString.contentBuffer.toString());
			fileUtilTime.apppendLargeFile(GlobalInstrumentationString.contentBufferWithCost.toString());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		simulate();
	}
}
