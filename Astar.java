import java.util.*;

public class Astar {
    
    private Point start;
    private Point goal;
    private int step;

    private PriorityQueue<State> states;

    public Astar(Point s, Point gl, int st) {
        this.start = s;
        this.goal = gl;
        this.step = st;
        Comparator<State> comp = new StateComparator();
        this.states = new PriorityQueue<State>(1000, comp);
        State root = new State(null, this.start);
        this.states.add(root);
    }

    public ArrayList<Point> solve() {
        
        State cur;
        cur = this.states.poll();
        if (cur == null) {
            System.out.println("Impossible!");
            System.exit(0);
        }
        
        if (cur.getSq().x == goal.x && cur.getSq().y == goal.y) {
            //TODO function that builds arraylist to return
        }
        else {
            //TODO create new states and put them in prio queue
        }
    }

}


