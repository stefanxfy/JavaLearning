package annotationLearning;

import java.lang.annotation.*;

//检查String的长度是否符合要求
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface LengthCheck {
    int min() default 2;
    int max() default 4;
}
