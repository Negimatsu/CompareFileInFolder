package ComparationGUI;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

import Observer.ProgressBar;

public class Form implements Runnable{
	private JFrame frame;
	private PanelInputForm fieldPanel;
	private JPanel headerPanel;
	private JCheckBox bwCheckBox;
	

	public static ProgressBar progressBar;
		
	public Form() {
		frame = new JFrame();						
		intLayout();
		initFrame();
	}
	
	
	private void initFrame(){
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(700, 250);
		frame.setLocationRelativeTo(null); 
		frame.setTitle("Comparation File");
		frame.setResizable(false);
		frame.addWindowListener(new WindowAdapter() {
	         public void windowClosing(WindowEvent windowEvent){
	            System.exit(0);
	         }        
	      });   
		frame.setLayout(new GridLayout(1,1));
		
//		frame.add(headerPanel);
		frame.add(fieldPanel);
		
	    frame.pack();
	    frame.setVisible(true);
		
	}
	
	private void intLayout(){
		headerPanel = new JPanel();
		headerPanel.setLayout(new FlowLayout());
		headerPanel.setSize(700, 10);
		
		fieldPanel = new PanelInputForm();
		
		fieldPanel.add(headerPanel);
	    welcomeMessage();


	    fieldPanel.initComponentField();
	   
	    
	    initProgressbar();
	}
	
	
	private void welcomeMessage(){
		JLabel welcomeMsg = new JLabel("Welcome to Comaparation file between folder");
		
		headerPanel.add(welcomeMsg);
	}
	
	private void initProgressbar(){
		progressBar = new ProgressBar();
		Thread threadProgress = new Thread(progressBar);
		
		progressBar.setStringPainted(true);
		threadProgress.start();
		fieldPanel.add(progressBar);	
		
	}



	@Override
	public void run() {
		intLayout();	
		initFrame();
		
	}

}
