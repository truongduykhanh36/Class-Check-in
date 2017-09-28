package DAO;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import pojo.DiemDanh;
import util.HibernateUtil;

import java.util.List;

import static util.HibernateUtil.getSession;

public class DiemDanhDAO {
    public static List<DiemDanh> layDSDiemDanhTheoSinhVien(String mssv) {
        List<DiemDanh> ds = null;
        Session session = HibernateUtil.getSession();
        try {
            String hql = "select dd from DiemDanh dd where dd.mssv = :mssv";
            Query query = session.createQuery(hql);
            query.setParameter("mssv", mssv);
            ds = query.list();
        } catch (Exception ex) {
            System.err.println(ex);
        } finally {
            session.close();
        }
        return ds;
    }
    public static List<DiemDanh> layDSDiemDanhTheoMonHoc(String maMon) {
        List<DiemDanh> ds = null;
        Session session = HibernateUtil.getSession();
        try {
            String hql = "select dd from DiemDanh dd where dd.maMon = :maMon";
            Query query = session.createQuery(hql);
            query.setParameter("maMon", maMon);
            ds = query.list();
        } catch (Exception ex) {
            System.err.println(ex);
        } finally {
            session.close();
        }
        return ds;
    }
    public static DiemDanh layDSDiemDanhTheoSinhVienVaMonHoc(String maMon, String mssv) {
        List<DiemDanh> ds = null;
        Session session = HibernateUtil.getSession();
        try {
            String hql = "select dd from DiemDanh dd where dd.maMon = :maMon and dd.mssv = :mssv";
            Query query = session.createQuery(hql);
            query.setParameter("mssv", mssv);
            query.setParameter("maMon", maMon);
            ds = query.list();
        } catch (Exception ex) {
            System.err.println(ex);
        } finally {
            session.close();
        }
        return ds.get(0);
    }

    public static boolean themHoacCapNhatThongTinDiemDanh(DiemDanh dd) {
        boolean r = true;
        final Session session = getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.saveOrUpdate(dd);
            transaction.commit();
        } catch (Exception ex) {
            transaction.rollback();
            System.err.println(ex);
            r = false;
        } finally {
            session.close();
        }
        return r;
    }

    public static boolean xoaDuLieuDiemDanhCuaSinhVienTrongMonHoc(String mssv, String maMon) {
        boolean r = true;

        final Session session = getSession();
        try {
            String hql = "delete from DiemDanh dd where dd.mssv = :mssv and dd.maMon = :maMon";
            Query query = session.createQuery(hql);
            query.setParameter("mssv", mssv);
            query.setParameter("maMon", maMon);
            System.out.println(query.executeUpdate());
        } catch (Exception ex) {
            r = false;
            System.err.println(ex);
        } finally {
            session.close();
        }
        return r;
    }
}
