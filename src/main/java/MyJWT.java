import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import netscape.javascript.JSObject;

import java.sql.Time;
import java.util.*;
public class MyJWT {
    public static final String SECRET="JKKLJOoasdlfj";//token密钥,可以自己随便定，但是定好不要随便改
    public static final int calendarField= Calendar.DATE;//一个月中的第几天
    public static final int calendarInterval=10;//时效，token时效为10天
    MyJWT(){

    }
    /*创建一个token*/
    public static String CreateToken(JSONObject Grades) throws Exception{
        Date iatDate=new Date();
        Calendar nowTime=Calendar.getInstance();//获取时间戳
        nowTime.add(calendarField,calendarInterval);//设置时间格式
        Date expiresDate=nowTime.getTime();        //token的header
        JWTCreator.Builder A= JWT.create().withHeader(Grades);
        /*遍历json，添加进token的Claim中*/
        for (String i:Grades.keySet()) {
            A.withClaim(i, String.valueOf(Grades.get(i)));
        }
        return A.withIssuedAt(iatDate)
                .withExpiresAt(expiresDate)
                .sign(Algorithm.HMAC256(SECRET));
    }
    /*解析token，返回一个包含Claim对象的map，将Claim转成String就是成绩*/
    /*范例：
    * JSONObject A=new JSONObject();
        A.put("数学","150");
        try {
            String T=MyJWT.CreateToken(A);      #传入json构建一个token
            System.out.println(MyJWT.veifyToken(T).get("数学").asString());   #输出数学成绩为：150
        } catch (Exception e) {
            e.printStackTrace();
        }
     */
    public static Map<String, Claim> veifyToken(String Token){
        DecodedJWT jwt=null;
        try{
            JWTVerifier verifier=JWT.require(Algorithm.HMAC256(SECRET)).build();//构建
            jwt=verifier.verify(Token);
        }catch (Exception e){
            e.printStackTrace();
        }
        assert jwt != null;
        return jwt.getClaims();
    }

}
