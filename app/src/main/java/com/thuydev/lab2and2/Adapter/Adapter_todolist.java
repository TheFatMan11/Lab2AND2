package com.thuydev.lab2and2.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thuydev.lab2and2.DAO.Todolist_DAO;
import com.thuydev.lab2and2.DTO.Todolist_DTO;
import com.thuydev.lab2and2.MainActivity;
import com.thuydev.lab2and2.R;

import java.util.Calendar;
import java.util.List;

public class Adapter_todolist extends RecyclerView.Adapter<Adapter_todolist.Viewholder> {

    Context context;
    List<Todolist_DTO> list;
    Todolist_DTO todolist_dto;
    Todolist_DAO todolistDao;
    Calendar lich = Calendar.getInstance();
    public Adapter_todolist(Context context, List<Todolist_DTO> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_list, parent, false);

        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        holder.tv_name.setText(list.get(position).getTiTle());
        holder.tv_ngay.setText(list.get(position).getDaTe());
        todolist_dto =list.get(position);
        todolistDao = new Todolist_DAO(context);


        if (todolist_dto.getTrangthai() == 1) {
            holder.sta.setChecked(true);

        }
        holder.sta.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                todolist_dto =list.get(position);
                if(isChecked){
                    todolist_dto.setTrangthai(1);
                    if(todolistDao.updateRowstatus(todolist_dto)>0){
                        Toast.makeText(context, "Bạn đã hoàn thành", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
        holder.sua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater inflater = ((Activity)context).getLayoutInflater();
                View view = inflater.inflate(R.layout.dialog_sua,null);
                builder.setView(view);
                Dialog dialog =builder.create();
                dialog.show();
                EditText title = view.findViewById(R.id.edt_title_up);
                EditText content = view.findViewById(R.id.edt_content_up);
                EditText date = view.findViewById(R.id.edt_date_up);
                EditText type = view.findViewById(R.id.edt_type_up);
                Button sua = view.findViewById(R.id.btn_sua);
                Button cancel = view.findViewById(R.id.btn_cancel);
               todolist_dto =list.get(position);
                todolistDao = new Todolist_DAO(context);

                int ngay = lich.get(Calendar.DAY_OF_MONTH);
                int thang = lich.get(Calendar.MONTH);
                int nam = lich.get(Calendar.YEAR);
                date.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatePickerDialog bangLich = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
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
                        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context);
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
                if(todolist_dto!=null){
                    title.setText(todolist_dto.getTiTle());
                    content.setText(todolist_dto.getConTent());
                    date.setText(todolist_dto.getDaTe());
                    type.setText(todolist_dto.getTiTle());
                }

                sua.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        todolist_dto.setTiTle(title.getText().toString());
                        todolist_dto.setConTent(content.getText().toString());
                        todolist_dto.setDaTe(date.getText().toString());
                        todolist_dto.setTyPe(type.getText().toString());
                        if (todolistDao.updateRow(todolist_dto) > 0) {
                            Toast.makeText(context, "Hoàn tất chỉnh sửa", Toast.LENGTH_SHORT).show();
                            list.clear();
                            list.addAll(todolistDao.getAll());
                            notifyDataSetChanged();
                            dialog.dismiss();
                        }else {
                            Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                        }



                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });


            }
        });
        holder.xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Todolist_DAO todolistDao = new Todolist_DAO(context);
                Todolist_DTO todolist_dto = list.get(holder.getPosition());
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
                builder.setTitle("Thông báo");
                builder.setIcon(R.drawable.baseline_warning_24);
                builder.setMessage("Bạn có chắn muốn xóa công việc này không ?");

                builder.setNegativeButton("KHông", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (todolistDao.deleteRow(todolist_dto) > 0) {
                            Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                            list.clear();
                            list = todolistDao.getAll();
                            notifyItemRemoved(holder.getPosition());
                        } else {
                            Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                        }
                        dialog.dismiss();
                    }
                });
                Dialog dialog = builder.create();
                dialog.show();
            }



        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class Viewholder extends RecyclerView.ViewHolder {
        TextView tv_name, tv_ngay;
        ImageButton sua, xoa;
        CheckBox sta;


        public Viewholder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name_mon);
            tv_ngay = itemView.findViewById(R.id.tv_ngay);
            sua = itemView.findViewById(R.id.ibtn_update);
            xoa = itemView.findViewById(R.id.ibtn_delete);
            sta = itemView.findViewById(R.id.cbk_);
        }
    }
}
