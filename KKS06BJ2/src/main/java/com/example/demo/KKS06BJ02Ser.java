package com.example.demo;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
/**
 * KKS06BJ02Serクラス
 * @author hikar
 *　
 */
public class KKS06BJ02Ser implements Tasklet {
    private static final Logger logger = LoggerFactory.getLogger(
            KKS06BJ02Ser.class);

    private static final String DELETE_SQL = "DELETE " + "FROM "
            + "t_attendance_info_detail " + "WHERE " + "INPUT_PERIOD <= ? "
            + ";";

    private static final DateTimeFormatter YM_FMT = DateTimeFormatter.ofPattern(
            "yyyyMM");

    @Override
    public RepeatStatus execute(StepContribution contribution,
            ChunkContext chunkContext) throws Exception {
        logger.info("batch起動");

        String baseYM = LocalDate.now().minusMonths(3).format(YM_FMT);
        System.out.println("基準年月 = " + baseYM);

        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = getConnection();

            // SELECT文の実行
            ps = con.prepareStatement(DELETE_SQL);
            ps.setString(1, baseYM);

            int deleted = ps.executeUpdate();
            logger.info("削除件数 = {}", deleted);

            contribution.setExitStatus(ExitStatus.COMPLETED);
            return RepeatStatus.FINISHED;

        } catch (SQLException e) {
            logger.error("削除処理中に例外発生", e);
            writeErrorLog(e);
            contribution.setExitStatus(new ExitStatus("8"));
            return RepeatStatus.FINISHED;

        } finally {
            try {
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
    }

    private void writeErrorLog(Exception e) {

        // ここで「バッチ実行年月」を改めて取得
        String yyyyMM = LocalDate.now().format(YM_FMT);

        // 出力先ディレクトリ
        String outDir = "\\\\wsl.localhost\\Ubuntu\\home\\hikari\\log";

        // ファイル名作成
        String fileName = yyyyMM + "_KKS06BJ02_err.log";

        Path out = Paths.get(outDir, fileName);

        try {
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
                    StandardOpenOption.APPEND
            // StandardOpenOption.TRUNCATE_EXISTING:上書き、以前の行を全て消して書く
            // StandardOpenOption.TRUNCATE_EXISTING
            )) {

                // ファイルへ出力
                // エラー発生時刻
                String timestamp = LocalDateTime.now().format(DateTimeFormatter
                        .ofPattern("yyyy-MM-dd:HH:mm:ss"));

                bw.write(timestamp);
                bw.newLine();

                // スタックトレース
                StringWriter sw = new StringWriter();
                e.printStackTrace(new PrintWriter(sw));
                bw.write(sw.toString());
                bw.newLine();
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
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
