package ComparationModel;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils; 

/**
 * @author w.tangwanidgoon
 *
 */
/**
 * @author w.tangwanidgoon
 *
 */
/**
 * @author w.tangwanidgoon
 *
 */
public class CopyNotMatchFile {
	private String folderPreName;
	private String folderPostName;
	private File preFolder;
	private File postFolder;
	
	public CopyNotMatchFile() {
		folderPreName = "Source1Folder";
		folderPostName = "Sourece2Folder";
		initFolder();
	}
	
	private void initFolder(){
		preFolder = createFolder(folderPreName);
		postFolder = createFolder(folderPostName);
		deleteFileinFolder(preFolder);
		deleteFileinFolder(postFolder);
	}	

	/**
	 * This Method use for copy file form source to destination.
	 * @param source 
	 * @param isPre if true is Pre Folder, false is Post Folder.
	 * @throws IOException
	 */
	public void copyFileUsing(File source, boolean isPre)
			throws IOException {
		
		if (isPre){
			FileUtils.copyFileToDirectory(source, preFolder);
		}else{
			FileUtils.copyFileToDirectory(source, postFolder);
		}	
		
	}
	
	
	private File createFolder(String folderName){
		File theDir = new File(folderName);

		if (!theDir.exists()) {
		    System.out.println("creating directory: " + theDir.getPath());
		    boolean result = false;

		    try{
		        theDir.mkdir();
		        result = true;
		    } 
		    catch(SecurityException se){
		        //handle it
		    }        
		    if(result) {    
		        System.out.println("DIR created " + folderName);  
		    }
		}
		return theDir;
	}
	
	private void deleteFileinFolder(File path){
		try {
			FileUtils.cleanDirectory(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	
}
