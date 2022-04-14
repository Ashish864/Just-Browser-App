package justemazine.justbrowser.entity.dao;

public class DaoManager {
    public static void createDefaultSiteList(AppDatabase db) {
        try {

            WebSite bing = new WebSite(null, "Google", "https://www.google.com");
            WebSite yahoo = new WebSite(null, "Yahoo", "https://www.Yahoo.com");
            WebSite youtube = new WebSite(null, "Youtube", "https://youtube.com/c/TechnologybyAshishshrivastav");
            WebSite facebook = new WebSite(null, "Facebook", "https://www.facebook.com");


            db.webSiteDao().insertAllWebSite(bing);
            db.webSiteDao().insertAllWebSite(yahoo);
            db.webSiteDao().insertAllWebSite(youtube);
            db.webSiteDao().insertAllWebSite(facebook);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
