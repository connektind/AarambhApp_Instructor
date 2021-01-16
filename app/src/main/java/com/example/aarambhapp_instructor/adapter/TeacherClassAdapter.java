package com.example.aarambhapp_instructor.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aarambhapp_instructor.R;
import com.example.aarambhapp_instructor.activitys.DashboardActivity;
import com.example.aarambhapp_instructor.activitys.MainActivity;
import com.example.aarambhapp_instructor.model.TeacherClassModel;

import java.util.ArrayList;

public class TeacherClassAdapter extends RecyclerView.Adapter<TeacherClassAdapter.MyViewHolder> {
    Context context;
    ArrayList<TeacherClassModel> teacherClassModelArrayList;
    public TeacherClassAdapter(Context context, ArrayList<TeacherClassModel> teacherClassModelArrayList) {
        this.context=context;
        this.teacherClassModelArrayList=teacherClassModelArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_teacher, parent, false);
        return new TeacherClassAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
         holder.class_name.setText(teacherClassModelArrayList.get(position).getStudentclass());
         holder.liveCardView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent=new Intent(context, MainActivity.class);
                 intent.putExtra("Live_Url",teacherClassModelArrayList.get(position).getLiveClass());
                 context.startActivity(intent);
             }
         });
    }

    @Override
    public int getItemCount() {
        return teacherClassModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img_logo, img_arrow;
        TextView liveDisp, txtWebninars,class_name;
        CardView liveCardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            class_name = itemView.findViewById(R.id.class_name);
            img_arrow = itemView.findViewById(R.id.img_arrow);
            liveDisp = itemView.findViewById(R.id.liveDisp);
            txtWebninars = itemView.findViewById(R.id.txtWebninars);
            liveCardView = itemView.findViewById(R.id.liveCardView);
        }
    }
}
