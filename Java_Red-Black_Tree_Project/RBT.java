public class RBT<E extends Comparable<E>> {
    
	private Node<E> root;
	private Node<E> nil;

    public RBT(){
    	// Creates T.nil
        nil = new Node<E>(null);
        nil.setColor('B');
        root = nil; // sets root to a nil
        root.setLeftChild(nil);
        root.setRightChild(nil);
        root.setParent(nil);
    }

    public Node<E> getRoot(){
        return root;
    }
    public Node<E> getNil(){
        return nil;
    }

    public void insert(E data){
        // Performs a regular insert
        // Checks to make sure the tree remains an RBT tree
        Node<E> z = new Node<E>(data);
        Node<E> y = nil;
        Node<E> x = root;

        while(x != nil){
            y = x;
            if(z.getData().compareTo(x.getData()) < 0){
                x = x.getLeftChild();
            }
            else{
                x = x.getRightChild();
            }
        }
        z.setParent(y);
        if(y == nil){
            root = z;
        }
        else if(z.getData().compareTo(y.getData()) < 0){
            y.setLeftChild(z);
        }
        else{
            y.setRightChild(z);
        }
        z.setLeftChild(nil);
        z.setRightChild(nil);
        z.setColor('R');
        insertFixup(z);


    }

    public void delete(E data){
    	// Removes element from tree
    	// then Fixes the tree to hold
    	// the Red-black properties 
        Node<E> z = search(data);
        if(z == null){
            System.out.println("Not Found");
            return;
        }
        Node<E> y = z;
        Node<E> x;
        char Color = y.getColor();

        if(z.getLeftChild() == nil){
            x = z.getRightChild();
            transplant(z, z.getRightChild());
        }
        else if(z.getRightChild() == nil){
            x = z.getLeftChild();
            transplant(z, z.getLeftChild());
        }
        else{
            y = getMin(z.getRightChild());
            Color = y.getColor();
            x = y.getRightChild();
            if(y.getParent() == z){
                x.setParent(y);
            }
            else{
                transplant(y, y.getRightChild());
                y.setRightChild(z.getRightChild());
                y.getRightChild().setParent(y);
            }
            transplant(z,y);
            y.setLeftChild(z.getLeftChild());
            y.getLeftChild().setParent(y);
            y.setColor(z.getColor());

        }
        if(Color == 'B'){
            deleteFix(x);
        }

    }
 // Fixes the red black tree after the insert is used
    public void insertFixup(Node<E> z){
        while(z.getParent().getColor() == 'R'){
            if(z.getParent() == z.getParent().getParent().getLeftChild()){
                Node<E> y = z.getParent().getParent().getRightChild();
                if(y.getColor() == 'R'){
                    z.getParent().setColor('B');
                    y.setColor('B');
                    z.getParent().getParent().setColor('R');
                    z = z.getParent().getParent();

                }
                else{
                    if(z == z.getParent().getRightChild()){
                    z = z.getParent();
                    leftRotate(z);
                }
                z.getParent().setColor('B');
                z.getParent().getParent().setColor('R');
                rightRotate(z.getParent().getParent());
                }
            }
            else{
                Node<E> y = z.getParent().getParent().getLeftChild();
                if(y.getColor() == 'R'){
                    z.getParent().setColor('B');
                    y.setColor('B');
                    z.getParent().getParent().setColor('R');
                    z = z.getParent().getParent();
                }
                else {
                    if(z == z.getParent().getLeftChild()){
                        z = z.getParent();
                        rightRotate(z);
                    }
                    z.getParent().setColor('B');
                    z.getParent().getParent().setColor('R');
                    leftRotate(z.getParent().getParent());
                    }
                }
            }
        getRoot().setColor('B');
    }

    public void deleteFix(Node<E> x){
    	// Fixes the tree after a delete
    	//
        while(x != root && x.getColor() == 'B'){
            if(x == x.getParent().getLeftChild()){
                Node<E> w = x.getParent().getRightChild();
                if(w.getColor() == 'R'){
                    w.setColor('B');
                    x.getParent().setColor('R');
                    leftRotate(x.getParent());
                    w = x.getParent().getRightChild();
                }
                if(w.getLeftChild().getColor() == 'B' && w.getRightChild().getColor() == 'B'){
                    w.setColor('R');
                    x = x.getParent();
                }
                else{
                    if(w.getRightChild().getColor() == 'B'){
                    w.getLeftChild().setColor('B');
                    w.setColor('R');
                    rightRotate(w);
                    w = x.getParent().getRightChild();
                }
                w.setColor(x.getParent().getColor());
                x.getParent().setColor('B');
                w.getRightChild().setColor('B');
                leftRotate(x.getParent().getParent());
                x = root;
            }
            }
            else{
                Node<E> w = x.getParent().getLeftChild();
                if(w.getColor() == 'R'){
                    w.setColor('B');
                    x.getParent().setColor('R');
                    rightRotate(x.getParent());
                    w = x.getParent().getLeftChild();
                }
                if(w.getRightChild().getColor() == 'B' && w.getLeftChild().getColor() == 'B'){
                    w.setColor('R');
                    x = x.getParent();
                }
                else {
                    if(w.getLeftChild().getColor() == 'B'){
                    w.getRightChild().setColor('B');
                    w.setColor('R');
                    leftRotate(w);
                    w = x.getParent().getLeftChild();
                }
                w.setColor(x.getParent().getColor());
                x.getParent().setColor('B');
                w.getLeftChild().setColor('B');
                rightRotate(x.getParent().getParent());
                x = root;
                }
            }
        }
        x.setColor('B');
    }

    

    public void rightRotate(Node<E> y){

        /*
        If x is the root of the tree to rotate with left child subtree T1 and right child y,
        where T2 and T3 are the left and right children of y:
            x becomes left child of y and T3 as its right child of y
            T1 becomes left child of x and T2 becomes right child of x
        */
        Node<E> x = y.getLeftChild();
        y.setLeftChild(x.getRightChild());
        if(x.getRightChild() != nil){
            x.getRightChild().setParent(y);
        }
        x.setParent(y.getParent());
        if(y.getParent() == nil ){
            root = x;
        }
        else if(y == y.getParent().getRightChild()){
            y.getParent().setRightChild(x);
        }
        else {
            y.getParent().setLeftChild(x);
        }
        x.setRightChild(y);
        y.setParent(x);
    }

    public void leftRotate(Node<E> x){

        /*
        If y is the root of the tree to rotate with right child subtree T3 and left child x,
        where T1 and T2 are the left and right children of x:
            y becomes right child of x and T1 as its left child of x
            T2 becomes left child of y and T3 becomes right child of y
        */
        Node<E> y = x.getRightChild();
        x.setRightChild(y.getLeftChild());
        if(y.getLeftChild() != nil){
            y.getLeftChild().setParent(x);
        }
        y.setParent(x.getParent());
        if(x.getParent() == nil ){
            root = y;
        }
        else if(x == x.getParent().getLeftChild()){
            x.getParent().setLeftChild(y);
        }
        else {
            x.getParent().setRightChild(y);

        }
        y.setLeftChild(x);
        x.setParent(y);
    }


    // HINT: You may want to create extra methods such as fixDelete or fixInsert
    public Node<E> search(E data){
        boolean done = false;
        Node<E> temp = root;
        while(!done){
            if (temp == nil){
                return null;
            }
            if(temp.getData().compareTo(data) == 0){
                done = true;
            } else if (temp.getData().compareTo(data) > 0){
                temp = temp.getLeftChild();
            } else if (temp.getData().compareTo(data) < 0){
                temp = temp.getRightChild();
            }
        }
        return temp;
    }
    
    // Transplants Nodes 
    public void transplant(Node<E> u, Node<E> v){
        if(u.getParent() == nil){
            root = v;
        }
        else if(u == u.getParent().getLeftChild()){
            u.getParent().setLeftChild(v);
        }
        else{
            u.getParent().setRightChild(v);
        }
        v.setParent(u.getParent());
    }

    public void traverse(String order, Node<E> top) {
        // Preform a preorder traversal of the tree
        if (top != nil){
        	if (top.getData() != nil) {
                System.out.print(top.getData().toString() + " ");
                traverse("preorder", top.getLeftChild());
                traverse("preorder", top.getRightChild());
            }
        }
    }
    // Finds the min of the input tree
    public Node<E> getMin(Node<E> top){
        boolean done = false;
        Node<E> temp = top;
        while(!done) {
            if (temp.getLeftChild() == nil) {
                done = true;
            } else {
                temp = temp.getLeftChild();
            }
        }
        return temp;
    }
    // ##################################################################
    // EXTRA CREDIT 
    //
    public boolean isRBT(Node<E> node){
    	root = node;
    	boolean result = false;
    	if (node == null){
    		result = false;
    	}
    	result = isRBT(node.getLeftChild()) && isRBT(node.getRightChild());
    	
    	return result; 
    }
}  