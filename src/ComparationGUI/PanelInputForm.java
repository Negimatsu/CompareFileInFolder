package ComparationGUI;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import Observer.ProgressBar;

public class PanelInputForm extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String source1;
	private String source2;
	private String fileList;
	private Integer skipLine;
	private JTextField source1Field;
	private JTextField source2Field;
	private JTextField refField;
	private JComboBox<Integer> skipLineField;
	private JButton clearBtn;
	private JButton compareBtn;
	private ProgressBar progressBar;
	private JCheckBox bwCheckBox;
	private boolean isBWReport;
	
	
	public PanelInputForm(){
		GridLayout layout = new GridLayout(9, 1);
		this.setLayout(layout);
		setDefault();
	}
	

	public void initComponentField(){
		EventGetPath e;
		//Source 1 Panel
		JPanel source1Panel = new JPanel();
		source1Panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		JLabel source1Label = new JLabel("Source 1 *");
		source1Label.setPreferredSize(new Dimension(100, 20));
		source1Field = new JTextField(40);		
		JButton source1Btn = new JButton("Choose File");
		source1Field.setText(source1);
		source1Label.setLabelFor(source1Field);
		
		source1Panel.add(source1Label);
		source1Panel.add(source1Field);
		source1Panel.add(source1Btn);
		e = new EventGetPath(source1Field, source1Label, true);
		source1Btn.addActionListener(e);
		this.add(source1Panel);
	
		
		//Source 2 Panel
		JPanel source2Panel = new JPanel();
		source2Panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		JLabel source2Label = new JLabel("Source 2 *");
		source2Label.setPreferredSize(new Dimension(100, 20));
		source2Field = new JTextField(40);
		JButton source2Btn = new JButton("Choose File");
		source2Field.setText(source2);
		source2Label.setLabelFor(source2Field);
		
		source2Panel.add(source2Label);
		source2Panel.add(source2Field);
		source2Panel.add(source2Btn);
		e = new EventGetPath(source2Field, source2Label, true);
		source2Btn.addActionListener(e);
		
		this.add(source2Panel);		
				
		//Reference Panel
		JPanel refFilePanel = new JPanel();
		refFilePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		JLabel refFileLabel = new JLabel("Reference File");
		refFileLabel.setPreferredSize(new Dimension(100, 20));
		refField = new JTextField(40);
		JButton refFileBtn = new JButton("Choose File");
		refField.setText(fileList);
		refFileLabel.setLabelFor(refField);
		
		refFilePanel.add(refFileLabel);
		refFilePanel.add(refField);
		refFilePanel.add(refFileBtn);
		e = new EventGetPath(refField, refFileLabel, false);
		refFileBtn.addActionListener(e);
		
		this.add(refFilePanel);
				
		//Skip line Panel
		JPanel skipLinePanel = new JPanel();
		skipLinePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		JLabel skipLineLabel = new JLabel("Skip Header line");
		skipLineLabel.setPreferredSize(new Dimension(100, 20));		
		DefaultComboBoxModel<Integer> listSkipline = new DefaultComboBoxModel<Integer>();
		int i = 0;
		for ( ; i <= 20; i++){
			listSkipline.addElement(i);
		}
		skipLineField = new JComboBox<Integer>(listSkipline);
		skipLineField.setSelectedItem(skipLine);	
		skipLineLabel.setLabelFor(skipLineField);
		
		
		skipLinePanel.add(skipLineLabel);
		skipLinePanel.add(skipLineField);
		this.add(skipLinePanel);
		
		JPanel isBWPanel = new JPanel();
		isBWPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		JLabel isBwLabel = new JLabel("Is BW");
		isBwLabel.setPreferredSize(new Dimension(100, 20));	
				
		bwCheckBox = new JCheckBox("");
		isBwLabel.setLabelFor(bwCheckBox);
		bwCheckBox.setSelected(true);
		
		isBWPanel.add(isBwLabel);
		isBWPanel.add(bwCheckBox);		
		
		this.add(isBWPanel);
		
		
		this.add(new JSeparator());

		
		//button line Panel
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		compareBtn = new JButton("Compare");
		clearBtn = new JButton("Clear");
		buttonPanel.add(compareBtn);
		buttonPanel.add(clearBtn);
		
		this.add(buttonPanel);
		
		setEventCompare();
		setEventClear();
		
	}
		
	
	public String getSource1() {
		source1 = source1Field.getText();
		return source1;
	}


	private void setSource1(String source1) {
		this.source1 = source1;
	}


	public String getSource2() {
		source2 = source2Field.getText();
		return source2;
	}


	private void setSource2(String source2) {
		this.source2 = source2;
	}


	public String getFileList() {
		fileList = refField.getText();
		return fileList;
	}


	private void setFileList(String fileList) {
		this.fileList = fileList;
	}


	public Integer getSkipLine() {
		skipLine = (Integer) skipLineField.getSelectedItem();
		return skipLine;
	}


	private void setSkipLine(Integer skipLine) {
		this.skipLine = skipLine;
	}
	
	private void setEventCompare(){
		EventCompare e;
		e = new EventCompare(source1Field, source2Field, refField, skipLineField, bwCheckBox);
		compareBtn.addActionListener(e);
	}
	
	private boolean isBWReport() {
		return isBWReport;
	}


	private void setBWReport(boolean isBWReport) {
		this.isBWReport = isBWReport;
	}
	
	private void setDefault(){
		this.setSource1("");
		this.setSource2("");
		this.setFileList("");
		this.setSkipLine(0);
		this.isBWReport = true;;

	}
	
	private void setField(){
		source1Field.setText(source1);
		source2Field.setText(source2);
		
		refField.setText(fileList);
		skipLineField.setSelectedIndex(skipLine);
		
		bwCheckBox.setSelected(isBWReport);
	}
	
	private void setEventClear(){
		clearBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(null, "Do you want to clear data?", "Confirm", JOptionPane.OK_CANCEL_OPTION);
				if (result == JOptionPane.OK_OPTION){
					setDefault();
					setField();
				}
					
			}
		});
	}


	
	
}
