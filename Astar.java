import java.util.*;

public class Astar {
    
    private Point start;
    private Point goal;
    private boolean[][] empty;
    private int n;
    private int m;
    private boolean[][] visited;
    private ArrayList<Point> FirstPath;

    private PriorityQueue<State> states;

    public Astar(Point s, Point gl, int step, boolean[][] em, ArrayList<Point> FstPath) {
        this.start = s;
        this.goal = gl;
        this.empty = em;
		this.FirstPath = FstPath;
        this.n = this.empty.length;
        this.m = this.empty[0].length;
        this.visited = new boolean[this.n][this.m];
        this.visited[this.start.x][this.start.y] = true;

        Comparator<State> comp = new StateComparator();
        this.states = new PriorityQueue<State>(1000, comp);
        State root = new State(this.start, this.goal, step);
        this.states.add(root);
    }

    public ArrayList<Point> solve() {
        
        State cur;
        Point temp;
        while (true) {
            cur = this.states.poll();

            if (this.FirstPath != null) {
                temp = FirstPath.get(cur.getStep());
                if (cur.getSq().x == temp.x && cur.getSq().y == temp.y) {
                    System.out.println("** Conflict **");
                    System.out.println("Robot 1 considering new position <" + (cur.getSq().x + 1) + "," + (cur.getSq().y + 1) +
                                       "> at step " + cur.getStep());
                    System.out.println("Robot 2 considering new position <" + (cur.getSq().x + 1) + "," + (cur.getSq().y + 1) + 
                                       "> at step " + cur.getStep());
				
                    if ((cur.getSq().x == goal.x && cur.getSq().y == goal.y) || (this.states.peek() == null)) {
						System.out.println("Resolving -- Robot 2 considering stalling at step " + (cur.getStep()));
						cur.incStep();
						this.states.add(cur);		
					}
                    else {
						System.out.println("Resolving -- Robot 2 considering new alternative position <" + (this.states.peek().getSq().x + 1) + "," + (this.states.peek().getSq().y + 1) + "> at step " + (cur.getStep() + 1));
                        this.visited[cur.getSq().x][cur.getSq().y] = false;
                    }
                    System.out.println("** End of Conflict **");
                    continue;
                }
            }


                

            if (cur == null) {
                System.out.println("Impossible!");
                System.exit(0);
            }
            
            if (cur.getSq().x == goal.x && cur.getSq().y == goal.y) {
                //TODO function that builds arraylist to return
                ArrayList<Point> path = new ArrayList<Point>();
                temp = cur.getSq();
                temp.setStep(cur.getStep());
                path.add(temp);
                while (cur.getParent() != null) {
                    cur = cur.getParent();
                    temp = cur.getSq();
                    temp.setStep(cur.getStep());
                    path.add(temp);
                }
                Collections.reverse(path);
                return path;
            }
            else {
                 //TODO anything else and test? 
                findNeighb(cur);
            }
        }
    }
    
    private void findNeighb(State cur) {
        int x = cur.getSq().x;
        int y = cur.getSq().y;
        
        int numOfNew = 0;
        Point[] nextSquares = new Point[4];
        if (x + 1 < this.n && empty[x+1][y] && !visited[x+1][y]) {
            nextSquares[numOfNew] = new Point(x+1, y);
            numOfNew += 1;
        }
        if (x - 1 >= 0 && empty[x-1][y] && !visited[x-1][y]) {
            nextSquares[numOfNew] = new Point(x-1, y);
            numOfNew += 1;
        }
        if (y + 1 < this.m && empty[x][y+1] && !visited[x][y+1]) {
            nextSquares[numOfNew] = new Point(x, y+1);
            numOfNew += 1;
        }
        if (y - 1 >= 0 && empty[x][y-1] && !visited[x][y-1]) {
            nextSquares[numOfNew] = new Point(x, y-1);
            numOfNew += 1;
        }
        for (int i = 0; i < numOfNew; i++) {
            State temp = new State(cur, nextSquares[i], this.goal);
            this.visited[nextSquares[i].x][nextSquares[i].y] = true; //valto visited edw?
            this.states.add(temp);
        }
    }        
}

    
