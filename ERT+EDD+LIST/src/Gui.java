
import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import java.awt.Font;
import java.awt.Toolkit;

public class Gui {

	/*private JFrame frame;
	private final JFileChooser openFileChooser;
	JComboBox comboBox;
	JTextArea textArea;
	JButton openFileButton;
	JLabel massageLabel;

	int end = 0;
	int NJ = 6;
	int NW = 3;
	double[] goalFunction = new double[2];

	int[] Js = new int[NJ];
	int[] Ws = new int[NW];
	Scheduler[] s = new Scheduler[NJ];
	Scheduler[] s2 = new Scheduler[NJ];
	Scheduler[] s3 = new Scheduler[NJ];
	Jobs[] job = new Jobs[NJ];
	Jobs[] job2 = new Jobs[NJ];
	Jobs[] job3 = new Jobs[NJ];
	Resources[] res = new Resources[NW];
	Resources[] res2 = new Resources[NW];
	Resources[] res3 = new Resources[NW];
	String[] algorithm = { "", "EDD+List", "SPT+List", "LPT+List" };
	private JScrollPane scrollPane;

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gui window = new Gui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public Gui() throws IOException {
		initialize();
		openFileChooser = new JFileChooser();
		openFileChooser.setCurrentDirectory(new File("C:\\Users\\User\\Desktop"));
		openFileChooser.setFileFilter(new FileNameExtensionFilter("TXT files", "txt"));
	}
	
	
	public void close() {
		WindowEvent closeWindow = new WindowEvent(frame, WindowEvent.WINDOW_CLOSING);
		Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(closeWindow);
	}
	
	
	private void initialize() throws IOException {

		for (int i = 0; i < NJ; i++) {
			s[i] = new Scheduler();
			s2[i] = new Scheduler();
			s3[i] = new Scheduler();
			job[i] = new Jobs();
			job2[i] = new Jobs();
			job3[i] = new Jobs();
		}

		for (int i = 0; i < NW; i++) {
			res[i] = new Resources();
			res2[i] = new Resources();
			res3[i] = new Resources();
			res[i].setId(i + 1);
			res[i].setL(200);
		}

		frame = new JFrame();
		frame.setBounds(100, 100, 660, 440);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		massageLabel = new JLabel("");
		massageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		massageLabel.setBounds(140, 11, 300, 23);
		frame.getContentPane().add(massageLabel);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 45, 445, 225);
		frame.getContentPane().add(scrollPane);

		textArea = new JTextArea();
		textArea.setFont(new Font("Arial", Font.PLAIN, 16));
		textArea.setTabSize(12);
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);

		openFileButton = new JButton("Open txt file...");
		openFileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int returnValue = openFileChooser.showOpenDialog(openFileButton);

				if (returnValue == JFileChooser.APPROVE_OPTION) {
					try {
						File inputFile = openFileChooser.getSelectedFile();
						FileReader inputReader = new FileReader(inputFile);
						BufferedReader inputBR = new BufferedReader(inputReader);

						String line = inputBR.readLine();

						textArea.setText("");
						while (line != null) {

							textArea.append(line + "\n");
							line = inputBR.readLine();

						}

						eddlist.ReadJobs(NJ, job);
						eddlist.ReadResources(NW, res);
						eddlist.SaveJobs(NJ, job, job2);
						eddlist.SaveWorkstations(NW, res, res2);
						eddlist.SaveJobs(NJ, job, job3);
						eddlist.SaveWorkstations(NW, res, res3);
						inputBR.close();
						inputReader.close();

						massage("File succesfully read!", false);

					} catch (IOException ex) {
						massage("Error reading file!", true);
					}
				} else {
					massage("No file chosen!", true);
				}

			}

			private void massage(String msg, boolean isError) {
				if (isError) {
					massageLabel.setText(msg);
					massageLabel.setForeground(Color.RED);
				} else {
					massageLabel.setText(msg);
					massageLabel.setForeground(Color.BLACK);
				}

			}

		});

		openFileButton.setBounds(10, 11, 115, 23);
		frame.getContentPane().add(openFileButton);

		comboBox = new JComboBox(algorithm);
		comboBox.setBounds(455, 11, 80, 22);
		frame.getContentPane().add(comboBox);

		JButton btnNewButton = new JButton("Press");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				eddlist.List(NJ, job, NW, res, s, Js, 1, Ws);
				eddlist.Simulation_P(NW, job, res, Js);
				eddlist.Evaluate(NJ, job, goalFunction);
				eddlist.List(NJ, job2, NW, res2, s, Js, 2, Ws);
				eddlist.Simulation_P(NW, job2, res2, Js);
				eddlist.Evaluate(NJ, job2, goalFunction);
				eddlist.List(NJ, job3, NW, res3, s, Js, 3, Ws);
				eddlist.Simulation_P(NW, job3, res3, Js);
				eddlist.Evaluate(NJ, job3, goalFunction);
				if (comboBox.getSelectedIndex() == 1) {
					textArea.setText("");
					textArea.append(eddlist.printGoals(goalFunction));
				} else if (comboBox.getSelectedIndex() == 2) {
					textArea.setText("");
					textArea.append(eddlist.printGoals(goalFunction));
				} else if (comboBox.getSelectedIndex() == 3) {
					textArea.setText("");
					textArea.append(eddlist.printGoals(goalFunction));
				}
			}
		});
		btnNewButton.setBounds(550, 11, 89, 23);
		frame.getContentPane().add(btnNewButton);
		
		JButton openGanttButton = new JButton("Open Gantt Chart");
		openGanttButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close();
				Gantt gantt = new Gantt();
				gantt.setVisible(true);
			}
		});
		openGanttButton.setBounds(10, 281, 150, 23);
		frame.getContentPane().add(openGanttButton);
		
		end = eddlist.SaveLastEndTime(NJ, Js, job);
		

	}*/
}
