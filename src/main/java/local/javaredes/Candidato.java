package local.javaredes;

import java.io.Serializable;

/**
 *
 * @author Renato Borges Viana
 */
public class Candidato implements Serializable {

    private String nome;
    private int numeroChapa;
    private int numeroVotos;

    public Candidato(String nome, int numeroChapa, int numeroVotos) {
        this.nome = nome;
        this.numeroChapa = numeroChapa;
        this.numeroVotos = numeroVotos;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getNumeroChapa() {
        return numeroChapa;
    }

    public void setNumeroChapa(int numeroChapa) {
        this.numeroChapa = numeroChapa;
    }

    public int getNumeroVotos() {
        return numeroVotos;
    }

    public void setNumeroVotos(int numeroVotos) {
        this.numeroVotos = numeroVotos;
    }
}
