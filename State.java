import java.util.ArrayList;

public class State {

    private Point Square;
    private State parent;
    private int step;
    private int h;
    private int g; //Always same as step :(
    
    //Is it really needed?
    private ArrayList<State> children;  
    
    // Point passes a reference - be careful with that
    public State(State par, Point sq, Point goal) {
        this.parent = par;
        this.Square = sq;
        this.g = this.parent.getStep() + 1;
        this.step = this.g;
        // Admissible heuristic
        //this.h = dist(this.Square, goal);
        // Non - Admissible heuristic
        this.h = 4 * dist(this.Square, goal);
    }

    public State(Point sq, Point goal, int st) {
        this.parent = null;
        this.Square = sq;
        this.g = st;
        this.step = this.g;
        this.h = dist(this.Square, goal);
    }
    
    
    public int getH() {
        return this.h;
    }
    
    public int getG() {
        return this.g;
    }

    public int getStep() {
        return this.step;
    }

    public void incStep() {
        this.step += 1;
        return;
    }

    public Point getSq() {
        return this.Square;
    }

    public State getParent() {
        return this.parent;
    }
    
    private static int dist(Point a, Point b) {
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
    }


}    
