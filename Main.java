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
        start1 = new Point(Integer.parseInt(help[0]),
                           Integer.parseInt(help[1]));

        help = read(reader);
        start2 = new Point(Integer.parseInt(help[0]), 
                           Integer.parseInt(help[1]));

        help = read(reader);
        goal = new Point(Integer.parseInt(help[0]), 
                         Integer.parseInt(help[1]));

        help = read(reader);
        numOfMids = Integer.parseInt(help[0]);

        Point[] midpoints = new Point[numOfMids];
        for (i = 0; i < numOfMids; i++) {
            help = read(reader);
            midpoints[i] = new Point(Integer.parseInt(help[0]), 
                                     Integer.parseInt(help[1]));
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
        // Sorts midpoints according to what is closer to 
        // the previous one
        // TODO TESTING
        min = manhDist(start1, midpoints[0]);
        which = start1;
        for (i = 0; i < numOfMids; i++) {
            next = i;
            for (j = i+1; j < numOfMids; j++) {
                cur = manhDist(which, midpoints[j]);
                if (cur < min) {
                    min = cur;
                    next = j;
                }
            }
            temp = midpoints[i];
            midpoints[i] = midpoints[next];
            midpoints[next] = temp;
            which = midpoints[i];
        }
/*      
 *      int step = 0;
 *      ArrayList<Point> FirstPath;
 *      Astar astar = new Astar(start1, midpoints[0], step);
 *      FirstPath = astar.solve();
 *      for (i = 1; i < numOfMids; i++) {
 *          step = FirstPath.get(FirstPath.size() - 1).getStep() + 1;
 *          astar = new Astar(midpoints[i-1], midpoints[i], step);
 *          FirstPath = FirstPath.addAll(astar.solve());
 *      }
 */

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

    private static int manhDist(Point a, Point b) {
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
    }
}
