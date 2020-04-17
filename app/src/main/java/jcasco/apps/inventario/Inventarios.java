package jcasco.apps.inventario;

public class Inventarios {
    public String userId;
    public String displayNameInventarios;
    public String date;
    public String descripcionInventarios;
    public String photoInventariosUrl;


    public Inventarios(String userId, String displayNameInventarios, String date, String descripcionInventarios, String photoInventariosUrl) {
        this.userId = userId;
        this.displayNameInventarios = displayNameInventarios;
        this.date = date;
        this.descripcionInventarios = descripcionInventarios;
        this.photoInventariosUrl = photoInventariosUrl;
    }

    public Inventarios(){}
}