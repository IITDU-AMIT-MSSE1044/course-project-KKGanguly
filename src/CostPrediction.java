import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import util.FileUtil;

public class CostPrediction {
	private List<ComplexityTransition> complexityTransitions=new ArrayList<ComplexityTransition>();
	private List<CostModel> costModels=new ArrayList<CostModel>();
	private static final String RESULTPATH="results/result.txt";

	public CostPrediction(List<ComplexityTransition> complexityTransitions, List<CostModel> costModels) {
		super();
		this.complexityTransitions = complexityTransitions;
		this.costModels = costModels;
	}

	public void predict() {
		FileUtil fileUtil=FileUtil.getFileUtil(RESULTPATH);
		fileUtil.makeWholePath();
		fileUtil.clearFile();
		for (ComplexityTransition complexityTransition : complexityTransitions) {
			for (ComplexityTransition complexityTransitionChild : complexityTransition.getChildList()) {
				String content=complexityTransition.getParent()+"->"+complexityTransitionChild.getParent()+" "+complexityTransition.getParentComplexity()+"->"+complexityTransition.getChildrenComplexity()+" ";
				double cost=0.0;
				for (CostModel costModel : costModels) {
					if(complexityTransitionChild.getParent().trim().equals(costModel.getContext())) {
						cost=costModel.getTime();
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
