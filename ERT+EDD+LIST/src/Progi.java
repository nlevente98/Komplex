import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JTextField;

public class Progi extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3052241424947888691L;
	JFileChooser openFileChooser;
	JComboBox comboBox;
	JTextArea textArea;
	JButton openJobButton;
	JLabel massageLabel;
	JLabel massageLabel_1;
	JScrollPane scrollPane;

	int isDone = 0;
	int end = 0;
	int NJ = 7;
	int NW = 3;
	double[] goalFunction = new double[2];
	double[] goalFunction2 = new double[2];
	double[] goalFunction3 = new double[2];

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
	private JTextField textField;
	private JButton btnNewButton_1;
	private JTextField textField_1;

	public Progi() {
		super("Progi");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(560, 390);
		getContentPane().setLayout(null);
		setLocationRelativeTo(null);

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

		openFileChooser = new JFileChooser();
		openFileChooser.setCurrentDirectory(new File("C:\\Users\\User\\Desktop"));
		openFileChooser.setFileFilter(new FileNameExtensionFilter("TXT files", "txt"));

		massageLabel = new JLabel("");
		massageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		massageLabel.setBounds(170, 11, 275, 23);
		getContentPane().add(massageLabel);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 79, 525, 225);
		getContentPane().add(scrollPane);

		textArea = new JTextArea();
		textArea.setFont(new Font("Arial", Font.PLAIN, 12));
		textArea.setTabSize(12);
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);

		openJobButton = new JButton("Jobs txt file...");
		openJobButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int returnValue = openFileChooser.showOpenDialog(openJobButton);

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

						Eddlist.ReadJobs(NJ, job, openFileChooser.getSelectedFile().toString());
						Eddlist.SaveJobs(NJ, job, job2);
						Eddlist.SaveJobs(NJ, job, job3);
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

		openJobButton.setBounds(10, 11, 150, 23);
		getContentPane().add(openJobButton);

		comboBox = new JComboBox(algorithm);
		comboBox.setBounds(455, 11, 80, 22);
		getContentPane().add(comboBox);

		JButton btnNewButton = new JButton("Press");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (massageLabel.getText() == "File succesfully read!"
						&& massageLabel_1.getText() == "File succesfully read!") {
					if (isDone == 0) {
						Eddlist.List(NJ, job, NW, res, s, Js, 1, Ws);
						Eddlist.Simulation_P(NW, job, res, Js);
						Eddlist.Evaluate(NJ, job, goalFunction);
						Eddlist.List(NJ, job2, NW, res2, s2, Js, 2, Ws);
						Eddlist.Simulation_P(NW, job2, res2, Js);
						Eddlist.Evaluate(NJ, job2, goalFunction2);
						Eddlist.List(NJ, job3, NW, res3, s3, Js, 3, Ws);
						Eddlist.Simulation_P(NW, job3, res3, Js);
						Eddlist.Evaluate(NJ, job3, goalFunction3);
					}

					if (comboBox.getSelectedIndex() == 0) {
						textArea.setText("No algorithm selected!");
						isDone++;
					} else if (comboBox.getSelectedIndex() == 1) {
						textArea.setText("");
						textArea.append(Eddlist.printGoals(goalFunction));
						textArea.append("\n\n");
						textArea.append(Eddlist.printJobs(job));
						textArea.append(Eddlist.printResources(res));
						isDone++;
					} else if (comboBox.getSelectedIndex() == 2) {
						textArea.setText("");
						textArea.append(Eddlist.printGoals(goalFunction2));
						textArea.append("\n\n");
						textArea.append(Eddlist.printJobs(job2));
						textArea.append(Eddlist.printResources(res2));
						isDone++;
					} else if (comboBox.getSelectedIndex() == 3) {
						textArea.setText("");
						textArea.append(Eddlist.printGoals(goalFunction3));
						textArea.append("\n\n");
						textArea.append(Eddlist.printJobs(job3));
						textArea.append(Eddlist.printResources(res3));
						isDone++;
					}
				} else {
					textArea.setText("Files not readed!");
				}
			}
		});
		btnNewButton.setBounds(455, 44, 80, 23);
		getContentPane().add(btnNewButton);

		JButton openGanttButton = new JButton("Open Gantt Chart");
		openGanttButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (massageLabel.getText() == "File succesfully read!"
						&& massageLabel_1.getText() == "File succesfully read!") {
					close();
					Gantt gantt = new Gantt(NJ, NW, end, job, job2, job3, res, res2, res3, s, s2, s3, goalFunction,
							goalFunction2, goalFunction3, Js, Ws);
					gantt.setVisible(true);
				}
				textArea.setText("Read inputs first!");
			}
		});
		openGanttButton.setBounds(10, 315, 150, 23);
		getContentPane().add(openGanttButton);

		massageLabel_1 = new JLabel("");
		massageLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		massageLabel_1.setBounds(170, 45, 275, 23);
		getContentPane().add(massageLabel_1);

		JButton openResButton = new JButton("Resources txt file...");
		openResButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int returnValue = openFileChooser.showOpenDialog(openResButton);

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

						Eddlist.ReadResources(NW, res, openFileChooser.getSelectedFile().toString());
						Eddlist.SaveWorkstations(NW, res, res2);
						Eddlist.SaveWorkstations(NW, res, res3);
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
					massageLabel_1.setText(msg);
					massageLabel_1.setForeground(Color.RED);
				} else {
					massageLabel_1.setText(msg);
					massageLabel_1.setForeground(Color.BLACK);
				}

			}
		});
		openResButton.setBounds(10, 45, 150, 23);
		getContentPane().add(openResButton);

		textField_1 = new JTextField();
		textField_1.setBounds(295, 315, 80, 23);
		getContentPane().add(textField_1);
		textField_1.setColumns(10);

		btnNewButton_1 = new JButton("Read number of jobs!");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (btnNewButton_1.getText() == "Read number of jobs!") {
					try {
						NJ = Integer.parseInt(textField_1.getText());
						textField_1.setText("");
						btnNewButton_1.setText("Read number of resources!");
						JOptionPane.showMessageDialog(null, "NJ: " + NJ);
					} catch (Exception ex) {
						ex.getMessage();
					}
				} else {
					try {
						NW = Integer.parseInt(textField_1.getText());
						textField_1.setText("");
						btnNewButton_1.setText("Now read inputs!");
						JOptionPane.showMessageDialog(null, "NR: " + NW);
					} catch (Exception ex) {
						ex.getMessage();
					}
				}
			}
		});
		btnNewButton_1.setBounds(385, 315, 150, 23);
		getContentPane().add(btnNewButton_1);

		setVisible(true);

	}

	public void close() {
		WindowEvent closeWindow = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
		Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(closeWindow);
	}

	public static void main(String[] args) {
		Progi p = new Progi();
	}
}