package br.edu.fateczl.atividade14_01.controller;

import java.sql.SQLException;
import java.util.List;

import br.edu.fateczl.atividade14_01.model.Livro;
import br.edu.fateczl.atividade14_01.persistence.LivroDao;

public class LivroController implements IController<Livro>{
    /*
     * @author: Gustavo Guimarães de Oliveira
     */
    private final LivroDao livroDao;

    public LivroController(LivroDao livroDao) {
        this.livroDao = livroDao;
    }

    @Override
    public void inserir(Livro livro) throws SQLException {
        if (livroDao.open() == null) {
            livroDao.open();
        }
        livroDao.insert(livro);
        livroDao.close();
    }

    @Override
    public void modificar(Livro livro) throws SQLException {
        if (livroDao.open() == null) {
            livroDao.open();
        }
        livroDao.update(livro);
        livroDao.close();
    }

    @Override
    public void deletar(Livro livro) throws SQLException {
        if (livroDao.open() == null) {
            livroDao.open();
        }
        livroDao.delete(livro);
        livroDao.close();
    }

    @Override
    public Livro buscar(Livro livro) throws SQLException {
        if (livroDao.open() == null) {
            livroDao.open();
        }
        return livroDao.findOne(livro);
    }

    @Override
    public List<Livro> listar() throws SQLException {
        if (livroDao.open() == null) {
            livroDao.open();
        }
        return livroDao.findAll();
    }
}
