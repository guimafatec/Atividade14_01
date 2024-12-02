package br.edu.fateczl.atividade14_01.model;

import androidx.annotation.NonNull;

import java.time.LocalDate;

public class Aluguel {
    /*
     * @author: Gustavo Guimarães de Oliveira
     */
    private Aluno aluno;
    private Exemplar exemplar;
    private LocalDate dt_retirada;
    private LocalDate dt_devolucao;
    public Aluguel() {
        super();
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Exemplar getExemplar() {
        return exemplar;
    }

    public void setExemplar(Exemplar exemplar) {
        this.exemplar = exemplar;
    }

    public LocalDate getDt_retirada() {
        return dt_retirada;
    }

    public void setDt_retirada(LocalDate dt_retirada) {
        this.dt_retirada = dt_retirada;
    }

    public LocalDate getDt_devolucao() {
        return dt_devolucao;
    }

    public void setDt_devolucao(LocalDate dt_devolucao) {
        this.dt_devolucao = dt_devolucao;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("%s | %s | %s | %s", aluno, exemplar, dt_retirada, dt_devolucao);
    }
}
