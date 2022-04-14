package justemazine.justbrowser.contract;

import java.util.List;

import justemazine.justbrowser.entity.dao.History;

public interface IHistory {

    interface View {
        void showHistory(List<History> result);
        void showEmptyResult();
    }

    interface Presenter {
        void getHistory(int pageNo, int pageSize);
        void onDestroy();
    }
}
