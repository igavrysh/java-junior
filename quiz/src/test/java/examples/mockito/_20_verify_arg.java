package examples.mockito;

import org.junit.Ignore;
import org.mockito.Mockito;

import java.util.List;

import static org.mockito.Mockito.verify;

// runs with jdk arg:
// --add-opens=java.base/java.lang=ALL-UNNAMED
@Ignore
public class _20_verify_arg {

    public static void main(String[] args) {
        // create + programming
        List list = Mockito.mock(List.class);

        // use
        list.add("A");
        list.remove("B");
        list.add("B");

        // ask?
        verify(list).add("B");
        verify(list).add("A");

    }

}
