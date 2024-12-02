package br.edu.fateczl.atividade14_01.persistence;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.edu.fateczl.atividade14_01.controller.AlunoController;
import br.edu.fateczl.atividade14_01.controller.LivroController;
import br.edu.fateczl.atividade14_01.controller.RevistaController;
import br.edu.fateczl.atividade14_01.model.Aluguel;
import br.edu.fateczl.atividade14_01.model.Aluno;
import br.edu.fateczl.atividade14_01.model.Exemplar;
import br.edu.fateczl.atividade14_01.model.Livro;
import br.edu.fateczl.atividade14_01.model.Revista;

public class AluguelDao implements ICRUDDao<Aluguel>, IAluguelDao {
    /*
     * @author: Gustavo Guimarães de Oliveira
     */
    private final Context context;
    private GenericDao gDao;
    private SQLiteDatabase db;
    private AlunoController alunoCtrl;
    private LivroController livroCtrl;
    private RevistaController revistaCTtrl;

    public AluguelDao(Context context) {
        this.context = context;
        alunoCtrl = new AlunoController(new AlunoDao(context));
        livroCtrl = new LivroController(new LivroDao(context));
        revistaCTtrl = new RevistaController(new RevistaDao(context));
    }

    @Override
    public AluguelDao open() throws SQLException {
        gDao = new GenericDao(context);
        db = gDao.getWritableDatabase();
        return this;
    }

    @Override
    public void close() {
        gDao.close();
    }

    @Override
    public void insert(Aluguel aluguel) throws SQLException {
        ContentValues contentValues = getContentValues(aluguel);
        db.insert("aluguel", null, contentValues);
    }

    @Override
    public void update(Aluguel aluguel) throws SQLException {
        ContentValues contentValues = getContentValues(aluguel);
        int codigo = aluguel.getExemplar().getCodigo();
        int ra = aluguel.getAluno().getRa();
        String dt_retirada = aluguel.getDt_retirada().toString();
        String where = String.format("exemplar_codigo = %d AND aluno_ra = %d AND data_retirada = '%s'", codigo, ra, dt_retirada);
        db.update("aluguel", contentValues, where, null);
    }

    @Override
    public void delete(Aluguel aluguel) throws SQLException {
        int codigo = aluguel.getExemplar().getCodigo();
        int ra = aluguel.getAluno().getRa();
        String dt_retirada = aluguel.getDt_retirada().toString();
        String where = String.format("exemplar_codigo = %d AND aluno_ra = %d AND data_retirada = '%s'", codigo, ra, dt_retirada);
        db.delete("aluguel", where, null);
    }

    @SuppressLint("Range")
    @Override
    public Aluguel findOne(Aluguel aluguel) throws SQLException {

        int codigo = aluguel.getExemplar().getCodigo();
        int ra = aluguel.getAluno().getRa();
        String dt_retirada = aluguel.getDt_retirada().toString();
        String where = String.format("a.exemplar_codigo = %d AND a.aluno_ra = %d AND a.data_retirada = '%s'", codigo, ra, dt_retirada);
        String sql = "SELECT " +
                "a.exemplar_codigo, a.aluno_ra, a.data_retirada, a.data_devolucao " +
                "FROM aluguel a WHERE " + where;
        System.out.println("AluguelDao: Query '" + sql + "'");
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null) {
            System.out.println("AluguelDao: Nenhum aluguel encontrado");
            cursor.moveToFirst();
        }
        if (!cursor.isAfterLast()) {
            Livro livro = new Livro();
            Revista revista = new Revista();
            livro.setCodigo(cursor.getInt(cursor.getColumnIndex("exemplar_codigo")));
            livro = livroCtrl.buscar(livro);
            revista.setCodigo(cursor.getInt(cursor.getColumnIndex("exemplar_codigo")));
            revista = revistaCTtrl.buscar(revista);
            Aluno aluno = new Aluno();
            aluno.setRa(cursor.getInt(cursor.getColumnIndex("aluno_ra")));
            aluno = alunoCtrl.buscar(aluno);

            aluguel.setAluno(aluno);
            if (revista != null) {
                aluguel.setExemplar(revista);
            }
            if (livro != null) {
                aluguel.setExemplar(livro);
            }
            aluguel.setDt_devolucao(LocalDate.parse(cursor.getString(cursor.getColumnIndex("data_devolucao"))));
            aluguel.setDt_retirada(LocalDate.parse(cursor.getString(cursor.getColumnIndex("data_retirada"))));
            System.out.println("AluguelDao: " + aluguel);
        }
        cursor.close();
        return aluguel;
    }

    @SuppressLint("Range")
    @Override
    public List<Aluguel> findAll() throws SQLException {
        List<Aluguel> alugueis = new ArrayList<>();

        String sql = "SELECT a.exemplar_codigo, a.aluno_ra, a.data_retirada, a.data_devolucao FROM aluguel a ";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        while (!cursor.isAfterLast()) {
            Livro livro = new Livro();
            Aluguel aluguel = new Aluguel();
            Revista revista = new Revista();
            Aluno aluno = new Aluno();
            livro.setCodigo(cursor.getInt(cursor.getColumnIndex("exemplar_codigo")));
            System.out.println("AluguelDao: Buscando Livro");
            livro = livroCtrl.buscar(livro);

            revista.setCodigo(cursor.getInt(cursor.getColumnIndex("exemplar_codigo")));
            System.out.println("AluguelDao: Buscando Revista");
            revista = revistaCTtrl.buscar(revista);


            aluno.setRa(cursor.getInt(cursor.getColumnIndex("aluno_ra")));
            aluno = alunoCtrl.buscar(aluno);

            aluguel.setAluno(aluno);
            if (revista != null) {
                aluguel.setExemplar(revista);
            } else if (livro != null) {
                aluguel.setExemplar(livro);
            }
            aluguel.setDt_devolucao(LocalDate.parse(cursor.getString(cursor.getColumnIndex("data_devolucao"))));
            aluguel.setDt_retirada(LocalDate.parse(cursor.getString(cursor.getColumnIndex("data_retirada"))));

            alugueis.add(aluguel);
            cursor.moveToNext();
        }
        cursor.close();
        return alugueis;
    }

    private ContentValues getContentValues(Aluguel aluguel) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("exemplar_codigo", aluguel.getExemplar().getCodigo());
        contentValues.put("aluno_ra", aluguel.getAluno().getRa());
        contentValues.put("data_retirada", aluguel.getDt_retirada().toString());
        contentValues.put("data_devolucao", aluguel.getDt_devolucao().toString());
        return contentValues;
    }
}
