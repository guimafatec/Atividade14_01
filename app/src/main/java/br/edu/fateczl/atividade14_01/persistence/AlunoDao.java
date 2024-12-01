package br.edu.fateczl.atividade14_01.persistence;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.edu.fateczl.atividade14_01.model.Aluno;

public class AlunoDao implements ICRUDDao<Aluno>, IAlunoDao{

    private final Context context;
    private GenericDao gDao;
    private SQLiteDatabase db;
    public AlunoDao(Context context) {
        this.context = context;
    }

    @Override
    public AlunoDao open() throws SQLException {
        gDao = new GenericDao(context);
        db = gDao.getWritableDatabase();
        return this;
    }

    @Override
    public void close() {
        gDao.close();
    }

    @Override
    public void insert(Aluno aluno) throws SQLException {
        ContentValues contentValues = getContentValues(aluno);
        db.insert("aluno", null, contentValues);
    }

    @Override
    public void update(Aluno aluno) throws SQLException {
        ContentValues contentValues = getContentValues(aluno);
        db.update("aluno", contentValues, "ra = " + aluno.getRa(), null);
    }

    @Override
    public void delete(Aluno aluno) throws SQLException {
        db.delete("aluno", "ra = " + aluno.getRa(), null);
    }

    @SuppressLint("Range")
    @Override
    public Aluno findOne(Aluno aluno) throws SQLException {
        String sql = "SELECT ra, nome, email FROM aluno WHERE ra = " + aluno.getRa();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        if (!cursor.isAfterLast()) {
            aluno.setRa(cursor.getInt(cursor.getColumnIndex("ra")));
            aluno.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            aluno.setEmail(cursor.getString(cursor.getColumnIndex("email")));
        }
        cursor.close();
        return aluno;

    }

    @SuppressLint("Range")
    @Override
    public List<Aluno> findAll() throws SQLException {
        List<Aluno> alunos = new ArrayList<>();
        String sql = "SELECT ra, nome, email FROM aluno";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        while (!cursor.isAfterLast()) {
            Aluno aluno = new Aluno();
            aluno.setRa(cursor.getInt(cursor.getColumnIndex("ra")));
            aluno.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            aluno.setEmail(cursor.getString(cursor.getColumnIndex("email")));
            alunos.add(aluno);
            cursor.moveToNext();
        }
        cursor.close();
        return alunos;
    }

    private ContentValues getContentValues(Aluno aluno) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("ra", aluno.getRa());
        contentValues.put("nome", aluno.getNome());
        contentValues.put("email", aluno.getEmail());
        return contentValues;
    }
}
