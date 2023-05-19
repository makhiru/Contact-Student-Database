package com.example.contactstudent.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contactstudent.Models.Student;
import com.example.contactstudent.R;
import com.example.contactstudent.interfaces.Student_onclick;

import java.util.ArrayList;
import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {

    Context context;
    List<Student> arrstudent = new ArrayList<>();
    Student_onclick onclick;

    public StudentAdapter(Context context, List<Student> arrstudent, Student_onclick onclick) {

        this.context = context;
        this.arrstudent = arrstudent;
        this.onclick = onclick;
    }

    public StudentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.addrowstudent, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StudentAdapter.ViewHolder holder, int position) {

        holder.name.setText(arrstudent.get(position).getName());
        holder.fname.setText(arrstudent.get(position).getFname());
        holder.sname.setText(arrstudent.get(position).getSname());
        holder.rollno.setText(arrstudent.get(position).getRollno());

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                onclick.onlongclick(arrstudent.get(holder.getAdapterPosition()));
                return true;
            }
        });

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onclick.onclick(arrstudent.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrstudent.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name, fname, sname, rollno;
        ImageView edit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.txtname);
            fname = itemView.findViewById(R.id.txtfname);
            sname = itemView.findViewById(R.id.txtsname);
            rollno = itemView.findViewById(R.id.txtrollno);
            edit = itemView.findViewById(R.id.editimg);

        }
    }
}
