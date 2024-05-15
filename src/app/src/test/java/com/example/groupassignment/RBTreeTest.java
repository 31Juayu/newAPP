package com.example.groupassignment;
import org.junit.Test;

import static org.junit.Assert.*;

import com.example.groupassignment.utility.RedBlackTree;

import java.util.ArrayList;

public class RBTreeTest {
    //Test put and generate normal tree
    @Test
    public void redBlackTreeTestSimple() {
        RedBlackTree<Integer> testTree = new RedBlackTree();
        int[] keysAndValues = {1,2,3,4,5,6};
        for (int keysAndValue : keysAndValues) {
            testTree.put(keysAndValue,keysAndValue);
        }
        ArrayList<ArrayList<Integer>> arraylist = testTree.preOrder();
        assertEquals("[[2, 2], [1, 1], [4, 4], [3, 3], [5, 5], [6, 6]]",arraylist.toString());
    }
    //Test the complex generate with the delete function
    @Test
    public void redBlackTreeTestComplex(){
        RedBlackTree<Integer> testTree = new RedBlackTree();
        int[] keysAndValues = {1,2,3,4,5,6,7,8,9};
        for (int keysAndValue : keysAndValues) {
            testTree.put(keysAndValue,keysAndValue);
        }
        testTree.remove(6);
        testTree.remove(5);
        testTree.remove(2);
        ArrayList<ArrayList<Integer>> arraylist = testTree.preOrder();
        assertEquals("[[4, 4], [3, 3], [1, 1], [7, 7], [8, 8], [9, 9]]",arraylist.toString());
    }
    //Use the key not equal to the value
    @Test
    public void redBlackTreeDifferentValues(){
        RedBlackTree<Integer> testTree = new RedBlackTree();
        int[] keysAndValues = {1,2,3,4,5,6,7,8,9};
        for (int keysAndValue : keysAndValues) {
            testTree.put(keysAndValue,keysAndValue * 2);
        }
        testTree.remove(6);
        testTree.remove(5);
        testTree.remove(2);
        ArrayList<ArrayList<Integer>> arraylist = testTree.preOrder();
        assertEquals("[[4, 8], [3, 6], [1, 2], [7, 14], [8, 16], [9, 18]]",arraylist.toString());
    }
    // Test insert accompany with delete and use the large testing list
    @Test
    public void redBlackTreeTestInOrderSimple(){
        RedBlackTree<Integer> testTree = new RedBlackTree();
        int[] keysAndValues = new int[10];
        for (int i = 0; i < keysAndValues.length; i++) {
            keysAndValues[i] = i+1;
        }
        for (int i = 0; i < keysAndValues.length; i++) {
            testTree.put(keysAndValues[i],keysAndValues[i] * 2);
            if ((i+1) % 3 == 0){
                testTree.remove(keysAndValues[i-1]);
            }
        }
        ArrayList<ArrayList<Integer>> arraylist = testTree.preOrder();
        assertEquals("[[3, 6], [1, 2], [6, 12], [4, 8], [9, 18], [7, 14], [10, 20]]",arraylist.toString());
    }
    @Test
    public void redBlackTreeTestInOrderComplex(){
        RedBlackTree<Integer> testTree = new RedBlackTree();
        int[] keysAndValues = new int[20];
        for (int i = 0; i < keysAndValues.length; i++) {
            keysAndValues[i] = i+1;
        }
        for (int i = 0; i < keysAndValues.length; i++) {
            testTree.put(keysAndValues[i],keysAndValues[i] * 2);
            if ((i+1) % 3 == 0){
                testTree.remove(keysAndValues[i-1]);
            }
        }
        ArrayList<ArrayList<Integer>> arraylist = testTree.preOrder();
        assertEquals("[[6, 12], [3, 6], [1, 2], [4, 8], [12, 24], [9, 18], [7, 14], [10, 20], [15, 30], [13, 26], [18, 36], [16, 32], [19, 38], [20, 40]]",arraylist.toString());
    }
}
