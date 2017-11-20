import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class Training {
	private static final String TRAINING_DIRECTORY="training";
	public void train() throws Exception {
		Process process=Runtime.getRuntime().exec("cmd /c training.bat",null,new File("training"));
		BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getErrorStream()));
		String output = null;
		while ((output = stdInput.readLine()) != null) {
			System.out.println(output);
		}
	}
	public static void main(String[] args) throws IOException, InterruptedException, CallGraphException {
		try {
			new Training().train();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
