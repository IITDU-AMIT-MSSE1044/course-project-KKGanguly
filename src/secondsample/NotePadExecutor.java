package secondsample;

public class NotePadExecutor {
	private NotePadSimulation notePadSimulation = new NotePadSimulation();

	public void run(String textLines, String selected) {
		GlobalInstrumentationString.contentBuffer.append("NotePadExecutor,run ");
		GlobalInstrumentationString.contentBufferWithCost.append("NotePadExecutor,run ");
		long start=System.nanoTime();
		addTextChunk(textLines);
		notePadSimulation.selectSingleWord(selected);
		GlobalInstrumentationString.contentBuffer.append("exit-NotePadExecutor,run ");
		GlobalInstrumentationString.contentBufferWithCost.append("exit-NotePadExecutor,run:");
		GlobalInstrumentationString.contentBufferWithCost.append((System.nanoTime()-start)+" ");
	}

	public void addTextChunk(String textLines) {
		GlobalInstrumentationString.contentBuffer.append("NotePadExecutor,addTextChunk ");
		GlobalInstrumentationString.contentBufferWithCost.append("NotePadExecutor,addTextChunk ");
		long start=System.nanoTime();
		String[] lines = textLines.split("\n");
		for (String line : lines) {
			notePadSimulation.addText(line);
		}
		GlobalInstrumentationString.contentBuffer.append("exit-NotePadExecutor,addTextChunk ");
		GlobalInstrumentationString.contentBufferWithCost.append("exit-NotePadExecutor,addTextChunk:");
		GlobalInstrumentationString.contentBufferWithCost.append((System.nanoTime()-start)+" ");
	}
}
