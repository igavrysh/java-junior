package examples.mockito;

import org.junit.Ignore;
import org.mockito.Mockito;

import java.util.List;

import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.RETURNS_MOCKS;

// runs with jdk arg:
// --add-opens=java.base/java.lang=ALL-UNNAMED
@Ignore
public class _15_mock_types {

    public static void main(String[] args) {
        List listDefault = Mockito.mock(List.class);
        System.out.println(listDefault.size());
        System.out.println(listDefault.isEmpty());
        System.out.println(listDefault.iterator());

        System.out.println();

        // TODO(igavrysh): Stubs still return nulls - how to fix it?
        List listDeepSub = Mockito.mock(
                List.class,
                Mockito.withSettings().defaultAnswer(RETURNS_DEEP_STUBS));
        System.out.println(listDeepSub.size());
        System.out.println(listDeepSub.isEmpty());
        System.out.println(listDeepSub.iterator());
        System.out.println(listDeepSub.iterator().next());
        System.out.println(listDeepSub.subList(0, 10).get(0).toString());
    }
}
