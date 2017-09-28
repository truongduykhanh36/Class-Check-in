package pojo;

import javax.persistence.*;

/**
 * Created by Admin on 8/4/2017.
 */
@Entity
@Table(name = "giao_vu", schema = "quanlymonhoc")
public class GiaoVu {
    private int id;
    private String taiKhoan;
    private String matKhau;
    private String ten;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

        GiaoVu that = (GiaoVu) o;

        if (id != that.id) return false;
        if (taiKhoan != null ? !taiKhoan.equals(that.taiKhoan) : that.taiKhoan != null) return false;
        if (matKhau != null ? !matKhau.equals(that.matKhau) : that.matKhau != null) return false;
        if (ten != null ? !ten.equals(that.ten) : that.ten != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (taiKhoan != null ? taiKhoan.hashCode() : 0);
        result = 31 * result + (matKhau != null ? matKhau.hashCode() : 0);
        result = 31 * result + (ten != null ? ten.hashCode() : 0);
        return result;
    }
}
