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
	private String currentLogLine;
	private Map<String, Double> costModel=new HashMap<String, Double>();
	private List<CostModel> costs=new ArrayList<CostModel>();
	public AverageCostCalculator(CallGraph callGraph) {
		super();
		this.callGraph = callGraph;
	}

	public List<CostModel> getCost() {
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
							workloads.add(Double.valueOf(workload));
							String context=complexityModelData.getCallingContextAsString().trim();
							if(!costModel.containsKey(context)) {
								costModel.put(context, averageCost);
							}
							else {
								costModel.put(context, (averageCost+costModel.get(context)/2.00));
							}
							
							//complexityModelData.addWorkloadAndExecutionCount(workloads, invocationCount);
							//System.out.println();
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
			return costs;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
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
			extracted = extracted.substring(extracted.indexOf(startingString),
					extracted.lastIndexOf(closingString) + closingString.length());
		}
		currentLogLine = currentLogLine.replace(caller, "");
		currentLogLine = currentLogLine.replace("  ", " ");
		return getCost(function.getClassName() + "," + function.getName(), extracted);
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
	public static void main(String[] args) throws IOException, InterruptedException, CallGraphException {
		new XSLTTransformer().transform();
		CallGraph callGraph = new StaticCallGraphExtractor().extractCallGraph();
		KProfileGraphBuilder graphBuilder = new KProfileGraphBuilder(callGraph);
		callGraph = graphBuilder.buildKProfileGraph();
/*		callGraph =new ComplexityModelDataGenerator(callGraph).generateData();
*/		new AverageCostCalculator(callGraph).getCost();
	}
}
