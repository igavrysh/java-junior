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
public class _12_when_arg_matcher {

    public static void main(String[] args) {
        List<String> list = mock(List.class);
        ArgumentMatcher<Integer> matcher = new ArgumentMatcher<Integer>() {
            @Override
            public boolean matches(Object arg) {
                return (arg instanceof Integer) && ((Integer) arg) % 3 == 0;
            }
        };
        when(list.get(Mockito.intThat(matcher))).thenReturn("Hello!");

        for (int k = 0; k < 10; k++) {
            System.out.println("list.get(" + k + ")" + list.get(k));
        }
    }
}
