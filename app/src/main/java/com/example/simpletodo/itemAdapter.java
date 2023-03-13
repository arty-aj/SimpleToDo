package com.example.simpletodo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class itemAdapter extends RecyclerView.Adapter<itemAdapter.myViewHolder> {
    private ArrayList<todoItem> itemsToDisplay;

    public itemAdapter(ArrayList<todoItem> itemsToDisplay) {
        this.itemsToDisplay = itemsToDisplay;
    }

    public class myViewHolder extends RecyclerView.ViewHolder{
        private TextView descriptionTV, titleTV;
        private LinearLayout linearLayout, toDoLinearLayout;
        private Button deleteButton, updateButton;
        private FloatingActionButton floatingActionButton;

        public myViewHolder(final View view){
            super(view);
            descriptionTV = view.findViewById(R.id.descriptiontv);
            titleTV = view.findViewById(R.id.titleTV);
            linearLayout = view.findViewById(R.id.linearLayout);
            toDoLinearLayout = view.findViewById(R.id.toDoLinearLayout);
            deleteButton = view.findViewById(R.id.deleteButton);
            updateButton = view.findViewById(R.id.updateButton);
            floatingActionButton = view.findViewById(R.id.floatingActionButton);
        }
    }

    @NonNull
    @Override
    public itemAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_item, parent, false);
        return new myViewHolder(itemView);
    }

    //change the text depending on the position in the array
    @Override
    public void onBindViewHolder(@NonNull itemAdapter.myViewHolder holder, int position) {
        String description = itemsToDisplay.get(holder.getAdapterPosition()).getDescription();
        holder.descriptionTV.setText(description);
        String title = itemsToDisplay.get(holder.getAdapterPosition()).getTitle();
        holder.titleTV.setText(title);

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext())
                        .setTitle("Delete Item")
                        .setMessage("Are you sure you want to delete this item?")
                        .setIcon(R.drawable.baseline_delete_24)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                itemsToDisplay.remove(holder.getAdapterPosition());
                                notifyItemRemoved(holder.getAdapterPosition());
                                Toast.makeText(view.getContext(), "Successfully deleted item", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                builder.show();
            }
        });

        holder.updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(view.getContext());
                dialog.setContentView(R.layout.add_update_layout);

                EditText title = dialog.findViewById(R.id.titleTv);
                EditText description = dialog.findViewById(R.id.descriptionTv);
                Button addButton = dialog.findViewById(R.id.submitBtn);
                TextView textTitle = dialog.findViewById(R.id.textTitle);


                textTitle.setText("Update to do item");
                addButton.setText("Update");

                title.setText((itemsToDisplay.get(holder.getAdapterPosition()).getTitle()));
                description.setText((itemsToDisplay.get(holder.getAdapterPosition()).getDescription()));


                addButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String title1 = "", description1 = "";
                        if(!title.getText().toString().equals("") && !description.getText().toString().equals("")) {
                            title1 = title.getText().toString();
                            title.setText("");
                            description1 = description.getText().toString();
                            description.setText("");
                            itemsToDisplay.set(holder.getAdapterPosition(), new todoItem(title1,description1));
                            notifyItemChanged(holder.getAdapterPosition());
                            dialog.dismiss();
                            Toast.makeText(textTitle.getContext(), "Successfully updated to object", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(textTitle.getContext(), "Please fill out both text boxes.", Toast.LENGTH_LONG).show();
                        }
                        dialog.dismiss();
                    }
                });
                dialog.show();

                addButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String title1 = "", description1 = "";
                        if(!title.getText().toString().equals("") && !description.getText().toString().equals("")) {
                            title1 = title.getText().toString();
                            title.setText("");
                            description1 = description.getText().toString();
                            description.setText("");
                            itemsToDisplay.set(holder.getAdapterPosition(), new todoItem(title1,description1));
                            notifyItemChanged(holder.getAdapterPosition());
                            dialog.dismiss();
                            Toast.makeText(textTitle.getContext(), "Successfully updated to object", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(textTitle.getContext(), "Please fill out both text boxes.", Toast.LENGTH_LONG).show();
                        }
                        dialog.dismiss();
                    }
                });
                dialog.show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return itemsToDisplay.size();
    }
}
