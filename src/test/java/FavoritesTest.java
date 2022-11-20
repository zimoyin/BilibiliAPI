import github.zimoyin.bili.cookie.Cookie;
import github.zimoyin.bili.cookie.GlobalCookie;
import github.zimoyin.bili.cookie.WebCookie;
import github.zimoyin.bili.favorites.info.UserFavorites;
import github.zimoyin.bili.favorites.pojo.userfavorites.FavList;
import org.junit.Test;

import java.util.List;

public class FavoritesTest {
    @Test
    public void testUserAllFavorites() throws Exception {
        UserFavorites favorites = new UserFavorites(GlobalCookie.getInstance());
        for (FavList favList : favorites.getJsonPojo(204700919).getData().getList()) {
            System.out.println(favList.getTitle()+": "+favList.getFid());
        }

    }
}
