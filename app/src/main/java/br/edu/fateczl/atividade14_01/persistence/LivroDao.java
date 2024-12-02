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

public class LivroDao implements ICRUDDao<Livro>, ILivroDao {
    private final Context context;
    private GenericDao gDao;
    private SQLiteDatabase db;

    public LivroDao(Context context) {
        this.context = context;
    }

    @Override
    public LivroDao open() throws SQLException {
        gDao = new GenericDao(context);
        db = gDao.getWritableDatabase();
        return this;
    }

    @Override
    public void close() {
        gDao.close();
    }

    @Override
    public void insert(Livro livro) throws SQLException {
        ContentValues contentValues = getContentValuesExemplar(livro);
        db.insert("exemplar", null, contentValues);
        contentValues = getContentValuesLivro(livro);
        db.insert("livro", null, contentValues);
    }

    @Override
    public void update(Livro livro) throws SQLException {
        ContentValues contentValues = getContentValuesExemplar(livro);
        db.update("exemplar", contentValues, "codigo = " + livro.getCodigo(), null);
        contentValues = getContentValuesLivro(livro);
        db.update("livro", contentValues, "exemplar_codigo = " + livro.getCodigo(), null);
    }

    @Override
    public void delete(Livro livro) throws SQLException {
        db.delete("livro", "exemplar_codigo = " + livro.getCodigo(), null);
        db.delete("exemplar", "codigo = " + livro.getCodigo(), null);
    }

    @SuppressLint("Range")
    @Override
    public Livro findOne(Livro livro) throws SQLException {
        String sql = "SELECT b.codigo, b.nome, b.qtd_paginas, a.isbn, a.edicao FROM livro a " +
                "INNER JOIN exemplar b " +
                "ON a.exemplar_codigo = b.codigo " +
                "WHERE b.codigo = " + livro.getCodigo();

        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        if (!cursor.isAfterLast()) {
            livro.setCodigo(cursor.getInt(cursor.getColumnIndex("codigo")));
            livro.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            livro.setQtdPaginas(cursor.getInt(cursor.getColumnIndex("qtd_paginas")));
            livro.setIsbn(cursor.getString(cursor.getColumnIndex("isbn")));
            livro.setEdicao(cursor.getInt(cursor.getColumnIndex("edicao")));
        }
        cursor.close();
        return livro;
    }

    @SuppressLint("Range")
    @Override
    public List<Livro> findAll() throws SQLException {
        List<Livro> livros = new ArrayList<>();
        String sql = "SELECT b.codigo, b.nome, b.qtd_paginas, a.isbn, a.edicao FROM livro a " +
                "INNER JOIN exemplar b " +
                "ON a.exemplar_codigo = b.codigo ";

        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        while (!cursor.isAfterLast()) {
            Livro livro = new Livro();
            livro.setCodigo(cursor.getInt(cursor.getColumnIndex("codigo")));
            livro.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            livro.setQtdPaginas(cursor.getInt(cursor.getColumnIndex("qtd_paginas")));
            livro.setIsbn(cursor.getString(cursor.getColumnIndex("isbn")));
            livro.setEdicao(cursor.getInt(cursor.getColumnIndex("edicao")));
            livros.add(livro);
            cursor.moveToNext();
        }
        cursor.close();
        return livros;
    }

    private ContentValues getContentValuesExemplar(Livro livro) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("codigo", livro.getCodigo());
        contentValues.put("nome", livro.getNome());
        contentValues.put("qtd_paginas", livro.getQtdPaginas());
        return contentValues;
    }
    private ContentValues getContentValuesLivro(Livro livro) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("exemplar_codigo", livro.getCodigo());
        contentValues.put("isbn", livro.getIsbn());
        contentValues.put("edicao", livro.getEdicao());
        return contentValues;
    }
}
