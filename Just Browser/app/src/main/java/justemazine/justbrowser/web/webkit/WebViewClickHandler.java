package justemazine.justbrowser.web.webkit;

import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

import justemazine.justbrowser.entity.bo.ClickInfo;
import justemazine.justbrowser.contract.IWebView;

public class WebViewClickHandler extends Handler {

    public static final String KEY_URL = "url";

    private WeakReference<IWebView> webViewWeakReference;

    public WebViewClickHandler(IWebView webView) {
        webViewWeakReference = new WeakReference<>(webView);
    }

    @Override
    public void handleMessage(Message msg) {
        IWebView webView = webViewWeakReference.get();
        if (webView == null || webView.getOnWebInteractListener() == null) {
            return;
        }
        if (msg.getData() == null) {
            return;
        }
        ClickInfo clickInfo = new ClickInfo();
        clickInfo.url = msg.getData().getString(KEY_URL);
        clickInfo.type = msg.what;
        webView.getOnWebInteractListener().onLongClick(clickInfo);
    }
}
