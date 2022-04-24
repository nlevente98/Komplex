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

public class GUI extends JFrame {

	private static final long serialVersionUID = 3052241424947888691L;
	JFileChooser openFileChooser;
	JComboBox comboBox;
	JTextArea textArea;
	JButton openJobButton;
	JLabel massageLabel;
	JLabel massageLabel_1;
	JScrollPane scrollPane;

	int isDone = 0, end = 0, NJ = 1000, NW = 300, NG = 5, algoritm = 0;
	Gantt gantt;
	Goals[] goals;
	int[] Js, Ws, types;
	Scheduler[] s;
	Jobs[] job, job2, job3, job4, job5;
	Resources[] res, res2, res3, res4, res5;
	String[] algorithm = { "", "EDD+List", "SPT+List", "LPT+List", "ERD+List", "RList" };
	private JButton btnNewButton_1;
	private JTextField textField_1;

	public GUI() {
		super("JIT Scheduler by Nagy Levente");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setSize(560, 390);
		getContentPane().setLayout(null);
		setLocationRelativeTo(null);

		goals = new Goals[NG];
		for (int i = 0; i < NG; i++) {
			goals[i] = new Goals();
		}
		goals[0].setName("EDD+List");
		goals[1].setName("SPT+List");
		goals[2].setName("LPT+List");
		goals[3].setName("ERD+ List");
		goals[4].setName("RList");

		textField_1 = new JTextField();
		textField_1.setBounds(205, 315, 40, 23);
		getContentPane().add(textField_1);
		textField_1.setColumns(10);

		btnNewButton_1 = new JButton("Read number of jobs!");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (btnNewButton_1.getText() == "Read number of jobs!") {
					try {
						NJ = Integer.parseInt(textField_1.getText());
						Js = new int[NJ];
						s = new Scheduler[NJ];
						job = new Jobs[NJ];
						job2 = new Jobs[NJ];
						job3 = new Jobs[NJ];
						job4 = new Jobs[NJ];
						job5 = new Jobs[NJ];

						for (int i = 0; i < NJ; i++) {
							s[i] = new Scheduler();
							job[i] = new Jobs();
						}

						textField_1.setText("");
						btnNewButton_1.setText("Read number of resources!");
						JOptionPane.showMessageDialog(null, "Number of jobs: " + NJ);
					} catch (Exception ex) {
						ex.getMessage();
						JOptionPane.showMessageDialog(null, "Please give an integer to number of jobs field!");
					}
				} else if (btnNewButton_1.getText() == "Read number of resources!") {
					try {
						NW = Integer.parseInt(textField_1.getText());
						Ws = new int[NW];
						res = new Resources[NW];
						res2 = new Resources[NW];
						res3 = new Resources[NW];
						res4 = new Resources[NW];
						res5 = new Resources[NW];

						for (int i = 0; i < NW; i++) {
							res[i] = new Resources();
							res[i].setId(i + 1);
							res[i].setL(0);
							res[i].setNumber(1);
						}

						textField_1.setText("");
						btnNewButton_1.setText("Reset");
						JOptionPane.showMessageDialog(null, "Number of resources: " + NW);
					} catch (Exception ex) {
						ex.getMessage();
						JOptionPane.showMessageDialog(null, "Please give an integer to number of resources field!");
					}
				} else if (btnNewButton_1.getText() == "Reset") {
					textField_1.setText("");
					btnNewButton_1.setText("Read number of jobs!");
					JOptionPane.showMessageDialog(null, "Please type again the number of jobs and resources!");
				} else {
					JOptionPane.showMessageDialog(null, "Read inputs please!");
				}
			}
		});
		btnNewButton_1.setBounds(255, 315, 190, 23);
		getContentPane().add(btnNewButton_1);

		openFileChooser = new JFileChooser();
		openFileChooser.setCurrentDirectory(new File("C:\\Users\\Leventeke\\Desktop"));
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

						Runable.ReadJobs(NJ, job, openFileChooser.getSelectedFile().toString());
						Runable.SaveJobs(NJ, job, job2);
						Runable.SaveJobs(NJ, job, job3);
						Runable.SaveJobs(NJ, job, job4);
						Runable.SaveJobs(NJ, job, job5);
						types = Runable.ListTypes(job, NJ);
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

		JButton btnNewButton = new JButton("Select");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (massageLabel.getText() == "File succesfully read!"
						&& massageLabel_1.getText() == "File succesfully read!") {
					if (isDone == 0) {
						Runable.EDDList(NJ, job, NW, res, s, Js, Ws, 1, goals, types);
						Runable.SPTList(NJ, job2, NW, res2, s, Js, Ws, 2, goals, types);
						Runable.LPTList(NJ, job3, NW, res3, s, Js, Ws, 3, goals, types);
						Runable.ERDList(NJ, job4, NW, res4, s, Js, Ws, 4, goals, types);
						Runable.RList(NJ, job5, NW, res5, s, Js, Ws, 5, goals, types);
					}
					if (comboBox.getSelectedIndex() == 0) {
						textArea.append("\nPlease select another menu to open Gantt charts!");
						isDone++;
					} else if (comboBox.getSelectedIndex() == 1) {
						textArea.setText("");
						textArea.append(Runable.printGoals(goals, 1));
						textArea.append("\n\n");
						textArea.append(Runable.printJobs(job));
						textArea.append(Runable.printResources(res));
						isDone++;
					} else if (comboBox.getSelectedIndex() == 2) {
						textArea.setText("");
						textArea.append(Runable.printGoals(goals, 2));
						textArea.append("\n\n");
						textArea.append(Runable.printJobs(job2));
						textArea.append(Runable.printResources(res2));
						isDone++;
					} else if (comboBox.getSelectedIndex() == 3) {
						textArea.setText("");
						textArea.append(Runable.printGoals(goals, 3));
						textArea.append("\n\n");
						textArea.append(Runable.printJobs(job3));
						textArea.append(Runable.printResources(res3));
						isDone++;
					} else if (comboBox.getSelectedIndex() == 4) {
						textArea.setText("");
						textArea.append(Runable.printGoals(goals, 4));
						textArea.append("\n\n");
						textArea.append(Runable.printJobs(job4));
						textArea.append(Runable.printResources(res4));
						isDone++;
					} else if (comboBox.getSelectedIndex() == 5) {
						textArea.setText("");
						textArea.append(Runable.printGoals(goals, 5));
						textArea.append("\n\n");
						textArea.append(Runable.printJobs(job5));
						textArea.append(Runable.printResources(res5));
						isDone++;
					}
				} else {
					JOptionPane.showMessageDialog(null,
							"Files are not readed yet! Please try again after read txt files!");
				}
			}
		});
		btnNewButton.setBounds(455, 44, 80, 23);
		getContentPane().add(btnNewButton);

		JButton openGanttButton = new JButton("Open Gantt Chart");
		openGanttButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				end = Runable.SaveLastEndTime(NJ, Js, job);
				int num = comboBox.getSelectedIndex();
				if (massageLabel.getText() == "File succesfully read!"
						&& massageLabel_1.getText() == "File succesfully read!") {
					close();
					switch (num) {
					case 0:
						JOptionPane.showMessageDialog(null, "Please select another menu to open Gantt charts!");
						break;
					case 1:
						gantt = new Gantt(NJ, NW, NG, end, job, s, Js, Ws, goals[0].getName());
						gantt.setVisible(true);
						break;
					case 2:
						gantt = new Gantt(NJ, NW, NG, end, job2, s, Js, Ws, goals[1].getName());
						gantt.setVisible(true);
						break;
					case 3:
						gantt = new Gantt(NJ, NW, NG, end, job3, s, Js, Ws, goals[2].getName());
						gantt.setVisible(true);
						break;
					case 4:
						gantt = new Gantt(NJ, NW, NG, end, job4, s, Js, Ws, goals[3].getName());
						gantt.setVisible(true);
						break;
					case 5:
						gantt = new Gantt(NJ, NW, NG, end, job5, s, Js, Ws, goals[4].getName());
						gantt.setVisible(true);
						break;
					}
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

						Runable.ReadResources(NW, res, openFileChooser.getSelectedFile().toString());
						Runable.SaveResources(NW, res, res2);
						Runable.SaveResources(NW, res, res3);
						Runable.SaveResources(NW, res, res4);
						Runable.SaveResources(NW, res, res5);
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

		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				JOptionPane.showMessageDialog(null, "App closed!");
			}
		});
		btnClose.setBounds(455, 315, 80, 23);
		getContentPane().add(btnClose);

		setVisible(true);

	}

	public void close() {
		WindowEvent closeWindow = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
		Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(closeWindow);
	}

	public static void main(String[] args) {
		GUI g = new GUI();
	}
}