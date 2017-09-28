package UI;

import DAO.GiaoVuDAO;
import DAO.SinhVienDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pojo.GiaoVu;
import pojo.SinhVien;
import util.BCrypt;
import util.HibernateUtil;

import java.util.List;

public class DangNhapController extends StageSceneController {
    @FXML
    Label lblStatus;

    @FXML
    TextField txtTaiKhoan, txtMatKhau;

    private List<GiaoVu> dsGiaoVu = GiaoVuDAO.layDanhSachGiaoVu();
    private List<SinhVien> dsSinhVien = SinhVienDAO.layDanhSachSinhVien();
    String taiKhoan, matKhau;

    public void DangNhap() {
        taiKhoan = txtTaiKhoan.getText();
        matKhau = txtMatKhau.getText();

        if(taiKhoan.length() == 0 || matKhau.length() == 0) {
            lblStatus.setText("Điền Tài khoản và Mật khẩu");
            return;
        }

        //Giao Vu
        for (GiaoVu gv: dsGiaoVu) {
            if(gv.getTaiKhoan().equals(taiKhoan)) {
                if(BCrypt.checkpw(matKhau, gv.getMatKhau()) == true) {
                    //Đăng nhập thành công vào giáo vụ
                    DangNhapVaoGiaoVu(gv);
                    return;
                }
                else {
                    lblStatus.setText("Sai Tài khoản hoặc Mật khẩu");
                    return;
                }
            }
        }

        //Sinh Vien
        for (SinhVien sv: dsSinhVien) {
            if(sv.getTaiKhoan().equals(taiKhoan)) {
                if(BCrypt.checkpw(matKhau, sv.getMatKhau()) == true) {
                    //Đăng nhập thành công vào Sinh viên
                    DangNhapVaoSinhVien(sv);
                    return;
                }
                else {
                    lblStatus.setText("Sai Tài khoản hoặc Mật khẩu");
                    return;
                }
            }
        }

        lblStatus.setText("Sai Tài khoản hoặc Mật khẩu");
    }

    private void DangNhapVaoGiaoVu(GiaoVu gv) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("GiaoVu_ManHinhChinh.fxml"));
            Parent root = loader.load();
            GiaoVu_ManHinhChinh_Controller controller = loader.getController();
            Stage stageGiaoVu = new Stage();
            controller.setStage(stageGiaoVu);
            controller.setGv(gv);

            stageGiaoVu.setTitle("Giáo vụ");
            stageGiaoVu.setScene(new Scene(root));
            stageGiaoVu.setResizable(false);
            stageGiaoVu.centerOnScreen();
            stageGiaoVu.setOnCloseRequest(e -> HibernateUtil.stop());

            this.stage.close();
            stageGiaoVu.show();
            controller.layDuLieu();
            controller.fillDuLieuKhoaHocLenView();
        }
        catch (Exception e) {
            lblStatus.setText("Không thể load Scene Giáo vụ");
        }
    }

    private void DangNhapVaoSinhVien(SinhVien sv) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SinhVien_ManHinhChinh.fxml"));
            Parent root = loader.load();
            SinhVien_ManHinhChinhController controller = loader.getController();
            Stage stageSinhVien = new Stage();
            controller.setStage(stageSinhVien);
            controller.setSv(sv);

            stageSinhVien.setTitle("Sinh viên");
            stageSinhVien.setScene(new Scene(root));
            stageSinhVien.centerOnScreen();
            stageSinhVien.setResizable(false);
            stageSinhVien.setOnCloseRequest(e -> HibernateUtil.stop());

            this.stage.close();
            stageSinhVien.show();
            controller.YeuCauDoiMatKhauOLanDangNhapDauTien();
            controller.layDuLieu();
            controller.fillDuLieuLenView();
        }
        catch (Exception e) {
            lblStatus.setText("Không thể load Scene Sinh viên");
        }
    }
}
