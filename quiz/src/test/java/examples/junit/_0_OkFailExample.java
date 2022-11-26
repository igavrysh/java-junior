package examples.junit;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class _0_OkFailExample {

    @Test
    public void test_size_after_add_one() {
        List<String> list = new ArrayList<>();
        list.add("A");
        if (list.size() != 1) {
            throw new AssertionError();
        }
    }

    @Test
    public void test_size_add_two() {
        List<String> list = new ArrayList<>();
        list.add("A");
        list.add("A");
        if (list.size() != 2) {
            throw new AssertionError();
        }
    }

    @Test
    public void test_get_the_same() {
        List<String> list = new ArrayList<>();
        list.add("A");
        if (!list.get(0).equals("A")) {
            throw new AssertionError();
        }
    }

}
