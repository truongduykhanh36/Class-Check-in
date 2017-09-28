package DAO;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import pojo.SinhVien;

import java.util.List;

import static util.HibernateUtil.getSession;

public class SinhVienDAO {
    public static List<SinhVien> layDanhSachSinhVien() {
        List<SinhVien> ds = null;
        Session session = getSession();
        try {
            String hql = "select sv from SinhVien sv";
            Query query = session.createQuery(hql);
            ds = query.list();
        } catch (Exception ex) {
            System.err.println(ex);
        } finally {
            session.close();
        }
        return ds;
    }
    public static SinhVien layThongTinSinhVien(String maSinhVien) {
        SinhVien sv = null;
        Session session = getSession();
        try {
            sv = session.get(SinhVien.class, maSinhVien);
        } catch (Exception ex) {
            System.err.println(ex);
        } finally {
            session.close();
        }
        return sv;
    }

    public static boolean ThemSinhVienNeuChuaTonTai(SinhVien sv) {
        boolean r = true;

        final Session session = getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(sv);
            transaction.commit();
        } catch (Exception ex) {
            r = false;
            transaction.rollback();
            System.err.println(ex);
        } finally {
            session.close();
        }
        return r;
    }
    public static boolean ThemHoacCapNhatSinhVien(SinhVien sv) {
        boolean r = true;

        final Session session = getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.saveOrUpdate(sv);
            transaction.commit();
        } catch (Exception ex) {
            r = false;
            transaction.rollback();
        } finally {
            session.close();
        }
        return r;
    }
}
