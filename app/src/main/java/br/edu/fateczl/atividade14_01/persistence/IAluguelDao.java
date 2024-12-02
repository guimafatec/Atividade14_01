package br.edu.fateczl.atividade14_01.persistence;

import java.sql.SQLException;

public interface IAluguelDao {
    public AluguelDao open() throws SQLException;
    public void close();
}
