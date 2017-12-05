package musiclib.Model;

public class Music {

    private int id;
    private String nome;
    private String artista;
    private int ano;
    private String midia;
    private String genero;


    Music(int id, String nome, String artista, int ano, String midia, String genero) {
        this.setId(id);
        this.setNome(nome);
        this.setArtista(artista);
        this.setAno(ano);
        this.setMidia(midia);
        this.setGenero(genero);
    }

    public int getId() {
        return id;
    }

    private void setId(int id){
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    private void setNome(String nome){
        this.nome = nome;
    }

    public String getArtista() {
        return artista;
    }

    private void setArtista(String artista) {
        this.artista = artista;
    }

    public int getAno() {
        return ano;
    }

    private void setAno(int ano) {
        this.ano = ano;
    }

    public String getMidia() {
        return midia;
    }

    private void setMidia(String midia) {
        this.midia = midia;
    }

    public String getGenero() {
        return genero;
    }

    private void setGenero(String genero) {
        this.genero = genero;
    }
}
