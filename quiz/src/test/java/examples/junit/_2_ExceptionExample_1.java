package examples.junit;

import org.junit.Test;

public class _2_ExceptionExample_1 {

    @Test(expected = RuntimeException.class)
    public void test_Ok() {
        throw new RuntimeException();
    }

    @Test(expected = RuntimeException.class)
    public void test_Ok_subclass() {
        throw new NullPointerException();
    }

    @Test
    public void test_Fail_notExpectedException() {
        throw new RuntimeException();
    }

    @Test(expected = RuntimeException.class)
    public void test_Fail_notExistedException() {
    }

    @Test(expected = RuntimeException.class)
    public void test_Fail_anotherException() {
        throw new Error();
    }

}
