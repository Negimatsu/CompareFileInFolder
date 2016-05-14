package ComparationController;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;

import ComparationGUI.Form;
import ComparationModel.ComparationFolder;
import ComparationModel.ReadFile;
import ComparationModel.WriteLogsFile;

public class ComparationController {
	private String source1;
	private String source2;
	private String fileList;
	private int skipLine;
	private boolean isBW;
	private long startTime;
	private long processTime;
	private WriteLogsFile logsFile;
	private final int SIZE = 1024 * 1024 * 1024;
	private ComparationFolder compare;


	public ComparationController(String source1, String source2, String fileList, int skipLine, boolean isBW) {
		this.source1 = source1;
		this.source2 = source2;
		this.fileList = fileList;
		this.skipLine = skipLine;
		this.isBW = isBW;
	}

	public void commandCompare(){
		recordLogs();
		setStartTime();

		ReadFile preFolder = new ReadFile(1, source1); //1 Pre folder
		preFolder.listFilesForFolder();

		ReadFile postFolder = new ReadFile(2, source2); //2 folder
		postFolder.listFilesForFolder();

		ReadFile listFile = new ReadFile(3, fileList); // File log

		listFile.listFileName();

		HashMap<String, File> pre = preFolder.getFiles();
		HashMap<String, File> post = postFolder.getFiles();
		ArrayList<String> fileNames = listFile.getListFileName();

		compare = new ComparationFolder(pre, post, fileNames, skipLine, isBW);

		compare.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent arg0) {				
				if ("progress".equalsIgnoreCase(arg0.getPropertyName())){
					Form.progressBar.setIndeterminate(false);
					Form.progressBar.setValue((Integer) arg0.getNewValue());					
				}
				else if ("done".equalsIgnoreCase(arg0.getPropertyName()) || compare.isDone())
				{					
					compareDone();
				} 		  
				else if ("pending".equalsIgnoreCase(arg0.getPropertyName())){
					Form.progressBar.setVisible(true);
					Form.progressBar.setIndeterminate(true);
				}         
			}
		});
		//compare.execute();
	}


	private void compareDone(){
		setEndTime();

		String timeResult = String.format("Took %.3f ms %n", processTime / 1e6, SIZE / 1024 / 1024);
		System.out.printf(timeResult);

		JOptionPane.showMessageDialog(null, "SuccessFul with time" + timeResult);
		openLogsFile();
	}


	private void recordLogs(){
		logsFile = new WriteLogsFile();

	}

	private void openLogsFile(){
		logsFile.openErrorFile();
		logsFile.openLogsFile();
	}

	private void setStartTime(){
		startTime = System.nanoTime();
	}

	private void setEndTime(){
		processTime = System.nanoTime() - startTime;

	}


}
