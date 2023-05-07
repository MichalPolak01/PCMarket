package com.example.pcmarket;

public class Shopping_list {
    String id_produktu_w_koszyku;
    String id_osoby;
    String id_produktu;
    String ilosc;

    public Shopping_list(String id_produktu_w_koszyku, String id_osoby, String id_produktu, String ilosc) {
        this.id_produktu_w_koszyku = id_produktu_w_koszyku;
        this.id_osoby = id_osoby;
        this.id_produktu = id_produktu;
        this.ilosc = ilosc;
    }

    public String getId_produktu_w_koszyku() {
        return id_produktu_w_koszyku;
    }

    public void setId_produktu_w_koszyku(String id_produktu_w_koszyku) {
        this.id_produktu_w_koszyku = id_produktu_w_koszyku;
    }

    public String getId_osoby() {
        return id_osoby;
    }

    public void setId_osoby(String id_osoby) {
        this.id_osoby = id_osoby;
    }

    public String getId_produktu() {
        return id_produktu;
    }

    public void setId_produktu(String id_produktu) {
        this.id_produktu = id_produktu;
    }

    public String getIlosc() {
        return ilosc;
    }

    public void setIlosc(String ilosc) {
        this.ilosc = ilosc;
    }
}
