package com.manpro.vancare;

import com.google.android.material.textfield.TextInputLayout;

public class UserHelperClass {
    private String name,email,nik,kabupaten,kecamatan,faskes, jenis, umur, birth, phoneNumber, password;

    public UserHelperClass(){}

    public UserHelperClass(String name, String email, String nik, String kabupaten, String kecamatan, String faskes, String jenis, String umur, String birth, String phoneNumber, String password) {
        this.name = name;
        this.email = email;
        this.nik = nik;
        this.kabupaten = kabupaten;
        this.kecamatan = kecamatan;
        this.faskes = faskes;
        this.jenis = jenis;
        this.umur = umur;
        this.birth = birth;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getKabupaten() {
        return kabupaten;
    }

    public void setKabupaten(String kabupaten) {
        this.kabupaten = kabupaten;
    }

    public String getKecamatan() {
        return kecamatan;
    }

    public void setKecamatan(String kecamatan) {
        this.kecamatan = kecamatan;
    }

    public String getFaskes() {
        return faskes;
    }

    public void setFaskes(String faskes) {
        this.faskes = faskes;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public String getUmur() {
        return umur;
    }

    public void setUmur(String umur) {
        this.umur = umur;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
