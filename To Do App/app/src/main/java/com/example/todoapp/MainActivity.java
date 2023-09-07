package com.example.todoapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText taskEditText;
    private Button addButton;
    private ListView taskListView;
    private ArrayAdapter<String> taskAdapter;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taskEditText = findViewById(R.id.taskEditText);
        addButton = findViewById(R.id.addButton);
        taskListView = findViewById(R.id.taskListView);
        dbHelper = new DatabaseHelper(this);

        taskAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dbHelper.getAllTasks());
        taskListView.setAdapter(taskAdapter);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String task = taskEditText.getText().toString().trim();
                if (!task.isEmpty()) {
                    dbHelper.insertTask(task);
                    taskAdapter.add(task);
                    taskEditText.getText().clear();
                } else {
                    Toast.makeText(MainActivity.this, "Task cannot be empty.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Add long-click listener to items for delete action
        taskListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Delete Task");
                builder.setMessage("Are you sure you want to delete this task?");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String taskToDelete = taskAdapter.getItem(position);
                        dbHelper.deleteTask(taskToDelete);
                        taskAdapter.remove(taskToDelete);
                        Toast.makeText(MainActivity.this, "Task deleted.", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("Cancel", null);
                builder.show();
                return true;
            }
        });

        // Add click listener to items for edit action
        taskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final String taskToUpdate = taskAdapter.getItem(position);
                final EditText editText = new EditText(MainActivity.this);
                editText.setText(taskToUpdate);

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Edit Task");
                builder.setView(editText);
                builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String updatedTask = editText.getText().toString().trim();
                        if (!updatedTask.isEmpty()) {
                            dbHelper.updateTask(taskToUpdate, updatedTask);
                            taskAdapter.remove(taskToUpdate);
                            taskAdapter.insert(updatedTask, position);
                            Toast.makeText(MainActivity.this, "Task updated.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Task cannot be empty.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("Cancel", null);
                builder.show();
            }
        });
    }
}
