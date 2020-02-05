package dictChecker;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class RedBlackTree<Key extends Comparable<Key>> {
	private static RedBlackTree.Node<String> root;

	public static class Node<Key extends Comparable<Key>> { // changed to static

		Key key;
		Node<String> parent;
		Node<String> leftChild;
		Node<String> rightChild;
		//boolean isRed;
		static final int RED = 0;
		static final int BLACK =1;
		int color;

		public Node(Key data) {
			this.key = data;
			leftChild = null;
			rightChild = null;
		}

		public int compareTo(Node<Key> n) { // this < that <0
			return key.compareTo(n.key); // this > that >0
		}

		public boolean isLeaf() {
			if (this.equals(root) && this.leftChild == null && this.rightChild == null)
				return true;
			if (this.equals(root))
				return false;
			if (this.leftChild == null && this.rightChild == null) {
				return true;
			}
			return false;
		}


	}

	public static boolean isLeaf(RedBlackTree.Node<String> n) {
		if (n.equals(root) && n.leftChild == null && n.rightChild == null)
			return true;
		if (n.equals(root))
			return false;
		if (n.leftChild == null && n.rightChild == null) {
			return true;
		}
		return false;
	}
	
	public interface Visitor<Key extends Comparable<Key>> {
		/**
		 * This method is called at each node.
		 * 
		 * @param n the visited node
		 */
		void visit(Node<Key> n);
	}

	public void visit(Node<Key> n) {
		System.out.println(n.key);
	}

	public void printTree() { // preorder: visit, go left, go right
		RedBlackTree.Node<String> currentNode = root;
		printTree(currentNode);

	}
	public String color(RedBlackTree.Node<String> node) {//returns color of the node
		if(node.color==0) return "RED";
		else return "BLACK";
	}
	
	
	
	
	/*
	 * Recursively calls itself by visiting, going left and going right, preorder
	 * @param node takes the node and prints its in preorder
	 */
	public void printTree(RedBlackTree.Node<String> node) {

		if(node!=null) {
		System.out.println(node.key + "  ->  " + color(node));
		if (node.isLeaf()) {
			return;
		}
		printTree(node.leftChild);
		printTree(node.rightChild);
		}
	}
	
	
	
	public  boolean isLeft(RedBlackTree.Node<String> n) {//retruns true if it is a left node of its parent
		if(lookup(n.key).parent.leftChild==n) {
			return true;
		}
		return false;
	}
	public  boolean isRight(RedBlackTree.Node<String> n) {//returns true if it is a right node of its parent
		if(lookup(n.key).parent.rightChild==n) {
			return true;
		}
		return false;
	}
	

	/*
	 * takes a string and makes it a node and adds it to the binary tree
	 * @param data string that you want to add to the RedBlack tree
	 */
	public void addNode(String data) { 

		RedBlackTree.Node<String> newNode = new RedBlackTree.Node<String>(data);
		RedBlackTree.Node<String> temp = root;
		newNode.color=0;
		if(root==null) {
			root = newNode;
			newNode.color=1;
			newNode.parent=null;
		}
		else {
			while(true) {
				if(data.compareTo(temp.key)<0) {
					if(temp.leftChild==null) {
						temp.leftChild= newNode;
						newNode.parent=temp;
						break;
						}
					else {
						temp=temp.leftChild;
					}
				}
				else if(data.compareTo(temp.key)>=0) {
					if(temp.rightChild==null) {
						temp.rightChild=newNode;
						newNode.parent=temp;
						break;
					} else {
						temp= temp.rightChild;
					}
				}
			}
			fixTree(newNode);
		}
		
	}

	public void insert(String data) { //insert a new node into the redblack tree
		addNode(data);	
	}
	
	
	
	/*
	 * Looks for the node through the redblack tree using recurssion 
	 * @param root the node where you want to start looking
	 * @param k string which you want to look through 
	 */
	public static  RedBlackTree.Node<String> look(RedBlackTree.Node<String> root, String k){
		if(root.key.compareTo(k)==0) {
			return root;
		}
		if(k.compareTo(root.key)<0 && root.leftChild!=null) {
			return look(root.leftChild, k);
		}
		else if(k.compareTo(root.key)>=0 && root.rightChild!=null) {	
			return look(root.rightChild,k);		
		}
		return new RedBlackTree.Node<String>("Not Found");
	}

	public static RedBlackTree.Node<String> lookup(String k) { //searches for the node throught out the tree
		return look(root, k);
	}

	public  RedBlackTree.Node<String> getSibling(RedBlackTree.Node<String> n) { //returns the sibling of the node n if it exists
		if(n.compareTo(lookup(n.key).parent)>=0) {
			return lookup(n.key).parent.leftChild;
		}
		else return lookup(n.key).parent.rightChild;
	}

	public RedBlackTree.Node<String> getAunt(RedBlackTree.Node<String> n) { //returns the aunt of the node n if it exists
		return getSibling(lookup(n.key).parent);
	}

	public static RedBlackTree.Node<String> getGrandparent(RedBlackTree.Node<String> n) { // returns the grandparent of the node n
		return n.parent.parent;
	}
	public static String getParent(RedBlackTree.Node<String> n) { //returns the parent of the node n
		if(n.parent==null) {
			return "";
		}
		return n.parent.key;
	}
	
	
	
	/*
	 * rotates the tree left
	 * @param n rotates the tree from the node n to the left
	 */
	public void rotateLeft(RedBlackTree.Node<String> n) {
		RedBlackTree.Node<String> node = lookup(n.key);
		RedBlackTree.Node<String> temp =node.rightChild;
		node.rightChild= temp.leftChild;
		if (temp.leftChild!=null)
		{
			temp.leftChild.parent=node;
		}
		temp.parent=node.parent;
		if (node.parent==null)
		{
			root=temp;
		}
		else if(node==node.parent.leftChild) {
			node.parent.leftChild=temp;
		}
		else {
			node.parent.rightChild=temp;
		}
		temp.leftChild=node;
		node.parent=temp;	
	}
	
	/*
	 * rotates the tree right
	 * @param n rotates the tree from the node n to the right
	 */
	public void rotateRight(RedBlackTree.Node<String> n) {
		RedBlackTree.Node<String> node = lookup(n.key);
		RedBlackTree.Node<String> temp =node.leftChild;
		node.leftChild = temp.rightChild;
		if (temp.rightChild != null) {
			temp.rightChild.parent=node;
		}
		temp.parent=node.parent;
		if(node.parent==null) {
			root=temp;
		}
		else if(node==node.parent.rightChild) {
			node.parent.rightChild=temp;
		}
		else {
			node.parent.leftChild=temp;
		}
		temp.rightChild=node;
		node.parent=temp;
	}
	
	
	/*
	 * fixes the binary tree according to red black tree principles
	 * @param current takes the nodes and fixes the tree 
	 */
	public void fixTree(RedBlackTree.Node<String> current) {
		RedBlackTree.Node<String> fix = lookup(current.key);
		RedBlackTree.Node<String> parent = fix.parent;		
		if(fix==root) {
			fix.color=1;
			return;
		}
		if(fix.parent.color==1) {
			return;
		}
		if(fix.color==0 && fix.parent.color==0) {
			if(getAunt(lookup(fix.key)) ==null || getAunt(lookup(fix.key)).color==1 ) {
				if(isLeft(fix.parent) == true && isRight(fix)== true) {
					rotateLeft(fix.parent);
					fixTree(parent);
				}
				if(isRight(fix.parent) == true && isLeft(fix)== true) {
					rotateRight(fix.parent);
					fixTree(parent);
				}
				if(isLeft(fix.parent) == true && isLeft(fix)== true) {
					fix.parent.color=1;
					fix.parent.parent.color=0;
					rotateRight(fix.parent.parent);
					return;

				}
				if(isRight(fix.parent) == true && isRight(fix)== true) {
					fix.parent.color=1;
					fix.parent.parent.color=0;
					rotateLeft(fix.parent.parent);
					return;
				}
				
				
			}
			else if(getAunt(lookup(fix.key)).color==0) {
				fix.parent.color=1;
				getAunt(lookup(fix.key)).color=1;
				fix.parent.parent.color=0;
				fixTree(fix.parent.parent);
				
			}
		}
	}

	public boolean isEmpty(RedBlackTree.Node<String> n) {
		if (n.key == null) {
			return true;
		}
		return false;
	}

	public boolean isLeftChild(RedBlackTree.Node<String> parent, RedBlackTree.Node<String> child) {
		if (child.compareTo(parent) < 0) {// child is less than parent
			return true;
		}
		return false;
	}

	public void preOrderVisit(Visitor<String> v) {
		preOrderVisit(root, v);
	}

	private static void preOrderVisit(RedBlackTree.Node<String> n, Visitor<String> v) {
		if (n==null) {
			return;
		}
		v.visit(n);
		preOrderVisit(n.leftChild, v);
		preOrderVisit(n.rightChild, v);
	}
	
	
	
	/*
	 * Checks if the words exists in the redblack tree
	 * @param word the word you want to check in a redblack tree
	 */
	public static String checker(String word) {
		if(lookup(word).key=="Not Found") {
			return "Incorrect/Not found";
		}
		else return "Correct";
	}
	
	
	/*
	 *this method finds the number of lines of a txt file 
	 * @param path location of the file you want to find the total number of lines of 
	 */
	public static int numberOfLines(String path) throws IOException{
		try {
			
			FileReader file = new FileReader(path);
			BufferedReader br = new BufferedReader(file);
			int numberOfLines =0;
			String line;
			while((line = br.readLine())!=null){
				numberOfLines++;
			}
			br.close();
			file.close();
			return numberOfLines;
		}
		catch(FileNotFoundException e) {
			throw new FileNotFoundException("File not found in this location");
		}
	}
	
	
	
	/*
	 * RBDictnoryCreator creates a rb tree using the words
	 * @param path of the file you want to create a rbdictnory of which consists of the words
	 */
	public static RedBlackTree RBDictnoryCreator(String path) throws IOException {
		FileReader file = new FileReader(path);
		BufferedReader br = new BufferedReader(file);
		RedBlackTree dict = new RedBlackTree();
		int line = numberOfLines(path);
		for(int i=0;i<line;i++) {
			String word = br.readLine();
			dict.insert(word);
		}
		br.close();
		file.close();
		return dict;
	}
	
	
	
	public static void main(String[] args) throws IOException {
		RedBlackTree rbt= new RedBlackTree();
		//rbt =RBDictnoryCreator("C:\\Users\\ximan\\Desktop\\dictionary.txt");
		rbt.insert("abba");
		rbt.insert("cow");
		rbt.insert("moo");
		rbt.insert("ur");
		rbt.insert("feast");
		rbt.insert("fat");
		rbt.insert("gogog");

		rbt.printTree();          //printTree() prints the tree in pre-order;
		System.out.println();
		System.out.println(checker("azzxe"));   //checker checks if the word is in the rbt or not
		System.out.println(checker("feast"));
		
	}
}

	
