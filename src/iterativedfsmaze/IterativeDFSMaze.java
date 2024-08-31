package iterativedfsmaze;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class IterativeDFSMaze extends Canvas {
	// X and y limits are interchanged
// public final static int xlimit=12;
// public final static int ylimit=12;
// public final static int xgoal=8;
//public final static int ygoal=6;
//public final static int blockSize=30;
	public final static int xlimit = 17;
	public final static int ylimit = 17;
	
	// 0f to 1f
	public final static float percentageBarriers = 0.4f;
//Vary size of maze
	public final static int blockSize = 29;
//Keep speed >=25
//Increasing value decreases speed
	public final static int algoSpeed = 50;

//Constants
	public final static int LEFT = 0;
	public final static int RIGHT = 1;
	public final static int TOP = 2;
	public final static int BOTTOM = 3;
	public final static int START = 4;
	
	public static int xgoal = 0;
	public static int ygoal = 0;

	public static ArrayList<Node> listNodes;
	public static Set<String> coordinateSet;
	static int once = 0;
//    static int[][] maze=
//       {{0,0,0,0,0},
//        {0,1,1,0,0},
//        {1,0,0,1,0},
//        {1,1,0,1,0},
//        {1,0,0,5,0},
//        {0,0,0,0,0}
//       };

//    static int[][] maze=
//       {{0,0,1,0,1,0,0,0,1,0,0,0},
//        {1,0,1,1,1,1,0,1,1,1,0,0},
//        {1,0,0,1,0,1,1,1,1,1,1,0},
//        {1,1,0,1,0,1,0,0,0,0,0,1},
//        {1,0,0,0,0,0,0,1,1,1,0,1},
//        {0,0,0,1,0,1,0,0,1,1,0,1},
//        {0,1,0,1,1,1,0,0,1,0,1,0},
//        {1,1,0,0,0,1,1,0,0,0,0,1},
//        {0,1,0,0,1,0,5,0,0,1,0,1},
//        {0,0,1,0,0,0,0,0,0,1,1,1},
//        {0,0,1,1,1,0,0,0,0,0,0,0},
//        {0,0,1,0,0,0,0,0,0,0,0,0}
//       };

	static int[][] maze;

	public static void main(String[] args) {
		maze = generateRandomMaze(xlimit, ylimit);
		listNodes = new ArrayList<>();
		coordinateSet = new HashSet<String>();
		IterativeDFSMaze m = new IterativeDFSMaze();

		JFrame f = new JFrame();
		f.add(m);
		f.setSize(520, 550);

		// f.setLayout(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);

		int iter = 1;
		while (true) {
			iterateOverDFS(new Node(0, 0, 1), iter, 1, START);
			iter++;
		}

	}
	
	public static int[][] generateRandomMaze(int xlimit, int ylimit) {
		int[][] maze = new int[xlimit][ylimit];
		int countBarriers = 0;
		int total = xlimit*ylimit;
		Random random = new Random();
		boolean randomPlacementSlower = true;
		
		//Randomly set goal
		while(xgoal<5 && ygoal<5) {
			xgoal = ThreadLocalRandom.current().nextInt(0, xlimit);
			ygoal = ThreadLocalRandom.current().nextInt(0, ylimit);
		}
		maze[xgoal][ygoal] = 5;
		
		
		//Find random path and mark those cells
		int pathx = 0, pathy = 0;
		int pathMarker = -1;
		while(true) {
			pathx = 0;
			pathy = 0;
			pathMarker--;
			while(maze[pathx][pathy]!=5 && (isValidMove(pathx, pathy+1, maze, pathMarker) || isValidMove(pathx+1, pathy, maze, pathMarker) || isValidMove(pathx-1, pathy, maze, pathMarker) || isValidMove(pathx, pathy-1, maze, pathMarker)) ) {
				int direction = ThreadLocalRandom.current().nextInt(0, 4);
				switch(direction) {
				case 0:
					if(isValidMove(pathx+1, pathy, maze, pathMarker)) {
						maze[pathx][pathy] = pathMarker;
						pathx+=1;
					}
					break;
				case 1:
					if(isValidMove(pathx, pathy+1, maze, pathMarker))
					{
						maze[pathx][pathy] = pathMarker;
						pathy+=1;
					}
					break;
				case 2:
					if(isValidMove(pathx-1, pathy, maze, pathMarker)) {
						maze[pathx][pathy] = pathMarker;
						pathx-=1;
					}
					break;
				case 3:
					if(isValidMove(pathx, pathy-1, maze, pathMarker)) {
						maze[pathx][pathy] = pathMarker;
						pathy-=1;
					}
					break;
				default:
					;
				}
			}
			if(maze[pathx][pathy]==5)
					break;
		}
		
		int placementTries = 0;
		
		while(countBarriers/(double)total < percentageBarriers && placementTries<100) {
		for(int i=0;i<xlimit;i++) {
			for(int j=0;j<ylimit;j++) {
				if(i == 0 && j == 0) {
					continue;
				}
				if(countBarriers/(double)total < percentageBarriers && maze[i][j]!=pathMarker && maze[i][j]<=0) {
					randomPlacementSlower=!randomPlacementSlower;
				    if(random.nextBoolean() && randomPlacementSlower) {
				    	countBarriers++;
				    	maze[i][j] = 1;
				    }
				}
			}
		}
		placementTries++;
		}
		return maze;
	}
	
	public static boolean isValidMove(int x, int y, int[][] maze, int pathMarker) {
		if(x<0 || x>=xlimit || y<0 || y>=ylimit) {
			return false;
		}
		if(maze[x][y] == pathMarker) {
			return false;
		}
		return true;
	}

	public void paint(Graphics g) {
//        g.drawString("Hello",40,40);  
//        setBackground(Color.WHITE);  
//        g.fillRect(130, 30,100, 80);  
//        g.drawOval(30,130,50, 60);  
//        setForeground(Color.RED);  
//        g.fillOval(130,130,50, 60);  
//        g.drawArc(30, 200, 40,50,90,60);  
//        g.fillArc(30, 130, 40,50,180,40);

////Drawing the maze
		setBackground(Color.WHITE);
		int xycor[][][] = new int[xlimit][ylimit][2];
		int xcor = 0, ycor = 0;
		for (int i = 0; i < xlimit; i++) {
			xcor = 0;
			for (int j = 0; j < ylimit; j++) {
				xycor[i][j][0] = xcor;
				xycor[i][j][1] = ycor;
				g.setColor(Color.BLACK);
				g.drawRect(xcor, ycor, blockSize, blockSize);

				if (maze[i][j] == 0) {
					g.setColor(Color.WHITE);
					g.fillRect(xcor + 2, ycor + 2, blockSize - 2, blockSize - 2);
				} else if (maze[i][j] == 1) {
					g.setColor(Color.DARK_GRAY);
					g.fillRect(xcor + 2, ycor + 2, blockSize - 2, blockSize - 2);
				} else if (maze[i][j] == 5) {
					g.setColor(Color.BLUE);
					g.fillRect(xcor + 2, ycor + 2, blockSize - 2, blockSize - 2);

				}
				// System.out.println(xcor);

				xcor = xcor + blockSize;
			}
			ycor = ycor + blockSize;
		}

		g.setColor(Color.RED);
		ArrayList<Node> list = (ArrayList) listNodes.clone();
		for (Node n : list) {
			g.fillRect(xycor[n.getX()][n.getY()][0] + 2, xycor[n.getX()][n.getY()][1] + 2, blockSize - 2,
					blockSize - 2);
		}

//try
//{
//Thread.sleep(10000);
//}
//catch(Exception e)
//{
//    
//}

		try {
			Thread.sleep(200);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.repaint();

	}

	public static void iterateOverDFS(Node root, int limit, int level, int direction) {
//       if(root==null)
//       {
//           root=new Node(0,0,level);
//           listNodes.add(root);
//       }
//       else
//       {
		listNodes.add(root);
		coordinateSet.add(root.getX() + "," + root.getY());
		try {
			Thread.sleep(algoSpeed);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("X: " + root.getX() + " Y: " + root.getY() + "Limit: " + limit);

		if (root.getX() == xgoal && root.getY() == ygoal) {
			System.out.println("Goal Found! at limit " + limit);
			try {
				Thread.sleep(10000);
			} catch (Exception e) {
				System.err.println("Error while suspending thread");
			}
		}

		level++;
//           if(root.getLevel()==limit)
//           {
//               
//           }
//           else
//           {
		if (root.getLeftChild() == null && direction != LEFT) {
			if (root.getX() != 0) {
				if (maze[root.getX() - 1][root.getY()] != 1
						&& !coordinateSet.contains((root.getX() - 1) + "," + root.getY())) {
					root.setLeftChild(new Node(root.getX() - 1, root.getY(), level));

					if (level - 1 < limit) {
						iterateOverDFS(root.getLeftChild(), limit, level, RIGHT);
					}
				}
			}
		}
		if (root.getRightChild() == null && direction != RIGHT) {
			if (root.getX() != xlimit - 1) {
				if (maze[root.getX() + 1][root.getY()] != 1
						&& !coordinateSet.contains((root.getX() + 1) + "," + root.getY())) {
					root.setRightChild(new Node(root.getX() + 1, root.getY(), level));

					if (level - 1 < limit) {
						iterateOverDFS(root.getRightChild(), limit, level, LEFT);
					}
				}
			}
		}
		if (root.getTopChild() == null && direction != TOP) {
			if (root.getY() != 0) {
				if (maze[root.getX()][root.getY() - 1] != 1
						&& !coordinateSet.contains(root.getX() + "," + (root.getY() - 1))) {
					root.setTopChild(new Node(root.getX(), root.getY() - 1, level));

					if (level - 1 < limit) {
						iterateOverDFS(root.getTopChild(), limit, level, BOTTOM);
					}
				}
			}
		}
		if (root.getBottomChild() == null && direction != BOTTOM) {
			if (root.getY() != ylimit - 1) {
				if (maze[root.getX()][root.getY() + 1] != 1
						&& !coordinateSet.contains(root.getX() + "," + (root.getY() + 1))) {
					root.setBottomChild(new Node(root.getX(), root.getY() + 1, level));

					if (level - 1 < limit) {
						iterateOverDFS(root.getBottomChild(), limit, level, TOP);
					}
				}
			}
		}

		listNodes.remove(root);
		coordinateSet.remove(root.getX() + "," + root.getY());
		try {
			Thread.sleep(algoSpeed);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
