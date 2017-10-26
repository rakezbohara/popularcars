package layout;


import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.techneekfactory.popularcars.popularcars.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class WebViewFragment extends Fragment {

    Bundle bundle = null;
    String URLToShow = "";
    WebView webViewControl;
    private ProgressDialog webLoadingProgressBar;

    public WebViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_web_view, container, false);


        bundle = getArguments();
        URLToShow = bundle.getString("URL");

        webLoadingProgressBar = new ProgressDialog(getActivity());
        webLoadingProgressBar.setIndeterminate(true);
        webLoadingProgressBar.setCancelable(false);
        webLoadingProgressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        webLoadingProgressBar.setMessage("Loading...");




        webViewControl = (WebView) v.findViewById(R.id.webView);
        webViewControl.clearCache(true);

        webViewControl.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
        webViewControl.setLongClickable(false);
        webViewControl.getSettings().setJavaScriptEnabled(true);
        webViewControl.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                webLoadingProgressBar.show();
            }

            @Override
            public void onPageFinished(WebView view, String url)
            {
                webLoadingProgressBar.dismiss();
            }
        });



        webViewControl.loadUrl(URLToShow);
        webViewControl.reload();

        return v;
    }

}
