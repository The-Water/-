package ui;

import java.util.ArrayList;
import java.util.Random;

public class CodeUtil {
    public static String getCode(){
        // 创建集合记录所有英文字符和数字
        ArrayList<Character> list=new ArrayList<>();
        for(int i=0;i<26;i++){
            list.add((char)('a'+i));
            list.add((char)('A'+i));
        }
        for(int i=0;i<=9;i++){
            list.add((char)('0'+i));
        }
        // 生成一个五位的验证码，
        Random r=new Random();
        StringBuilder ans=new StringBuilder("");
        for (int i = 0; i < 5; i++) {
            ans.append(list.get(r.nextInt(list.size())));
        }
        return ans.toString();
    }
}
