package sample;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Executor {
	private WDPBTest wdpbTest;
	private int numberOfMetadatas;
	public Executor(int numberOfMetadatas) {
		this.numberOfMetadatas=numberOfMetadatas;
	}
	private void init() {
		// TODO Auto-generated method stub
		GlobalInstrumentationString.contentBuffer.append("Executor,init ");
		GlobalInstrumentationString.contentBufferWithCost.append("Executor,init ");
		long start=System.nanoTime();
		List<MetaData> metaDatas=new ArrayList<MetaData>();
		for(int i=0;i<numberOfMetadatas;i++) {
			metaDatas.add(getGeneratedMetadata());
		}
		wdpbTest=new WDPBTest(metaDatas);
		
		GlobalInstrumentationString.contentBuffer.append("exit-Executor,init ");
		GlobalInstrumentationString.contentBufferWithCost.append("exit-Executor,init:");
		GlobalInstrumentationString.contentBufferWithCost.append((System.nanoTime()-start)+" ");
	}
	public void run() {
		GlobalInstrumentationString.contentBuffer.append("Executor,run ");
		GlobalInstrumentationString.contentBufferWithCost.append("Executor,run ");
		long start=System.nanoTime();
		init();
		List<MetaData> metaDatas=new ArrayList<MetaData>();
		for(int i=0;i<numberOfMetadatas;i++) {
			metaDatas.add(getGeneratedMetadata());
		}
		for (int i=0;i<numberOfMetadatas;i++) {
			MetaData metaData=metaDatas.get(i);
			wdpbTest.handleAllSelect(metaData,numberOfMetadatas);
		}
		GlobalInstrumentationString.contentBuffer.append("exit-Executor,run ");
		
		GlobalInstrumentationString.contentBufferWithCost.append("exit-Executor,run:");
		GlobalInstrumentationString.contentBufferWithCost.append((System.nanoTime()-start)+" ");
	}
	
	private MetaData getGeneratedMetadata() {
		GlobalInstrumentationString.contentBuffer.append("Executor,getGeneratedMetadata ");
		GlobalInstrumentationString.contentBufferWithCost.append("Executor,getGeneratedMetadata ");
		long start=System.nanoTime();
		List<String> statuses=new ArrayList<String>();
		MetaData metaData=new MetaData();
		metaData.itemName="item"+new Random().nextInt();
		for(int i=0;i<1000;i++) {
			statuses.add("status"+new Random().nextInt());
		}
		metaData.compositeStatus=statuses;
		GlobalInstrumentationString.contentBuffer.append("exit-Executor,getGeneratedMetadata ");
		
		GlobalInstrumentationString.contentBufferWithCost.append("exit-Executor,getGeneratedMetadata:");
		GlobalInstrumentationString.contentBufferWithCost.append((System.nanoTime()-start)+" ");
		return metaData;
	}
}
