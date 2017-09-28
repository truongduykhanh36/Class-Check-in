package UI;

import DAO.DiemDanhDAO;
import DAO.MonHocDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.ContextMenuEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pojo.DiemDanh;
import pojo.MonHoc;
import pojo.SinhVien;
import util.BCrypt;

import java.net.URL;
import java.util.*;

public class SinhVien_ManHinhChinhController extends StageSceneController implements Initializable{
    private SinhVien sv;
    private List<DiemDanh> dsDiemDanh;
    private List<MonHoc> dsKhoaHoc;

    public void setSv(SinhVien sv) {
        this.sv = sv;
    }

    @FXML
    ListView<String> lstDanhSachKhoaHoc;

    @FXML
    Label lblStatus;

    @FXML
    MenuItem mnDiemDanh;

    public void DangXuat() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DangNhap.fxml"));
            Parent root = loader.load();
            DangNhapController controller = loader.getController();
            controller.setStage(stage);

            stage.setTitle("Quản lý Điểm danh");
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.centerOnScreen();

            stage.show();
        } catch (Exception e) {
            System.err.println("Không thể load Scene Đăng nhập");
        }
    }

    public void ThayDoiMatKhau() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DoiMatKhau.fxml"));
            Parent root = loader.load();
            DoiMatKhauController controller = loader.getController();
            controller.init(sv);
            controller.setControllerCha(this);

            Stage stage = new Stage();
            stage.setTitle("Đổi mật khẩu");
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.centerOnScreen();

            controller.setStage(stage);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(this.stage);
            stage.showAndWait();
        } catch (Exception e) {
            System.err.println("Không thể load Scene Đổi mật khẩu");
        }
    }

    public void DiemDanh() {
        int index = lstDanhSachKhoaHoc.getSelectionModel().getSelectedIndex();
        int tuanHienTai = dsKhoaHoc.get(index).tuanHienTai(Calendar.getInstance());
        if(dsDiemDanh.get(index).getDiemDanh()[index] == '1') {
            setStatus("Đã điểm danh");
        }
        else {
            dsDiemDanh.get(index).setDiemDanh(tuanHienTai, (byte) '1');
            boolean r = DiemDanhDAO.themHoacCapNhatThongTinDiemDanh(dsDiemDanh.get(index));
            if (r == true) {
                capNhatListViewTaiViTri(index);
                setStatus("Điểm danh thành công");
            } else {
                setStatus("Lưu vào database thất bại");
            }
        }
    }

    public void KiemTraCoTheDiemDanh() {
        MultipleSelectionModel<String> selectionModel = lstDanhSachKhoaHoc.getSelectionModel();
        if(selectionModel.getSelectedItems().isEmpty()) {
            mnDiemDanh.setDisable(true);
        }
        else {
            int index = selectionModel.getSelectedIndex();
            if(!dsKhoaHoc.get(index).isCoTheDiemDanh(Calendar.getInstance())) {
                mnDiemDanh.setDisable(true);
            }
            else {
                mnDiemDanh.setDisable(false);
            }
        }
    }

    private void capNhatListViewTaiViTri(int index) {
        lstDanhSachKhoaHoc.getItems().set(index, layThongTinChoListItem(index));
        lstDanhSachKhoaHoc.refresh();
    }

    public void layDuLieu() {
        dsDiemDanh = DiemDanhDAO.layDSDiemDanhTheoSinhVien(sv.getMssv());

        List<String> dsMaMon = new LinkedList<>();
        for (DiemDanh dd : dsDiemDanh) {
            dsMaMon.add(dd.getMaMon());
        }
        dsKhoaHoc = MonHocDAO.layDanhSachMonHocTheoSinhVien(dsMaMon);

        dsDiemDanh.sort(Comparator.comparing(DiemDanh::getMaMon));
        dsKhoaHoc.sort(Comparator.comparing(MonHoc::getMaMon));
    }

    public void fillDuLieuLenView() {
        for (int i = 0; i < dsKhoaHoc.size(); i++) {
            lstDanhSachKhoaHoc.getItems().add(layThongTinChoListItem(i));
        }
    }

    private String layThongTinChoListItem(int index) {
        MonHoc mh = dsKhoaHoc.get(index);
        String thongTinTomTat = mh.getTenMon() + " | " + mh.layGioHocTrongTuan();
        int tuanHienTai = mh.tuanHienTai(Calendar.getInstance());
        thongTinTomTat += (" | " + dsDiemDanh.get(index).layThongTinTomTat(tuanHienTai));
        return thongTinTomTat;
    }

    public void hienThiThongTinChiTiet() {
        int index = lstDanhSachKhoaHoc.getSelectionModel().getSelectedIndex();
        int tuanHienTai = dsKhoaHoc.get(index).tuanHienTai(Calendar.getInstance());
        String thongTinChiTiet = dsDiemDanh.get(index).layThongTinChiTiet(tuanHienTai);
        lblStatus.setText(thongTinChiTiet);
    }

    public void YeuCauDoiMatKhauOLanDangNhapDauTien() {
        if (BCrypt.checkpw(sv.getMssv(), sv.getMatKhau())) {
            ThayDoiMatKhau();
        }
    }

    @Override
    public void setStatus(String status) {
        lblStatus.setText(status);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lstDanhSachKhoaHoc.addEventFilter(ContextMenuEvent.CONTEXT_MENU_REQUESTED, event -> {
            MultipleSelectionModel<String> duLieuVungChon = lstDanhSachKhoaHoc.getSelectionModel();
            if(duLieuVungChon.getSelectedItems().isEmpty())
                event.consume();
        });
    }
}
