package com.example.contactstudent.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.contactstudent.Models.Contact;
import com.example.contactstudent.R;
import com.example.contactstudent.adapters.ContactAdapter;
import com.example.contactstudent.database.DBhelper;
import com.example.contactstudent.interfaces.Contact_onclick;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class Contact_fragment extends Fragment {

    RecyclerView recyclerViewcontact;
    ContactAdapter contactAdapter;
    FloatingActionButton btn;
    List<Contact> allcontact = new ArrayList<>();
    DBhelper database;


    public Contact_fragment() {

    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_contact_fragment, container, false);

        recyclerViewcontact = view.findViewById(R.id.contactrecycleview);
        btn = view.findViewById(R.id.btncontactadd);

        database = new DBhelper(getContext());
        allcontact = database.getallcontact();

        recyclerViewcontact.setLayoutManager(new LinearLayoutManager(getContext()));
        contactAdapter = new ContactAdapter(getContext(), allcontact, onclick);
        recyclerViewcontact.setAdapter(contactAdapter);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog dialog = new Dialog(getContext());

                dialog.create();
                dialog.setContentView(R.layout.contactadddialog);
                dialog.setCancelable(false);

                EditText edtname = dialog.findViewById(R.id.contactaddname);
                EditText edtnumber = dialog.findViewById(R.id.contactaddnumber);
                Button btnadd = dialog.findViewById(R.id.cbtnadd);
                Button btncancel = dialog.findViewById(R.id.cbtncancel);

                btnadd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String name = edtname.getText().toString();
                        String number = edtnumber.getText().toString();

                        if (!name.isEmpty() && !number.isEmpty()) {

                            if (number.length() == 10) {

                                database.contact_add(name, number);
                                refresh();
                                dialog.dismiss();
                                Toast.makeText(getContext(), "Data Add Sucessfully!", Toast.LENGTH_SHORT).show();

                            } else {
                                edtnumber.setError("Enter 10 digit number!");
                            }
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

    public void refresh() {
        allcontact.clear();
        allcontact.addAll(database.getallcontact());
        contactAdapter.notifyDataSetChanged();
    }

    final Contact_onclick onclick = new Contact_onclick() {
        @SuppressLint("MissingInflatedId")
        @Override
        public void onclick(Contact contact, boolean call) {

            if (call) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel: +91" + contact.getNumber()));
                startActivity(intent);
            } else {

                LayoutInflater inflater = LayoutInflater.from(getContext());
                View subview = inflater.inflate(R.layout.editcontact, null);

                EditText name = subview.findViewById(R.id.edtname);
                EditText number = subview.findViewById(R.id.edtnumber);

                name.setText(contact.getName());
                number.setText(contact.getNumber());

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                builder.setCancelable(false);
                builder.setView(subview);
                builder.create();
                builder.setPositiveButton("EDIT CONTACT", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        String txtname = name.getText().toString();
                        String txtnumber = number.getText().toString();

                        if (!txtname.isEmpty() && !txtnumber.isEmpty()) {

                            database.editcontact(new Contact(contact.getId(), txtname, txtnumber));
                            allcontact.clear();
                            allcontact.addAll(database.getallcontact());
                            contactAdapter.notifyDataSetChanged();
                            dialogInterface.dismiss();
                            Toast.makeText(getContext(), "Update  data!", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(getContext(), "Please, Check Your Input!", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Toast.makeText(getContext(), "No, Edit Contact", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();

            }
        }

        @Override
        public void onlongclick(Contact contact) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());

            dialog.setIcon(R.drawable.ic_baseline_delete_24);
            dialog.setTitle("Delete Contact");
            dialog.setMessage("Are you sure delete data?");
            dialog.create();
            dialog.setCancelable(false);
            dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    database.contactdeletedata(contact.getId());
                    allcontact.clear();
                    allcontact.addAll(database.getallcontact());
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
}