package iterativedfsmaze;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.*;
import java.awt.*;
import java.util.LinkedList; 
import java.util.Queue;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class IterativeDFSMaze extends Canvas {
    //X and y limits are interchanged
// public final static int xlimit=12;
// public final static int ylimit=12;
// public final static int xgoal=8;
//public final static int ygoal=6;
//public final static int blockSize=30;
    public final static int xlimit=17;
 public final static int ylimit=17;
 public final static int xgoal=7;
public final static int ygoal=8;
//Vary size of maze
public final static int blockSize=29;
//Keep speed >=25
//Increasing value decreases speed
public final static int algoSpeed=100;

//Constants
public final static int LEFT=0;
public final static int RIGHT=1;
public final static int TOP=2;
public final static int BOTTOM=3;
public final static int START=4;






public static ArrayList<Node> listNodes;
public static Set<String> coordinateSet;
    static int once=0;
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
    
    
    
    
    
    
     static int[][] maze=
       {{0,0,1,0,0,0,0,1,1,1,0,0,0,0,1,0,0},
        {1,0,1,1,1,1,0,0,1,0,0,1,1,0,1,1,1},
        {1,0,0,1,0,1,0,1,1,0,1,0,0,0,0,0,0},
        {1,0,0,1,0,1,0,0,0,0,0,1,0,0,1,0,0},
        {1,1,0,0,0,0,0,1,0,1,0,0,1,1,0,1,0},
        {0,0,0,1,0,1,0,1,0,1,0,1,0,0,0,1,0},
        {0,1,0,1,1,0,0,1,0,0,1,0,0,1,1,1,0},
        {1,1,0,0,1,1,0,0,5,0,0,1,0,1,0,0,0},
        {0,1,0,0,1,0,0,0,0,1,0,1,1,1,0,0,0},
        {0,0,1,0,0,0,0,1,0,1,1,1,0,0,0,0,0},
        {0,0,1,1,1,0,1,0,1,1,1,0,0,0,0,0,0},
        {0,1,1,0,0,0,1,0,1,0,1,0,0,0,1,1,1},
        {1,0,1,0,1,0,1,0,1,0,1,1,0,0,0,0,1},
        {1,0,1,0,1,1,1,0,1,0,0,1,0,1,1,0,0},
        {1,0,0,0,0,0,0,0,0,0,0,1,1,1,1,0,0},
        {1,1,0,0,1,0,1,1,1,1,0,0,0,0,1,0,0},
        {0,0,1,0,1,0,1,0,0,1,1,1,0,0,0,0,0}
       };
    
    
    
    
    
    
    
    
    
   
    public static void main(String[] args) {
        listNodes=new ArrayList<>();
         coordinateSet= new HashSet<String>();
       IterativeDFSMaze m=new IterativeDFSMaze();  
       
        JFrame f=new JFrame();  
        f.add(m);  
        f.setSize(520,550);  
        
        //f.setLayout(null);  
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true); 
        
        
        int iter=1;
        while(true)
        {
            iterateOverDFS(new Node(0,0,1),iter,1,START);
            iter++;
        }
       
    
    
    
    
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
int xycor[][][]=new int[xlimit][ylimit][2];
int xcor=0,ycor=0;
for(int i=0;i<xlimit;i++)
{
    xcor=0;
    for(int j=0;j<ylimit;j++)
    {
        xycor[i][j][0]=xcor;
        xycor[i][j][1]=ycor;
        g.setColor(Color.BLACK);
        g.drawRect(xcor, ycor, blockSize, blockSize);
        
        if(maze[i][j]==0)
        {
            g.setColor(Color.WHITE);
           g.fillRect(xcor+2, ycor+2,blockSize-2, blockSize-2); 
        }
        else if(maze[i][j]==1)
        {
            g.setColor(Color.DARK_GRAY);
           g.fillRect(xcor+2, ycor+2,blockSize-2, blockSize-2); 
        }
        else if(maze[i][j]==5)
        {g.setColor(Color.BLUE);
           g.fillRect(xcor+2, ycor+2,blockSize-2, blockSize-2);
            
        }
            //System.out.println(xcor);
            
        xcor=xcor+blockSize;
    }
    ycor=ycor+blockSize;
}

g.setColor(Color.RED);
ArrayList<Node> list=(ArrayList)listNodes.clone();
for(Node n:list)
{
    g.fillRect(xycor[n.getX()][n.getY()][0]+2, xycor[n.getX()][n.getY()][1]+2,blockSize-2, blockSize-2);
}

//try
//{
//Thread.sleep(10000);
//}
//catch(Exception e)
//{
//    
//}

   
  try
  {
   Thread.sleep(100);
  }
  catch(Exception e)
  {
      e.printStackTrace();
  }
   this.repaint();
   
   
          
    } 
    
    
    public static void iterateOverDFS(Node root,int limit,int level,int direction)
   {
//       if(root==null)
//       {
//           root=new Node(0,0,level);
//           listNodes.add(root);
//       }
//       else
//       {
           listNodes.add(root);
           coordinateSet.add(root.getX()+","+root.getY());
           try
           {
           Thread.sleep(algoSpeed);
           }
           catch(Exception e)
           {
               e.printStackTrace();
           }
           
           
           System.out.println("X: "+root.getX()+" Y: "+root.getY()+"Limit: "+limit);
               
               
               if(root.getX()==xgoal&&root.getY()==ygoal)
               {
                   System.out.println("Goal Found! at limit "+limit);
                   try
                   {
                   Thread.sleep(10000);
                   }
                   catch(Exception e)
                   {
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
               if(root.getLeftChild()==null&&direction!=LEFT)
               {
                   if(root.getX()!=0)
                   {
                       if(maze[root.getX()-1][root.getY()]!=1&&!coordinateSet.contains((root.getX()-1)+","+root.getY()))
                       {
                        root.setLeftChild(new Node(root.getX()-1,root.getY(),level));
                        
                        if(level-1<limit)
                        {
                            iterateOverDFS(root.getLeftChild(),limit,level,RIGHT);
                        }
                       }
                   }
               }
               if(root.getRightChild()==null&&direction!=RIGHT)
               {
                   if(root.getX()!=xlimit-1)
                   {
                       if(maze[root.getX()+1][root.getY()]!=1&&!coordinateSet.contains((root.getX()+1)+","+root.getY()))
                       {
                        root.setRightChild(new Node(root.getX()+1,root.getY(),level));
                        
                        if(level-1<limit)
                        {
                            iterateOverDFS(root.getRightChild(),limit,level,LEFT);
                        }
                       }
                   }
               }
               if(root.getTopChild()==null&&direction!=TOP)
               {
                   if(root.getY()!=0)
                   {
                       if(maze[root.getX()][root.getY()-1]!=1&&!coordinateSet.contains(root.getX()+","+(root.getY()-1)))
                       {
                        root.setTopChild(new Node(root.getX(),root.getY()-1,level));
                        
                        if(level-1<limit)
                        {
                            iterateOverDFS(root.getTopChild(),limit,level,BOTTOM);
                        }
                       }
                   }
               }
               if(root.getBottomChild()==null&&direction!=BOTTOM)
               {
                   if(root.getY()!=ylimit-1)
                   {
                       if(maze[root.getX()][root.getY()+1]!=1&&!coordinateSet.contains(root.getX()+","+(root.getY()+1)))
                       {
                        root.setBottomChild(new Node(root.getX(),root.getY()+1,level));
                        
                        if(level-1<limit)
                        {
                            iterateOverDFS(root.getBottomChild(),limit,level,TOP);
                        }
                       }
                   }
               }
           
               
               listNodes.remove(root);
               coordinateSet.remove(root.getX()+","+root.getY());
               try
                {
                    Thread.sleep(algoSpeed);
                }
                catch(Exception e)
                {
                     e.printStackTrace();
                }
       }
   
}
