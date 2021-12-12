package ru.otus.processor.homework;

import ru.otus.model.Message;
import ru.otus.processor.Processor;
import ru.otus.processor.homework.exception.EvenSecondException;
import ru.otus.processor.homework.provider.api.DateTimeProvider;

public class ExceptionOnEvenSecondProcessor implements Processor {

    private final DateTimeProvider dateTimeProvider;

    public ExceptionOnEvenSecondProcessor(DateTimeProvider dateTimeProvider) {
        this.dateTimeProvider = dateTimeProvider;
    }

    @Override
    public Message process(Message message) {
        if (isEvenSecond()) {
            throw new EvenSecondException();
        }

        return message;
    }

    private boolean isEvenSecond() {
        return dateTimeProvider.getDate().getSecond() % 2 == 0;
    }
}
