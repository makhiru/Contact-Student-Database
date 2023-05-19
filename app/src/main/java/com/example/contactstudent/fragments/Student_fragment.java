package com.example.contactstudent.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.contactstudent.Models.Contact;
import com.example.contactstudent.Models.Student;
import com.example.contactstudent.R;
import com.example.contactstudent.adapters.StudentAdapter;
import com.example.contactstudent.database.DBhelper;
import com.example.contactstudent.interfaces.Student_onclick;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class Student_fragment extends Fragment {

    RecyclerView recyclerViewstudent;
    FloatingActionButton btn;
    DBhelper database;
    List<Student> allstudent = new ArrayList<>();
    StudentAdapter studentAdapter;

    public Student_fragment() {

    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_student_fragment, container, false);

        recyclerViewstudent = view.findViewById(R.id.studentrecycleview);
        btn = view.findViewById(R.id.btnstudentadd);

        database = new DBhelper(getContext());
        allstudent = database.getallstudent();

        recyclerViewstudent.setLayoutManager(new LinearLayoutManager(getContext()));
        studentAdapter = new StudentAdapter(getContext(), allstudent, onclick);
        recyclerViewstudent.setAdapter(studentAdapter);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog dialog = new Dialog(getContext());
                dialog.create();
                dialog.setContentView(R.layout.studentadddialog);
                dialog.setCancelable(false);

                EditText edtname = dialog.findViewById(R.id.studentaddname);
                EditText edtfname = dialog.findViewById(R.id.studentaddfname);
                EditText edtsname = dialog.findViewById(R.id.studentaddsname);
                EditText edtrollno = dialog.findViewById(R.id.studentaddrollno);
                Button btnadd = dialog.findViewById(R.id.sbtnadd);
                Button btncancel = dialog.findViewById(R.id.sbtncancel);

                btnadd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = edtname.getText().toString();
                        String fname = edtfname.getText().toString();
                        String sname = edtsname.getText().toString();
                        String rollno = edtrollno.getText().toString();

                        if (!name.isEmpty() && !fname.isEmpty() && !sname.isEmpty() && !rollno.isEmpty()) {
                            database.student_add(name, fname, sname, rollno);
                            refresh();
                            Toast.makeText(getContext(), "Student add!", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();

                        }
                    }
                });

                btncancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

        return view;
    }

    final Student_onclick onclick = new Student_onclick() {
        @SuppressLint("MissingInflatedId")
        @Override
        public void onclick(Student student) {

            LayoutInflater inflater = LayoutInflater.from(getContext());
            View view = inflater.inflate(R.layout.editstudent, null);

            EditText name = view.findViewById(R.id.sedtname);
            EditText fname = view.findViewById(R.id.edtfname);
            EditText sname = view.findViewById(R.id.edtsname);
            EditText rollno = view.findViewById(R.id.edtrollno);

            name.setText(student.getName());
            fname.setText(student.getFname());
            sname.setText(student.getSname());
            rollno.setText(student.getRollno());

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

            builder.create();
            builder.setCancelable(false);
            builder.setView(view);
            builder.setPositiveButton("Edit Contact", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    String txtname = name.getText().toString();
                    String txtfname = fname.getText().toString();
                    String txtsname = sname.getText().toString();
                    String txtrollno = rollno.getText().toString();

                    if (!txtname.isEmpty() && !txtfname.isEmpty() && !txtsname.isEmpty() && !txtrollno.isEmpty()) {
                        database.editstudent(new Student(student.getId(), txtname, txtfname, txtsname, txtrollno));
                        allstudent.clear();
                        allstudent.addAll(database.getallstudent());
                        studentAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                        Toast.makeText(getContext(), "Update  data!", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getContext(), "Please, Check Your Input!", Toast.LENGTH_SHORT).show();
                    }

                }
            });

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getContext(), "No, Edit Contact", Toast.LENGTH_SHORT).show();
                }
            });
            builder.show();
        }

        @Override
        public void onlongclick(Student student) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());

            dialog.setIcon(R.drawable.ic_baseline_delete_24);
            dialog.setTitle("Delete Contact");
            dialog.setMessage("Are you sure delete data?");
            dialog.create();
            dialog.setCancelable(false);
            dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    database.studentdeletedata(student.getId());
                    allstudent.clear();
                    allstudent.addAll(database.getallstudent());
                    refresh();

                    Toast.makeText(getContext(), "data Delete Succesfully!", Toast.LENGTH_SHORT).show();
                }
            });

            dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getContext(), "Cancel", Toast.LENGTH_SHORT).show();
                }
            });

            dialog.show();
        }
    };

    public void refresh() {
        allstudent.clear();
        allstudent.addAll(database.getallstudent());
        studentAdapter.notifyDataSetChanged();
    }
}