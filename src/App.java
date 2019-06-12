import java.util.HashMap;
import java.util.UUID;

public class App {

    public static void main(String[] args) {
    int y = 0;
        OwnHashMap<Integer, String> map = new OwnHashMap<>();
        HashMap<Integer,String> map2 = new HashMap<>();
          for(int i = 0; i < 25; i++){
              int x =UUID.randomUUID().hashCode();
              map.put(x,"value " + i);
              if(i==4) {
                  y = x;
              }
              map2.put(i,"value " + i);
          }

        System.out.println(map.getSize());
        System.out.println(map.get(y) + " ");
        map.put(y, "change value");
        System.out.println(map.get(y));


    }
}
