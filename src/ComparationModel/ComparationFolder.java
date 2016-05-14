package ComparationModel;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import ComparationGUI.Form;
import Observer.ProgressBar;

public class ComparationFolder extends SwingWorker<Integer, String>{

	private HashMap<String, File> preFolder;
	private HashMap<String, File> postFolder;
	private ArrayList<String> listFile;
	private boolean result = false;
	private int skipLine;
	private ArrayList<String> listFileNotMatch;
	private boolean isBW;

	public ComparationFolder(HashMap<String, File> preFolder, HashMap<String, File> postFolder, ArrayList<String> listFile, int skipline, boolean isBW) {
		this.preFolder = preFolder;
		this.postFolder = postFolder;
		this.listFile = listFile;
		this.skipLine = skipline;
		this.isBW = isBW;
	}	

	public void compare() throws Exception {	

		//publish("Listing all text files under the directory: " + preFolder + " and " + postFolder);

		if (!isFileNumberEqual()){
			System.err.println("Source 1 have " + preFolder.size() + "files\n" +
					"Source 2 have " + postFolder.size() + "files");
			//			publish("Wait");
			result = isFileMissing();

			failIfInterrupted();
		}else{
			compareAllFile();
		}		
	}


	private void compareAllFile(){
		int numberOfCompare = 0;
		int numberOfNotEqual = 0;
		float divValue = getFileNumber();		
		listFileNotMatch = new ArrayList<>();
		
		for (String filename : preFolder.keySet()){
			int progressValue = (int)  Math.floor((numberOfCompare + 1) * 100 / divValue);
			//			if (progressValue%50 == 0){
			setProgress(progressValue);
			//			}
			//			Form.progressBar.setValue(progressValue);
			//			Form.progressBar.setIndeterminate(false);
			//			if ((numberOfCompare*divValue)%20 == 0 ){
			//				progressNumber += 5;
			////				progressbar.updateBar(progressNumber);
			//				ProgressBar.updateProgressValue(progressNumber);
			//				JOptionPane.showMessageDialog(null, "Hello" + progressNumber);
			//			}

			File preFile = preFolder.get(filename);
			File postFile = postFolder.get(filename);
			ComparationFile compareFile = new ComparationFile(preFile, postFile, skipLine, isBW);

			if (compareFile.compareFile()){
				System.out.println("File "+preFile.getName()+" is Match.");
			}else{
				listFileNotMatch.add(filename);
				System.err.println("File "+preFile.getName()+" is not Match.");
				System.err.println(compareFile.getErrMessage());
				numberOfNotEqual++;				
			}			
			numberOfCompare++;
		}
		if (numberOfNotEqual > 0){
			System.err.println("\n\n**************************************************************");
			System.err.println("\nSummary file not equal is " + numberOfNotEqual + " File(s).");
		}
		
		copyFileNotMatch();
		outErrorFile();
	}
	
	
	private void copyFileNotMatch(){
		if (!listFileNotMatch.isEmpty()){
			CopyNotMatchFile copyFile = new CopyNotMatchFile();
			
			for (String filename : listFileNotMatch){
				File preFile = preFolder.get(filename);
				File postFile = postFolder.get(filename);
				
				try {
					copyFile.copyFileUsing(preFile, true);
					copyFile.copyFileUsing(postFile, false);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
		
	}

	private void outErrorFile(){
		if (!listFileNotMatch.isEmpty()){
			System.err.println("\nList file is not match : ");
			for (String filename : listFileNotMatch){
				System.err.println(filename);
			}
		}
	}



	private boolean isFileNumberEqual(){
		if (preFolder.size() == postFolder.size()){
			result = true;
		}else{
			System.err.println("File in 2 Source not equal.\n");
			result = false;			
		}
		return result;
	}



	private boolean isFileMissing(){
		boolean isFileMissing = false;
		if (listFile.size() > 0 ){
			isFileMissing =  findFileMissingFromListFile();
		}else{
			isFileMissing = findFilesMissing();
		}

		return isFileMissing;
	}


	private boolean findFileMissingFromListFile(){
		boolean isMissing = false;
		for(String filename : listFile ){
			if (!preFolder.containsKey(filename) && !preFolder.containsKey(filename)){
				isMissing = true;
				System.err.println("Connot find file " + filename + "in Source1(Pre) and Source2(Post)");
			}			
			else if (!preFolder.containsKey(filename)){
				isMissing = true;
				System.err.println("Connot find file " + filename + "in Source1(Pre)");
			}			
			else if (!postFolder.containsKey(filename)){
				isMissing = true;
				System.err.println("Connot find file " + filename + "in Source2(Post)");
			}
		}

		return isMissing;
	}


	private boolean findFilesMissing(){
		//		HashMap<String, File> preTemp = preFolder;
		//		HashMap<String, File> postTemp = postFolder;
		List<String> preTemp = new ArrayList<String>(preFolder.keySet());
		List<String> postTemp = new ArrayList<String>(postFolder.keySet());

		List<String> preKeys = new ArrayList<String>(preFolder.keySet());
		List<String> postKeys = new ArrayList<String>(postFolder.keySet());
		//		System.out.println(preFolder.keySet());
		//		System.out.println(postFolder.keySet());
		//		
		//		
		//		for(Iterator<Map.Entry<String, File>> it = preFolder.entrySet().iterator(); it.hasNext();)
		//		{
		//		    Map.Entry<String, File> entry = it.next();		  	
		//			System.out.println(entry.getKey() + "Hello");
		//			publish("Wait");
		//			if(postFolder.containsKey(entry.getKey())){
		//				preTemp.remove(entry.getKey());
		//				postTemp.remove(entry.getKey());
		//			}else{
		//				System.err.println("Connot find file "+ entry.getKey()+" in Source2(Post)");
		//				result = false;
		//			}
		//		 }
		//		 JOptionPane.showMessageDialog(null, "1");
		//		      
		for (String filenamePre : preKeys){
			if(postKeys.contains(filenamePre)){
				preTemp.remove(filenamePre);
				postTemp.remove(filenamePre);
			}else{
				System.err.println("Connot find file ,"+ filenamePre+",in Source2 (Post)");
				result = false;
			}
		}

		for (String filenamePost : postTemp){
			System.err.println("Connot find file ,"+ filenamePost+",in Source1(Pre)");
			result = false;
		}

		return result;
	}


	public boolean isResult(){
		return this.result;
	}

	private int getFileNumber(){
		return preFolder.keySet().size();
	}


	/* Thread Zone
	 * (non-Javadoc)
	 * @see javax.swing.SwingWorker#doInBackground()
	 */

	@Override
	protected Integer doInBackground() throws Exception {
		compare();
		return null;
	}

	
	@Override
	protected void done() {
		try {        	 
			//             JOptionPane.showMessageDialog(null, "Done method cancel status" + isCancelled());
			publish("done");
		} catch (Exception e) {
		}
	}

	private void failIfInterrupted() throws InterruptedException {
		if (Thread.currentThread().isInterrupted()) {
			throw new InterruptedException("Interrupted while comparing files");
		}
	}

	@Override
	protected void process(final List<String> chunks) {
		// Updates the messages text area
		for (final String string : chunks) {
			System.err.println(string);
		}
	}
	
}
