/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iterativedfsmaze;

/**
 *
 * @author user
 */
public class Node 
{
    Node leftChild,rightChild,topChild,bottomChild;
    int x,y;
    int level;

    public Node(Node leftChild, Node rightChild,Node topChild,Node bottomChild, int x, int y,int level) {
        this.leftChild = leftChild;
        this.rightChild = rightChild;
        this.topChild=topChild;
        this.bottomChild=bottomChild;
        this.x = x;
        this.y = y;
    }

    public Node(int x, int y,int level) {
        this.x = x;
        this.y = y;
    }

    public Node getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(Node leftChild) {
        this.leftChild = leftChild;
    }

    public Node getRightChild() {
        return rightChild;
    }

    public void setRightChild(Node rightChild) {
        this.rightChild = rightChild;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Node getTopChild() {
        return topChild;
    }

    public void setTopChild(Node topChild) {
        this.topChild = topChild;
    }

    public Node getBottomChild() {
        return bottomChild;
    }

    public void setBottomChild(Node bottomChild) {
        this.bottomChild = bottomChild;
    }
    
}
