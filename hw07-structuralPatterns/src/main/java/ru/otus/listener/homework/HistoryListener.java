package ru.otus.listener.homework;

import ru.otus.listener.Listener;
import ru.otus.model.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HistoryListener implements Listener, HistoryReader {

    List<Message> messageHistory = new ArrayList<>();

    @Override
    public void onUpdated(Message msg) {
        messageHistory.add(msg.toBuilder().build());
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        return messageHistory.stream()
                .filter(message -> message.getId() == id)
                .findFirst();
    }
}
