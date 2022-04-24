import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.geom.Line2D;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Gantt2 extends JFrame {

	private static final long serialVersionUID = 6473734425115595325L;
	int[] start, starts, proc, duedate, jobid;
	Color[] colors;
	int ended, vPlus, NJOB;
	String algoritm;

	public Gantt2(int NJ, Jobs[] job, int end, int[] s, String goalname) {
		super("Job-oriented Gantt chart");

		int v = 70;
		NJOB = NJ;
		algoritm = goalname;
		if ((end * 2) > 350) {
			ended = (end * 2) - 350;
		}
		if (NJOB > 6)
			vPlus = (NJOB - 6) * 40;

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(600 + ended, 370 + vPlus);
		getContentPane().setLayout(null);
		setLocationRelativeTo(null);

		colors = new Color[] { Color.BLACK, Color.BLUE, Color.RED };
		start = new int[NJOB];
		starts = new int[NJOB];
		proc = new int[NJOB];
		jobid = new int[NJOB];
		duedate = new int[NJOB];

		JLabel[] Jobs = new JLabel[NJOB];
		for (int i = 0; i < NJOB; i++) {
			starts[i] = s[i];
			Jobs[i] = new JLabel((i + 1) + ". Job");
			Jobs[i].setHorizontalAlignment(SwingConstants.CENTER);
			Jobs[i].setBounds(0, v, 37, 14);
			v += 40;
			getContentPane().add(Jobs[i]);
			start[i] = job[i].getStartT() * 2;
			proc[i] = job[i].getProcT() * 2;
			duedate[i] = job[i].getD() * 2;
			jobid[i] = job[i].getId();
		}

		JButton backButton = new JButton("Back");
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close();
			}
		});
		backButton.setBounds(465 + ended, 297 + vPlus, 80, 23);
		getContentPane().add(backButton);
		setVisible(true);

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
		int next = 0;
		boolean even = false;

		g.drawString("Algoritm: " + algoritm, 50, v + -10);

		for (int i = 0; i < NJOB; i++) {
			v += 30;
			next = 50 + start[i];
			if (even == true) {
				g.setColor(colors[2]);
				even = false;
			} else {
				g.setColor(colors[1]);
				even = true;
			}
			g.fillRect(next, v, proc[i], 13);
			if (start[i] <= 20)
				g.drawString("" + start[i] / 2, next, v - 3);
			else
				g.drawString("" + start[i] / 2, next - 22, v + 10);
			g.drawString("" + (start[i] / 2 + proc[i] / 2), next + proc[i] + 12, v + 10);
			g.setColor(colors[0]);
			g.drawRect(next, v, proc[i], 13);
			next = 50 + starts[i];
			g.drawRect(next, v, (duedate[i] - starts[i]), 13);
			g.drawString("" + duedate[i] / 2, (next + duedate[i] - starts[i] - 6), v - 3);
			v += 10;
		}

	}

	public void close() {
		WindowEvent closeWindow = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
		Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(closeWindow);
	}

	public Gantt2() {
		super();
		getContentPane().setLayout(null);
	}

	public static void main(String[] args) {
		Gantt2 gantt = new Gantt2();
	}
}
