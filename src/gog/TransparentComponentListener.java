/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gog;

import java.awt.Component;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.JFrame;

  class TransparentComponentListener
           implements ComponentListener {

       public void componentResized(ComponentEvent e) {
           Component[] components = ((JFrame) e.getComponent())
                   .getContentPane().getComponents();
           components[0].repaint();
       }

       public void componentMoved(ComponentEvent e) {
           componentResized(e);
       }

       public void componentShown(ComponentEvent e) {
           componentResized(e);
       }

       public void componentHidden(ComponentEvent e) {
           componentResized(e);
       }
   }