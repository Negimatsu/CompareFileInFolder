package ComparationGUI;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import ComparationController.ComparationController;

public class EventCompare extends Component implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField source1;
	private JTextField source2;
	private JTextField fileList;
	private JComboBox<Integer> skipLine;
	private JCheckBox isBWCheckBox;
	
	private String source1Txt;
	private String source2Txt;
	private String fileListTxt;
	private int skipLineInt;
	private boolean isBW;
	
	public EventCompare(JTextField source1, JTextField source2, JTextField fileList, JComboBox<Integer> skipLine, JCheckBox
			isBW) {
		this.source1 = source1;
		this.source2 = source2;
		this.fileList = fileList;
		this.skipLine = skipLine;
		this.isBWCheckBox = isBW;
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		setValue();		
		this.isBW = isBWCheckBox.isSelected();
		if(isValidateData()){			
			ComparationController compare = new ComparationController(source1Txt, source2Txt, fileListTxt, skipLineInt, isBW);
			compare.commandCompare();
		}
	}
	
	public void setValue(){
		this.source1Txt = source1.getText();
		this.source2Txt = source2.getText();
		this.fileListTxt = fileList.getText();
		this.skipLineInt = (Integer) skipLine.getSelectedItem();
	}
	
	@SuppressWarnings("unused")
	private boolean isNotNullFileList(){
		return fileListTxt.equals(null) || fileListTxt.isEmpty();
	}
	
	private boolean isNotNullSource1(){
		return source1Txt.equals(null) || source1Txt.isEmpty();
	}
	
	private boolean isNotNullSource2(){
		return source2Txt.equals(null) || source2Txt.isEmpty();
	}
	
	
	private boolean isValidateData(){
		boolean result = true;
		String message = "";
		if (isNotNullSource1() && isNotNullSource2()){
			result = false;
			message = "Field Source 1 and Source 2 shouldn't have null value.";
		}else if (isNotNullSource1()){
			result = false;
			message =  "Field Source 1 shouldn't have null value.";
		}else if (isNotNullSource2()){
			result = false;
			message = "Field Source 2 shouldn't have null value.";
		}
		
		
		if (!message.isEmpty()){
			JOptionPane.showMessageDialog(null, message + "\n" + "Please Field again");
		}
		
		return result;
		
	}

	private boolean isBW() {
		return isBW;
	}

	private void setBW(boolean isBW) {
		this.isBW = isBW;
	}
	

}
