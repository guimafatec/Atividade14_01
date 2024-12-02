package br.edu.fateczl.atividade14_01.model;

import androidx.annotation.NonNull;

public abstract class Exemplar {
    /*
     * @author: Gustavo Guimarães de Oliveira
     */
    private int codigo;
    private String nome;
    private int qtdPaginas;
    public Exemplar() {
        super();
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getQtdPaginas() {
        return qtdPaginas;
    }

    public void setQtdPaginas(int qtdPaginas) {
        this.qtdPaginas = qtdPaginas;
    }

    @Override
    public String toString() {
        return String.format("%s | %s | %s ", codigo, nome, qtdPaginas);
    }
}