import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws IOException, InterruptedException, CallGraphException {
		System.out.println("Enter Mode:");
		Scanner scanner=new Scanner(System.in);
		int mode=scanner.nextInt();
		scanner.close();
		if(mode==1) {
			new XSLTTransformer("src\\secondsample").transform();
			CallGraph callGraph = new StaticCallGraphExtractor("run","NotePadExecutor").extractCallGraph();
			KProfileGraphBuilder graphBuilder = new KProfileGraphBuilder(callGraph);
			WorkloadSimulatorMultiParameter.simulate(1);
			callGraph = graphBuilder.buildKProfileGraph();
			System.out.println("Finished Generating K-Profile Graph");
			callGraph = new MultiparameterComplexityModelDataGenerator(callGraph).generateData();
			System.out.println("Finished Building Complexity Model");
			new TrainingDataBuilder(callGraph).buildTrainingDataMultiAttribute();
			System.out.println("Training Data Prepared Succesfully");
		}
		else if(mode==2) {
			new XSLTTransformer("src\\secondsample").transform();
			CallGraph callGraph = new StaticCallGraphExtractor("run","NotePadExecutor").extractCallGraph();
			KProfileGraphBuilder graphBuilder = new KProfileGraphBuilder(callGraph);
			WorkloadSimulatorMultiParameter.simulate(2);
			callGraph = graphBuilder.buildKProfileGraph();
			System.out.println("Finished Generating K-Profile Graph");
			callGraph =new ComplexityModelDataGenerator(callGraph).generateData();
			System.out.println("Finished Building Complexity Model");
			new TrainingDataBuilder(callGraph).buildTrainingData();
			System.out.println("Training Data Prepared Succesfully");
		}
		else if(mode==3) {
			new XSLTTransformer("src\\secondsample").transform();
			CallGraph callGraph = new StaticCallGraphExtractor("run","NotePadExecutor").extractCallGraph();
			KProfileGraphBuilder graphBuilder = new KProfileGraphBuilder(callGraph);
			WorkloadSimulatorMultiParameter.simulate(3);
			callGraph = graphBuilder.buildKProfileGraph();
			System.out.println("Finished Generating K-Profile Graph");
			callGraph = new MultiparameterComplexityModelDataGenerator(callGraph).generateData();
			System.out.println("Finished Building Complexity Model");
			new TrainingDataBuilder(callGraph).buildTrainingDataMultiAttribute();
			System.out.println("Training Data Prepared Succesfully");
			List<ComplexityTransition> complexityTransitions = new WDPBLoopExtractor("trainingextendedmulti/complexity.txt").extractWDPBloop();
			System.out.println("WDPB Loops Extracted");
			List<CostModel> costModels = new AverageCostCalculator(callGraph,"results-multiple/costs.txt").getCost();
			CostPrediction costPrediction = new CostPrediction(complexityTransitions, costModels,"results-multiple/result.txt","results-multiple/coverage.txt");
			costPrediction.predict();
			System.out.println("Cost Coverage Analysis Complete");
		}
		else {
			new XSLTTransformer("src\\secondsample").transform();
			CallGraph callGraph = new StaticCallGraphExtractor("run","NotePadExecutor").extractCallGraph();
			KProfileGraphBuilder graphBuilder = new KProfileGraphBuilder(callGraph);
			WorkloadSimulatorMultiParameter.simulate(4);
			callGraph = graphBuilder.buildKProfileGraph();
			System.out.println("Finished Generating K-Profile Graph");
			callGraph =new ComplexityModelDataGenerator(callGraph).generateData();
			System.out.println("Finished Building Complexity Model");
			new TrainingDataBuilder(callGraph).buildTrainingData();
			System.out.println("Training Data Prepared Succesfully");
			List<ComplexityTransition> complexityTransitions=new WDPBLoopExtractor("training/complexity.txt").extractWDPBloop();
			System.out.println("WDPB Loops Extracted");
			List<CostModel> costModels=new AverageCostCalculator(callGraph,"results-single/costs.txt").getCost();
			CostPrediction costPrediction=new CostPrediction(complexityTransitions, costModels,"results-single/result.txt","results-single/coverage.txt");
			costPrediction.predict();
			System.out.println("Cost Coverage Analysis Complete");
		}
	}
}


