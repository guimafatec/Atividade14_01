package br.edu.fateczl.atividade14_01.controller;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import br.edu.fateczl.atividade14_01.model.Aluno;
import br.edu.fateczl.atividade14_01.persistence.AlunoDao;

public class AlunoController implements IController<Aluno> {
    private final AlunoDao alunoDao;

    public AlunoController(AlunoDao alunoDao) {
        this.alunoDao = alunoDao;
    }

    @Override
    public List<Aluno> listar() throws SQLException {
        if (alunoDao.open() == null) {
            alunoDao.open();
        }
        return alunoDao.findAll();
    }

    @Override
    public Aluno buscar(Aluno aluno) throws SQLException {
        if (alunoDao.open() == null) {
            alunoDao.open();
        }
        return alunoDao.findOne(aluno);
    }

    @Override
    public void deletar(Aluno aluno) throws SQLException {
        if (alunoDao.open() == null) {
            alunoDao.open();
        }
        alunoDao.delete(aluno);
        alunoDao.close();
    }

    @Override
    public void modificar(Aluno aluno) throws SQLException {
        if (alunoDao.open() == null) {
            alunoDao.open();
        }
        alunoDao.update(aluno);
        alunoDao.close();
    }

    @Override
    public void inserir(Aluno aluno) throws SQLException {
        if (alunoDao.open() == null) {
            alunoDao.open();
        }
        alunoDao.insert(aluno);
        alunoDao.close();
    }
}
