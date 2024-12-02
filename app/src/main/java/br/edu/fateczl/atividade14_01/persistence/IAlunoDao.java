package br.edu.fateczl.atividade14_01.persistence;

import java.sql.SQLException;

public interface IAlunoDao {
    /*
     * @author: Gustavo Guimarães de Oliveira
     */
    public AlunoDao open() throws SQLException;
    public void close();
}
