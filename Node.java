import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import java.awt.Color;

public class Node extends JButton implements ActionListener {
    Node parent;
    int col;
    int row;
    int gCost; // the distance between current node and start node
    int hCost; // the distance between current node and end node
    int fCost; // sum of gCost and hCost
    boolean start;
    boolean end;
    boolean solid;
    boolean open;
    boolean checked;

    public Node(int col, int row) {
        this.col = col;
        this.row = row;

        setBackground(Color.white);
        setForeground(Color.black);
        addActionListener(this);

    }

    public void setAsStartN() {
        setBackground(Color.red);
        setText("Start");
        start = true;
    }

    public void setAsEndN() {
        setBackground(Color.green);
        setForeground(Color.white);
        setText("End");
        end = true;
    }

    public void setAsSolid() {
        setBackground(Color.black);
        solid = true;
    }

    public void actionPerformed(ActionEvent e) {
        setBackground(Color.orange);
    }

    public void setAsOpen() {
        open = true;
    }

    public void setAsChecked() {
        if (start == false && end == false) {
            setBackground(Color.orange);
            setForeground(Color.black);
        }

        checked = true;
    }

    public void setAsPath() {
        setBackground(Color.blue);
        setForeground(Color.black);
    }
}
