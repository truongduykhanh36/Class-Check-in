package UI;

import DAO.GiaoVuDAO;
import DAO.SinhVienDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import pojo.GiaoVu;
import pojo.SinhVien;
import util.BCrypt;

public class DoiMatKhauController extends StageSceneController {
    @FXML
    PasswordField txtMatKhauCu, txtMatKhauMoi, txtNhapLaiMatKhauMoi;

    @FXML
    Label lblStatus;

    private Object taiKhoan;

    public void DoiMatKhau() {
        String matKhauCu = txtMatKhauCu.getText();
        String matKhauMoi = txtMatKhauMoi.getText();
        String nhapLaiMatKhauMoi = txtNhapLaiMatKhauMoi.getText();
        if(matKhauCu.length() == 0 || matKhauMoi.length() == 0 || nhapLaiMatKhauMoi.length() == 0) {
            lblStatus.setText("Hãy điền cả 3 trường");
            return;
        }

        if(taiKhoan.getClass() == SinhVien.class) {
            SinhVien sv = (SinhVien)taiKhoan;

            if(BCrypt.checkpw(matKhauCu, sv.getMatKhau()) == false) {
                lblStatus.setText("Sai mật khẩu");
                return;
            }
            else if(nhapLaiMatKhauMoi.compareTo(matKhauMoi) != 0) {
                lblStatus.setText("Mật khẩu mới chưa khớp nhau");
            }
            else if(matKhauMoi.compareTo(matKhauCu) == 0) {
                lblStatus.setText("Mật khẩu mới phải khác mật khẩu cũ");
            }
            else {
                sv.setMatKhau(BCrypt.hashpw(matKhauMoi, BCrypt.gensalt()));
                boolean r = SinhVienDAO.ThemHoacCapNhatSinhVien(sv);
                stage.close();
                if(r == true) {
                    controllerCha.setStatus("Đổi mật khẩu thành công");
                }
                else {
                    controllerCha.setStatus("Đổi mật khẩu thất bại");
                }
            }
        }
        else if (taiKhoan.getClass() == GiaoVu.class) {
            GiaoVu gv = (GiaoVu)taiKhoan;

            if(BCrypt.checkpw(matKhauCu, gv.getMatKhau()) == false) {
                lblStatus.setText("Sai mật khẩu");
                return;
            }
            else if(nhapLaiMatKhauMoi.compareTo(matKhauMoi) != 0) {
                lblStatus.setText("Mật khẩu mới chưa khớp nhau");
            }
            else {
                gv.setMatKhau(BCrypt.hashpw(matKhauMoi, BCrypt.gensalt()));
                boolean r = GiaoVuDAO.ThemHoacCapNhatGiaoVu(gv);
                stage.close();
                if(r == true) {
                    controllerCha.setStatus("Đổi mật khẩu thành công");
                }
                else {
                    controllerCha.setStatus("Đổi mật khẩu thất bại");
                }
            }
        }
    }

    public void HuyTacVu() {
        stage.close();
    }

    public void init(Object taiKhoan) {
        this.taiKhoan = taiKhoan;
    }
}
