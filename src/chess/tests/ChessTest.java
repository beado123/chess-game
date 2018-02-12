package chess.tests;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * @author Yihan Zhang
 * @since 2-10-2018
 */
public class ChessTest extends TestCase {

	/**
	 * This function runs all test cases written in the package
	 */
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(BoardTest.class);
		testSuite.addTestSuite(ChessPieceTest.class);
		testSuite.addTestSuite(KingTest.class);
		testSuite.addTestSuite(QueenTest.class);
		testSuite.addTestSuite(BishopTest.class);
		testSuite.addTestSuite(KnightTest.class);
		testSuite.addTestSuite(RookTest.class);
		testSuite.addTestSuite(PawnTest.class);	
		testSuite.addTestSuite(PrincessTest.class);	
		testSuite.addTestSuite(AmazonTest.class);	
		return testSuite;
	}
}
