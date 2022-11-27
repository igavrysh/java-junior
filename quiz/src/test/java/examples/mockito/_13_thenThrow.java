package examples.mockito;

import org.junit.Ignore;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

// runs with jdk arg:
// --add-opens=java.base/java.lang=ALL-UNNAMED
@Ignore
public class _13_thenThrow {

    public static void main(String[] args) {
        List<String> list = mock(List.class);
        when(list.get(9)).thenThrow(new RuntimeException("Boo!"));

        for (int k = 0; k < 10; k++) {
            System.err.println("list.get(" + k + "): " + list.get(k));
        }
    }
}
