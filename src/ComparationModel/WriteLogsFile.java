package ComparationModel;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class WriteLogsFile {
	private String errorFileName;
	private String logsFileName;
	
	public WriteLogsFile() {
		errorFileName = "error.txt";
		logsFileName = "logs.txt";
		writeFile();
	}	
	
	private void writeFile(){
		PrintStream outLogs = null;
		try {
			File fileLogs = new File(logsFileName); //Your file
			FileOutputStream fos = new FileOutputStream(fileLogs);
			outLogs = new PrintStream(fos);
			System.setOut(outLogs);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		PrintStream outErr = null;
		try {
			File fileError = new File(errorFileName); //Your file
			FileOutputStream fos = new FileOutputStream(fileError);
			outErr = new PrintStream(fos);
			System.setErr(outErr);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public void openErrorFile(){
		try {
			Desktop.getDesktop().open(new File(errorFileName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void openLogsFile(){
		try {
			Desktop.getDesktop().open(new File(logsFileName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
