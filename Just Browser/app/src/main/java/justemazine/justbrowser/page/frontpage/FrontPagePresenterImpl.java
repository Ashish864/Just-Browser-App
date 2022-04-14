package justemazine.justbrowser.page.frontpage;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.ref.WeakReference;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import justemazine.justbrowser.EasyApplication;
import justemazine.justbrowser.contract.IFrontPage;
import justemazine.justbrowser.entity.dao.AppDatabase;
import justemazine.justbrowser.entity.dao.DaoManager;
import justemazine.justbrowser.entity.dao.WebSite;
import justemazine.justbrowser.utils.SharedPreferencesUtils;

public class FrontPagePresenterImpl implements IFrontPage.Presenter {

    private WeakReference<Context> mContext;
    private WeakReference<IFrontPage.View> mView;
    private CompositeDisposable mDisposable = new CompositeDisposable();

    public FrontPagePresenterImpl(Context context, IFrontPage.View view) {
        this.mContext = new WeakReference<>(context);
        this.mView = new WeakReference<>(view);
    }

    @Override
    public void getWebSite() {
        Disposable dps = Observable.create(new ObservableOnSubscribe<List<WebSite>>() {

            @Override
            public void subscribe(ObservableEmitter<List<WebSite>> emitter) throws Exception {
                Context _context = mContext.get();
                if (_context == null) {
                    emitter.onError(new NullPointerException());
                    return;
                }

                final EasyApplication application = (EasyApplication) _context.getApplicationContext();
                AppDatabase db = application.getAppDatabase();
                SharedPreferences sp = SharedPreferencesUtils.getSettingSP(_context);
                if (sp == null) {
                    emitter.onError(new NullPointerException());
                    return;
                }

                boolean firstBoot = sp.getBoolean(SharedPreferencesUtils.KEY_FIRST_BOOT, true);
                boolean siteListCreated = sp.getBoolean(SharedPreferencesUtils.KEY_SITE_LIST_CREATED, false);
                if (firstBoot && !siteListCreated) {
                    DaoManager.createDefaultSiteList(db);
                    sp.edit().putBoolean(SharedPreferencesUtils.KEY_SITE_LIST_CREATED, true).apply();
                }
                List<WebSite> result = db.webSiteDao().getAll();
                emitter.onNext(result);
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<WebSite>>() {
                    @Override
                    public void accept(List<WebSite> webSiteList) throws Exception {
                        // handle result
                        IFrontPage.View _view = mView.get();
                        if (_view == null || webSiteList == null) {
                            return;
                        }

                        _view.showWebSite(webSiteList);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        // handle error
                    }
                });
        mDisposable.add(dps);
    }
}
