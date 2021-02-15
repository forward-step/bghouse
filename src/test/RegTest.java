import org.junit.Test;

// 测试正则表达式
public class RegTest {
    @Test
    public void username(){
        // 默认为手机号，2到11位的字母、数字、中文、下划线、减号
        String usernameReg = "^[\\u4e00-\\u9fa5|a-z|A-Z|0-9|-|_]{2,11}$";
        boolean matches = "吴杰辉吴杰辉".matches(usernameReg);
        System.out.println(matches);
    }
    @Test
    public void pwd(){
        // 6到18位的字母或者数字
        String pwdReg = "^[a-z|A-Z|0-9]{6,18}$";
        System.out.println("a12345_".matches(pwdReg)); // false
        System.out.println("a12345".matches(pwdReg)); // true
    }
    @Test
    public void realname(){
        // 最少俩个中文
        String realnameReg = "^[\\u4e00-\\u9fa5]{2,6}$";
        System.out.println("".matches(realnameReg));
        System.out.println("杰".matches(realnameReg));
        System.out.println("杰辉".matches(realnameReg));
        System.out.println("吴杰辉吴杰辉".matches(realnameReg));
        System.out.println("吴杰辉吴杰辉w".matches(realnameReg));
    }
    @Test
    public void phone(){
        String phoneReg = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8}$"; // 手机号的正则表达式
        System.out.println("13680422246".matches(phoneReg)); // true
        System.out.println("136804222461".matches(phoneReg)); // false
    }
    @Test
    public void idCard(){
        String idCardReg = "^[1-9]\\d{5}(18|19|20|(3\\d))\\d{2}((0[1-9])|(1[0-2]))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$"; // 身份证的正则表达式
        System.out.println("44078319981024153x".matches(idCardReg)); // true
        System.out.println("44078319981024153x1".matches(idCardReg)); // false
    }
}
