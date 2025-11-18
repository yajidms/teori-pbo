import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class MapCompare {
  public static void main(String[] args) {
    Map<Integer, String> hm = new HashMap<>();
    hm.put(20, "dua puluh"); hm.put(5, "lima"); hm.put(12, "dua belas");
    System.out.println("HashMap (unordered iteration): " + hm);

    TreeMap<Integer, String> tm = new TreeMap<>();
    tm.put(20, "dua puluh"); tm.put(5, "lima"); tm.put(12, "dua belas");
    System.out.println("TreeMap (sorted by key): " + tm);
    System.out.println("ceilingKey(10) = " + tm.ceilingKey(10));
  }
}
