import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import util.FileUtil;

public class CostTrainingDataGenerator {
	private CallGraph callGraph;
	private List<String> workloadLog = new ArrayList<String>();
	private static final String LOG = "logTime.txt";
	private static final String WORKLOADLOG = "workload.txt";
	private String currentLogLine;
	private static final String TRAINING_DIRECTORY="trainingExtended";
	private Map<String, Double> costModel=new HashMap<String, Double>();
	private List<CostModel> costs=new ArrayList<CostModel>();
	public CostTrainingDataGenerator(CallGraph callGraph) {
		super();
		this.callGraph = callGraph;
		init();
	}

	private CallGraph updateWorkloadAndCost() {
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
							double averageCost = findExecutionCostUnderContext(currentLogLine,
									complexityModelData.getCallingContext(), function);
							List<Double> workloads = new ArrayList<Double>();
							String[] workloadArray=workload.split(",");
							workloads.addAll(convertStringArrayToDoubleList(workloadArray));
							complexityModelData.addWorkloadAndCost(workloads, averageCost);
						}
						
				}
			}
			bufferedReader.close();
			for (String context : costModel.keySet()) {
				CostModel model=new CostModel();
				model.setContext(context);
				model.setTime(costModel.get(context));
				costs.add(model);
			}
			return callGraph;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	private List<Double> convertStringArrayToDoubleList(String[] stringArray){
		List<Double> lists=new ArrayList<Double>();
		for (String string : stringArray) {
			try {
				lists.add(Double.valueOf(string));
			}catch(Exception exception) {
				lists.add(0D);
			}
		}
		return lists;
	}

	private void readWorkloadLog() {
		FileUtil fileUtil = FileUtil.getFileUtil(WORKLOADLOG);
		String content = fileUtil.readFile();
		String[] lines = content.split("\n");
		workloadLog = new ArrayList<>(Arrays.asList(lines));
	}
	
	private double findExecutionCostUnderContext(String content, List<Function> callingContext, Function function) {
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
			int indexOfClosingString=extracted.lastIndexOf(closingString);
			extracted = extracted.substring(extracted.indexOf(startingString),
					indexOfClosingString+ getClosingTagLength(indexOfClosingString, extracted));
		}
		currentLogLine = currentLogLine.replace(caller, "");
		currentLogLine = currentLogLine.replace("  ", " ");
		return getCost(function.getClassName() + "," + function.getName(), extracted);
	}
	private int getClosingTagLength(int index,String line) {
		int i;
		for(i=index;i<line.length();i++) {
			if(line.charAt(i)==' ') {
				break;
			}
		}
		return i-index;
	}
	private double getCost(String subString, String string) {
		Pattern pattern = Pattern.compile(subString+":"+"[0-9]+");
		Pattern costPattern = Pattern.compile("[0-9]+");
		Matcher fullMatcher = pattern.matcher(string);
		int j = 0;
		double cost=0.0;
		String content="";
		while (fullMatcher.find()) {
			content+=fullMatcher.group();
			j++;
		}
		Matcher costMatcher = costPattern.matcher(content);
		while (costMatcher.find()) {
			try{
				cost+=Double.valueOf(costMatcher.group());
			}catch(Exception exception) {
				cost+=0.0;
			}
			j++;
		}
		return j!=0?(cost/(double)j):0.0;
	}
	private void init() {
		// TODO Auto-generated method stub
		File directory=new File(TRAINING_DIRECTORY);
		if(!directory.exists()) {
			directory.mkdirs();
		}
	}

	public void buildTrainingData() {
		callGraph=updateWorkloadAndCost();
		for (Function function : callGraph.getFunctions()) {
			for (ComplexityModelData complexityModelData : function.getComplexityModelDatas()) {
				
				String trainingFilePath=TRAINING_DIRECTORY+File.separator+complexityModelData.getCallingContextAsString()+".csv";
				FileUtil fileUtil=FileUtil.getFileUtil(trainingFilePath);
				String content ="";
				Map<List<Double>,Double> workloadCostMap=complexityModelData.getWorkLoadToCostMap();
				int columnNo=0;
				for (List<Double> workloadAttributes : workloadCostMap.keySet()) {
					columnNo=workloadAttributes.size();
					for (Double workloadAttribute : workloadAttributes) {
						content+=workloadAttribute+",";
					}
					content+=workloadCostMap.get(workloadAttributes)+"\n";
				}
				if(!content.isEmpty()) {
				String header="";
				for (int i=1;i<=columnNo;i++) {
					header+="workload"+i+",";
				}
				header+="Cost";
				fileUtil.clearFile();
				fileUtil.appendFile(header);
				fileUtil.appendFile(content);
			}
			}
		}
	}
}
