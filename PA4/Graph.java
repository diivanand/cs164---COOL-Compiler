import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: diivanand
 * Date: 12/5/13
 * Time: 8:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class Graph {
    public Map<String,Vertex> nameVertexMap;

    public Graph(){
    	 nameVertexMap = new HashMap<String, Vertex>();
    }
    

    public void addEdge(String sourceName, String destName, double cost){
        Vertex v = getVertex(sourceName);
        Vertex w = getVertex(destName);
        v.adj.add(new Edge(w, cost));
	nameVertexMap.put(v.name, v);
	nameVertexMap.put(w.name, w);
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

    public boolean hasCycle(){
	return getCycles().keySet().size() > 0;	
    }

    public Map<String, String> getCycles(){
        clearAll(); // initialize graph
	Map<String,String> cycles = new HashMap<String,String>();
        Set<Vertex> visitedSet = new HashSet<Vertex>();
        Set<Vertex> recursionStackSet = new HashSet<Vertex>();
        boolean cycle = false;
        for(Vertex v: vertexMap.values()){
            if(!visitedSet.contains(v)){
               	cycleCheckerHelper(v, visitedSet, recursionStackSet, cycles);
            }
        }
	return cycles;
    }

    private void cycleCheckerHelper(Vertex v, Set<Vertex> visitedSet, Set<Vertex> recursionStackSet, Map<String,String> cycles){
        visitedSet.add(v);
        recursionStackSet.add(v);
        for(Edge e: v.adj){
            if(!visitedSet.contains(e.v)){
                cycleCheckerHelper(e.v, visitedSet, recursionStackSet, cycles);
            } else if (recursionStackSet.contains(e.v)) {
                cycles.put(e.v.name, v.name);
            }
        }
        recursionStackSet.remove(v);
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

    // Precondition: assumes the graph is a tree.
    // WARNING: Output not defined for non-tree graphs, may not terminate
    // Output: a hashMap representing the subtree rooted at root
    // key is node name, value is parent name, null at root
    private Map<String, String> toNodeParentHashMap(Vertex root) {
    	Map<String, String> output = new HashMap<String, String>();
	Stack<Vertex> stack = new Stack<Vertex>();
	stack.push(root);
	output.put(root.name, null);
	while(!stack.isEmpty()){
		Vertex node = stack.pop();
		
		for(Edge e : node.adj) {
			output.put(e.v.name, node.name);
			stack.push(e.v);
		}
	}

	return output;
    }

    public Map<String, String> getNodeParentHashMap(String rootName){
    	return toNodeParentHashMap(nameVertexMap.get(rootName));
    }

    
    //Precondition: assumes graph is a tree and root node is "Object"
    // WARNING: Output not defined for non-tree graphs, may not terminate
    //Returns true if class1 is a subtype of class2
    //Returns false otherwise or if class1 or class2 or rootName are not in tree
    public boolean conforms(String class1, String class2, String rootName){
    	Map<String,String> nodeParentMap = toNodeParentHashMap(nameVertexMap.get(rootName));
	String c1 = class1;
	String c2 = class2;
	if(c1.equals(TreeConstants.No_type.toString()))
		return true;
	if(c2 == null)
		return false;
	while (c1 != null) {
		if(c1.length() >= 10 && c1.substring(0,10).equals("SELF_TYPE_") && c2.length() >= 10 && c2.substring(0,10).equals("SELF_TYPE_")){
			return true;
		} else if(c1.length() >= 10 && c1.substring(0,10).equals("SELF_TYPE_")) {
			return conforms(c1.substring(10, c1.length()), class2, rootName);
		} else if(c2.length() >= 10 && c2.substring(0,10).equals("SELF_TYPE_")){
			return false;
		} else {
			if(c1.equals(class2)){
				return true;
			}	
		}
		c1 = nodeParentMap.get(c1);
	}
	return false;
    }

    //Precondition: assumes graph is a tree and root node is "Object"
    // WARNING: Output not defined for non-tree graphs, may not terminate
    //Returns lub class name
    //Returns null if class1 or class2 or rootName are not in tree
    public String lub(String class1, String class2, String rootName){
    	//System.out.println("Entered lub");
	Map<String,String> nodeParentMap = toNodeParentHashMap(nameVertexMap.get(rootName));
	Stack<String> c1AncestorStack = new Stack<String>(); //includes c1 itself
	Stack<String> c2AncestorStack = new Stack<String>(); //includes c2 itself
	String c1 = class1;
	String c2 = class2;
	
	if(c1.length() >= 10 && c1.substring(0,10).equals("SELF_TYPE_") && c2.length() >= 10 && c2.substring(0,10).equals("SELF_TYPE_")){
		return lub(c1.substring(10,c1.length()), c2.substring(10,c2.length()), rootName);
	} else if(c1.length() >= 10 && c1.substring(0,10).equals("SELF_TYPE_")) {
		return lub(c1.substring(10, c1.length()), c2, rootName);
	} else if(c2.length() >= 10 && c2.substring(0,10).equals("SELF_TYPE_")){
		return  lub(c1, c2.substring(10, c2.length()), rootName);
	} else {
		//System.out.println("Entered else in lub");
		if(nameVertexMap.get(c1) == null || nameVertexMap.get(c2) == null){
			//System.out.println("One of the classes is not in our graph, lub returning null");
			return null;
		}
		while (c1 != null) {
			c1AncestorStack.push(c1);
			c1 = nodeParentMap.get(c1);
		}
		while (c2 != null) {
			c2AncestorStack.push(c2);
			c2 = nodeParentMap.get(c2);
		}
		if(c1AncestorStack.empty() || c2AncestorStack.empty()){
			//System.out.println("Lub stack empty, returning null");
			return null;
		}
		String lowestCommonAncestorSoFar = null;
		while(!c1AncestorStack.empty() && !c2AncestorStack.empty()){
			String c1Ancestor = c1AncestorStack.pop();
			String c2Ancestor = c2AncestorStack.pop();
			if(c1Ancestor.equals(c2Ancestor))
				lowestCommonAncestorSoFar = c1Ancestor;
			else
				break;
		}
		return lowestCommonAncestorSoFar;
	}
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
