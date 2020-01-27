package com.example.inventario;

public class ImagenPerfil {
    public String userId;
    public String imagenP;
    public String mediaUrl;
    public String mediaTYPE;

    public ImagenPerfil(String userId, String imagenP, String mediaUrl, String mediaTYPE ) {
        this.userId = userId;
        this.imagenP = imagenP;
        this.mediaUrl = mediaUrl;
        this.mediaTYPE = mediaTYPE;
    }

    public ImagenPerfil(){}
}