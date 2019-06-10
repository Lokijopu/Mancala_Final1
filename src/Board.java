import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeListener;

public class Board extends JComponent {
	private BufferedImage image;
	private File f;
	private JLabel lbl;
	private int[][] pits;
	private int[] mancalas;
	private int activePlayer;
	private boolean freeTurn;
	private int activeUndoPlayer;
	private ArrayList<ChangeListener> listeners;
	private boolean gameOver;

	public static final int N_PLAYERS = 2;
	public static final int BOARD_LENGTH = 6;
	private static final int UNDO_MAX = 3;
	public Board(int stones) {
	    image = null;
	    f = null;
	    lbl = null;
	    pits = new int[N_PLAYERS][BOARD_LENGTH];
		mancalas = new int[N_PLAYERS];
		listeners = new ArrayList<ChangeListener>();
		gameOver = false;
		for (int p = 0; p < N_PLAYERS; p++)
		{
			mancalas[p] = 0;
			for (int col = 0; col < BOARD_LENGTH; col++)
				pits[p][col] = stones;
		}
		activePlayer = 0;
		freeTurn = false;
		activeUndoPlayer = 0;
	}
	public JLabel getImage() {
		try{
			  int width = 963;    
		      int height = 640;  
		      f = new File("C:/Images/mancala_board.jpg"); //image file pathway
		      image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		      image = ImageIO.read(f);
		      lbl = new JLabel(new ImageIcon(image));
		    }catch(IOException e){
		      System.out.println("Error: "+e);
		    }
		return lbl;
	}
}
