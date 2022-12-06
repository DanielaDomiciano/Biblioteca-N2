package br.org.femass.model;

public class Professor extends Leitor {
    private String disciplina;

    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }

    public String getDisciplina() {
        return disciplina;
    }
}
