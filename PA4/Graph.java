import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: diivanand
 * Date: 12/5/13
 * Time: 8:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class Graph {
    public void addEdge(String sourceName, String destName, double cost){
        Vertex v = getVertex(sourceName);
        Vertex w = getVertex(destName);
        v.adj.add(new Edge(w, cost));
    }

    private Vertex getVertex(String vertexName){
        if(vertexName == null){
            return null;
        }

        Vertex v = vertexMap.get(vertexName);
        if(v == null){
            v = new Vertex(vertexName);
            vertexMap.put(vertexName, v);
        }
        return v;
    }

    private void clearAll(){
        for(Vertex v : vertexMap.values()){
            v.reset();
        }
    }

    private void printPath(Vertex dest) {
        if (dest.prev != null) {
            printPath(dest.prev);
            System.out.print(" to ");
        }
        System.out.print(dest.name);
    }

    public void printPath(String destName){
        Vertex w = vertexMap.get(destName);
        if (w == null) {
            throw new NoSuchElementException();
        } else if (Double.isInfinite(w.dist)){
            System.out.println(destName + " is unreachable");
        } else {
            System.out.print( "(Cost is: " + w.dist + ") ");
            printPath(w);
            System.out.println();
        }
    }

    public void dfs(String startName, String goalName){
        clearAll(); // initialize graph
        Set<Vertex> exploredSet = new HashSet<Vertex>();
        Stack<Vertex> nodes = new Stack<Vertex>();
        Vertex v = vertexMap.get(startName);
        Vertex goal = vertexMap.get(goalName);
        v.dist = 0;
        nodes.push(v);
        while(!nodes.isEmpty()){
             v = nodes.pop();
             exploredSet.add(v);
             if(v.equals(goal)){
                 printPath(v.name);
                 return;
             }
             for(Edge e : v.adj){
                 if(!exploredSet.contains(e.v)){
                     e.v.prev = v;
                     e.v.dist = v.dist + e.cost;
                     nodes.push(e.v);
                 }
             }
        }
        System.out.println("Could not find goal vertex");
    }

    public void bfs(String startName, String goalName){
        clearAll(); // initialize graph
        Set<Vertex> exploredSet = new HashSet<Vertex>();
        Queue<Vertex> nodes = new LinkedList<Vertex>();
        Vertex v = vertexMap.get(startName);
        Vertex goal = vertexMap.get(goalName);
        v.dist = 0;
        nodes.add(v);
        while(!nodes.isEmpty()){
            v = nodes.poll();
            exploredSet.add(v);
            if(v.equals(goal)){
                printPath(v.name);
                return;
            }
            for(Edge e : v.adj){
                if(!exploredSet.contains(e.v)){
                    e.v.prev = v;
                    e.v.dist = v.dist + e.cost;
                    nodes.add(e.v);
                }
            }
        }
        System.out.println("Could not find goal vertex");
    }

    public void ucs(String startName, String goalName){
        clearAll(); // initialize graph
        Set<Vertex> exploredSet = new HashSet<Vertex>();
        PriorityQueue<Vertex> nodes = new PriorityQueue<Vertex>(vertexMap.size(),new Comparator<Vertex>(){
            public int compare(Vertex v1, Vertex v2){
                double diff = v1.dist - v2.dist;
                if (diff < 0){
                    return -1;
                } else if (diff > 0) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });
        Vertex v = vertexMap.get(startName);
        Vertex goal = vertexMap.get(goalName);
        v.dist = 0;
        nodes.add(v);
        while(!nodes.isEmpty()){
            v = nodes.poll();
            exploredSet.add(v);
            if(v.equals(goal)){
                printPath(v.name);
                return;
            }
            for(Edge e : v.adj){
                if(!exploredSet.contains(e.v)){
                    e.v.prev = v;
                    e.v.dist = v.dist + e.cost;
                    nodes.add(e.v);
                }
            }
        }
        System.out.println("Could not find goal vertex");
    }

    public String toString(){
        String vertices = "Vertices: " + Arrays.toString(vertexMap.keySet().toArray()) + "\n";
        StringBuilder sb = new StringBuilder("Edges: ");
        for(Vertex v : vertexMap.values()){
            String vTo = "(" + v.name + ",";
            for(Edge e : v.adj){
                String edge = vTo + e.v.name + ")- " + e.cost + ",";
                sb.append(edge);
            }
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append('\n');
        return vertices + sb.toString();
    }

    private Map<String, Vertex> vertexMap = new HashMap<String, Vertex>();
}
