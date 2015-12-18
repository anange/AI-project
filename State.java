import java.util.ArrayList;

public class State {

    private int Square;
    private State parent;
    private int step;
    private int h;
    private int g;
    
    //Is it really needed?
    private ArrayList<State> children;  

    public int getH() {
        return this.h;
    }
    
    public int getG() {
        return this.g;
    }
}    
