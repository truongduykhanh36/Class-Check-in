package pojo;

import javax.persistence.*;
import java.util.Arrays;

@Entity
@Table(name = "diem_danh", schema = "quanlymonhoc")
@IdClass(DiemDanhPK.class)
public class DiemDanh {
    private String mssv;
    private String maMon;
    private byte[] diemDanh;

    public DiemDanh() {

    }
    public DiemDanh(String mssv, String maMon) {
        this.mssv = mssv;
        this.maMon = maMon;
        diemDanh = new byte[15];
        for(int i = 0; i < 15; i++) {
            diemDanh[i] = '0';
        }
    }

    @Id
    @Column(name = "mssv")
    public String getMssv() {
        return mssv;
    }

    public void setMssv(String mssv) {
        this.mssv = mssv;
    }

    @Id
    @Column(name = "ma_mon")
    public String getMaMon() {
        return maMon;
    }

    public void setMaMon(String maMon) {
        this.maMon = maMon;
    }

    @Basic
    @Column(name = "diem_danh")
    public byte[] getDiemDanh() {
        return diemDanh;
    }

    public void setDiemDanh(byte[] diemDanh) {
        this.diemDanh = diemDanh;
    }

    public void setDiemDanh(int index, byte value) {
        if(index >= 0 && index < diemDanh.length) {
            diemDanh[index] = value;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DiemDanh that = (DiemDanh) o;

        if (mssv != null ? !mssv.equals(that.mssv) : that.mssv != null) return false;
        if (maMon != null ? !maMon.equals(that.maMon) : that.maMon != null) return false;
        if (!Arrays.equals(diemDanh, that.diemDanh)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = mssv != null ? mssv.hashCode() : 0;
        result = 31 * result + (maMon != null ? maMon.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(diemDanh);
        return result;
    }

    public String layThongTinTomTat(int tuanHienTai) {
        if(tuanHienTai < 0) {
            return "Chưa Bắt đầu";
        }
        else if (tuanHienTai >= 15) {
            return "Đã Kết thúc";
        }
        else {
            int soBuoiVang = 0;
            for(int i = 0; i <= tuanHienTai; i++) {
                if(diemDanh[i] == '0') {
                    soBuoiVang++;
                }
            }

            if(soBuoiVang == 0) {
                return "Điểm danh Đầy đủ";
            }
            else
                return "Vắng " + soBuoiVang + " buổi";
        }
    }
    public String layThongTinChiTiet(int tuanHienTai) {
        if(tuanHienTai < 0) {
            return "Chưa Bắt đầu";
        }
        else {
            if(tuanHienTai == 15) tuanHienTai = 14;
            boolean coVang = false;
            String cacTuanVang = "";
            for(int i = 0; i <= tuanHienTai; i++) {
                if(diemDanh[i] == '0') {
                    coVang = true;
                    cacTuanVang += (" " + (i + 1));
                }
            }

            if(!coVang) {
                return "Điểm danh Đầy đủ";
            }
            else
                return "Vắng các Tuần:" + cacTuanVang;
        }
    }
}
