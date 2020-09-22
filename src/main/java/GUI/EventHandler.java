package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public interface EventHandler extends ActionListener {

    @Override
    void actionPerformed(ActionEvent e);
}
