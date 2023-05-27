package snakeGame;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.io.File;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;

public class SnakeGame {
	public static void main(String[] args) {

				
				JFrame frame = new SnakeFrame();
				frame.setTitle("Snake Game");
	            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	            frame.setLocationRelativeTo(null);
	            frame.setVisible(true);
			}

	}
	

class SnakeFrame extends JFrame{
	

	
	public Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	public SnakeFrame()
	{	
		add(new SnakeComponent());
		pack();
		setResizable(true);
	}
}

class SnakeComponent extends JPanel implements ActionListener
{
	
	public Dimension dim2 = Toolkit.getDefaultToolkit().getScreenSize();
	
	final int FRAME_HEIGHT = 500;
	final int FRAME_WIDTH = 500;


	private final int DOT_SIZE = 25;
	private final int DOTS = ((int)(FRAME_HEIGHT * FRAME_WIDTH)) / (DOT_SIZE*DOT_SIZE) ;
	private int snakeX[] = new int[DOTS];
	private int snakeY[] = new int[DOTS];
	private int dots = 5;
	private int appleX;
	private int appleY;
	//private Color colorArray[] = {Color.RED, Color.BLUE, Color.GREEN, Color.PINK, Color.MAGENTA, Color.YELLOW , Color.WHITE, Color.ORANGE, Color.CYAN, Color.GRAY};
	
	private boolean left = false;
    private boolean right = false;//right true
    private boolean up = false;
    private boolean down = false;
	
	
    private Timer timer;
    private Random random;
    private final int DELAY = 200;
    boolean started = false;
    
    
	
	public SnakeComponent()
	{
		random = new Random();
		setBackground(Color.BLACK);
		setFocusable(true);
		requestFocusInWindow();
		addKeyListener(new MyKeyAdapter());
		startGame();

		
		
	}
	
	private void startGame() {
		appleLocation();
		started = true;
		timer = new Timer(DELAY, this);
		timer.start();
		right = true; 
		
	}
	


	private void appleCheck()
	{

			if(snakeX[0] == appleX && snakeY[0] == appleY)
			{
				dots++;
				appleLocation();
			}
	}
	
	private void appleLocation()
	{
		appleX = random.nextInt((int)(FRAME_WIDTH/DOT_SIZE))*DOT_SIZE;
		appleY = random.nextInt((int)(FRAME_HEIGHT/DOT_SIZE))*DOT_SIZE;
	}

	private void moveSnake()
	{
		
		
			for(int z = dots; z>0; z--)
			{
				snakeX[z] = snakeX[(z-1)];
				snakeY[z] = snakeY[(z-1)];
				
			
			}
				
		
		if(left)
		{
			snakeX[0] -= DOT_SIZE;
		}
		else if(right)
		{
			snakeX[0] += DOT_SIZE;
		}
		else if(up)
		{
			snakeY[0] -= DOT_SIZE;
		}
		else if(down)
		{
			snakeY[0] += DOT_SIZE;
		}			
				
		
	}

	private void collisionCheck()
	{
		
			
		if(snakeY[0]>= 500)
		{	
			started = false;
		}	
		else if(snakeY[0] < 0)
		{	
			started = false;
		}
		else if(snakeX[0] >= 500)
		{	
			started = false;
		}
		else if(snakeX[0] < 0)
		{	
			started = false;				
		}
		
		for(int i = dots; i>0; i--)
		{
			if(snakeX[0] == snakeX[i] && snakeY[0] == snakeY[i])
			{
				started = false;
			}
		}
		
		if(!started)
		{
			timer.stop();
		}
		
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		   
		
		g.setColor(Color.RED);
		g.fillOval(appleX, appleY, DOT_SIZE, DOT_SIZE);
		
		
		
			g2.setColor(Color.WHITE);
			for(int z = 0; z < dots; z++)
			{
				if(z == 0)
				{
					g2.setColor(new Color(34,139,34));
					g2.fill(new Rectangle2D.Double(snakeX[z], snakeY[z], DOT_SIZE, DOT_SIZE));
				}
				else
				{
					g2.setColor(Color.GREEN);
					g2.fill(new Rectangle2D.Double(snakeX[z], snakeY[z], DOT_SIZE, DOT_SIZE));
				}
			}
			
			if(started == false)
			{
				Font stringFont = new Font( "Emulogic", Font.BOLD, 22 );
				g2.setFont(stringFont);
				g2.setColor(Color.WHITE);
				g2.drawString("Game Over", (FRAME_WIDTH/2) -110, (FRAME_HEIGHT/2) );
			}

		}
	
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {	
		
		
		if(started)
		{
		moveSnake();
		appleCheck();
		collisionCheck();
		}
					
		repaint();
		    
  
	}

	public class MyKeyAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e)
		{
			switch(e.getKeyCode())
			{
			case KeyEvent.VK_LEFT:
				
				if(right != true)
				{
					left = true;
					up = false;
					down = false;
					
				}
				
				break;
			case KeyEvent.VK_RIGHT:
				
				if(left != true)
				{
					right = true;
					up = false;
					down = false;
					
				}
			
				break;
			case KeyEvent.VK_UP:
				
				if(up != true)
				{
					up = true;
					left = false;
					right = false;
				}
				
				break;
			case KeyEvent.VK_DOWN:
				
				if(down != true)
				{
					down = true;
					left = false;
					right = false;
				}
				
				break;
			}
		
		}
	}
		
		
		

	
	@Override public Dimension getPreferredSize() {
		return new Dimension(FRAME_WIDTH,FRAME_HEIGHT);
	}
}
