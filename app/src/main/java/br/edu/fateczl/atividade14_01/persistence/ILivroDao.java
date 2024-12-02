package br.edu.fateczl.atividade14_01.persistence;

import java.sql.SQLException;

public interface ILivroDao {
    public LivroDao open() throws SQLException;
    public void close();
}
