import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import util.FileUtil;

public class CostPrediction {
	private List<ComplexityTransition> complexityTransitions=new ArrayList<ComplexityTransition>();
	private List<CostModel> costModels=new ArrayList<CostModel>();
	private static final String RESULTPATH="results/result.txt";
	private static final String TOTALCOSTLOG = "logTotalTime.txt";
	private static final String COSTCOVERAGELOG = "results/coverage.txt";
	private static final String WORKLOADLOG = "workload.txt";
	private List<String> workloadLog = new ArrayList<String>();

	public CostPrediction(List<ComplexityTransition> complexityTransitions, List<CostModel> costModels) {
		super();
		this.complexityTransitions = complexityTransitions;
		this.costModels = costModels;
	}
	private double getTotalAverageCost() {
		FileUtil fileUtil=FileUtil.getFileUtil(TOTALCOSTLOG);
		String costs=fileUtil.readFile();
		String[] lines=costs.split("\n");
		double cost=0.0;
		for (String line : lines) {
			cost+=Double.valueOf(line.trim());
		}
		return lines.length!=0?(cost/(double) lines.length):0;
	}
	private void readWorkloadLog() {
		FileUtil fileUtil = FileUtil.getFileUtil(WORKLOADLOG);
		String content = fileUtil.readFile();
		String[] lines = content.split("\n");
		workloadLog = new ArrayList<>(Arrays.asList(lines));
	}
	public void predict() {
		readWorkloadLog();
		FileUtil fileUtil=FileUtil.getFileUtil(RESULTPATH);
		fileUtil.makeWholePath();
		fileUtil.clearFile();
		FileUtil fileUtilCoverage=FileUtil.getFileUtil(COSTCOVERAGELOG);
		fileUtilCoverage.makeWholePath();
		fileUtilCoverage.clearFile();
		fileUtilCoverage.appendFile("context,total_cost,contextwise_cost,percentage");
		double avgcost=getTotalAverageCost();
		for (ComplexityTransition complexityTransition : complexityTransitions) {
			for (ComplexityTransition complexityTransitionChild : complexityTransition.getChildList()) {
				String content=complexityTransition.getParent()+"->"+complexityTransitionChild.getParent()+" "+complexityTransition.getParentComplexity()+"->"+complexityTransition.getChildrenComplexity()+" ";
				double cost=0.0;
				for (CostModel costModel : costModels) {
					if(complexityTransitionChild.getParent().trim().equals(costModel.getContext())) {
						long averageInvocation=0;
						for (String workload : workloadLog) {
							if(workload.contains(",")) {
							String[] workloadArray=workload.split(",");
							for (String workloadItem : workloadArray) {
								averageInvocation+=Math.pow(Long.valueOf(workloadItem),complexityTransition.getChildrenComplexity()/2);
							}
							}
							else {
								averageInvocation+=Math.pow(Long.valueOf(workload),complexityTransition.getChildrenComplexity());
								System.out.println(averageInvocation);
							}
						}
						cost=costModel.getTime()*(averageInvocation/workloadLog.size());
						try {
							fileUtilCoverage.apppendLargeFile(costModel.getContext()+","+avgcost+","+cost+","+(cost/avgcost)+"\n");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						break;
					}
				}
				content+=cost+"\n";
				try {
					fileUtil.apppendLargeFile(content);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		
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

	public static void main(String[] args) throws IOException, InterruptedException, CallGraphException {
		new XSLTTransformer().transform();
		CallGraph callGraph = new StaticCallGraphExtractor().extractCallGraph();
		KProfileGraphBuilder graphBuilder = new KProfileGraphBuilder(callGraph);
		callGraph = graphBuilder.buildKProfileGraph();
		callGraph =new ComplexityModelDataGenerator(callGraph).generateData();
		new TrainingDataBuilder(callGraph).buildTrainingData();
		List<ComplexityTransition> complexityTransitions=new WDPBLoopExtractor().extractWDPBloop();
		List<CostModel> costModels=new AverageCostCalculator(callGraph).getCost();
		CostPrediction costPrediction=new CostPrediction(complexityTransitions, costModels);
		costPrediction.predict();
	}
}
