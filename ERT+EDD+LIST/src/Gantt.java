import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.geom.Line2D;
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

	public Gantt(int NJ, int NW, int end, Jobs[] job, Jobs[] job2, Jobs[] job3, Resources[] res, Resources[] res2,
			Resources[] res3, Scheduler[] s, Scheduler[] s2, Scheduler[] s3, double[] g, double[] g2, double[] g3,
			int[] Js, int[] Ws) {
		super("Gantt chart");
		int i = 0;

		if (end > 500) {
			i = end - 500;
		}
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(600 + i, 370);
		getContentPane().setLayout(null);
		setLocationRelativeTo(null);

		JLabel[] Resources = new JLabel[NW];
		int v = 70;
		for (int j = 0; j < NW; j++) {
			Resources[j] = new JLabel(j + 1 + ". Res");
			Resources[j].setHorizontalAlignment(SwingConstants.CENTER);
			Resources[j].setBounds(0, v, 40, 14);
			v += 40;
			getContentPane().add(Resources[j]);
		}

		JLabel lblNewLabel_1 = new JLabel("Resources/Time");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(0, 30, 100, 14);
		getContentPane().add(lblNewLabel_1);

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
		
		ScheduleRes(Js, job2, NJ, s2);

		int id = 0;
		start = new int[NJ];
		proc = new int[NJ];
		for (int j = 0; j < NJ; j++) {
			id = s2[j].getJobID();
			if (s2[j].getWorkstationID() == 1) {
				if (id == job[1].getId()) {
					start[0] = job[1].getStartT();
					proc[0] = job[1].getProcT() * 2;
				} else {
					start[1] = job[4].getStartT() + (proc[0] / 2);
					proc[1] = job[4].getProcT() * 2;
				}
			}
			if (s2[j].getWorkstationID() == 2) {
				if (id == 1) {
					start[2] = job[0].getStartT();
					proc[2] = job[0].getProcT() * 2;
				} else {
					start[3] = job[2].getStartT() + (proc[2] / 2);
					proc[3] = job[2].getProcT() * 2;
				}
			}
			if (s2[j].getWorkstationID() == 3) {
				if (id == 6) {
					start[4] = job[5].getStartT();
					proc[4] = job[5].getProcT() * 2;
				} else {
					start[5] = job[3].getStartT() + (proc[4] / 2);
					proc[5] = job[3].getProcT() * 2;
				}
			}
		}

		setVisible(true);
	}

	public void close() {
		WindowEvent closeWindow = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
		Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(closeWindow);
	}
	
	public void ScheduleRes(int[] s, Jobs[] job, int NJ, Scheduler[] s2) {
		for (int i = 0; i < (NJ-1); i++) {
			int index = i;
			for (int j = (i+1); j < NJ; j++) {
				if(s2[index].getWorkstationID() > s2[j].getWorkstationID())
					index = j;
			}
			if(index != i) {
				int temp = s[index];
				s[index] = s[i];
				s[i] = temp;
			}
		}
		for (int i = 0; i < (NJ-1); i++) {
			int index = i;
			for (int j = (i+1); j < NJ; j++) {
				if(s2[index].getWorkstationID() == s2[j].getWorkstationID() && job[s[index]].getStartT() < job[s[j]].getStartT())
					index = j;
			}
			if(index != i) {
				int temp = s[index];
				s[index] = s[i];
				s[i] = temp;
			}
		}
		for (int i = 0; i < NJ; i++) {
			System.out.println(""+(s[i]+1));
		}
		
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

		g.setColor(Color.WHITE);
		g.drawRect(50 + start[0], v + 33, proc[0], 10);
		g.setColor(Color.RED);
		g.fillRect(50 + start[0], v + 33, proc[0], 10);
		g.drawString("" + start[0], 50 + start[0], v + 30);
		g.setColor(Color.WHITE);
		g.drawRect(51 + start[1], v + 33, proc[1], 10);
		g.setColor(Color.RED);
		g.fillRect(51 + start[1], v + 33, proc[1], 10);
		g.drawString("" + start[1], 51 + start[1], v + 30);
		v += 40;

		g.setColor(Color.WHITE);
		g.drawRect(50 + start[2], v + 33, proc[2], 10);
		g.setColor(Color.GREEN);
		g.fillRect(50 + start[2], v + 33, proc[2], 10);
		g.setColor(Color.WHITE);
		g.drawRect(51 + start[3], v + 33, proc[3], 10);
		g.setColor(Color.GREEN);
		g.fillRect(51 + start[3], v + 33, proc[3], 10);
		v += 40;

		g.setColor(Color.WHITE);
		g.drawRect(50 + start[4], v + 33, proc[4], 10);
		g.setColor(Color.BLUE);
		g.fillRect(50 + start[4], v + 33, proc[4], 10);
		g.setColor(Color.WHITE);
		g.drawRect(51 + start[5], v + 33, proc[5], 10);
		g.setColor(Color.BLUE);
		g.fillRect(51 + start[5], v + 33, proc[5], 10);

	}

	public Gantt() {
		super();
	}

	public static void main(String[] args) {
		Gantt gantt = new Gantt();
	}
}