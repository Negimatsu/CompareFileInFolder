package ComparationModel;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

public class ComparationFile {
	private File preFile;
	private File postFile;
	private boolean resultAll;
	private FileReader preReader;
	private FileReader postReader;
	private BufferedReader bufferPre;
	private BufferedReader bufferPost;
	private int skipLine;
	private String errMessage;
	private int MAX_ERRORLINE = 5;
	private boolean isBW;


	public ComparationFile(File pre, File post, int skipLine, boolean isBW) {
		this.preFile = pre;
		this.postFile = post;
		this.resultAll = false;
		this.skipLine = skipLine;
		this.errMessage = "";
		this.isBW = isBW;
		readFile();

	}

	public boolean compareFile(){
		if (islineNumberEqual()){
			readFile();
			if (isBW){
			
				if (!isCompareLinesEqualForBW()){
					resultAll = false;
				}
			}else{
				if (!isCompareLinesEqual()){
					resultAll = false;
				}
			}
		}else{
			addErrMessage("\t Line not equal in pre file between " + preFile.getName() + " and " + postFile.getName() + "\n");			
			resultAll = false;
		}
		return resultAll;

	}
	
	private boolean isCompareLinesEqual(){
		boolean resultFile = false;

		bufferPre = new BufferedReader(preReader);
		bufferPost = new BufferedReader(postReader);

		try {			

			int lineNumber = 1;
			int lineError = 0;
			String preLine = null;
			String postLine = null;

			resultFile = true;
			while ((preLine = bufferPre.readLine()) != null && (postLine = bufferPost.readLine()) != null)
			{				
				
				if (!preLine.equals(postLine) && lineNumber > skipLine)   //Not compare 3 line header
				{
					resultFile = false;
					addErrMessage("\tString in line " + lineNumber + " isn't equal.\n");
					lineError++;
				}
				
				if (lineError > MAX_ERRORLINE){
					addErrMessage("\tString in line more than "+ MAX_ERRORLINE +" error.\n");
					break;
				}
				lineNumber++;
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}				

		return resultFile;
	}

	private boolean isCompareLinesEqualForBW(){		
		boolean resultFile = false;

		bufferPre = new BufferedReader(preReader);
		bufferPost = new BufferedReader(postReader);

		try {			

			int lineNumber = 1;
			int lineError = 0;
			String preLine = null;
			String postLine = null;
			LineObject preLineObj = null;
			LineObject postLineObj = null;

			resultFile = true;
			while ((preLine = bufferPre.readLine()) != null && (postLine = bufferPost.readLine()) != null)
			{				
				preLineObj = new LineObject(preLine);
				postLineObj = new LineObject(postLine);
				
				
				if (!preLineObj.compareData(postLineObj) && lineNumber > skipLine)   //Not compare 3 line header
				{
					resultFile = false;
					addErrMessage("\tString in line " + lineNumber + " isn't equal.\n");
					lineError++;
				}
				
				if (lineError > MAX_ERRORLINE){
					addErrMessage("\tString in line more than "+ MAX_ERRORLINE +" error.\n");
					break;
				}
				lineNumber++;
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}				

		return resultFile;
	}


	private boolean islineNumberEqual() {
		LineNumberReader linePre = new LineNumberReader(preReader);
		LineNumberReader linePost = new LineNumberReader(postReader);
		try {
			while ((linePre.readLine()) != null);
			while ((linePost.readLine()) != null);
			
			if (linePre.getLineNumber() == linePost.getLineNumber()){
				resultAll = true;
				return true;
			}
		} catch (IOException e) {			
			e.printStackTrace();
		}
		return false;
	}


	private void readFile(){		

		try {
			preReader = new FileReader(preFile);					

			postReader = new FileReader(postFile);

		} catch (FileNotFoundException e) {
			System.err.println(preFile.getAbsolutePath() + "File not found");
			e.printStackTrace();
		}
	}

	public boolean isResultAll() {
		return resultAll;
	}

	public String getErrMessage() {
		return errMessage;
	}

	private void addErrMessage(String errMessage) {
		this.errMessage += errMessage;
	}

}
