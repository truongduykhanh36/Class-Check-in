package UI;

import pojo.DiemDanh;
import pojo.SinhVien;

public class SinhVienD4V {
    SinhVien sv;
    DiemDanh diemDanh;

    public SinhVienD4V(String MSSV, String hoTen, String maMon) {
        sv = new SinhVien(MSSV, hoTen);
        diemDanh = new DiemDanh(MSSV, maMon);
    }
    public SinhVienD4V (SinhVien sv, DiemDanh diemDanh) {
        this.sv = sv;
        this.diemDanh = diemDanh;
    }

    public String getMSSV() {
        return sv.getMssv();
    }
    public String getHoTen() {
        return sv.getTen();
    }
    public char getDiemDanh1() {
        return (char)diemDanh.getDiemDanh()[0];
    }
    public char getDiemDanh2() {
        return (char)diemDanh.getDiemDanh()[1];
    }
    public char getDiemDanh3() {
        return (char)diemDanh.getDiemDanh()[2];
    }
    public char getDiemDanh4() {
        return (char)diemDanh.getDiemDanh()[3];
    }
    public char getDiemDanh5() {
        return (char)diemDanh.getDiemDanh()[4];
    }
    public char getDiemDanh6() {
        return (char)diemDanh.getDiemDanh()[5];
    }
    public char getDiemDanh7() {
        return (char)diemDanh.getDiemDanh()[6];
    }
    public char getDiemDanh8() {
        return (char)diemDanh.getDiemDanh()[7];
    }
    public char getDiemDanh9() {
        return (char)diemDanh.getDiemDanh()[8];
    }
    public char getDiemDanh10() {
        return (char)diemDanh.getDiemDanh()[9];
    }
    public char getDiemDanh11() {
        return (char)diemDanh.getDiemDanh()[10];
    }
    public char getDiemDanh12() {
        return (char)diemDanh.getDiemDanh()[11];
    }
    public char getDiemDanh13() {
        return (char)diemDanh.getDiemDanh()[12];
    }
    public char getDiemDanh14() {
        return (char)diemDanh.getDiemDanh()[13];
    }
    public char getDiemDanh15() {
        return (char)diemDanh.getDiemDanh()[14];
    }

    public SinhVien getSv() {
        return sv;
    }
    public DiemDanh getDiemDanh() {
        return diemDanh;
    }
    public byte getDiemDanh(int index) {
        if(index >= 0 && index < 15) return diemDanh.getDiemDanh()[index];
        return '2';
    }
}
