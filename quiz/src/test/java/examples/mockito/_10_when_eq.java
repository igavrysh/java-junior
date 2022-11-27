package examples.mockito;

import org.junit.Ignore;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

// runs with jdk arg:
// --add-opens=java.base/java.lang=ALL-UNNAMED
@Ignore
public class _10_when_eq {

    public static void main(String[] args) {
        List<String> list = mock(List.class);
        when(list.get(5)).thenReturn("A");

        for (int k = 0; k < 10; k++) {
            System.out.println("list.get(" + k + "): " + list.get(k));
        }
    }
}
