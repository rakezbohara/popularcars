package com.techneekfactory.popularcars.popularcars;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.techneekfactory.popularcars.popularcars.extras.Faq;
import com.techneekfactory.popularcars.popularcars.extras.MyHolder;
import com.techneekfactory.popularcars.popularcars.extras.QuestionsModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 10/20/17.
 */


public class QuestionAnswerAdapter extends RecyclerView.Adapter<MyHolder>{

//
//    public QuestionAnswerAdapter(Context context, ArrayList<QuestionsModel> questionsModels) {
//        super(context, 0 , questionsModels);
////        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        // Get the data item for this position
//        QuestionsModel questionsModel = getItem(position);
//        // Check if an existing view is being reused, otherwise inflate the view
//        if (convertView == null) {
//            convertView = LayoutInflater.from(getContext()).inflate(R.layout.loans_question_answer, parent, false);
//        }
//        // Lookup view for data population
//        TextView tvName = (TextView) convertView.findViewById(R.id.question);
//        TextView tvHome = (TextView) convertView.findViewById(R.id.answer);
//        // Populate the data into the template view using the data object
//        tvName.setText(questionsModel.getQuestion());
//        tvHome.setText(questionsModel.getAnswer());
//        // Return the completed view to render on screen
//        return convertView;
//    }
ArrayList<QuestionsModel> questionsModels;
    Context context;


    public QuestionAnswerAdapter( Context ctx, ArrayList<QuestionsModel> questionsModels) {
        this.questionsModels = questionsModels;
        this.context=ctx;

    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyHolder viewHolder;

        View parentView = LayoutInflater.from(parent.getContext()).inflate(R.layout.loans_question_answer,
                null);
        MyHolder holder  = new MyHolder(parentView);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.bind(questionsModels.get(position));
        holder.txtItem.setText(questionsModels.get(position).getQuestion());
        holder.txtItem1.setText(questionsModels.get(position).getAnswer());


    }

    @Override
    public int getItemCount() {
        return questionsModels.size();
    }

}