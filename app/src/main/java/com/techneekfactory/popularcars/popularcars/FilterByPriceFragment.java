package com.techneekfactory.popularcars.popularcars;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;




/**
 * A simple {@link Fragment} subclass.
 */
public class FilterByPriceFragment extends Fragment {


    GridView grid;
    String[] priceLabels = {
            "OMR 0 - OMR 1000",
            "OMR 1001 - OMR 2000",
            "OMR 2001 - OMR 3000",
            "OMR 3001 - OMR 4000",
            "OMR 4001 - OMR 5000",
            "OMR 5001 - OMR 10000",
            "OMR 10001 - OMR 15000",
            "OMR 15001 - OMR 20000",
            "OMR 20001 - OMR 25000",
            "OMR 25001 - OMR 30000",
            "OMR 30001 - OMR 35000",
            "OMR 35001 - OMR 40000",
            "OMR 40001 - OMR 45000",
            "OMR 45001 - OMR 50000",
            "OMR 50001 - OMR 55000",
            "OMR 55001 - OMR 60000"
    };

    String[] priceLabelValues = {
            "0|1000",
            "1001|2000",
            "2001|3000",
            "3001|4000",
            "4001|5000",
            "5001|10000",
            "10001|15000",
            "15001|20000",
            "20001|25000",
            "25001|30000",
            "30001|35000",
            "35001|40000",
            "40001|45000",
            "45001|50000",
            "50001|55000",
            "55001|60000"
    };


    public ProgressBar progressBar;
    ImageView arrowDown;

    public FilterByPriceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_filter_by_brand, container, false);
        progressBar = (ProgressBar) v.findViewById(R.id.progressBarBrandGrid);
        progressBar.setVisibility(View.GONE);
        arrowDown = v.findViewById(R.id.price_grid_arrow_down);
        arrowDown.setVisibility(View.VISIBLE);

        PriceGridAdapter adapter = new PriceGridAdapter(getContext(), priceLabels);
        grid = (GridView) v.findViewById(R.id.brandGridView);
        grid.setAdapter(adapter);
        grid.setNumColumns(2);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            grid.setNestedScrollingEnabled(true);
        }
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
//                Toast.makeText(getContext(), "You Clicked at " + priceLabels[+position], Toast.LENGTH_SHORT).show();


                // Start NewActivity.class
                Intent myIntent = new Intent(getActivity(),
                        BrowseCarsActivity.class);

                myIntent.putExtra("budget", String.valueOf(priceLabelValues[+position]));

                myIntent.putExtra("priceLabel", String.valueOf(priceLabels[+position]));
                myIntent.putExtra("type", "price");


                startActivity(myIntent);


            }
        });
        arrowDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                grid.smoothScrollToPosition(priceLabels.length);
            }
        });


        return v;
    }

}
