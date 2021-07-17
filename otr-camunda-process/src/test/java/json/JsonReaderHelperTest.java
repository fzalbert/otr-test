package json;

import org.example.dto.appeal.Appeal;
import org.example.utils.JsonReaderHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Type;

@ExtendWith(MockitoExtension.class)
public class JsonReaderHelperTest {


    @Mock
    JsonReaderHelper jsonReader;

    @BeforeEach
    void init() {

    }

    @Test
    void compute(){
        String jsonMock = "";
        Class<Appeal> type = Appeal.class;

//        Mockito.when(jsonReader.read(jsonMock, type))
//               .thenReturn(fooData);
//
//
//        long actualResult = fooService.compute(...);
//        long expectedResult = ...;
//        Assertions.assertEquals(expectedResult, actualResult);
    }

}
