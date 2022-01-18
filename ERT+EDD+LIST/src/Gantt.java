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

	int[] start;
	int[] proc;
	int[] reso;
	int[] jobid;
	Color[] colors;
	int NJOB;
	int NRES;

	public Gantt(int NJ, int NW, int end, Jobs[] job, Jobs[] job2, Jobs[] job3, Resources[] res, Resources[] res2,
			Resources[] res3, Scheduler[] s, Scheduler[] s2, Scheduler[] s3, double[] g, double[] g2, double[] g3,
			int[] Js, int[] Ws) {
		super("Gantt chart");
		colors = new Color[] { Color.RED, Color.BLUE, Color.BLACK, Color.GRAY, Color.ORANGE, Color.MAGENTA };
		NJOB = NJ;
		NRES = NW;
		int i = 0;
		int v = 70;
		if ((end * 2) > 500) {
			i = (end * 2) - 500;
		}
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(600 + i, 370);
		getContentPane().setLayout(null);
		setLocationRelativeTo(null);

		JLabel[] Resources = new JLabel[NW];
		for (int j = 0; j < NW; j++) {
			Resources[j] = new JLabel(j + 1 + ". Res");
			Resources[j].setHorizontalAlignment(SwingConstants.CENTER);
			Resources[j].setBounds(0, v, 37, 14);
			v += 40;
			getContentPane().add(Resources[j]);
		}

		JButton backButton = new JButton("Back");
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close();
				Progi p = new Progi();
				p.setVisible(true);
			}
		});
		backButton.setBounds(485, 297, 89, 23);
		getContentPane().add(backButton);

		int best = 0;
		Jobs[] jobs = new Jobs[NJ];
		Scheduler[] sch = new Scheduler[NJ];
		best = Eddlist.BestAlgorithm(g, g2, g3);
		switch (best) {
		case 1:
			Eddlist.ScheduleRes(Js, job, NJ, s);
			for (int k = 0; k < NJ; k++) {
				sch[k] = new Scheduler();
				sch[k] = s[k];
				jobs[k] = new Jobs();
				jobs[k] = job[k];
			}
			break;
		case 2:
			Eddlist.ScheduleRes(Js, job2, NJ, s2);
			for (int k = 0; k < NJ; k++) {
				sch[k] = new Scheduler();
				sch[k] = s2[k];
				jobs[k] = new Jobs();
				jobs[k] = job2[k];
			}
			break;
		case 3:
			Eddlist.ScheduleRes(Js, job3, NJ, s3);
			for (int k = 0; k < NJ; k++) {
				sch[k] = new Scheduler();
				sch[k] = s3[k];
				jobs[k] = new Jobs();
				jobs[k] = job3[k];
			}
			break;
		}

		start = new int[NJ];
		proc = new int[NJ];
		jobid = new int[NJ];
		reso = new int[NW];

		int need = 0;
		int need2 = 0;
		for (int j2 = 1; j2 < NW + 1; j2++) {
			need = Eddlist.CountNumbersAppers(j2, NJ, NW, sch);
			reso[(j2 - 1)] = need;
			if (j2 == 0) {
				for (int j = 0; j < need; j++) {
					if (j == 0) {
						start[j] = jobs[(sch[j].getJobID() - 1)].getStartT() * 2;
						proc[j] = jobs[(sch[j].getJobID() - 1)].getProcT() * 2;
						jobid[j] = jobs[(sch[j].getJobID() - 1)].getId();
					} else {
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
					} else {
						start[k] = jobs[(sch[k].getJobID() - 1)].getStartT() * 2;
						proc[k] = jobs[(sch[k].getJobID() - 1)].getProcT() * 2;
						jobid[k] = jobs[(sch[k].getJobID() - 1)].getId();
					}
				}
				need2 += need;
			}
		}

		setVisible(true);
	}

	public void close() {
		WindowEvent closeWindow = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
		Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(closeWindow);
	}

	public void paint(Graphics g) {
		super.paint(g);

		Graphics2D g2 = (Graphics2D) g;
		Line2D lin = new Line2D.Double(50, 80, 550, 80);
		g2.draw(lin);
		Line2D lin2 = new Line2D.Double(50, 80, 50, 320);
		g2.draw(lin2);
		Line2D lin3 = new Line2D.Double(50, 320, 550, 320);
		g2.draw(lin3);
		Line2D lin4 = new Line2D.Double(550, 80, 550, 320);
		g2.draw(lin4);
		int v = 70;
		int need = 0, need2 = 0, next = 0;
		int[] colorPick = new int[NRES];
		Random r = new Random();
		while (need < NRES) {
			need2 = r.nextInt(6) - 0;
			boolean repeat = false;
			do {
				for (int i = 0; i < NRES; i++) {
					if (need2 == colorPick[i]) {
						repeat = true;
						break;
					} else if (i == need) {
						colorPick[need] = need2;
						need++;
						repeat = true;
						break;
					}
				}
			} while (!repeat);
		}

		need = 0;
		need2 = 0;
		for (int i = 0; i < NRES; i++) {
			need = reso[i];
			if (i == 0) {
				v += 30;
				Color c = colors[colorPick[i]];
				for (int j = 0; j < need; j++) {
					if (j == 0) {
						next = 50 + start[j];
						g.setColor(Color.WHITE);
						g.drawRect(next, v, proc[j], 13);
						g.setColor(c);
						g.fillRect(next, v, proc[j], 13);
						g.drawString("" + start[j] / 2, next, v - 3);
						g.setColor(Color.WHITE);
						g.drawString(jobid[j] + ". job", next + 3, v + 10);
						next += 1 + proc[j];
					} else {
						g.setColor(Color.WHITE);
						g.drawRect(next, v, proc[j], 13);
						g.setColor(c);
						g.fillRect(next, v, proc[j], 13);
						g.drawString("" + start[j] / 2, next, v - 3);
						g.setColor(Color.WHITE);
						g.drawString(jobid[j] + ". job", next + 3, v + 10);
						g.setColor(c);
						if (j == (need - 1))
							g.drawString("" + (start[j] / 2 + proc[j] / 2), next + proc[j] - 6, v - 3);
						next += 1 + proc[j];
					}
				}
				need2 = need;
				v += 10;
			} else {
				v += 30;
				Color c = colors[colorPick[i]];
				for (int j = need2; j < (need + need2); j++) {
					if (j == need2) {
						next = 50 + start[j];
						g.setColor(Color.WHITE);
						g.drawRect(next, v, proc[j], 13);
						g.setColor(c);
						g.fillRect(next, v, proc[j], 13);
						g.drawString("" + start[j] / 2, next, v - 3);
						g.setColor(Color.WHITE);
						g.drawString(jobid[j] + ". job", next + 3, v + 10);
						next += 1 + proc[j];
					} else {
						g.setColor(Color.WHITE);
						g.drawRect(next, v, proc[j], 13);
						g.setColor(c);
						g.fillRect(next, v, proc[j], 13);
						g.drawString("" + start[j] / 2, next, v - 3);
						g.setColor(Color.WHITE);
						g.drawString(jobid[j] + ". job", next + 3, v + 10);
						g.setColor(c);
						if (j == ((need + need2) - 1))
							g.drawString("" + (start[j] / 2 + proc[j] / 2), next + proc[j] - 6, v - 3);
						next += 1 + proc[j];
					}
				}
				need2 += need;
				v += 10;
			}
		}

	}

	public Gantt() {
		super();
	}

	public static void main(String[] args) {
		Gantt gantt = new Gantt();
	}
}