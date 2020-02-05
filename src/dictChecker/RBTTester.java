package dictChecker;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Test;
import java.util.*;



public class RBTTester extends RedBlackTree{

	@Test
	// Test the Red Black Tree
	public void test() {
		RedBlackTree rbt = new RedBlackTree();
		rbt.insert("D");
		rbt.insert("B");
		rbt.insert("A");
		rbt.insert("C");
		rbt.insert("F");
		rbt.insert("E");
		rbt.insert("H");
		rbt.insert("G");
		rbt.insert("I");
		rbt.insert("J");
		assertEquals("DBACFEHGIJ", makeString(rbt));
		String str = "Color: 1, Key:D Parent: \n" + "Color: 1, Key:B Parent: D\n" + "Color: 1, Key:A Parent: B\n"
				+ "Color: 1, Key:C Parent: B\n" + "Color: 1, Key:F Parent: D\n" + "Color: 1, Key:E Parent: F\n"
				+ "Color: 0, Key:H Parent: F\n" + "Color: 1, Key:G Parent: H\n" + "Color: 1, Key:I Parent: H\n"
				+ "Color: 0, Key:J Parent: I\n";
		assertEquals(str, makeStringDetails(rbt));
	}
	
	
	@Test
	//tester for spell checker
	public void SpellCheckerTester() throws IOException {
		
		RedBlackTree rbt= new RedBlackTree();
		long start = System.currentTimeMillis();
		
		rbt =RBDictnoryCreator("dictionary.txt"); //path of the dictionary file
		long end = System.currentTimeMillis();
		

		Scanner poem = new Scanner(new File("poem.txt"));  //path of the poem you want to spell check
		long seaSta = System.currentTimeMillis();
		while(poem.hasNext()) {
			String word = poem.next().toLowerCase();
			System.out.println(word +" : "+ checker(word)); //check checks the given word in the rbt using lookup
			                                                //Incorrect are probably the words that are not on the dictionary
		}
		long seaEnd = System.currentTimeMillis();
		System.out.println();
		System.out.println("time to create a dictionary: " + ((end - start)) +" ms");
		System.out.println("time to check the poem: " + ((seaEnd - seaSta)) +" ms");
		
		
		//rbt.printTree(); //if you want to print the dict :)

		
	}


	
	

	public static String makeString(RedBlackTree t) {
		class MyVisitor implements RedBlackTree.Visitor {
			String result = "";

			public void visit(RedBlackTree.Node n) {
				result = result + n.key;
			}
		}
		MyVisitor v = new MyVisitor();
		t.preOrderVisit(v);
		return v.result;
	}

	public static String makeStringDetails(RedBlackTree t) {
			class MyVisitor implements RedBlackTree.Visitor {
				String result = "";

				public void visit(RedBlackTree.Node n) {
					if (!(n.key).equals(""))
						result = result + "Color: " + n.color + ", Key:" + n.key + " Parent: " + getParent(n) + "\n";
				}
			}
			MyVisitor v = new MyVisitor();
			t.preOrderVisit(v);
			return v.result;
	}


}
