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
		GlobalInstrumentationString.contentBufferWithCost.append("WDPBTest,handleAllSelect ");
		long start=System.nanoTime();
		changeStatus(metaData,num);
		addMetadata(metaData);
		GlobalInstrumentationString.contentBuffer.append("exit-WDPBTest,handleAllSelect ");
		GlobalInstrumentationString.contentBufferWithCost.append("exit-WDPBTest,handleAllSelect:");
		GlobalInstrumentationString.contentBufferWithCost.append((System.nanoTime()-start)+" ");
	}

	private void changeStatus(MetaData metaData,int num) {
		GlobalInstrumentationString.contentBuffer.append("WDPBTest,changeStatus ");
		GlobalInstrumentationString.contentBufferWithCost.append("WDPBTest,changeStatus ");
		long start=System.nanoTime();
		for (int i=0;i<num/2;i++) {
			// any constant operation
			addMetadata(metaData);
		}
		
		GlobalInstrumentationString.contentBuffer.append("exit-WDPBTest,changeStatus ");
		GlobalInstrumentationString.contentBufferWithCost.append("exit-WDPBTest,changeStatus:");
		GlobalInstrumentationString.contentBufferWithCost.append((System.nanoTime()-start)+" ");
	}
	public void addMetadata(MetaData metaData) {
		GlobalInstrumentationString.contentBuffer.append("WDPBTest,addMetadata ");
		GlobalInstrumentationString.contentBufferWithCost.append("WDPBTest,addMetadata:");
		long start=System.nanoTime();
		metaDataList.add(metaData);
		
		GlobalInstrumentationString.contentBuffer.append("exit-WDPBTest,addMetadata ");
		GlobalInstrumentationString.contentBufferWithCost.append("exit-WDPBTest,addMetadata:");
		GlobalInstrumentationString.contentBufferWithCost.append((System.nanoTime()-start)+" ");
	}
	public int returnStatus(Character statusType) {
		GlobalInstrumentationString.contentBuffer.append("WDPBTest,returnStatus ");
		GlobalInstrumentationString.contentBufferWithCost.append("WDPBTest,returnStatus ");
		long start=System.nanoTime();
		switch (statusType) {
		case 'S':
			
			GlobalInstrumentationString.contentBuffer.append("exit-WDPBTest,returnStatus ");
			GlobalInstrumentationString.contentBufferWithCost.append("exit-WDPBTest,returnStatus:");
			GlobalInstrumentationString.contentBufferWithCost.append((System.nanoTime()-start)+" ");
			return 0;
		case 'D':
			
			GlobalInstrumentationString.contentBuffer.append("exit-WDPBTest,returnStatus ");
			GlobalInstrumentationString.contentBufferWithCost.append("exit-WDPBTest,returnStatus:");
			GlobalInstrumentationString.contentBufferWithCost.append((System.nanoTime()-start)+" ");
			return -1;
		case 'A':
			
			GlobalInstrumentationString.contentBuffer.append("exit-WDPBTest,returnStatus ");
			GlobalInstrumentationString.contentBufferWithCost.append("exit-WDPBTest,returnStatus:");
			GlobalInstrumentationString.contentBufferWithCost.append((System.nanoTime()-start)+" ");
			return 1;
		default:
			
			GlobalInstrumentationString.contentBuffer.append("exit-WDPBTest,returnStatus ");
			GlobalInstrumentationString.contentBufferWithCost.append("exit-WDPBTest,returnStatus:");
			GlobalInstrumentationString.contentBufferWithCost.append((System.nanoTime()-start)+" ");
			return Integer.MAX_VALUE;
		}
	}

	public void writeStatusToFile(int status) {
		GlobalInstrumentationString.contentBuffer.append("WDPBTest,writeStatusToFile ");
		GlobalInstrumentationString.contentBufferWithCost.append("WDPBTest,writeStatusToFile ");
		long start=System.nanoTime();
		FileHandler fileHandler=FileHandler.getFileHandler("statuses.txt");
		fileHandler.writeFile(Integer.toString(status));
		
		GlobalInstrumentationString.contentBuffer.append("exit-WDPBTest,writeStatusToFile ");
		GlobalInstrumentationString.contentBufferWithCost.append("exit-WDPBTest,writeStatusToFile:");
		GlobalInstrumentationString.contentBufferWithCost.append((System.nanoTime()-start)+" ");
	}

	public String readStatuses() {
		GlobalInstrumentationString.contentBuffer.append("WDPBTest,readStatuses ");
		GlobalInstrumentationString.contentBufferWithCost.append("WDPBTest,readStatuses ");
		long start=System.nanoTime();
		FileHandler fileHandler=FileHandler.getFileHandler("statuses.txt");
		
		GlobalInstrumentationString.contentBuffer.append("exit-WDPBTest,readStatuses ");
		GlobalInstrumentationString.contentBufferWithCost.append("exit-WDPBTest,readStatuses:");
		GlobalInstrumentationString.contentBufferWithCost.append((System.nanoTime()-start)+" ");
		return fileHandler.readFile();
	}
}
