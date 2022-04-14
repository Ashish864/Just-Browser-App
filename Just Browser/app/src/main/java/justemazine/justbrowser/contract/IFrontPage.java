package justemazine.justbrowser.contract;

import java.util.List;

import justemazine.justbrowser.entity.dao.WebSite;

public interface IFrontPage {

    interface View {
        void showWebSite(List<WebSite> webSiteList);
    }

    interface Presenter {
        void getWebSite();
    }
}
