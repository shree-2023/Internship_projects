package com.example.todoapp.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.AddNewTask;
import com.example.todoapp.Home;
import com.example.todoapp.MainActivity;
import com.example.todoapp.Model.Todomodel;
import com.example.todoapp.R;
import com.example.todoapp.Utils.DatabaseHelper;

import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.MyViewHolder> {
    private List<Todomodel> mList;
    private Home activity;
    private DatabaseHelper myDB;
    public ToDoAdapter(DatabaseHelper myDB,Home activity){
        this.activity=activity;
        this.myDB=myDB;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout,parent,false);
      return  new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
    final Todomodel item=mList.get(position);
    holder.mcheckBox.setText(item.getTask());
    holder.mcheckBox.setChecked(toBoolean(item.getStatus()));
    holder.mcheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked){
                myDB.updateStatus(item.getId(),1);
            }else{
                myDB.updateStatus(item.getId(),0);
            }
        }
    });

    }
    public boolean toBoolean(int num){
        return num!=0;
    }
    public Context getContext(){
        return activity;
    }
    public void setTasks(List<Todomodel> mList){
        this.mList=mList;
        notifyDataSetChanged();
    }
    public void deleteTask(int position){
        Todomodel item=mList.get(position);
        myDB.deleteTask(item.getId());
        mList.remove(position);
        notifyItemRemoved(position);
    }
    public void editItem(int position){
        Todomodel item=mList.get(position);

        Bundle bundle=new Bundle();
        bundle.putInt("id",item.getId());
        bundle.putString("task",item.getTask());

        AddNewTask task=new AddNewTask();
        task.show(activity.getSupportFragmentManager(),task.getTag());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class  MyViewHolder extends RecyclerView.ViewHolder{
    CheckBox mcheckBox;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mcheckBox=itemView.findViewById(R.id.mcheckbox);
        }
    }


}
