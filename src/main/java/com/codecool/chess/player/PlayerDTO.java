package com.codecool.chess.player;

import java.time.LocalDate;

public record PlayerDTO(String name, int fide_id, LocalDate born, String born_place, int earn_gm, String country, int id) {}

