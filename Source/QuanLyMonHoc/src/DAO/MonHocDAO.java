package DAO;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import pojo.MonHoc;
import util.HibernateUtil;

import java.util.List;

import static util.HibernateUtil.getSession;

public class MonHocDAO {
    public static List<MonHoc> layDanhSachMonHoc() {
        List<MonHoc> ds = null;
        Session session = HibernateUtil.getSession();
        try {
            String hql = "select mh from MonHoc mh";
            Query query = session.createQuery(hql);
            ds = query.list();
        } catch (Exception ex) {
            System.err.println(ex);
        } finally {
            session.close();
        }
        return ds;
    }
    public static MonHoc layThongTinMonHoc(String maMon) {
        MonHoc mh = null;
        Session session = HibernateUtil.getSession();
        try {
            mh = session.get(MonHoc.class, maMon);
        } catch (Exception ex) {
            System.err.println(ex);
        } finally {
            session.close();
        }
        return mh;
    }
    public static List<MonHoc> layDanhSachMonHocTheoSinhVien(List<String> dsMaMon) {
        List<MonHoc> ds = null;
        Session session = HibernateUtil.getSession();
        try {
            String hql = "select mh from MonHoc mh where mh.maMon in (:dsMaMon)";
            Query query = session.createQuery(hql);
            query.setParameter("dsMaMon", dsMaMon);
            ds = query.list();
        } catch (Exception ex) {
            System.err.println(ex);
        } finally {
            session.close();
        }
        return ds;
    }

    public static boolean themHoacCapNhatMonHoc(MonHoc mh) {
        boolean r = true;

        final Session session = getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.saveOrUpdate(mh);
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
}
