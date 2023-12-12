import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

public class DemoPanel extends JPanel {
    final int maxCol = 15;
    final int maxRow = 10;
    final int nodeSize = 70;
    final int screenW = nodeSize * maxCol; // W = width
    final int screenH = nodeSize * maxRow; // H = height

    Node[][] node = new Node[maxCol][maxRow];
    Node startN, endN, curN; // starting Node, Ending Node, current Node

    ArrayList<Node> openList = new ArrayList<>();
    ArrayList<Node> checkedList = new ArrayList<>();

    boolean endReached = false;
    int step = 0;

    public DemoPanel() {
        this.setPreferredSize(new Dimension(screenW, screenH));
        // this.setBackground(Color.black);
        this.setLayout(new GridLayout(maxRow, maxCol));
        this.addKeyListener(new KeyHandler(this));
        this.setFocusable(true);

        // place node
        int col = 0;
        int row = 0;

        while (col < maxCol && row < maxRow) {
            node[col][row] = new Node(col, row);
            this.add(node[col][row]);

            col++;
            if (col == maxCol) {
                col = 0;
                row++;
            }
        }

        setStartNode(3, 6);
        setEndNode(13, 4);
        for (int i = 2; i < 8; i++)
            setSolidNode(11, i);

        // 8 col 2 row and 7 row
        for (int i = 2; i <= 7; i += 5) {
            for (int j = 8; j < 11; j++)
                setSolidNode(j, i);

        }

        setCostOnNode();

    }

    private void setStartNode(int col, int row) {
        node[col][row].setAsStartN();
        startN = node[col][row];
        curN = startN;
    }

    private void setEndNode(int col, int row) {
        node[col][row].setAsEndN();
        endN = node[col][row];

    }

    private void setSolidNode(int col, int row) {
        node[col][row].setAsSolid();
    }

    private void getCost(Node node) {
        // G cost
        int xDistance = Math.abs(node.col - startN.col);
        int yDistance = Math.abs(node.row - startN.row);
        node.gCost = xDistance + yDistance;

        // H cost
        xDistance = Math.abs(node.col - endN.col);
        yDistance = Math.abs(node.row - endN.row);
        node.hCost = xDistance + yDistance;

        // F cost
        node.fCost = node.gCost + node.hCost;

        if (node != startN && node != endN) {
            node.setText("<html>F: " + node.fCost + "<br>G: " + node.gCost + "</html>");
        }
    }

    private void setCostOnNode() {
        int col = 0;
        int row = 0;

        while (col < maxCol && row < maxRow) {
            getCost(node[col][row]);
            col++;
            if (col == maxCol) {
                col = 0;
                row++;
            }
        }
    }

    private void trackThePath() {
        Node current = endN;

        while (current != startN) {
            current = current.parent;
            if (current != startN)
                current.setAsPath();
        }
    }

    public void search() {
        if (endReached == false && step < 99999) {
            int col = curN.col;
            int row = curN.row;
            curN.setAsChecked();
            checkedList.add(curN);
            openList.remove(curN);

            if (row - 1 >= 0)
                openNode(node[col][row - 1]);
            if (col - 1 >= 0)
                openNode(node[col - 1][row]);
            if (row + 1 < maxRow)
                openNode(node[col][row + 1]);
            if (col + 1 < maxCol)
                openNode(node[col + 1][row]);

            int bestNodeIndex = 0;
            int bestNodefCost = 999;

            for (int i = 0; i < openList.size(); i++) {
                // check beter F cost
                if (openList.get(i).fCost < bestNodefCost) {
                    bestNodeIndex = i;
                    bestNodefCost = openList.get(i).fCost;
                } else if (openList.get(i).fCost == bestNodefCost) { // if f is equal check the f cost
                    if (openList.get(i).gCost < openList.get(bestNodeIndex).gCost) {
                        bestNodeIndex = i;
                    }
                }

            }

            curN = openList.get(bestNodeIndex);

            if (curN == endN) {
                endReached = true;
                trackThePath();
            }

        }
        step++;
    }

    public void autosearch() {
        while (endReached == false) {
            int col = curN.col;
            int row = curN.row;
            curN.setAsChecked();
            checkedList.add(curN);
            openList.remove(curN);

            if (row - 1 >= 0)
                openNode(node[col][row - 1]);
            if (col - 1 >= 0)
                openNode(node[col - 1][row]);
            if (row + 1 < maxRow)
                openNode(node[col][row + 1]);
            if (col + 1 < maxCol)
                openNode(node[col + 1][row]);

            int bestNodeIndex = 0;
            int bestNodefCost = 999;

            for (int i = 0; i < openList.size(); i++) {
                // check beter F cost
                if (openList.get(i).fCost < bestNodefCost) {
                    bestNodeIndex = i;
                    bestNodefCost = openList.get(i).fCost;
                } else if (openList.get(i).fCost == bestNodefCost) { // if f is equal check the f cost
                    if (openList.get(i).gCost < openList.get(bestNodeIndex).gCost) {
                        bestNodeIndex = i;
                    }
                }

            }

            curN = openList.get(bestNodeIndex);

            if (curN == endN) {
                endReached = true;
                trackThePath();
            }

        }
    }

    public void openNode(Node node) {
        if (node.open == false && node.checked == false && node.solid == false) {
            node.setAsOpen();
            node.parent = curN;
            openList.add(node);
        }
    }
}
