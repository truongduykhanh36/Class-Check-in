package pojo;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "mon_hoc", schema = "quanlymonhoc")
public class MonHoc {
    private String maMon;
    private String tenMon;
    private Date ngayBatDau;
    private Time gioBatDau;
    private Time gioKetThuc;
    private String phongHoc;

    private List<Calendar> dsNgayGioBatDauHoc, dsNgayGioKetThucHoc;

    public MonHoc(){
        maMon = "UNKNOWN"; tenMon = "UNKNOWN";
        phongHoc = "UNKNOWN";
        ngayBatDau = new Date(0);
        gioBatDau = new Time(0);
        gioKetThuc = new Time(0);
    }
    public MonHoc(String maMon, String tenMon, String phongHoc) {
        //CAN CODE O DAY
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
    @Column(name = "ten_mon")
    public String getTenMon() {
        return tenMon;
    }

    public void setTenMon(String tenMon) {
        this.tenMon = tenMon;
    }

    @Basic
    @Column(name = "ngay_bat_dau")
    public Date getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(Date ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    @Basic
    @Column(name = "gio_bat_dau")
    public Time getGioBatDau() {
        return gioBatDau;
    }

    public void setGioBatDau(Time gioBatDau) {
        this.gioBatDau = gioBatDau;
    }

    @Basic
    @Column(name = "gio_ket_thuc")
    public Time getGioKetThuc() {
        return gioKetThuc;
    }

    public void setGioKetThuc(Time gioKetThuc) {
        this.gioKetThuc = gioKetThuc;
    }

    @Basic
    @Column(name = "phong_hoc")
    public String getPhongHoc() {
        return phongHoc;
    }

    public void setPhongHoc(String phongHoc) {
        this.phongHoc = phongHoc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MonHoc that = (MonHoc) o;

        if (maMon != null ? !maMon.equals(that.maMon) : that.maMon != null) return false;
        if (tenMon != null ? !tenMon.equals(that.tenMon) : that.tenMon != null) return false;
        if (ngayBatDau != null ? !ngayBatDau.equals(that.ngayBatDau) : that.ngayBatDau != null) return false;
        if (gioBatDau != null ? !gioBatDau.equals(that.gioBatDau) : that.gioBatDau != null) return false;
        if (gioKetThuc != null ? !gioKetThuc.equals(that.gioKetThuc) : that.gioKetThuc != null) return false;
        if (phongHoc != null ? !phongHoc.equals(that.phongHoc) : that.phongHoc != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = maMon != null ? maMon.hashCode() : 0;
        result = 31 * result + (tenMon != null ? tenMon.hashCode() : 0);
        result = 31 * result + (ngayBatDau != null ? ngayBatDau.hashCode() : 0);
        result = 31 * result + (gioBatDau != null ? gioBatDau.hashCode() : 0);
        result = 31 * result + (gioKetThuc != null ? gioKetThuc.hashCode() : 0);
        result = 31 * result + (phongHoc != null ? phongHoc.hashCode() : 0);
        return result;
    }

    private List<Calendar> layDSGioBatDauHoc() {
        List<Calendar> ds = new LinkedList<>();
        Calendar ngayGioBatDauDauTien = sqlDateTimeToCalendar(ngayBatDau, gioBatDau);
        ds.add(ngayGioBatDauDauTien);
        for(int i = 1; i < 15; i++) {
            Calendar ngayGioBatDauTiepTheo = Calendar.getInstance();
            ngayGioBatDauTiepTheo.setTime(ngayGioBatDauDauTien.getTime());
            ngayGioBatDauDauTien.add(Calendar.DATE, 7);
            ds.add(ngayGioBatDauTiepTheo);
        }

        ds.sort(Calendar::compareTo);
        return ds;
    }
    private List<Calendar> layDSGioKetThucHoc() {
        List<Calendar> ds = new LinkedList<>();
        Calendar ngayGioKetThucDauTien = sqlDateTimeToCalendar(ngayBatDau, gioKetThuc);
        ds.add(ngayGioKetThucDauTien);
        for(int i = 1; i < 15; i++) {
            Calendar ngayGioKetThucTiepTheo = Calendar.getInstance();
            ngayGioKetThucTiepTheo.setTime(ngayGioKetThucDauTien.getTime());
            ngayGioKetThucDauTien.add(Calendar.DATE, 7);
            ds.add(ngayGioKetThucTiepTheo);
        }

        ds.sort(Calendar::compareTo);
        return ds;
    }
    private Calendar sqlDateTimeToCalendar(Date ngay, Time gio) {
        Calendar dateCal = Calendar.getInstance(); dateCal.setTime(ngay);
        Calendar timeCal = Calendar.getInstance(); timeCal.setTime(gio);

        dateCal.set(Calendar.HOUR_OF_DAY, timeCal.get(Calendar.HOUR_OF_DAY));
        dateCal.set(Calendar.MINUTE, timeCal.get(Calendar.MINUTE));
        dateCal.set(Calendar.SECOND, 0);

        Calendar ngayGioBatDauDauTien = Calendar.getInstance();
        ngayGioBatDauDauTien.setTime(dateCal.getTime());

        return ngayGioBatDauDauTien;
    }
    private void tinhGioHoc() {
        dsNgayGioBatDauHoc = layDSGioBatDauHoc();
        dsNgayGioKetThucHoc = layDSGioKetThucHoc();
    }

    public boolean isCoTheDiemDanh(Calendar atThisTime) {
        if(dsNgayGioBatDauHoc == null) tinhGioHoc();
        boolean r = false;
        for(int i = 0; i < dsNgayGioBatDauHoc.size(); i++) {
            if(atThisTime.after(dsNgayGioBatDauHoc.get(i)) && atThisTime.before(dsNgayGioKetThucHoc.get(i))) {
                r = true;
                break;
            }
        }

        return r;
    }
    public int tuanHienTai(Calendar atThisTime) {
        if(dsNgayGioBatDauHoc == null) {
            tinhGioHoc();
        }
        for (int i = 0; i < 15 ; i++) {
            if(atThisTime.before(dsNgayGioBatDauHoc.get(i))) {
                return i - 1;
            }
        }
        return 15;
    }
    public String layGioHocTrongTuan() {
        String[] thuTrongTuan = {"CN", "T2", "T3", "T4", "T5", "T6", "T7"};
        String r = "";
        Calendar calBatDau = sqlDateTimeToCalendar(ngayBatDau, gioBatDau);
        Calendar calKetThuc = sqlDateTimeToCalendar(ngayBatDau, gioKetThuc);

        r += (thuTrongTuan[calBatDau.get(Calendar.DAY_OF_WEEK) - 1]) + ", ";
        r += calBatDau.get(Calendar.HOUR_OF_DAY) + ":" + calBatDau.get(Calendar.MINUTE);
        r += " - " + calKetThuc.get(Calendar.HOUR_OF_DAY) + ":" + calKetThuc.get(Calendar.MINUTE);

        return r;
    }
    public int tieuChiSapXep(Calendar atThisTime) {
        int tuanHienTai = tuanHienTai(atThisTime);
        if(tuanHienTai >= 0 && tuanHienTai < 15) return 0;
        if(tuanHienTai < 0) return 1;
        return 2;
    }

    public Calendar layNgayGioBatDauChuan() {
        return sqlDateTimeToCalendar(ngayBatDau, gioBatDau);
    }
    public Calendar layGioKetThucChuan() { return sqlDateTimeToCalendar(ngayBatDau, gioKetThuc);}
}
