import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import util.FileUtil;

public class TrainingDataBuilder {
	private CallGraph callGraph;
	private static final String TRAINING_DIRECTORY="training";
	public TrainingDataBuilder(CallGraph callGraph) {
		super();
		this.callGraph = callGraph;
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		File directory=new File(TRAINING_DIRECTORY);
		if(!directory.exists()) {
			directory.mkdirs();
		}
	}

	public void buildTrainingData() {
		for (Function function : callGraph.getFunctions()) {
			for (ComplexityModelData complexityModelData : function.getComplexityModelDatas()) {
				String trainingFilePath=TRAINING_DIRECTORY+File.separator+complexityModelData.getCallingContextAsString()+".csv";
				FileUtil fileUtil=FileUtil.getFileUtil(trainingFilePath);
				String content="";
				Map<List<Double>,Integer> workloadExecutionCountMap=complexityModelData.getWorkLoadToExecutionCountMap();
				for (List<Double> workloadAttributes : workloadExecutionCountMap.keySet()) {
					for (Double workloadAttribute : workloadAttributes) {
						content+=workloadAttribute+",";
					}
					content+=workloadExecutionCountMap.get(workloadAttributes)+"\n";
				}
				fileUtil.clearFile();
				fileUtil.appendFile("workload,Count");
				fileUtil.appendFile(content);
				System.out.println("here");
			}
		}
	}

}
