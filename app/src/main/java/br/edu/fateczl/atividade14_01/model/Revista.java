package br.edu.fateczl.atividade14_01.model;

import androidx.annotation.NonNull;

public class Revista extends Exemplar{
    private String issn;
    public Revista() {
        super();
    }

    public String getIssn() {
        return issn;
    }
    public void setIssn(String issn) {
        this.issn = issn;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("%d | %s | %s | %d", getCodigo(), issn, getNome(), getQtdPaginas());
    }
}
