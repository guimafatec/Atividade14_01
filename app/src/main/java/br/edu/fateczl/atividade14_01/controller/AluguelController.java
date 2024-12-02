package br.edu.fateczl.atividade14_01.controller;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import br.edu.fateczl.atividade14_01.model.Aluguel;
import br.edu.fateczl.atividade14_01.persistence.AluguelDao;
import br.edu.fateczl.atividade14_01.persistence.AlunoDao;

public class AluguelController implements IController<Aluguel> {
    private final AluguelDao aluguelDao;

    public AluguelController(AluguelDao aluguelDao) {
        this.aluguelDao = aluguelDao;
    }

    @Override
    public void inserir(Aluguel aluguel) throws SQLException {
        if (aluguelDao.open() == null) {
            aluguelDao.open();
        }
        aluguelDao.insert(aluguel);
        aluguelDao.close();
    }

    @Override
    public void modificar(Aluguel aluguel) throws SQLException {
        if (aluguelDao.open() == null) {
            aluguelDao.open();
        }
        aluguelDao.update(aluguel);
        aluguelDao.close();
    }

    @Override
    public void deletar(Aluguel aluguel) throws SQLException {
        if (aluguelDao.open() == null) {
            aluguelDao.open();
        }
        aluguelDao.delete(aluguel);
        aluguelDao.close();
    }

    @Override
    public Aluguel buscar(Aluguel aluguel) throws SQLException {
        if (aluguelDao.open() == null) {
            aluguelDao.open();
        }
        return aluguelDao.findOne(aluguel);
    }

    @Override
    public List<Aluguel> listar() throws SQLException {
        if (aluguelDao.open() == null) {
            aluguelDao.open();
        }
        return aluguelDao.findAll();
    }
}
