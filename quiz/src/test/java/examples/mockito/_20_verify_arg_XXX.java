package examples.mockito;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

// runs with jdk arg:
// --add-opens=java.base/java.lang=ALL-UNNAMED
@Ignore
public class _20_verify_arg_XXX {

    private ArrayList<String> list;

    @Before
    public void setUp() {
        list = new ArrayList<>();
    }

    @Test
    public void test_addAll() {
        // create + programming
        Collection<String> coll = Mockito.mock(Collection.class);
        when(coll.toArray()).thenReturn(new String[] {"A", "B", "C"});
        when(coll.iterator()).thenReturn(asList("A", "B", "C").iterator());

        // use
        list.addAll(coll);
        list.addAll(coll);
        list.addAll(coll);

        //System.out.println(list);

        // ask?

        assertThat(list, equalTo(
                asList("A", "B", "C", "A", "B", "C", "A", "B", "C"))
        );
        //verify(arg).iterator();
        verify(coll, times(3)).toArray();
    }
}
