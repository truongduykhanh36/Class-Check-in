package UI;

import DAO.MonHocDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import pojo.MonHoc;

import java.net.URL;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.ResourceBundle;

public class ThemKhoaHocMoiController extends StageSceneController implements Initializable {
    @FXML
    TextField txtMaKhoaHoc, txtTenKhoaHoc,txtGioBatDau,txtPhutBatDau,txtGioKetThuc,txtPhutKetThuc,txtPhongHoc;

    @FXML
    DatePicker dtNgayBatDau;

    @FXML
    Label lblStatus;

    private MonHoc monHoc = new MonHoc();
    private boolean capNhat = false;

    public void initWithData(MonHoc mh) {
        //Nếu có dữ liệu gốc nghĩa là Sửa khóa học
        this.monHoc = mh;
        capNhat = true;
        txtMaKhoaHoc.setText(monHoc.getMaMon());
        txtMaKhoaHoc.setEditable(false);
        txtTenKhoaHoc.setText(monHoc.getTenMon());
        dtNgayBatDau.setValue(monHoc.getNgayBatDau().toLocalDate());

        Calendar ngayGioBatDau = monHoc.layNgayGioBatDauChuan();
        Calendar gioKetThuc = monHoc.layGioKetThucChuan();
        txtGioBatDau.setText(ngayGioBatDau.get(Calendar.HOUR_OF_DAY) + "");
        txtPhutBatDau.setText(ngayGioBatDau.get(Calendar.MINUTE) + "");
        txtGioKetThuc.setText(gioKetThuc.get(Calendar.HOUR_OF_DAY) + "");
        txtPhutKetThuc.setText(gioKetThuc.get(Calendar.MINUTE) + "");
        txtPhongHoc.setText(monHoc.getPhongHoc());
    }
    public void ThemHoacCapNhatKhoaHoc() {
        if(kiemTraDuLieuDauVao()) {
            monHoc.setMaMon(txtMaKhoaHoc.getText());
            monHoc.setTenMon(txtTenKhoaHoc.getText());
            monHoc.setNgayBatDau(Date.valueOf(dtNgayBatDau.getValue()));
            monHoc.setGioBatDau(Time.valueOf(txtGioBatDau.getText() + ":" + txtPhutBatDau.getText() + ":00"));
            monHoc.setGioKetThuc(Time.valueOf(txtGioKetThuc.getText() + ":" + txtPhutKetThuc.getText() + ":00"));
            monHoc.setPhongHoc(txtPhongHoc.getText());

            boolean r = MonHocDAO.themHoacCapNhatMonHoc(monHoc);
            if(r) {
                stage.close();
                if(capNhat) {
                    controllerCha.setStatus("Cập nhật Thành công");
                }
                else {
                    controllerCha.setStatus("Đã Thêm Khóa học " + monHoc.getTenMon());
                }
                controllerCha.capNhatDuLieu(monHoc, capNhat);
            }
            else {
                setStatus("Lỗi ở Database, thử lại sau");
            }
        }
    }
    public void HuyTacVu() {
        stage.close();
    }

    private boolean kiemTraDuLieuDauVao() {
        if(txtMaKhoaHoc.getText().length() == 0
                || txtTenKhoaHoc.getText().length() == 0
                || txtGioBatDau.getText().length() == 0
                || txtPhutBatDau.getText().length() == 0
                || txtGioKetThuc.getText().length() == 0
                || txtPhutKetThuc.getText().length() == 0
                || txtPhongHoc.getText().length() == 0) {
            setStatus("Hãy điền đủ các trường");
            return false;
        }
        try {
            LocalDate ld = dtNgayBatDau.getValue();
            int gioBatDau = Integer.parseInt(txtGioBatDau.getText());
            int gioKetThuc = Integer.parseInt(txtGioKetThuc.getText());
            int phutBatDau = Integer.parseInt(txtPhutBatDau.getText());
            int phutKetThuc = Integer.parseInt(txtPhutKetThuc.getText());

            if(ld == null
                ||gioBatDau < 0 || gioBatDau > 23
                || gioKetThuc < 0 || gioKetThuc > 23
                || phutBatDau < 0 || phutBatDau > 59
                ||phutKetThuc < 0 || phutKetThuc > 59) {
                setStatus("Dữ liệu ngày giờ không hợp lệ");
                return false;
            }
        }
        catch (Exception e) {
            setStatus("Dữ liệu ngày giờ không hợp lệ");
            return false;
        }

        return true;
    }

    @Override
    public void setStatus(String status) {
        lblStatus.setText(status);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dtNgayBatDau.setConverter(new StringConverter<LocalDate>() {
            private DateTimeFormatter dateTimeFormatter= DateTimeFormatter.ofPattern("dd/MM/yyyy");

            @Override
            public String toString(LocalDate localDate)
            {
                if(localDate==null)
                    return "";
                return dateTimeFormatter.format(localDate);
            }

            @Override
            public LocalDate fromString(String dateString)
            {
                if(dateString==null || dateString.trim().isEmpty())
                {
                    return null;
                }
                return LocalDate.parse(dateString,dateTimeFormatter);
            }
        });
    }
}
