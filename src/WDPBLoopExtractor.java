import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.FileUtil;

public class WDPBLoopExtractor {
	private static final String TRAINING_RESULT_FILE="training/complexity.txt";
	private Map<String,Double> complexityMap=new HashMap<String, Double>();
	private List<ComplexityTransition> complexityTransitions=new ArrayList<ComplexityTransition>();
	public void extractWDPBloop() {
		FileUtil fileUtil=FileUtil.getFileUtil(TRAINING_RESULT_FILE);
		String content=fileUtil.readFile();
		String[] lines=content.split("\n");
		for (String line : lines) {
			String[] splittedLine=line.split("\\t+");
			System.out.println(splittedLine[0]);
			if(splittedLine[0].indexOf(".csv")!=-1) {
			String parent=splittedLine[0].substring(0, splittedLine[0].indexOf(".csv")).trim();
			double complexity=Double.valueOf(splittedLine[1].trim());
			ComplexityTransition complexityTransition=new ComplexityTransition();
			complexityTransition.setParent(parent);
			complexityTransition.setParentComplexity(complexity);
			complexityTransitions.add(complexityTransition);
			}
		}
		for (ComplexityTransition complexityTransitionParent : complexityTransitions) {
			for (ComplexityTransition complexityTransitionChild : complexityTransitions) {
				if(!complexityTransitionParent.getParent().equals(complexityTransitionChild.getParent())&&complexityTransitionParent.getParentComplexity()!=complexityTransitionChild.getParentComplexity()) {
					if(checkIfChildIsComplexityTransition(complexityTransitionParent, complexityTransitionChild.getParent(), complexityTransitionChild.getParentComplexity())) {
						complexityTransitionParent.getChildList().add(complexityTransitionChild);
					}
				}
			}
		}
		List<ComplexityTransition> complexityTransitionsForWDPB=new ArrayList<ComplexityTransition>();
		for (ComplexityTransition complexityTransition : complexityTransitions) {
			if(!complexityTransition.getChildList().isEmpty()) {
				complexityTransitionsForWDPB.add(complexityTransition);
			}
		}
		for (ComplexityTransition complexityTransition : complexityTransitionsForWDPB) {
			System.out.println(complexityTransition.getParent()+" "+complexityTransition.getChildList());
		}
	}
	
	private boolean checkIfChildIsComplexityTransition(ComplexityTransition transition,String child,double complexity) {
		return child.indexOf(transition.getParent())!=-1&&child.split(",").length==transition.getParent().split(",").length+1&&complexity>transition.getParentComplexity()&&(transition.getChildrenComplexity()!=0?complexity==transition.getChildrenComplexity():true);
	}
	public static void main(String[] args) throws IOException, InterruptedException, CallGraphException {
		new XSLTTransformer().transform();
		CallGraph callGraph = new StaticCallGraphExtractor().extractCallGraph();
		KProfileGraphBuilder graphBuilder = new KProfileGraphBuilder(callGraph);
		callGraph = graphBuilder.buildKProfileGraph();
		callGraph =new ComplexityModelDataGenerator(callGraph).generateData();
		new TrainingDataBuilder(callGraph).buildTrainingData();
		new WDPBLoopExtractor().extractWDPBloop();
	}
}
