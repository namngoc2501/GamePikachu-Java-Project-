/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamepikachudoanmonjava;

import static com.sun.xml.internal.fastinfoset.alphabet.BuiltInRestrictedAlphabets.table;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.ScrollPane;
import java.awt.Scrollbar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.layout.BackgroundImage;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.ComponentUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import org.omg.CORBA.ORB;

/**
 *
 * @author Acer
 */
public class MainFrame extends JFrame implements ActionListener, Runnable {

    private int row = 10;
    private int col = 10;
    private int width = 1200;
    private int height = 700;

    private ColtrollerGame graphicsPanel;
    private JPanel mainPanel;
    private int MAX_TIME = 200;
    public int time = MAX_TIME;
    public JLabel lbScore;
    public JLabel lbDanhSachDiem;
    private JProgressBar progressTime;
    private JButton btnNewGame;
    private JButton btnDungGame;
    private JButton btnTiepTucGame;
    private JButton btnThoatGame;
    private JButton btnGameMoin;
    private JTable tbDiem;
    private boolean pause = true;
    private boolean resume = false;

    ///
    private Vector vctHeader = new Vector();

    private Vector vctData = new Vector();

    public MainFrame() throws IOException {
        add(mainPanel = createMainPanel());
        setTitle("????? ??n Nh??m Nam, ????ng");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(width, height);
        setLocationRelativeTo(null);
        btnTiepTucGame.setEnabled(false);
        btnDungGame.setEnabled(false);
        setVisible(true);

    }

    public void newGame() {
        time = MAX_TIME;
        graphicsPanel.removeAll();
        mainPanel.add(createGraphicsPanel(), BorderLayout.CENTER);
        mainPanel.validate();
        mainPanel.setVisible(true);
        lbScore.setText("0");

    }

    private JButton createButton(String buttonName) {
        JButton btn = new JButton(buttonName);
        btn.addActionListener(this);
        return btn;
    }

    private JPanel createMainPanel() throws IOException {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(createGraphicsPanel(), BorderLayout.CENTER);
        panel.add(createControlPanel(), BorderLayout.EAST);
        return panel;
    }

    private JPanel createGraphicsPanel() {
        graphicsPanel = new ColtrollerGame(this,row, col);
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.gray);
        panel.add(graphicsPanel);
        return panel;
    }

    public boolean isResume() {
        return resume;
    }

    public void setResume(boolean resume) {
        this.resume = resume;
    }

    public boolean isPause() {
        return pause;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }

    public void showDialogNewGame(String message, String title, int t) {
        pause = true;
        resume = false;
        int select = JOptionPane.showOptionDialog(null, message, title,
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
        if (select == 0) {
            pause = false;
            newGame();
            btnDungGame.setEnabled(true);
            btnTiepTucGame.setEnabled(true);
        } else {
            if (t == 1) {
                graphicsPanel.ghiFile("D:\\TaiLieuIT\\Hoc ki 3\\Java\\MyJava\\DoAnMonJava_TranNgocNam_NguyenCongDang_SangThu4\\GamePikachu\\DSD.txt");
                System.exit(0);
            } else {
                resume = false;
            }
        }
    }

    public void showDialogThoatGame(String message, String title) {
        pause = true;
        resume = false;
        int select = JOptionPane.showOptionDialog(null, message, title,
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                null, null);
        if (select == 0) {
            setVisible(false);
        }
    }

    private JPanel createControlPanel() {
        //t???o JLabel lblScore v???i gi?? tr??? ban ?????u l?? 0
        lbScore = new JLabel("0");
        progressTime = new JProgressBar(0, 100);
        progressTime.setValue(100);

        //t???o Panel ch???a Score v?? Time
        JPanel panelLeft = new JPanel(new GridLayout(2, 1, 5, 5));
        panelLeft.add(new JLabel("??i???m:"));
        panelLeft.add(new JLabel("Th???i Gian:"));

        JPanel panelCenter = new JPanel(new GridLayout(2, 1, 5, 5));
        panelCenter.add(lbScore);
        panelCenter.add(progressTime);

        JPanel panelScoreAndTime = new JPanel(new BorderLayout(5, 0));
        panelScoreAndTime.add(panelLeft, BorderLayout.WEST);
        panelScoreAndTime.add(panelCenter, BorderLayout.CENTER);

        // t???o Panel m???i ch???a panelScoreAndTime v?? n??t New Game
        JPanel panelControl = new JPanel(new BorderLayout(30, 30));
        panelControl.setBorder(new EmptyBorder(10, 3, 5, 3));
        panelControl.add(panelScoreAndTime, BorderLayout.PAGE_START);
        panelControl.add(btnNewGame = createButton("B???t ?????u Ch??i"),
                BorderLayout.LINE_START);
        panelControl.add(btnDungGame = createButton("T???m D???ng"),
                BorderLayout.CENTER);
        panelControl.add(btnTiepTucGame = createButton("Ti???p T???c"),
                BorderLayout.EAST);
        panelControl.add(btnThoatGame = createButton("Tho??t"),
                BorderLayout.PAGE_END);

        JPanel panel1 = new JPanel(new BorderLayout(30, 30));
        panel1.setBorder(new EmptyBorder(10, 3, 5, 3));
        panel1.add(lbDanhSachDiem = new JLabel("Danh S??ch ??i???m!!"), BorderLayout.PAGE_START);
        panel1.add(tbDiem = new JTable(), BorderLayout.CENTER);
        JScrollPane scroll = new JScrollPane(tbDiem);
        panel1.add(scroll);
        vctHeader.add("Th??? H???ng");
        vctHeader.add("S??? ??i???m");
        vctHeader.add("Th???i Gian");

        int STT = ColtrollerGame.getSTT("D:\\TaiLieuIT\\Hoc ki 3\\Java\\MyJava\\DoAnMonJava_TranNgocNam_NguyenCongDang_SangThu4\\GamePikachu\\DSD.txt");
        String[][] S = ColtrollerGame.docFile("D:\\TaiLieuIT\\Hoc ki 3\\Java\\MyJava\\DoAnMonJava_TranNgocNam_NguyenCongDang_SangThu4\\GamePikachu\\DSD.txt");
        for (int i = 0; i < STT; i++) {
            Vector vctRow = new Vector();
            for (int j = 0; j < 3; j++) {
                vctRow.add(S[i][j] + "");
            }
            vctData.add(vctRow);
        }
//        Vector vctRow1 = new Vector();
//        vctRow1.add("1");
//        vctRow1.add("200");
//        vctRow1.add(java.time.LocalDate.now().toString());
//        vctData.add(vctRow1);
//        Vector vctRow2 = new Vector();
//        vctRow2.add("1");
//        vctRow2.add("200");
//        vctRow2.add(java.time.LocalDate.now().toString());
//        vctData.add(vctRow2);

        tbDiem.setModel(new DefaultTableModel(vctData, vctHeader));

        // Set BorderLayout ????? panelControl xu???t hi???n ??? ?????u trang
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new TitledBorder("Pokemon"));
        panel.add(panelControl, BorderLayout.PAGE_START);
        panel.add(panel1);
        panel.setBackground(Color.ORANGE);
        return panel;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            progressTime.setValue((int) ((double) time / MAX_TIME * 100));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnNewGame) {
            showDialogNewGame("B???n C?? Mu??n B???t ?????u Ch??i ?", "Warning", 0);
        }
        if (e.getSource() == btnDungGame) {
            Component[] comp = graphicsPanel.getComponents();
            for (int i = 0; i < comp.length; i++) {
                if (comp[i] instanceof JButton) {
                    ((JButton) comp[i]).setEnabled(false);
                }
            }
            pause = true;
            resume = false;
            btnNewGame.setEnabled(false);
        }
        if (e.getSource() == btnTiepTucGame) {
            Component[] comp = graphicsPanel.getComponents();
            for (int i = 0; i < comp.length; i++) {
                if (comp[i] instanceof JButton) {
                    ((JButton)comp[i]).setEnabled(true);
                }
            }
            pause = false;
            resume = true;
            btnNewGame.setEnabled(true);
        }
        if (e.getSource() == btnThoatGame) {
            showDialogThoatGame("B???n C?? Mu???n Tho??t Tr?? Ch??i ", "Warning");
        }
    }
}
