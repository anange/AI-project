import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    
    public static void main(String[] args) {
        
        BufferedReader reader;
        try{
            reader = new BufferedReader(new FileReader(args[0]));
        }

        catch(IOException ex) {
            System.out.println("File not found!");
            return;
        }

        String[] help;
        int n, m, numOfMids, i, j, min, next, cur;
        Point start1, start2, goal, temp, which;

        help = read(reader);
        n = Integer.parseInt(help[0]);
        m = Integer.parseInt(help[1]);

        help = read(reader);
        start1 = new Point(Integer.parseInt(help[0]) - 1,
                           Integer.parseInt(help[1]) - 1);

        help = read(reader);
        start2 = new Point(Integer.parseInt(help[0]) - 1, 
                           Integer.parseInt(help[1]) - 1);

        help = read(reader);
        goal = new Point(Integer.parseInt(help[0]) - 1, 
                         Integer.parseInt(help[1]) - 1);

        help = read(reader);
        numOfMids = Integer.parseInt(help[0]);

        Point[] midpoints = new Point[numOfMids];
        for (i = 0; i < numOfMids; i++) {
            help = read(reader);
            midpoints[i] = new Point(Integer.parseInt(help[0]) - 1, 
                                     Integer.parseInt(help[1]) - 1);
        }

        boolean[][] empty = new boolean[n][m];
        for (i = 0; i < n; i++) {
            String line;
            try {
                line = reader.readLine();
            }
            catch(IOException ex) {
                System.out.println("Something went wrong");
                return;
            }
            for (j = 0; j < m; j++) 
                if (line.charAt(j) == 'X')
                    empty[i][j] = false;
                else
                    empty[i][j] = true;
        }
        sortMidpoints(start1, midpoints, numOfMids);
        int step = 0;
        int nodes;
        boolean fingoal = false;
        ArrayList<Point> FirstPath;
        Astar astar = new Astar(start1, midpoints[0], fingoal, step, empty, null);
        FirstPath = astar.solve();
        nodes = astar.getNumOfNodes();
        
        for (i = 1; i <= numOfMids; i++) {

            if (i == numOfMids)
                temp = goal;
            else
                temp = midpoints[i];
            step = FirstPath.get(FirstPath.size() - 1).getStep();
            FirstPath.remove(FirstPath.size() - 1);
            astar = new Astar(midpoints[i-1], temp, fingoal, step, empty, null);
            FirstPath.addAll(astar.solve());
            nodes += astar.getNumOfNodes();
        }
        
        sortMidpoints(start2, midpoints, numOfMids);
        step = 0;
        ArrayList<Point> SecPath;
        astar = new Astar(start2, midpoints[0], fingoal, step, empty, FirstPath);
        SecPath = astar.solve();
        nodes += astar.getNumOfNodes();
        for (i = 1; i <= numOfMids; i++) {
            if (i == numOfMids) {
                fingoal = true;
                temp = goal;
			}
            else
                temp = midpoints[i];
            step = SecPath.get(SecPath.size() - 1).getStep();
            SecPath.remove(SecPath.size() - 1);
            astar = new Astar(midpoints[i-1], temp, fingoal, step, empty, FirstPath);
            SecPath.addAll(astar.solve());
            nodes += astar.getNumOfNodes();
        }
       
        ArrayList<Point> FirstPathBack, SecPathBack;
        fingoal = false;
        step = Math.max(FirstPath.get(FirstPath.size() - 1).getStep(), 
                        SecPath.get(SecPath.size() - 1).getStep());
        astar = new Astar(goal, start1, fingoal, step, empty, null);
        FirstPathBack = astar.solve();
        nodes += astar.getNumOfNodes();
        astar = new Astar(SecPath.get(SecPath.size() - 1),
                          start2, fingoal, step, empty, FirstPathBack);
        SecPathBack = astar.solve();
        nodes += astar.getNumOfNodes();
        System.out.println("First Robot | Second Robot | Step");
        for (i = 0; i <= step; i++) {
            if (i >= FirstPath.size())
                System.out.print("Wait for 2nd");
            else
                System.out.print("    (" + (FirstPath.get(i).x + 1) + "," + (FirstPath.get(i).y + 1) + ")   ");
            
            if (i >= SecPath.size())
                System.out.println(" Wait for 1st      " + i);
            else if (i > 0 && SecPath.get(i).getStep() - SecPath.get(i).getStep() > 1) 
                System.out.println("    Stalling    " + i);
            else
                System.out.println("     (" + (SecPath.get(i).x + 1) + " " + (SecPath.get(i).y + 1) + ")" + "         " + i);
        }
        int end = Math.max(FirstPathBack.size(), SecPathBack.size());
        for (i = 1; i < end; i++) {
           if (i >= FirstPathBack.size())
               System.out.print("Wait for 2nd");
            else 
               System.out.print("    (" + (FirstPathBack.get(i).x + 1) + "," + (FirstPathBack.get(i).y + 1) + ")   ");
            if (i >= SecPathBack.size())
                System.out.println(" Wait for 1st      " + (step + i));
            else
                System.out.println("     (" + (SecPathBack.get(i).x + 1) + " " + (SecPathBack.get(i).y + 1) + ")" + "         " + (step + i));
        }
        System.out.println("Number of nodes: " +  nodes);
    }           
    
    private static String[] read(BufferedReader r) {
        String line;
        try {
            line = r.readLine();
        }
        catch(IOException ex) {
            System.out.println("Wrong Input Format.");
            return null;
        }
        String[] nums = line.split(" ");
        return nums;
    }
    
    // Sorts midpoints according to what is closer to 
    // the previous one
    private static void sortMidpoints(Point start, Point[] midPoints, int numOfMids) {
        int min, i, next, j, cur;
        Point which, temp;
		min = manhDist(start, midPoints[0]);
        which = start;
        for (i = 0; i < numOfMids; i++) {
            next = i;
            for (j = i+1; j < numOfMids; j++) {
                cur = manhDist(which, midPoints[j]);
                if (cur < min) {
                    min = cur;
                    next = j;
                }
            }
            temp = midPoints[i];
            midPoints[i] = midPoints[next];
            midPoints[next] = temp;
            which = midPoints[i];
            if (i + 1 < numOfMids)
                min = manhDist(midPoints[next], midPoints[i+1]);
        }
    }

    private static int manhDist(Point a, Point b) {
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
    }
    
}
