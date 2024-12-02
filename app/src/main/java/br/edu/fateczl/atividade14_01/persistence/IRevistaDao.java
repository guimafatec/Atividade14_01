package br.edu.fateczl.atividade14_01.persistence;

import java.sql.SQLException;

public interface IRevistaDao {
    /*
     * @author: Gustavo Guimarães de Oliveira
     */
    public RevistaDao open() throws SQLException;
    public void close();
}
