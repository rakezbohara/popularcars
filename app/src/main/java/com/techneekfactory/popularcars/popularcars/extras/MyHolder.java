package com.techneekfactory.popularcars.popularcars.extras;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;



import com.techneekfactory.popularcars.popularcars.R;

/**
 * Created by Hp on 3/17/2016.
 */
public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    //OUR VIEWS

    public TextView txtItem,txtItem1;
    private final Context context;

    private QuestionsModel currentItem;




    public MyHolder(View itemView) {
        super(itemView);
        context = itemView.getContext();

        this.txtItem= (TextView) itemView.findViewById(R.id.question);
        this.txtItem1= (TextView) itemView.findViewById(R.id.answer);


        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
//        this.itemClickListener.onItemClick(v,getLayoutPosition());

    }

    public void bind(QuestionsModel questionsModel) {
        txtItem.setText(questionsModel.getQuestion());
        txtItem1.setText(questionsModel.getAnswer());
        currentItem= questionsModel;
    }


}
