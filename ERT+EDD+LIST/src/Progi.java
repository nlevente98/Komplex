import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.AbstractAction;
import javax.swing.Action;

public class Progi {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private final Action action = new SwingAction();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Progi window = new Progi();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Progi() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(100, 11, 90, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JTextPane txtpnWorks = new JTextPane();
		txtpnWorks.setEditable(false);
		txtpnWorks.setText("Jobs:");
		txtpnWorks.setBounds(10, 11, 40, 20);
		frame.getContentPane().add(txtpnWorks);
		
		JTextPane txtpnWorkstations = new JTextPane();
		txtpnWorkstations.setText("Workstations:");
		txtpnWorkstations.setEditable(false);
		txtpnWorkstations.setBounds(10, 42, 80, 20);
		frame.getContentPane().add(txtpnWorkstations);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(100, 42, 90, 20);
		frame.getContentPane().add(textField_1);
		
		JButton btnNewButton = new JButton("Generate");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int a = Integer.parseInt(textField.getText());
				textField_1.setText(Integer.toString(a));
			}
		});
		btnNewButton.setAction(action);
		btnNewButton.setBounds(260, 39, 120, 20);
		frame.getContentPane().add(btnNewButton);
	}
	private class SwingAction extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
}
