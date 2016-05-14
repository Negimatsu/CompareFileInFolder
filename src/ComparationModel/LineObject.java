package ComparationModel;
import java.text.DecimalFormat;

public class LineObject {

	private String line;
	private String SPLIT_SYMBOLS = "|";
	private String[] data;
	private String value;
	private String lineNumber;
	private boolean isValue;

	public LineObject(String line) {
		this.line = line;
		splitLine();
		convertData();
	}


	private void splitLine(){
		data = line.split(SPLIT_SYMBOLS);
		lineNumber = data[0];
	}

	private void convertData(){
		if (haveValue()){
			String stringValue = data[1];			
			Float valueFloat = Float.parseFloat(stringValue);
			DecimalFormat df = new DecimalFormat("0.00");
			df.setMaximumFractionDigits(2);
			value = df.format(valueFloat);
		}
	}

	private boolean haveValue(){
		if (data[1].isEmpty()){
			isValue = false;
		}else{
			isValue = true;
		}
		return isValue;
	}


	public String getValue(){
		return value;
	}

	public String getLineNumber(){
		return lineNumber;
	}

	public boolean valueNotNull(){
		return isValue;
	}

	public boolean compareData(LineObject compareLine){
		boolean result = false;
		if (this.getLineNumber().equals(compareLine.getLineNumber())){

			if (this.valueNotNull() && compareLine.valueNotNull()){
				result =  this.getValue().equals(compareLine.getValue()) ? true : false; 
			}else if (this.valueNotNull() == compareLine.valueNotNull()){
				result = true;
			}else{
				result = false;			
			}

		}else{
			result = false;
		}

		return result;
	}

}
