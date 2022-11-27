package examples.mockito;

import org.junit.Ignore;
import org.mockito.ArgumentMatcher;
import org.mockito.Mockito;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

// runs with jdk arg:
// --add-opens=java.base/java.lang=ALL-UNNAMED
@Ignore
public class _14_middle_mock {

    public static void main(String[] args) {
        List<String> list = mock(List.class);

        when(list.get(Mockito.anyInt())).thenReturn("O");
        when(list.get(5)).thenReturn("A");

        ArgumentMatcher<Integer> matcher = new ArgumentMatcher<Integer>() {
            @Override
            public boolean matches(Object arg) {
                return ((Integer) arg) % 3 == 0;
            }
        };
        when(list.get(Mockito.intThat(matcher))).thenReturn("every third");
        when(list.get(9)).thenThrow(new RuntimeException("Boo!"));

        for (int k = 0; k < 10; k++) {
            System.err.println("list.get(" + k + "): " + list.get(k));
        }
    }
}
