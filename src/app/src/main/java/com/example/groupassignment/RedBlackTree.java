package com.example.groupassignment;

public class RedBlackTree {
    enum Color{
        RED,BLACK
    }

    private Node root;

    private static class Node{
        int key;
        int value;
        Node left;
        Node right;

        Node parent;

        Color color = Color.RED;//新增的默认是红色

        //判断是否是左孩子
        boolean isLeftChild(){
            return parent != null && parent.left == this;
        }

        //返回叔叔节点（uncle）:根parent节点平辈的节点
        Node uncle(){
            //有parent且有祖辈的才可能有叔叔节点
            if(parent == null || parent.parent == null){
                return null;
            }
            if(parent.isLeftChild()){
                return parent.parent.right;
            }else{
                return  parent.parent.left;
            }
        }

        //返回兄弟节点
        Node sibling(){
            if (parent == null){
                return null;
            }
            if(this.isLeftChild()){
                return this.right;
            }else{
                return this.left;
            }
        }

        public Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    //判断红，黑
    boolean isRed(Node node){
        //rule 2:所有的null为黑色
        return node != null && node.color == Color.RED;
    }

    boolean isBlack(Node node){
        return !isRed(node);
    }

    private void rightRotate(Node pink){
        Node parent = pink.parent;
        Node yellow = pink.left;
        Node green = yellow.right;
        //pink.parent.left = yellow;
        if(green != null){
            green.parent = pink;
        }
        yellow.right = pink;
        //处理父子关系
        yellow.parent = pink.parent;
        pink.left = green;
        //处理父子关系
        pink.parent = yellow;

        //与上层建立链接
        if (parent == null){
            root = yellow;
        }else if(parent.left == pink){
            parent.left = yellow;
        } else {
            parent.right = yellow;
        }
    }

    private void leftRotate(Node pink){
        Node parent = pink.parent;
        Node yellow = pink.right;
        Node green = yellow.left;
        //pink.parent.right = yellow;
        if(green != null){
            green.parent = pink;
        }
        yellow.left = pink;
        //处理父子关系
        yellow.parent = pink.parent;
        pink.right = green;
        //处理父子关系
        pink.parent = yellow;

        //与上层建立链接
        if (parent == null){
            root = yellow;
        }else if(parent.right == pink){
            parent.right = yellow;
        } else {
            parent.left = yellow;
        }
    }

    //红黑树的插入
    public void put(int key, int value){
        Node p = root;
        Node parent = null;//由于null和p是不同的空间，所以要有parent去记录。不能直接用p，在链接的时候
        while(p != null){
            parent = p;
            if(key < p.key){
                p = p.left;
            } else if (key > p.key) {
                p = p.right;
            }else{
                p.value = value;
                return;
            }
        }

        Node inserted = new Node(key,value);
        if(parent == null){
            root = inserted;
        } else if (key < parent.key) {
            parent.left = inserted;
            inserted.parent = parent;//修改parent属性
        }else{
            parent.right = inserted;
            inserted.parent = parent;
        }
        fixRedRed(inserted);
    }

    //默认插入的都是红色的，然后rule 3:红色的不相邻，所以要修改
    void fixRedRed(Node x){
        //case 1:根节点为红色
        if (x == root){
            x.color = Color.BLACK;
            return;
        }

        //case 2:若父亲为黑色，树的性质不变，不调整
        if(isBlack(x.parent)){
            return;
        }

        //case 3:父亲为红色，且叔叔为红色
        //把父亲和叔叔都变成黑色，再把祖父变成红色。把祖父当成新插入的节点，递归调用直到根节点
        Node parent = x.parent;
        Node uncle = x.uncle();
        Node grandparent = parent.parent;
        if(isRed(uncle)){
            parent.color = Color.BLACK;
            uncle.color = Color.BLACK;
            grandparent.color = Color.RED;
            fixRedRed(grandparent);
            return;
        }

        //case 4:父亲为红色，且叔叔为黑色
        //父亲为左，插入节点也是左，LL，父亲变黑，祖父变红，祖父右旋转（祖父为轴旋转下去）
        if(parent.isLeftChild() && x.isLeftChild()){
            parent.color = Color.BLACK;
            grandparent.color = Color.RED;
            rightRotate(grandparent);
        }
        //父亲为左，插入节点是右，LR，父亲先左旋，就成了LL(插入节点成了新父亲，变黑，祖父变红，祖父右旋)
        else if (parent.isLeftChild() && !x.isLeftChild()) {
            leftRotate(parent);
            x.color = Color.BLACK;
            grandparent.color = Color.RED;
            rightRotate(grandparent);
        }
        //父亲为右，插入节点是右，RR，父亲变黑，祖父变红，祖父左旋转（祖父为轴旋转下去）
        else if (!parent.isLeftChild() && !x.isLeftChild()) {
            parent.color = Color.BLACK;
            grandparent.color = Color.RED;
            leftRotate(grandparent);
        }
        //父亲为右，插入节点是左，RL,父亲先右旋，就成了RR
        else{
            rightRotate(parent);
            x.color = Color.BLACK;
            grandparent.color = Color.RED;
            leftRotate(grandparent);
        }

    }

    //红黑树的删除
    public void remove(int key){
        Node deleted = find(key);
        if(deleted == null){
            return;
        }
        doRemove(deleted);
    }

    //删除的节点是黑色，剩下的节点也是黑色
    //deleted node and the remaining node are black
    //case 3-5
    //function to rebalanced
    private void fixDoubleBlack(Node x){
        if(x == root){
            return;
        }
        Node parent = x.parent;
        Node sibling = x.sibling();
        //case 3: 删除的节点是黑色，剩下的节点也是黑色
        // 被调整的节点的兄弟为红色，同时其兄弟的子节点为黑色
        //方法：1.被调整的是左孩子，左旋父亲。反之右旋
        //1.If the adjusted node is a left child, left rotate the parent node;
        // if it's a right child, right rotate the parent node.
        //2.原父亲变红，原兄弟变黑
        //2.The original parent becomes red,
        // and the original sibling becomes black.
        if(isRed(sibling)){
            if(x.isLeftChild()){
                leftRotate(parent);
            }else{
                rightRotate(parent);
            }
            parent.color = Color.RED;
            sibling.color = Color.BLACK;
            fixDoubleBlack(x);
            return;
        }

        if(sibling != null){
            //case 4:
            //Turn the sibling red.

            //If the parent is red, turn the parent black.

            //If the parent is black, treat the parent as the adjusted node
            // and recurse.
            if(isBlack(sibling.left) && isBlack(sibling.right)){
                sibling.color = Color.RED;
                if(isRed(parent)){
                    parent.color = Color.BLACK;
                } else{
                    fixDoubleBlack(parent);
                }
            }
            //case 5:
            else{
                //LL
                //If the sibling is the left child
                // and the sibling's left node is red
                //method: 1. right rotate the parent node,
                //2. make the original left node of the sibling black,
                //3. make the original sibling node the same color as the original parent node,
                //4. and make the parent node black
                if(sibling.isLeftChild()&& isRed(sibling.left)) {
                    rightRotate(parent);
                    sibling.left.color = Color.BLACK;
                    sibling.color = parent.color;
                    parent.color = Color.BLACK;
                }
                //LR
                //If the sibling is the left child
                // and the sibling's right node is red
                //method: 1.left rotate the sibling node,
                //2.right rotate the parent node,
                //3.make the original right node of the sibling node the same color as the original parent node,
                //4.and make the parent node black.
                else if (sibling.isLeftChild() && isRed(sibling.right)) {
                    //prevent null condition
                    sibling.right.color = parent.color;

                    leftRotate(sibling);
                    rightRotate(parent);
                    parent.color = Color.BLACK;
                }

                //RL
                else if (!sibling.isLeftChild() && isRed(sibling.left)) {
                    sibling.left.color = parent.color;
                    rightRotate(sibling);
                    leftRotate(parent);
                    parent.color = Color.BLACK;
                }
                //RR
                else{
                    leftRotate(parent);
                    sibling.right.color = Color.BLACK;
                    sibling.color = parent.color;
                    parent.color = Color.BLACK;
                }

            }
        } else {
            fixDoubleBlack(parent);
        }
    }


    private void doRemove(Node deleted){
        Node replaced = findReplaced(deleted);
        Node parent = deleted.parent;
        //被删除节点没孩子,先调整平衡再删除
        //deleted node has no children
        if(replaced == null){
            //case 1________________________________________
            if(deleted == root){
                root = null;
            }
            //case 2-5__________________________________________
            else{
                //如果删的是黑色节点，那么必须重新使树平衡
                //如果删除的是红色节点，那么不需要考虑平衡
                if(isBlack(deleted)){
                    //If the deleted node is black, then the tree must be rebalanced
                    fixDoubleBlack(deleted);
                }else{
                    //If the deleted node is red, then there's no need to consider balancing
                    //do nothing
                }

                //delete logic
                if(deleted.isLeftChild()){
                    parent.left = null;
                }else {
                    parent.right = null;
                }
                deleted.parent = null;
            }
            return;
        }

        //被删除节点有一个孩子，先调删除再调整平衡
        //deleted node has one children
        if(deleted.left == null || deleted.right == null){

            if(deleted == root){
                //case 1_______________________________________
                //用剩余节点替换掉根节点的key和value，根节点的孩子=null
                // Replace the key and value of the root node with
                // the remaining node, and set the root node's child to null.
                root.key = replaced.key;
                root.value = replaced.value;
                root.left = null;
                root.right = null;
            }
            //case 2-5________________________________________
            else {
                //delete logic
                if(deleted.isLeftChild()){
                    parent.left = replaced;
                }else {
                    parent.right = replaced;
                }
                replaced.parent = parent;
                deleted.left = null;
                deleted.right = null;
                deleted.parent = null;

                //如果删的是黑色节点，那么必须重新使树平衡
                //如果删除的是红色节点，那么不需要考虑平衡
                if(!isBlack(deleted)){
                    //do nothing

                } else if(isBlack(deleted) && isBlack(replaced)){
                    //
                    fixDoubleBlack(replaced);
                } else {
                    //case 2:删除的是黑色，剩下的是红色：剩下的红节点变黑
                    //case 2:The deleted node is black, and the remaining node is red
                    //method: change remaining node to black
                    replaced.color = Color.BLACK;
                }
            }
            return;
        }
        //________________________________________________________
        //pro: 被删除节点有两个孩子=>只有一个孩子/没有孩子的删除节点
        //Pros: The deleted node has two children
        // => The deleted node has only one child / no children
        //case 0:
        //switch key
        int t = deleted.key;
        deleted.key = replaced.key;
        replaced.key = t;

        //switch value
        int v = deleted.value;
        deleted.value = replaced.value;
        replaced.value = v;
        doRemove(replaced);
    }

    //查找删除的节点
    Node find(int key){
        Node p = root;
        while(p != null){
            if(key < p.key){
                p = p.left;
            } else if (key > p.key) {
                p = p.right;
            } else {
                return p;
            }
        }
        return null;
    }

    //查找被删除节点的剩余节点
    Node findReplaced(Node deleted){
        //被删除节点的左右孩子都是null
        if(deleted.left == null && deleted.right == null){
            return null;
        }
        //若只有一个孩子，返回那个孩子
        if(deleted.left == null){
            return deleted.right;
        }
        if (deleted.right == null){
            return deleted.left;
        }
        //若有两个孩子，则返回后继节点（比它大的当中最小的）
        //successor Node
        Node s = deleted.right;
        while (s.left != null){
            s = s.left;
        }
        return s;
    }


}
