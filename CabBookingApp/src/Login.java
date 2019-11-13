import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import com.jgoodies.forms.factories.DefaultComponentFactory;
import java.awt.Font;
import java.awt.Image;

public class Login {
	private JFrame frame;
	private JPasswordField passwrd;
	private JTextField uid;
	private JLabel hyperlink;
	private Registration registrationPage;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
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
	public Login() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 774, 533);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblUsername = new JLabel("Password");
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblUsername.setBounds(220, 255, 99, 37);
		frame.getContentPane().add(lblUsername);
		
		JLabel lblUserid = new JLabel("UserID");
		lblUserid.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblUserid.setBounds(241, 205, 60, 37);
		frame.getContentPane().add(lblUserid);
		
		passwrd = new JPasswordField();
		passwrd.setBounds(330, 263, 214, 22);
		frame.getContentPane().add(passwrd);
		
		uid = new JTextField();
		uid.setBounds(330, 213, 214, 22);
		frame.getContentPane().add(uid);
		uid.setColumns(10);
		
		JButton btnLogin = new JButton("LOGIN");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String userid = uid.getText();
				String password = String.valueOf(passwrd.getPassword());
				if(userid.trim().isEmpty() || password.trim().isEmpty())
					JOptionPane.showMessageDialog(frame, "Please fill all the fields");
				else if(User.checkCredentials(userid, password)) {
						new BookingPortal(userid,password);
						EventQueue.invokeLater(new Runnable() {
							public void run() {
								try {
									BookingPortal window = new BookingPortal();
									window.frame.setVisible(true);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});
					}
				else {
					JOptionPane.showMessageDialog(frame,"Invalid Userid or Password");
				}
			}
		});
		btnLogin.setBounds(308, 346, 97, 25);
		frame.getContentPane().add(btnLogin);
		
		
		hyperlink = new JLabel("New User? Register here");
		hyperlink.setFont(new Font("Tahoma", Font.PLAIN, 16));
		hyperlink.setBounds(268, 396, 195, 32);
		frame.getContentPane().add(hyperlink);
		
		hyperlink.setForeground(Color.BLUE.darker());
	    hyperlink.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
	  
	    registrationPage = new Registration();
	    hyperlink.addMouseListener(new MouseAdapter() {
	 
	            @Override
	            public void mouseClicked(MouseEvent e) {
	                registrationPage.setVisible(true);
	            }
	 
	            @Override
	            public void mouseExited(MouseEvent e) {
	                hyperlink.setText("New User? Register here");
	            }
	 
	            @Override
	            public void mouseEntered(MouseEvent e) {
	                hyperlink.setText("<html><a href=''>" + "New User? Register here" + "</a></html>");
	            }
	    });
		
		JLabel lblWelcomeToUber = DefaultComponentFactory.getInstance().createTitle("Welcome to MARS Cabs");
		lblWelcomeToUber.setFont(new Font("Tahoma", Font.BOLD, 36));
		lblWelcomeToUber.setBounds(165, 40, 439, 59);
		frame.getContentPane().add(lblWelcomeToUber);
		
		JLabel label_1 = new JLabel("Sign In");
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 22));
		label_1.setBounds(321, 132, 68, 25);
		frame.getContentPane().add(label_1);
		
		JLabel label = new JLabel("");
		label.setBounds(165, 205, 43, 37);
		frame.getContentPane().add(label);
		Image img1 = new ImageIcon(this.getClass().getResource("/user.png")).getImage();
		label.setIcon(new ImageIcon(img1));
		
		JLabel label_2 = new JLabel("");
		label_2.setBounds(165, 255, 43, 37);
		frame.getContentPane().add(label_2);
		Image img2 = new ImageIcon(this.getClass().getResource("/password2.png")).getImage();
		label_2.setIcon(new ImageIcon(img2));
	}
}
