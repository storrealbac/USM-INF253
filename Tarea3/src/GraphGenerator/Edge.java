package GraphGenerator;

public class Edge implements Comparable<Edge> {
    public final Integer x;
    public final Integer y;

    public Edge(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    public int compareTo(Edge e) {
        int first = this.x.compareTo(e.x);
        int second = this.y.compareTo(e.y);
        return first != 0 ? first : second;
    }

}
