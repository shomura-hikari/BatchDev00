package com.example.demo;

import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import com.example.demo.model.T_attendance_info_detail;

@Component
public class KKS06BJ01Ser implements Tasklet {
    private static final Logger logger = LoggerFactory.getLogger(
            KKS06BJ01Ser.class);

    private static final String SELECT_SQL = "SELECT " + "EMPLOYEE_CD "
            + ", INPUT_PERIOD " + ", INPUT_DATE " + ", KIND " + ", REASON "
            + ", ATTENDANCE_TIME " + ", CLOCKOUT_TIME " + ", OVERTIME_HOURS "
            + ", EARLY_SHIFT_OVERTIME_HOURS " + ", LATE_TIME "
            + ", LEAVE_EARLY_TIME " + ", DELETE_FLG " + ", INSERT_DATE "
            + ", INSERT_USER_CD " + ", UPDATE_DATE " + ", UPDATE_USER_CD "
            + ", CLOCKING_METHOD " + ", ACTUAL_HOURS " + ", BREAK_TIME "
            + ", NIGHT_SHIFT_HOURS " + ", CLOCK_AREA_ID " + ", CLOCK_REMARKS "
            + ", ADMIN_REMARKS " + ", CONFIRM_FLG " + ", LATE_CANCELL_FLAG "
            + "FROM " + "t_attendance_info_detail " + "WHERE "
            + "INPUT_PERIOD <= ? " + ";";

    private static final DateTimeFormatter YM_FMT = DateTimeFormatter.ofPattern(
            "yyyyMM");

    @Override
    public RepeatStatus execute(StepContribution contribution,
            ChunkContext chunkContext) throws Exception {
        logger.info("batch起動");
        String baseYM = LocalDate.now().minusMonths(3).format(YM_FMT);
        System.out.println("基準年月 = " + baseYM);

        List<T_attendance_info_detail> entityResultList = new ArrayList<T_attendance_info_detail>();

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = getConnection();

            // SELECT文の実行
            ps = con.prepareStatement(SELECT_SQL);
            ps.setString(1, baseYM);
            rs = ps.executeQuery();

            // SELECT結果の受け取り
            while (rs.next()) {
                T_attendance_info_detail entityResult = new T_attendance_info_detail();
                entityResult.setEmployeeCd(rs.getString("EMPLOYEE_CD"));
                entityResult.setInputPeriod(rs.getString("INPUT_PERIOD"));
                entityResult.setInputDate(rs.getString("INPUT_DATE"));
                entityResult.setKind(rs.getString("KIND"));
                entityResult.setReason(rs.getString("REASON"));
                entityResult.setAttendanceTime(rs.getString("ATTENDANCE_TIME"));
                entityResult.setClockoutTime(rs.getString("CLOCKOUT_TIME"));
                entityResult.setOvertimeHours(rs.getBigDecimal(
                        "OVERTIME_HOURS"));
                entityResult.setEarlyShiftOvertimeHours(rs.getBigDecimal(
                        "EARLY_SHIFT_OVERTIME_HOURS"));
                entityResult.setLateTime(rs.getBigDecimal("LATE_TIME"));
                entityResult.setLeaveEarlyTime(rs.getBigDecimal(
                        "LEAVE_EARLY_TIME"));
                entityResult.setDeleteFlg(rs.getString("DELETE_FLG"));
                entityResult.setInsertDate(rs.getDate("INSERT_DATE"));
                entityResult.setInsertUserCd(rs.getString("INSERT_USER_CD"));
                entityResult.setUpdateDate(rs.getDate("UPDATE_DATE"));
                entityResult.setUpdateUserCd(rs.getString("UPDATE_USER_CD"));
                entityResult.setClockingMethod(rs.getString("CLOCKING_METHOD"));
                entityResult.setActualHours(rs.getBigDecimal("ACTUAL_HOURS"));
                entityResult.setBreakTime(rs.getBigDecimal("BREAK_TIME"));
                entityResult.setNightShiftHours(rs.getBigDecimal(
                        "NIGHT_SHIFT_HOURS"));
                entityResult.setClockAreaId(rs.getString("CLOCK_AREA_ID"));
                entityResult.setClockRemarks(rs.getString("CLOCK_REMARKS"));
                entityResult.setAdminRemarks(rs.getString("ADMIN_REMARKS"));
                entityResult.setConfirmFlg(rs.getString("CONFIRM_FLG"));
                entityResult.setLateCancellFlag(rs.getString(
                        "LATE_CANCELL_FLAG"));

                entityResultList.add(entityResult);
            }
            // // SELECT結果のログ出力
            // for (int i = 0; i < entityResultList.size(); i++) {
            // logger.info(entityResultList.get(i).toString());
            // }
            // 先頭(0番目)だけ
//            if (!entityResultList.isEmpty()) {
//                logger.info("先頭レコード: {}", entityResultList.get(0));
//            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        // ファイル出力
        if (entityResultList.isEmpty()) {
            // 上記SQLの検索結果0件の場合
            contribution.setExitStatus(ExitStatus.COMPLETED); // 戻り値: 0
            // ExitStatus.COMPLETED:成功終了を表す public static final 定数
            // ExitStatus は Spring Batch ライブラリが持つ定数:最終結果の意味
            logger.info("検索結果 0 件のため終了");
            return RepeatStatus.FINISHED;
        }

        // 出力先ファイル指定
        
        // 出力先ディレクトリ
        final String OUT_DIR = "\\\\wsl.localhost\\Ubuntu\\home\\hikari\\bk";

        // ファイル名作成
        String fileName = baseYM + "_T_ATTENDANCE_INFO_DETAIL_backup.csv";
        Path out = Paths.get(OUT_DIR, fileName);
        // 文字列を「どこに作る／読むファイルか」を示す Path オブジェクト に変換（ディレクトリ＋ファイル名で Path を生成）
        // Path:文字列で書いたパス”をオブジェクト化したもの)
        // Paths.get(...):java.nio.file.Paths にある static メソッド。渡された文字列を、システム既定のファイルシステムで解釈した Path に変換

        // ディレクトリが無ければ作成（既にあれば何もしない）
        Files.createDirectories(out.getParent());
        
        // BufferedWriterは、ファイルなどに「文字列をまとめて書く」ためのバッファ付きラッパー
        // バッファ：読み書き時に データを一旦メモリ上に貯めておく領域
        // ラッパー：既存のクラスや機能を “包んで” 使いやすくするオブジェクト

        try (BufferedWriter bw = Files.newBufferedWriter(out,
                StandardCharsets.UTF_8,
                // StandardOpenOption.CREATE:ファイルが無ければ新規作成
                StandardOpenOption.CREATE,
                // StandardOpenOption.APPEND:あれば末尾に追記（既存の行の後ろに付け足す）
                // StandardOpenOption.APPEND
                // StandardOpenOption.TRUNCATE_EXISTING:上書き、以前の行を全て消して書く
                StandardOpenOption.TRUNCATE_EXISTING)) {
            for (T_attendance_info_detail e : entityResultList) {

                // 25項目をカンマ区切りで連結
                String line = String.join(",", e.getEmployeeCd(), e
                        .getInputPeriod(), e.getInputDate(), e.getKind(), e
                                .getReason(), e.getAttendanceTime(), e
                                        .getClockoutTime(), toStr(e
                                                .getOvertimeHours()), toStr(e
                                                        .getEarlyShiftOvertimeHours()),
                        toStr(e.getLateTime()), toStr(e.getLeaveEarlyTime()), e
                                .getDeleteFlg(), fmtDate(e.getInsertDate()), e
                                        .getInsertUserCd(), fmtDate(e
                                                .getUpdateDate()), e
                                                        .getUpdateUserCd(), e
                                                                .getClockingMethod(),
                        toStr(e.getActualHours()), toStr(e.getBreakTime()),
                        toStr(e.getNightShiftHours()), e.getClockAreaId(), e
                                .getClockRemarks(), e.getAdminRemarks(), e
                                        .getConfirmFlg(), e
                                                .getLateCancellFlag());

                // ファイルへ出力
                bw.write(line);
                bw.newLine();
            }

        } catch (IOException ex) {
            // ファイル出力時例外
            logger.error("ファイル出力で例外発生", ex);
            // 第2引数に ex を渡すとスタックトレースも一緒に記録される
            contribution.setExitStatus(new ExitStatus("8"));
            return RepeatStatus.FINISHED;
        }

        logger.info("CSV 出力完了 ({} 行)", entityResultList.size());
        contribution.setExitStatus(ExitStatus.COMPLETED);
        return RepeatStatus.FINISHED;
    }

    // private CharSequence fmtDate(Date insertDate) {
    // // TODO 自動生成されたメソッド・スタブ
    // return null;
    // }
    //
    // private CharSequence toStr(BigDecimal overtimeHours) {
    // // TODO 自動生成されたメソッド・スタブ
    // return null;
    // }

    // 上だとnull返す。課題終わったら調べる
    private String fmtDate(Date date) {
        if (date == null)
            return "";
        return new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(
                date);
    }

    private String toStr(BigDecimal num) {
        return (num == null) ? "" : num.toPlainString();
    }

    /**
     * 引数のコネクションで、PostgreSQLへ接続
     * @param con コネクション
     * @throws SQLException
     */
    private Connection getConnection() throws SQLException {

        Connection con = null;

        // 接続文字列
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String user = "postgres";
        String password = "pgP@ssw0rd";

        // PostgreSQLへ接続
        con = DriverManager.getConnection(url, user, password);

        return con;
    }

}
