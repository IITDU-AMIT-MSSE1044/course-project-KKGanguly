import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import util.FileUtil;

public class WDPBLoopExtractor {
	private static final String TRAINING_RESULT_FILE="training/complexity.txt";
	private Map<String,Double> complexityMap=new HashMap<String, Double>();
	private List<ComplexityTransition> complexityTransitions=new ArrayList<ComplexityTransition>();
	public List<ComplexityTransition> extractWDPBloop() {
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
		List<ComplexityTransition> complexityTransitionChildren=new ArrayList<ComplexityTransition>(complexityTransitions);
		ListIterator<ComplexityTransition> iter = complexityTransitions.listIterator();
	
		while(iter.hasNext()){
			ComplexityTransition complexityTransitionParent=iter.next();
			ListIterator<ComplexityTransition> iterChild = complexityTransitionChildren.listIterator();
			while(iterChild.hasNext()){
				ComplexityTransition complexityTransitionChild=iterChild.next();
				//System.out.println(complexityTransitionParent.getParent()+" "+complexityTransitionParent.getParentComplexity());
				//System.out.println(complexityTransitionChild.getParent()+" "+complexityTransitionChild.getParentComplexity());
				if(complexityTransitionParent.getParent().equals(complexityTransitionChild.getParent())&&complexityTransitionParent.getParentComplexity()==complexityTransitionChild.getParentComplexity()) {
					continue;
				}
				
				//System.out.println(checkIfChildIsComplexityTransition(complexityTransitionParent, complexityTransitionChild.getParent(), complexityTransitionChild.getParentComplexity()));
					if(checkIfChildIsComplexityTransition(complexityTransitionParent, complexityTransitionChild.getParent(), complexityTransitionChild.getParentComplexity())) {
						complexityTransitionParent.getChildList().add(complexityTransitionChild);
						complexityTransitionParent.setChildrenComplexity(complexityTransitionChild.getParentComplexity());
					}else if(checkIfNewChildComplexityTransition(complexityTransitionParent, complexityTransitionChild.getParent(), complexityTransitionChild.getParentComplexity())) {
						ComplexityTransition complexityTransition=new ComplexityTransition();
						complexityTransition.setParent(complexityTransitionParent.getParent());
						complexityTransition.setParentComplexity(complexityTransitionParent.getParentComplexity());
						complexityTransition.getChildList().add(complexityTransitionChild);
						complexityTransition.setChildrenComplexity(complexityTransitionChild.getParentComplexity());
						iter.add(complexityTransition);
						iterChild.add(complexityTransition);
					}
				}
			}
		
	
		List<ComplexityTransition> complexityTransitionsForWDPB=new ArrayList<ComplexityTransition>();
		for (ComplexityTransition complexityTransition : complexityTransitions) {
			if(!complexityTransition.getChildList().isEmpty()) {
				complexityTransitionsForWDPB.add(complexityTransition);
			}
		}
		return complexityTransitionsForWDPB;
	}
	
	private boolean checkIfChildIsComplexityTransition(ComplexityTransition transition,String child,double complexity) {
		//System.out.println(transition.getParent()+" CHILD "+child);
		return child.indexOf(transition.getParent())!=-1&&child.split("\\s+").length==transition.getParent().split("\\s+").length+1&&complexity>transition.getParentComplexity()&&(transition.getChildrenComplexity()!=0?complexity==transition.getChildrenComplexity():true);
	}
	private boolean checkIfNewChildComplexityTransition(ComplexityTransition transition,String child,double complexity) {
		return child.indexOf(transition.getParent())!=-1&&child.split("\\s+").length==transition.getParent().split("\\s+").length+1&&complexity>transition.getParentComplexity()&&(transition.getChildrenComplexity()!=0?complexity!=transition.getChildrenComplexity():true);
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
