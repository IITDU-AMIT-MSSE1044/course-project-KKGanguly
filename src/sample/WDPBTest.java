package sample;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class WDPBTest {
	List<MetaData> metaDataList;
	FileHandler fileHandler = FileHandler.getFileHandler("statuses.txt");

	public WDPBTest(List<MetaData> metaDataList) {
		super();
		this.metaDataList = metaDataList;
	}

	public void handleAllSelect(MetaData metaData) {
		try {
			Files.write(Paths.get("log.txt"), "WDPBTest,handleAllSelect ".getBytes(),StandardOpenOption.APPEND);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		changeStatus(metaData);
	}

	private void changeStatus(MetaData metaData) {
		try {
			Files.write(Paths.get("log.txt"), "WDPBTest,changeStatus ".getBytes(),StandardOpenOption.APPEND);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (String status : metaData.compositeStatus) {
			// any constant operation
		}
	}
	public void add(MetaData metaData) {
		try {
			Files.write(Paths.get("log.txt"), "WDPBTest,add ".getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		metaDataList.add(metaData);
	}
	public int returnStatus(Character statusType) {
		try {
			Files.write(Paths.get("log.txt"), "WDPBTest,returnStatus ".getBytes(),StandardOpenOption.APPEND);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		switch (statusType) {
		case 'S':
			return 0;
		case 'D':
			return -1;
		case 'A':
			return 1;
		default:
			return Integer.MAX_VALUE;
		}
	}

	public void writeStatusToFile(int status) {
		try {
			Files.write(Paths.get("log.txt"), "WDPBTest,writeStatusToFile ".getBytes(),StandardOpenOption.APPEND);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fileHandler.writeFile(Integer.toString(status));
	}

	public String readStatuses() {
		try {
			Files.write(Paths.get("log.txt"), "WDPBTest,readStatuses ".getBytes(),StandardOpenOption.APPEND);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fileHandler.readFile();
	}
}
