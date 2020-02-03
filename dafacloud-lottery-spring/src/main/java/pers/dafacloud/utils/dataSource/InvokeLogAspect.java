package pers.dafacloud.utils.dataSource;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import pers.dafacloud.configuration.DataSourceType;

import java.lang.reflect.Method;

@Aspect
@Component
public class InvokeLogAspect {

    private static Logger log = LoggerFactory.getLogger(InvokeLogAspect.class);

    // 切点
    @Pointcut("@annotation(pers.dafacloud.utils.dataSource.MyDataSource)")//注解切面
    public void executePointCut() {
    }

    //方法调用之前
    @Before("executePointCut()")
    public void before(JoinPoint joinPoint) {
        //log.info("在切面中修改数据源");
        MethodSignature sign = (MethodSignature) joinPoint.getSignature();
        Method method = sign.getMethod();
        MyDataSource annotation = method.getAnnotation(MyDataSource.class);
        //判断不为空并且为读库
        if (!StringUtils.isEmpty(annotation.value()) && annotation.value().equals(DataSourceType.dev1)) {
            DataSourceContextHolder.setDev1();
        } else if (annotation.value().equals(DataSourceType.proBetting)) {
            DataSourceContextHolder.setProBetting();
        } else {
            DataSourceContextHolder.setLocal();
        }
    }
}
