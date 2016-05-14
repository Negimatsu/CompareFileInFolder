package ComparationGUI;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class EventGetPath extends Component implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField field;
	private JLabel label;
	private boolean isFolder;
	
	
	public EventGetPath(JTextField efield, JLabel elabel, boolean isFolder) {		
		field = efield;
		label = elabel;
		this.isFolder = isFolder;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		File selectedFolder = null;
		JFileChooser fileChooser = new JFileChooser();
		
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		fileChooser.setDialogTitle("Select " + label.getText());
		
		if (isFolder){
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		}
		
		int result = fileChooser.showOpenDialog(this);		
		if (result == JFileChooser.APPROVE_OPTION) {
			
			selectedFolder = fileChooser.getSelectedFile();
		    
		    System.out.println("Selected file: " + selectedFolder.getAbsolutePath());
		}
		
		field.setText(selectedFolder.getAbsolutePath());
		
	}

	
}
