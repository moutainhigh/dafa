import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Demo {


    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        for (int i = 4; i < 56; i++) {
            list.add(i);
        }
        System.out.println(list);
        System.out.println(get("11167", list));

    }

    //测试
    public static String get(String poker, List<Integer> list) {
        List<String> list1 = new ArrayList<>(Arrays.asList(poker.split("")));
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < list1.size(); i++) {
            int count = 40;
            int count2 = 0;
            int temp = 0;
            if (list1.get(i).equals("0")) {
                sb.append(list.remove(Integer.parseInt(list1.get(i))) + ",");
            } else {
                if (Integer.parseInt(list1.get(i)) == temp) {
                    sb.append(list.remove(Integer.parseInt(list1.get(i)) * 4 - 4) + ",");
                    count2++;
                }else{
                    temp=0;
                }
            }
        }
        return sb.toString();
    }
}
