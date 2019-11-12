import java.awt.EventQueue;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class BookingPortal {
	public JFrame frame;
	
	static User u1 = new User();
	String from,to;
	int x1,y1,x2,y2;
	double distance;
	int time;
	static Driver d = new Driver();

	/**
	 * Launch the application.
	 */
	
	public BookingPortal(String id, String passwd){
		u1 = User.getUser(id, passwd);
	}
	
	public static void main(String[] args) {
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

	/**
	 * Create the application.
	 */
	public BookingPortal() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		String s[]= SqlConnector.DBConnectgetcity();
		
		JComboBox comboBox = new JComboBox(s);
		comboBox.setBounds(114, 13, 242, 22);
		frame.getContentPane().add(comboBox);
		
		JComboBox comboBox_1 = new JComboBox(s);
		comboBox_1.setBounds(114, 48, 242, 22);
		frame.getContentPane().add(comboBox_1);
		
		JButton btnSearchForCabs = new JButton("Search for Cabs");
		
		JButton btnConfirmBooking = new JButton("Confirm Booking");
		
		btnConfirmBooking.setVisible(false);
		btnConfirmBooking.setBounds(70, 199, 135, 25);
		frame.getContentPane().add(btnConfirmBooking);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setVisible(false);
		lblNewLabel.setBounds(184, 121, 148, 16);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setVisible(false);
		lblNewLabel_1.setBounds(184, 141, 148, 16);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("New label");
		lblNewLabel_2.setVisible(false);
		lblNewLabel_2.setBounds(184, 170, 148, 16);
		frame.getContentPane().add(lblNewLabel_2);
		
		JLabel lblPickup = new JLabel("Pickup :");
		lblPickup.setBounds(24, 16, 56, 16);
		frame.getContentPane().add(lblPickup);
		
		JLabel lblDestination = new JLabel("Destination :");
		lblDestination.setBounds(24, 51, 89, 16);
		frame.getContentPane().add(lblDestination);
		
		JLabel lblDistance = new JLabel("Distance:");
		lblDistance.setVisible(false);
		lblDistance.setBounds(70, 121, 56, 16);
		frame.getContentPane().add(lblDistance);
		
		JLabel lblEstimatedTime = new JLabel("Estimated Time:");
		lblEstimatedTime.setVisible(false);
		lblEstimatedTime.setBounds(70, 141, 102, 16);
		frame.getContentPane().add(lblEstimatedTime);
		
		JLabel lblEstimatedFare = new JLabel("Estimated Fare:");
		lblEstimatedFare.setVisible(false);
		lblEstimatedFare.setBounds(70, 170, 102, 16);
		frame.getContentPane().add(lblEstimatedFare);
		
		
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setVisible(false);
		btnCancel.setBounds(249, 199, 97, 25);
		frame.getContentPane().add(btnCancel);
		
		btnSearchForCabs.setBounds(70, 83, 148, 25);
		frame.getContentPane().add(btnSearchForCabs);
		
		JButton btnMyWallet = new JButton("My Wallet");
		btnMyWallet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new UserWallet(u1.userid,u1.password);
				EventQueue.invokeLater(new Runnable() {
        			public void run() {
        				try {
        					UserWallet window = new UserWallet();
        					window.frame.setVisible(true);
        				} catch (Exception e) {
        					e.printStackTrace();
        				}
        			}
        		});
			}
		});
		btnMyWallet.setBounds(235, 83, 153, 25);
		frame.getContentPane().add(btnMyWallet);
		
		btnSearchForCabs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				from = (String) comboBox.getSelectedItem();
				to = (String) comboBox_1.getSelectedItem();
				int x1 = SqlConnector.getX(from);
				int y1 = SqlConnector.getY(from);
				int x2 = SqlConnector.getX(to);
				int y2 = SqlConnector.getY(to);
				distance = User.distanceCal(x1, y1, x2, y2);
				u1 = User.getUser(u1.userid, u1.password);
				if(to.equals(from)) {
					JFrame f; 
            	    f=new JFrame();
            	    JOptionPane.showMessageDialog(f,"Please Enter 2 different locations!");
				}
				else if(!u1.checkWallet(distance)) {
					JFrame f; 
            	    f=new JFrame();
            	    JOptionPane.showMessageDialog(f,"Insufficient Wallet Balance!");
				}
				else if(!DriverAllotment.checkDriverCount(from)) {
					JFrame f; 
            	    f=new JFrame();
            	    JOptionPane.showMessageDialog(f,"No cabs available at the moment!");
            	    String location = DriverAllotment.maxDriverCity();
            	    String BestDriver = DriverAllotment.driverAllocate(location);
            	    DriverAllotment.reallocateDrivers(location, from, BestDriver);
				}
				else if(DriverAllotment.checkDriverCount(from)){
					d.vehicleNo = DriverAllotment.driverAllocate(from);
					DriverAllotment.decreaseCityDriverCount(from);
					SqlConnector.updateAvailabilityN(d.vehicleNo);
					
					btnConfirmBooking.setVisible(true);
					btnCancel.setVisible(true);
					lblNewLabel.setText(Double.toString(distance) + " Km");
					lblNewLabel.setVisible(true);
					lblDistance.setVisible(true);
					lblNewLabel_1.setText(Double.toString(User.timeCal(distance)/1000) + " min");
					lblNewLabel_1.setVisible(true);
					lblEstimatedTime.setVisible(true);
					lblNewLabel_2.setText("Rs. "+ Double.toString(User.costCal(distance)));
					lblNewLabel_2.setVisible(true);
					lblEstimatedFare.setVisible(true);
					btnSearchForCabs.setVisible(false);
				}
			}
		});
		
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SqlConnector.updateAvailabilityY(d.vehicleNo);
				DriverAllotment.increaseCityDriverCount(from);
				
				lblNewLabel.setVisible(false);
				lblDistance.setVisible(false);
				lblNewLabel_1.setVisible(false);
				lblEstimatedTime.setVisible(false);
				lblNewLabel_2.setVisible(false);
				lblEstimatedFare.setVisible(false);
				btnConfirmBooking.setVisible(false);
				btnSearchForCabs.setVisible(true);
				btnCancel.setVisible(false);
			}
		});
		
		btnConfirmBooking.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				time = (int) User.timeCal(distance);
				
				if(SqlConnector.IsRiding(u1.userid)) {
					JFrame f; 
            	    f=new JFrame();
            	    JOptionPane.showMessageDialog(f,"You are already on a ride!");
				}
				else if(!u1.checkWallet(distance)) {
					JFrame f; 
            	    f=new JFrame();
            	    JOptionPane.showMessageDialog(f,"Insufficient Wallet Balance!");
				}
				
				else {
					SqlConnector.updateRidingY(u1.userid);
					btnConfirmBooking.setVisible(false);
					btnCancel.setVisible(false);
					lblNewLabel.setVisible(false);
					lblDistance.setVisible(false);
					lblNewLabel_1.setVisible(false);
					lblEstimatedTime.setVisible(false);
					lblNewLabel_2.setVisible(false);
					lblEstimatedFare.setVisible(false);
					btnSearchForCabs.setVisible(false);
					btnMyWallet.setVisible(false);
					
					new Trip(d.vehicleNo,to,time,u1.userid,u1.password,distance);
					
					EventQueue.invokeLater(new Runnable() {
	        			public void run() {
	        				try {
	        					Trip window = new Trip();
	        					window.frame.setVisible(true);
	        				} catch (Exception e) {
	        					e.printStackTrace();
	        				}
	        			}
	        		});
					Timer t = new Timer(time,new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							btnSearchForCabs.setVisible(true);
							btnMyWallet.setVisible(true);
						}
						
					});
					t.start();
				}
			}
		});
	}
}
