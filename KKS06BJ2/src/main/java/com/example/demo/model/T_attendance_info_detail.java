package com.example.demo.model;

import java.math.BigDecimal;
import java.sql.Date;

/**
 * T_attendance_info_detailテーブルDTO
 * @author hikar
 *
 */
public class T_attendance_info_detail {

    // 社員コード
    private String employeeCd;
    
    // 入力年月
    private String inputPeriod;
    
    // 入力年月日
    private String inputDate;
    
    // 種別
    private String kind;
    
    // 事由
    private String reason;
    
    // 出勤時間
    private String attendanceTime;
    
    // 退勤時間
    private String clockoutTime;
    
    // 普通残業時間
    private BigDecimal overtimeHours;
    
    // 早出残業時間
    private BigDecimal earlyShiftOvertimeHours;
    
    // 遅刻時間
    private BigDecimal lateTime;
    
    // 早退時間
    private BigDecimal leaveEarlyTime;
    
    // 削除フラグ
    private String deleteFlg;
    
    // 登録日時
    private Date insertDate;
    
    // 登録ユーザーコード
    private String insertUserCd;
    
    // 更新日時
    private Date updateDate;
    
    // 更新ユーザーコード
    private String updateUserCd;
    
    // 打刻方法
    private String clockingMethod;
    
    // 実働時間
    private BigDecimal actualHours;
    
    // 休憩時間
    private BigDecimal breakTime;
    
    // 深夜労働時間
    private BigDecimal nightShiftHours;
    
    // 打刻エリアID
    private String clockAreaId;
    
    // 打刻備考
    private String clockRemarks;
    
    // 管理者備考
    private String adminRemarks;
    
    // 確定フラグ
    private String confirmFlg;
    
    // 遅刻取消フラグ
    private String lateCancellFlag;

    /**
     * @return employeeCd
     */
    public String getEmployeeCd() {
        return employeeCd;
    }

    /**
     * @param employeeCd セットする employeeCd
     */
    public void setEmployeeCd(String employeeCd) {
        this.employeeCd = employeeCd;
    }

    /**
     * @return inputPeriod
     */
    public String getInputPeriod() {
        return inputPeriod;
    }

    /**
     * @param inputPeriod セットする inputPeriod
     */
    public void setInputPeriod(String inputPeriod) {
        this.inputPeriod = inputPeriod;
    }

    /**
     * @return inputDate
     */
    public String getInputDate() {
        return inputDate;
    }

    /**
     * @param inputDate セットする inputDate
     */
    public void setInputDate(String inputDate) {
        this.inputDate = inputDate;
    }

    /**
     * @return kind
     */
    public String getKind() {
        return kind;
    }

    /**
     * @param kind セットする kind
     */
    public void setKind(String kind) {
        this.kind = kind;
    }

    /**
     * @return reason
     */
    public String getReason() {
        return reason;
    }

    /**
     * @param reason セットする reason
     */
    public void setReason(String reason) {
        this.reason = reason;
    }

    /**
     * @return attendanceTime
     */
    public String getAttendanceTime() {
        return attendanceTime;
    }

    /**
     * @param attendanceTime セットする attendanceTime
     */
    public void setAttendanceTime(String attendanceTime) {
        this.attendanceTime = attendanceTime;
    }

    /**
     * @return clockoutTime
     */
    public String getClockoutTime() {
        return clockoutTime;
    }

    /**
     * @param clockoutTime セットする clockoutTime
     */
    public void setClockoutTime(String clockoutTime) {
        this.clockoutTime = clockoutTime;
    }

    /**
     * @return overtimeHours
     */
    public BigDecimal getOvertimeHours() {
        return overtimeHours;
    }

    /**
     * @param overtimeHours セットする overtimeHours
     */
    public void setOvertimeHours(BigDecimal overtimeHours) {
        this.overtimeHours = overtimeHours;
    }

    /**
     * @return earlyShiftOvertimeHours
     */
    public BigDecimal getEarlyShiftOvertimeHours() {
        return earlyShiftOvertimeHours;
    }

    /**
     * @param earlyShiftOvertimeHours セットする earlyShiftOvertimeHours
     */
    public void setEarlyShiftOvertimeHours(BigDecimal earlyShiftOvertimeHours) {
        this.earlyShiftOvertimeHours = earlyShiftOvertimeHours;
    }

    /**
     * @return lateTime
     */
    public BigDecimal getLateTime() {
        return lateTime;
    }

    /**
     * @param lateTime セットする lateTime
     */
    public void setLateTime(BigDecimal lateTime) {
        this.lateTime = lateTime;
    }

    /**
     * @return leaveEarlyTime
     */
    public BigDecimal getLeaveEarlyTime() {
        return leaveEarlyTime;
    }

    /**
     * @param leaveEarlyTime セットする leaveEarlyTime
     */
    public void setLeaveEarlyTime(BigDecimal leaveEarlyTime) {
        this.leaveEarlyTime = leaveEarlyTime;
    }

    /**
     * @return deleteFlg
     */
    public String getDeleteFlg() {
        return deleteFlg;
    }

    /**
     * @param deleteFlg セットする deleteFlg
     */
    public void setDeleteFlg(String deleteFlg) {
        this.deleteFlg = deleteFlg;
    }

    /**
     * @return insertDate
     */
    public Date getInsertDate() {
        return insertDate;
    }

    /**
     * @param insertDate セットする insertDate
     */
    public void setInsertDate(Date insertDate) {
        this.insertDate = insertDate;
    }

    /**
     * @return insertUserCd
     */
    public String getInsertUserCd() {
        return insertUserCd;
    }

    /**
     * @param insertUserCd セットする insertUserCd
     */
    public void setInsertUserCd(String insertUserCd) {
        this.insertUserCd = insertUserCd;
    }

    /**
     * @return updateDate
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * @param updateDate セットする updateDate
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * @return updateUserCd
     */
    public String getUpdateUserCd() {
        return updateUserCd;
    }

    /**
     * @param updateUserCd セットする updateUserCd
     */
    public void setUpdateUserCd(String updateUserCd) {
        this.updateUserCd = updateUserCd;
    }

    /**
     * @return clockingMethod
     */
    public String getClockingMethod() {
        return clockingMethod;
    }

    /**
     * @param clockingMethod セットする clockingMethod
     */
    public void setClockingMethod(String clockingMethod) {
        this.clockingMethod = clockingMethod;
    }

    /**
     * @return actualHours
     */
    public BigDecimal getActualHours() {
        return actualHours;
    }

    /**
     * @param actualHours セットする actualHours
     */
    public void setActualHours(BigDecimal actualHours) {
        this.actualHours = actualHours;
    }

    /**
     * @return breakTime
     */
    public BigDecimal getBreakTime() {
        return breakTime;
    }

    /**
     * @param breakTime セットする breakTime
     */
    public void setBreakTime(BigDecimal breakTime) {
        this.breakTime = breakTime;
    }

    /**
     * @return nightShiftHours
     */
    public BigDecimal getNightShiftHours() {
        return nightShiftHours;
    }

    /**
     * @param nightShiftHours セットする nightShiftHours
     */
    public void setNightShiftHours(BigDecimal nightShiftHours) {
        this.nightShiftHours = nightShiftHours;
    }

    /**
     * @return clockAreaId
     */
    public String getClockAreaId() {
        return clockAreaId;
    }

    /**
     * @param clockAreaId セットする clockAreaId
     */
    public void setClockAreaId(String clockAreaId) {
        this.clockAreaId = clockAreaId;
    }

    /**
     * @return clockRemarks
     */
    public String getClockRemarks() {
        return clockRemarks;
    }

    /**
     * @param clockRemarks セットする clockRemarks
     */
    public void setClockRemarks(String clockRemarks) {
        this.clockRemarks = clockRemarks;
    }

    /**
     * @return adminRemarks
     */
    public String getAdminRemarks() {
        return adminRemarks;
    }

    /**
     * @param adminRemarks セットする adminRemarks
     */
    public void setAdminRemarks(String adminRemarks) {
        this.adminRemarks = adminRemarks;
    }

    /**
     * @return confirmFlg
     */
    public String getConfirmFlg() {
        return confirmFlg;
    }

    /**
     * @param confirmFlg セットする confirmFlg
     */
    public void setConfirmFlg(String confirmFlg) {
        this.confirmFlg = confirmFlg;
    }

    /**
     * @return lateCancellFlag
     */
    public String getLateCancellFlag() {
        return lateCancellFlag;
    }

    /**
     * @param lateCancellFlag セットする lateCancellFlag
     */
    public void setLateCancellFlag(String lateCancellFlag) {
        this.lateCancellFlag = lateCancellFlag;
    }

}
