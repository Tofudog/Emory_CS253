

//this class is created to support balancing operations
//whenever a node is inserted/deleted.
public class Balanced<T extends Comparable<T>> extends BinaryTree {

    public boolean zig_zig_right(Node node) {
        boolean ok = true;
        if (!(node.left == null && node.right!=null)) {ok = false;}
        if (!(node.right.left == null && node.right.right != null)) {ok = false;}
        return ok;
    }

    public void insert1(T data) {
        
        Node node = super.insert(data, data);

        //case 1: zig-zig right
        if (node!=null && node.data.equals("g")  && node.parent!=null && this.zig_zig_right(node.parent.parent)) {
            Node temp = node.parent.parent;
            Node A = temp;
            Node B = temp.right;
            Node C = temp.right.right;

            B.parent = A.parent;
            A.parent = B;
            B.left = A;
        }

        //case 2: zig-zig left
        // if (node.right == null && node.left != null &&
        //         node.left.right == null && node.left.left != null) {
        //     Node<T> A = node;
        //     Node<T> B = node.left;
        //     Node<T> C = node.left.left;

        //     B.parent = A.parent;
        //     A.parent = B;
        //     B.right = A;
        // }

        //case 3: zig-zag right-left

        //case 4: zig-zag left-right
        
    }

    public static void main(String[] args) {
        Balanced<String> bst = new Balanced<String>();

        bst.preorder(); System.out.println();

        bst.insert1("d");
        bst.insert1("e");
        bst.insert1("f");
        bst.insert1("g");
        bst.insert1("a");
        
        //bst.preorder();
    }
}