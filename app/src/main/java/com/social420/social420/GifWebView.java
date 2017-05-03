package com.social420.social420;

import android.content.Context;
import android.webkit.WebView;

/**
 * Created by pradeeppj on 09/04/17.
 */

public class GifWebView extends WebView {

    public GifWebView(Context context, String path) {
        super(context);

        loadUrl(path);
    }
}