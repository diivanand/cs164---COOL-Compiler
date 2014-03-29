/**
 * Created with IntelliJ IDEA.
 * User: diivanand
 * Date: 12/5/13
 * Time: 8:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class GraphExample {

    public static void main(String[] args){
        //Example 1
        System.out.println("Example 1:");
        System.out.println();
        Graph g = new Graph();
        g.addEdge("A","B",1000);
        g.addEdge("A","C",1);
        g.addEdge("C","A",10);
        g.addEdge("C","D",1);
        g.addEdge("E","C",6);
        g.addEdge("E","F",1);
        g.addEdge("F","D",7);
        g.addEdge("D","B",2);
        g.addEdge("D","H",5);
        System.out.println(g);
        System.out.println();
        g.dfs("E","H");
        g.bfs("E","H");
        g.ucs("E","H");
        System.out.println();
        g.dfs("A","B");
        g.bfs("A","B");
        g.ucs("A","B");
        String cycle = g.hasCycle() ? "Yes" : "No";
        System.out.println("Does Graph have a cycle?: " + cycle);
        //Example 2
        System.out.println();
        System.out.println("Example 2:");
        System.out.println();
        g = new Graph();
        g.addEdge("A","B",1000);
        g.addEdge("A","C",1);
        g.addEdge("A","D",1);
	System.out.println(g);
        System.out.println();
        cycle = g.hasCycle() ? "Yes" : "No";
        System.out.println("Does Graph have a cycle?: " + cycle);
        //Example 3
        System.out.println();
        System.out.println("Example 3:");
        System.out.println();
        g = new Graph();
        g.addEdge("A","B",1000);
        g.addEdge("A","C",1);
        g.addEdge("C","A",1);
	System.out.println(g);
        System.out.println();
        cycle = g.hasCycle() ? "Yes" : "No";
        System.out.println("Does Graph have a cycle?: " + cycle);
    }
}
