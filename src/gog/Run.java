package gog;

import java.awt.event.ItemEvent;
import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.*;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageProducer;

class Run {

    JFrame mframe;
    static int WIDTH =  700;
    static int HEIGHT = 700;


    ImagePuller ip;
    JLabel info;
    Board brd;
    Timer t2;
    int auto = 2;
    boolean blitz = false;
    JButton b4;



    JFrame fff;
    BufferedImage imm;
    ImageProducer ipp;
    Rectangle screen;




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
        

        JPanel p1 = new JPanel();
        p1.setPreferredSize(new Dimension(ImagePuller.IMG_WIDTH, ImagePuller.HEIGHT));
        p1.setBorder(BorderFactory.createLineBorder(Color.RED));
        contentPane.add(p1,BorderLayout.LINE_START);

        JPanel p2 = new JPanel();
        p2.setBorder(BorderFactory.createLineBorder(Color.GREEN));
        contentPane.add(p2,BorderLayout.CENTER);

        info = new JLabel("Задайте координаты");
        
        p1.add(info,BorderLayout.PAGE_START);
        p1.add(ip,BorderLayout.LINE_END);



        

        JButton b = new JButton("Задать координаты");
        b.setPreferredSize(new Dimension(200,50));
        JButton b2 = new JButton("Бомба");
        b2.setPreferredSize(new Dimension(200,50));
        JButton b3 = new JButton("Ход");
        b3.setPreferredSize(new Dimension(200,50));

        b4 = new JButton("Робот");
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
                        brd = new Board(ip, 9, 7);
                        brd.setAuto(auto);
                        brd.setCells();
                        brd.bestBomb();

                    }
                });
                t.setRepeats(false);
                t.start();
            }
        });

        

        
        
        b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
               info.setText("Курсор на левый угол игрового поля.Ждите 2 сек.");
               Timer t = new Timer(500,new ActionListener() {
                public void actionPerformed(ActionEvent ae) {


                    
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
                   JButton button = new JButton("Задать координаты");
                   bg.add("North", button);
                   button.addActionListener(new ActionListener() {

                            public void actionPerformed(ActionEvent ae) {
                                //System.out.println(bg.getboardX());
                                ip.setLftTopCorneCoords(bg.getboardX()+5,bg.getboardY()+4);
                                ip.setLftTopHpCoords(bg.gethpX(), bg.gethpY());
                                info.setText("Координаты заданы");
                                ip.repaint();
                            }
                        });

                   frame.getContentPane().add("Center", bg);
                   frame.pack();
                   frame.setSize(ImagePuller.IMG_WIDTH+30, ImagePuller.IMG_HEIGHT + 150);
                   frame.show();



                        


                        
                    }
                });
               t.setRepeats(false);
               t.start();
               
            }
        });



                 t2 = new Timer(900,new ActionListener() {
                    public void actionPerformed(ActionEvent ae) {

                            ip.repaint();
                            Timer t = new Timer(100,new ActionListener() {
                            public void actionPerformed(ActionEvent ae) {
                                    brd = new Board(ip, 9, 7);
                                    brd.maxDamage = true;
                                    brd.setAuto(auto);
                                    brd.setCells();
                                    brd.bestStep();

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
                    auto = 1;
                    b4.setForeground(Color.red);
                    t2.start();
                } else {
                    blitz = false;
                    auto = 2;
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
                   
                        brd = new Board(ip, 9, 7);
                        brd.setAuto(auto);
                        brd.setCells();
                        brd.bestStep();

                    }
                });
                t.setRepeats(false);
                t.start();
            }
        });


        p1.add(b,BorderLayout.PAGE_END);
        p1.add(b2,BorderLayout.PAGE_END);
        p1.add(b3,BorderLayout.PAGE_END);
        p1.add(autostep,BorderLayout.PAGE_END);
        p1.add(b4,BorderLayout.PAGE_END);
        mframe.add(contentPane);

        
        
      
    }

    


    





}


