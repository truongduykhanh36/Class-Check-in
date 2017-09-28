package pojo;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class DiemDanhPK implements Serializable {
    private String mssv;
    private String maMon;

    @Column(name = "mssv")
    @Id
    public String getMssv() {
        return mssv;
    }

    public void setMssv(String mssv) {
        this.mssv = mssv;
    }

    @Column(name = "ma_mon")
    @Id
    public String getMaMon() {
        return maMon;
    }

    public void setMaMon(String maMon) {
        this.maMon = maMon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DiemDanhPK that = (DiemDanhPK) o;

        if (mssv != null ? !mssv.equals(that.mssv) : that.mssv != null) return false;
        if (maMon != null ? !maMon.equals(that.maMon) : that.maMon != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = mssv != null ? mssv.hashCode() : 0;
        result = 31 * result + (maMon != null ? maMon.hashCode() : 0);
        return result;
    }
}
