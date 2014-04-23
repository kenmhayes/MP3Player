import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class MP3PlayerView extends JFrame implements ActionListener {

    private MP3PlayerController controller;

    private final JTextArea tName;

    private final JButton bPlay, bPrevious, bSkip;

    private static final int MP3_PLAYER_ROW = 2, MP3_PLAYER_COL = 1,
            BUTTON_PANEL_ROW = 1, BUTTON_PANEL_COL = 3, TEXT_AREA_HEIGHT = 1,
            TEXT_AREA_WIDTH = 40;

    public MP3PlayerView() {

        super("MP3 Player");

        this.tName = new JTextArea("", TEXT_AREA_HEIGHT, TEXT_AREA_WIDTH);
        this.tName.setEditable(false);

        this.bPlay = new JButton("Play");
        this.bPrevious = new JButton("<<");
        this.bSkip = new JButton(">>");

        JPanel buttonPanel = new JPanel(new GridLayout(BUTTON_PANEL_ROW,
                BUTTON_PANEL_COL));

        buttonPanel.add(this.bPrevious);
        buttonPanel.add(this.bPlay);
        buttonPanel.add(this.bSkip);

        this.setLayout(new GridLayout(MP3_PLAYER_ROW, MP3_PLAYER_COL));

        this.add(this.tName);
        this.add(buttonPanel);

        this.bPlay.addActionListener(this);
        this.bPrevious.addActionListener(this);
        this.bSkip.addActionListener(this);

        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public void registerObserver(MP3PlayerController controller) {
        this.controller = controller;
    }

    public void updateNameDisplay(String name) {
        this.tName.setText(name);
    }

    public void updatePlayButton(boolean play) {
        if (play) {
            this.bPlay.setText("Pause");
        } else {
            this.bPlay.setText("Play");
        }
    }

    public void updatePreviousButton(boolean allowed) {
        this.bPrevious.setEnabled(allowed);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        Object source = event.getSource();
        if (source == this.bPlay) {
            this.controller.processPlayEvent();
        } else if (source == this.bPrevious) {
            this.controller.processPreviousEvent();
        } else if (source == this.bSkip) {
            this.controller.processSkipEvent();
        }

        this.setCursor(Cursor.getDefaultCursor());
    }
}
