-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema quanlymonhoc
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema quanlymonhoc
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `quanlymonhoc` DEFAULT CHARACTER SET utf8 ;
USE `quanlymonhoc` ;

-- -----------------------------------------------------
-- Table `quanlymonhoc`.`mon_hoc`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `quanlymonhoc`.`mon_hoc` (
  `ma_mon` VARCHAR(10) NOT NULL,
  `ten_mon` VARCHAR(30) CHARACTER SET 'utf8' NULL DEFAULT NULL,
  `ngay_bat_dau` DATE NULL DEFAULT NULL,
  `gio_bat_dau` TIME NULL DEFAULT NULL,
  `gio_ket_thuc` TIME NULL DEFAULT NULL,
  `phong_hoc` VARCHAR(10) CHARACTER SET 'utf8' NULL DEFAULT NULL,
  PRIMARY KEY (`ma_mon`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `quanlymonhoc`.`sinh_vien`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `quanlymonhoc`.`sinh_vien` (
  `mssv` VARCHAR(7) NOT NULL,
  `tai_khoan` VARCHAR(7) NOT NULL,
  `mat_khau` VARCHAR(60) BINARY NOT NULL,
  `ten` VARCHAR(30) CHARACTER SET 'utf8' NULL DEFAULT NULL,
  PRIMARY KEY (`mssv`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `quanlymonhoc`.`diem_danh`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `quanlymonhoc`.`diem_danh` (
  `mssv` VARCHAR(7) NOT NULL,
  `ma_mon` VARCHAR(10) NOT NULL,
  `diem_danh` BINARY(15) NULL DEFAULT NULL,
  PRIMARY KEY (`mssv`, `ma_mon`),
  INDEX `ma_mon_fk` (`ma_mon` ASC),
  CONSTRAINT `ma_mon_fk`
    FOREIGN KEY (`ma_mon`)
    REFERENCES `quanlymonhoc`.`mon_hoc` (`ma_mon`),
  CONSTRAINT `mssv_fk`
    FOREIGN KEY (`mssv`)
    REFERENCES `quanlymonhoc`.`sinh_vien` (`mssv`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `quanlymonhoc`.`giao_vu`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `quanlymonhoc`.`giao_vu` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `tai_khoan` VARCHAR(30) NOT NULL,
  `mat_khau` VARCHAR(60) BINARY NOT NULL,
  `ten` VARCHAR(30) CHARACTER SET 'utf8' NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;



/* Đổ dữ liệu vào ------------------------ */
INSERT INTO `quanlymonhoc`.`sinh_vien` (`mssv`, `tai_khoan`, `mat_khau`, `ten`) VALUES ('1412204', '1412204', '$2a$10$cHr1XNQjvtMZoc3i/dWo8ObCAdZeburazo3nS/5qUMmnLHK8m900.', 'Nguyễn Công Tuấn Huy');
INSERT INTO `quanlymonhoc`.`sinh_vien` (`mssv`, `tai_khoan`, `mat_khau`, `ten`) VALUES ('1412243', '1412243', '$2a$10$obp0rsrXV0FOogbQacJm/.zqeTA7EO1.osU9aeheZG1lI6kvDepKK', 'Trương Duy Khánh');
INSERT INTO `quanlymonhoc`.`sinh_vien` (`mssv`, `tai_khoan`, `mat_khau`, `ten`) VALUES ('1412252', '1412252', '$2a$10$u9vnw3nGGXOFgko.wgDz9uQ8Mgyx8vJXN8L.X7xnWJHrL0ipC//xu', 'Trần Lê Anh Khoa');


INSERT INTO `quanlymonhoc`.`mon_hoc` (`ma_mon`, `ten_mon`, `ngay_bat_dau`, `gio_bat_dau`, `gio_ket_thuc`, `phong_hoc`) VALUES ('JAVA01', 'Lập trình Java', '2017/2/10', '7:30:00', '9:30:00', 'C22');
INSERT INTO `quanlymonhoc`.`mon_hoc` (`ma_mon`, `ten_mon`, `ngay_bat_dau`, `gio_bat_dau`, `gio_ket_thuc`, `phong_hoc`) VALUES ('PTUDW01', 'Phát triển Ứng dụng Web', '2017/2/12', '9:20:00', '11:20:00', 'E201');
INSERT INTO `quanlymonhoc`.`mon_hoc` (`ma_mon`, `ten_mon`, `ngay_bat_dau`, `gio_bat_dau`, `gio_ket_thuc`, `phong_hoc`) VALUES ('LTDD01', 'Lập trình Di động 1', '2016/2/12', '12:30:00', '14:30:00', 'C32');
INSERT INTO `quanlymonhoc`.`mon_hoc` (`ma_mon`, `ten_mon`, `ngay_bat_dau`, `gio_bat_dau`, `gio_ket_thuc`, `phong_hoc`) VALUES ('TTHCM01', 'Tư tưởng Hồ Chí Minh', '2017/4/13', '7:30:00', '9:30:00', 'GĐ02');


INSERT INTO `quanlymonhoc`.`giao_vu` (`tai_khoan`, `mat_khau`, `ten`) VALUES ('GV01', '$2a$10$cpOfaehmPXv0m2sb8XuRhO9bEL.lfIt4n7NNDRvMM9WXzcRduX7SO', 'Nguyễn Văn A');
INSERT INTO `quanlymonhoc`.`giao_vu` (`tai_khoan`, `mat_khau`, `ten`) VALUES ('GV02', '$2a$10$pExXLLN/euuwqzI084lz0.ZPfBc8wIPmTWEK4ojdFhZz8Pqt636Om', 'Trần Thị B');
INSERT INTO `quanlymonhoc`.`giao_vu` (`tai_khoan`, `mat_khau`, `ten`) VALUES ('GV03', '$2a$10$Hs1swehMKgRTuR1uV.XuGO7xqfKIVATiyDCYRV12keq/aKoJPgTR.', 'Đỗ Văn C');


INSERT INTO `quanlymonhoc`.`diem_danh` (`mssv`, `ma_mon`, `diem_danh`) VALUES ('1412204', 'TTHCM01', '000000000000000');
INSERT INTO `quanlymonhoc`.`diem_danh` (`mssv`, `ma_mon`, `diem_danh`) VALUES ('1412204', 'JAVA01', '000000000000000');
INSERT INTO `quanlymonhoc`.`diem_danh` (`mssv`, `ma_mon`, `diem_danh`) VALUES ('1412204', 'PTUDW01', '000000000000000');
INSERT INTO `quanlymonhoc`.`diem_danh` (`mssv`, `ma_mon`, `diem_danh`) VALUES ('1412204', 'LTDD01', '000000000000000');
INSERT INTO `quanlymonhoc`.`diem_danh` (`mssv`, `ma_mon`, `diem_danh`) VALUES ('1412243', 'TTHCM01', '000000000000000');
INSERT INTO `quanlymonhoc`.`diem_danh` (`mssv`, `ma_mon`, `diem_danh`) VALUES ('1412243', 'JAVA01', '000000000000000');
INSERT INTO `quanlymonhoc`.`diem_danh` (`mssv`, `ma_mon`, `diem_danh`) VALUES ('1412243', 'PTUDW01', '000000000000000');
INSERT INTO `quanlymonhoc`.`diem_danh` (`mssv`, `ma_mon`, `diem_danh`) VALUES ('1412243', 'LTDD01', '000000000000000');
INSERT INTO `quanlymonhoc`.`diem_danh` (`mssv`, `ma_mon`, `diem_danh`) VALUES ('1412252', 'TTHCM01', '000000000000000');
INSERT INTO `quanlymonhoc`.`diem_danh` (`mssv`, `ma_mon`, `diem_danh`) VALUES ('1412252', 'JAVA01', '000000000000000');
INSERT INTO `quanlymonhoc`.`diem_danh` (`mssv`, `ma_mon`, `diem_danh`) VALUES ('1412252', 'PTUDW01', '000000000000000');
INSERT INTO `quanlymonhoc`.`diem_danh` (`mssv`, `ma_mon`, `diem_danh`) VALUES ('1412252', 'LTDD01', '000000000000000');