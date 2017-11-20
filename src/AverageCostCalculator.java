import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import util.FileUtil;

public class AverageCostCalculator {
	private CallGraph callGraph;
	private List<String> workloadLog = new ArrayList<String>();
	private static final String LOG = "logTime.txt";
	private static final String WORKLOADLOG = "workload.txt";
	private double totalCost;
	private String realCostLog = "results-single/costs.txt";
	private String currentLogLine;
	private Map<String, Double> costModel=new HashMap<String, Double>();
	private Map<String, Double> totalCostModel=new HashMap<String, Double>();
	private static final String TOTALCOSTLOG = "logTotalTime.txt";
	private int numRows;
	private List<CostModel> costs=new ArrayList<CostModel>();
	public AverageCostCalculator(CallGraph callGraph,String realCostLog) {
		super();
		this.callGraph = callGraph;
		this.realCostLog=realCostLog;
	}
	
	public List<CostModel> getCost() {
		readWorkloadLog();
		String content="";
		FileUtil fileUtilCoverage=FileUtil.getFileUtil(realCostLog);
		fileUtilCoverage.makeWholePath();
		fileUtilCoverage.clearFile();
		fileUtilCoverage.appendFile("context,total_cost,contextwise_cost,percentage");
		Iterator<String> iterator=workloadLog.iterator();
		try {
			BufferedReader bufferedReader=new BufferedReader(new FileReader(new File(LOG)));
			while((content=bufferedReader.readLine())!=null) {
					String logEntry = content;
					for (Function function : callGraph.getFunctions()) {
						currentLogLine = new String(logEntry);
						for (ComplexityModelData complexityModelData : function.getComplexityModelDatas()) {
							double averageCost = findExecutionCostUnderContext(currentLogLine,
									complexityModelData.getCallingContext(), function);
							String context=complexityModelData.getCallingContextAsString().trim()+" "+function;
							if(!costModel.containsKey(context)) {
								costModel.put(context, averageCost);
								totalCostModel.put(context, totalCost);
							}
							else {
								costModel.put(context, (averageCost+costModel.get(context))/2.00);
								totalCostModel.put(context,(totalCost+ totalCostModel.get(context)));
								
							}
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
			double cost=getTotalAverageCost();
			for (String context : totalCostModel.keySet()) {
				try {
					double totalCostInContext=totalCostModel.get(context)/numRows;
					fileUtilCoverage.apppendLargeFile(context+","+cost+","+totalCostInContext+","+(totalCostInContext/cost)+"\n");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return costs;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	private double getTotalAverageCost() {
		FileUtil fileUtil=FileUtil.getFileUtil(TOTALCOSTLOG);
		String costs=fileUtil.readFile();
		String[] lines=costs.split("\n");
		double cost=0.0;
		numRows=lines.length;
		for (String line : lines) {
			cost+=Double.valueOf(line.trim());
		}
		return numRows!=0?(cost/(double) numRows):0;
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
			try {
				extracted = extracted.substring(extracted.indexOf(startingString),
					indexOfClosingString + getClosingTagLength(indexOfClosingString, extracted));
			}catch(Exception exception) {
				return 0;
			}
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
		totalCost=cost;
		return j!=0?(cost/(double)j):0.0;
	}
	
}
