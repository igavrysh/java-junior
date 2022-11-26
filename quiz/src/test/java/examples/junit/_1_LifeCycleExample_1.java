package examples.junit;

import org.junit.*;

public class _1_LifeCycleExample_1 {

    public _1_LifeCycleExample_1() {
        System.out.println("constructor()");
    }

    @BeforeClass
    public static void setUpClass() {
        System.out.println("BeforeClass");
    }

    @Before
    public void setUp() {
        System.out.println("Before");
    }

    @Test
    public void test0() {
        System.out.println("test-0");
    }

    @After
    public void testDown() {
        System.out.println("After");
    }

    @AfterClass
    public static void tearDownClass() {
        System.out.println("AfterClass");
    }
}
