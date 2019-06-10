import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Mancala extends JFrame {
	private int numTurns;
	private int bluePoints;
	private int orangePoints;
	private int[][] pits;
	private boolean gameOver;
	private boolean freeTurn;
	public static final int NUM_PLAYERS = 2;
	public static final int BOARD_LENGTH = 6;
	public Mancala() {
		freeTurn = false;
		bluePoints = 0;
		orangePoints = 0;
		pits = new int[2][7];
		pits[0][0] = 0; // starting number of stones for blue player's mancala
		pits[1][6] = 0; // starting number of stones for orange player's mancala
		for (int i = 0; i < 6; i++) {
			pits[1][i] = 4;
		}
		for (int j = 1; j < pits[0].length; j++) {
			pits[0][j] = 4;
		}
		setTitle("Mancala");
		getContentPane().setBackground(Color.YELLOW);
		numTurns = 1;
		setBounds(0,0,499,499);
		setBounds(0,0,500,500);
		setLayout(new GridBagLayout());
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		CardLayout cL = new CardLayout();
		JPanel overall = new JPanel();
		overall.setLayout(cL);
		
		JPanel homePanel = new JPanel();
		homePanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		JLabel mancalaLabel = new JLabel("Mancala");
		mancalaLabel.setBackground(Color.RED);
		gbc.gridx = 0;
		gbc.gridy = 0;
		mancalaLabel.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 120));
		homePanel.add(mancalaLabel, gbc);
		JButton start = new JButton("Start");
		gbc.gridx = 0;
		gbc.gridy = 1;
		start.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				cL.show(overall, "panel 2");
			}
			
		});
		homePanel.add(start, gbc);
		JPanel instrPanel = new JPanel();
		instrPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc2 = new GridBagConstraints();
		JLabel lbl1 = new JLabel("The goal of this game is to collect as many pieces in your mancala (the rightmost store) as you can. You and your opponent take turns to move the gems according to the following rules:");
		gbc2.gridx = 0;
		gbc2.gridy = 0;
		lbl1.setFont(new Font("TimesRoman", Font.BOLD, 16));
		instrPanel.add(lbl1, gbc2);
		JLabel lbl2 = new JLabel("1.\t\tYou may only move the gems on your side.");
		gbc2.gridx = 0;
		gbc2.gridy = 1;
		lbl2.setFont(new Font("TimesRoman", Font.BOLD, 16));
		instrPanel.add(lbl2, gbc2);
		JLabel lbl3 = new JLabel("2.\t\tEach time you select a pit, all the pieces in the pit will be allocated to each pit counterclockwise, with pieces ending up in your mancala if it is the next pit and skips your opponent's mancala.");
		gbc2.gridx = 0;
		gbc2.gridy = 2;
		lbl3.setFont(new Font("TimesRoman", Font.BOLD, 16));
		instrPanel.add(lbl3, gbc2);
		JLabel lbl4 = new JLabel("3.\t\tIf the last piece of a move ends in your mancala, you can move again.");
		gbc2.gridx = 0;
		gbc2.gridy = 3;
		lbl4.setFont(new Font("TimesRoman", Font.BOLD, 16));
		instrPanel.add(lbl4, gbc2);
		JLabel lbl5 = new JLabel("4.\t\tIf the last piece of a move lands on an empty pit on your side and there are some pieces in the opposite pit, then the pieces in the two pits will go to your mancala.");
		gbc2.gridx = 0;
		gbc2.gridy = 4;
		lbl5.setFont(new Font("TimesRoman", Font.BOLD, 16));
		instrPanel.add(lbl5, gbc2);
		JButton play = new JButton("Play");
		gbc2.gridx = 0;
		gbc2.gridy = 5;
		instrPanel.add(play, gbc2);
		play.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				cL.show(overall, "panel 3");
			}
			
		});
		
		JPanel boardPanel = new JPanel();
		JLabel playerDisplay = new JLabel("Orange Player's turn");
		Board board = new Board(4);
		gbc.gridx = 0;
		gbc.gridy = 0;
		JLabel label = board.getImage();
		JButton[][] arr = new JButton[2][7];
		
		arr[0][0] = new JButton("" + pits[0][0]);
		arr[0][0].setBounds(46,100,100,400);
		label.add(arr[0][0]);
		
		arr[1][6] = new JButton("" + pits[1][6]);
		arr[1][6].setBounds(874,100,100,400);
		label.add(arr[1][6]);
		
		for (int i = 1; i < 7; i++) {
			arr[0][i] = new JButton("" + pits[0][i]);
			arr[0][i].setBounds(60+120*i,75,70,70);
			label.add(arr[0][i]);
			final Integer innerI = (Integer) i;
			arr[0][i].addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					if (numTurns % 2 == 0) {
						if (pits[0][innerI] == 0) JOptionPane.showMessageDialog(boardPanel, "Please select a nonempty pit."); 
						else {
							if (pits[0][innerI] - 1 < innerI) {
								for (int j = 1; j < innerI; j++) {
									pits[0][innerI-j]++;
									arr[0][innerI-j].setText("" + pits[0][innerI-j]);
								}
								if (pits[0][innerI-pits[0][innerI]] == 1 && pits[1][innerI-pits[0][innerI]] > 0) {
									bluePoints += pits[0][innerI-pits[0][innerI]] + pits[1][innerI-pits[0][innerI]];
									arr[0][innerI-pits[0][innerI]].setText("" + 0);
									pits[0][innerI-pits[0][innerI]] = 0;
									arr[1][innerI-pits[0][innerI]].setText("" + 0);
									pits[1][innerI-pits[0][innerI]] = 0;
								}
								pits[0][innerI] = 0;
								arr[0][innerI].setText("" + pits[0][innerI]);
								numTurns++;
								playerDisplay.setText("Orange Player's turn");
							} else if (pits[0][innerI] - 1 == innerI) {
								for (int j = 1; j <= innerI; j++) {
									pits[0][innerI-j]++;
									arr[0][innerI-j].setText("" + pits[0][innerI-j]);
								}
								pits[0][innerI] = 0;
								arr[0][innerI].setText("" + pits[0][innerI]);
								freeTurn = true;
								bluePoints++;
								if (freeTurn)
								playerDisplay.setText("Free turn for blue player!");
							} else if (pits[0][innerI] - 1 > innerI && pits[0][innerI] - 1 < innerI + 6){
								for (int j = 1; j <= innerI; j++) {
									pits[0][innerI-j]++;
									arr[0][innerI-j].setText("" + pits[0][innerI-j]);
								}
								for (int k = 0; k < pits[0][innerI] - innerI; k++) {
									pits[1][k]++;
									arr[1][k].setText("" + pits[1][k]);
								}
								if (pits[0][pits[0][innerI]-innerI] == 1 && pits[1][pits[0][innerI]-innerI] > 0) {
									bluePoints += pits[0][pits[0][innerI]-innerI] + pits[1][pits[0][innerI]-innerI];							
									arr[0][pits[0][innerI]-innerI].setText("" + 0);
									pits[0][pits[0][innerI]-innerI] = 0;
									arr[1][pits[0][innerI]-innerI].setText("" + 0);
									pits[1][pits[0][innerI]-innerI] = 0;								
								}
								pits[0][innerI] = 0;
								arr[0][innerI].setText("" + pits[0][innerI]);
								bluePoints++;
								numTurns++;
								playerDisplay.setText("Orange Player's turn");
							} else {
								for (int j = 1; j <= innerI; j++) {
									pits[0][innerI-j]++;
									arr[0][innerI-j].setText("" + pits[0][innerI-j]);
								}
								for (int k = 0; k < 6; k++) {
									pits[1][k]++;
									arr[1][k].setText("" + pits[1][k]);
								}
								for (int l = 6; l > 12-pits[0][innerI]+innerI; l--) {
									pits[0][l]++;
									arr[0][l].setText("" + pits[0][l]);
								}
								bluePoints++;
								numTurns++;
								playerDisplay.setText("Orange Player's turn");
							}
						}
						freeTurn = false;
						isGameOver();
					} else {
						JOptionPane.showMessageDialog(boardPanel, "Please select a pit from orange player's side."); 
					}
				}
			});
		for (int j = 0; j < 6; j++) {
			arr[1][j] = new JButton("" + pits[1][j]);
			arr[1][j].setBounds(180+120*j,450,70,70);
			label.add(arr[1][j]);
			final Integer innerJ = (Integer) j;
			arr[1][j].addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					if (numTurns % 2 == 1) {
						if (pits[1][innerJ] == 0) JOptionPane.showMessageDialog(boardPanel, "Please select a nonempty pit."); 
						else {
							if (pits[1][innerJ] < 6 - innerJ) {
								for (int k = innerJ+1; k < pits[1][innerJ]; k++) {
									pits[1][k]++;
									arr[1][k].setText("" + pits[1][k]);
								}
								if (pits[1][pits[1][innerJ]-innerJ] == 1 && pits[0][pits[1][innerJ]-innerJ] > 0) {
									bluePoints += pits[1][pits[1][innerJ]-innerJ] + pits[1][pits[1][innerJ]-innerJ];
									arr[0][pits[1][innerJ]-innerJ].setText("" + 0);
									pits[0][pits[1][innerJ]-innerJ] = 0;
									arr[1][pits[1][innerJ]-innerJ].setText("" + 0);
									pits[1][pits[1][innerJ]-innerJ] = 0;
								}
								pits[1][innerJ] = 0;
								arr[1][innerJ].setText("" + pits[1][innerJ]);
								numTurns++;
								playerDisplay.setText("Blue Player's turn");
							} else if (pits[1][innerJ] == 6-innerJ) {
								for (int k = 1; k <= innerJ; k++) {
									pits[1][innerJ-k]++;
									arr[1][innerJ-k].setText("" + pits[1][innerJ-k]);
								}
								pits[1][innerJ] = 0;
								arr[1][innerJ].setText("" + pits[1][innerJ]);
								freeTurn = true;
								if (freeTurn)
								playerDisplay.setText("Free turn for orange player!");
								orangePoints++;
							} else if (pits[1][innerJ] < 6 - innerJ && pits[1][innerJ] > 12 -innerJ) {
								orangePoints++;
								numTurns++;
								playerDisplay.setText("Blue Player's turn");
							} else {
								orangePoints++;
								numTurns++;
								playerDisplay.setText("Blue Player's turn");
							}
						}
						freeTurn = false;
						isGameOver();
					} else {
						JOptionPane.showMessageDialog(boardPanel, "Please select a pit from blue player's side."); 
					}
				}
			
			});
		}
		
		}
		if (gameOver) {
			if (bluePoints > orangePoints) {
				JOptionPane.showMessageDialog(boardPanel, "Blue player won by " + (bluePoints - orangePoints) + "!\nPress the home button to play again!");
			} else if (orangePoints > bluePoints) {
				JOptionPane.showMessageDialog(boardPanel, "Orange player won by " + (orangePoints - bluePoints) + "!\nPress the home button to play again!");
			} else {
				JOptionPane.showMessageDialog(boardPanel, "There was a tie!\nPress the home button to play again!");
			}
		}
		boardPanel.add(playerDisplay);
		label.setLayout(null);
		boardPanel.add(label);
		ImageIcon image = new ImageIcon(getClass().getResource("Home button.jfif"));
		JButton home = new JButton(image);
		home.setBorder(BorderFactory.createEmptyBorder());
		home.setContentAreaFilled(false);
		home.setPreferredSize(new Dimension(100, 100));
		boardPanel.add(home);
		home.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				numTurns = 1;
				bluePoints = 0;
				orangePoints = 0;
				freeTurn = false;
				cL.show(overall, "panel 1");
				pits[0][0] = 0; 
				pits[1][6] = 0; 
				for (int i = 0; i < 6; i++) {
					pits[1][i] = 4;
				}
				for (int j = 1; j < pits[0].length; j++) {
					pits[0][j] = 4;
				}

			for (int i = 1; i < 7; i++) {
				arr[0][i].setText("" + 4);
			}
			for (int j = 0; j < 6; j++) {
				arr[1][j].setText("" + 4);
			}
			arr[0][0].setText("" + 0);
			arr[1][6].setText("" + 0);
			playerDisplay.setText("Orange Player's turn");
		}
		});
		/*
		ImageIcon image2 = new ImageIcon(getClass().getResource("languages.jfif"));
		JButton lang = new JButton(image2);
		lang.setBorder(BorderFactory.createEmptyBorder());
		lang.setContentAreaFilled(false);
		lang.setPreferredSize(new Dimension(100, 100));
		boardPanel.add(lang);
		lang.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				cL.show(overall, "panel 4");
			}
			
		});
		ImageIcon image3 = new ImageIcon(getClass().getResource("question.jfif"));
		JButton qButton = new JButton(image3);
		qButton.setBorder(BorderFactory.createEmptyBorder());
		qButton.setContentAreaFilled(false);
		qButton.setPreferredSize(new Dimension(100, 100));
		boardPanel.add(qButton);
		qButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				cL.show(overall, "panel 2");
			}
			
		});
		JPanel langPanel = new JPanel();
		JLabel langLabel = new JLabel("Choose your language:");
		JComboBox<String> languages = new JComboBox<String>();
		langPanel.add(langLabel);
		languages.addItem("Select your language");
		languages.addItem("English");
		languages.addItem("German");
		languages.addItem("Spanish");
		languages.addItem("Latin");
		languages.addItem("French");
		langPanel.add(languages);
		JButton ok = new JButton("Ok");
		ok.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				if (languages.getSelectedItem().equals("English")) {
					java.util.Locale.setDefault(java.util.Locale.ENGLISH);

					JFileChooser chooser = new JFileChooser();

					chooser.setLocale(Locale.ENGLISH);
					
					JComponent.setDefaultLocale(Locale.ENGLISH); 
					
					cL.show(overall, "panel 3");
				} else if (languages.getSelectedItem().equals("German")) {
					java.util.Locale.setDefault(java.util.Locale.GERMAN);

					JFileChooser chooser = new JFileChooser();

					chooser.setLocale(Locale.GERMAN);
					
					JComponent.setDefaultLocale(Locale.GERMAN); 
					cL.show(overall, "panel 3");
				} else {
					JOptionPane.showMessageDialog(langPanel, "Please select a language.");
					cL.show(overall, "panel4");
				}
				
			}
			
		});
		langPanel.add(ok);	
		*/
		getContentPane().add(boardPanel);
		overall.add(homePanel, "panel 1");
		overall.add(instrPanel, "panel 2");
		overall.add(boardPanel, "panel 3");
		//overall.add(langPanel, "panel 4");
		cL.show(overall, "panel1");
		add(overall);
		}
	public void isGameOver() {
		for (int i = 1; i < 7; i++) {
			if (pits[0][i] != 0) gameOver = false;
		}
		for (int j = 0; j < 6; j++) {
			if (pits[1][j] != 0) gameOver = false;
		}
		gameOver = true;
	}
	public static void main (String[] args) {
		new Mancala();
	}
}
