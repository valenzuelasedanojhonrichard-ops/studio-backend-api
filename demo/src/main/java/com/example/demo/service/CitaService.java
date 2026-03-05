package com.example.demo.service;

import com.example.demo.DTO.DashboardCitaDTO;
import com.example.demo.entity.Cita;
import com.example.demo.entity.EstadoCita;
import com.example.demo.repository.CitaRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class CitaService {

    private final CitaRepo citaRepo;


    // solo activas
    public List<Cita> listarActivas(){
        return citaRepo.findByEstado(EstadoCita.RESERVADA);
    }

    public Cita guardar(Cita cita){

        if(cita.getDuracionMinutos() == null || cita.getDuracionMinutos() <= 0){
            throw new RuntimeException("Duración inválida");
        }

        LocalDateTime inicioNueva = cita.getFechaHora();
        LocalDateTime finNueva = inicioNueva.plusMinutes(cita.getDuracionMinutos());

        // traer solo citas del mismo día
        LocalDateTime inicioDia = inicioNueva.toLocalDate().atStartOfDay();
        LocalDateTime finDia = inicioDia.plusDays(1);

        List<Cita> citasDia = citaRepo.findByFechaHoraBetween(inicioDia, finDia);

        for(Cita existente : citasDia){

            LocalDateTime inicioExistente = existente.getFechaHora();
            LocalDateTime finExistente =
                    inicioExistente.plusMinutes(existente.getDuracionMinutos());

            boolean seCruza =
                    inicioExistente.isBefore(finNueva) &&
                            finExistente.isAfter(inicioNueva);

            if(seCruza){
                throw new RuntimeException("Horario ocupado");
            }
        }

        cita.setEstado(EstadoCita.RESERVADA);

        return citaRepo.save(cita);
    }

    // editar
    public Cita actualizar(Long id, Cita nueva){

        Cita c = citaRepo.findById(id).orElseThrow();

        c.setClienteNombre(nueva.getClienteNombre());
        c.setServicio(nueva.getServicio());
        c.setFechaHora(nueva.getFechaHora());
        c.setPrecio(nueva.getPrecio());

        return citaRepo.save(c);
    }

    // cancelar
    public Cita cancelar(Long id){
        Cita c = citaRepo.findById(id).orElseThrow();
        c.setEstado(EstadoCita.CANCELADA);
        return citaRepo.save(c);
    }

    // atender
    public Cita atender(Long id){
        Cita c = citaRepo.findById(id).orElseThrow();
        c.setEstado(EstadoCita.ATENDIDA);
        return citaRepo.save(c);
    }

    public DashboardCitaDTO resumenCitas() {

        LocalDateTime ahora = LocalDateTime.now();

        LocalDate hoy = ahora.toLocalDate();

// HOY
        LocalDateTime inicioHoy = hoy.atStartOfDay();
        LocalDateTime finHoy = hoy.atTime(23, 59, 59);

// SEMANA (lunes → domingo)
        LocalDate inicioSem = hoy.with(DayOfWeek.MONDAY);
        LocalDate finSem = hoy.with(DayOfWeek.SUNDAY);

        LocalDateTime inicioSemana = inicioSem.atStartOfDay();
        LocalDateTime finSemana = finSem.atTime(23, 59, 59);

// MES
        LocalDate inicioM = hoy.withDayOfMonth(1);
        LocalDate finM = hoy.withDayOfMonth(hoy.lengthOfMonth());

        LocalDateTime inicioMes = inicioM.atStartOfDay();
        LocalDateTime finMes = finM.atTime(23, 59, 59);

        long hoyCount = citaRepo.countByFechaHoraBetween(inicioHoy, finHoy);

        long semanaCount = citaRepo.countByFechaHoraBetween(inicioSemana,finSemana);

        long mesCount = citaRepo.countByFechaHoraBetween(inicioMes, finMes);

        long reservadas = citaRepo.countByFechaHoraBetweenAndEstado(inicioMes, finMes, EstadoCita.RESERVADA);

        long canceladas = citaRepo.countByFechaHoraBetweenAndEstado(inicioMes, finMes, EstadoCita.CANCELADA);

        long atendidas = citaRepo.countByFechaHoraBetweenAndEstado(inicioMes, finMes, EstadoCita.ATENDIDA);


        return new DashboardCitaDTO(
                hoyCount,
                semanaCount,
                mesCount,
                reservadas,
                canceladas,
                atendidas
        );
    }


}
