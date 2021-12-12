package ru.otus.handler;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.model.Message;
import ru.otus.processor.Processor;
import ru.otus.processor.homework.ExceptionOnEvenSecondProcessor;
import ru.otus.processor.homework.exception.EvenSecondException;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class ExceptionOnEvenSecondProcessorTest {

    @Test
    @DisplayName("Тестируем обработку исключения которое вылетит на четной секунде")
    void handleExceptionOnEvenSecondTest() {
        var message = new Message.Builder(1L).field8("field8").build();

        Processor processor1 = new ExceptionOnEvenSecondProcessor(() -> LocalDateTime.of(2022, 12, 12, 12, 12, 12));

        //when
        assertThatExceptionOfType(EvenSecondException.class).isThrownBy(() -> processor1.process(message));
    }

}
