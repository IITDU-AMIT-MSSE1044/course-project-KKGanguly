import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import util.FileUtil;

public class ComplexityModelDataGenerator {
	private CallGraph callGraph;
	private List<String> workloadLog = new ArrayList<String>();
	private static final String LOG = "log.txt";
	private static final String WORKLOADLOG = "workload.txt";
	private String currentLogLine;

	public ComplexityModelDataGenerator(CallGraph callGraph) {
		super();
		this.callGraph = callGraph;
	}

	public CallGraph generateData() {
		readExecutionLog();
		readWorkloadLog();
		String content="";
		Iterator<String> iterator=workloadLog.iterator();
		try {
			BufferedReader bufferedReader=new BufferedReader(new FileReader(new File(LOG)));
			while((content=bufferedReader.readLine())!=null) {
					String logEntry = content;
					// needs customization support
					String workload = iterator.hasNext()?iterator.next():"";
					for (Function function : callGraph.getFunctions()) {
						currentLogLine = new String(logEntry);
						for (ComplexityModelData complexityModelData : function.getComplexityModelDatas()) {
							int invocationCount = findExecutionCountUnderContext(currentLogLine,
									complexityModelData.getCallingContext(), function);
							List<Double> workloads = new ArrayList<Double>();
							workloads.add(Double.valueOf(workload));
							complexityModelData.addWorkloadAndExecutionCount(workloads, invocationCount);
						}
				}
			}
			bufferedReader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return callGraph;
	}

	private void readExecutionLog() {
		FileUtil fileUtil = FileUtil.getFileUtil(LOG);
		
	}

	private void readWorkloadLog() {
		FileUtil fileUtil = FileUtil.getFileUtil(WORKLOADLOG);
		String content = fileUtil.readFile();
		String[] lines = content.split("\n");
		workloadLog = new ArrayList<>(Arrays.asList(lines));
	}

	private int findExecutionCountUnderContext(String content, List<Function> callingContext, Function function) {
		String extracted = new String(content), caller = "";
		List<Function> callingContextWithBelongingFunction = new ArrayList<Function>(callingContext);
		callingContextWithBelongingFunction.add(function);
		for (Function callingContextFunction : callingContextWithBelongingFunction) {
			String closingString = "exit-" + callingContextFunction.getClassName() + ","
					+ callingContextFunction.getName();
			String startingString = callingContextFunction.getClassName() + "," + callingContextFunction.getName();
			if (callingContextFunction.equals(function)) {
				caller = extracted;
			}
			
			try{
				extracted = extracted.substring(extracted.indexOf(startingString+" "),extracted.lastIndexOf(closingString+" ") + closingString.length());
			}catch(Exception ex) {
				return 0;
			}
					
		}
		currentLogLine = currentLogLine.replace(caller, "");
		currentLogLine = currentLogLine.replace("  ", " ");
		return countMatches(function.getClassName() + "," + function.getName(), extracted);
	}

	private int countMatches(String subString, String string) {
		Pattern redundantPattern = Pattern.compile("[-]" + subString);
		Pattern fullPattern = Pattern.compile(subString);
		Matcher redundantMatcher = redundantPattern.matcher(string);
		Matcher fullMatcher = fullPattern.matcher(string);
		int i = 0, j = 0;
		while (redundantMatcher.find()) {
			i++;
		}
		while (fullMatcher.find()) {
			j++;
		}
		return j - i;
	}


}
