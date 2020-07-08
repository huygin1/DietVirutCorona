/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amThanh;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author huygi
 */
public class amThanh {

    public Clip clip;
    private Clip nhacjump;
    private Clip SFXDietVirut;
    private Clip menuOption;
    private Clip menuSelect;
    private Clip SFXVirutHit;
    private Clip SFXVirutDie;
    private Clip DichChuyen;
    public amThanh() {

        try {
            File fileDichChuyen = new File("amThanh/dichchuyen.wav");
            File fileSFXVirutDie = new File("amThanh/virutdie.wav");
            File fileSFXVirutHit = new File("amThanh/enemyhit.wav");
            File file = new File("amThanh/a.wav");
            File fileMenuOption = new File("amThanh/chonMenu.wav");
            File fileMenuSelect = new File("amThanh/menu.wav");
            File fileSFXDietVirut = new File("amThanh/playerattack.wav");
            File fileNhacJump = new File("amThanh/jump.wav");
            if (fileNhacJump.exists()) {
                AudioInputStream soundjump = AudioSystem.getAudioInputStream(fileNhacJump);

                nhacjump = AudioSystem.getClip();
                nhacjump.open(soundjump);

            } else {
                throw new RuntimeException("Khong tim thay am thanh: ");
            }
             if (fileSFXVirutDie.exists()) {
                AudioInputStream sound = AudioSystem.getAudioInputStream(fileSFXVirutDie);

                SFXVirutDie = AudioSystem.getClip();
                SFXVirutDie.open(sound);

            } else {
                throw new RuntimeException("Khong tim thay am thanh: ");
            }
             if (fileDichChuyen.exists()) {
                AudioInputStream sound = AudioSystem.getAudioInputStream(fileDichChuyen);

                DichChuyen = AudioSystem.getClip();
                DichChuyen.open(sound);

            } else {
                throw new RuntimeException("Khong tim thay am thanh: ");
            }
            if (fileSFXDietVirut.exists()) {
                AudioInputStream sound = AudioSystem.getAudioInputStream(fileSFXDietVirut);

                SFXDietVirut = AudioSystem.getClip();
                SFXDietVirut.open(sound);

            } else {
                throw new RuntimeException("Khong tim thay am thanh: ");
            }
            if (fileMenuOption.exists()) {
                AudioInputStream sound = AudioSystem.getAudioInputStream(fileMenuOption);

                menuOption = AudioSystem.getClip();
                menuOption.open(sound);

            } else {
                throw new RuntimeException("Khong tim thay am thanh: ");
            }
            if (fileMenuSelect.exists()) {
                AudioInputStream sound = AudioSystem.getAudioInputStream(fileMenuSelect);

                menuSelect = AudioSystem.getClip();
                menuSelect.open(sound);

            } else {
                throw new RuntimeException("Khong tim thay am thanh: ");
            }
             if (fileSFXVirutHit.exists()) {
                AudioInputStream sound = AudioSystem.getAudioInputStream(fileSFXVirutHit);

                SFXVirutHit = AudioSystem.getClip();
                SFXVirutHit.open(sound);

            } else {
                throw new RuntimeException("Khong tim thay am thanh: ");
            }
            if (file.exists()) {
                AudioInputStream sound = AudioSystem.getAudioInputStream(file);

                clip = AudioSystem.getClip();
                clip.open(sound);
            } else {
                throw new RuntimeException("Khong tim thay am thanh: ");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new RuntimeException("loi URL: " + e);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
            throw new RuntimeException("Khong ho tro dinh dang file nay: " + e);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Nhap Xuat file That bai: " + e);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
            throw new RuntimeException("Sound: Line Unavailable Exception Error: " + e);
        }
    }

    public void play() {

        if (nhacjump.isRunning()) {
            nhacjump.stop();
        }
        nhacjump.setFramePosition(0);
        while (!nhacjump.isRunning()) {
            nhacjump.start();
        }
    }
    public void playDichChuyen(){
        if (DichChuyen.isRunning()) {
            DichChuyen.stop();
        }
        DichChuyen.setFramePosition(0);
        while (!DichChuyen.isRunning()) {
            DichChuyen.start();
        } 
    }
    public void playloop() {

        if (clip.isRunning()) {
            clip.stop();
        }
        clip.setFramePosition(0);
        while (!clip.isRunning()) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }
    public void playattack() {

        if (SFXDietVirut.isRunning()) {
            SFXDietVirut.stop();
        }
        SFXDietVirut.setFramePosition(0);
        while (!SFXDietVirut.isRunning()) {
            SFXDietVirut.start();
        }
    }

    public void playMenuOption() {

        if (menuOption.isRunning()) {
            menuOption.stop();
        }
        menuOption.setFramePosition(0);
        while (!menuOption.isRunning()) {
            menuOption.start();
        }
    }

    public void playMenuSelect() {

        if (menuSelect.isRunning()) {
            menuSelect.stop();
        }
        menuSelect.setFramePosition(0);
        while (!menuSelect.isRunning()) {
            menuSelect.start();
        }
    }
    public void playVirutHit() {

        if (SFXVirutHit.isRunning()) {
            SFXVirutHit.stop();
        }
        SFXVirutHit.setFramePosition(0);
        while (!SFXVirutHit.isRunning()) {
            SFXVirutHit.start();
        }
    }
    public void playVirutDie(){
         if (SFXVirutDie.isRunning()) {
            SFXVirutDie.stop();
        }
        SFXVirutDie.setFramePosition(0);
        while (!SFXVirutDie.isRunning()) {
            SFXVirutDie.start();
        }
    }
}
