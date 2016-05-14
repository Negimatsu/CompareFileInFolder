package ComparationModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import java.awt.Component;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;  


public class ReadFile extends Component{

	private String path;
	private int type;
	private String typeText;
	private HashMap<String, File> files;
	private FileReader listFileReader;
	private ArrayList<String> listFileName;

	/**
	 * 
	 * @param type 1 is pre folder, 2 is post folder and 3 is Text File. 
	 */
	public ReadFile(int type, String path) {
		this.type = type;
		listFileName = new ArrayList<String>();
		
		setTypeText();		
		setPath(path);
		files = new HashMap<String, File>();
	}


	public void setPath(String path){

		this.path = path;
	}

	public void setPath(){
		File selectedFolder = null;
		JFileChooser fileChooser = new JFileChooser();

		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		fileChooser.setDialogTitle("Select " + typeText);
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		int result = fileChooser.showOpenDialog(this);		
		if (result == JFileChooser.APPROVE_OPTION) {

			selectedFolder = fileChooser.getSelectedFile();

			System.out.println("Selected file: " + selectedFolder.getAbsolutePath());
		}

		this.path = selectedFolder.getAbsolutePath();
	}	

	public void listFilesForFolder() {
		listFilesForFolder(path);
	}	

	public void listFilesForFolder(final String folderName) {
		int fileNumber = 1;
		if (isTextFile()){
			JOptionPane.showMessageDialog(null, "This is not Folder File");
			System.err.println("This paht ("+path+") is not Text File.");
		}
		else{
			File folder = new File(folderName);
			
			for (final File fileEntry : folder.listFiles()) {
				if (fileEntry.isDirectory()) {
					listFilesForFolder(fileEntry.getName());
				} else {
					files.put(fileEntry.getName(), fileEntry);	            
				}
				fileNumber++;
			}
		}
	}
	
	
	public void listFileName() {
		if (!isTextFile()){
			JOptionPane.showMessageDialog(null, "This is not Text File");
			System.err.println("This paht ("+path+") is not Text File.");
		}else if(isPathNull()){			
			System.out.println("Not have file List");
		}
		else{
			File listFilename = new File(path);
			listFileReader = readFile(listFilename);
			
			BufferedReader bufferListFile = new BufferedReader(listFileReader);
			
			
			String line = null;
			
			try {
				while ((line = bufferListFile.readLine()) != null)
				{				
					listFileName.add(line);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}finally {
				if (bufferListFile != null) {
					try {
						bufferListFile.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	

	public String getPath() {
		return path;
	}

	public HashMap<String, File> getFiles(){
		return this.files;
	}

	public String getTypeText() {
		return typeText;
	}

	private void setTypeText() {

		switch (type){		
		case 1 :
			this.typeText = "Pre folder";
			break;
		case 2 :
			this.typeText = "Post Folder";
			break;
		default :
			this.typeText = "I don't know folder";

		}

	}

	private boolean isTextFile(){
		if (type == 3){
			return true;
		}else{
			return false;
		}

	}
	
	
	private FileReader readFile(File filePath){		
		FileReader listFileReader = null;
		try {
			listFileReader = new FileReader(filePath);					
						
			
		} catch (FileNotFoundException e) {
			System.err.println(filePath.getAbsolutePath() + "File not found");
			e.printStackTrace();
		} finally {
			if (listFileReader != null) {
				try {
					listFileReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return listFileReader;
	}
	
	private boolean isPathNull(){
		boolean pathNull = false;
		if (this.path == null){
			pathNull = true;
		}else if (this.path.isEmpty()){
			pathNull = true;
		}else{
			pathNull = false;
		}
		
		return pathNull; 
	}
	
	
	public ArrayList<String> getListFileName() {
		return listFileName;
	}

}
