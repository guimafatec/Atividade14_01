package br.edu.fateczl.atividade14_01.persistence;

import java.sql.SQLException;

public interface IAlunoDao {
    public AlunoDao open() throws SQLException;
    public void close();
}
