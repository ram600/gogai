package gog;

import java.awt.event.ItemEvent;
import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.*;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.border.LineBorder;

class Run {

    JFrame mframe;
    static int WIDTH =  630;
    static int HEIGHT = 700;
    static   final JLabel lb_hp = new JLabel("-");
    static   final JLabel lb_rivel_hp = new JLabel("-");
   
    
    ImagePuller ip;
    JLabel info;
    Board brd;
    Timer t2;
    int auto = 2;
    boolean blitz = false;
    JButton b4;
    static JLabel axe;
    static JLabel niga;


    JFrame fff;
    BufferedImage imm;
    ImageProducer ipp;
    Rectangle screen;

        JButton b ;
        JButton b2 ;
        JButton tnt_button ;
         JButton b3 ;




    public static void main(String[] args){
        new Run();
    }

     Run(){
        mframe = new JFrame();
        mframe.setTitle("Дар богов - AI");
        mframe.setSize(this.WIDTH, this.HEIGHT);
        mframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mframe.getContentPane().setLayout(new FlowLayout());
        ip = new ImagePuller();
        
        start();
        mframe.setVisible(true);
        
        
    }


    private void starts(){
       
        JButton b = new JButton("Начать");
        mframe.getContentPane().add(b);
        mframe.getContentPane().add(new ImagePuller());
        mframe.getContentPane().validate();
          
      
    

        
    }


    private void start(){JFrame.setDefaultLookAndFeelDecorated(true);
        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setPreferredSize(new Dimension(mframe.getWidth()-50, mframe.getHeight()-50));
        contentPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        

        final JPanel p1 = new JPanel();
        p1.setPreferredSize(new Dimension(ImagePuller.IMG_WIDTH, ImagePuller.HEIGHT));
        //p1.setBorder(BorderFactory.createLineBorder(Color.RED));
        contentPane.add(p1,BorderLayout.LINE_START);

        JPanel p2 = new JPanel();
        //p2.setBorder(BorderFactory.createLineBorder(Color.GREEN));
        contentPane.add(p2,BorderLayout.CENTER);

        info = new JLabel("Задайте координаты");
        
        p1.add(info,BorderLayout.PAGE_START);
        p1.add(ip,BorderLayout.LINE_END);

        
        Image image = null;
        try {
            
            File file = new File(new File(".").getAbsolutePath()+"/data/help.jpg");
            image = ImageIO.read(file);

        } catch (IOException e) {
            System.out.print(e.getMessage());
        }

       
        JLabel help = new JLabel(new ImageIcon(image));
        

        

         b = new JButton("Задать координаты");
        b.setPreferredSize(new Dimension(200,50));
        
         b2 = new JButton("Бомба");
        b2.setPreferredSize(new Dimension(100,50));
        tnt_button = new JButton("Динамит");
        tnt_button.setPreferredSize(new Dimension(100,50));

        b3 = new JButton("Ход");
        b3.setPreferredSize(new Dimension(200,50));

        b4 = new JButton("Блиц");
        b4.setPreferredSize(new Dimension(200,50));
        

         JCheckBox autostep = new JCheckBox("Автоход");
         autostep.setSelected(false);
         autostep.addItemListener(new ItemListener() {

            public void itemStateChanged(ItemEvent ie) {
                auto = ie.getStateChange();
            }
        });

        

        b2.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                ip.repaint();
                Timer t = new Timer(100,new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                        brd.setIP(ip);
                        brd.setAuto(auto);
                        brd.setCells();
                        brd.bestBomb(false);
                        b2.setForeground(Color.red);
                        t2.stop();
                        offAll(b2);

                    }
                });
                t.setRepeats(false);
                t.start();
            }
        });

        tnt_button.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                ip.repaint();
                Timer t = new Timer(100,new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                        brd.setIP(ip);
                        brd.setAuto(auto);
                        brd.setCells();
                        brd.bestBomb(true);
                        tnt_button.setForeground(Color.red);
                        t2.stop();
                        offAll(tnt_button);

                    }
                });
                t.setRepeats(false);
                t.start();
            }
        });

        

        
        
        b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
               info.setText("Наведите рамочку на игровую облать");
//               Timer t = new Timer(100,new ActionListener() {
//                public void actionPerformed(ActionEvent ae) {


                    
//                    Point location = MouseInfo.getPointerInfo().getLocation();
//                    double x = location.getX();
//                    double y = location.getY();
//                    System.out.println("x = " + x);
//                    System.out.println("y = " + y);

//                        ip.setLftTopCorneCoords();
//                        info.setText("Координаты заданы");
//                        ip.repaint();
                    // OLOLOLO


                   JFrame frame = new JFrame("Transparent Window");
                   frame.addComponentListener(new TransparentComponentListener());
                   final TransparentBackground bg = new TransparentBackground(frame);
                   bg.setLayout(new BorderLayout());
                   JButton button = new JButton("УСТАНОВИТЬ");
                   bg.add("North", button);
                   button.addActionListener(new ActionListener() {

                            public void actionPerformed(ActionEvent ae) {
                                //System.out.println(bg.getboardX());
                                ip.setLftTopCorneCoords(bg.getboardX()+7,bg.getboardY()+7);
                                ip.setLftTopHpCoords(bg.gethpX(), bg.gethpY());
                                info.setText("Координаты заданы");
                                ip.repaint();
                                brd = new Board(ip, 9, 7);
                                b3.doClick();

                                try {



                                    Image im_axe = ImageIO.read(new File(new File(".").getAbsolutePath()+"/data/axe.png"));
                                     p1.setLayout(null);
                                     axe = new JLabel(new ImageIcon(im_axe));
                                     p1.add(axe);
                                     Insets insets = p1.getInsets();
                                     Dimension size = axe.getPreferredSize();
                                     axe.setBounds(65+ insets.left, 25 + insets.top,
                                     size.width, size.height);
                                     
                                    Image im_niga = ImageIO.read(new File(new File(".").getAbsolutePath()+"/data/niga.jpg"));
                                     p1.setLayout(null);
                                     niga = new JLabel(new ImageIcon(im_niga));
                                     p1.add(niga);
                                      insets = p1.getInsets();
                                      size = niga.getPreferredSize();
                                     niga.setBounds(65+ insets.left, 25 + insets.top,
                                     size.width, size.height);

                                     
                                     
                                } catch (Exception e) {
                                }
                                

                            }
                        });

                   frame.getContentPane().add("Center", bg);
                   frame.pack();
                   frame.setSize(ImagePuller.IMG_WIDTH+30, ImagePuller.IMG_HEIGHT + 150);
                   frame.show();



                        


                        
//                    }
//                });
//               t.setRepeats(false);
//               t.start();
               
            }
        });



                 t2 = new Timer(900,new ActionListener() {
                    public void actionPerformed(ActionEvent ae) {

                            ip.repaint();
                            Timer t = new Timer(100,new ActionListener() {
                            public void actionPerformed(ActionEvent ae) {
                                    brd.setIP(ip);
                                    brd.maxDamage = true;
                                    brd.setAuto(auto);
                                    brd.setCells();
                                    brd.autoStep();
                                    

                                }
                            });
                            t.setRepeats(false);
                            t.start();


                        }
                    });
                    t2.setRepeats(true);


           b4.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {

                if (!blitz) {
                    blitz = true;
                    
                    b4.setForeground(Color.red);
                    t2.start();
                    offAll(b4);
                } else {
                    blitz = false;
                   
                    b4.setForeground(Color.black);
                    t2.stop();
                }

            }
        });

        
        
        
        b3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                //ColorBrain cb = new ColorBrain(ip.getImage());
                ///создать Board только один разЁЁЁ
                ip.repaint();
                Timer t = new Timer(100,new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                   
                        brd.setIP(ip);
                        brd.setAuto(auto);
                        brd.setCells();
                        brd.bestStep();
                        b3.setForeground(Color.red);
                        t2.stop();
                        offAll(b3);

                    }
                });
                t.setRepeats(false);
                t.start();
            }
        });



        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Помощь");
        menu.setMnemonic(KeyEvent.VK_A);
        menu.getAccessibleContext().setAccessibleDescription( "The only menu in this program that has menu items");
        menuBar.add(menu);
        //a group of JMenuItems
        JMenuItem menuItem = new JMenuItem("Помощь",
                                 KeyEvent.VK_F1);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_F1, ActionEvent.ALT_MASK));
        menuItem.setAccelerator(KeyStroke.getKeyStroke("F1"));
        menuItem.getAccessibleContext().setAccessibleDescription(
                "This doesn't really do anything");

        menuItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                JFrame help = new JFrame("Помощь");
                help.setSize(900, 500);

                Image image = null;
                try {

                    File file = new File(new File(".").getAbsolutePath()+"/data/help2.jpg");
                    image = ImageIO.read(file);
                    

                } catch (IOException e) {
                    System.out.print(e.getMessage());
                }
                JPanel h_p = new JPanel();
              
              
                
                JLabel help_lbl = new JLabel(new ImageIcon(image));
                h_p.add(help_lbl);
                

                JScrollPane jScrollPane = new JScrollPane(h_p);
                
                jScrollPane.setPreferredSize(new Dimension(700, 500));
                jScrollPane.setViewportBorder(new LineBorder(Color.RED));
                jScrollPane.setAutoscrolls(true);
                //h_p.add(jScrollPane);
                
                
                help.add(jScrollPane,BorderLayout.PAGE_START);
                help.setVisible(true);
            }
        });
        
        menu.add(menuItem);


        menuBar.add(menu);


        p1.add(b,BorderLayout.PAGE_END);
        p1.add(b2,BorderLayout.PAGE_END);
        p1.add(tnt_button,BorderLayout.PAGE_END);
        p1.add(b3,BorderLayout.PAGE_END);
        p1.add(autostep,BorderLayout.PAGE_END);
        p1.add(b4,BorderLayout.PAGE_END);
       // p1.add(lb_hp,BorderLayout.PAGE_END);
       // p1.add(lb_rivel_hp,BorderLayout.PAGE_END);
        p1.add(help,BorderLayout.PAGE_END);
        
        mframe.add(contentPane);
        mframe.setJMenuBar(menuBar);

        
        
      
    }

    

    private void offAll(Component c){

        if(!c.equals(b)){
            b.setForeground(Color.black);
        }
        if(!c.equals(b2)){
            b2.setForeground(Color.black);
        }
        if(!c.equals(tnt_button)){
            tnt_button.setForeground(Color.black);
        }
        if(!c.equals(b3)){
            b3.setForeground(Color.black);
        }
        if(!c.equals(b4)){
            b4.setForeground(Color.black);
        }

    }
    





}


