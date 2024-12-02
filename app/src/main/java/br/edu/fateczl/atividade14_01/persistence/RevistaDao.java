package br.edu.fateczl.atividade14_01.persistence;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.edu.fateczl.atividade14_01.model.Livro;
import br.edu.fateczl.atividade14_01.model.Revista;

public class RevistaDao implements ICRUDDao<Revista>, IRevistaDao {
    private final Context context;
    private GenericDao gDao;
    private SQLiteDatabase db;

    public RevistaDao(Context context) {
        this.context = context;
    }

    @Override
    public RevistaDao open() throws SQLException {
        gDao = new GenericDao(context);
        db = gDao.getWritableDatabase();
        return this;
    }

    @Override
    public void close() {
        gDao.close();
    }

    @Override
    public void insert(Revista revista) throws SQLException {
        ContentValues contentValues = getContentValuesExemplar(revista);
        db.insert("exemplar", null, contentValues);
        contentValues = getContentValuesRevista(revista);
        db.insert("revista", null, contentValues);
    }

    @Override
    public void update(Revista revista) throws SQLException {
        ContentValues contentValues = getContentValuesExemplar(revista);
        db.update("exemplar", contentValues, "codigo = " + revista.getCodigo(), null);
        contentValues = getContentValuesRevista(revista);
        db.update("revista", contentValues, "exemplar_codigo = " + revista.getCodigo(), null);
    }

    @Override
    public void delete(Revista revista) throws SQLException {
        db.delete("revista", "exemplar_codigo = " + revista.getCodigo(), null);
        db.delete("exemplar", "codigo = " + revista.getCodigo(), null);
    }

    @SuppressLint("Range")
    @Override
    public Revista findOne(Revista revista) throws SQLException {
        String sql = "SELECT b.codigo, b.nome, b.qtd_paginas, a.issn FROM revista a " +
                "INNER JOIN exemplar b " +
                "ON a.exemplar_codigo = b.codigo " +
                "WHERE b.codigo = " + revista.getCodigo();

        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        if (!cursor.isAfterLast()) {
            revista.setCodigo(cursor.getInt(cursor.getColumnIndex("codigo")));
            revista.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            revista.setQtdPaginas(cursor.getInt(cursor.getColumnIndex("qtd_paginas")));
            revista.setIssn(cursor.getString(cursor.getColumnIndex("issn")));
        }
        System.out.println("REVISTA" + revista.toString());
        cursor.close();
        return revista;
    }

    @SuppressLint("Range")
    @Override
    public List<Revista> findAll() throws SQLException {
        List<Revista> revistas = new ArrayList<>();
        String sql = "SELECT b.codigo, b.nome, b.qtd_paginas, a.issn FROM revista a " +
                "INNER JOIN exemplar b " +
                "ON a.exemplar_codigo = b.codigo ";

        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        while (!cursor.isAfterLast()) {
            Revista revista = new Revista();
            revista.setCodigo(cursor.getInt(cursor.getColumnIndex("codigo")));
            revista.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            revista.setQtdPaginas(cursor.getInt(cursor.getColumnIndex("qtd_paginas")));
            revista.setIssn(cursor.getString(cursor.getColumnIndex("issn")));
            revistas.add(revista);
            cursor.moveToNext();
        }
        cursor.close();
        return revistas;
    }

    private ContentValues getContentValuesExemplar(Revista revista) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("codigo", revista.getCodigo());
        contentValues.put("nome", revista.getNome());
        contentValues.put("qtd_paginas", revista.getQtdPaginas());
        return contentValues;
    }
    private ContentValues getContentValuesRevista(Revista revista) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("exemplar_codigo", revista.getCodigo());
        contentValues.put("issn", revista.getIssn());
        return contentValues;
    }
}
