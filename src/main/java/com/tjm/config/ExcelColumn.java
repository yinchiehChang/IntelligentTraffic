package com.tjm.config;

import java.lang.annotation.*;

//自定义实体类需要的bean(Excel属性标题、位置等)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExcelColumn {

    //Excel标题
    String value() default "";

    //Excel从左到右排列位置
    int col() default 0;
}
