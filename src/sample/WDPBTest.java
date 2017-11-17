package sample;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class WDPBTest {
	List<MetaData> metaDataList;

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
		try {
			Files.write(Paths.get("log.txt"), "exit-WDPBTest,handleAllSelect ".getBytes(),StandardOpenOption.APPEND);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
			addMetadata(metaData);
		}
		try {
			Files.write(Paths.get("log.txt"), "exit-WDPBTest,changeStatus ".getBytes(),StandardOpenOption.APPEND);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void addMetadata(MetaData metaData) {
		try {
			Files.write(Paths.get("log.txt"), "WDPBTest,addMetadata ".getBytes(),StandardOpenOption.APPEND);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		metaDataList.add(metaData);
		try {
			Files.write(Paths.get("log.txt"), "exit-WDPBTest,addMetadata ".getBytes(),StandardOpenOption.APPEND);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
			try {
				Files.write(Paths.get("log.txt"), "exit-WDPBTest,returnStatus ".getBytes(),StandardOpenOption.APPEND);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return 0;
		case 'D':
			try {
				Files.write(Paths.get("log.txt"), "exit-WDPBTest,returnStatus ".getBytes(),StandardOpenOption.APPEND);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return -1;
		case 'A':
			try {
				Files.write(Paths.get("log.txt"), "exit-WDPBTest,returnStatus ".getBytes(),StandardOpenOption.APPEND);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return 1;
		default:
			try {
				Files.write(Paths.get("log.txt"), "exit-WDPBTest,returnStatus ".getBytes(),StandardOpenOption.APPEND);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
		FileHandler fileHandler=FileHandler.getFileHandler("statuses.txt");
		fileHandler.writeFile(Integer.toString(status));
		try {
			Files.write(Paths.get("log.txt"), "exit-WDPBTest,writeStatusToFile ".getBytes(),StandardOpenOption.APPEND);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String readStatuses() {
		try {
			Files.write(Paths.get("log.txt"), "WDPBTest,readStatuses ".getBytes(),StandardOpenOption.APPEND);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		FileHandler fileHandler=FileHandler.getFileHandler("statuses.txt");
		try {
			Files.write(Paths.get("log.txt"), "exit-WDPBTest,readStatuses ".getBytes(),StandardOpenOption.APPEND);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fileHandler.readFile();
	}
}
