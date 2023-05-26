package com.project.cfgames.services;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class DataService {
    // retorna valor da data e hora atual
    public LocalDateTime getDateTimeNow(){
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        String dateTimeNow = format.format(LocalDateTime.now());

        return LocalDateTime.parse(dateTimeNow, format);
    }

    // retorna valodar da data e hora de validade (30 dias)
    public LocalDateTime getDateTimeValidade(){
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        String dateTimeValidade = format.format(LocalDateTime.now().plusDays(30));

        return LocalDateTime.parse(dateTimeValidade, format);
    }
}
