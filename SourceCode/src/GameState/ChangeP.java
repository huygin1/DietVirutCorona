package GameState;

import Entity.PlayerSave;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import GameState.*;
import Handlers.Keys;
import Main.GamePanel;
import Sever.KetNoiMySQL;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.*;

public class ChangeP extends GameState {

    private JFrame mainFrame;
    private JLabel headerLabel;
    private JLabel headerLabel1;
    private JLabel headerLabel2;
    private JLabel headerLabel3;
    private JLabel headerLabel4;

    private JPanel controlPanel;
    private int i;
    private int o;
    private String[] mk = new String[200];
    private int[] IDU = new int[200];
    private String mkc;
    private String mkm;
    private String mkm2;

    private void D1() {
        Connection cn = KetNoiMySQL.getConnect();

        try {

            String s = "select * from Player;";
            Statement sta = cn.createStatement();

            ResultSet rs = sta.executeQuery(s);
            i = 0;
            while (rs.next()) {

                mk[i] = new String(rs.getString(5));
                IDU[i] = new Integer(rs.getInt(1));

                i++;

            }
            o = i;

            //5. dong resources
            sta.close();
            cn.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public int capnhatDB() {
        int r = -1;

        
        Connection cn = KetNoiMySQL.getConnect();

        try {
            String sql;
            if (KetNoiMySQL.Csdl == false) {
                sql = "UPDATE `Player` SET `PassWord` = ? WHERE `Player`.`ID` = ?;";
            } else {
                sql = "UPDATE Player SET PassWord = ? WHERE Player.ID = ?;";
            }

            PreparedStatement pst = cn.prepareStatement(sql);

            pst.setString(1,MKmoi);

            pst.setInt(2,PlayerSave.IDU);

            r = pst.executeUpdate();

            pst.close();
            cn.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return r;
    }

    private void prepareGUI() {

        mainFrame = new JFrame();
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - mainFrame.getWidth()) / 2 - 250);
        int y = (int) ((dimension.getHeight() - mainFrame.getHeight()) / 2 - 300);
        mainFrame.setSize(500, 600);
        mainFrame.setLocation(x, y);

        mainFrame.setUndecorated(true);

        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {

                System.exit(0);
            }
        });

        headerLabel = new JLabel("", JLabel.CENTER);
        headerLabel1 = new JLabel("", JLabel.CENTER);
        headerLabel2 = new JLabel("", JLabel.CENTER);
        headerLabel3 = new JLabel("", JLabel.CENTER);
        headerLabel4 = new JLabel("", JLabel.CENTER);

        controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(11, 1));

        mainFrame.add(controlPanel);

        mainFrame.setVisible(true);

        mainFrame.setAlwaysOnTop(true);

    }
    private String MKcNhap;
    private String MKmoi;
    private String MKmoi2;
    private JPanel hello;
    private int statuscheck;

    private void showTextFieldDemo() {
        headerLabel.setPreferredSize(new Dimension(200, 300));
        headerLabel.setText("ĐỔI MẬT KHẨU");

        headerLabel1.setText("");
        headerLabel2.setText("");
        headerLabel3.setText("");
        headerLabel4.setText("");

        JLabel namelabel = new JLabel("Mật khẩu cũ: ", JLabel.LEFT);
        JLabel passwordLabel = new JLabel("Mật khẩu mới: ", JLabel.LEFT);
        JLabel passwordLabel2 = new JLabel("Nhập lại mật khẩu: ", JLabel.LEFT);
        final JPasswordField passwordTextOld = new JPasswordField(6);

        final JPasswordField passwordText = new JPasswordField(6);
        final JPasswordField passwordText2 = new JPasswordField(6);
        JButton loginButton = new JButton("Đổi mật khẩu");

        JButton thoat = new JButton("Quay lại");
        loginButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                D1();
                MKcNhap = new String(passwordTextOld.getPassword());
                MKmoi = new String(passwordText.getPassword());
                MKmoi2 = new String(passwordText2.getPassword());
                if (MKmoi2.isEmpty() || MKmoi.isEmpty()) {
                    JOptionPane.showMessageDialog(mainFrame, "Không được để trống!");

                } else {

                    for (int j = 0; j < o; j++) {
                        if (PlayerSave.IDU == IDU[j]) {
                            mkc = mk[j];
                            break;
                        }

                    }

                    if (mkc.equals(MKcNhap)) {
                        if (MKmoi.equals(MKmoi2)) {
                            capnhatDB();
                             JOptionPane.showMessageDialog(mainFrame, "Đổi mật khẩu thành công!");
                        } else {
                            JOptionPane.showMessageDialog(mainFrame, "Nhập lại mật khẩu không trùng khớp!");
                        }
                    } else {
                        JOptionPane.showMessageDialog(mainFrame, "Mật khẩu cũ không đúng!");
                    }

                }

            }

        });

        thoat.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                mainFrame.setVisible(false);
                gsm.setState(GameStateManager.IF);
            }

        });
        controlPanel.add(headerLabel);
        controlPanel.add(headerLabel1);
        controlPanel.add(namelabel);

        controlPanel.add(passwordTextOld);

        controlPanel.add(passwordLabel);

        controlPanel.add(passwordText);
        controlPanel.add(passwordLabel2);
        controlPanel.add(passwordText2);
        hello = new JPanel();
        hello.setLayout(new GridLayout(1, 3));

        controlPanel.add(headerLabel3);
        controlPanel.add(headerLabel4);
        controlPanel.add(hello);
        hello.add(loginButton);
//        controlPanel.add(loginButton);

        hello.add(thoat);
        mainFrame.setVisible(true);

    }

    public ChangeP(GameStateManager gsm) {

        super(gsm);
          prepareGUI();
            showTextFieldDemo();

    }

    public void init() {
    }

    public void update() {
        handleInput();
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.decode("#eeeeee"));
        g.fillRect(0, 0, (int) GamePanel.WIDTH, (int) GamePanel.HEIGHT);

    }

    public void handleInput() {
        if (Keys.isPressed(Keys.ESCAPE)) {
            gsm.setPaused(false);
        }

    }

}
