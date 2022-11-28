package examples.mockito;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class _XXX_test_arraylist_addAll {

    @Test
    public void test_AllAll() {
        ArrayList<String> list = new ArrayList<>();
        list.addAll(Arrays.asList("1", "2", "3"));
        list.addAll(Arrays.asList("1", "2", "3"));
        list.addAll(Arrays.asList("1", "2", "3"));
        System.out.println(list);
    }
}
