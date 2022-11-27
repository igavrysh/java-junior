package examples.mockito;

import org.junit.Ignore;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.mock;

// runs with jdk arg:
// --add-opens=java.base/java.lang=ALL-UNNAMED
@Ignore
public class _11_when_all {

    public static void main(String[] args) {
        List<String> list = mock(List.class);
        Mockito.when(list.size()).thenReturn(Integer.MAX_VALUE);
        Mockito.when(list.get(Mockito.anyInt())).thenReturn("Hello!");

        List<String> list1000 = Collections.nCopies(1000, "Hello");
        System.out.println(list1000);

        System.out.println(list.size());
        for (int k = 0; k < 10; k++) {
            System.out.println("list.get(" + k + ")" + list.get(k));
        }
    }
}
