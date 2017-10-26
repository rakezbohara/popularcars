package layout;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TabHost;

import com.techneekfactory.popularcars.popularcars.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ThirdPartyFinanceFragment extends Fragment {


    TabHost tabHost;
    WebView banksWebView, islamicWebView, financeWebView;
    private ProgressDialog webLoadingProgressBar;

    public ThirdPartyFinanceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_third_party_finance, container, false);


        banksWebView = v.findViewById(R.id.banksWebView);
        islamicWebView = v.findViewById(R.id.islamicWebView);
        financeWebView = v.findViewById(R.id.financeWebView);

        webLoadingProgressBar = new ProgressDialog(getActivity());
        webLoadingProgressBar.setIndeterminate(true);
        webLoadingProgressBar.setCancelable(false);
        webLoadingProgressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        webLoadingProgressBar.setMessage("Loading...");



        TabHost host = (TabHost) v.findViewById(R.id.tabHost);
        host.setup();

        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("Banks");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Banks");
        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec("Islamic");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Islamic");
        host.addTab(spec);

        //Tab 3
        spec = host.newTabSpec("Finance");
        spec.setContent(R.id.tab3);
        spec.setIndicator("Finance");
        host.addTab(spec);



        banksWebView.clearCache(true);
        banksWebView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
        banksWebView.setLongClickable(false);
        banksWebView.getSettings().setJavaScriptEnabled(true);
        banksWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                webLoadingProgressBar.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                webLoadingProgressBar.dismiss();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // open in Webview
                if (url.contains("android_asset") ){
                    // Can be clever about it like so where myshost is defined in your strings file
                    // if (Uri.parse(url).getHost().equals(getString(R.string.myhost)))
                    return false;
                }
                // open rest of URLS in default browser
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
                return true;
            }


        });

        banksWebView.loadUrl("http://popularcarsoman.dev.techneek.in/appfinance/banks");
        banksWebView.reload();




        islamicWebView.clearCache(true);
        islamicWebView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
        islamicWebView.setLongClickable(false);
        islamicWebView.getSettings().setJavaScriptEnabled(true);
        islamicWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                webLoadingProgressBar.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
//                webLoadingProgressBar.dismiss();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // open in Webview
                if (url.contains("android_asset") ){
                    // Can be clever about it like so where myshost is defined in your strings file
                    // if (Uri.parse(url).getHost().equals(getString(R.string.myhost)))
                    return false;
                }
                // open rest of URLS in default browser
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
                return true;
            }
        });

        islamicWebView.loadUrl("http://popularcarsoman.dev.techneek.in/appfinance/islamic-banking");
        islamicWebView.reload();



        financeWebView.clearCache(true);
        financeWebView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
        financeWebView.setLongClickable(false);
        financeWebView.getSettings().setJavaScriptEnabled(true);
        financeWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                webLoadingProgressBar.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
//                webLoadingProgressBar.dismiss();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // open in Webview
                if (url.contains("android_asset") ){
                    // Can be clever about it like so where myshost is defined in your strings file
                    // if (Uri.parse(url).getHost().equals(getString(R.string.myhost)))
                    return false;
                }
                // open rest of URLS in default browser
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
                return true;
            }


        });

        financeWebView.loadUrl("http://popularcarsoman.dev.techneek.in/appfinance/finance-companies");
        financeWebView.reload();







        return v;
    }

}
