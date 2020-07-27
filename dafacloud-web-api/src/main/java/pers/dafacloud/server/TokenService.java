//package pers.dafacloud.server;
//
//import com.auth0.jwt.JWT;
//import com.auth0.jwt.algorithms.Algorithm;
//import org.springframework.stereotype.Service;
//import pers.dafacloud.entity.User;
//
//import java.util.Date;
//
//@Service
//public class TokenService {
//
//    public String getToken(User user) {
//        Date start = new Date();
//        long currentTime = System.currentTimeMillis() + 60 * 60 * 1000;//一小时有效时间
//        Date end = new Date(currentTime);
//        return JWT.create().withAudience(user.getUserId()).withIssuedAt(start).withExpiresAt(end)
//                .sign(Algorithm.HMAC256(user.getPassword()));
//    }
//}
