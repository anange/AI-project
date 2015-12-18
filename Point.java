public class Point {

    public int x;
    public int y;
    private int step;

    public Point() {
        this.x = -1;
        this.y = -1;
        this.step = -1;
    }

    public Point(int i, int j) {
        this.x = i;
        this.y = j;
        this.step = -1;
    }
    
    public int getStep() {
        return this.step;
    }

    public void setStep(int s) {
        this.step = s;
    }
}
