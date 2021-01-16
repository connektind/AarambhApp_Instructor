package com.example.aarambhapp_instructor.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.aarambhapp_instructor.R;
import com.example.aarambhapp_instructor.model.ChatMessageModel;

import java.util.ArrayList;

public class ChatBoxAdapter extends RecyclerView.Adapter<ChatBoxAdapter.MyViewHolder> {
    ArrayList<ChatMessageModel> MessageList;
    Context context;

    public ChatBoxAdapter(Context context, ArrayList<ChatMessageModel> MessageList) {
        this.context = context;
        this.MessageList = MessageList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView student_name;
        public TextView message;


        public MyViewHolder(View view) {
            super(view);

            student_name = (TextView) view.findViewById(R.id.studnet_name_chat);
            message = (TextView) view.findViewById(R.id.message_chat);

        }
    }


    @Override
    public int getItemCount() {
        return MessageList.size();
    }

    @Override
    public ChatBoxAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chat_msg, parent, false);
        return new ChatBoxAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ChatBoxAdapter.MyViewHolder holder, final int position) {
        final ChatMessageModel m = MessageList.get(position);
        Log.e("name & Msg", m.getNickname() + "/" + m.getMessage());
        holder.student_name.setText(m.getNickname() + " : ");

        holder.message.setText(m.getMessage());
    }


}
