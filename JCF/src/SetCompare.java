import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;


public class SetCompare {
  public static void main(String[] args) {
    Set<Integer> hs = new HashSet<>();
    Collections.addAll(hs, 5, 3, 5, 1);
    System.out.println("HashSet (unordered): " + hs);

    TreeSet<Integer> ts = new TreeSet<>();
    Collections.addAll(ts, 5, 3, 5, 1);
    System.out.println("TreeSet (sorted): " + ts);
    System.out.println("first=" + ts.first() + ", last=" + ts.last());
  }
}
