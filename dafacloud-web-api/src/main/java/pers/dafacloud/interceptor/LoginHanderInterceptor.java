package pers.dafacloud.interceptor;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import pers.dafacloud.config.Audience;
import pers.dafacloud.server.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 请求拦截器
 */
public class LoginHanderInterceptor implements HandlerInterceptor {

    @Autowired
    UserService userService;

    @Autowired
    private Audience audience;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //String xToken = request.getHeader("x-token");
        //System.out.println(xToken);
        //if (audience == null) {
        //    BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
        //    audience = (Audience) factory.getBean("audience");
        //}
        //try {
        //    JwtTokenUtil.parseJWT(xToken, audience.getBase64Secret());
        //} catch (Exception e) {
        //    //e.printStackTrace();
        //    // 验证token是否有效--无效已做异常抛出，由全局异常处理后返回对应信息
        //    response.setCharacterEncoding("UTF-8");
        //    response.setContentType("application/json; charset=utf-8");
        //
        //    JSONObject jsonObject = new JSONObject(true);
        //    jsonObject.put("code", 0);
        //    jsonObject.put("data", null);
        //    if(e.getMessage().equals("异常")){
        //        jsonObject.put("msg", "用户未登陆");
        //    }else {
        //        jsonObject.put("msg", "由于您长时间未操作，已自动退出，需要重新登录");
        //    }
        //    response.getWriter().write(jsonObject.toString());
        //    return false;
        //}
        HttpSession session = request.getSession();
        Object userInfo = session.getAttribute("user");
        if (userInfo == null) {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            response.setStatus(200);
            JSONObject jsonObject = new JSONObject(true);
            jsonObject.put("code", 0);
            jsonObject.put("data", null);
            jsonObject.put("msg", "由于您长时间未操作，已自动退出，需要重新登录");
            response.getWriter().write(jsonObject.toString());
            return false;
        }
        //else {
        //    //log.info("已经登录过啦，用户信息为：" + session.getAttribute("userInfo"));
        //    System.out.println("已经登录过啦，用户信息为：" + session.getAttribute("user"));
        //}
        return true;
    }
}
