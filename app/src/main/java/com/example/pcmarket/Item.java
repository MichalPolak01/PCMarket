package com.example.pcmarket;

public class Item {
    String id_produktu;
    String nazwa;
    String marka;
    String cena;
    String zdjecie;
    String ilosc;
    String opis;

    public Item(String id_produktu, String nazwa, String marka, String cena, String zdjecie, String ilosc, String opis) {
        this.id_produktu = id_produktu;
        this.nazwa = nazwa;
        this.marka = marka;
        this.cena = cena;
        this.zdjecie = zdjecie;
        this.ilosc = ilosc;
        this.opis = opis;
    }

    public String getId_produktu() {
        return id_produktu;
    }

    public void setId_produktu(String id_produktu) {
        this.id_produktu = id_produktu;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getMarka() {
        return marka;
    }

    public void setMarka(String marka) {
        this.marka = marka;
    }

    public String getCena() {
        return cena;
    }

    public void setCena(String cena) {
        this.cena = cena;
    }

    public String getZdjecie() {
        return zdjecie;
    }

    public void setZdjecie(String zdjecie) {
        this.zdjecie = zdjecie;
    }

    public String getIlosc() {
        return ilosc;
    }

    public void setIlosc(String ilosc) {
        this.ilosc = ilosc;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }
}
