package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.junit.runners.BlockJUnit4ClassRunner;

import buildings.ArcheryRange;
import buildings.Barracks;
import buildings.EconomicBuilding;
import buildings.Farm;
import buildings.Market;
import buildings.MilitaryBuilding;
import buildings.Stable;
import engine.City;
import engine.Game;
import exceptions.BuildingInCoolDownException;
import exceptions.FriendlyCityException;
import exceptions.FriendlyFireException;
import exceptions.MaxCapacityException;
import exceptions.MaxLevelException;
import exceptions.MaxRecruitedException;
import exceptions.NotEnoughGoldException;
import exceptions.TargetNotReachedException;
import units.Archer;
import units.Army;
import units.Cavalry;
import units.Status;
import units.Unit;

public class Launcher implements ActionListener {
	private String cd="";
	public int width, height;
	JFrame frame = new JFrame("Conquer Game");
	JFrame defendingarmyFrame = new JFrame("Defending Army");

	JFrame armycontrolledview = new JFrame("Controlled Armies");
	JFrame idelframe = new JFrame("Idel Army Represtation");
	JFrame attackFrame = new JFrame("Attack Frame");
	JFrame frame2 = new JFrame("Conquer GAME");

	JLabel name = new JLabel("Please Enter Your Name , Sir");
	Image cityimage = new ImageIcon(this.getClass().getResource("/wp.jpg")).getImage();
	Image image1 = new ImageIcon(this.getClass().getResource("/images.jpg")).getImage();
	Image image3 = new ImageIcon(this.getClass().getResource("/start.jpg")).getImage();
	ImageIcon cityimage1 = new ImageIcon(cityimage);
	JLabel citylabel = new JLabel("", cityimage1, JLabel.CENTER);
	ImageIcon img = new ImageIcon(image3);
	JLabel label = new JLabel("", img, JLabel.CENTER);
	JLabel info = new JLabel();

	String[] cities = { "Cairo", "Sparta", "Rome" };
	JComboBox citiesBox = new JComboBox<>(cities);
	JComboBox controlledcitiesBox = new JComboBox<>();
	JComboBox targetcitiesBox = new JComboBox<>();
	JComboBox defendingUnitsBox = new JComboBox<>();
	JComboBox chooseUnitBox = new JComboBox<>();
	JComboBox chooseArmy1Box = new JComboBox<>();
	JComboBox chooseArmy2Box = new JComboBox<>();
	JComboBox idelarmiesBox = new JComboBox();
	JComboBox marchingarmiesBox = new JComboBox();
	JComboBox besiegingarmiesBox = new JComboBox();
	JComboBox attackUnit = new JComboBox();
	JComboBox targetUnit = new JComboBox();
	JTextArea player_name = new JTextArea();
	JTextArea idleArmies = new JTextArea();
	JTextArea marchingArmies = new JTextArea();
	JTextArea besiegingArmies = new JTextArea();
	JTextArea armyArea = new JTextArea();

	JPanel conarmyPanel = new JPanel(new FlowLayout());
	JPanel economicview = new JPanel(new GridLayout(2, 2));
	JPanel militryview = new JPanel(new GridLayout(3, 3));
	JPanel defendingview = new JPanel(new GridLayout(4, 2));
	JPanel goback = new JPanel(new GridLayout(1, 1));
	JPanel army = new JPanel(new GridLayout(1, 4));
	JPanel armies = new JPanel(new GridLayout(1, 1));
	JPanel armyPanel = new JPanel(new GridLayout(1, 2));
	JPanel controledArmiesPanel = new JPanel(new GridLayout(1, 3));

	JButton button = new JButton("Start");
	JButton attackcairoButton = new JButton("Attack Cairo");
	JButton attackspartaButton = new JButton("Attack Sparta");
	JButton attackromaButton = new JButton("Attack Roma");
	JButton targetcairoButton = new JButton("Target Cairo");
	JButton targetspartaButton = new JButton("Target Sparta");
	JButton targetromaButton = new JButton("Target Roma");
	JButton besiegecairoButton = new JButton("Besiege Cairo");
	JButton besiegespartaButton = new JButton("Besiege Sparta");
	JButton besiegeromaButton = new JButton("Besiege Roma");
	JButton autoresolveButton1 = new JButton("Auto Resolve Attack");
	JButton autoresolveButton2 = new JButton("Auto Resolve Attack");
	JButton autoresolveButton3 = new JButton("Auto Resolve Attack");
	JButton archerButton = new JButton();
	JButton infantryButton = new JButton();
	JButton cavalryButton = new JButton();
	JButton archeryButton = new JButton("Bulid ArcheryRange , 1500 $");
	JButton barracksButton = new JButton("Build Barracks , 2000 $");
	JButton stableButton = new JButton("Bulid Stable , 2500 $");
	JButton archeryupgrade = new JButton("UpGrade ArcheryRange , 800 $");
	JButton barracksupgrade = new JButton("UpGrade Barracks , 1000 $");
	JButton stableupgrade = new JButton("UpGrade Stable , 1500 $");
	JButton archeryrecruit = new JButton("Recruit Archer,Level 1,400$");
	JButton barracksrecruit = new JButton("Recruit Infanfry,Level 1,500$");
	JButton stablerecruit = new JButton("Recruit Cavalry,Level 1,600$");
	JButton farmButton = new JButton("Build Farm , 1000 $");
	JButton farmupgrade = new JButton("Farm level 1 , Upgrade cost 500 $");
	JButton marketButton = new JButton("Build Market , 1500 $");
	JButton marketupgrade = new JButton("Market UpGrde , 700 $");
	JButton defendingarmyButton = new JButton("DefendingArmy");
	JButton cityarmyButton = new JButton("City Army");
	JButton goBackButton = new JButton("World Map");
	JButton endTurnButton = new JButton("End Turn");
	JButton archerButtoninfo = new JButton();
	JButton infantryButtoninfo = new JButton();
	JButton cavalryButtoninfo = new JButton();
	JButton attackJButton = new JButton("Attack");
	JButton relocateButton = new JButton("Relocate Unit");
	JButton initiateArmyButton = new JButton("Initiate Army");
	JButton gotoavailablecities = new JButton("Go to available cities");
	JButton spartaButton = new JButton("Sparta");
	JButton romaButton = new JButton("Roma");
	JButton cairoButton = new JButton("Cairo");
	JButton GoBackToCity = new JButton("Go Back To City");
	JButton GetControlledArmies = new JButton("Get Controlled Armies(Idle, Marching & Besieging)");
	JButton attackButton = new JButton("Attack");
	JTextArea attackInfo = new JTextArea();
	

//	Image archer = new ImageIcon(this.getClass().getResource("/archerim.jpg")).getImage();
//	ImageIcon archerimg = new ImageIcon(archer);
//	Image cavalry = new ImageIcon(this.getClass().getResource("/cavalryim.jpg")).getImage();
//	ImageIcon cavalryimg = new ImageIcon(cavalry);
//	Image farm = new ImageIcon(this.getClass().getResource("/farmimage.jpg")).getImage();
//	ImageIcon farmimg = new ImageIcon(farm);
//	Image infantry = new ImageIcon(this.getClass().getResource("/infantryim.jpg")).getImage();
//	ImageIcon infantryimg = new ImageIcon(infantry);
//	Image market = new ImageIcon(this.getClass().getResource("/marketim.jpg")).getImage();
//	ImageIcon marketimg = new ImageIcon(market);

	JScrollPane scrollableTextArea = new JScrollPane(armyArea);
	JScrollPane scrollableTextArea1 = new JScrollPane(idleArmies);
	JScrollPane scrollableTextArea2 = new JScrollPane(marchingArmies);
	JScrollPane scrollableTextArea3 = new JScrollPane(besiegingArmies);
	JScrollPane scrollableTextArea4 = new JScrollPane(besiegingArmies);
	String selectedcity = "";

	City city;
	private int visit = 0;
	private int visit1= 5;
	private int x=0;
	private int cityC=5;
	private int cityR=5;
	private int cityS=5;
	private int cityNow=5;
	
	public int getCityNow() {
		return cityNow;
	}

	Game game = null;

	Launcher() {
		width = Toolkit.getDefaultToolkit().getScreenSize().width;
		height = Toolkit.getDefaultToolkit().getScreenSize().width;

		button.setBounds(4 * width / 9, 4 * height / 10, 2 * width / 19, 2 * height / 40);
		button.setBackground(Color.GREEN);
		button.setFont(new Font("", Font.ITALIC, 60));
		button.setForeground(new Color(255, 0, 255));
		GoBackToCity.setBackground(Color.GREEN);
		GoBackToCity.setFont(new Font("", Font.ITALIC, 24));
		GoBackToCity.setForeground(new Color(255, 0, 255));
		button.setFocusable(false);
		//attackButton.setBounds(4 * width / 9, 4 * height / 25, 2 * width / 19, 2 * height / 40);
		//attackInfo.setBounds(25 * width / 64, 5 * height / 64, 3 * width / 15, 2 * height / 76);
		//attackUnit.setBounds(5 * width / 48, 25 * height / 105, 25 * width / 192, 25 * height / 384);
	///	targetUnit.setBounds(155 * width / 192, 25 * height / 105, 25 * width / 192, 25 * height / 384);
		attackFrame.add(attackButton,BorderLayout.SOUTH);
		attackFrame.add(attackInfo ,BorderLayout.CENTER);
		attackFrame.add(attackUnit , BorderLayout.WEST);
		attackFrame.add(targetUnit, BorderLayout.EAST);
		armyArea.setEditable(false);
		armies.add(endTurnButton);

		button.addActionListener(this);
		relocateButton.addActionListener(this);
		initiateArmyButton.addActionListener(this);
		barracksButton.addActionListener(this);
		archeryButton.addActionListener(this);
		stableButton.addActionListener(this);
		farmButton.addActionListener(this);
		marketButton.addActionListener(this);
		farmupgrade.addActionListener(this);
		marketupgrade.addActionListener(this);
		stableupgrade.addActionListener(this);
		barracksupgrade.addActionListener(this);
		archeryupgrade.addActionListener(this);
		archeryrecruit.addActionListener(this);
		barracksrecruit.addActionListener(this);
		stablerecruit.addActionListener(this);
		GetControlledArmies.addActionListener(this);
		defendingarmyButton.addActionListener(this);
		cityarmyButton.addActionListener(this);
		GoBackToCity.addActionListener(this);
		gotoavailablecities.addActionListener(this);

		attackcairoButton.addActionListener(this);
		attackButton.addActionListener(this);
		attackspartaButton.addActionListener(this);
		attackromaButton.addActionListener(this);

		targetcairoButton.addActionListener(this);
		targetspartaButton.addActionListener(this);
		targetromaButton.addActionListener(this);

		besiegecairoButton.addActionListener(this);
		besiegespartaButton.addActionListener(this);
		besiegeromaButton.addActionListener(this);

		autoresolveButton1.addActionListener(this);
		autoresolveButton2.addActionListener(this);
		autoresolveButton3.addActionListener(this);

		idleArmies.setEditable(false);
		marchingArmies.setEditable(false);
		besiegingArmies.setEditable(false);

		archerButton.setIcon(new ImageIcon("C:/Users/mahmo/eclipse-workspace/EmpireM2/img/archerim.jpg"));
		infantryButton.setIcon(new ImageIcon("C:/Users/mahmo/eclipse-workspace/EmpireM2/img/infa.png"));
		cavalryButton.setIcon(new ImageIcon("C:/Users/mahmo/eclipse-workspace/EmpireM2/img/cavalryim.png"));

		defendingview.add(defendingUnitsBox);
		defendingview.add(initiateArmyButton);
		defendingview.add(chooseArmy1Box);
		defendingview.add(chooseArmy2Box);
		defendingview.add(chooseUnitBox);
		defendingview.add(relocateButton);
		defendingview.add(scrollableTextArea);

//		defendingview.add(archerButton);
//		defendingview.add(infantryButton);
//		defendingview.add(cavalryButton);
		goback.add(GoBackToCity);

		armyPanel.add(gotoavailablecities);
		GetControlledArmies.setVisible(true);
		name.setFont(new Font("", Font.BOLD, 28));
		name.setBounds(4 * width / 10, 2 * height / 60, 2 * width / 6, 2 * height / 40);
		name.setForeground(new Color(255, 0, 255));
		player_name.setBounds(25 * width / 64, 5 * height / 64, 377 * width / 1536, 25 * height / 768);
		player_name.setBackground(Color.ORANGE);
		player_name.setForeground(new Color(0, 0, 255));
		player_name.setFont(new Font("", Font.ITALIC, 45));
		label.setPreferredSize(new Dimension(frame.getWidth(), frame.getHeight()));
		endTurnButton.addActionListener(this);
		goBackButton.addActionListener(this);
		citiesBox.addActionListener(this);
		citiesBox.setEditable(false);
		citiesBox.setBounds(4 * width / 9, 3 * height / 15, 2 * width / 19, 2 * height / 40);
		label.add(citiesBox);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		label.add(button);
		label.add(name);
		label.add(player_name);
		label.setIcon(img);
		info.setFont(new Font("", Font.BOLD, 16));
		frame.add(label);
		frame.pack();
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);
		defendingview.setPreferredSize(new Dimension(3 * width / 5, 6 * height / 13));
		chooseArmy1Box.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(chooseArmy1Box.getSelectedItem()==null) {
					
				}else if("Select Army To Relocate Unit From".equals(chooseArmy1Box.getSelectedItem())) {
					chooseUnitBox.removeAllItems();
					chooseUnitBox.addItem("Select Unit To Relocate");
					chooseUnitBox.addItem("choose Army First");
				} 
				else if("Defending Army".equals(chooseArmy1Box.getSelectedItem())){
					chooseUnitBox.removeAllItems();
					chooseUnitBox.addItem("Select Unit To Relocate");
					for(int j=0;j<game.getPlayer().getControlledCities().get(0).getDefendingArmy().getUnits().size();) {
						chooseUnitBox.addItem("Unit "+(++j));
					}
				}
				else {
					chooseUnitBox.removeAllItems();
					chooseUnitBox.addItem("Select Unit To Relocate");
					String string1=(String)(chooseArmy1Box.getSelectedItem());
					int idx1 =-1;
					for (int i = 0; i < string1.length(); i++) {
						if(Character.isDigit(string1.charAt((string1.length())-1-i))) {
							idx1 +=Character.getNumericValue((string1.charAt((string1.length())-1-i)))*Math.pow(10,i);
						} else break;
					}
					//game.getPlayer().getControlledArmies().get(idx1)
					for(int j=0;j<game.getPlayer().getControlledArmies().get(idx1).getUnits().size();) {
						chooseUnitBox.addItem("Unit "+(++j));
					}
				}
				
				
			}
		});

	}

	public static void main(String[] args) {
		Launcher launcher = new Launcher();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (player_name.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "You must Enter a Name", "Warning", JOptionPane.WARNING_MESSAGE);
			frame.dispose();
			Launcher launcher = new Launcher();
		} else {
			if (e.getSource() == button) {
				label.removeAll();
				
				armies.add(GetControlledArmies);
				JTextArea controlledcities = new JTextArea("Controlled Cities : ");
				controlledcities.setEditable(false);
				controlledcities.setBounds(5 * width / 768, 5 * height / 512, 15 * width / 128, 5 * height / 256);
				controlledcitiesBox.addItem(citiesBox.getSelectedItem());
				selectedcity = (String) controlledcitiesBox.getSelectedItem();
				targetcitiesBox.setBounds(2 * width / 3, 0, width / 9, height / 25);
				idelarmiesBox.setBounds(4*width/5, 0, width/9, height/25);
				controlledcitiesBox.setBounds(width / 8, 0, width / 9, height / 25);
				controlledcities.setForeground(new Color(0, 0, 255));
				controlledcities.setFont(new Font("", Font.BOLD, 20));
				label.add(controlledcitiesBox);
				label.add(targetcitiesBox);
				label.add(idelarmiesBox);
				label.add(controlledcities);
				frame.add(armies, BorderLayout.NORTH);
				Image image3 = new ImageIcon(this.getClass().getResource("/wp.jpg")).getImage();
				ImageIcon img = new ImageIcon(image3);
				label.setIcon(img);

				spartaButton.setBounds(5 * width / 48, 25 * height / 105, 25 * width / 192, 25 * height / 384);
				romaButton.setBounds(175 * width / 384, 25 * height / 105, 25 * width / 192, 25 * height / 384);
				cairoButton.setBounds(155 * width / 192, 25 * height / 105, 25 * width / 192, 25 * height / 384);

				targetcairoButton.setBounds(155 * width / 192, 25 * height / 81, 25 * width / 192, 25 * height / 384);
				besiegecairoButton.setBounds(155 * width / 192, 25 * height / 66, 25 * width / 192, 25 * height / 384);
				attackcairoButton.setBounds(155 * width / 192, 25 * height / 56, 25 * width / 192, 25 * height / 384);

				targetromaButton.setBounds(175 * width / 384, 25 * height / 81, 25 * width / 192, 25 * height / 384);
				besiegeromaButton.setBounds(175 * width / 384, 25 * height / 66, 25 * width / 192, 25 * height / 384);
				attackromaButton.setBounds(175 * width / 384, 25 * height / 56, 25 * width / 192, 25 * height / 384);

				targetspartaButton.setBounds(5 * width / 48, 25 * height / 81, 25 * width / 192, 25 * height / 384);
				besiegespartaButton.setBounds(5 * width / 48, 25 * height / 66, 25 * width / 192, 25 * height / 384);
				attackspartaButton.setBounds(5 * width / 48, 25 * height / 56, 25 * width / 192, 25 * height / 384);

				autoresolveButton1.setBounds(5 * width / 48, 25 * height / 150, 25 * width / 192, 25 * height / 384);
				autoresolveButton2.setBounds(175 * width / 384, 25 * height / 150, 25 * width / 192, 25 * height / 384);
				autoresolveButton3.setBounds(155 * width / 192, 25 * height / 150, 25 * width / 192, 25 * height / 384);

				spartaButton.setBackground(Color.GREEN);
				spartaButton.setFont(new Font("", Font.ITALIC, 45));
				spartaButton.setForeground(new Color(255, 0, 255));
				romaButton.setBackground(Color.GREEN);
				romaButton.setFont(new Font("", Font.ITALIC, 45));
				romaButton.setForeground(new Color(255, 0, 255));
				cairoButton.setBackground(Color.GREEN);
				cairoButton.setFont(new Font("", Font.ITALIC, 45));
				cairoButton.setForeground(new Color(255, 0, 255));
				spartaButton.setFocusable(false);
				spartaButton.addActionListener(this);
				romaButton.setFocusable(false);
				romaButton.addActionListener(this);
				cairoButton.setFocusable(false);
				cairoButton.addActionListener(this);
				label.setBounds(0, 0, width, height);
				label.setFont(new Font(null, Font.BOLD, 25));
				label.add(spartaButton);
				label.add(romaButton);
				label.add(cairoButton);

				label.add(targetcairoButton);
				label.add(besiegecairoButton);
				label.add(attackcairoButton);

				label.add(targetromaButton);
				label.add(besiegeromaButton);
				label.add(attackromaButton);

				label.add(targetspartaButton);
				label.add(besiegespartaButton);
				label.add(attackspartaButton);

				label.add(targetcairoButton);
				label.add(besiegecairoButton);
				label.add(attackcairoButton);

				label.add(autoresolveButton1);
				label.add(autoresolveButton2);
				label.add(autoresolveButton3);

				frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
				frame.getContentPane().setBackground(Color.WHITE);
				frame.setResizable(false);
				frame.add(label);
				frame.setVisible(true);
				city = new City((String) citiesBox.getSelectedItem());
				chooseArmy1Box.addItem("Select Army To Relocate Unit From");
				chooseArmy2Box.addItem("Select Army To Relocate Unit To");
				// chooseUnitBox.addItem("Select Unit To Relocate");
				defendingUnitsBox.addItem("Select Unit From Defending Army For The New Army");
				chooseArmy1Box.addItem("Defending Army");
				chooseArmy2Box.addItem("Defending Army");
				try {
					game = new Game(player_name.getText(), (String) citiesBox.getSelectedItem());

				} catch (IOException e1) {
					e1.printStackTrace();
				}
				citiesBox.removeItem(controlledcitiesBox.getSelectedItem());
				for (int i = 0; i < 2; i++) {
					targetcitiesBox.addItem(citiesBox.getItemAt(i));
				}
				info.setText(player_name.getText()
						+ "                                                                    " + "Turns Count : "
						+ game.getCurrentTurnCount()
						+ "                                                             Food : "
						+ game.getPlayer().getFood() + "                                                       Gold : "
						+ game.getPlayer().getTreasury());
				frame.add(info , BorderLayout.SOUTH);
			}
		}
		if (e.getSource() == endTurnButton) {
			game.endTurn();
			info.setText(
					player_name.getText() + "                                                                        "
							+ "Turns Count : " + game.getCurrentTurnCount()
							+ "                                                                 Food : "
							+ game.getPlayer().getFood()
							+ "                                                              Gold : "
							+ game.getPlayer().getTreasury());
			frame2.revalidate();
			frame2.repaint();
			if (game.getCurrentTurnCount() == game.getMaxTurnCount()) {
				JOptionPane.showMessageDialog(null, "You are loser", "End Game", JOptionPane.YES_OPTION);
			}
			else if(game.getPlayer().getControlledCities().size()==3) {
				JOptionPane.showMessageDialog(null, "You are victourios", "End Game", JOptionPane.YES_OPTION);
			}
			System.out.println(game.getPlayer().getTreasury());
		}
		if (e.getSource() == spartaButton) {
			cityNow=cityS;
			if (visit == 0) {
				city= game.getPlayer().getControlledCities().get(cityNow);
				info.setText(player_name.getText()
						+ "                                                                    " + "Turns Count : "
						+ game.getCurrentTurnCount()
						+ "                                                             Food : "
						+ game.getPlayer().getFood() + "                                                       Gold : "
						+ game.getPlayer().getTreasury());
				frame.setVisible(false);
				frame2.add(info, BorderLayout.NORTH);
				frame2.getContentPane().setBackground(Color.WHITE);
				frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame2.setExtendedState(JFrame.MAXIMIZED_BOTH);
				goBackButton.setBackground(Color.BLACK);
				goBackButton.setForeground(Color.WHITE);
				goBackButton.setFont(new Font("", Font.BOLD, 20));
				goBackButton.setFocusable(false);
				endTurnButton.setBackground(Color.BLACK);
				endTurnButton.setForeground(Color.WHITE);
				endTurnButton.setFont(new Font("", Font.BOLD, 20));
				endTurnButton.setFocusable(false);
				frame2.add(endTurnButton);
				frame2.add(goBackButton);
				economicview.add(farmButton);
				economicview.add(farmupgrade);
				economicview.add(marketButton);
				economicview.add(marketupgrade);
				militryview.add(archeryButton);
				militryview.add(archeryupgrade);
				militryview.add(archeryrecruit);
				militryview.add(barracksButton);
				militryview.add(barracksupgrade);
				militryview.add(barracksrecruit);
				militryview.add(stableButton);
				militryview.add(stableupgrade);
				militryview.add(stablerecruit);
				farmupgrade.setVisible(false);
				marketupgrade.setVisible(false);
				archeryupgrade.setVisible(false);
				archeryrecruit.setVisible(false);
				barracksupgrade.setVisible(false);
				barracksrecruit.setVisible(false);
				stableupgrade.setVisible(false);
				stablerecruit.setVisible(false);
				barracksButton.setBackground(Color.CYAN);
				barracksButton.setForeground(Color.RED);
				barracksButton.setFont(new Font("", Font.ITALIC, 25));
				barracksButton.setFocusable(false);
				farmButton.setBackground(Color.CYAN);
				farmButton.setForeground(Color.RED);
				farmButton.setFont(new Font("", Font.ITALIC, 25));
				farmButton.setFocusable(false);
				marketButton.setBackground(Color.CYAN);
				marketButton.setForeground(Color.RED);
				marketButton.setFont(new Font("", Font.ITALIC, 25));
				marketButton.setFocusable(false);
				archeryButton.setBackground(Color.CYAN);
				archeryButton.setForeground(Color.RED);
				archeryButton.setFont(new Font("", Font.ITALIC, 22));
				archeryButton.setFocusable(false);
				stableButton.setBackground(Color.CYAN);
				stableButton.setForeground(Color.RED);
				stableButton.setFont(new Font("", Font.ITALIC, 25));
				stableButton.setFocusable(false);
				army.add(defendingarmyButton);
				army.add(cityarmyButton);
				army.add(endTurnButton);
				army.add(goBackButton);
				defendingarmyButton.setBackground(Color.BLACK);
				defendingarmyButton.setForeground(Color.WHITE);
				defendingarmyButton.setFont(new Font("", Font.BOLD, 20));
				defendingarmyButton.setFocusable(false);
				cityarmyButton.setBackground(Color.BLACK);
				cityarmyButton.setForeground(Color.WHITE);
				cityarmyButton.setFont(new Font("", Font.BOLD, 20));
				cityarmyButton.setFocusable(false);
				frame2.add(economicview, BorderLayout.WEST);
				frame2.add(militryview, BorderLayout.CENTER);
				frame2.add(army, BorderLayout.SOUTH);
			}
			else {
				City c= game.getPlayer().getControlledCities().get(cityNow);
				for(MilitaryBuilding militaryBuilding:c.getMilitaryBuildings()) {
					if(militaryBuilding instanceof ArcheryRange) {
						archeryButton.addActionListener(null);
						archeryButton.setIcon(new ImageIcon("C:/Users/mahmo/eclipse-workspace/EmpireM2/img/a.jpg"));
						archeryupgrade.setVisible(true);
						archeryrecruit.setVisible(true);
						if(militaryBuilding.getLevel()==3) {
							archeryupgrade.setVisible(false);
							archeryrecruit.setVisible(true);
							archeryupgrade.addActionListener(null);
							archeryrecruit.setText("Recruit Archer,Level 3,500$");
						}
						else if(militaryBuilding.getLevel()==2) {
							archeryupgrade.setVisible(true);
							archeryrecruit.setVisible(true);
							archeryupgrade.addActionListener(this);
							archeryrecruit.setText("Recruit Archer,Level 2,450$");
						}
						else if(militaryBuilding.getLevel()==1) {
							archeryupgrade.setVisible(true);
							archeryrecruit.setVisible(true);
							archeryupgrade.addActionListener(this);
							archeryrecruit.setText("Recruit Archer,Level 2,400$");
						}
					}
					if(militaryBuilding instanceof Stable) {
						stableButton.addActionListener(null);
						stablerecruit.setVisible(true);
						stableupgrade.setVisible(true);
						stableButton.setIcon(new ImageIcon("C:/Users/mahmo/eclipse-workspace/EmpireM2/img/c.jpg"));
						if(militaryBuilding.getLevel()==3) {
							stableupgrade.setVisible(false);
							stablerecruit.setVisible(true);
							stableupgrade.addActionListener(null);
							stablerecruit.setText("Recruit Archer,Level 3,700$");
						}
						else if(militaryBuilding.getLevel()==2) {
							stableupgrade.setVisible(true);
							stablerecruit.setVisible(true);
							stableupgrade.addActionListener(this);
							stablerecruit.setText("Recruit Archer,Level 2,650$");
						}
						else if(militaryBuilding.getLevel()==1) {
							stableupgrade.setVisible(true);
							stablerecruit.setVisible(true);
							stableupgrade.addActionListener(this);
							stablerecruit.setText("Recruit Archer,Level 2,600$");
						}
					}
					if(militaryBuilding instanceof Barracks) {
						barracksButton.addActionListener(null);
						barracksupgrade.setVisible(true);
						barracksrecruit.setVisible(true);
						barracksButton.setIcon(new ImageIcon("C:/Users/mahmo/eclipse-workspace/EmpireM2/img/i.jpg"));
						if(militaryBuilding.getLevel()==3) {
							barracksupgrade.setVisible(false);
							barracksrecruit.setVisible(true);
							barracksupgrade.addActionListener(null);
							barracksrecruit.setText("Recruit Infantry,Level 3,600$");
						}
						else if(militaryBuilding.getLevel()==2) {
							barracksupgrade.setVisible(true);
							barracksrecruit.setVisible(true);
							barracksupgrade.addActionListener(this);
							barracksrecruit.setText("Recruit Infantry,Level 2,550$");
						}
						else if(militaryBuilding.getLevel()==1) {
							barracksupgrade.setVisible(true);
							barracksrecruit.setVisible(true);
							barracksupgrade.addActionListener(this);
							barracksrecruit.setText("Recruit Infantry,Level 2,500$");
						}
					}


				}
				for(EconomicBuilding economicBuilding:c.getEconomicalBuildings()) {
					if(economicBuilding instanceof Farm ) {
						farmButton.addActionListener(null);
						farmButton.setText(null);
						farmButton.setIcon(new ImageIcon("C:/Users/mahmo/eclipse-workspace/EmpireM2/img/farmimage.jpg"));
						if(economicBuilding.getLevel()==3) {
							farmupgrade.setVisible(false);
						}
						else if(economicBuilding.getLevel()==2) {
							farmupgrade.setVisible(true);
						}
						else if(economicBuilding.getLevel()==1) {
							farmupgrade.setVisible(true);
						}
					}
				}
			}
			frame2.setResizable(false);
			frame2.revalidate();
			frame2.repaint();
			frame2.setVisible(true);

		}
		if (e.getSource() == romaButton) {
			cityNow= cityR;
			if (visit == 0) {
				city= game.getPlayer().getControlledCities().get(cityNow);
				info.setText(player_name.getText()
						+ "                                                                    " + "Turns Count : "
						+ game.getCurrentTurnCount()
						+ "                                                             Food : "
						+ game.getPlayer().getFood() + "                                                       Gold : "
						+ game.getPlayer().getTreasury());
				frame.setVisible(false);
				frame2.add(info, BorderLayout.NORTH);
				frame2.getContentPane().setBackground(Color.WHITE);
				frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame2.setExtendedState(JFrame.MAXIMIZED_BOTH);
				goBackButton.setBackground(Color.BLACK);
				goBackButton.setForeground(Color.WHITE);
				goBackButton.setFont(new Font("", Font.BOLD, 20));
				goBackButton.setFocusable(false);
				endTurnButton.setBackground(Color.BLACK);
				endTurnButton.setForeground(Color.WHITE);
				endTurnButton.setFont(new Font("", Font.BOLD, 20));
				endTurnButton.setFocusable(false);
				frame2.add(endTurnButton);
				frame2.add(goBackButton);
				economicview.add(farmButton);
				economicview.add(farmupgrade);
				economicview.add(marketButton);
				economicview.add(marketupgrade);
				militryview.add(archeryButton);
				militryview.add(archeryupgrade);
				militryview.add(archeryrecruit);
				militryview.add(barracksButton);
				militryview.add(barracksupgrade);
				militryview.add(barracksrecruit);
				militryview.add(stableButton);
				militryview.add(stableupgrade);
				militryview.add(stablerecruit);
				farmupgrade.setVisible(false);
				marketupgrade.setVisible(false);
				archeryupgrade.setVisible(false);
				archeryrecruit.setVisible(false);
				barracksupgrade.setVisible(false);
				barracksrecruit.setVisible(false);
				stableupgrade.setVisible(false);
				stablerecruit.setVisible(false);
				barracksButton.setBackground(Color.CYAN);
				barracksButton.setForeground(Color.RED);
				barracksButton.setFont(new Font("", Font.ITALIC, 25));
				barracksButton.setFocusable(false);
				farmButton.setBackground(Color.CYAN);
				farmButton.setForeground(Color.RED);
				farmButton.setFont(new Font("", Font.ITALIC, 25));
				farmButton.setFocusable(false);
				marketButton.setBackground(Color.CYAN);
				marketButton.setForeground(Color.RED);
				marketButton.setFont(new Font("", Font.ITALIC, 25));
				marketButton.setFocusable(false);
				archeryButton.setBackground(Color.CYAN);
				archeryButton.setForeground(Color.RED);
				archeryButton.setFont(new Font("", Font.ITALIC, 22));
				archeryButton.setFocusable(false);
				stableButton.setBackground(Color.CYAN);
				stableButton.setForeground(Color.RED);
				stableButton.setFont(new Font("", Font.ITALIC, 25));
				stableButton.setFocusable(false);
				army.add(defendingarmyButton);
				army.add(cityarmyButton);
				army.add(endTurnButton);
				army.add(goBackButton);
				defendingarmyButton.setBackground(Color.BLACK);
				defendingarmyButton.setForeground(Color.WHITE);
				defendingarmyButton.setFont(new Font("", Font.BOLD, 20));
				defendingarmyButton.setFocusable(false);
				cityarmyButton.setBackground(Color.BLACK);
				cityarmyButton.setForeground(Color.WHITE);
				cityarmyButton.setFont(new Font("", Font.BOLD, 20));
				cityarmyButton.setFocusable(false);
				frame2.add(economicview, BorderLayout.WEST);
				frame2.add(militryview, BorderLayout.CENTER);
				frame2.add(army, BorderLayout.SOUTH);
			}
			else {
				City c= game.getPlayer().getControlledCities().get(cityNow);
				for(MilitaryBuilding militaryBuilding:c.getMilitaryBuildings()) {
					if(militaryBuilding instanceof ArcheryRange) {
						archeryButton.addActionListener(null);
						archeryButton.setIcon(new ImageIcon("C:/Users/mahmo/eclipse-workspace/EmpireM2/img/a.jpg"));
						archeryupgrade.setVisible(true);
						archeryrecruit.setVisible(true);
						if(militaryBuilding.getLevel()==3) {
							archeryupgrade.setVisible(false);
							archeryrecruit.setVisible(true);
							archeryupgrade.addActionListener(null);
							archeryrecruit.setText("Recruit Archer,Level 3,500$");
						}
						else if(militaryBuilding.getLevel()==2) {
							archeryupgrade.setVisible(true);
							archeryrecruit.setVisible(true);
							archeryupgrade.addActionListener(this);
							archeryrecruit.setText("Recruit Archer,Level 2,450$");
						}
						else if(militaryBuilding.getLevel()==1) {
							archeryupgrade.setVisible(true);
							archeryrecruit.setVisible(true);
							archeryupgrade.addActionListener(this);
							archeryrecruit.setText("Recruit Archer,Level 2,400$");
						}
					}
					if(militaryBuilding instanceof Stable) {
						stableButton.addActionListener(null);
						stablerecruit.setVisible(true);
						stableupgrade.setVisible(true);
						stableButton.setIcon(new ImageIcon("C:/Users/mahmo/eclipse-workspace/EmpireM2/img/c.jpg"));
						if(militaryBuilding.getLevel()==3) {
							stableupgrade.setVisible(false);
							stablerecruit.setVisible(true);
							stableupgrade.addActionListener(null);
							stablerecruit.setText("Recruit Archer,Level 3,700$");
						}
						else if(militaryBuilding.getLevel()==2) {
							stableupgrade.setVisible(true);
							stablerecruit.setVisible(true);
							stableupgrade.addActionListener(this);
							stablerecruit.setText("Recruit Archer,Level 2,650$");
						}
						else if(militaryBuilding.getLevel()==1) {
							stableupgrade.setVisible(true);
							stablerecruit.setVisible(true);
							stableupgrade.addActionListener(this);
							stablerecruit.setText("Recruit Archer,Level 2,600$");
						}
					}
					if(militaryBuilding instanceof Barracks) {
						barracksButton.addActionListener(null);
						barracksupgrade.setVisible(true);
						barracksrecruit.setVisible(true);
						barracksButton.setIcon(new ImageIcon("C:/Users/mahmo/eclipse-workspace/EmpireM2/img/i.jpg"));
						if(militaryBuilding.getLevel()==3) {
							barracksupgrade.setVisible(false);
							barracksrecruit.setVisible(true);
							barracksupgrade.addActionListener(null);
							barracksrecruit.setText("Recruit Infantry,Level 3,600$");
						}
						else if(militaryBuilding.getLevel()==2) {
							barracksupgrade.setVisible(true);
							barracksrecruit.setVisible(true);
							barracksupgrade.addActionListener(this);
							barracksrecruit.setText("Recruit Infantry,Level 2,550$");
						}
						else if(militaryBuilding.getLevel()==1) {
							barracksupgrade.setVisible(true);
							barracksrecruit.setVisible(true);
							barracksupgrade.addActionListener(this);
							barracksrecruit.setText("Recruit Infantry,Level 2,500$");
						}
					}


				}
				for(EconomicBuilding economicBuilding:c.getEconomicalBuildings()) {
					if(economicBuilding instanceof Farm ) {
						farmButton.addActionListener(null);
						farmButton.setText(null);
						farmButton.setIcon(new ImageIcon("C:/Users/mahmo/eclipse-workspace/EmpireM2/img/farmimage.jpg"));
						if(economicBuilding.getLevel()==3) {
							farmupgrade.setVisible(false);
						}
						else if(economicBuilding.getLevel()==2) {
							farmupgrade.setVisible(true);
						}
						else if(economicBuilding.getLevel()==1) {
							farmupgrade.setVisible(true);
						}
					}
				}
			}
		
			frame2.setResizable(false);
			frame2.revalidate();
			frame2.repaint();
			frame2.setVisible(true);
		}
		if (e.getSource() == cairoButton) {
			cityNow = cityC;
			city= game.getPlayer().getControlledCities().get(cityNow);
			if (visit == 0) {
				info.setText(player_name.getText()
						+ "                                                                    " + "Turns Count : "
						+ game.getCurrentTurnCount()
						+ "                                                             Food : "
						+ game.getPlayer().getFood() + "                                                       Gold : "
						+ game.getPlayer().getTreasury());
				frame.setVisible(false);
				frame2.add(info, BorderLayout.NORTH);
				frame2.getContentPane().setBackground(Color.WHITE);
				frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame2.setExtendedState(JFrame.MAXIMIZED_BOTH);
				goBackButton.setBackground(Color.BLACK);
				goBackButton.setForeground(Color.WHITE);
				goBackButton.setFont(new Font("", Font.BOLD, 20));
				goBackButton.setFocusable(false);
				endTurnButton.setBackground(Color.BLACK);
				endTurnButton.setForeground(Color.WHITE);
				endTurnButton.setFont(new Font("", Font.BOLD, 20));
				endTurnButton.setFocusable(false);
				frame2.add(endTurnButton);
				frame2.add(goBackButton);
				economicview.add(farmButton);
				economicview.add(farmupgrade);
				economicview.add(marketButton);
				economicview.add(marketupgrade);
				militryview.add(archeryButton);
				militryview.add(archeryupgrade);
				militryview.add(archeryrecruit);
				militryview.add(barracksButton);
				militryview.add(barracksupgrade);
				militryview.add(barracksrecruit);
				militryview.add(stableButton);
				militryview.add(stableupgrade);
				militryview.add(stablerecruit);
				farmupgrade.setVisible(false);
				marketupgrade.setVisible(false);
				archeryupgrade.setVisible(false);
				archeryrecruit.setVisible(false);
				barracksupgrade.setVisible(false);
				barracksrecruit.setVisible(false);
				stableupgrade.setVisible(false);
				stablerecruit.setVisible(false);
				barracksButton.setBackground(Color.CYAN);
				barracksButton.setForeground(Color.RED);
				barracksButton.setFont(new Font("", Font.ITALIC, 25));
				barracksButton.setFocusable(false);
				farmButton.setBackground(Color.CYAN);
				farmButton.setForeground(Color.RED);
				farmButton.setFont(new Font("", Font.ITALIC, 25));
				farmButton.setFocusable(false);
				marketButton.setBackground(Color.CYAN);
				marketButton.setForeground(Color.RED);
				marketButton.setFont(new Font("", Font.ITALIC, 25));
				marketButton.setFocusable(false);
				archeryButton.setBackground(Color.CYAN);
				archeryButton.setForeground(Color.RED);
				archeryButton.setFont(new Font("", Font.ITALIC, 22));
				archeryButton.setFocusable(false);
				stableButton.setBackground(Color.CYAN);
				stableButton.setForeground(Color.RED);
				stableButton.setFont(new Font("", Font.ITALIC, 25));
				stableButton.setFocusable(false);
				army.add(defendingarmyButton);
				army.add(cityarmyButton);
				army.add(endTurnButton);
				army.add(goBackButton);
				defendingarmyButton.setBackground(Color.BLACK);
				defendingarmyButton.setForeground(Color.WHITE);
				defendingarmyButton.setFont(new Font("", Font.BOLD, 20));
				defendingarmyButton.setFocusable(false);
				cityarmyButton.setBackground(Color.BLACK);
				cityarmyButton.setForeground(Color.WHITE);
				cityarmyButton.setFont(new Font("", Font.BOLD, 20));
				cityarmyButton.setFocusable(false);
				frame2.add(economicview, BorderLayout.WEST);
				frame2.add(militryview, BorderLayout.CENTER);
				frame2.add(army, BorderLayout.SOUTH);
			}
			else {
				City c= game.getPlayer().getControlledCities().get(cityNow);
				for(MilitaryBuilding militaryBuilding:c.getMilitaryBuildings()) {
					if(militaryBuilding instanceof ArcheryRange) {
						archeryButton.addActionListener(null);
						archeryButton.setIcon(new ImageIcon("C:/Users/mahmo/eclipse-workspace/EmpireM2/img/a.jpg"));
						archeryupgrade.setVisible(true);
						archeryrecruit.setVisible(true);
						if(militaryBuilding.getLevel()==3) {
							archeryupgrade.setVisible(false);
							archeryrecruit.setVisible(true);
							archeryupgrade.addActionListener(null);
							archeryrecruit.setText("Recruit Archer,Level 3,500$");
						}
						else if(militaryBuilding.getLevel()==2) {
							archeryupgrade.setVisible(true);
							archeryrecruit.setVisible(true);
							archeryupgrade.addActionListener(this);
							archeryrecruit.setText("Recruit Archer,Level 2,450$");
						}
						else if(militaryBuilding.getLevel()==1) {
							archeryupgrade.setVisible(true);
							archeryrecruit.setVisible(true);
							archeryupgrade.addActionListener(this);
							archeryrecruit.setText("Recruit Archer,Level 2,400$");
						}
					}
					if(militaryBuilding instanceof Stable) {
						stableButton.addActionListener(null);
						stablerecruit.setVisible(true);
						stableupgrade.setVisible(true);
						stableButton.setIcon(new ImageIcon("C:/Users/mahmo/eclipse-workspace/EmpireM2/img/c.jpg"));
						if(militaryBuilding.getLevel()==3) {
							stableupgrade.setVisible(false);
							stablerecruit.setVisible(true);
							stableupgrade.addActionListener(null);
							stablerecruit.setText("Recruit Archer,Level 3,700$");
						}
						else if(militaryBuilding.getLevel()==2) {
							stableupgrade.setVisible(true);
							stablerecruit.setVisible(true);
							stableupgrade.addActionListener(this);
							stablerecruit.setText("Recruit Archer,Level 2,650$");
						}
						else if(militaryBuilding.getLevel()==1) {
							stableupgrade.setVisible(true);
							stablerecruit.setVisible(true);
							stableupgrade.addActionListener(this);
							stablerecruit.setText("Recruit Archer,Level 2,600$");
						}
					}
					if(militaryBuilding instanceof Barracks) {
						barracksButton.addActionListener(null);
						barracksupgrade.setVisible(true);
						barracksrecruit.setVisible(true);
						barracksButton.setIcon(new ImageIcon("C:/Users/mahmo/eclipse-workspace/EmpireM2/img/i.jpg"));
						if(militaryBuilding.getLevel()==3) {
							barracksupgrade.setVisible(false);
							barracksrecruit.setVisible(true);
							barracksupgrade.addActionListener(null);
							barracksrecruit.setText("Recruit Infantry,Level 3,600$");
						}
						else if(militaryBuilding.getLevel()==2) {
							barracksupgrade.setVisible(true);
							barracksrecruit.setVisible(true);
							barracksupgrade.addActionListener(this);
							barracksrecruit.setText("Recruit Infantry,Level 2,550$");
						}
						else if(militaryBuilding.getLevel()==1) {
							barracksupgrade.setVisible(true);
							barracksrecruit.setVisible(true);
							barracksupgrade.addActionListener(this);
							barracksrecruit.setText("Recruit Infantry,Level 2,500$");
						}
					}


				}
				for(EconomicBuilding economicBuilding:c.getEconomicalBuildings()) {
					if(economicBuilding instanceof Farm ) {
						farmButton.addActionListener(null);
						farmButton.setText(null);
						farmButton.setIcon(new ImageIcon("C:/Users/mahmo/eclipse-workspace/EmpireM2/img/farmimage.jpg"));
						if(economicBuilding.getLevel()==3) {
							farmupgrade.setVisible(false);
						}
						else if(economicBuilding.getLevel()==2) {
							farmupgrade.setVisible(true);
						}
						else if(economicBuilding.getLevel()==1) {
							farmupgrade.setVisible(true);
						}
					}
				}
			}
			frame2.setResizable(false);
			frame2.revalidate();
			frame2.repaint();
			frame2.setVisible(true);
		}
		if (e.getSource() == goBackButton) {
			int v=1;
			idelarmiesBox.removeAllItems();
			for (Army army : game.getPlayer().getControlledArmies()) {
				if (army.getCurrentStatus() == Status.IDLE) {
					idelarmiesBox.addItem("Army "+(v++));					
				}
			}
			for (Army army : game.getPlayer().getControlledArmies()) {
				if (army.getCurrentStatus() == Status.MARCHING) {

					idelarmiesBox.addItem("Army "+(v++));
				}
			}

			for (Army army : game.getPlayer().getControlledArmies()) {
				if (army.getCurrentStatus() == Status.BESIEGING) {
					besiegingarmiesBox.addItem("Army "+(v++));
				}
			}
			frame2.dispose();
			frame.setVisible(true);
			visit = 1;
			if(visit1!=5 && cityNow==1) visit1=1;
		}
		if (selectedcity.equals("Sparta")) {
			cairoButton.setEnabled(false);
			attackspartaButton.setEnabled(false);
			besiegespartaButton.setEnabled(false);
			targetspartaButton.setEnabled(false);
			autoresolveButton1.setEnabled(false);
			romaButton.setEnabled(false);
			cityS=0;
		}
		if (selectedcity.equals("Rome")) {
			spartaButton.setEnabled(false);
			attackromaButton.setEnabled(false);
			besiegeromaButton.setEnabled(false);
			targetromaButton.setEnabled(false);
			cairoButton.setEnabled(false);
			autoresolveButton2.setEnabled(false);
			cityR=0;
		}
		if (selectedcity.equals("Cairo")) {
			spartaButton.setEnabled(false);
			romaButton.setEnabled(false);
			attackcairoButton.setEnabled(false);
			besiegecairoButton.setEnabled(false);
			targetcairoButton.setEnabled(false);
			autoresolveButton3.setEnabled(false);
			cityC =0;
		}
		if (e.getSource() == archeryButton) {
			try {
				game.getPlayer().build("ArcheryRange",game.getPlayer().getControlledCities().get(cityNow).getName());
				archeryButton.addActionListener(null);
				archeryButton.setIcon(new ImageIcon("C:/Users/mahmo/eclipse-workspace/EmpireM2/img/a.jpg"));
				archeryupgrade.setVisible(true);
				archeryrecruit.setVisible(true);
			} catch (NotEnoughGoldException e1) {
				JOptionPane.showMessageDialog(null, "You Do not have enough Gold", "Warning",
						JOptionPane.WARNING_MESSAGE);
				e1.printStackTrace();
			}
			info.setText(player_name.getText() + "                                                                    "
					+ "Turns Count : " + game.getCurrentTurnCount()
					+ "                                                             Food : "
					+ game.getPlayer().getFood() + "                                                       Gold : "
					+ game.getPlayer().getTreasury());
		}
		if (e.getSource() == archeryupgrade) {
			try {
				ArcheryRange archeryRange = null;
				for (MilitaryBuilding militaryBuilding : city.getMilitaryBuildings()) {
					if (militaryBuilding instanceof ArcheryRange)
						archeryRange = (ArcheryRange) militaryBuilding;
				}
				game.getPlayer().upgradeBuilding(archeryRange);
				if (archeryRange.getLevel() == 2) {
					archeryupgrade.setText("ArcheryRange level 2 , Upgrade cost 700 $");
					archeryrecruit.setText("Recruit Archer Level 2, Cost 450$");
				} else if (archeryRange.getLevel() == 3) {
					archeryupgrade.setVisible(false);
					archeryrecruit.setText("Recruit Archer Level 3, Cost 500$");
				}
				info.setText(player_name.getText()
						+ "                                                                    " + "Turns Count : "
						+ game.getCurrentTurnCount()
						+ "                                                             Food : "
						+ game.getPlayer().getFood() + "                                                       Gold : "
						+ game.getPlayer().getTreasury());

			} catch (NotEnoughGoldException e1) {
				JOptionPane.showMessageDialog(null, "You Do not have enough Gold", "Warning",
						JOptionPane.WARNING_MESSAGE);
				e1.printStackTrace();

			} catch (MaxLevelException e1) {
				JOptionPane.showMessageDialog(null, "You have reached the maxlevel", "Warning",
						JOptionPane.WARNING_MESSAGE);
				e1.printStackTrace();
			} catch (BuildingInCoolDownException e1) {
				JOptionPane.showMessageDialog(null, "Building is in cooldown", "Warning", JOptionPane.WARNING_MESSAGE);
				e1.printStackTrace();
			}
		}
		if (e.getSource() == archeryrecruit) {
			try {
				game.getPlayer().recruitUnit("Archer", city.getName());
				x++;
				defendingUnitsBox.addItem("Unit "+x);
				info.setText(player_name.getText()
						+ "                                                                    " + "Turns Count : "
						+ game.getCurrentTurnCount()
						+ "                                                             Food : "
						+ game.getPlayer().getFood() + "                                                       Gold : "
						+ game.getPlayer().getTreasury());

			} catch (NotEnoughGoldException e1) {
				JOptionPane.showMessageDialog(null, "You Do not have enough Gold", "Warning",
						JOptionPane.WARNING_MESSAGE);
				e1.printStackTrace();

			} catch (BuildingInCoolDownException e1) {
				JOptionPane.showMessageDialog(null, "Building is in cooldown", "Warning", JOptionPane.WARNING_MESSAGE);
				e1.printStackTrace();
			} catch (MaxRecruitedException e1) {
				JOptionPane.showMessageDialog(null, "This building have reached maximum number of recrutied units",
						"Warning", JOptionPane.WARNING_MESSAGE);
				e1.printStackTrace();
			}
		}

		if (e.getSource() == barracksButton) {

			try {
				game.getPlayer().build("Barracks", city.getName());
				barracksButton.addActionListener(null);
				barracksupgrade.setVisible(true);
				barracksrecruit.setVisible(true);
				barracksButton.setIcon(new ImageIcon("C:/Users/mahmo/eclipse-workspace/EmpireM2/img/i.jpg"));
			} catch (NotEnoughGoldException e1) {
				JOptionPane.showMessageDialog(null, "You Do not have enough Gold", "Warning",
						JOptionPane.WARNING_MESSAGE);
				e1.printStackTrace();
			}
			info.setText(player_name.getText() + "                                                                    "
					+ "Turns Count : " + game.getCurrentTurnCount()
					+ "                                                             Food : "
					+ game.getPlayer().getFood() + "                                                       Gold : "
					+ game.getPlayer().getTreasury());
		}
		if (e.getSource() == barracksupgrade) {
			try {
				Barracks barracks = null;
				for (MilitaryBuilding militaryBuilding : city.getMilitaryBuildings()) {
					if (militaryBuilding instanceof Barracks)
						barracks = (Barracks) militaryBuilding;
				}
				game.getPlayer().upgradeBuilding(barracks);
				if (barracks.getLevel() == 2) {
					barracksupgrade.setText("Barracks level 2 , Upgrade cost 1500 $");
					barracksrecruit.setText("Recruit Infantry Level 2, Cost 550$");
				} else if (barracks.getLevel() == 3) {
					barracksupgrade.setVisible(false);
					barracksrecruit.setText("Recruit Infantry Level 3, Cost 600$");
				}
				info.setText(player_name.getText()
						+ "                                                                    " + "Turns Count : "
						+ game.getCurrentTurnCount()
						+ "                                                             Food : "
						+ game.getPlayer().getFood() + "                                                       Gold : "
						+ game.getPlayer().getTreasury());

			} catch (NotEnoughGoldException e1) {
				JOptionPane.showMessageDialog(null, "You Do not have enough Gold", "Warning",
						JOptionPane.WARNING_MESSAGE);
				e1.printStackTrace();

			} catch (MaxLevelException e1) {
				JOptionPane.showMessageDialog(null, "You have reached the maxlevel", "Warning",
						JOptionPane.WARNING_MESSAGE);
				e1.printStackTrace();
			} catch (BuildingInCoolDownException e1) {
				JOptionPane.showMessageDialog(null, "Building is in cooldown", "Warning", JOptionPane.WARNING_MESSAGE);
				e1.printStackTrace();
			}
		}
		if (e.getSource() == barracksrecruit)
		{ game.getPlayer().getControlledCities().get(cityNow);
			try {
				game.getPlayer().recruitUnit("Infantry", city.getName());
				x++;
				defendingUnitsBox.addItem("Unit "+x);
				info.setText(player_name.getText()
						+ "                                                                    " + "Turns Count : "
						+ game.getCurrentTurnCount()
						+ "                                                             Food : "
						+ game.getPlayer().getFood() + "                                                       Gold : "
						+ game.getPlayer().getTreasury());

			} catch (NotEnoughGoldException e1) {
				JOptionPane.showMessageDialog(null, "You Do not have enough Gold", "Warning",
						JOptionPane.WARNING_MESSAGE);
				e1.printStackTrace();

			} catch (BuildingInCoolDownException e1) {
				JOptionPane.showMessageDialog(null, "Building is in cooldown", "Warning", JOptionPane.WARNING_MESSAGE);
				e1.printStackTrace();
			} catch (MaxRecruitedException e1) {
				JOptionPane.showMessageDialog(null, "This building have reached maximum number of recrutied units",
						"Warning", JOptionPane.WARNING_MESSAGE);
				e1.printStackTrace();
			}
		}
		if (e.getSource() == stableButton) {
			try {
				game.getPlayer().build("Stable", city.getName());
				stableButton.addActionListener(null);
				stablerecruit.setVisible(true);
				stableupgrade.setVisible(true);
				stableButton.setIcon(new ImageIcon("C:/Users/mahmo/eclipse-workspace/EmpireM2/img/c.jpg"));
			} catch (NotEnoughGoldException e1) {
				JOptionPane.showMessageDialog(null, "You Do not have enough Gold", "Warning",
						JOptionPane.WARNING_MESSAGE);
				e1.printStackTrace();
			}
			info.setText(player_name.getText() + "                                                                    "
					+ "Turns Count : " + game.getCurrentTurnCount()
					+ "                                                             Food : "
					+ game.getPlayer().getFood() + "                                                       Gold : "
					+ game.getPlayer().getTreasury());
		}
		if (e.getSource() == stableupgrade) {
			try {
				Stable stable = null;
				for (MilitaryBuilding militaryBuilding : city.getMilitaryBuildings()) {
					if (militaryBuilding instanceof Stable)
						stable = (Stable) militaryBuilding;
				}
				game.getPlayer().upgradeBuilding(stable);
				if (stable.getLevel() == 2) {
					stableupgrade.setText("Stable level 2 , Upgrade cost 2000 $");
					stablerecruit.setText("Recruit Cavalry Level 3, Cost 650$");
				} else if (stable.getLevel() == 3) {
					stableupgrade.setVisible(false);
					stablerecruit.setText("Recruit Cavalry Level 3, Cost 700$");
				}
				info.setText(player_name.getText()
						+ "                                                                    " + "Turns Count : "
						+ game.getCurrentTurnCount()
						+ "                                                             Food : "
						+ game.getPlayer().getFood() + "                                                       Gold : "
						+ game.getPlayer().getTreasury());

			} catch (NotEnoughGoldException e1) {
				JOptionPane.showMessageDialog(null, "You Do not have enough Gold", "Warning",
						JOptionPane.WARNING_MESSAGE);
				e1.printStackTrace();

			} catch (MaxLevelException e1) {
				JOptionPane.showMessageDialog(null, "You have reached the maxlevel", "Warning",
						JOptionPane.WARNING_MESSAGE);
				e1.printStackTrace();
			} catch (BuildingInCoolDownException e1) {
				JOptionPane.showMessageDialog(null, "Building is in cooldown", "Warning", JOptionPane.WARNING_MESSAGE);
				e1.printStackTrace();
			}
		}
		if (e.getSource() == stablerecruit) {
			try {
				game.getPlayer().recruitUnit("Cavalry", city.getName());
				x++;
				defendingUnitsBox.addItem("Unit "+x);
				info.setText(player_name.getText()
						+ "                                                                    " + "Turns Count : "
						+ game.getCurrentTurnCount()
						+ "                                                             Food : "
						+ game.getPlayer().getFood() + "                                                       Gold : "
						+ game.getPlayer().getTreasury());

			} catch (NotEnoughGoldException e1) {
				JOptionPane.showMessageDialog(null, "You Do not have enough Gold", "Warning",
						JOptionPane.WARNING_MESSAGE);
				e1.printStackTrace();

			} catch (BuildingInCoolDownException e1) {
				JOptionPane.showMessageDialog(null, "Building is in cooldown", "Warning", JOptionPane.WARNING_MESSAGE);
				e1.printStackTrace();
			} catch (MaxRecruitedException e1) {
				JOptionPane.showMessageDialog(null, "This building have reached maximum number of recrutied units",
						"Warning", JOptionPane.WARNING_MESSAGE);
				e1.printStackTrace();
			}
		}
		if (e.getSource() == farmButton) {
			try {
				game.getPlayer().build("Farm", city.getName());
				farmButton.addActionListener(null);
				farmButton.setText(null);
				farmButton.setIcon(new ImageIcon("C:/Users/mahmo/eclipse-workspace/EmpireM2/img/farmimage.jpg"));
				info.setText(player_name.getText()
						+ "                                                                    " + "Turns Count : "
						+ game.getCurrentTurnCount()
						+ "                                                             Food : "
						+ game.getPlayer().getFood() + "                                                       Gold : "
						+ game.getPlayer().getTreasury());
				farmupgrade.setVisible(true);
			} catch (NotEnoughGoldException e1) {
				JOptionPane.showMessageDialog(null, "You Do not have enough Gold", "Warning",
						JOptionPane.WARNING_MESSAGE);
				e1.printStackTrace();
			}

		}
		if (e.getSource() == farmupgrade) {
			try {
				Farm farm = null;
				for (EconomicBuilding economicBuilding : city.getEconomicalBuildings()) {
					if (economicBuilding instanceof Farm)
						farm = (Farm) economicBuilding;
				}
				game.getPlayer().upgradeBuilding(farm);
				if (farm.getLevel() == 2)
					farmupgrade.setText("Farm level 2 , Upgrade cost 700 $");
				else if (farm.getLevel() == 3)
					farmupgrade.setVisible(false);
				info.setText(player_name.getText()
						+ "                                                                    " + "Turns Count : "
						+ game.getCurrentTurnCount()
						+ "                                                             Food : "
						+ game.getPlayer().getFood() + "                                                       Gold : "
						+ game.getPlayer().getTreasury());

			} catch (NotEnoughGoldException e1) {
				JOptionPane.showMessageDialog(null, "You Do not have enough Gold", "Warning",
						JOptionPane.WARNING_MESSAGE);
				e1.printStackTrace();

			} catch (MaxLevelException e1) {
				JOptionPane.showMessageDialog(null, "You have reached the maxlevel", "Warning",
						JOptionPane.WARNING_MESSAGE);
				e1.printStackTrace();
			} catch (BuildingInCoolDownException e1) {
				JOptionPane.showMessageDialog(null, "Building is in cooldown", "Warning", JOptionPane.WARNING_MESSAGE);
				e1.printStackTrace();
			}
		}
		if (e.getSource() == marketButton) {

			try {
				game.getPlayer().build("market", city.getName());
				marketButton.removeActionListener(this);
				marketupgrade.setVisible(true);
				marketButton.setText(null);
				marketButton.setIcon(new ImageIcon("C:/Users/mahmo/eclipse-workspace/EmpireM2/img/marketim.jpg"));
			} catch (NotEnoughGoldException e1) {
				JOptionPane.showMessageDialog(null, "You Do not have enough Gold", "Warning",
						JOptionPane.WARNING_MESSAGE);
				e1.printStackTrace();
			}
			info.setText(player_name.getText() + "                                                                    "
					+ "Turns Count : " + game.getCurrentTurnCount()
					+ "                                                             Food : "
					+ game.getPlayer().getFood() + "                                                       Gold : "
					+ game.getPlayer().getTreasury());
		}
		if (e.getSource() == marketupgrade) {
			try {
				Market market = null;
				for (EconomicBuilding economicBuilding : city.getEconomicalBuildings()) {
					if (economicBuilding instanceof Market)
						market = (Market) economicBuilding;
				}
				game.getPlayer().upgradeBuilding(market);
				if (market.getLevel() == 2)
					marketupgrade.setText("Farm level 2 , Upgrade cost 700 $");
				else if (market.getLevel() == 3)
					marketupgrade.setVisible(false);
				info.setText(player_name.getText()
						+ "                                                                    " + "Turns Count : "
						+ game.getCurrentTurnCount()
						+ "                                                             Food : "
						+ game.getPlayer().getFood() + "                                                       Gold : "
						+ game.getPlayer().getTreasury());

			} catch (NotEnoughGoldException e1) {
				JOptionPane.showMessageDialog(null, "You Do not have enough Gold", "Warning",JOptionPane.WARNING_MESSAGE);
				e1.printStackTrace();

			} catch (MaxLevelException e1) {
				JOptionPane.showMessageDialog(null, "You have reached the maxlevel", "Warning",
						JOptionPane.WARNING_MESSAGE);
				e1.printStackTrace();
			} catch (BuildingInCoolDownException e1) {
				JOptionPane.showMessageDialog(null, "Building is in cooldown", "Warning", JOptionPane.WARNING_MESSAGE);
				e1.printStackTrace();
			}
		}
		if (e.getSource() == defendingarmyButton) {
			
			for(int j =1; j<(game.getPlayer().getControlledArmies().size()+1);j++) {
				chooseArmy1Box.addItem("Army "+j);
				chooseArmy2Box.addItem("Army "+j);
			}
			armyArea.setText(game.getPlayer().getText(cityNow));
			frame2.setVisible(false);
			defendingarmyFrame.setVisible(true);
			defendingarmyFrame.add(defendingview, BorderLayout.NORTH);
			defendingarmyFrame.add(goback, BorderLayout.SOUTH);
			defendingarmyFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			defendingarmyFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);								

		}
//		if (e.getSource() == defendingarmyButton) {
//			frame2.setVisible(false);
//			defendingarmyFrame.setVisible(true);
//			if (frame.isVisible())
//				frame.setVisible(false);
//			Unit unit = null;
//			archerButtoninfo.setText("Unit Type : Archer" + " , Unit Level : ");
//			defendingview.add(archerButtoninfo);
//			defendingview.add(infantryButtoninfo);
//			defendingview.add(cavalryButtoninfo);
//			defendingarmyFrame.add(defendingview, BorderLayout.CENTER);
//			defendingarmyFrame.add(goback, BorderLayout.SOUTH);
//			defendingarmyFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//			defendingarmyFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
//		}
		if (e.getSource() == GoBackToCity) {
			defendingarmyFrame.setVisible(false);
			frame2.setVisible(true);
		}
		if (e.getSource() == cityarmyButton) {
			frame2.setVisible(false);
//			chooseUnitBox.revalidate();
//			chooseUnitBox.repaint();
			defendingarmyFrame.setVisible(true);
			if (frame.isVisible())
				frame.setVisible(false);
			defendingarmyFrame.add(defendingview, BorderLayout.CENTER);
			defendingarmyFrame.add(goback, BorderLayout.SOUTH);
			defendingarmyFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			defendingarmyFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		}
		if (e.getSource() == initiateArmyButton && !((String)(defendingUnitsBox.getSelectedItem())).equals("Select Unit From Defending Army") && game.getPlayer().getControlledCities().get(cityNow).getDefendingArmy().getUnits().size()!=1) {
			
			String string=(String)(defendingUnitsBox.getSelectedItem());
			int idx =-1;
			for (int i = 0; i < string.length(); i++) {
				if(Character.isDigit(string.charAt((string.length())-1-i))) {
					idx +=Character.getNumericValue((string.charAt((string.length())-1-i)))*Math.pow(10,i);
				} else break;
			}
			int x=game.getPlayer().getControlledCities().get(cityNow).getDefendingArmy().getUnits().size();
			if(idx<x) {
				game.getPlayer().initiateArmy(game.getPlayer().getControlledCities().get(cityNow),game.getPlayer().getControlledCities().get(cityNow).getDefendingArmy().getUnits().get(idx));
				chooseArmy1Box.removeAllItems();
				chooseArmy2Box.removeAllItems();
				chooseArmy1Box.addItem("Select Army To Relocate Unit From");
				chooseArmy2Box.addItem("Select Army To Relocate Unit To");
				chooseArmy1Box.addItem("Defending Army");
				chooseArmy2Box.addItem("Defending Army");
				for(int j =1; j<(game.getPlayer().getControlledArmies().size()+1);j++) {
					chooseArmy1Box.addItem("Army "+j);
					chooseArmy2Box.addItem("Army "+j);
				}
				armyArea.setText(game.getPlayer().getText(cityNow));
			}
			
			
			
		}
		if(e.getSource()==relocateButton && !((String)(chooseArmy1Box.getSelectedItem())).equals("Select Army To Relocate Unit From") && !((String)(chooseArmy2Box.getSelectedItem())).equals("Select Army To Relocate Unit To") && !((String)(chooseUnitBox.getSelectedItem())).equals("Select Unit To Relocate") ) {
			String string1=(String)(chooseArmy1Box.getSelectedItem());
			String string2=(String)(chooseArmy2Box.getSelectedItem());
			Boolean c=false;
			int idx1 =-1;
			int idx2 =-1;
			for (int i = 0; i < string1.length(); i++) {
				if(Character.isDigit(string1.charAt((string1.length())-1-i))) {
					idx1 +=Character.getNumericValue((string1.charAt((string1.length())-1-i)))*Math.pow(10,i);
				} else break;
			}
			for (int i = 0; i < string2.length(); i++) {
				if(Character.isDigit(string2.charAt((string2.length())-1-i))) {
					idx2 +=Character.getNumericValue((string2.charAt((string2.length())-1-i)))*Math.pow(10,i);
				} else break;
			}
			String string=(String)(defendingUnitsBox.getSelectedItem());
			char idx3 =string.charAt((string.length())-1);
			int idx=Character.getNumericValue(idx3);
			if(idx==0) idx=9;
			else idx-=1;
			Army army1,army2;
			if(idx1==-1) { 
				army1=game.getPlayer().getControlledCities().get(cityNow).getDefendingArmy();
				c=true;
			}else army1=game.getPlayer().getControlledArmies().get(idx1);
			if(idx2==-1) army2=game.getPlayer().getControlledCities().get(cityNow).getDefendingArmy();
			else army2=game.getPlayer().getControlledArmies().get(idx2);
			if(idx<army1.getUnits().size()&&!army1.equals(army2)) {
				try {
					army2.relocateUnit(army1.getUnits().get(idx));
					if(c) {
						defendingUnitsBox.removeItemAt(defendingUnitsBox.getItemCount() - 1);
					}
				} catch (MaxCapacityException e1) {
					JOptionPane.showMessageDialog(null, "Army At Maximum Capacity", "Warning", JOptionPane.WARNING_MESSAGE);
					e1.printStackTrace();
				}
			}
			armyArea.setText(game.getPlayer().getText(cityNow));
		}
		if (e.getSource() == GetControlledArmies) {
			frame.setVisible(false);
			idleArmies.setText("idleArmies");
			String h="";
			int v=1;
			for (int i=0;i<game.getPlayer().getControlledArmies().size();i++) {
				Army army=game.getPlayer().getControlledArmies().get(i);
				if (army.getCurrentStatus() == Status.IDLE) {
					h+="Army "+i;
					//idelarmiesBox.addItem("Idle Army "+(v++));
					//idleArmies.add(idelarmiesBox);
					for(int j=0;j<army.getUnits().size();) {
						String c="";
						String type="";
						Unit unit= army.getUnits().get(j);
						if(unit instanceof Archer) type="Archer";
						else if(unit instanceof Cavalry) type="Cavalry";
						else type="Infantry";
						c="(Unit "+ (++j)+" :- Type : "+type+", Level : "+unit.getLevel()+", Current Solider Count : "+ unit.getCurrentSoldierCount()+", Max Soldier Count : "+ unit.getMaxSoldierCount()+") ";
						h+=c;
					}
					h+="\r\n";
					
				}
			}
			idleArmies.setText("Idle Armies"+"\r\n"+h);
			h="";
			for (int i=0;i<game.getPlayer().getControlledArmies().size();i++) {
				Army army=game.getPlayer().getControlledArmies().get(i);
				if (army.getCurrentStatus() == Status.MARCHING) {
					h+="Army "+i;
					//marchingarmiesBox.addItem("Army "+(v++));
					for(int j=0;j<army.getUnits().size();) {
						String c="";
						String type="";
						Unit unit= army.getUnits().get(j);
						if(unit instanceof Archer) type="Archer";
						else if(unit instanceof Cavalry) type="Cavalry";
						else type="Infantry";
						c="(Unit "+ (++j)+" :- Type : "+type+", Level : "+unit.getLevel()+", Current Solider Count : "+ unit.getCurrentSoldierCount()+", Max Soldier Count : "+ unit.getMaxSoldierCount()+") ";
						h+=c;
					}
					h+="\r\n" +"Target city"+ army.getTarget()+"turns till reaching the target " +army.getDistancetoTarget();
					
				}
			}
			marchingArmies.setText("Marching Army"+"\r\n"+h);
			h="";
			for (int i=0;i<game.getPlayer().getControlledArmies().size();i++) {
				Army army=game.getPlayer().getControlledArmies().get(i);
				if (army.getCurrentStatus() == Status.BESIEGING) {
					h+="Army "+i;
					//besiegingarmiesBox.addItem("Army "+(v++));
					for(int j=0;j<army.getUnits().size();) {
						String c="";
						String type="";
						Unit unit= army.getUnits().get(j);
						if(unit instanceof Archer) type="Archer";
						else if(unit instanceof Cavalry) type="Cavalry";
						else type="Infantry";
						c="(Unit "+ (++j)+" :- Type : "+type+", Level : "+unit.getLevel()+", Current Solider Count : "+ unit.getCurrentSoldierCount()+", Max Soldier Count : "+ unit.getMaxSoldierCount()+") ";
						h+=c;
					}
					City yCity=null;
					for(City c:game.getAvailableCities()) {
						if(c.getName().equals(army.getCurrentLocation())) yCity=c;
					}
					h+="\r\n"+"Besieged City "+army.getCurrentLocation()+" turns under siege "+ yCity.getTurnsUnderSiege()+"\r\n";
					
				}
			}
			
			controledArmiesPanel.add(idleArmies);

			controledArmiesPanel.add(marchingArmies);
			besiegingArmies.setText("Besieging Armies"+"\r\n"+h);
			controledArmiesPanel.add(besiegingArmies);
			armycontrolledview.add(controledArmiesPanel, BorderLayout.CENTER);
			armycontrolledview.add(armyPanel, BorderLayout.SOUTH);
			armycontrolledview.setVisible(true);
			armycontrolledview.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			armycontrolledview.setResizable(false);
			armycontrolledview.setExtendedState(JFrame.MAXIMIZED_BOTH);
		}
		if (e.getSource() == gotoavailablecities) {
			armycontrolledview.setVisible(false);
			frame.setVisible(true);
		}
		if(e.getSource()==besiegecairoButton) {
			City city1=null; 
			for(City c :game.getAvailableCities()) {
				if(c.getName().toLowerCase()=="cairo") city1=c;
			}
			String g=(String)idelarmiesBox.getSelectedItem();
			Army army=null;
			if(g.charAt(0)=='A') {
				int idx =-1;
				for (int i = 0; i < g.length(); i++) {
					if(Character.isDigit(g.charAt((g.length())-1-i))) {
						idx +=Character.getNumericValue((g.charAt((g.length())-1-i)))*Math.pow(10,i);
					} else break;
				}
				army=game.getPlayer().getControlledArmies().get(idx);
				
			}
			try {
				game.getPlayer().laySiege(army, city1);
			} catch (TargetNotReachedException e1) {
				JOptionPane.showMessageDialog(null, "Target Has Not Been Reached Yet", "Warning",JOptionPane.WARNING_MESSAGE);
				e1.printStackTrace();
			} catch (FriendlyCityException e1) {
				JOptionPane.showMessageDialog(null, "Friendly City Cannot Besiege", "Warning",JOptionPane.WARNING_MESSAGE);
				e1.printStackTrace();
			}
		}
		if(e.getSource()==besiegespartaButton) {
			City city1=null; 
			for(City c :game.getAvailableCities()) {
				if(c.getName().toLowerCase()=="sparta") city1=c;
			}
			String g=(String)idelarmiesBox.getSelectedItem();
			Army army=null;
			if(g.charAt(0)=='A') {
				int idx =-1;
				for (int i = 0; i < g.length(); i++) {
					if(Character.isDigit(g.charAt((g.length())-1-i))) {
						idx +=Character.getNumericValue((g.charAt((g.length())-1-i)))*Math.pow(10,i);
					} else break;
				}
				army=game.getPlayer().getControlledArmies().get(idx);
				
			}
			try {
				game.getPlayer().laySiege(army, city1);
			} catch (TargetNotReachedException e1) {
				JOptionPane.showMessageDialog(null, "Target Has Not Been Reached Yet", "Warning",JOptionPane.WARNING_MESSAGE);
				e1.printStackTrace();
			} catch (FriendlyCityException e1) {
				JOptionPane.showMessageDialog(null, "Friendly City Cannot Besiege", "Warning",JOptionPane.WARNING_MESSAGE);
				e1.printStackTrace();
			}
		}
		if(e.getSource()==besiegeromaButton) {
			City city1=null; 
			for(City c :game.getAvailableCities()) {
				if(c.getName().toLowerCase()=="rome") city1=c;
			}
			String g=(String)idelarmiesBox.getSelectedItem();
			Army army=null;
			if(g.charAt(0)=='A') {
				int idx =-1;
				for (int i = 0; i < g.length(); i++) {
					if(Character.isDigit(g.charAt((g.length())-1-i))) {
						idx +=Character.getNumericValue((g.charAt((g.length())-1-i)))*Math.pow(10,i);
					} else break;
				}
				army=game.getPlayer().getControlledArmies().get(idx);
				
			}
			if(army!=null) {
			try {
				game.getPlayer().laySiege(army, city1);
			} catch (TargetNotReachedException e1) {
				JOptionPane.showMessageDialog(null, "Target Has Not Been Reached Yet", "Warning",JOptionPane.WARNING_MESSAGE);
				e1.printStackTrace();
			} catch (FriendlyCityException e1) {
				JOptionPane.showMessageDialog(null, "Friendly City Cannot Besiege", "Warning",JOptionPane.WARNING_MESSAGE);
				e1.printStackTrace();
			}
			}
			}
			if(e.getSource()==targetcairoButton) {
				City city1=null; 
				for(City c :game.getAvailableCities()) {
					if(c.getName().toLowerCase().equals("cairo")) city1=c;
				}
				String g=(String)idelarmiesBox.getSelectedItem();
				Army army=null;
				if(g.charAt(0)=='A') {
					int idx =-1;
					for (int i = 0; i < g.length(); i++) {
						if(Character.isDigit(g.charAt((g.length())-1-i))) {
							idx +=Character.getNumericValue((g.charAt((g.length())-1-i)))*Math.pow(10,i);
						} else break;
					}
					army=game.getPlayer().getControlledArmies().get(idx);
					
				}
				if(army!=null && army.getCurrentStatus()==Status.IDLE) {
					game.targetCity(army,"Cairo");
				}
			}
			if(e.getSource()==targetromaButton) {
				City city1=null; 
				for(City c :game.getAvailableCities()) {
					if(c.getName().toLowerCase().equals("rome")) city1=c;
				}
				String g=(String)idelarmiesBox.getSelectedItem();
				Army army=null;
				if(g.charAt(0)=='A') {
					int idx =-1;
					for (int i = 0; i < g.length(); i++) {
						if(Character.isDigit(g.charAt((g.length())-1-i))) {
							idx +=Character.getNumericValue((g.charAt((g.length())-1-i)))*Math.pow(10,i);
						} else break;
					}
					army=game.getPlayer().getControlledArmies().get(idx);
					
				}
				if(army!=null && army.getCurrentStatus()==Status.IDLE) {
					game.targetCity(army,"Rome");
				}
			}
			if(e.getSource()==targetspartaButton) {
				City city1=null; 
				for(City c :game.getAvailableCities()) {
					if(c.getName().toLowerCase().equals("sparta")) city1=c;
				}
				String g=(String)idelarmiesBox.getSelectedItem();
				Army army=null;
				if(g.charAt(0)=='A') {
					int idx =-1;
					for (int i = 0; i < g.length(); i++) {
						if(Character.isDigit(g.charAt((g.length())-1-i))) {
							idx +=Character.getNumericValue((g.charAt((g.length())-1-i)))*Math.pow(10,i);
						} else break;
					}
					army=game.getPlayer().getControlledArmies().get(idx);
					
				}
				if(army!=null && army.getCurrentStatus()==Status.IDLE) {
					game.targetCity(army,"Sparta");
				}
			}
			if (e.getSource() == attackcairoButton) {
				cd="cairo";
				attackFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				attackFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
				attackFrame.setResizable(false);
				attackFrame.setVisible(true);
				attackInfo.setEditable(false);
				String g=(String)idelarmiesBox.getSelectedItem();
				Army army=null;
				if(g.charAt(0)=='A') {
					int idx =-1;
				for (int i = 0; i < g.length(); i++) {
						if(Character.isDigit(g.charAt((g.length())-1-i))) {
							idx +=Character.getNumericValue((g.charAt((g.length())-1-i)))*Math.pow(10,i);
						} else break;
					}
					army=game.getPlayer().getControlledArmies().get(idx);
					
				}
				for(int i=0;i<army.getUnits().size();i++) {
					attackUnit.addItem("Unit "+i);
				}
				City attackedCity=null;
				for(City c :game.getAvailableCities()) {
					if(c.getName().toLowerCase().equals("cairo")) attackedCity=c;
				}
				for(int i=0;i<attackedCity.getDefendingArmy().getUnits().size();i++) {
					targetUnit.addItem("Unit "+i);
				}
//				int idx3 =-1;
//				int idx4 =-1;
//				
//				String string1=(String)attackUnit.getSelectedItem(); 
//				String string2=(String)targetUnit.getSelectedItem();
//				for (int i = 0; i < string1.length(); i++) {
//					if(Character.isDigit(string1.charAt((string1.length())-1-i))) {
//						idx3 +=Character.getNumericValue((string1.charAt((string1.length())-1-i)))*Math.pow(10,i);
//					} else break;
//				}
//				for (int i = 0; i < string2.length(); i++) {
//					if(Character.isDigit(string2.charAt((string2.length())-1-i))) {
//						idx4 +=Character.getNumericValue((string2.charAt((string2.length())-1-i)))*Math.pow(10,i);
//					} else break;
//				}
			}
			if (e.getSource() == attackspartaButton) {
				cd="sparta";
				attackFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				attackFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
				attackFrame.setResizable(false);
				attackFrame.setVisible(true);
				attackInfo.setEditable(false);
				String g=(String)idelarmiesBox.getSelectedItem();
				Army army=null;
				if(g.charAt(0)=='A') {
					int idx =-1;
				for (int i = 0; i < g.length(); i++) {
						if(Character.isDigit(g.charAt((g.length())-1-i))) {
							idx +=Character.getNumericValue((g.charAt((g.length())-1-i)))*Math.pow(10,i);
						} else break;
					}
					army=game.getPlayer().getControlledArmies().get(idx);
					
				}
				for(int i=0;i<army.getUnits().size();i++) {
					attackUnit.addItem("Unit "+i);
				}
				City attackedCity=null;
				for(City c :game.getAvailableCities()) {
					if(c.getName().toLowerCase().equals("sparta")) attackedCity=c;
				}
				for(int i=0;i<attackedCity.getDefendingArmy().getUnits().size();i++) {
					targetUnit.addItem("Unit "+i);
				}
//				int idx3 =-1;
//				int idx4 =-1;
//				
//				String string1=(String)attackUnit.getSelectedItem(); 
//				String string2=(String)targetUnit.getSelectedItem();
//				for (int i = 0; i < string1.length(); i++) {
//					if(Character.isDigit(string1.charAt((string1.length())-1-i))) {
//						idx3 +=Character.getNumericValue((string1.charAt((string1.length())-1-i)))*Math.pow(10,i);
//					} else break;
//				}
//				for (int i = 0; i < string2.length(); i++) {
//					if(Character.isDigit(string2.charAt((string2.length())-1-i))) {
//						idx4 +=Character.getNumericValue((string2.charAt((string2.length())-1-i)))*Math.pow(10,i);
//					} else break;
//				}
			}
			if (e.getSource() == attackromaButton) {
				cd="rome";
				attackFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				attackFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
				attackFrame.setResizable(false);
				attackFrame.setVisible(true);
				attackInfo.setEditable(false);
				String g=(String)idelarmiesBox.getSelectedItem();
				Army army=null;
				if(g.charAt(0)=='A') {
					int idx =-1;
				for (int i = 0; i < g.length(); i++) {
						if(Character.isDigit(g.charAt((g.length())-1-i))) {
							idx +=Character.getNumericValue((g.charAt((g.length())-1-i)))*Math.pow(10,i);
						} else break;
					}
					army=game.getPlayer().getControlledArmies().get(idx);
					
				}
				for(int i=0;i<army.getUnits().size();i++) {
					attackUnit.addItem("Unit "+i);
				}
				City attackedCity=null;
				for(City c :game.getAvailableCities()) {
					if(c.getName().toLowerCase().equals("rome")) attackedCity=c;
				}
				for(int i=0;i<attackedCity.getDefendingArmy().getUnits().size();i++) {
					targetUnit.addItem("Unit "+i);
				}
//				int idx3 =-1;
//				int idx4 =-1;
//				
//				String string1=(String)attackUnit.getSelectedItem(); 
//				String string2=(String)targetUnit.getSelectedItem();
//				for (int i = 0; i < string1.length(); i++) {
//					if(Character.isDigit(string1.charAt((string1.length())-1-i))) {
//						idx3 +=Character.getNumericValue((string1.charAt((string1.length())-1-i)))*Math.pow(10,i);
//					} else break;
//				}
//				for (int i = 0; i < string2.length(); i++) {
//					if(Character.isDigit(string2.charAt((string2.length())-1-i))) {
//						idx4 +=Character.getNumericValue((string2.charAt((string2.length())-1-i)))*Math.pow(10,i);
//					} else break;
//				}
			}
			if(e.getSource()==attackButton) {
				
				String g=(String)idelarmiesBox.getSelectedItem();
				if(g!=null) {
				Army army=null;
				if(g.charAt(0)=='A') {
					int idx =-1;
					for (int i = 0; i < g.length(); i++) {
						if(Character.isDigit(g.charAt((g.length())-1-i))) {
							idx +=Character.getNumericValue((g.charAt((g.length())-1-i)))*Math.pow(10,i);
						} else break;
					}
					army=game.getPlayer().getControlledArmies().get(idx);
					
				}
				for(int i=0;i<army.getUnits().size();i++) {
					attackUnit.addItem("Unit "+i);
				}
				City attackedCity=null;
				for(City c :game.getAvailableCities()) {
					if(c.getName().toLowerCase().equals(cd)) attackedCity=c;
				}
				for(int i=0;i<attackedCity.getDefendingArmy().getUnits().size();i++) {
					targetUnit.addItem("Unit "+i);
				}
				int idx3 =-1;
				int idx4 =-1;
				
				String string1=(String)attackUnit.getSelectedItem(); 
				String string2=(String)targetUnit.getSelectedItem();
				for (int i = 0; i < string1.length(); i++) {
					if(Character.isDigit(string1.charAt((string1.length())-1-i))) {
						idx3 +=Character.getNumericValue((string1.charAt((string1.length())-1-i)))*Math.pow(10,i);
					} else break;
				}
				for (int i = 0; i < string2.length(); i++) {
					if(Character.isDigit(string2.charAt((string2.length())-1-i))) {
						idx4 +=Character.getNumericValue((string2.charAt((string2.length())-1-i)))*Math.pow(10,i);
					} else break;
				}
				Unit unit1=army.getUnits().get(idx3);
				Unit unit2 =attackedCity.getDefendingArmy().getUnits().get(idx4);
				String actionString="Before "+ "Attacking Unit "+idx3+ unit1.getCurrentSoldierCount()+ "Attacked Unit "+ unit2.getCurrentSoldierCount();
				try {
					unit1.attack(unit2);
				} catch (FriendlyFireException e1) {
					JOptionPane.showMessageDialog(null, "Friendly Unit Cannot Attack", "Warning",JOptionPane.WARNING_MESSAGE);
					e1.printStackTrace();
				}
				actionString="After "+ "Attacking Unit "+idx3+ unit1.getCurrentSoldierCount()+ "Attacked Unit "+ unit2.getCurrentSoldierCount();
				attackInfo.setText(actionString);
				targetUnit.removeAllItems();
				for(int i=0;i<attackedCity.getDefendingArmy().getUnits().size();i++) {
					targetUnit.addItem("Unit "+i);
					
				}
				if(attackedCity.getDefendingArmy().getUnits().size()==0|| attackedCity.getDefendingArmy()==null) {
					game.getPlayer().getControlledCities().add(attackedCity);
					controlledcitiesBox.addItem(attackedCity.getName());
					if(cd.equals("sparta")){
					attackspartaButton.setEnabled(false);
					spartaButton.setEnabled(true);
					besiegespartaButton.setEnabled(false);
					autoresolveButton1.setEnabled(false);
				}
				
				}
				}
			}
			if(e.getSource()==autoresolveButton1) {
				String g=(String)idelarmiesBox.getSelectedItem();
				Army army=null;
				if(g.charAt(0)=='A') {
					int idx =-1;
				for (int i = 0; i < g.length(); i++) {
						if(Character.isDigit(g.charAt((g.length())-1-i))) {
							idx +=Character.getNumericValue((g.charAt((g.length())-1-i)))*Math.pow(10,i);
						} else break;
					}
					army=game.getPlayer().getControlledArmies().get(idx);
					
				}
				for(int i=0;i<army.getUnits().size();i++) {
					attackUnit.addItem("Unit "+i);
				}
				City attackedCity=null;
				for(City c :game.getAvailableCities()) {
					if(c.getName().toLowerCase().equals("sparta")) attackedCity=c;
				}
				for(int i=0;i<attackedCity.getDefendingArmy().getUnits().size();i++) {
					targetUnit.addItem("Unit "+i);
				}
				if(attackedCity.getDefendingArmy().getUnits().size()==0|| attackedCity.getDefendingArmy()==null) {
					game.getPlayer().getControlledCities().add(attackedCity);
					controlledcitiesBox.addItem(attackedCity.getName());
					attackspartaButton.setEnabled(false);
					spartaButton.setEnabled(true);
					besiegespartaButton.setEnabled(false);
					autoresolveButton1.setEnabled(false);
					
				}
				try {
					game.autoResolve(army,attackedCity.getDefendingArmy());
					if(attackedCity.getDefendingArmy().getUnits().size()==0|| attackedCity.getDefendingArmy()==null) {
						game.getPlayer().getControlledCities().add(attackedCity);
						controlledcitiesBox.addItem(attackedCity.getName());
						attackspartaButton.setEnabled(false);
						spartaButton.setEnabled(true);
						besiegespartaButton.setEnabled(false);
						autoresolveButton1.setEnabled(false);
						
					}
				} catch (FriendlyFireException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			if(e.getSource()==autoresolveButton3) {
				String g=(String)idelarmiesBox.getSelectedItem();
				Army army=null;
				if(g.charAt(0)=='A') {
					int idx =-1;
				for (int i = 0; i < g.length(); i++) {
						if(Character.isDigit(g.charAt((g.length())-1-i))) {
							idx +=Character.getNumericValue((g.charAt((g.length())-1-i)))*Math.pow(10,i);
						} else break;
					}
					army=game.getPlayer().getControlledArmies().get(idx);
					
				}
				for(int i=0;i<army.getUnits().size();i++) {
					attackUnit.addItem("Unit "+i);
				}
				City attackedCity=null;
				for(City c :game.getAvailableCities()) {
					if(c.getName().toLowerCase().equals("cairo")) attackedCity=c;
				}
				for(int i=0;i<attackedCity.getDefendingArmy().getUnits().size();i++) {
					targetUnit.addItem("Unit "+i);
				}
				try {
					game.autoResolve(army,attackedCity.getDefendingArmy());
				} catch (FriendlyFireException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
			if(e.getSource()==autoresolveButton2) {
				String g=(String)idelarmiesBox.getSelectedItem();
				Army army=null;
				if(g.charAt(0)=='A') {
					int idx =-1;
				for (int i = 0; i < g.length(); i++) {
						if(Character.isDigit(g.charAt((g.length())-1-i))) {
							idx +=Character.getNumericValue((g.charAt((g.length())-1-i)))*Math.pow(10,i);
						} else break;
					}
					army=game.getPlayer().getControlledArmies().get(idx);
					
				}
				for(int i=0;i<army.getUnits().size();i++) {
					attackUnit.addItem("Unit "+i);
				}
				City attackedCity=null;
				for(City c :game.getAvailableCities()) {
					if(c.getName().toLowerCase().equals("rome")) attackedCity=c;
				}
				for(int i=0;i<attackedCity.getDefendingArmy().getUnits().size();i++) {
					targetUnit.addItem("Unit "+i);
				}
				try {
					game.autoResolve(army,attackedCity.getDefendingArmy());
				} catch (FriendlyFireException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
	}
	
	
	
}
