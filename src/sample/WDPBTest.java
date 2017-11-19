package sample;

import java.util.List;

public class WDPBTest {
	List<MetaData> metaDataList;

	public WDPBTest(List<MetaData> metaDataList) {
		super();
		this.metaDataList = metaDataList;
	}

	public void handleAllSelect(MetaData metaData,int num) {
		GlobalInstrumentationString.contentBuffer.append("WDPBTest,handleAllSelect ");
		changeStatus(metaData,num);
		addMetadata(metaData);
		GlobalInstrumentationString.contentBuffer.append("exit-WDPBTest,handleAllSelect ");
	}

	private void changeStatus(MetaData metaData,int num) {
		GlobalInstrumentationString.contentBuffer.append("WDPBTest,changeStatus ");
		for (int i=0;i<num/2;i++) {
			// any constant operation
			addMetadata(metaData);
		}
		GlobalInstrumentationString.contentBuffer.append("exit-WDPBTest,changeStatus ");
	}
	public void addMetadata(MetaData metaData) {
		GlobalInstrumentationString.contentBuffer.append("WDPBTest,addMetadata ");
		metaDataList.add(metaData);
		GlobalInstrumentationString.contentBuffer.append("exit-WDPBTest,addMetadata ");
	}
	public int returnStatus(Character statusType) {
		GlobalInstrumentationString.contentBuffer.append("WDPBTest,returnStatus ");
		switch (statusType) {
		case 'S':
			GlobalInstrumentationString.contentBuffer.append("exit-WDPBTest,returnStatus ");
			return 0;
		case 'D':
			GlobalInstrumentationString.contentBuffer.append("exit-WDPBTest,returnStatus ");
			return -1;
		case 'A':
			GlobalInstrumentationString.contentBuffer.append("exit-WDPBTest,returnStatus ");
			return 1;
		default:
			GlobalInstrumentationString.contentBuffer.append("exit-WDPBTest,returnStatus ");
			return Integer.MAX_VALUE;
		}
	}

	public void writeStatusToFile(int status) {
		GlobalInstrumentationString.contentBuffer.append("WDPBTest,writeStatusToFile ");
		FileHandler fileHandler=FileHandler.getFileHandler("statuses.txt");
		fileHandler.writeFile(Integer.toString(status));
		GlobalInstrumentationString.contentBuffer.append("exit-WDPBTest,writeStatusToFile ");
	}

	public String readStatuses() {
		GlobalInstrumentationString.contentBuffer.append("WDPBTest,readStatuses ");
		FileHandler fileHandler=FileHandler.getFileHandler("statuses.txt");
		GlobalInstrumentationString.contentBuffer.append("exit-WDPBTest,readStatuses ");
		return fileHandler.readFile();
	}
}
