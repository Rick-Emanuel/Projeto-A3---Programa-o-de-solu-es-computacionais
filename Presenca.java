package src;

public class Presenca {
    private int ra;
    private String nomeCompleto; // <- Adicionado
    private boolean presente;

    public Presenca(int ra, String nomeCompleto, boolean presente) {
        this.ra = ra;
        this.nomeCompleto = nomeCompleto;
        this.presente = presente;
    }

    public int getRa() {
        return ra;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public boolean isPresente() {
        return presente;
    }

    public void setPresente(boolean presente) {
        this.presente = presente;
    }
}
