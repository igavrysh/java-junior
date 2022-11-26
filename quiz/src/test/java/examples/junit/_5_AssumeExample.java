package examples.junit;

import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assume.assumeTrue;

public class _5_AssumeExample {
    @Test
    public void testFailButIgnored() {
        List<String> list = new ArrayList<>();
        assumeTrue(!list.isEmpty());
        assertThat(list.get(0), Matchers.is("A"));
    }
}
