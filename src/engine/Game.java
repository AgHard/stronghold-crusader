package engine;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import buildings.EconomicBuilding;
import buildings.Farm;
import buildings.Market;
import buildings.MilitaryBuilding;
import exceptions.FriendlyFireException;
import units.Archer;
import units.Army;
import units.Cavalry;
import units.Infantry;
import units.Status;
import units.Unit;

public class Game {
	private Player player;
	private ArrayList<City> availableCities;
	private ArrayList<Distance> distances;
	private final int maxTurnCount = 50;
	private int currentTurnCount;
	private JFrame frame;
	private Canvas canvas; // draw pictures to see it in frame
	private String title;
	private int width, height;

	public Game(String title, int width, int height) {
		this.title = title;
		this.width = width;
		this.height = height;
	}

	public Game(String playerName, String playerCity) throws IOException {

		player = new Player(playerName);
		player.setTreasury(5000);
		availableCities = new ArrayList<City>();
		distances = new ArrayList<Distance>();
		currentTurnCount = 1;
		loadCitiesAndDistances();
		for (City c : availableCities) {
			if (c.getName().equals(playerCity)) {
				player.getControlledCities().add(c);
			}
		}
		if (playerCity.toLowerCase().equals("cairo")) {
			loadArmy("Rome", "rome_army.csv");
			loadArmy("Sparta", "sparta_army.csv");
		} else if (playerCity.toLowerCase().equals("rome")) {
			loadArmy("Cairo", "cairo_army.csv");
			loadArmy("sparta", "sparta_army.csv");
		} else {
			loadArmy("Rome", "rome_army.csv");
			loadArmy("Cairo", "cairo_army.csv");
		}
	}

	private void loadCitiesAndDistances() throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("distances.csv"));
		String currentLine = br.readLine();
		ArrayList<String> names = new ArrayList<String>();

		while (currentLine != null) {

			String[] content = currentLine.split(",");
			if (!names.contains(content[0])) {
				availableCities.add(new City(content[0]));
				names.add(content[0]);
			} else if (!names.contains(content[1])) {
				availableCities.add(new City(content[1]));
				names.add(content[1]);
			}
			distances.add(new Distance(content[0], content[1], Integer.parseInt(content[2])));
			currentLine = br.readLine();

		}
		br.close();
	}

	public void loadArmy(String cityName, String path) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader(path));
		String currentLine = br.readLine();
		Army resultArmy = new Army(cityName);
		while (currentLine != null) {
			String[] content = currentLine.split(",");
			String unitType = content[0].toLowerCase();
			int unitLevel = Integer.parseInt(content[1]);
			Unit u = null;
			if (unitType.equals("archer")) {

				if (unitLevel == 1)
					u = (new Archer(1, 60, 0.4, 0.5, 0.6));

				else if (unitLevel == 2)
					u = (new Archer(2, 60, 0.4, 0.5, 0.6));
				else
					u = (new Archer(3, 70, 0.5, 0.6, 0.7));
			} else if (unitType.equals("infantry")) {
				if (unitLevel == 1)
					u = (new Infantry(1, 50, 0.5, 0.6, 0.7));

				else if (unitLevel == 2)
					u = (new Infantry(2, 50, 0.5, 0.6, 0.7));
				else
					u = (new Infantry(3, 60, 0.6, 0.7, 0.8));
			} else if (unitType.equals("cavalry")) {
				if (unitLevel == 1)
					u = (new Cavalry(1, 40, 0.6, 0.7, 0.75));

				else if (unitLevel == 2)
					u = (new Cavalry(2, 40, 0.6, 0.7, 0.75));
				else
					u = (new Cavalry(3, 60, 0.7, 0.8, 0.9));
			}
			resultArmy.getUnits().add(u);
			u.setParentArmy(resultArmy);
			currentLine = br.readLine();
		}
		for (City c : availableCities) {
			if (c.getName().toLowerCase().equals(cityName.toLowerCase()))
				c.setDefendingArmy(resultArmy);
		}
		br.close();
	}

	public void targetCity(Army army, String targetName) {

		String from = army.getCurrentLocation();
		if (army.getCurrentLocation().equals("onRoad"))
			from = army.getTarget();
		for (Distance d : distances) {
			if ((d.getFrom().equals(from) || d.getFrom().equals(targetName))
					&& (d.getTo().equals(from) || d.getTo().equals(targetName))) {
				army.setTarget(targetName);
				int distance = d.getDistance();
				if (army.getCurrentLocation().equals("onRoad"))
					distance += army.getDistancetoTarget();
				army.setDistancetoTarget(distance);
			}
		}

	}

	public void endTurn() {
		currentTurnCount++;
		double totalUpkeep = 0;
		if(player.getControlledCities().isEmpty()) System.out.println("null");
		for (City c : player.getControlledCities()) {
			for (MilitaryBuilding b : c.getMilitaryBuildings()) {

				b.setCoolDown(false);
				b.setCurrentRecruit(0);

			}
			for (EconomicBuilding b : c.getEconomicalBuildings()) {
				b.setCoolDown(false);
				if (b instanceof Market)
					player.setTreasury(player.getTreasury() + b.harvest());
				else if (b instanceof Farm)
					player.setFood(player.getFood() + b.harvest());
			}
			totalUpkeep += c.getDefendingArmy().foodNeeded();
		}
		for (Army a : player.getControlledArmies()) {
			if (!a.getTarget().equals("") && a.getCurrentStatus() == Status.IDLE) {
				a.setCurrentStatus(Status.MARCHING);
				a.setCurrentLocation("onRoad");
			}
			if (a.getDistancetoTarget() > 0 && !a.getTarget().equals(""))
				a.setDistancetoTarget(a.getDistancetoTarget() - 1);
			if (a.getDistancetoTarget() == 0) {
				a.setCurrentLocation(a.getTarget());
				a.setTarget("");
				a.setCurrentStatus(Status.IDLE);
			}
			totalUpkeep += a.foodNeeded();

		}
		if (totalUpkeep <= player.getFood())
			player.setFood(player.getFood() - totalUpkeep);
		else {
			player.setFood(0);
			for (Army a : player.getControlledArmies()) {

				for (Unit u : a.getUnits()) {
					u.setCurrentSoldierCount(u.getCurrentSoldierCount() - (int) (u.getCurrentSoldierCount() * 0.1));
				}
			}
		}

		for (City c : availableCities) {
			if (c.isUnderSiege()) {
				if (c.getTurnsUnderSiege() < 3) {
					c.setTurnsUnderSiege(c.getTurnsUnderSiege() + 1);

				} else {
					// player should choose to attack
					c.setUnderSiege(false);
					return;
				}
				for (Unit u : c.getDefendingArmy().getUnits()) {
					u.setCurrentSoldierCount(u.getCurrentSoldierCount() - (int) (u.getCurrentSoldierCount() * 0.1));
				}
			}
		}

	}

	public void autoResolve(Army attacker, Army defender) throws FriendlyFireException {
		int turn = 1;
		while (attacker.getUnits().size() != 0 && defender.getUnits().size() != 0) {
			Unit unit1 = attacker.getUnits().get((int) (Math.random() * attacker.getUnits().size()));
			Unit unit2 = defender.getUnits().get((int) (Math.random() * defender.getUnits().size()));
			if (turn == 1)
				unit1.attack(unit2);
			else
				unit2.attack(unit1);
			turn = turn == 1 ? 0 : 1;

		}
		if (attacker.getUnits().size() != 0)
			occupy(attacker, defender.getCurrentLocation());

	}

	public void occupy(Army a, String cityName) {
		for (City c : availableCities) {
			if (c.getName().equals(cityName)) {
				player.getControlledCities().add(c);
				player.getControlledArmies().remove(a);
				c.setDefendingArmy(a);
				c.setUnderSiege(false);
				c.setTurnsUnderSiege(-1);
				a.setCurrentStatus(Status.IDLE);
			}
		}
	}

	public boolean isGameOver() {
		return player.getControlledCities().size() == availableCities.size() || currentTurnCount > maxTurnCount;
	}

	public ArrayList<City> getAvailableCities() {
		return availableCities;
	}

	public ArrayList<Distance> getDistances() {
		return distances;
	}

	public int getMaxTurnCount() {
		return maxTurnCount;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public int getCurrentTurnCount() {
		return currentTurnCount;
	}

	public void setCurrentTurnCount(int currentTurnCount) {
		this.currentTurnCount = currentTurnCount;
	}

	public static void main(String[] args) {
		Game game = new Game("Conquer_Game", 700, 700);
		JFrame frame = new JFrame(game.title);
		frame.setSize(game.width, game.height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setLayout(null);
		JLabel name = new JLabel("Please Enter Your Name , Sir");
		name.setFont(new Font("", Font.BOLD, 28));
		name.setBounds(150, 70, 450, 30);
		name.setForeground(new Color(255, 0, 255));
		JTextArea player_name = new JTextArea();
		player_name.setBounds(190, 100, game.width / 2, 1 * game.height / 12);
		player_name.setBackground(Color.ORANGE);
		player_name.setForeground(new Color(0, 0, 255));
		player_name.setFont(new Font("", Font.ITALIC, 45));
		JButton startButton = new JButton("Start");
		startButton.setBounds(3 * game.width / 8, 9 * game.height / 12, game.width / 4, 1 * game.height / 12);
		startButton.setBackground(Color.GREEN);
		startButton.setFont(new Font("", Font.ITALIC, 45));
		startButton.setForeground(new Color(255, 0, 255));
		Image image1 = new ImageIcon(game.getClass().getResource("/images.jpg")).getImage();
		// Image image2 = new
		// ImageIcon(game.getClass().getResource("/login.jpg")).getImage();
		// ImageIcon(game.getClass().getResource("/login.png")).getImage();
		Image image3 = new ImageIcon(game.getClass().getResource("/imag.jpg")).getImage();
		// name.setIcon(new ImageIcon(image2));
		ImageIcon img = new ImageIcon(image3);
		JLabel label = new JLabel("", img, JLabel.CENTER);
		label.setBounds(0, 0, game.width, game.height);
		// label.setIcon(img);
		// label.setHorizontalTextPosition(JLabel.CENTER);
		// label.setIconTextGap(100); //set gap of text to image

		// label.setBackground(null);
		// label.setOpaque(true);//display backGround color

		// Border border = BorderFactory.createLineBorder(Color.RED, 2);
		// label.setBorder(border);

		// label.setVerticalAlignment(JLabel.TOP);//set horizontal position of image or
		// text
		// label.setHorizontalAlignment(JLabel.TOP);

		// if u want to split the screen in to many rectangles u can do it with Panel
		// JPanel bJPanel = new JPanel();

		// startButton.addActionListener(null);
		// startButton.setIcon(img);

		// FlowLayOut places components in a row , sized at their preferred size.
		// If the horizontal space in the container is too small,the flowLayout class
		// uses the next available row
		// frame.setLayout(new FlowLayout());
		// JButton button = new JButton("1");
		// frame.add(button);

		// GridLayOut place components in a grid of cells each component takes all the
		// available size within its cell and each cell is the same size
		/*
		 * frame.setLayout(new GridLayout(3,3 , 0 , 10)); //rows and couloms , hgap and
		 * vgap frame.add(new JButton("1")); frame.add(new JButton("2")); frame.add(new
		 * JButton("3")); frame.add(new JButton("4")); frame.add(new JButton("5"));
		 * frame.add(new JButton("6")); frame.add(new JButton("7")); frame.add(new
		 * JButton("8")); frame.add(new JButton("9"));
		 */

		// JLayeredPane swing container that provides us with third dimension for
		// positioning components
		// ex depth , Z-index
		/*
		 * JLabel label = new JLabel(); label.setOpaque(true);
		 * label.setBackground(Color.BLUE); label.setBounds(50,50,200,200);
		 * 
		 * JLabel label2 = new JLabel(); label2.setOpaque(true);
		 * label2.setBackground(Color.RED); label2.setBounds(100,100,200,200);
		 * 
		 * JLabel label3 = new JLabel(); label3.setOpaque(true);
		 * label3.setBackground(Color.ORANGE); label3.setBounds(150,150,200,200);
		 * 
		 * JLayeredPane layeredPane = new JLayeredPane();
		 * layeredPane.setBounds(0,0,500,500); //layeredPane.add(label);
		 * layeredPane.add(label2); layeredPane.add(label3); layeredPane.add(label ,
		 * Integer.valueOf(0));
		 * 
		 * JFrame frame = new JFrame(); frame.add(layeredPane); frame.setSize(500,500);
		 * frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); frame.setLayout(null);
		 * frame.setVisible(true);
		 */

		// To start an action first create 3 classes
		/*
		 * 1- Display
		 * 
		 * import javax.swing.*; import java.awt.*; import
		 * java.text.AttributedCharacterIterator; import java.util.Map;
		 * 
		 * public class Display {
		 * 
		 * public static void main(String[] args) {
		 * 
		 * Launcher launcher = new Launcher();
		 * 
		 * 2- Launcher
		 * 
		 * import javax.swing.*; import java.awt.event.ActionEvent; import
		 * java.awt.event.ActionListener;+
		 * 
		 * public class Launcher implements ActionListener {
		 * 
		 * JFrame frame = new JFrame(); JButton button = new JButton("New Window");
		 * Launcher(){
		 * 
		 * button.setBounds(100,160,200,40); button.setFocusable(false);
		 * button.addActionListener(this);
		 * 
		 * frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); frame.setSize(500,500);
		 * frame.setLayout(null); frame.setVisible(true); frame.add(button);
		 * 
		 * } public static void main(String[] args) { }
		 * 
		 * @Override public void actionPerformed(ActionEvent e) {
		 * 
		 * if (e.getSource()==button){ frame.dispose(); Game game = new Game(); } } }
		 * 
		 * } }
		 * 
		 * 3- Game
		 * 
		 * import javax.swing.*; import java.awt.*;
		 * 
		 * public class Game {
		 * 
		 * JFrame frame = new JFrame(); JLabel label = new JLabel("Hello");
		 * 
		 * Game(){
		 * 
		 * label.setBounds(0,0,100,50); label.setFont(new Font(null , Font.BOLD , 25));
		 * 
		 * frame.add(label); frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 * frame.setSize(500,500); frame.setLayout(null); frame.setVisible(true); }
		 * 
		 * public static void main(String[] args) {
		 * 
		 * }
		 * 
		 * }
		 * 
		 */

		// JOptionPane = pop up a standard dialog box that prompts users for a value
		// or inform them of something
		// JOptionPane.showMessageDialog(null , "Mahmoud" , "title" ,
		// JOptionPane.WARNING_MESSAGE);
		// JOptionPane.showMessageDialog(null , "Mahmoud" , "title" ,
		// JOptionPane.QUESTION_MESSAGE);
		// JOptionPane.showConfirmDialog(null , "mm" , "t" ,
		// JOptionPane.YES_NO_CANCEL_OPTION);//sout
		// JOptionPane.showInputDialog("Hello");
		// JOptionPane.showOptionDialog(null,"Mahmoud" , "title" ,
		// JOptionPane.YES_NO_CANCEL_OPTION , JOptionPane.INFORMATION_MESSAGE , null ,
		// null , 0);//u can set 2td null to String Array

		/*
		 * JButton button; JTextField textField; Launcher(){
		 * 
		 * this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); this.setSize(500,500);
		 * this.setLayout(new FlowLayout()); textField = new JTextField();
		 * textField.setPreferredSize(new Dimension(250 , 40)); textField.setFont(new
		 * Font("Cola" , Font.ITALIC , 36)); textField.setForeground(Color.CYAN);
		 * textField.setBackground(Color.BLACK); //textField.setCaretColor(Color.GREEN);
		 * textField.setText("UserName"); button = new JButton("Submit");
		 * button.addActionListener(this); this.add(button); this.add(textField);
		 * this.pack(); this.setVisible(true);
		 * 
		 * } public static void main(String[] args) { Launcher launcher = new
		 * Launcher(); }
		 * 
		 * @Override public void actionPerformed(ActionEvent e) { if (e.getSource() ==
		 * button){ System.out.println(textField.getText()); // if u want the user not
		 * to enter another name button.setEnabled(false); textField.setEditable(false);
		 * } }
		 */

		/*
		 * JButton button; JCheckBox checkBox; ImageIcon xIcon; ImageIcon
		 * checkIcon;//and iniliaze them Launcher(){ //JCheckBox = can selected or
		 * deselected this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 * this.setSize(500,500); this.setLayout(new FlowLayout()); checkBox = new
		 * JCheckBox(); //checkBox.setPreferredSize(new Dimension(250 , 40));
		 * checkBox.setFont(new Font("Coda" , Font.ITALIC , 36));
		 * checkBox.setForeground(Color.CYAN); checkBox.setBackground(Color.BLACK);
		 * checkBox.setFocusable(false); //textField.setCaretColor(Color.GREEN);
		 * checkBox.setText("UserName is Right ??"); checkBox.setIcon(xIcon);
		 * checkBox.setSelectedIcon(checkIcon); button = new JButton("Submit");
		 * button.addActionListener(this); this.add(button); this.add(checkBox);
		 * this.pack(); this.setVisible(true);
		 * 
		 * } public static void main(String[] args) { Launcher launcher = new
		 * Launcher(); }
		 * 
		 * @Override public void actionPerformed(ActionEvent e) { if (e.getSource() ==
		 * button){ System.out.println(checkBox.isSelected()); } }
		 */

		/*
		 * JButton button; JRadioButton radioButton1; JRadioButton radioButton2;
		 * JRadioButton radioButton3;
		 * 
		 * ImageIcon ra1; ImageIcon ra2; ImageIcon ra3; Launcher(){ //JRadioButton = one
		 * or more buttons in a group which one only can be selected
		 * this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); this.setSize(500,500);
		 * this.setLayout(new FlowLayout()); radioButton1 = new JRadioButton("Osama");
		 * radioButton2 = new JRadioButton("Mahmoud"); radioButton3 = new
		 * JRadioButton("Mohamed "); radioButton3.setIcon(ra3); ButtonGroup group = new
		 * ButtonGroup(); group.add(radioButton1); group.add(radioButton2);
		 * group.add(radioButton3);
		 * 
		 * radioButton1.addActionListener(this); radioButton2.addActionListener(this);
		 * radioButton3.addActionListener(this);
		 * 
		 * this.add(radioButton1); this.add(radioButton2); this.add(radioButton3);
		 * this.pack(); this.setVisible(true);
		 * 
		 * } public static void main(String[] args) { Launcher launcher = new
		 * Launcher(); }
		 * 
		 * @Override public void actionPerformed(ActionEvent e) { if
		 * (e.getSource()==radioButton1) System.out.print("sss"); }
		 */

		/*
		 * JComboBox radioButton2;
		 * 
		 * Launcher(){ //JComboBox = a component that combines buttons in a drop-down
		 * list
		 * 
		 * this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); this.setSize(500,500);
		 * this.setLayout(new FlowLayout()); String[] animals = {"dog" , "cat" ,
		 * "bird"}; //u have to use rubber class Integer[] .... radioButton2 = new
		 * JComboBox(animals);
		 * 
		 * 
		 * radioButton2.getItemCount(); radioButton2.addItem("horse");
		 * radioButton2.insertItemAt("pig" , 0);//it appears but not selected
		 * radioButton2.setSelectedIndex(0); radioButton2.removeItem(0);
		 * 
		 * this.
		 * 
		 * } public static void main(String[] args) { Launcher launcher = new
		 * Launcher(); }
		 * 
		 * @Override public void actionPerformed(ActionEvent e) { if
		 * (e.getSource()==radioButton2)
		 * System.out.println(radioButton2.getSelectedIndex()); }
		 */

		/*
		 * JFrame frame = new JFrame(); JProgressBar bar = new JProgressBar(0,500);
		 * 
		 * Launcher(){ //process bar = Visual aid to let the user know that the
		 * operation is processing bar.setValue(10); bar.setBounds(0,0,420,50);
		 * bar.setStringPainted(true); bar.setFont(new Font("Nice" , Font.BOLD , 24));
		 * bar.setForeground(Color.RED); bar.setBackground(Color.BLUE); frame.add(bar);
		 * 
		 * frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); frame.setSize(500,500);
		 * frame.setLayout(null); frame.setVisible(true); fill();
		 * 
		 * } public static void main(String[] args) { Launcher launcher = new
		 * Launcher(); }
		 * 
		 * public void fill(){ for (int i = 0; i <= 500; i+=1) { bar.setValue(i); try {
		 * Thread.sleep(50); } catch (InterruptedException e) { e.printStackTrace(); } }
		 * bar.setString("Done"); }
		 */

		/*
		 * JMenuItem menuItem1; JMenuItem menuItem2; JMenuItem menuItem3; Launcher(){
		 * this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); this.setSize(500,500);
		 * this.setLayout(null);
		 * 
		 * JMenuBar menuBar = new JMenuBar(); JMenu menu1 = new JMenu("File"); JMenu
		 * menu2 = new JMenu("eDIT"); JMenu menu3 = new JMenu("Help");
		 * 
		 * menu1.setMnemonic(KeyEvent.VK_C);// Alt + c menuItem1 = new
		 * JMenuItem("Save"); menuItem2 = new JMenuItem("Load"); menuItem3 = new
		 * JMenuItem("preference");
		 * 
		 * menuItem1.setMnemonic(KeyEvent.VK_S); // L for load
		 * menuItem2.setMnemonic(KeyEvent.VK_L);
		 * 
		 * menuItem1.addActionListener(this); menuItem2.addActionListener(this);
		 * menuItem3.addActionListener(this);
		 * 
		 * menu1.add(menuItem1); menu1.add(menuItem2); menu1.add(menuItem3);
		 * 
		 * menuBar.add(menu1); menuBar.add(menu2); menuBar.add(menu3);
		 * this.setJMenuBar(menuBar); this.setVisible(true);
		 * 
		 * } public static void main(String[] args) { Launcher launcher = new
		 * Launcher(); }
		 * 
		 * @Override public void actionPerformed(ActionEvent e) { if
		 * (e.getSource()==menuItem1) System.out.println("saved"); }
		 */

		frame.add(startButton);
		frame.add(name);
		frame.add(player_name);
		frame.setVisible(true);
		frame.getContentPane().setBackground(Color.YELLOW);
		frame.setIconImage(image1);
		frame.add(label);
		startButton.setFocusable(false);

	}

}
