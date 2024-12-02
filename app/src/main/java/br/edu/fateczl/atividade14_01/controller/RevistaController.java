package br.edu.fateczl.atividade14_01.controller;

import java.sql.SQLException;
import java.util.List;

import br.edu.fateczl.atividade14_01.model.Revista;
import br.edu.fateczl.atividade14_01.persistence.RevistaDao;

public class RevistaController implements IController<Revista>{
    private final RevistaDao revistaDao;

    public RevistaController(RevistaDao revistaDao) {
        this.revistaDao = revistaDao;
    }

    @Override
    public void inserir(Revista revista) throws SQLException {
        if (revistaDao.open() == null) {
            revistaDao.open();
        }
        revistaDao.insert(revista);
        revistaDao.close();
    }

    @Override
    public void modificar(Revista revista) throws SQLException {
        if (revistaDao.open() == null) {
            revistaDao.open();
        }
        revistaDao.update(revista);
        revistaDao.close();
    }

    @Override
    public void deletar(Revista revista) throws SQLException {
        if (revistaDao.open() == null) {
            revistaDao.open();
        }
        revistaDao.delete(revista);
        revistaDao.close();
    }

    @Override
    public Revista buscar(Revista revista) throws SQLException {
        if (revistaDao.open() == null) {
            revistaDao.open();
        }
        return revistaDao.findOne(revista);
    }

    @Override
    public List<Revista> listar() throws SQLException {
        if (revistaDao.open() == null) {
            revistaDao.open();
        }
        return revistaDao.findAll();
    }
}
