import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import sample.Executor;

public class WorkloadSimulator {
	public static void simulate() {
		try {
			Files.write(Paths.get("log.txt"), ("").getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Files.write(Paths.get("workload.txt"), ("").getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i=10;i<30;i+=10) {
			new Executor(i).run();
			try {
				Files.write(Paths.get("log.txt"), "\n".getBytes(),StandardOpenOption.APPEND);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				Files.write(Paths.get("workload.txt"), (Integer.toString(i)+"\n").getBytes(),StandardOpenOption.APPEND);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(i);
		}
	}
	public static void main(String[] args) {
		simulate();
	}
}
