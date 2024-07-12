package TypingGameSRC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameMenu extends JFrame implements ActionListener {
    GamePanel panel = new GamePanel();
    //创建对象GameRun下的条目
    JMenuItem replayItem = new JMenuItem("重新游戏");
    JMenu choseItem = new JMenu("选择难度");
    JMenuItem closeItem = new JMenuItem("关闭游戏");
    JMenuItem degree0 = new JMenuItem("经典");
    JMenuItem degree1 = new JMenuItem("简单");
    JMenuItem degree2 = new JMenuItem("普通");
    JMenuItem degree3 = new JMenuItem("困难");
    JMenu HelpJMenu = new JMenu("帮助");
    JMenuItem Help = new JMenuItem("提示");

    public GameMenu() {

        initJFrame();
        initJMenuBar();
        this.setResizable(false);//不可拉伸
        this.setVisible(true);
    }

    private void initJFrame() {
        this.setTitle("打字游戏");
        this.add(panel);
        Thread t = new Thread(panel);
        t.start();
        panel.addKeyListener(panel);
        panel.setFocusable(true);
        this.setSize(760, 600);//760,600
        this.setDefaultCloseOperation(3);//设置关闭模式
        this.setLocationRelativeTo((Component) null);//将窗口放到桌面中间
    }
    private void initJMenuBar() {
        JMenuBar jMenuBar = new JMenuBar();
        // 创建菜单上的选项（功能，帮助）
        JMenu FuncationJMenu = new JMenu("功能");
        //添加被点击执行后的功能
        replayItem.addActionListener(this);
        choseItem.addActionListener(this);
        closeItem.addActionListener(this);
        Help.addActionListener(this);
        degree0.addActionListener(this);
        degree1.addActionListener(this);
        degree2.addActionListener(this);
        degree3.addActionListener(this);
        //将每一个选项的条目添加到当前选项中
        choseItem.add(degree0);
        choseItem.add(degree1);
        choseItem.add(degree2);
        choseItem.add(degree3);
        FuncationJMenu.add(replayItem);
        FuncationJMenu.add(choseItem);
        FuncationJMenu.add(closeItem);
        HelpJMenu.add(Help);
        //将菜单里面的两个功能添加到菜单中
        jMenuBar.add(FuncationJMenu);
        jMenuBar.add(HelpJMenu);
        this.setJMenuBar(jMenuBar);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object c = e.getSource();
        if (c == replayItem) {
            System.out.println("重新游戏");
            this.setVisible(false);
            new GameMenu();
        }if (c == degree0  ) {
            panel.f = true;
            System.out.println("经典");
            System.out.println(panel.getSpeed());
        }
        if (c == degree1) {
            panel.f = false;
            System.out.println("简单");
            panel.setSpeed(7);
        }
        if (c == degree2) {
            panel.f = false;
            System.out.println("普通");
            panel.setSpeed(5);
        }
        if (c == degree3) {
            panel.f = false;
            System.out.println("困难");
            panel.setSpeed(2);
        }
        if (c == closeItem) {
            System.out.println("关闭游戏");
            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@");
            System.out.println("@");
            System.out.println("@         分数：" + panel.getScore());
            System.out.println("@         正确率：" + panel.rate + "%");
            System.out.println("@         时间：" + (System.currentTimeMillis() - panel.StartTime) / 1000 + "s");
            System.out.println("@         生命值：" + panel.hp);
            System.out.println("@                       ");
            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@");
            System.exit(0);
        }
        if (c == Help) {
            System.out.println("提示");
            panel.flag = false;
            JOptionPane.showMessageDialog(null,
                    "你可以选择不同的难度\n按空格表示暂停或开始\n按backspace删除一个字符\n选择功能内的关闭游戏可结算游戏并关闭", "提示", JOptionPane.PLAIN_MESSAGE);
        }
        panel.flag = true;
    }
}


