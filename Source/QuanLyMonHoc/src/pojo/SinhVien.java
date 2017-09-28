package pojo;

import util.BCrypt;

import javax.persistence.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "sinh_vien", schema = "quanlymonhoc")
public class SinhVien {
    private String mssv;
    private String ten;
    private String taiKhoan;
    private String matKhau;

    public SinhVien() {}
    public SinhVien(String mssv, String ten) {
        this.mssv = mssv;
        this.taiKhoan = mssv;
        this.ten = ten;
        this.matKhau = BCrypt.hashpw(mssv, BCrypt.gensalt());
    }
    public SinhVien(String mssv, String ten, String taiKhoan, String matKhau) {
        this.mssv = mssv;
        this.taiKhoan = taiKhoan;
        this.matKhau = matKhau;
        this.ten = ten;
    }

    @Id
    @Column(name = "mssv")
    public String getMssv() {
        return mssv;
    }

    public void setMssv(String mssv) {
        this.mssv = mssv;
    }

    @Basic
    @Column(name = "tai_khoan")
    public String getTaiKhoan() {
        return taiKhoan;
    }

    public void setTaiKhoan(String taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    @Basic
    @Column(name = "mat_khau")
    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    @Basic
    @Column(name = "ten")
    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SinhVien that = (SinhVien) o;

        if (mssv != null ? !mssv.equals(that.mssv) : that.mssv != null) return false;
        if (taiKhoan != null ? !taiKhoan.equals(that.taiKhoan) : that.taiKhoan != null) return false;
        if (matKhau != null ? !matKhau.equals(that.matKhau) : that.matKhau != null) return false;
        if (ten != null ? !ten.equals(that.ten) : that.ten != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = mssv != null ? mssv.hashCode() : 0;
        result = 31 * result + (taiKhoan != null ? taiKhoan.hashCode() : 0);
        result = 31 * result + (matKhau != null ? matKhau.hashCode() : 0);
        result = 31 * result + (ten != null ? ten.hashCode() : 0);
        return result;
    }

    public static List<SinhVien> parseFromCSV(String fileDir) {
        LinkedList<SinhVien> DSSV = new LinkedList<>();
        String dong;
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileDir), "UTF8"));
            while ((dong = br.readLine()) != null) {
                String token[] = dong.split("\t");
                if(token.length >= 2) {
                    DSSV.add(new SinhVien(token[0].trim(), token[1].trim()));
                }
            }
        } catch (Exception ex) {
            System.err.println(ex);
        }
        return DSSV;
    }
}
