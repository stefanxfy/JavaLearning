package annotationLearning;

import java.lang.reflect.Field;

public class Parser {
    public static void main(String[] args) {
        User u = new User();
        u.setName("zsf");
        try {
            Class userClass = Class.forName("annotationLearning.User");
            Class lcClass = Class.forName("annotationLearning.LengthCheck");
            Field[] fields = userClass.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(lcClass)) {
                    LengthCheck lc = (LengthCheck) field.getAnnotation(lcClass);
                    field.setAccessible(true);
                    String str = (String) field.get(u);
                    System.out.println(str);
                    int length = str.length();
                    if (lc.max() >= length && lc.min() <= length) {
                        System.out.println("字符串长度符合要求");
                    } else {
                        System.out.println("长度为" + lc.min() + "~" + lc.max());
                    }

                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
