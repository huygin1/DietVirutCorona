package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import Main.GamePanel;
import javax.imageio.ImageIO;
import amThanh.amThanh;

import Entity.PlayerSave;
import Handlers.Keys;
import Main.GamePanel;
import Sever.MyLIB;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Button;
import java.awt.Frame;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import Main.GamePanel;
import Sever.KetNoiMySQL;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.TextField;
import java.awt.Toolkit;
import java.sql.PreparedStatement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class MenuState extends GameState {

    int IDplayer;
    String[] NameIndex = new String[200];
    String[] NameIndex1 = new String[200];
    int[] Status = new int[200];
    int[] IDIndex = new int[200];
    int p;
    int q;
    String Namedk;

    private JFrame mainFrame;
    private JLabel headerLabel;
    private JLabel headerLabel1;
    private JLabel headerLabel2;
    private JLabel headerLabel3;
    private JLabel headerLabel4;

    private JPanel controlPanel;
    private amThanh at = new amThanh();
    public boolean checkLogin;
    private BufferedImage head;
    private BufferedImage huongdan;
    private int currentChoice = 0;
    private String[] options = {
        "Start",
        "2", "3", "4", "5", "6"
    };

    GamePanel gp = new GamePanel();
    private Color titleColor;
    private Font titleFont;

    private Font font;
    private Font font2;
    String fontFileName = "/Fonts/HuyFont.otf";

    InputStream fonthuy = this.getClass().getResourceAsStream(fontFileName);
    private Font ttfBase;
    private Font HuyFont;
    private Font TieuDe;
    private String RName;
    private String REmail;
    private String RPW2;
    private String RUserName;
    private String RPassWord;
    boolean KTName = true;
    boolean KTEmail = true;
    boolean KTUserName = true;
    boolean XacnhanUsername = false;
    boolean XacnhanName = false;
    boolean XacnhanEmail = false;

    boolean XacnhanPassWord = false;

    public MenuState(GameStateManager gsm) {

        super(gsm);

        if (gsm.checkloginadmin == true) {
            checkLogin = true;
        } else {
            prepareGUI();
            showTextFieldDemo();

        }

        try {

            // load floating head
            head = ImageIO.read(
                    getClass().getResourceAsStream("/HUD/hub.png")
            ).getSubimage(0, 52, 60, 54);

            // titles and fonts
            titleColor = Color.PINK;
            titleFont = new Font("Times New Roman", Font.PLAIN, 28);
            font = new Font("Arial", Font.PLAIN, 14);
            font2 = new Font("Arial", Font.PLAIN, 110);

            ttfBase = Font.createFont(Font.TRUETYPE_FONT, fonthuy);
            HuyFont = ttfBase.deriveFont(Font.PLAIN, 40);
            TieuDe = ttfBase.deriveFont(Font.PLAIN, 80);
            huongdan = ImageIO.read(
                    getClass().getResourceAsStream("/HUD/menu.png")
            );
            // load sound fx

//			JukeBox.load("/SFX/menuoption.mp3", "menuoption");
//			JukeBox.load("/SFX/menuselect.mp3", "menuselect");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private String[] ttk = new String[200];
    private String[] mk = new String[200];
    private int[] IDU = new int[200];
    private int i;
    private int o;

    public void init() {

    }

    public void update() {

        if (checkLogin == true) {
            handleInput();
        } else {
            blockinput();
        }

    }

    private void D1() {
        Connection cn = KetNoiMySQL.getConnect();

        try {

            String s = "select * from Player;";
            Statement sta = cn.createStatement();

            ResultSet rs = sta.executeQuery(s);
            i = 0;
            while (rs.next()) {

                ttk[i] = new String(rs.getString(4));
                mk[i] = new String(rs.getString(5));
                IDU[i] = new Integer(rs.getInt(1));
                Status[i] = new Integer(rs.getInt(6));
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
    private String mkLogin;
    private String tkkLogin;
    private JPanel hello;
    private int statuscheck;

    private void showTextFieldDemo() {
        headerLabel.setPreferredSize(new Dimension(200, 300));
        headerLabel.setText("ĐĂNG NHẬP");

        headerLabel1.setText("");
        headerLabel2.setText("");
        headerLabel3.setText("");
        headerLabel4.setText("");

        JLabel namelabel = new JLabel("Tên đăng nhập: ", JLabel.LEFT);
        JLabel passwordLabel = new JLabel("Mật khẩu: ", JLabel.LEFT);
        final JTextField userText = new JTextField(6);
        userText.setText("admin");
        final JPasswordField passwordText = new JPasswordField(6);
        JButton loginButton = new JButton("Đăng nhập");
        JButton registerButton = new JButton("Đăng ki");
        JButton thoat = new JButton("Thoát");
        loginButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                D1();
                tkkLogin = userText.getText();
                mkLogin = new String(passwordText.getPassword());
                if (tkkLogin.isEmpty() || mkLogin.isEmpty()) {
                    JOptionPane.showMessageDialog(mainFrame, "Tên tài khoản hoặc mật khẩu không được để trống!");

                } else {
                    boolean check = false;
                    for (int j = 0; j < o; j++) {
                        if (mk[j].equals(mkLogin) && ttk[j].toLowerCase().equals(tkkLogin.toLowerCase())) {
                            if (Status[j] == 0) {

                                PlayerSave.IDU = IDU[j];
                                gsm.checkloginadmin = true;
                                checkLogin = true;
                                mainFrame.setVisible(false);
                                check = true;
                                break;
                            } else {
                                statuscheck = 1;

                            }

                        }

                    }
                    if (check == false) {
                        if (statuscheck == 1) {
                            JOptionPane.showMessageDialog(mainFrame, "Tài khoản bị của bạn đã bị khóa!!");
                        } else {
                            JOptionPane.showMessageDialog(mainFrame, "sai tên tài khoản hoặc mật khẩu!!");
                        }

                    }

                }

            }

        });
        registerButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                register();
                mainFrame.setVisible(false);
            }

        });
        thoat.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }

        });
        controlPanel.add(headerLabel);
        controlPanel.add(headerLabel1);
        controlPanel.add(namelabel);

        controlPanel.add(userText);

        controlPanel.add(passwordLabel);

        controlPanel.add(passwordText);

        hello = new JPanel();
        hello.setLayout(new GridLayout(1, 3));

        controlPanel.add(headerLabel3);
        controlPanel.add(headerLabel4);
        controlPanel.add(hello);
        hello.add(loginButton);
//        controlPanel.add(loginButton);

        hello.add(registerButton);
        hello.add(thoat);
        mainFrame.setVisible(true);

    }
    JTextField tname;
    JTextField email;
    JTextField address;
    JPasswordField pass;
    JPasswordField pass2;

    public void register() {
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2 - 200);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2 - 250);
        frame.setSize(400, 500);
        frame.setLocation(x, y);

        frame.setTitle("Đăng kí");

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });
        JLabel lablename = new JLabel("Nhập họ và tên");
        tname = new JTextField(30);
        tname.setColumns(30);
        tname.setPreferredSize(new Dimension(30, 50));

        JLabel lableemail = new JLabel("Nhập địa chỉ email");
        email = new JTextField(30);
        email.setColumns(30);
        email.setPreferredSize(new Dimension(30, 50));
        JLabel lableaddress = new JLabel("Nhập tên đăng nhập");
        address = new JTextField(30);
        address.setColumns(30);

        address.setPreferredSize(new Dimension(30, 50));
        address.setFont(Font.getFont(Font.SERIF));
        JLabel lablepass = new JLabel("Nhập mật khẩu");
        pass = new JPasswordField(30);
        pass.setColumns(30);
        pass.setPreferredSize(new Dimension(30, 50));
        JLabel lablepass2 = new JLabel("Nhập lại mật khẩu");
        pass2 = new JPasswordField(30);
        pass2.setColumns(30);
        pass2.setPreferredSize(new Dimension(30, 50));

        JButton login = new JButton();
        JButton create = new JButton();
        login.setPreferredSize(new Dimension(150, 50));
        login.setText("Quay lại");
        create.setPreferredSize(new Dimension(150, 50));
        create.setText("Tạo Tài khoản");

        panel.add(lablename);
        panel.add(tname);
        panel.add(lableemail);
        panel.add(email);
        panel.add(lableaddress);

        panel.add(address);
        panel.add(lablepass);
        panel.add(pass);
        panel.add(lablepass2);
        panel.add(pass2);
        panel.add(create);
        panel.add(login);

        frame.setAlwaysOnTop(true);
        frame.add(panel);
        frame.setUndecorated(true);
        frame.setVisible(true);
        login.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                mainFrame.setVisible(true);
                frame.setVisible(false);
            }

        });
        create.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                inputRegister();
                if (KTDK == true) {
                    String[] options = new String[2];
                    options[0] = new String("Đồng ý");
                    options[1] = new String("Không Đồng ý");
                    int output = JOptionPane.showOptionDialog(frame.getContentPane(), "Đăng kí thành công\nBạn có muốn quay trở lại màn hình đăng nhập", "Xác nhận", 0, JOptionPane.INFORMATION_MESSAGE, null, options, null);

                    if (output == JOptionPane.YES_OPTION) {

                        mainFrame.setVisible(true);
                        frame.setVisible(false);
                    }
                } else {
                    KTDK = false;

                }
                KTDK = false;

            }

        });
    }

    public void inputRegister() {
        RName = tname.getText();

        REmail = email.getText();
        RUserName = address.getText();
        RPassWord = new String(pass.getPassword());
        RPW2 = new String(pass2.getPassword());
        XacnhanPassWord = false;
        String kitudacbiet[] = new String[32];
        for (char c = 33; c <= 64; c++) {
            kitudacbiet[c - 33] = String.valueOf(c);
        }

        for (String s : kitudacbiet) {
            if (RName.contains(s)) {
                KTName = false;
            } else {

            }
        }
        String kitudacbiet2[] = new String[6];
        for (char c = 91; c <= 96; c++) {
            kitudacbiet2[c - 91] = String.valueOf(c);
        }

        for (String a : kitudacbiet2) {
            if (RName.contains(a)) {
                KTName = false;
            } else {

            }
        }
        for (String a : kitudacbiet2) {
            if (REmail.contains(a)) {
                KTEmail = false;
            }
            if (RUserName.contains(a)) {
                KTUserName = false;
            }
        }
        String kitudacbiet3[] = new String[7];
        for (char c = 123; c <= 129; c++) {
            kitudacbiet3[c - 123] = String.valueOf(c);
        }

        for (String a : kitudacbiet3) {
            if (RName.contains(a)) {
                KTName = false;
            } else {

            }
        }
        String kitudacbietemail[] = new String[13];
        for (char c = 33; c <= 45; c++) {
            kitudacbietemail[c - 33] = String.valueOf(c);
        }

        for (String a : kitudacbietemail) {
            if (REmail.contains(a)) {
                KTEmail = false;
            }

        }
        java.util.regex.Pattern p = Pattern.compile("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");

        java.util.regex.Matcher m = null;

        m = p.matcher(REmail);

        if (m.matches()) {

        } else {
            KTEmail = false;

        }
        String kitudacbietunl[] = new String[15];
        for (char c = 33; c <= 47; c++) {
            kitudacbietunl[c - 33] = String.valueOf(c);
        }

        for (String a : kitudacbietunl) {
            if (RUserName.contains(a)) {
                KTUserName = false;
            }

        }

        if (KTName == false) {
            JOptionPane.showMessageDialog(mainFrame, "Tên không được chứa kí tự đặc biệt");
            KTName = true;
            XacnhanName = false;
        } else {
            if (RName.endsWith(" ")) {
                JOptionPane.showMessageDialog(mainFrame, "Tên không được kết thúc bằng khoảng trắng");
                XacnhanName = false;
            } else {
                XacnhanName = true;
                if (RName.startsWith(" ")) {
                    JOptionPane.showMessageDialog(mainFrame, "Tên không được bắt đầu bằng khoảng trắng");
                    XacnhanName = false;
                } else {
                    XacnhanName = true;
                    if (RName.length() > 59) {
                        JOptionPane.showMessageDialog(mainFrame, "Tên không vượt quá 60 kí tự");
                        XacnhanName = false;
                    } else {
                        XacnhanName = true;
                        if (RName.contains(" ")) {
                            XacnhanName = true;
                            if (RName.length() < 5) {
                                JOptionPane.showMessageDialog(mainFrame, "Tên không ít hơn 5 kí tự");
                                XacnhanName = false;
                            } else {
                                XacnhanName = true;
                            }
                        } else {
                            JOptionPane.showMessageDialog(mainFrame, "Tên không hợp lệ");
                            XacnhanName = false;
                        }
                    }
                }
            }

        }

        String kitudacbietemail1[] = new String[6];
        for (char c = 58; c <= 63; c++) {
            kitudacbietemail1[c - 58] = String.valueOf(c);
        }

        for (String s : kitudacbietemail1) {
            if (REmail.contains(s)) {
                KTEmail = false;
            }
            if (RUserName.contains(s)) {
                KTUserName = false;
            }
        }
        String kitudacbietemail2[] = new String[388];
        for (char c = 123; c <= 510; c++) {
            kitudacbietemail2[c - 123] = String.valueOf(c);
        }

        for (String s : kitudacbietemail2) {
            if (REmail.contains(s)) {
                KTEmail = false;
            }
            if (RUserName.contains(s)) {
                KTUserName = false;
            }
        }
        if (KTEmail == false) {
            JOptionPane.showMessageDialog(mainFrame, "Email không hợp lệ");
            KTEmail = true;
            XacnhanEmail = false;
        } else {
            if (REmail.endsWith(" ")) {
                JOptionPane.showMessageDialog(mainFrame, "Email Không được kết thúc bằng khoảng trắng");
                XacnhanEmail = false;
            } else {
                XacnhanEmail = true;
                if (REmail.startsWith(" ")) {
                    JOptionPane.showMessageDialog(mainFrame, "Email Không được bắt đầu bằng khoảng trắng");
                    XacnhanEmail = false;
                } else {
                    XacnhanEmail = true;
                    if (REmail.length() > 59) {
                        JOptionPane.showMessageDialog(mainFrame, "Email không vượt quá 60 kí tự");
                        XacnhanEmail = false;
                    } else {
                        XacnhanEmail = true;
                        if (REmail.contains("@") && REmail.contains(".")) {

                            if (REmail.contains(".com") || REmail.contains(".vn")) {
                                XacnhanEmail = true;
                            } else {
                                JOptionPane.showMessageDialog(mainFrame, "Email phải kết thúc bằng .com hoặc .vn!!");
                                XacnhanEmail = false;
                            }

                        } else {
                            JOptionPane.showMessageDialog(mainFrame, "Email không hợp lệ!!");
                            XacnhanEmail = false;
                        }
                    }
                }
            }

        }

        if (KTUserName == false) {
            JOptionPane.showMessageDialog(mainFrame, "Tên tài khoản không được chứa kí tự đặc biệt!!");
            KTUserName = true;
            XacnhanUsername = false;
        } else {
            if (RUserName.endsWith(" ")) {
                JOptionPane.showMessageDialog(mainFrame, "Tên tài khoản Không được kết thúc bằng khoảng trắng!!");
                XacnhanUsername = false;
            } else {
                XacnhanUsername = true;
                if (RUserName.startsWith(" ")) {
                    JOptionPane.showMessageDialog(mainFrame, "Tên tài khoản Không được bắt đầu bằng khoảng trắng!!");
                    XacnhanUsername = false;
                } else {
                    XacnhanUsername = true;
                    if (RUserName.length() > 59) {
                        JOptionPane.showMessageDialog(mainFrame, "Tên tài khoản không vượt quá 60 kí tự!!");
                        XacnhanUsername = false;
                    } else {
                        XacnhanUsername = true;
                        if (RUserName.contains(" ")) {
                            JOptionPane.showMessageDialog(mainFrame, "Tên tài khoản không được để khoảng trắng!!");
                            XacnhanUsername = false;
                        } else {
                            XacnhanUsername = true;
                            if (RUserName.length() < 5) {
                                JOptionPane.showMessageDialog(mainFrame, "Tên tài khoản không được ít hơn 5 kí tự!!");
                                XacnhanUsername = false;
                            } else {
                                XacnhanUsername = true;
                                D1();
                                for (int j = 0; j < o; j++) {
                                    if (ttk[j].toLowerCase().equals(RUserName.toLowerCase())) {

                                        XacnhanUsername = false;
                                        JOptionPane.showMessageDialog(mainFrame, "Tên tài khoản này đã được đăng kí!!\nVui lòng chọn tên tài khoản khác");
                                        break;
                                    } else {
                                        XacnhanUsername = true;
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }
        if (RPW2.equals(RPassWord)) {

            XacnhanPassWord = true;
        } else {
            XacnhanPassWord = false;
            JOptionPane.showMessageDialog(mainFrame, "Nhập lại mật khẩu không đúng!!");
        }
        if (XacnhanName == true && XacnhanEmail == true && XacnhanPassWord == true && XacnhanUsername == true) {
            insertDB();
        }
    }

    public int ISPlayer() {
        int s = -1;
        Connection cn = KetNoiMySQL.getConnect();
        try {

            String sql = "INSERT INTO `Player` (`ID`, `Name`, `Email`, `UserName`, `PassWord`, `Status`) VALUES (NULL, ?, ?, ?, ?, ?);";
            PreparedStatement pst = cn.prepareStatement(sql);

            pst.setString(1, RName);
            pst.setString(2, REmail);
            pst.setString(3, RUserName);
            pst.setString(4, RPassWord);
            pst.setInt(5, 0);
            s = pst.executeUpdate();
            pst.close();
            cn.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        Namedk = RUserName;
        D2();
        return s;

    }

    public int ISPlayer2() {
        int s = -1;
        Connection cn = KetNoiMySQL.getConnect();
        try {

            String sql = "INSERT INTO Player ( Name, Email, UserName, PassWord, Status) VALUES ( ?, ?, ?, ?, ?);";
            PreparedStatement pst = cn.prepareStatement(sql);

            pst.setString(1, RName);
            pst.setString(2, REmail);
            pst.setString(3, RUserName);
            pst.setString(4, RPassWord);
            pst.setInt(5, 0);
            s = pst.executeUpdate();
            pst.close();
            cn.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        Namedk = RUserName;
        D2();
        return s;

    }

    private void D2() {
        Connection cn = KetNoiMySQL.getConnect();

        try {

            String s = "select * from Player;";
            Statement sta = cn.createStatement();

            ResultSet rs = sta.executeQuery(s);
            p = 0;
            while (rs.next()) {
                NameIndex1[p] = new String(rs.getString(2));
                NameIndex[p] = new String(rs.getString(4));
                IDIndex[p] = new Integer(rs.getInt(1));

                p++;

            }
            q = p;

            //5. dong resources
            sta.close();
            cn.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        for (int j = 0; j < q; j++) {
            if (NameIndex[j].equals(Namedk)) {
                IDplayer = IDIndex[j];

                break;
            }

        }

        if (KetNoiMySQL.Csdl == false) {
            isCharacter2();
        } else {
            isCharacter();
        }

    }
    boolean KTDK = false;

    public int isCharacter2() {
        int ac = -1;
        System.out.println(IDplayer);
        Connection cn = KetNoiMySQL.getConnect();
        try {
            String sql = "INSERT INTO `Character` (`ID`,`PlayerID`,`Level` ,`State`, `Heart`, `Live`, `Exp`, `Total`, `Point`, `Time`) VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, IDplayer);
            pst.setInt(2, 1);
            pst.setInt(3, 1);
            pst.setInt(4, 5);
            pst.setInt(5, 3);
            pst.setInt(6, 0);
            pst.setInt(7, 0);
            pst.setInt(8, 0);
            pst.setInt(9, 0);

            ac = pst.executeUpdate();
            pst.close();
            cn.close();
            KTDK = true;

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        isPoint2();
        return ac;
    }

    public int isPoint2() {
        int ac = -1;
        System.out.println(IDplayer);
        Connection cn = KetNoiMySQL.getConnect();
        try {
            String sql = "INSERT INTO `HighScore` (`ID`,`PlayerID`,`HighPoint`  ) VALUES (NULL, ?, ?);";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, IDplayer);
            pst.setInt(2, 0);

            ac = pst.executeUpdate();
            pst.close();
            cn.close();
            KTDK = true;

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return ac;
    }

    public int isPoint() {
        int ac = -1;
        System.out.println(IDplayer);
        Connection cn = KetNoiMySQL.getConnect();
        try {
            String sql = "INSERT INTO HighScore (PlayerID,HighPoint) VALUES ( ?, ?);";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, IDplayer);
            pst.setInt(2, 0);

            ac = pst.executeUpdate();
            pst.close();
            cn.close();
            KTDK = true;

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return ac;
    }

    public int isCharacter() {
        int ac = -1;
        System.out.println(IDplayer);
        Connection cn = KetNoiMySQL.getConnect();
        try {
            String sql = "INSERT INTO Character (PlayerID,Level ,State, Heart, Live, Exp, Total, Point, Time) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, IDplayer);
            pst.setInt(2, 1);
            pst.setInt(3, 1);
            pst.setInt(4, 5);
            pst.setInt(5, 3);
            pst.setInt(6, 0);
            pst.setInt(7, 0);
            pst.setInt(8, 0);
            pst.setInt(9, 0);

            ac = pst.executeUpdate();
            pst.close();
            cn.close();
            KTDK = true;

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        isPoint();
        return ac;
    }

    public void insertDB() {
        if (KetNoiMySQL.Csdl == false) {
            ISPlayer();
        } else {
            ISPlayer2();
        }

    }

    public void draw(Graphics2D g) {
        if (checkLogin == true) {
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, (int) GamePanel.WIDTH, (int) GamePanel.HEIGHT);
            g.drawImage(huongdan, ((int) GamePanel.WIDTH - 400), (int) GamePanel.HEIGHT - 250, null);
            // draw title

            g.setColor(Color.red);
            g.setFont(TieuDe);
            g.drawString("DIỆT TRỪ VIRUT CORONA", ((int) GamePanel.WIDTH - ("DIỆT TRỪ VIRUT CORONA".length() * 42)) / 2, 160);

            // draw menu options
            g.setFont(HuyFont);

            g.setColor(Color.ORANGE);
            if (currentChoice == 0) {
                g.drawImage(head, (int) GamePanel.WIDTH / 2 - 300, (int) GamePanel.HEIGHT / 2 - 150, null);
                g.setColor(Color.GRAY);
                g.drawString("Bắt đầu", ((int) GamePanel.WIDTH - ("Bắt đầu".length() * 25)) / 2, (int) GamePanel.HEIGHT / 2 - 100);
                g.setColor(Color.ORANGE);
                g.drawString("Hướng dẫn", ((int) GamePanel.WIDTH - ("Hướng dẫn".length() * 25)) / 2, (int) GamePanel.HEIGHT / 2 - 50);
                g.drawString("Thử nghiệm", ((int) GamePanel.WIDTH - ("Thử nghiệm".length() * 25)) / 2, (int) GamePanel.HEIGHT / 2);
                g.drawString("Thông tin nhân vật", ((int) GamePanel.WIDTH - ("Thông tin nhân vật".length() * 20)) / 2, (int) GamePanel.HEIGHT / 2 + 50);
                g.drawString("Bảng điểm", ((int) GamePanel.WIDTH - ("Bảng điểm".length() * 25)) / 2, (int) GamePanel.HEIGHT / 2 + 100);
                g.drawString("Log out", ((int) GamePanel.WIDTH - ("Thoát".length() * 34)) / 2, (int) GamePanel.HEIGHT / 2 + 150);
            } else if (currentChoice == 1) {
                g.drawImage(head, (int) GamePanel.WIDTH / 2 - 300, (int) GamePanel.HEIGHT / 2 - 100, null);
                g.setColor(Color.ORANGE);
                g.drawString("Bắt đầu", ((int) GamePanel.WIDTH - ("Bắt đầu".length() * 25)) / 2, (int) GamePanel.HEIGHT / 2 - 100);
                g.setColor(Color.GRAY);
                g.drawString("Hướng dẫn", ((int) GamePanel.WIDTH - ("Hướng dẫn".length() * 25)) / 2, (int) GamePanel.HEIGHT / 2 - 50);
                g.setColor(Color.ORANGE);
                g.drawString("Thử nghiệm", ((int) GamePanel.WIDTH - ("Thử nghiệm".length() * 25)) / 2, (int) GamePanel.HEIGHT / 2);
                g.drawString("Thông tin nhân vật", ((int) GamePanel.WIDTH - ("Thông tin nhân vật".length() * 20)) / 2, (int) GamePanel.HEIGHT / 2 + 50);
                g.drawString("Bảng điểm", ((int) GamePanel.WIDTH - ("Bảng điểm".length() * 25)) / 2, (int) GamePanel.HEIGHT / 2 + 100);

                g.drawString("Log out", ((int) GamePanel.WIDTH - ("Thoát".length() * 34)) / 2, (int) GamePanel.HEIGHT / 2 + 150);
            } else if (currentChoice == 2) {
                g.drawImage(head, (int) GamePanel.WIDTH / 2 - 300, (int) GamePanel.HEIGHT / 2 - 50, null);
                g.setColor(Color.ORANGE);
                g.drawString("Bắt đầu", ((int) GamePanel.WIDTH - ("Bắt đầu".length() * 25)) / 2, (int) GamePanel.HEIGHT / 2 - 100);

                g.drawString("Hướng dẫn", ((int) GamePanel.WIDTH - ("Hướng dẫn".length() * 25)) / 2, (int) GamePanel.HEIGHT / 2 - 50);

                g.setColor(Color.GRAY);
                g.drawString("Thử nghiệm", ((int) GamePanel.WIDTH - ("Thử nghiệm".length() * 25)) / 2, (int) GamePanel.HEIGHT / 2);
                g.setColor(Color.ORANGE);
                g.drawString("Thông tin nhân vật", ((int) GamePanel.WIDTH - ("Thông tin nhân vật".length() * 20)) / 2, (int) GamePanel.HEIGHT / 2 + 50);
                g.drawString("Bảng điểm", ((int) GamePanel.WIDTH - ("Bảng điểm".length() * 25)) / 2, (int) GamePanel.HEIGHT / 2 + 100);

                g.drawString("Log out", ((int) GamePanel.WIDTH - ("Thoát".length() * 34)) / 2, (int) GamePanel.HEIGHT / 2 + 150);
            } else if (currentChoice == 3) {
                g.drawImage(head, (int) GamePanel.WIDTH / 2 - 300, (int) GamePanel.HEIGHT / 2, null);
                g.setColor(Color.ORANGE);
                g.drawString("Bắt đầu", ((int) GamePanel.WIDTH - ("Bắt đầu".length() * 25)) / 2, (int) GamePanel.HEIGHT / 2 - 100);

                g.drawString("Hướng dẫn", ((int) GamePanel.WIDTH - ("Hướng dẫn".length() * 25)) / 2, (int) GamePanel.HEIGHT / 2 - 50);

                g.drawString("Thử nghiệm", ((int) GamePanel.WIDTH - ("Thử nghiệm".length() * 25)) / 2, (int) GamePanel.HEIGHT / 2);
                g.setColor(Color.GRAY);

                g.drawString("Thông tin nhân vật", ((int) GamePanel.WIDTH - ("Thông tin nhân vật".length() * 20)) / 2, (int) GamePanel.HEIGHT / 2 + 50);
                g.setColor(Color.ORANGE);
                g.drawString("Bảng điểm", ((int) GamePanel.WIDTH - ("Bảng điểm".length() * 25)) / 2, (int) GamePanel.HEIGHT / 2 + 100);

                g.drawString("Log out", ((int) GamePanel.WIDTH - ("Thoát".length() * 34)) / 2, (int) GamePanel.HEIGHT / 2 + 150);
            } else if (currentChoice == 4) {
                g.drawImage(head, (int) GamePanel.WIDTH / 2 - 300, (int) GamePanel.HEIGHT / 2 + 50, null);
                g.setColor(Color.ORANGE);
                g.drawString("Bắt đầu", ((int) GamePanel.WIDTH - ("Bắt đầu".length() * 25)) / 2, (int) GamePanel.HEIGHT / 2 - 100);

                g.drawString("Hướng dẫn", ((int) GamePanel.WIDTH - ("Hướng dẫn".length() * 25)) / 2, (int) GamePanel.HEIGHT / 2 - 50);

                g.drawString("Thử nghiệm", ((int) GamePanel.WIDTH - ("Thử nghiệm".length() * 25)) / 2, (int) GamePanel.HEIGHT / 2);

                g.drawString("Thông tin nhân vật", ((int) GamePanel.WIDTH - ("Thông tin nhân vật".length() * 20)) / 2, (int) GamePanel.HEIGHT / 2 + 50);

                g.setColor(Color.GRAY);
                g.drawString("Bảng điểm", ((int) GamePanel.WIDTH - ("Bảng điểm".length() * 25)) / 2, (int) GamePanel.HEIGHT / 2 + 100);
                g.setColor(Color.ORANGE);
                g.drawString("Log out", ((int) GamePanel.WIDTH - ("Thoát".length() * 34)) / 2, (int) GamePanel.HEIGHT / 2 + 150);
            } else if (currentChoice == 5) {
                g.drawImage(head, (int) GamePanel.WIDTH / 2 - 300, (int) GamePanel.HEIGHT / 2 + 100, null);
                g.setColor(Color.ORANGE);
                g.drawString("Bắt đầu", ((int) GamePanel.WIDTH - ("Bắt đầu".length() * 25)) / 2, (int) GamePanel.HEIGHT / 2 - 100);

                g.drawString("Hướng dẫn", ((int) GamePanel.WIDTH - ("Hướng dẫn".length() * 25)) / 2, (int) GamePanel.HEIGHT / 2 - 50);

                g.drawString("Thử nghiệm", ((int) GamePanel.WIDTH - ("Thử nghiệm".length() * 25)) / 2, (int) GamePanel.HEIGHT / 2);

                g.drawString("Thông tin nhân vật", ((int) GamePanel.WIDTH - ("Thông tin nhân vật".length() * 20)) / 2, (int) GamePanel.HEIGHT / 2 + 50);

                g.drawString("Bảng điểm", ((int) GamePanel.WIDTH - ("Bảng điểm".length() * 25)) / 2, (int) GamePanel.HEIGHT / 2 + 100);

                g.setColor(Color.GRAY);
                g.drawString("Log out", ((int) GamePanel.WIDTH - ("Thoát".length() * 34)) / 2, (int) GamePanel.HEIGHT / 2 + 150);
            }
            g.setFont(HuyFont);
            g.setColor(Color.cyan);
            g.drawString("Project Nhóm 1", ((int) GamePanel.WIDTH - ("Project Nhóm 1".length() * 22)) / 2, 832);

            g.setColor(Color.GRAY);

        } else {
            g.setColor(Color.decode("#eeeeee"));
            g.fillRect(0, 0, (int) (int) GamePanel.WIDTH, (int) (int) GamePanel.HEIGHT);
        }

        // draw bg
    }

    private void select() {
        if (currentChoice == 0) {
            at.playMenuSelect();
//			JukeBox.play("menuselect");
            PlayerSave.init();
            switch (PlayerSave.state) {
                case 1:
                    gsm.setState(GameStateManager.LEVEL1);
                    break;
                case 2:
                    gsm.setState(GameStateManager.LEVEL2);
                    break;
                case 3:
                    gsm.setState(GameStateManager.LEVEL3);
                    break;
                case 4:
                    gsm.setState(GameStateManager.LEVEL4);
                    break;
                case 5:
                    gsm.setState(GameStateManager.LEVEL5);
                    break;
               
                default:
                    gsm.setState(GameStateManager.LEVEL1);
            }

        } else if (currentChoice == 1) {

            at.playMenuSelect();
            gsm.setState(GameStateManager.GUIDESTATE);
        } else if (currentChoice == 2) {

            at.playMenuSelect();
            gsm.setState(GameStateManager.TEST);
        } else if (currentChoice == 3) {

            at.playMenuSelect();
            gsm.setState(GameStateManager.IF);
        } else if (currentChoice == 4) {

            at.playMenuSelect();
            gsm.setState(GameStateManager.HP);
        } else if (currentChoice == 5) {
            at.playMenuSelect();

            checkLogin = false;
            prepareGUI();
            showTextFieldDemo();
        }
    }

    public void blockinput() {
        if (Keys.isPressed(Keys.ENTER)) {

        }
        if (Keys.isPressed(Keys.UP)) {

        }
        if (Keys.isPressed(Keys.DOWN)) {

        }
    }

    public void handleInput() {

        if (Keys.isPressed(Keys.ENTER)) {
            select();
        }
        if (Keys.isPressed(Keys.UP)) {
            if (currentChoice > 0) {
                at.playMenuOption();
//				JukeBox.play("menuoption", 0);

                currentChoice--;
            }
        }
        if (Keys.isPressed(Keys.DOWN)) {
            if (currentChoice < options.length - 1) {
                at.playMenuOption();
//				JukeBox.play("menuoption", 0);
                currentChoice++;
            }
        }

    }

}
