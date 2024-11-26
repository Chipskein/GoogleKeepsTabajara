package negocio;

import java.time.LocalDateTime;

public class Anotacao {
    private int id;
    private String titulo;
    private LocalDateTime data_hora;
    private String descricao;
    private Cores cor;
    private byte[] foto;

    private String colorNameToANSI(String colorName) {
        switch (colorName.toUpperCase()) {
            case "BLACK":
                return "\u001B[30m";
            case "RED":
                return "\u001B[31m";
            case "GREEN":
                return "\u001B[32m";
            case "YELLOW":
                return "\u001B[33m";
            case "BLUE":
                return "\u001B[34m";
            case "PURPLE":
                return "\u001B[35m";
            case "CYAN":
                return "\u001B[36m";
            case "WHITE":
                return "\u001B[37m";
            default:
                return "\u001B[0m"; // Reset color (default to no color if unknown)
        }
    }
    

    @Override
    public String toString() {
        return colorNameToANSI(cor.name())+"Anotacao [id=" + id + ", titulo=" + titulo + ", data_hora=" + data_hora + ", descricao=" + descricao + " Foto size " + (foto!=null ? foto.length:0) + " bytes]"+ "\u001B[0m";
    }
    
    @Override
    public Anotacao clone(){
        return new Anotacao(id,titulo,descricao,data_hora,cor,foto);
    }

    public int getId() {
        return id;
    }
    
    public Anotacao(String titulo, String descricao) {
        this.titulo = titulo;
        this.descricao = descricao;
    }

    public Anotacao(int id, String titulo,  String descricao, LocalDateTime data_hora,Cores cor, byte[] foto) {
        this.id = id;
        this.titulo = titulo;
        this.data_hora = data_hora;
        this.descricao = descricao;
        this.cor = cor;
        this.foto = foto;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public LocalDateTime getData_hora() {
        return data_hora;
    }
    public void setData_hora(LocalDateTime data_hora) {
        this.data_hora = data_hora;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public Cores getCor() {
        return cor;
    }
    public void setCor(Cores cor) {
        this.cor = cor;
    }
    public byte[] getFoto() {
        return foto;
    }
    public void setFoto(byte[] foto) {
        this.foto = foto;
    }
}
