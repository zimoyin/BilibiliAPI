import github.zimoyin.core.cookie.GlobalCookie;
import github.zimoyin.core.exception.CookieNotFoundException;
import org.junit.Test;

public class T {
    @Test
    public void s(){
        try {
            System.out.println(GlobalCookie.getInstance());
        } catch (CookieNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}