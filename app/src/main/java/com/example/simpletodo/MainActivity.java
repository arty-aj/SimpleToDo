package com.example.simpletodo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ArrayList<todoItem> todoItemList;
    private RecyclerView recyclerView;
    private itemAdapter adapter;
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.itemRV);
        todoItemList = new ArrayList<>();
        floatingActionButton = findViewById(R.id.floatingActionButton);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.add_update_layout);
                EditText title = dialog.findViewById(R.id.titleTv);
                EditText description = dialog.findViewById(R.id.descriptionTv);
                Button addButton = dialog.findViewById(R.id.submitBtn);

                addButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String title1 = "", description1 = "";
                        if(!title.getText().toString().equals("") && !description.getText().toString().equals("")) {
                            title1 = title.getText().toString();
                            title.setText("");
                            description1 = description.getText().toString();
                            description.setText("");
                            todoItemList.add(new todoItem(title1,description1));
                            adapter.notifyItemInserted(todoItemList.size() -1);
                            recyclerView.scrollToPosition(todoItemList.size() -1);
                            Toast.makeText(MainActivity.this, "Successfully added to list", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(MainActivity.this, "Please fill out both text boxes.", Toast.LENGTH_LONG).show();
                        }

                    }
                });
                dialog.show();

            }
        });

        setItemInfo();
        adapter = new itemAdapter(todoItemList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void setItemInfo() {
        todoItemList.add(new todoItem("thing one","Clean the dishes"));
        todoItemList.add(new todoItem("thing one","Clean the fridge"));
        todoItemList.add(new todoItem("thing one","Clean the stove"));
    }

}