package DAO;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import pojo.GiaoVu;
import util.HibernateUtil;

import java.util.List;

import static util.HibernateUtil.getSession;

public class GiaoVuDAO {
    public static List<GiaoVu> layDanhSachGiaoVu() {
        List<GiaoVu> ds = null;
        Session session = HibernateUtil.getSession();
        try {
            String hql = "select gv from GiaoVu gv";
            Query query = session.createQuery(hql);
            ds = query.list();
        } catch (HibernateException ex) {
            System.err.println(ex);
        } finally {
            session.close();
        }
        return ds;
    }
    public static GiaoVu layThongTinGiaoVu(String taiKhoan) {
        List<GiaoVu> ds = null;
        Session session = HibernateUtil.getSession();
        try {
            String hql = "select gv from GiaoVu gv where gv.taiKhoan = :taiKhoan";
            Query query = session.createQuery(hql);
            query.setParameter("taiKhoan", taiKhoan);
            ds = query.list();
        } catch (HibernateException ex) {
            System.err.println(ex);
        } finally {
            session.close();
        }
        return ds.get(0);
    }

    public static boolean ThemHoacCapNhatGiaoVu(GiaoVu gv) {
        boolean r = true;

        final Session session = getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.saveOrUpdate(gv);
            transaction.commit();
        } catch (HibernateException ex) {
            transaction.rollback();
            r = false;
            System.err.println(ex);
        } finally {
            session.close();
        }
        return r;
    }
}
