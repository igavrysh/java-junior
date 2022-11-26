package examples.junit;

import org.junit.Ignore;
import org.junit.Test;

public class _3_IgnoreExample {
    @Test
    public void testOk() {
        System.out.println("The test is Ok and done");
    }

    @Ignore
    @Test
    public void testFailButIgnored() {
        System.out.println("This test is Fail but ignored");
        throw new RuntimeException();
    }
}
