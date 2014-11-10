/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.tree;

import java.util.ArrayList;
import pct.PCTNode;

/**
 *
 * @author djordje
 */
public class TreeUtils {

    public static int numberOfLeafs(PCTNode rootNode) {
        if (rootNode == null) {
            return 0;
        }
        if (rootNode.getSuccessors().isEmpty()) {
            return 1;
        }
        return numberOfLeafs(rootNode.getSuccessors().get(0)) + numberOfLeafs(rootNode.getSuccessors().get(1));
    }

    public static void GetAllLeafs(ArrayList<PCTNode> leafs, PCTNode rootNode) {
        if (rootNode == null) {
            return;
        }
        if (rootNode.getSuccessors().isEmpty()) {
            leafs.add(rootNode);
            return;
        }
        GetAllLeafs(leafs, rootNode.getSuccessors().get(0));
        GetAllLeafs(leafs, rootNode.getSuccessors().get(1));
    }

}
