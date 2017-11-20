package secondsample;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NotePadSimulation {
	private List<String> texts=new ArrayList<String>();
	
	public void addText(String text) {
		GlobalInstrumentationString.contentBuffer.append("NotePadSimulation,addText ");
		GlobalInstrumentationString.contentBufferWithCost.append("NotePadSimulation,addText ");
		long start=System.nanoTime();
		texts.add(text);
		GlobalInstrumentationString.contentBuffer.append("exit-NotePadSimulation,addText ");
		GlobalInstrumentationString.contentBufferWithCost.append("exit-NotePadSimulation,addText:");
		GlobalInstrumentationString.contentBufferWithCost.append((System.nanoTime()-start)+" ");
	}
	public void removeText(String text) {
		GlobalInstrumentationString.contentBuffer.append("NotePadSimulation,removeText ");
		GlobalInstrumentationString.contentBufferWithCost.append("NotePadSimulation,removeText ");
		long start=System.nanoTime();
		texts.remove(text);
		GlobalInstrumentationString.contentBuffer.append("exit-NotePadSimulation,removeText ");
		GlobalInstrumentationString.contentBufferWithCost.append("exit-NotePadSimulation,removeText:");
		GlobalInstrumentationString.contentBufferWithCost.append((System.nanoTime()-start)+" ");
	}
	public void selectSingleWord(String searched) {
		GlobalInstrumentationString.contentBuffer.append("NotePadSimulation,selectSingleWord ");
		GlobalInstrumentationString.contentBufferWithCost.append("NotePadSimulation,selectSingleWord ");
		long start=System.nanoTime();
		for (String line : texts) {
			searchLine(line,searched);
		}
		GlobalInstrumentationString.contentBuffer.append("exit-NotePadSimulation,selectSingleWord ");
		GlobalInstrumentationString.contentBufferWithCost.append("exit-NotePadSimulation,selectSingleWord:");
		GlobalInstrumentationString.contentBufferWithCost.append((System.nanoTime()-start)+" ");
	}
	private void searchLine(String line,String searched) {
		GlobalInstrumentationString.contentBuffer.append("NotePadSimulation,searchLine ");
		GlobalInstrumentationString.contentBufferWithCost.append("NotePadSimulation,searchLine ");
		long start=System.nanoTime();
		String[] words=line.split("\\s+");
		for (int i=0;i<words.length;i++) {
			if(words[i].equals(searched)) {
				changeColor(i);
			}
			muteColor(i);
		}
		GlobalInstrumentationString.contentBuffer.append("exit-NotePadSimulation,searchLine ");
		GlobalInstrumentationString.contentBufferWithCost.append("exit-NotePadSimulation,searchLine:");
		GlobalInstrumentationString.contentBufferWithCost.append((System.nanoTime()-start)+" ");
	}
	private void changeColor(int i) {
		GlobalInstrumentationString.contentBuffer.append("NotePadSimulation,changeColor ");
		GlobalInstrumentationString.contentBufferWithCost.append("NotePadSimulation,changeColor ");
		long start=System.nanoTime();
		try {
			Thread.sleep(new Random().nextInt(150));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		//change color
		GlobalInstrumentationString.contentBuffer.append("exit-NotePadSimulation,changeColor ");
		GlobalInstrumentationString.contentBufferWithCost.append("exit-NotePadSimulation,changeColor:");
		GlobalInstrumentationString.contentBufferWithCost.append((System.nanoTime()-start)+" ");
	}
	private void muteColor(int i) {
		GlobalInstrumentationString.contentBuffer.append("NotePadSimulation,muteColor ");
		GlobalInstrumentationString.contentBufferWithCost.append("NotePadSimulation,muteColor ");
		long start=System.nanoTime();
		try {
			Thread.sleep(new Random().nextInt(150));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		//mute color
		GlobalInstrumentationString.contentBuffer.append("exit-NotePadSimulation,muteColor ");
		GlobalInstrumentationString.contentBufferWithCost.append("exit-NotePadSimulation,muteColor:");
		GlobalInstrumentationString.contentBufferWithCost.append((System.nanoTime()-start)+" ");
	}
}
