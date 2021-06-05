package com.selatan.tanikopi;

public class IsiStok {
    String kodeProses,jumlahGreen,gabah,jenis;

    public IsiStok(String kodeProses, String jumlahGreen,String gabah,String jenis) {
        this.kodeProses = kodeProses;
        this.jumlahGreen = jumlahGreen;
        this.gabah = gabah;
        this.jenis = jenis;
    }

    public void setGabah(String gabah) {
        this.gabah = gabah;
    }
    public String getJenis() {
        return jenis;
    }
    public void setJenis(String jenis) {
        this.jenis = jenis;
    }
    public String getKodeProses() {
        return kodeProses;
    }
    public void setKodeProses(String kodeProses) {
        this.kodeProses = kodeProses;
    }
    public String getJumlahGreen() {
        return jumlahGreen;
    }
    public String getGabah() {
        return gabah;
    }
    public void setJumlahGreen(String jumlahGreen) {
        this.jumlahGreen = jumlahGreen;
    }

    @Override
    public String toString() {
        return "IsiStok{" +
                "kodeProses='" + kodeProses + '\'' +
                ", jumlahGreen='" + jumlahGreen + '\'' +
                ", gabah='" + gabah + '\'' +
                ", jenis='" + jenis + '\'' +
                '}';
    }
}
