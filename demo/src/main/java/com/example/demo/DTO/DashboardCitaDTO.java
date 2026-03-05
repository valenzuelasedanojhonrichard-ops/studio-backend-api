package com.example.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardCitaDTO {

        private long hoy;
        private long semana;
        private long mes;
        private long reservadas;
        private long canceladas;
        private long atendidas;
}
