package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * An interface for the event used in the GUI
 */
public interface EventHandler extends ActionListener {

    void actionPerformed(ActionEvent e);
}
