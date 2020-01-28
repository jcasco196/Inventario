package com.example.inventario;

public class ImagenPerfil {
    public String userId;
    public String imagenP;
    public String imagenP2;
    public String mediaUrl;
    public String mediaTYPE;

    public ImagenPerfil(String userId, String imagenP, String imagenP2, String mediaUrl, String mediaTYPE ) {
        this.userId = userId;
        this.imagenP = imagenP;
        this.imagenP2 = imagenP2;
        this.mediaUrl = mediaUrl;
        this.mediaTYPE = mediaTYPE;
    }

    public ImagenPerfil(){}
}