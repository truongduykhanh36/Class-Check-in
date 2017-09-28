package UI;

import DAO.DiemDanhDAO;
import DAO.MonHocDAO;
import DAO.SinhVienDAO;
import com.sun.javafx.scene.control.skin.TableHeaderRow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ContextMenuEvent;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pojo.DiemDanh;
import pojo.GiaoVu;
import pojo.MonHoc;
import pojo.SinhVien;

import java.io.File;
import java.net.URL;
import java.util.*;

public class GiaoVu_ManHinhChinh_Controller extends StageSceneController implements Initializable {
    private GiaoVu gv;

    public void setGv(GiaoVu gv) {
        this.gv = gv;
    }

    List<MonHoc> dsKhoaHoc;
    List<List<DiemDanh>> dsDiemDanh = new ArrayList<>();
    List<SinhVien> dsSinhVien;

    @FXML
    ListView lstDSKhoaHoc;

    @FXML
    Label lblStatus;

    @FXML
    TableView<SinhVienD4V> tblDiemDanh;

    @FXML
    TableColumn clmMSSV, clmHoTen, clm1, clm2, clm3, clm4, clm5, clm6, clm7, clm8, clm9, clm10, clm11, clm12, clm13, clm14, clm15;

    @FXML
    MenuItem mnCoMat, mnVang;

    @FXML
    TextField txtMSSV, txtHoTen;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Để khóa vị trí các column, không cho người dùng thay đổi.
        tblDiemDanh.widthProperty().addListener((source, oldWidth, newWidth) -> {
            TableHeaderRow header = (TableHeaderRow) tblDiemDanh.lookup("TableHeaderRow");
            header.reorderingProperty().addListener((observable, oldValue, newValue) ->
                    header.setReordering(false));
        });

        //Để chọn từng ô chứ không chọn từng dòng
        TableView.TableViewSelectionModel<SinhVienD4V> selectionModel = tblDiemDanh.getSelectionModel();
        selectionModel.cellSelectionEnabledProperty().setValue(true);
        selectionModel.setSelectionMode(SelectionMode.SINGLE);

        //Set field dữ liệu để fill cho các cột
        clmMSSV.setCellValueFactory(new PropertyValueFactory<SinhVienD4V, String>("MSSV"));
        clmHoTen.setCellValueFactory(new PropertyValueFactory<SinhVienD4V, String>("HoTen"));
        clm1.setCellValueFactory(new PropertyValueFactory<SinhVienD4V, String>("diemDanh1"));
        clm2.setCellValueFactory(new PropertyValueFactory<SinhVienD4V, String>("diemDanh2"));
        clm3.setCellValueFactory(new PropertyValueFactory<SinhVienD4V, String>("diemDanh3"));
        clm4.setCellValueFactory(new PropertyValueFactory<SinhVienD4V, String>("diemDanh4"));
        clm5.setCellValueFactory(new PropertyValueFactory<SinhVienD4V, String>("diemDanh5"));
        clm6.setCellValueFactory(new PropertyValueFactory<SinhVienD4V, String>("diemDanh6"));
        clm7.setCellValueFactory(new PropertyValueFactory<SinhVienD4V, String>("diemDanh7"));
        clm8.setCellValueFactory(new PropertyValueFactory<SinhVienD4V, String>("diemDanh8"));
        clm9.setCellValueFactory(new PropertyValueFactory<SinhVienD4V, String>("diemDanh9"));
        clm10.setCellValueFactory(new PropertyValueFactory<SinhVienD4V, String>("diemDanh10"));
        clm11.setCellValueFactory(new PropertyValueFactory<SinhVienD4V, String>("diemDanh11"));
        clm12.setCellValueFactory(new PropertyValueFactory<SinhVienD4V, String>("diemDanh12"));
        clm13.setCellValueFactory(new PropertyValueFactory<SinhVienD4V, String>("diemDanh13"));
        clm14.setCellValueFactory(new PropertyValueFactory<SinhVienD4V, String>("diemDanh14"));
        clm15.setCellValueFactory(new PropertyValueFactory<SinhVienD4V, String>("diemDanh15"));

        //Để context menu chỉ hiện ở vị trí hợp lý
        tblDiemDanh.addEventFilter(ContextMenuEvent.CONTEXT_MENU_REQUESTED, event -> {
            TableView.TableViewSelectionModel<SinhVienD4V> duLieuVungChon = tblDiemDanh.getSelectionModel();
            if (duLieuVungChon.getSelectedCells().isEmpty()) event.consume();

            int colum = duLieuVungChon.getSelectedCells().get(0).getColumn();
            if (colum < 2) event.consume();

            int row = duLieuVungChon.getSelectedCells().get(0).getRow();
            int tuanHienTai = monHocHienTai.tuanHienTai(Calendar.getInstance());
            if (colum - 2 > tuanHienTai) {
                mnCoMat.setDisable(true);
                mnVang.setDisable(true);
                setStatus("Tuần này chưa học tới nên chưa được phép sửa");
            } else {
                char dd = (char) tblDiemDanh.getItems().get(row).getDiemDanh(colum - 2);
                if (dd == '0') {
                    mnCoMat.setDisable(false);
                    mnVang.setDisable(true);
                } else {
                    mnCoMat.setDisable(true);
                    mnVang.setDisable(false);
                }
            }
        });
        lstDSKhoaHoc.addEventFilter(ContextMenuEvent.CONTEXT_MENU_REQUESTED, event -> {
            if (lstDSKhoaHoc.getSelectionModel().getSelectedItems().isEmpty()) event.consume();
        });
    }

    public void ThemKhoaHoc() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ThemKhoaHocMoi.fxml"));
            Parent root = loader.load();
            ThemKhoaHocMoiController controller = loader.getController();
            controller.setControllerCha(this);

            Stage stage = new Stage();
            stage.setTitle("Thêm Khóa học mới");
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.centerOnScreen();

            controller.setStage(stage);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(this.stage);
            stage.showAndWait();
        } catch (Exception e) {
            setStatus("Không thể load Scene Thêm Khóa học mới");
        }
    }

    public void SuaThongTinKhoaHoc() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ThemKhoaHocMoi.fxml"));
            Parent root = loader.load();
            ThemKhoaHocMoiController controller = loader.getController();
            controller.setControllerCha(this);
            controller.initWithData(monHocHienTai);

            Stage stage = new Stage();
            stage.setTitle("Sửa Khóa học");
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.centerOnScreen();

            controller.setStage(stage);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(this.stage);
            stage.showAndWait();
        } catch (Exception e) {
            setStatus("Không thể load Scene Sửa Khóa học");
        }
    }

    @Override
    public void capNhatDuLieu(Object o, boolean flag) {
        MonHoc mhMoi = (MonHoc)o;
        if(flag) {
            for(int i = 0; i < dsKhoaHoc.size(); i++) {
                MonHoc mh = dsKhoaHoc.get(i);
                if(mh.getMaMon().compareTo(mhMoi.getMaMon()) == 0) {
                    mh.setTenMon(mhMoi.getTenMon());
                    mh.setGioBatDau(mhMoi.getGioBatDau());
                    mh.setGioKetThuc(mhMoi.getGioKetThuc());
                    mh.setNgayBatDau(mhMoi.getNgayBatDau());
                    mh.setPhongHoc(mhMoi.getPhongHoc());

                    String thongTinListItem = mh.getTenMon() + " - " + mh.getPhongHoc();
                    lstDSKhoaHoc.getItems().set(i, thongTinListItem);

                    break;
                }
            }
        }
        else {
            dsKhoaHoc.add(mhMoi);
            dsDiemDanh.add(new ArrayList<>());
            String thongTinListItem = mhMoi.getTenMon() + " - " + mhMoi.getPhongHoc();
            lstDSKhoaHoc.getItems().add(thongTinListItem);
            lstDSKhoaHoc.refresh();
        }
    }

    public void ThemSinhVienNhapTay() {
        String MSSV = txtMSSV.getText();
        String HoTen = txtHoTen.getText();
        txtMSSV.setText("");
        txtHoTen.setText("");

        if (dsDiemDanhHienTai == null) {
            setStatus("Hãy chọn khóa học");
            return;
        } else {
            if(MSSV.length() == 0) {
                setStatus("Nhập MSSV");
                return;
            }
            if(HoTen.length() == 0) HoTen = "UNKNOWN";
            ThemSinhVienVaoKhoaHoc(MSSV, HoTen);
        }
    }

    String initDirectory = System.getProperty("user.dir");
    public void ThemSinhVienTuCSV() {
        if (dsDiemDanhHienTai == null) {
            setStatus("Hãy chọn khóa học");
        } else {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Chọn file CSV");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV File", "*.csv"));
            fileChooser.setInitialDirectory(new File(initDirectory));
            List<File> list = fileChooser.showOpenMultipleDialog(stage);
            if(list != null) {
                int soSinhVienDaThemVaoThanhCong = 0;
                initDirectory = list.get(0).getParent();
                for(File file : list) {
                    List<SinhVien> dsSinhVienCanThemVao = SinhVien.parseFromCSV(file.getAbsolutePath());
                    for(SinhVien sv : dsSinhVienCanThemVao) {
                        soSinhVienDaThemVaoThanhCong += ThemSinhVienVaoKhoaHoc(sv.getMssv(), sv.getTen());
                    }
                }
                setStatus("Đã thêm " + soSinhVienDaThemVaoThanhCong + " Sinh viên vào " + monHocHienTai.getTenMon());
            }
        }
    }

    private int ThemSinhVienVaoKhoaHoc(String MSSV, String HoTen) {
        for (DiemDanh dd : dsDiemDanhHienTai) {
            if (dd.getMssv().compareTo(MSSV) == 0) {
                setStatus("Sinh viên " + MSSV + " đã có trong khóa học");
                return 0;
            }
        }

        //Nếu sinh viên chưa có trong khóa học thì ghi vào database
        boolean chuaTonTai = SinhVienDAO.ThemSinhVienNeuChuaTonTai(new SinhVien(MSSV, HoTen));
        boolean r = DiemDanhDAO.themHoacCapNhatThongTinDiemDanh(new DiemDanh(MSSV, monHocHienTai.getMaMon()));
        if (r) {
            setStatus("Đã thêm " + MSSV + " vào " + monHocHienTai.getTenMon());
            dsDiemDanhHienTai.add(new DiemDanh(MSSV, monHocHienTai.getMaMon()));

            //Nếu ghi vào database thành công thì mới thêm dữ liệu vào RAM và fill lên view
            SinhVien svMoi;
            if(chuaTonTai) {
                svMoi = new SinhVien(MSSV, HoTen);
                dsSinhVien.add(svMoi);
            }
            else {
                svMoi = SinhVienDAO.layThongTinSinhVien(MSSV);
            }

            tblDiemDanh.getItems().add(new SinhVienD4V(svMoi, new DiemDanh(MSSV, monHocHienTai.getMaMon())));
            tblDiemDanh.refresh();
            return 1;
        } else {
            setStatus("Lỗi ở Database");
            return 0;
        }
    }

    public void DiemDanhCoMat() {
        TableView.TableViewSelectionModel<SinhVienD4V> duLieuVungChon = tblDiemDanh.getSelectionModel();

        int row = duLieuVungChon.getSelectedCells().get(0).getRow();
        int colum = duLieuVungChon.getSelectedCells().get(0).getColumn();
        tblDiemDanh.getItems().get(row).getDiemDanh().setDiemDanh(colum - 2, (byte) '1');
        tblDiemDanh.refresh();

        boolean r = DiemDanhDAO.themHoacCapNhatThongTinDiemDanh(tblDiemDanh.getItems().get(row).getDiemDanh());
        if (r) {
            setStatus("Sửa điểm danh thành công");
        } else {
            setStatus("Lỗi, không thể lưu vào Database");
        }
    }

    public void DiemDanhVang() {
        TableView.TableViewSelectionModel<SinhVienD4V> duLieuVungChon = tblDiemDanh.getSelectionModel();

        int row = duLieuVungChon.getSelectedCells().get(0).getRow();
        int colum = duLieuVungChon.getSelectedCells().get(0).getColumn();
        tblDiemDanh.getItems().get(row).getDiemDanh().setDiemDanh(colum - 2, (byte) '0');
        tblDiemDanh.refresh();

        boolean r = DiemDanhDAO.themHoacCapNhatThongTinDiemDanh(tblDiemDanh.getItems().get(row).getDiemDanh());
        if (r) {
            setStatus("Sửa điểm danh thành công");
        } else {
            setStatus("Lỗi, không thể lưu vào Database");
        }
    }

    public void layDuLieu() {
        dsKhoaHoc = MonHocDAO.layDanhSachMonHoc();
        Calendar thisTime = Calendar.getInstance();
        dsKhoaHoc.sort(Comparator.comparingInt(mh -> mh.tieuChiSapXep(thisTime)));

        for (MonHoc mh : dsKhoaHoc) {
            List<DiemDanh> dsDiemDanhCuaKhoaHocHienTai = DiemDanhDAO.layDSDiemDanhTheoMonHoc(mh.getMaMon());
            if(!dsDiemDanhCuaKhoaHocHienTai.isEmpty()) {
                dsDiemDanh.add(dsDiemDanhCuaKhoaHocHienTai);
            }
        }

        dsSinhVien = SinhVienDAO.layDanhSachSinhVien();
    }

    public void fillDuLieuKhoaHocLenView() {
        for (MonHoc mh : dsKhoaHoc) {
            String thongTinListItem = mh.getTenMon() + " - " + mh.getPhongHoc();
            lstDSKhoaHoc.getItems().add(thongTinListItem);
        }
        lstDSKhoaHoc.refresh();
    }

    MonHoc monHocHienTai = new MonHoc();
    List<DiemDanh> dsDiemDanhHienTai = null;

    public void yeuCauFillDuLieuDiemDanhLenView() {
        final MultipleSelectionModel duLieuCacItemDuocChon = lstDSKhoaHoc.getSelectionModel();
        if (duLieuCacItemDuocChon.getSelectedItems().isEmpty()) return;
        int index = duLieuCacItemDuocChon.getSelectedIndex();
        if (monHocHienTai.getMaMon().compareTo(dsKhoaHoc.get(index).getMaMon()) != 0) {
            monHocHienTai = dsKhoaHoc.get(index);
            fillDuLieuDiemDanhTuongUngLenView(monHocHienTai);
        }
    }

    private void fillDuLieuDiemDanhTuongUngLenView(MonHoc monHocYeuCau) {
        String maMonYeuCau = monHocYeuCau.getMaMon();
        int tuanHienTai = monHocYeuCau.tuanHienTai(Calendar.getInstance());
        if(tuanHienTai < 0) setStatus("Khóa học Chưa Bắt đầu");
        else if (tuanHienTai >= 15) setStatus("Khóa học Đã Kết thúc");
        else setStatus("Số tuần Đã học: " + (tuanHienTai + 1));

        boolean isCoSinhVienTrongLop = false;
        for (List<DiemDanh> l : dsDiemDanh) {
            if (!l.isEmpty()) {
                if (l.get(0).getMaMon().compareTo(maMonYeuCau) == 0) {
                    isCoSinhVienTrongLop = true;
                    dsDiemDanhHienTai = l;
                    break;
                }
            }
        }
        if(!isCoSinhVienTrongLop) {
            dsDiemDanhHienTai = new ArrayList<>();
            dsDiemDanh.add(dsDiemDanhHienTai);
        }

        ObservableList<SinhVienD4V> data = buildDuLieuChoTableView(dsDiemDanhHienTai);
        tblDiemDanh.setItems(data);
        tblDiemDanh.refresh();
    }

    private ObservableList<SinhVienD4V> buildDuLieuChoTableView(List<DiemDanh> dsDiemDanhCuaMonHoc) {
        ObservableList<SinhVienD4V> dt = FXCollections.observableArrayList();
        List<SinhVien> dsSinhVienCuaKhoaHoc = new ArrayList<>();
        for (DiemDanh dd : dsDiemDanhCuaMonHoc) {
            String mssv = dd.getMssv();
            for (SinhVien sv : dsSinhVien) {
                if (sv.getMssv().compareTo(mssv) == 0) {
                    dsSinhVienCuaKhoaHoc.add(sv);
                    break;
                }
            }
        }

        dsDiemDanhCuaMonHoc.sort(Comparator.comparing(DiemDanh::getMssv));
        dsSinhVienCuaKhoaHoc.sort(Comparator.comparing(SinhVien::getMssv));
        for (int i = 0; i < dsDiemDanhCuaMonHoc.size(); i++) {
            dt.add(new SinhVienD4V(dsSinhVienCuaKhoaHoc.get(i), dsDiemDanhCuaMonHoc.get(i)));
        }

        return dt;
    }

    public void DangXuat() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DangNhap.fxml"));
            Parent root = loader.load();
            DangNhapController controller = loader.getController();

            Stage stageDangNhap = new Stage();
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
            controller.init(gv);
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

    @Override
    public void setStatus(String status) {
        lblStatus.setText(status);
    }
}
