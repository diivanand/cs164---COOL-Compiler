import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: diivanand
 * Date: 12/5/13
 * Time: 8:48 PM
 * To change this template use File | Settings | File Templates.
 */
class Vertex {
    public String name;
    public List<Edge> adj;
    public double dist;
    public Vertex prev;
    public int scratch;

    public Vertex(String nm){
        this.name = nm;
        this.adj = new LinkedList<Edge>();
        reset();

    }

    public void reset(){
        this.dist = Double.POSITIVE_INFINITY;
        this.prev = null;
        this.scratch = 0;
    }

    @Override
    public boolean equals(Object other){
        if(other instanceof Vertex){
            Vertex otherV = (Vertex) other;
            return this.name.equals(otherV.name);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode(){
        return this.name.hashCode();
    }

    public String toString(){
        return name;
    }
}
