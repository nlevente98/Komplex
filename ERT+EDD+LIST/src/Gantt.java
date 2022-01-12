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
	
	public Gantt() {
		super("Gantt chart");

		int i =50;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(550+i, 320+i);
		getContentPane().setLayout(null);
		setLocationRelativeTo(null);
		
		JLabel lblNewLabel = new JLabel("1.Res");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(0, 70, 40, 14);
		getContentPane().add(lblNewLabel);
		
		
		JLabel lbljob_1 = new JLabel("2.Res");
		lbljob_1.setHorizontalAlignment(SwingConstants.CENTER);
		lbljob_1.setBounds(0, 150, 40, 14);
		getContentPane().add(lbljob_1);
		
		
		JLabel lbljob_2 = new JLabel("3.Res");
		lbljob_2.setHorizontalAlignment(SwingConstants.CENTER);
		lbljob_2.setBounds(0, 230, 40, 14);
		getContentPane().add(lbljob_2);
		
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

	public static void main(String[] args) {
		Gantt gantt = new Gantt();
	}
}