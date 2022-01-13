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
			Resources[j] = new JLabel(j+1 + ". Res");
			Resources[j].setHorizontalAlignment(SwingConstants.CENTER);
			Resources[j].setBounds(0, v, 40, 14);
			v += 80;
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

		g.setColor(Color.WHITE);
		g.drawRect(53, 103, 20, 10);
		g.setColor(Color.RED);
		g.fillRect(53, 103, 20, 10);
		g.setColor(Color.WHITE);
		g.drawRect(74, 103, 153, 10);
		g.setColor(Color.RED);
		g.fillRect(74, 103, 153, 10);

		g.setColor(Color.WHITE);
		g.drawRect(57, 183, 60, 10);
		g.setColor(Color.GREEN);
		g.fillRect(57, 183, 60, 10);
		g.setColor(Color.WHITE);
		g.drawRect(118, 183, 163, 10);
		g.setColor(Color.GREEN);
		g.fillRect(118, 183, 163, 10);

		g.setColor(Color.WHITE);
		g.drawRect(55, 263, 56, 10);
		g.setColor(Color.BLUE);
		g.fillRect(55, 263, 56, 10);
		g.setColor(Color.WHITE);
		g.drawRect(112, 263, 149, 10);
		g.setColor(Color.BLUE);
		g.fillRect(112, 263, 149, 10);

	}

	public Gantt() {
		super();
	}

	public static void main(String[] args) {
		Gantt gantt = new Gantt();
	}
}