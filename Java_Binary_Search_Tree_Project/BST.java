

public class BST<E extends Comparable<E>> {    
	private Node<E> root;
		
    public BST(){        
    	root = null;    
    	}
    public Node<E> getRoot(){        
    	return root;     
    	}
    public void insert(E data){
        // Find the right spot in the tree for the new node        
    	// Make sure to check if anything is in the tree        
    	// Hint: if a node n is null, calling n.getData() will cause an error
    	Node<E> newNode = new Node<E>(data);
    	if (getRoot() == null){
    		root = newNode;
    	}else{
    		Node<E> temp = getRoot();
    		boolean done = false;
    		
    		while(done == false){
    			if((newNode.getData().compareTo(temp.getData()))<0){
    				if(temp.getLeftChild() == null){
    					temp.setLeftChild(newNode);
    					newNode.setParent(temp);
    					done = true;
    				}else{
    					temp = temp.getLeftChild();
    				}
    				
    			}else{
    				if(temp.getRightChild() == null){
    					temp.setRightChild(newNode);
    					newNode.setParent(temp);
    					done = true;
    				}else{
    				temp = temp.getRightChild();
    				}
    			}
    		}
    	}
    }     
    public Node<E> find(E data){
    	// Search the tree for a node whose data field is equal to data 
		boolean found = false;
    	Node<E> temp = getRoot();
		while(found == false){
    		if(temp == null){
    			found = true;
    			return null;
    		}
			if (data == temp.getData()){
    			found = true;
    			return temp;
    		}else if(data.compareTo(temp.getData())<0){
    			temp = temp.getLeftChild();
    		}else if(data.compareTo(temp.getData())>0){
    			temp = temp.getRightChild();
			}
    	}  
		return null;
    }   
    
    public Node<E> findMin(Node<E> node) {
        Node<E> current = node;
 
        /* loop down to find the leftmost leaf */
        while (current.getLeftChild() != null) {
            current = current.getLeftChild();
        }
        return current;
    }
    public void delete(E data){
        // Find the right node to be deleted
        // If said node has no children, simply remove it by setting its parent to point to null instead of it.         // If said node has one child, delete it by making its parent point to its child.         // If said node has two children, then replace it with its successor,         //          and remove the successor from its previous location in the tree.         // The successor of a node is the left-most node in the node's right subtree.         // If the value specified by delete does not exist in the tree, then the structure is left unchanged.
        // Hint: You may want to implement a findMin() method to find the successor when there are two children
    	Node<E> dataNode = find(data);
    	if( dataNode != null){
    		if(dataNode == getRoot()){
    			if((dataNode.getRightChild() == null) &&(dataNode.getLeftChild() == null)){
    				root = null;
    			}else if(dataNode.getRightChild()==null && (dataNode.getLeftChild() != null)){
    				root = dataNode.getLeftChild();
    				root.setParent(null);
    			}else if(dataNode.getLeftChild()==null && (dataNode.getRightChild()!=null)){
    				root = dataNode.getRightChild();
    				root.setParent(null);
    			}else if((dataNode.getLeftChild()!=null) && (dataNode.getRightChild()!=null)){
    				Node<E> successor = findMin(dataNode.getRightChild());
    				successor.getParent().setLeftChild(null);
        			successor.setParent(null);
        			successor.setLeftChild(dataNode.getLeftChild());
        			successor.setRightChild(dataNode.getRightChild());
        			successor.getLeftChild().setParent(successor);
        			successor.getRightChild().setParent(successor);
        			root = successor;
        			
    			}
    			
    		}else{
	    		Node<E> parent = dataNode.getParent();
	    		// Case1 == has no children
	        	if((dataNode.getRightChild() == null) &&(dataNode.getLeftChild() == null)){
	        		if(parent.getLeftChild() == dataNode){
	        			parent.setLeftChild(null);
	        		}else {
	        			parent.setRightChild(null);
	        		}
	        	// Case2 == has a left child
	        	}else if(dataNode.getRightChild()==null && (dataNode.getLeftChild() != null)){
	        		if(parent.getLeftChild() == dataNode){
	        			parent.setLeftChild(dataNode.getLeftChild());
	        			dataNode.getLeftChild().setParent(parent);
	        		}else {
	        			parent.setRightChild(dataNode.getLeftChild());
	        			dataNode.getLeftChild().setParent(parent);
	        		}
	        	// Case3 == has a right child
	        	}else if(dataNode.getLeftChild()==null && (dataNode.getRightChild()!=null)){
	        		if(parent.getRightChild() == dataNode){
	        			parent.setRightChild(dataNode.getRightChild());
	        			dataNode.getRightChild().setParent(parent);
	        		}else {
	        			parent.setLeftChild(dataNode.getRightChild());
	        			dataNode.getRightChild().setParent(parent);
	        		}
	        		
	        	// Case4 == has two child
	        	}else if((dataNode.getLeftChild()!=null) && (dataNode.getRightChild()!=null)){
	        		Node<E> successor = findMin(dataNode.getRightChild());
	        		if(parent.getRightChild().getData() == dataNode.getData()){
	        			successor.getParent().setLeftChild(null);
	        			parent.setRightChild(successor);
	        			successor.setParent(parent);
	        			successor.setLeftChild(dataNode.getLeftChild());
	        			successor.setRightChild(dataNode.getRightChild());
	        			successor.getLeftChild().setParent(successor);
	        			successor.getRightChild().setParent(successor);
	        		}else {
	        			successor.getParent().setLeftChild(null);
	        			parent.setRightChild(successor);
	        			successor.setParent(parent);
	        			successor.setLeftChild(dataNode.getLeftChild());
	        			successor.setRightChild(dataNode.getRightChild());
	        			successor.getLeftChild().setParent(successor);
	        			successor.getRightChild().setParent(successor);
	        		}
	        }
        	}
		}
    	
    }
    public void traverse(String order, Node<E> top) {       
    	if (top != null){           
    		switch (order) {               
    		case "preorder":                    
    			if(top != null){
    				System.out.printf("%d",top.getData());   
    				traverse("preorder", top.getLeftChild());
    				traverse("preorder",top.getRightChild());
    			}
    			break;                
    		case "inorder": 
    			if(top != null){
    				traverse("inorder", top.getLeftChild());
    				System.out.printf("%d",top.getData());   
    				traverse("inorder",top.getRightChild());
    			}
                break;               
    		case "postorder":                    
    			if(top != null){
    				traverse("postorder", top.getLeftChild());
    				traverse("postorder",top.getRightChild());
    				System.out.printf("%d",top.getData());   
    				
    			}                    
    			break;      	
    		}        
                    	
    	}    
                    	
    }
}