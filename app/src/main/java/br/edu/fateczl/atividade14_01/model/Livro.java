package br.edu.fateczl.atividade14_01.model;

import androidx.annotation.NonNull;

public class Livro extends Exemplar {
    /*
     * @author: Gustavo Guimarães de Oliveira
     */
    private String isbn;
    private int edicao;

    public Livro() {
        super();
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getEdicao() {
        return edicao;
    }

    public void setEdicao(int edicao) {
        this.edicao = edicao;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("%d | %s | %s | %d | %d", getCodigo(), isbn, getNome(), getQtdPaginas(), edicao);
    }
}

