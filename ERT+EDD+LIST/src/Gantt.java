import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.geom.Line2D;
import java.util.Random;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;

public class Gantt extends JFrame {

	private static final long serialVersionUID = 3935369734081452145L;
	int[] start, startedd,proc, reso, jobid;
	Color[] colors;
	int NJOB, NRES, ended, vPlus;
	Scheduler[] sch;
	Jobs[] jobs;
	String algoritm;

	public Gantt(int NJ, int NW, int NG, int end, Jobs[] job, Scheduler[] s, int[] Js, int[] Ws, String goalname) {
		super("Resource-oriented Gantt chart");
		
		colors = new Color[] { Color.RED, Color.BLUE };
		NJOB = NJ;
		NRES = NW;
		algoritm = goalname;
		int v = 70;
		if ((end * 2) > 400) {
			ended = (end * 2) - 400;
		}
		if (NRES > 6)
			vPlus = (NRES - 6) * 40;

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(600 + ended, 370 + vPlus);
		getContentPane().setLayout(null);
		setLocationRelativeTo(null);

		JButton backButton = new JButton("Back");
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close();
			}
		});
		backButton.setBounds(465 + ended, 297 + vPlus, 80, 23);
		getContentPane().add(backButton);

		startedd = new int[NJ];
		jobs = new Jobs[NJ];
		sch = new Scheduler[NJ];

		for (int j = 0; j < NJ; j++) {
			jobs[j] = job[j];
			sch[j] = s[j];
			startedd[j] = (job[j].getStartT() * 2);
		}
		sch = Runable.ScheduleDoneJobs(NJ, sch);
		Runable.ScheduleRes(Js, jobs, NJ, sch);

		start = new int[NJ];
		proc = new int[NJ];
		jobid = new int[NJ];
		reso = new int[NW];

		int need = 0;
		int need2 = 0;
		JLabel[] Resources = new JLabel[NW];
		for (int j2 = 1; j2 < NW + 1; j2++) {
			need = Runable.CountNumbersAppers(j2, sch);
			if (need != 0) {
				reso[j2 - 1] = need;
				Resources[j2 - 1] = new JLabel(j2 + ". Res");
				Resources[j2 - 1].setHorizontalAlignment(SwingConstants.CENTER);
				Resources[j2 - 1].setBounds(0, v, 37, 14);
				v += 40;
				getContentPane().add(Resources[j2 - 1]);
				if (j2 == 0) {
					for (int j = 0; j < need; j++) {
						if (j == 0) {
							start[j] = jobs[(sch[j].getJobID() - 1)].getStartT() * 2;
							proc[j] = jobs[(sch[j].getJobID() - 1)].getProcT() * 2;
							jobid[j] = jobs[(sch[j].getJobID() - 1)].getId();
						} else if (sch[j].getResourceID() != 0) {
							start[j] = jobs[(sch[j].getJobID() - 1)].getStartT() * 2;
							proc[j] = jobs[(sch[j].getJobID() - 1)].getProcT() * 2;
							jobid[j] = jobs[(sch[j].getJobID() - 1)].getId();
						}
					}
					need2 += need;
				} else {
					for (int k = need2; k < (need + need2); k++) {
						if (k == need2) {
							start[k] = jobs[(sch[k].getJobID() - 1)].getStartT() * 2;
							proc[k] = jobs[(sch[k].getJobID() - 1)].getProcT() * 2;
							jobid[k] = jobs[(sch[k].getJobID() - 1)].getId();
						} else if (sch[k].getResourceID() != 0) {
							start[k] = jobs[(sch[k].getJobID() - 1)].getStartT() * 2;
							proc[k] = jobs[(sch[k].getJobID() - 1)].getProcT() * 2;
							jobid[k] = jobs[(sch[k].getJobID() - 1)].getId();
						}
					}
					need2 += need;
				}
			}
		}

		JButton openGanttButton = new JButton("Jobs chart");
		openGanttButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close();
				Gantt2 gantt = new Gantt2(NJ, jobs, end, startedd, algoritm);
				gantt.setVisible(true);
			}
		});
		openGanttButton.setBounds(465 + ended, 10, 80, 23);
		getContentPane().add(openGanttButton);

		setVisible(true);
	}

	public void close() {
		WindowEvent closeWindow = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
		Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(closeWindow);
	}

	public void paint(Graphics g) {
		super.paint(g);

		Graphics2D g2 = (Graphics2D) g;
		Line2D lin = new Line2D.Double(50, 80, 550 + ended, 80);
		g2.draw(lin);
		Line2D lin2 = new Line2D.Double(50, 80, 50, 320 + vPlus);
		g2.draw(lin2);
		Line2D lin3 = new Line2D.Double(50, 320 + vPlus, 550 + ended, 320 + vPlus);
		g2.draw(lin3);
		Line2D lin4 = new Line2D.Double(550 + ended, 80, 550 + ended, 320 + vPlus);
		g2.draw(lin4);
		int v = 70;
		int need = 0, need2 = 0, next = 0;
		int increase = 0;

		g.drawString("Algoritm: " + algoritm, 50, v + -10);

		for (int i = 0; i < NRES; i++) {
			need = reso[i];
			if (reso[i] != 0) {
				if (i == 0) {
					v += 30;
					for (int j = 0; j < need; j++) {
						if (j == 0 && start[j] != 0) {
							next = 50 + start[j];
							g.setColor(Color.WHITE);
							g.drawRect(next, v, proc[j], 13);
							shuffleColors(colors.length, colors);
							g.setColor(colors[j]);
							g.fillRect(next, v, proc[j], 13);
							g.setColor(Color.BLACK);
							g.drawString("" + start[j] / 2, next, v - 3);
							if (j == (need - 1))
								g.drawString("" + (start[j] / 2 + proc[j] / 2), next + proc[j] - 6, v - 3);
							g.drawString(jobid[j] + ". job", next + 3, v + 10);
							next += 1 + proc[j];
						} else if (start[j] != 0) {
							g.setColor(Color.WHITE);
							g.drawRect(next, v, proc[j], 13);
							increase = j / colors.length;
							if (j >= colors.length) {
								g.setColor(colors[j - colors.length * increase]);
							} else
								g.setColor(colors[j]);
							g.fillRect(next, v, proc[j], 13);
							g.setColor(Color.BLACK);
							if (j % 2 == 0) {
								g.drawString("" + start[j] / 2, next, v - 3);
								if (j == ((need + need2) - 1))
									g.drawString("" + (start[j] / 2 + proc[j] / 2), next + proc[j] - 6, v + 25);
							} else {
								g.drawString("" + start[j] / 2, next, v + 25);
								if (j == ((need + need2) - 1))
									g.drawString("" + (start[j] / 2 + proc[j] / 2), next + proc[j] - 6, v - 3);
							}
							g.drawString(jobid[j] + ". job", next + 3, v + 10);
							next += 1 + proc[j];
						}
					}
					need2 = need;
					v += 10;
				} else {
					v += 30;
					for (int j = need2; j < (need + need2); j++) {
						if (j == need2 && start[j] != 0) {
							next = 50 + start[j];
							g.setColor(Color.WHITE);
							g.drawRect(next, v, proc[j], 13);
							increase = j / colors.length;
							if (j >= colors.length) {
								g.setColor(colors[j - colors.length * increase]);
							} else
								g.setColor(colors[j]);
							g.fillRect(next, v, proc[j], 13);
							g.setColor(Color.BLACK);
							g.drawString("" + start[j] / 2, next, v - 3);
							if (j == ((need + need2) - 1))
								g.drawString("" + (start[j] / 2 + proc[j] / 2), next + proc[j] - 6, v - 3);
							g.drawString(jobid[j] + ". job", next + 3, v + 10);
							next += 1 + proc[j];
						} else if (start[j] != 0) {
							g.setColor(Color.WHITE);
							g.drawRect(next, v, proc[j], 13);
							increase = j / colors.length;
							if (j >= colors.length)
								g.setColor(colors[j - increase * colors.length]);
							else
								g.setColor(colors[j]);
							g.fillRect(next, v, proc[j], 13);
							g.setColor(Color.BLACK);
							if (j % 2 == 0) {
								g.drawString("" + start[j] / 2, next, v - 3);
								if (j == ((need + need2) - 1))
									g.drawString("" + (start[j] / 2 + proc[j] / 2), next + proc[j] - 6, v + 25);
							} else {
								g.drawString("" + start[j] / 2, next, v + 25);
								if (j == ((need + need2) - 1))
									g.drawString("" + (start[j] / 2 + proc[j] / 2), next + proc[j] - 6, v - 3);
							}
							g.drawString(jobid[j] + ". job", next + 3, v + 10);
							next += 1 + proc[j];
						}
					}
					need2 += need;
					v += 10;
				}
			}
		}

	}

	public void shuffleColors(int a, Color[] colors) {
		Random r = new Random();
		for (int i = a - 1; i > 0; i--) {
			int j = r.nextInt(i + 1);
			Color temp = colors[i];
			colors[i] = colors[j];
			colors[j] = temp;
		}
	}

	public Gantt() {
		super();
		getContentPane().setLayout(null);
	}

	public static void main(String[] args) {
		Gantt gantt = new Gantt();
	}
}