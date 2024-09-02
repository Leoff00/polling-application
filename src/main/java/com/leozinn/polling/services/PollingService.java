package com.leozinn.polling.services;

import com.google.gson.Gson;
import com.leozinn.polling.entities.Users;
import com.leozinn.polling.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class PollingService {
    private static final Logger logger = LoggerFactory.getLogger(PollingService.class);

    @Autowired
    private UserRepository userRepository;
    private final Gson gson = new Gson();
    private int offset = 0;
    private final int batch = 1000;


    @Scheduled(fixedRate = 3000)
    public void findAll() {
        logger.info("INICIALIZANDO POLLING PUXANDO REGISTROS NO DB");
        List<Users> users = userRepository.findBatch(offset, batch);
        int totalCount = userRepository.countTotalRecords();

        if (!users.isEmpty()) {
            processUsers(users);
            offset += 1000;
            int remainingRecords = totalCount - offset;
            logger.info("REGISTROS FALTANTES A SEREM PROCESSADOS: {}", remainingRecords);
        }
        else {
            logger.info("NADA A PROCESSAR NO MOMENTO");
        }
    }

    private void processUsers(List<Users> users) {
    for (Users row : users) {
        logger.info("PROCESSANDO {} \n", gson.toJson(row));
        }
        logger.info("ENVIANDO PARA A FILA X OS LOTES...");

    }
}