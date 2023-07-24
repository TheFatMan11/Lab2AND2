package com.thuydev.lab2and2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.thuydev.lab2and2.Adapter.Adapter_todolist;
import com.thuydev.lab2and2.DAO.Todolist_DAO;
import com.thuydev.lab2and2.DTO.Todolist_DTO;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Todolist_DAO todolist_dao;
    Todolist_DTO todolist_dto;
    List<Todolist_DTO> list;
    Adapter_todolist adapter_todolist;
    RecyclerView view;
    Calendar lich = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText title = findViewById(R.id.edt_title);
        EditText content = findViewById(R.id.edt_content);
        EditText date = findViewById(R.id.edt_date);
        EditText type = findViewById(R.id.edt_type);
        Button add = findViewById(R.id.btn_add);
        view = findViewById(R.id.rc_list);

        todolist_dao = new Todolist_DAO(this);
        list = todolist_dao.getAll();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        view.setLayoutManager(linearLayoutManager);
        adapter_todolist = new Adapter_todolist(this, list);
        view.setAdapter(adapter_todolist);

        int ngay = lich.get(Calendar.DAY_OF_MONTH);
        int thang = lich.get(Calendar.MONTH);
        int nam = lich.get(Calendar.YEAR);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog bangLich = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        date.setText(String.format("%d-%d-%d", dayOfMonth,month,year));
                    }
                }, nam, thang, ngay);
                bangLich.show();
            }
        });

        type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Vui lòng chọn mức độ");
                String[] mang= new String[]{
                        "Khó","Trung Bình","Dễ"
                };
                builder.setItems(mang, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        type.setText(mang[which]);
                        dialog.dismiss();
                    }
                });
                Dialog dialog = builder.create();
                dialog.show();
            }
        });


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!title.getText().toString().isEmpty()&&!content.getText().toString().isEmpty()&&!date.getText().toString().isEmpty()&&!type.getText().toString().isEmpty()){
                    todolist_dao.addRow(new Todolist_DTO(title.getText().toString(), content.getText().toString(), date.getText().toString(), type.getText().toString()));
                    list.clear();
                    list.addAll(todolist_dao.getAll());
                    adapter_todolist.notifyDataSetChanged();
                }else {
                    Toast.makeText(MainActivity.this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}