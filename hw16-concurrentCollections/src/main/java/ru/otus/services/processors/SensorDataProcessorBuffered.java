package ru.otus.services.processors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.api.SensorDataProcessor;
import ru.otus.api.model.SensorData;
import ru.otus.lib.SensorDataBufferedWriter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SensorDataProcessorBuffered implements SensorDataProcessor {
    private static final Logger log = LoggerFactory.getLogger(SensorDataProcessorBuffered.class);

    private final int bufferSize;
    private final SensorDataBufferedWriter writer;
    private List<SensorData> bufferedData = createSynchronizedList();

    public SensorDataProcessorBuffered(int bufferSize, SensorDataBufferedWriter writer) {
        this.bufferSize = bufferSize;
        this.writer = writer;
    }

    @Override
    public void process(SensorData data) {
        if (bufferedData.size() >= bufferSize) {
            synchronized (this) {
                if (bufferedData.size() >= bufferSize) {
                    flush();
                }
            }
        }

        bufferedData.add(data);
    }

    public synchronized void flush() {
        try {
            if (!bufferedData.isEmpty()) {
                bufferedData.sort(Comparator.comparing(SensorData::getMeasurementTime));
                writer.writeBufferedData(bufferedData);
                bufferedData = createSynchronizedList();
            }
        } catch (Exception e) {
            log.error("Ошибка в процессе записи буфера", e);
        }
    }

    private List<SensorData> createSynchronizedList() {
        return Collections.synchronizedList(new ArrayList<>());
    }

    @Override
    public void onProcessingEnd() {
        flush();
    }
}
