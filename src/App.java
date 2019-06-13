
public class App {

    public static void main(String[] args) {
        OwnHashMap<Integer, String> customHashMap = new OwnHashMap<>();
        for (int i = 0; i < 25; i++) {
            customHashMap.put(i, "value " + i);
            System.out.println(customHashMap.get(i));
        }
        System.out.println(customHashMap.getSize() + " SIZE");
        System.out.println(customHashMap.get(0) + " 0 INDEX ");
        customHashMap.put(0, "change value");
        System.out.println(customHashMap.get(0) + " 0 INDEX");
        System.out.println(customHashMap.remove(1) + " was removed \n");
        for (int i = 0; i < 25; i++) {
            System.out.println(customHashMap.get(i));
        }
        System.out.println(customHashMap.containsKey(1));
        System.out.println(customHashMap.containsKey(2));
        customHashMap.clear();
        System.out.println("get by key = 0 : " + customHashMap.get(0) + ". Is empty ?  - " + customHashMap.isEmpty());
        customHashMap.put(0, "new value");
        System.out.println("get by key = 0 : " + customHashMap.get(0) + ". Is empty ?  - " + customHashMap.isEmpty());
    }
}
