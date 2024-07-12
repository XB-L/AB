package TypingGameSRC;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.Random;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable, KeyListener {
    final long StartTime = System.currentTimeMillis();//记录游戏开始时间
    boolean flag = true;//判断用户是否暂停，true为运行，false为暂停
    public boolean f = false;
    private boolean flag1 = true;//判断游戏是否结束（即生命值为0）
    private String s = "";//记录玩家输入的字符串
    private String target = "";//记录玩家想要消除的字符串
    private int targetIndex = 0;//记录玩家想要消除单词的索引
    private int lowyy;//记录最靠下的单词y方向坐标
    private float LetterNumber = 0;//玩家输入的总字母个数
    private float TrueLetter = 0;//玩家输入的正确字母个数
    int rate = 0;//玩家输入的正确率
    int hp = 100;//生命值
    private int[] xx = new int[10];//横坐标
    private int[] yy = new int[10];//纵坐标
    private String[] words = new String[10];//单词库
    private Color[] colors = new Color[15];//每个单词的颜色
    private int score = 0;//分数
    private double speed = 10;//单词下落速度
    public int getScore(){
        return score;
    }
    public double getSpeed(){
        return speed;
    }
    public void setSpeed(int speed){
        this.speed = speed;
    }

    public GamePanel() {
        setWords();
        background();
    }

    public void setWords() {
        for (int i = 0; i < 10; i++) {
            this.xx[i] = (int) (Math.random() * 645);
            this.yy[i] = (int) (Math.random() * 200);
            this.colors[i] = RandomColor();
            words[i] = Words();
        }
    }

    public void background() {
        this.setLayout(null);//取消默认居中放置（针对添加的组件位置）
        //要和背景图片路径保持一致
        ImageIcon im = new ImageIcon("src\\TypingGameSRC\\BackGround.jpg");
        JLabel jl = new JLabel(im);
        jl.setSize(750, 550);//750 550
        this.add(jl);//添加背景图片
        this.repaint();//更新界面
    }

    public Color RandomColor() {
        Random r = new Random();
        Color color = new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));
        return color;
    }

    private String Words() {
        String[] s = {"public", "static", "void", "main", "double",
                      "int", "return", "char", "String", "private",
                      "extends","boolean","float","long","Arrays"};
        Random a = new Random();
        return s[a.nextInt(s.length)];
    }

    public void paint(Graphics g) {
        super.paint(g);
        if (!flag) {
            g.setFont(new Font("宋体", Font.BOLD, 80));
            if (!flag1)
                g.drawString("游戏结束", 230, 250);
            else
                g.drawString("游戏暂停", 230, 250);
        }
        Font ft = new Font("微软雅黑", 1, 28);
        g.setFont(ft);
        g.drawString("分数:" + this.score, 50, 50);
        g.drawString("正确率:" + rate + "%", 50, 100);
        g.drawString("输入:" + s, 50, 200);
        g.drawString("生命值:" + hp, 50, 150);
        for (int i = 0; i < 10; i++) {
            g.setColor(this.colors[i]);
            g.drawString(this.words[i], this.xx[i], this.yy[i]);
        }
    }

    public void run() {
        while (true) {
            while (!flag) {     //判断是否暂停
                try {
                    Thread.sleep(100);
                } catch (InterruptedException var2) {
                    var2.printStackTrace();
                }
            }
            for (int i = 0; i < 10; i++) {
                this.yy[i]++;
                if (this.yy[i] > 560) {
                    this.hp--;
                    if (hp == 0) {
                        flag1 = false;
                        flag = !flag;
                    }
                    this.yy[i] = 0;
                }
            }
            try {
                Thread.sleep((long) this.speed * 3);
            } catch (InterruptedException var2) {
                var2.printStackTrace();
            }
            this.repaint();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        Random p = new Random();
        if (e.getKeyCode() == 8 && s.length() != 0)//刪除1个字母（当玩家按下backspace时）
        {
            s = s.substring(0, s.length() - 1);
        }
        if (e.getKeyCode() == 32) {
            if (flag) System.out.println("暂停");
            if (!flag) System.out.println("开始");
            flag = !flag;
            this.repaint();
        }
        if (Character.isLetter(e.getKeyChar())) {
            this.LetterNumber++;
            s += e.getKeyChar();
        }
        if (s.length() == 2) {  //当玩家每次输入2个字母后，开始遍历，找到玩家想要删除的单词（这里不是很严谨）
            for (int i = 0; i < words.length; i++) {
                String t = words[i].substring(0, 2);
                if (t.equals(s)) {
                    target = words[i];
                    targetIndex = i;
                }
            }
        }
        for (int i = 0; i < words.length; i++) {//遍历单词，找到最靠下的单词
            if (words[targetIndex].equals(words[i])) {
                if (this.yy[targetIndex] < this.yy[i]) {
                    targetIndex = i;
                }
                if (yy[i] < yy[(i + 1) % 9]) lowyy = yy[(i + 1) % 9];
            }
        }
        if (s.length() >= 2)//当前正在聚焦的单词发生闪烁（当玩家输入一个单词的两个字母后）
        {
            colors[targetIndex] = new Color(p.nextInt(255), p.nextInt(255), p.nextInt(255));
        }
        if (lowyy >= 600) {//判断是否有单词已经到达最低点
            hp--;
        }
        if (s.equals(target) && s.length() != 0) {  //如果用户输对一个单词
            if(f){
                speed  = speed - 0.1;
            }
            Voice();
            System.out.println(s);
            this.xx[targetIndex] = (int) (Math.random() * 670.0D);
            this.yy[targetIndex] = 0;
            this.words[targetIndex] = Words();
            this.colors[targetIndex] = RandomColor();
            if (targetIndex + 1 > 9) targetIndex %= words.length - 1;
            this.score++;
            this.TrueLetter += s.length();
            if (LetterNumber != 0) this.rate = (int) ((this.TrueLetter) / this.LetterNumber * 100);
            s = "";
        }
        this.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
    void Voice(){
        final String f="src\\TypingGameSRC\\voice.aif";
        Clip c=null;
        try
        {
            c= AudioSystem.getClip();
            c.open(AudioSystem.getAudioInputStream(new File(f)));
            c.loop(0);
        }
        catch(Exception ex)
        {
        }
    }
}



