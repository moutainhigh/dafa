package pers.dafacloud.utils.dataSource;


import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
// TYPE    用于描述类、接口(包括注解类型) 或enum声明
// FIELD    用于描述域
// METHOD   用于描述方法
// PARAMETER    用于描述参数
// CONSTRUCTOR  用于描述构造器
// LOCAL_VARIABLE   用于描述局部变量
// ANNOTATION_TYPE   注释类型声明
// PACKAGE     用于描述包
// TYPE_PARAMETER  类型参数声明
// TYPE_USE   类型的使用

//定义了该Annotation被保留的时间长短
@Retention(RetentionPolicy.RUNTIME)
//SOURCE  在源文件中有效（即源文件保留）
//CLASS  在class文件中有效（即class保留）
//RUNTIME  在运行时有效（即运行时保留）

//用于描述其它类型的annotation应该被作为被标注的程序成员的公共API，因此可以被例如javadoc此类的工具文档化
@Documented

//　@Inherited 元注解是一个标记注解，@Inherited阐述了某个被标注的类型是被继承的。如果一个使用了@Inherited修饰的annotation类型被用于一个class，
// 则这个annotation将被用于该class的子类。
//注意：@Inherited annotation类型是被标注过的class的子类所继承。类并不从它所实现的接口继承annotation，方法并不从它所重载的方法继承annotation。
//当@Inherited annotation类型标注的annotation的Retention是RetentionPolicy.RUNTIME，则反射API增强了这种继承性。
//如果我们使用java.lang.reflect去查询一个@Inherited annotation类型的annotation时，反射代码检查将展开工作：
//检查class和其父类，直到发现指定的annotation类型被发现，或者到达类继承结构的顶层。
@Inherited
public @interface MyDataSource {

    /**
     * 注解参数的可支持数据类型：
     * 　　   1.所有基本数据类型（int,float,boolean,byte,double,char,long,short)
     * 　　   2.String类型
     * 　　   3.Class类型
     * 　　   4.enum类型
     * 　　　5.Annotation类型
     * 　　　6.以上所有类型的数组
     */

    String value();//default DataSourceType.dev1
}
