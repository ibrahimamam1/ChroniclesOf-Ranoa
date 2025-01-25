package ai;

import java.util.ArrayList;

import entity.Entity;
import game.GamePanel;

public class PathFinder {
    
    GamePanel gp;
    Node[][] node;
    ArrayList<Node> openList = new ArrayList<>();
    public ArrayList<Node>pathList = new ArrayList<>();
    Node startNode , goalNode , currentNode;
    boolean goalReached = false;
    int step = 0;

    public PathFinder(GamePanel gp) {
        this.gp = gp;
        instatiateNodes();
    }

    public void instatiateNodes() {
        node = new Node[gp.maxWorldRow][gp.maxWorldCol];
        
        for(int i=0; i<gp.maxWorldRow; i++) {
            for(int j=0; j<gp.maxWorldCol; j++) {
                node[i][j] = new Node(i, j);
            }
        }
    }

    public void resetNodes() {
        for(int i=0; i<gp.maxWorldRow; i++) {
            for(int j=0; j<gp.maxWorldCol; j++) {
                node[i][j].open = false;
                node[i][j].checked = false;
                node[i][j].solid = false;
            }
        }

        openList.clear();
        pathList.clear();
        goalReached = false;
        step = 0;
    }

    public void setNodes(int startCol , int startRow , int goalCol , int goalRow , Entity entity) {

        resetNodes();

        //set start and goal nodes
        startNode = node[startRow][startCol];
        currentNode = startNode;
        goalNode = node[goalRow][goalCol];
        openList.add(currentNode);

        for(int i=0; i<gp.maxWorldRow; i++) {
            for(int j=0; j<gp.maxWorldCol; j++) {
                
                int tileNum = gp.tileManager.mapTileNum[i][j];

                if(gp.tileManager.tile[tileNum].walkable == false) {
                    node[i][j].solid = true;
                }

                //check Interactive tiles
                for(int k=0; k<gp.interactiveTile.length; k++) {

                    if(gp.interactiveTile[k] != null && gp.interactiveTile[k].isDestructible == true) {
                        int itCol = gp.interactiveTile[k].worldX/gp.tileSize;
                        int itRow = gp.interactiveTile[k].worldY/gp.tileSize;
                        node[itRow][itCol].solid = true;
                    }
                }

                //setCost
                getCost(node[i][j]);
            }
        }
    }

    public void getCost(Node node) {

        //G cost
        int xDistance = Math.abs(node.col - startNode.col);
        int yDistance = Math.abs(node.row - startNode.row);
        node.gcost = xDistance + yDistance;

        //H cost
        xDistance = Math.abs(node.col - goalNode.col);
        yDistance = Math.abs(node.row - goalNode.row);
        node.hcost = xDistance + yDistance;

        //F Cost
        node.fcost = node.gcost + node.hcost;
    }

    public boolean search() {
        while(goalReached == false) {

            int col = currentNode.col;
            int row = currentNode.row;

            //Check the current Node
            currentNode.checked = true;
            openList.remove(currentNode);

            //OPEN ADJACENT NODES

            //up node
            if(row-1 >= 0) {
                openNode(node[row-1][col]);
            }
            //down node
            if(row+1 < gp.maxWorldRow) {
                openNode(node[row+1][col]);
            }
            //left node
            if(col-1 >= 0) {
                openNode(node[row][col-1]);
            }
            //up node
            if(col+1 < gp.maxWorldCol) {
                openNode(node[row][col+1]);
            }

            //FIND THE BEST NODE

            int bestNodeIndex = 0;
            int bestNodeCost = 9999;

            for(int i=0; i<openList.size(); i++) {
                //check if this nodes f cost is better
                if(openList.get(i).fcost < bestNodeCost) {
                    bestNodeIndex = i;
                    bestNodeCost = openList.get(i).fcost;
                }
                else if(openList.get(i).fcost == bestNodeCost) {
                    if(openList.get(i).gcost < openList.get(bestNodeIndex).gcost) {
                        bestNodeIndex = i;
                    }
                }
            }

            if(openList.size() == 0) {
                break;
            }
            currentNode = openList.get(bestNodeIndex);
            if(currentNode == goalNode) {
                goalReached = true;
                trackThePath();
             }
            step++;
        }
        return goalReached;
    }
    public void openNode(Node node) {
        if(node.open == false && node.checked == false && node.solid == false) {
            node.open = true;
            node.parent = currentNode;
            openList.add(node);
        }
    }

    public void trackThePath() {
        Node current = goalNode;
        while(current != startNode) {
            pathList.add(0 , current); 
            current = current.parent;
        }
    }
}


