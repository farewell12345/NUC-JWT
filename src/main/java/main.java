import com.alibaba.fastjson.JSONObject;

public class main {
    public static void main(String[] args) {
        JSONObject A=new JSONObject();
        A.put("数学","150");
        try {
            String T=MyJWT.CreateToken(A);
            System.out.println(T);
            System.out.println(MyJWT.veifyToken(T).get("数学").asString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
