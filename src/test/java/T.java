import github.zimoyin.core.column.AnthologyInfo;
import github.zimoyin.core.cookie.GlobalCookie;
import github.zimoyin.core.exception.CodeException;
import github.zimoyin.core.exception.CookieNotFoundException;
import github.zimoyin.core.favorites.FavoriteUtil;
import github.zimoyin.core.favorites.info.UserFavorites;
import github.zimoyin.core.favorites.operation.CreateFavorite;
import github.zimoyin.core.favorites.operation.DeleteFavorite;
import github.zimoyin.core.favorites.operation.ModifyFavorite;
import github.zimoyin.core.search.SearchAll;
import github.zimoyin.core.search.SearchCategories;
import github.zimoyin.core.search.enums.SearchType;
import github.zimoyin.core.search.pojo.sall.Result;
import github.zimoyin.core.search.pojo.sall.SearchAllJsonRoot;
import github.zimoyin.core.search.pojo.search.SearchCategoriesJsonRoot;
import github.zimoyin.core.search.pojo.search.result.user.ResultUser;
import github.zimoyin.core.user.up.SearchUser;
import org.apache.http.HttpException;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

public class T {
    SearchType type;

    @Test
    public void s() throws HttpException, IOException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException, CodeException, CookieNotFoundException {
//        CreateFavorite createFavorite = new CreateFavorite(GlobalCookie.getInstance());
//        System.out.println(createFavorite.create("test"));

        FavoriteUtil util = new FavoriteUtil(GlobalCookie.getInstance());

//        System.out.println(util.getFavoriteID("子墨茵", "test1"));

    }

    public static void main(String[] args) throws CookieNotFoundException, IOException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException {
        AnthologyInfo anthologyInfo = new AnthologyInfo();
        System.out.println(anthologyInfo.getJsonPojo(343404));
    }
}