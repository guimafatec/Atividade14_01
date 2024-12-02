package br.edu.fateczl.atividade14_01.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class GenericDao extends SQLiteOpenHelper {
    /*
     * @author: Gustavo Guimarães de Oliveira
     */
    private static final String DATABASE = "BIBLIOTECA";
    private static final int DATABASE_VER = 2;
    private final String CREATE_TABLE_ALUNO =
            "CREATE TABLE aluno ( " +
                    "ra INTEGER PRIMARY KEY, " +
                    "nome VARCHAR(100), " +
                    "email VARCHAR(50) " +
                    ")";
    private final String CREATE_TABLE_EXEMPLAR =
            "CREATE TABLE exemplar ( " +
                    "codigo INTEGER PRIMARY KEY, " +
                    "nome VARCHAR(50), " +
                    "qtd_paginas INTEGER " +
                    ")";
    private final String CREATE_TABLE_LIVRO =
            "CREATE TABLE livro ( " +
                    "exemplar_codigo INTEGER, " +
                    "isbn CHAR(13), " +
                    "edicao INTEGER, " +
                    "FOREIGN KEY (exemplar_codigo) REFERENCES exemplar(codigo) " +
                    ")";
    private final String CREATE_TABLE_REVISTA =
            "CREATE TABLE revista ( " +
                    "exemplar_codigo INTEGER, " +
                    "issn CHAR(8), " +
                    "FOREIGN KEY (exemplar_codigo) REFERENCES exemplar(codigo) " +
                    ")";
    private final String CREATE_TABLE_ALUGUEL =
            "CREATE TABLE aluguel ( " +
                    "exemplar_codigo INTEGER, " +
                    "aluno_ra INTEGER, " +
                    "data_retirada VARCHAR(10), " +
                    "data_devolucao VARCHAR(10), " +
                    "FOREIGN KEY (exemplar_codigo) REFERENCES exemplar (codigo), " +
                    "FOREIGN KEY (aluno_ra) REFERENCES aluno (ra), " +
                    "PRIMARY KEY (exemplar_codigo, aluno_ra, data_retirada) " +
                    ")";

    public GenericDao(Context context) {
        super(context, DATABASE, null, DATABASE_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ALUNO);
        db.execSQL(CREATE_TABLE_EXEMPLAR);
        db.execSQL(CREATE_TABLE_LIVRO);
        db.execSQL(CREATE_TABLE_REVISTA);
        db.execSQL(CREATE_TABLE_ALUGUEL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            db.execSQL("DROP TABLE IF EXISTS aluguel");
            db.execSQL("DROP TABLE IF EXISTS aluno");
            db.execSQL("DROP TABLE IF EXISTS livro");
            db.execSQL("DROP TABLE IF EXISTS revista");
            db.execSQL("DROP TABLE IF EXISTS exemplar");
            onCreate(db);
        }
    }
}
