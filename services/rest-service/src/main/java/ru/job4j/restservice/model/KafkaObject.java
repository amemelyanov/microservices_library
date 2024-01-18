package ru.job4j.restservice.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class KafkaObject<V> {
  private V data;
}