package ui;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Objects;
import java.util.Random;

public class GameJFrame extends JFrame implements KeyListener, ActionListener {
    public GameJFrame() throws HeadlessException {
        this.step=0;
        this.win= new int[][]{
                {1,5,9,13},
                {2,6,10,14},
                {3,7,11,15},
                {4,8,12,0}
        };
        // 初始化界面
        this.initJFrame();
        // 初始化菜单
        this.initJMenu();
        // 初始化数据
        this.initJData();
        // 初始化路径
        this.initPath();
        // 初始化图片
        this.initImage();
        // 界面可视
        this.setVisible(true);
    }
    // 初始化界面尺寸及可视化
    private void initJFrame() {
        this.setSize(603,680);
        // 设置标题
        this.setTitle("拼图单机版 v1.0");
        // 设置界面居中
        this.setLocationRelativeTo(null);
        // 设置关闭模式
        this.setDefaultCloseOperation(3);
        // 取消默认的居中放置
        this.setLayout(null);
        this.addKeyListener(this);
    }

    // 初始化菜单
    private void initJMenu() {
        // 创建菜单对象
        JMenuBar jMenuBar=new JMenuBar();
        JMenu functionJMenu=new JMenu("功能");
        JMenu changeJMenu=new JMenu("更换图片");
        JMenu aboutJMenu=new JMenu("关于我们");

        this.girlItem=new JMenuItem("美女");
        this.animalItem=new JMenuItem("动物");
        this.sportItem=new JMenuItem("运动");


        this.replayItem=new JMenuItem("重新游戏");
        this.reloginItem=new JMenuItem("重新登录");
        this.closeItem=new JMenuItem("关闭游戏");
        this.accountItem=new JMenuItem("公众号");

        // 将每一个Item加入到菜单选项中
        changeJMenu.add(this.girlItem);
        changeJMenu.add(this.animalItem);
        changeJMenu.add(this.sportItem);
        functionJMenu.add(changeJMenu);
        functionJMenu.add(replayItem);
        functionJMenu.add(reloginItem);
        functionJMenu.add(closeItem);
        aboutJMenu.add(accountItem);

        // 绑定事件
        this.girlItem.addActionListener(this);
        this.animalItem.addActionListener(this);
        this.sportItem.addActionListener(this);
        this.replayItem.addActionListener(this);
        this.reloginItem.addActionListener(this);
        this.closeItem.addActionListener(this);
        this.accountItem.addActionListener(this);

        // 将菜单中的两个选项添加到菜单中
        jMenuBar.add(functionJMenu);
        jMenuBar.add(aboutJMenu);

        this.setJMenuBar(jMenuBar);
    }
    private void initPath(){
        // 随机选择美女、动物、运动(0,1,2)
        Random r=new Random();
        int typeNo=r.nextInt(3);
        this.updatePath(typeNo);
    }
    // 初始化数据
    private void initJData() {
        int[] tempArr={0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
        // 打乱数组中的数据
        Random r=new Random();
        for (int i = 0; i < tempArr.length; i++) {
            int index=r.nextInt(tempArr.length);
            int temp=tempArr[i];
            tempArr[i]=tempArr[index];
            tempArr[index]=temp;
        }
        /*
        for (int i = 0; i < tempArr.length; i++) {
            System.out.print(tempArr[i]+" ");
        }System.out.println();
        */
        // 赋值给二维数组
        this.data=new int[4][4];
        for (int i = 0; i < tempArr.length; i++) {
            if(tempArr[i]==0){
                x=i/4;
                y=i%4;
            }
            this.data[i/4][i%4]=tempArr[i];
        }

    }
    // 初始化图片
    private void initImage() {
        // System.out.println("图片路径为："+this.path);
        // 清空界面
        this.getContentPane().removeAll();

        // 判断是否成功
        if(this.isVictory()){
            // 显示胜利里的图标
            JLabel winJLabel=new JLabel(new ImageIcon("Day16-项目练习\\image\\win.png"));
            winJLabel.setBounds(208,283,197,73);
            this.getContentPane().add(winJLabel);
        }

        // 显示步数
        JLabel stepCount=new JLabel("步数："+step);
        stepCount.setBounds(50,30,100,20);
        this.getContentPane().add(stepCount);
        // 载入拼图
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < 4; i++) {
                JLabel jLabel=new JLabel(new ImageIcon(
                        path+this.data[i][j]+".jpg"));
                // if(this.data[i][j]==0) System.out.println(x+","+y+"为空白图片");
                jLabel.setBounds(105*i+83,105*j+134,105,105);
                // 图片添加边框
                jLabel.setBorder(new BevelBorder(BevelBorder.LOWERED));
                // 添加到界面中
                // this.add(jLabel);
                this.getContentPane().add(jLabel);
            }
        }
        // 添加背景图片
        JLabel background=new JLabel(new ImageIcon("Day16-项目练习\\image\\background.png"));
        background.setBounds(40,40,508,560);
        // 将背景图片添加到界面
        this.getContentPane().add(background);
        // 刷新界面
        this.getContentPane().repaint();
    }
    private int[][] data;
    public int[][] getData() {
        return data;
    }
    private int x,y;// 记录空白方块的位置
    private int[][] win;
    private int step;// 记录步数
    private String path="Day16-项目练习\\image\\";
    public String getPath() {
        return path;
    }
    private JMenuItem girlItem,animalItem,sportItem,replayItem,reloginItem,closeItem,accountItem;// 按钮对象
    private String type;// 图片类型
    private int N0;// 图片编号
    public void setPath(String path) {
        this.path = path;
    }
    @Override
    public int getX() {
        return x;
    }
    @Override
    public int getY() {
        return y;
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }
    // 按下不松时调用
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if(code==65){
            // 清除所有图片
            this.getContentPane().removeAll();
            // 加载第一张完整的图片
            JLabel all=new JLabel(new ImageIcon(path+"all.jpg"));
            all.setBounds(83,134,420,420);
            this.getContentPane().add(all);
            // 添加背景图片
            JLabel background=new JLabel(new ImageIcon("Day16-项目练习\\image\\background.png"));
            background.setBounds(40,40,508,560);
            // 将背景图片添加到界面
            this.getContentPane().add(background);
            // 刷新界面
            this.getContentPane().repaint();
        }
    }
    // 松开时调用
    @Override
    public void keyReleased(KeyEvent e) {
        if(isVictory()) return;
        // 对上下左右进行判断
        int code=e.getKeyCode();
        // System.out.println(code);
        if(code==37){
            // System.out.println("向左移动");
            if(x<3){
                this.data[x][y] = this.data[x+1][y];
                this.data[x+1][y] = 0;
                this.x += 1;
                this.step++;
                this.initImage();
            }
        }
        else if(code==38){
            // System.out.println("向上移动");
            if(y<3){
                this.data[x][y] = this.data[x][y + 1];
                this.data[x][y + 1] = 0;
                this.y += 1;
                this.step++;
                this.initImage();
            }
        }
        else if(code==39){
            //System.out.println("向右移动");
            if(x>0){
                this.data[x][y] = this.data[x-1][y];
                this.data[x-1][y] = 0;
                this.x -= 1;
                this.step++;
                this.initImage();
            }
        }
        else if(code==40){
            // System.out.println("向下移动");
            if(y>0){
                this.data[x][y] = this.data[x][y - 1];
                this.data[x][y - 1] = 0;
                this.y -= 1;
                this.step++;
                this.initImage();
            }
        }
        else if(code==65){
            // 按a显示图片
            this.initImage();
        }
        else if (code==70) {
            // 按f跳过
            this.data=new int[][]{
                    {1,5,9,13},
                    {2,6,10,14},
                    {3,7,11,15},
                    {4,8,12,0}
            };
            this.x=3;
            this.y=3;
            this.initImage();
        }
    }
    // 判断是否成功
    public boolean isVictory(){
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if(this.data[i][j]!=this.win[i][j]) return false;
            }
        }
        return true;
    }
    // 点击时调用
    @Override
    public void actionPerformed(ActionEvent e) {
        // 获取被点击的对象
        Object obj=e.getSource();
        if(obj==this.replayItem){
            // System.out.println("重新开始");
            // 计步器清零
            this.step=0;
            // 打乱数据
            initJData();
            // 重新加载图片
            initImage();
        }
        else if(obj==this.reloginItem){
            // System.out.println("重新登录");
            this.setVisible(false);
            new LoginJFrame();
        }
        else if(obj==this.closeItem){
            // System.out.println("关闭游戏");
            System.exit(0);
        }
        else if(obj==this.accountItem){
            System.out.println("显示公众号");
            JDialog jDialog=new JDialog();
            JLabel jAbout=new JLabel(new ImageIcon("Day16-项目练习\\image\\about.png"));
            jAbout.setBounds(0,0,258,258);
            jDialog.getContentPane().add(jAbout);
            jDialog.setSize(344,344);
            jDialog.setAlwaysOnTop(true);
            jDialog.setLocationRelativeTo(null);
            // 弹窗不关闭则无法进行下一步
            jDialog.setModal(true);
            jDialog.setVisible(true);
        }
        else if (obj==this.girlItem) {
            this.updatePath(0);
            this.initJData();
            this.initImage();
        }
        else if(obj==this.animalItem){
            this.updatePath(1);
            this.initJData();
            this.initImage();
        }
        else if(obj==this.sportItem){
            this.updatePath(2);
            this.initJData();
            this.initImage();
        }

    }
    private void updatePath(int typeNo) {
        String[] arrType=new String[]{"girl","animal","sport"};
        int[] arrTypeCapacity=new int[]{13,8,10};
        this.type = arrType[typeNo];
        // 选择图片编号
        Random r = new Random();
        this.N0=r.nextInt(1,arrTypeCapacity[typeNo]);
        this.path="Day16-项目练习\\image\\"+this.type+"\\"+this.type+this.N0+"\\";
        this.step=0;
    }
}
