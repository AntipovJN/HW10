public class App {

    public static void main(String[] args) {
        OwnHashMap<Integer, String> customHashMap= new OwnHashMap<>();
        for (int i = 0; i < 25; i++) {
            customHashMap.put(i, "value " + i);
            System.out.println(customHashMap.get(i));
        }
        System.out.println(customHashMap.getSize() + " SIZE");
        System.out.println(customHashMap.get(0) + " 0 INDEX ");
        customHashMap.put(0, "change value");
        System.out.println(customHashMap.get(0) + " 0 INDEX");
    }
}
