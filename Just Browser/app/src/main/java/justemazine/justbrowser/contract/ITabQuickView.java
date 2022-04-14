package justemazine.justbrowser.contract;

import java.util.List;

import justemazine.justbrowser.entity.bo.TabInfo;

public interface ITabQuickView {

    interface Subject {
        void attach(Observer observer);

        void detach();

        List<TabInfo> provideInfoList();

        void updateTabInfo(TabInfo info);
    }

    interface Observer {
        void updateQuickView();
    }
}
