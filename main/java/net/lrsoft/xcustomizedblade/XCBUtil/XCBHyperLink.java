package net.lrsoft.xcustomizedblade.XCBUtil;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.JLabel;

public class XCBHyperLink extends JLabel {
	/*Code from CSDN,author _cha1R
	 https://blog.csdn.net/tanjiayqq/article/details/17062275
	 */
   private static final long serialVersionUID = 1L;
   private String text;
   private URL link = null;
   private Color preColor = null;
   public XCBHyperLink(String vText, String vLink) {
       super("<html>" + vText + "</html>");
       this.text = vText;
       try {
           if (!vLink.startsWith("http://"))
               vLink = "http://" + vLink;
           this.link = new URL(vLink);
       } catch (MalformedURLException err) {
           err.printStackTrace();
       }
       this.addMouseListener(new MouseAdapter() {
           @Override
           public void mouseExited(MouseEvent e) {
        	   XCBHyperLink.this.setCursor(Cursor
                       .getPredefinedCursor(Cursor.DEFAULT_CURSOR));
               if (preColor != null)
            	   XCBHyperLink.this.setForeground(preColor);
               XCBHyperLink.this.setText("<html>" + text + "</html>");
           }

           @Override
           public void mouseEntered(MouseEvent e) {
        	   XCBHyperLink.this.setCursor(Cursor
                       .getPredefinedCursor(Cursor.HAND_CURSOR));
               preColor = XCBHyperLink.this.getForeground();
               XCBHyperLink.this.setForeground(Color.BLUE);
               XCBHyperLink.this.setText("<html><u>" + text + "</u></html>");
           }

           @Override
           public void mouseClicked(MouseEvent e) {
               try {
                   Desktop.getDesktop().browse(link.toURI());
               } catch (IOException err) {
                   err.printStackTrace();
               } catch (URISyntaxException err) {
                   err.printStackTrace();
               }
           }
       });
   }
}
