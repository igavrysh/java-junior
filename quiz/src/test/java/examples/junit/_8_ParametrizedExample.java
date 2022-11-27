package examples.junit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runners.Parameterized.Parameter;

import static java.lang.Math.abs;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(Parameterized.class)
public class _8_ParametrizedExample {

    @Parameters
    public static Iterable<Object[]> data() {
        return asList(new Object[][] {{-3,3}, {-2,2}, {-1,1}, {0,0}, {1,1}, {2,2}});
    }

    @Parameter(0)
    public int input;

    @Parameter(1)
    public int expectedResult;

    @Test
    public void test() {
        assertThat(abs(input), is(expectedResult));
    }
}
