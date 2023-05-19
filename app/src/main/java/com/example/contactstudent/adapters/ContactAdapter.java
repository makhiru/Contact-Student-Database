package com.example.contactstudent.adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contactstudent.Models.Contact;
import com.example.contactstudent.R;
import com.example.contactstudent.database.DBhelper;
import com.example.contactstudent.interfaces.Contact_onclick;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    Context context;
    List<Contact> arrcontact = new ArrayList<>();
    Contact_onclick onclick;
    DBhelper database;

    public ContactAdapter(Context context, List<Contact> arrcontact, Contact_onclick onclick) {

        this.context = context;
        this.arrcontact = arrcontact;
        this.onclick = onclick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.addrowcontact, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ContactAdapter.ViewHolder holder, int position) {

        holder.name.setText(arrcontact.get(position).getName());
        holder.number.setText(arrcontact.get(position).getNumber());

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                onclick.onlongclick(arrcontact.get(holder.getAdapterPosition()));
                return true;
            }
        });

        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onclick.onclick(arrcontact.get(holder.getAdapterPosition()),true);
            }
        });

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onclick.onclick(arrcontact.get(holder.getAdapterPosition()),false);
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrcontact.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name, number;
        LinearLayout llrow;
        ImageView call, edit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.txtname);
            number = itemView.findViewById(R.id.txtnumber);
            llrow = itemView.findViewById(R.id.llrow);
            call = itemView.findViewById(R.id.imgcall);
            edit = itemView.findViewById(R.id.imgedit);

        }
    }
}
