package justemazine.justbrowser.contract;

import android.graphics.Bitmap;

import justemazine.justbrowser.entity.bo.TabInfo;

public interface ITab {
    TabInfo provideTabInfo();

    boolean onBackPressed();

    void goForward();

    void gotoHomePage();

    void loadUrl(String url);

    Bitmap getTabPreview();
}
