package user.up;

import github.zimoyin.bili.user.pojo.works.UserWorksRootBean;
import github.zimoyin.bili.user.pojo.works.Vlist;
import github.zimoyin.bili.user.up.UserWorks;
import org.junit.Test;

import java.util.Date;
import java.util.List;

public class UserWorksTest {
    @Test
    public void testUserWorks() throws Exception {
        UserWorks works = new UserWorks();
        UserWorksRootBean pojo = works.userWorksPojo(378885845, 50, 1);
        int count = pojo.getData().getPage().getCount();
        System.out.println("video count: " + count);
        List<Vlist> list = pojo.getData().getList().getVlist();
        System.out.println("return video number: " + list.size());

        for (int i = 2; i <= count / 50 + 1; i++) {
            UserWorksRootBean bean = works.userWorksPojo(378885845, 50, i);
            list.addAll(bean.getData().getList().getVlist());
        }

        System.out.println("final return video number: " + list.size());

        for (Vlist vlist : list) {
            System.out.println(new Date(vlist.getCreated() * 1000)+": "+vlist.getTitle());
        }
    }
}
