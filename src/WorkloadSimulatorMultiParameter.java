import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import sample.Executor;
import secondsample.GlobalInstrumentationString;
import secondsample.NotePadExecutor;
import util.FileUtil;

public class WorkloadSimulatorMultiParameter {
	private static List<String> simulationData = new ArrayList<String>(Arrays.asList("This is sample data",
			"This is sample data This is sample data\n This is sample data This is sample data","This is sample data data data data data", "This is sample data data data data data \n This is sample data data data data data \n This is sample data data data data data This is sample data data data data data This is sample data data data data data \n This is sample data data data data data This is sample data data data data data",
			"This is \n This is This isThis isThis isThis isThis isThis isThis isThis isThis isThis is \n This is \n This is","a\na",
			"This is sample data This is sample data This is sample data This is sample data This is sample data This is sample data\n This is sample data This is sample data This is sample data This is sample data This is sample data This is sample data This is sample data This is sample data \n This is sample data This is sample data This is sample data This is sample data This is sample data This is sample data This is sample data This is sample data"));

	public static void simulate() {
		FileUtil fileUtil = FileUtil.getFileUtil("log.txt");
		FileUtil fileUtilWorkload = FileUtil.getFileUtil("workload.txt");
		FileUtil fileUtilTotalTime = FileUtil.getFileUtil("logTotalTime.txt");
		FileUtil fileUtilTime = FileUtil.getFileUtil("logTime.txt");
		fileUtil.clearFile();
		fileUtilWorkload.clearFile();
		fileUtilTime.clearFile();
		fileUtilTotalTime.clearFile();
		for (String data : simulationData) {
			long start=System.nanoTime();
			new NotePadExecutor().run(data, "data");
			
			try {
				fileUtilTotalTime.apppendLargeFile((System.nanoTime()-start) + "\n");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			GlobalInstrumentationString.contentBuffer.append("\n");
			GlobalInstrumentationString.contentBufferWithCost.append("\n");
			try {
					fileUtil.apppendLargeFile(GlobalInstrumentationString.contentBuffer.toString());
					fileUtilTime.apppendLargeFile(GlobalInstrumentationString.contentBufferWithCost.toString());
					GlobalInstrumentationString.contentBuffer=new StringBuffer();
					GlobalInstrumentationString.contentBufferWithCost=new StringBuffer();
			} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
			}
			
			/*try {
				fileUtilWorkload.apppendLargeFile(data.split("\n").length+","+data.length() + "\n");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}*/
			/*try {
				fileUtilWorkload.apppendLargeFile((data.split("\n")).length+","+data.split("\\s+").length + "\n");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}*/
			try {
				fileUtilWorkload.apppendLargeFile(data.split("\n").length+ "\n");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//System.out.println(i);
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
