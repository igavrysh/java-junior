package examples.junit;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.Collection;
import java.util.List;

import static java.lang.Math.sin;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


public class _4_HamcrestExample_1 {

    private static final double ERROR = 0.01;

    @Test
    public void test_float_short() {
        assertThat(sin(100.0), is(closeTo(-0.5, ERROR)));
    }

    @Test
    public void test_float_long() {
        assertThat(sin(100.0), Matchers.is(Matchers.closeTo(-0.5, ERROR)));
    }

    @Test
    public void test_hasItem() {
        List<String> list = asList("A", "B", "C");
        assertThat(list, hasItem("B"));
    }

    @Test
    public void test_X_or_Y() {
        List<String> list = asList("A", "Y", "X");
        assertThat(list, anyOf(hasItem("X"), hasItem("Y")));
    }

    @Test
    public void test_onlyX_or_onlyY() {
        //List<String> list = asList("A", "X", "Y");
        List<String> list = asList("A", "X", "*");
        assertThat(list, anyOf(allOf(hasItem("X"), not(hasItem("Y"))), allOf(not(hasItem("X")), hasItem("Y"))));
    }

    // ref.f().g().h().x(); // method chaining
    // ref.f(g(h(x()))

    @Test
    public void test_twoX_by_hand() {
        List<String> list = asList("A", "X", "X", "*", "+");
        int firstIndex = list.indexOf("X");
        System.out.println(firstIndex);
        int secondIndex = list.subList(firstIndex+1, list.size()).indexOf("X");
        System.out.println(secondIndex);
        int thirdIndex = list.subList(firstIndex+secondIndex+2, list.size()).indexOf("X");
        System.out.println(thirdIndex);
        if (!(firstIndex >= 0 && secondIndex >= 0 && thirdIndex < 0)) {
            throw new AssertionError();
        }
    }

    @Test
    public void test_twoX_by_custom_matcher() {
        List<String> list = asList("A", "X", "X", "*", "+");
        assertThat(list, hasStrictCount("X", 2));
    }

    public static <T> Matcher<Collection<T>> hasStrictCount(final T expectedElem, final int expectedCount) {
        return new BaseMatcher<Collection<T>>() {
            private int actualCount;
            private Object arg;
            @Override
            public boolean matches(Object item) {
                arg = item;
                actualCount = 0;
                for (T value : ((Iterable<T>) item)) {
                    actualCount += value.equals(expectedElem) ? 1 : 0;
                }
                return actualCount == expectedCount;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("Expected: " + expectedCount + " of '" + expectedElem
                        + "' but found " + actualCount + " in " + arg);
            }
        };
    }
}
