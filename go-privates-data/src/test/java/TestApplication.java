import org.apache.commons.lang3.StringUtils;

/**
 * @author zhanglifeng
 * @since 2024-02-28
 */
public class TestApplication {

    public static void main(String[] args) {

        String code = "YQFQ-2001-2067-00020240116163431623-1";
        String substring = StringUtils.substring(code, -6);
//        String substring = StringUtils.substring(code, code.length() - 6);
        System.out.println("substring = " + substring);
    }
}
