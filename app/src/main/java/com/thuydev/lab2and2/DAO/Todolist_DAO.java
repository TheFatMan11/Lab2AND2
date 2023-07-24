package com.thuydev.lab2and2.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.thuydev.lab2and2.DBHelper.DBHelper_lab2;
import com.thuydev.lab2and2.DTO.Todolist_DTO;

import java.util.ArrayList;
import java.util.List;

public class Todolist_DAO {
    DBHelper_lab2 helperLab2;
    SQLiteDatabase db;

    public Todolist_DAO(Context context) {
        helperLab2 = new DBHelper_lab2(context);
        db = helperLab2.getWritableDatabase();
    }

    public long addRow(Todolist_DTO todolist_dto) {
        ContentValues values = new ContentValues();
        values.put("Title", todolist_dto.getTiTle());
        values.put("Content", todolist_dto.getConTent());
        values.put("Date", todolist_dto.getDaTe());
        values.put("Type", todolist_dto.getTyPe());
        values.put("trangthai", 0);
        return db.insert("tb_lichHoc", null, values);
    }

    public int updateRowstatus(Todolist_DTO todolist_dto) {
        ContentValues values = new ContentValues();

        values.put("trangthai", todolist_dto.getTrangthai());

        String[] index = new String[]{
                todolist_dto.getId() + ""
        };
        return db.update("tb_lichHoc", values, "id_todo=?", index);
    }

    public int deleteRow(Todolist_DTO todolist_dto) {
        String[] index = new String[]{
                todolist_dto.getId() + ""
        };
        return db.delete("tb_LichHoc", "id_todo=?", index);
    }

    public int updateRow(Todolist_DTO todolist_dto) {
        ContentValues values = new ContentValues();
        values.put("Title", todolist_dto.getTiTle());
        values.put("Content", todolist_dto.getConTent());
        values.put("Date", todolist_dto.getDaTe());
        values.put("Type", todolist_dto.getTyPe());
        values.put("trangthai", todolist_dto.getTrangthai());

        String[] index = new String[]{
                todolist_dto.getId() + ""
        };
        return db.update("tb_lichHoc", values, "id_todo=?", index);

    }

    public List<Todolist_DTO> getAll() {
        List<Todolist_DTO> list = new ArrayList<>();
        Cursor c = db.rawQuery("select * from tb_lichHoc", null);

        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            while (true) {
                String title = c.getString(0);
                String content = c.getString(1);
                String date = c.getString(2);
                String type = c.getString(3);
                int id = c.getInt(4);
                int trangthai = c.getInt(5);
                Todolist_DTO tl = new Todolist_DTO(id, title, content, date, type, trangthai);

                list.add(tl);
                if (c.moveToNext() == false) {
                    break;
                }
            }
        }
        return list;
    }
}
