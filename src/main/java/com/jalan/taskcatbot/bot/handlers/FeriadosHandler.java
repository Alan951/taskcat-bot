package com.jalan.taskcatbot.bot.handlers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import com.jalan.taskcatbot.bot.IBot;
import com.jalan.taskcatbot.bot.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class FeriadosHandler implements IHandler, IHandlerSelector {

    private List<Dia> dias;
    private String commands[] = {"proximo", "proximo feriado", "proximo pago"};
    private Locale locale = new Locale("es", "MX");

    public FeriadosHandler() {
        dias = new ArrayList<Dia>();

        dias.add(new Dia(LocalDate.of(2022, 1, 1), TipoDia.ASUETO, "Año nuevo!"));
        dias.add(new Dia(LocalDate.of(2022, 1, 13), TipoDia.PAGO_NOMINA));
        dias.add(new Dia(LocalDate.of(2022, 1, 28), TipoDia.PAGO_NOMINA));
        dias.add(new Dia(LocalDate.of(2022, 2, 7), TipoDia.ASUETO));
        dias.add(new Dia(LocalDate.of(2022, 2, 14), TipoDia.PAGO_NOMINA));
        dias.add(new Dia(LocalDate.of(2022, 2, 25), TipoDia.PAGO_NOMINA));
        dias.add(new Dia(LocalDate.of(2022, 3, 14), TipoDia.PAGO_NOMINA));
        dias.add(new Dia(LocalDate.of(2022, 3, 30), TipoDia.PAGO_NOMINA));
        dias.add(new Dia(LocalDate.of(2022, 4, 13), TipoDia.PAGO_NOMINA));
        dias.add(new Dia(LocalDate.of(2022, 4, 14), TipoDia.ASUETO));
        dias.add(new Dia(LocalDate.of(2022, 4, 15), TipoDia.ASUETO));
        dias.add(new Dia(LocalDate.of(2022, 4, 28), TipoDia.PAGO_NOMINA));
        dias.add(new Dia(LocalDate.of(2022, 5, 1), TipoDia.ASUETO));
        dias.add(new Dia(LocalDate.of(2022, 5, 12), TipoDia.PAGO_NOMINA));
        dias.add(new Dia(LocalDate.of(2022, 5, 30), TipoDia.PAGO_NOMINA));
        dias.add(new Dia(LocalDate.of(2022, 6, 14), TipoDia.PAGO_NOMINA));
        dias.add(new Dia(LocalDate.of(2022, 6, 29), TipoDia.PAGO_NOMINA));
        dias.add(new Dia(LocalDate.of(2022, 7, 14), TipoDia.PAGO_NOMINA));
        dias.add(new Dia(LocalDate.of(2022, 7, 28), TipoDia.PAGO_NOMINA));
        dias.add(new Dia(LocalDate.of(2022, 8, 12), TipoDia.PAGO_NOMINA));
        dias.add(new Dia(LocalDate.of(2022, 8, 30), TipoDia.PAGO_NOMINA));
        dias.add(new Dia(LocalDate.of(2022, 9, 14), TipoDia.PAGO_NOMINA));
        dias.add(new Dia(LocalDate.of(2022, 9, 16), TipoDia.ASUETO));
        dias.add(new Dia(LocalDate.of(2022, 9, 29), TipoDia.PAGO_NOMINA));
        dias.add(new Dia(LocalDate.of(2022, 10, 13), TipoDia.PAGO_NOMINA));
        dias.add(new Dia(LocalDate.of(2022, 10, 28), TipoDia.PAGO_NOMINA));
        dias.add(new Dia(LocalDate.of(2022, 11, 14), TipoDia.PAGO_NOMINA));
        dias.add(new Dia(LocalDate.of(2022, 11, 21), TipoDia.ASUETO));
        dias.add(new Dia(LocalDate.of(2022, 11, 29), TipoDia.PAGO_NOMINA));
        dias.add(new Dia(LocalDate.of(2022, 12, 12), TipoDia.ASUETO));
        dias.add(new Dia(LocalDate.of(2022, 12, 14), TipoDia.PAGO_NOMINA));
        dias.add(new Dia(LocalDate.of(2022, 12, 25), TipoDia.ASUETO));
        dias.add(new Dia(LocalDate.of(2022, 12, 29), TipoDia.PAGO_NOMINA));
    }

    @Override
    public String getName() {
        return "feriados";
    }

    @Override
    public void handle(IBot bot, User user, String message) {
        if(message.equals(getName())) {
            bot.sendAssists(Arrays.asList(commands), user);
            return;
        }

        Dia dia = null;

        switch (message) {
            case "proximo feriado":
                dia = getProximoFeriado();

                if(dia != null) {
                    bot.sendMessage(user, "Faltan " + diasFaltantes(dia.getFecha())  + " días para el próximo día feriado.");
                } else {
                    bot.sendMessage(user, "No hay ningún feriado próximo");
                }

                break;
            case "proximo pago":
                dia = getProximoPago();

                if(dia != null) {
                    bot.sendMessage(user, "Faltan " + diasFaltantes(dia.getFecha())  + " días para el próximo pago de nómina. Pagen el " + getDayName(dia.getFecha()) + " " + dia.getFecha().getDayOfMonth() + " de " + getMonthName(dia.getFecha()) + ".");
                } else {
                    bot.sendMessage(user, "No hay ningún pago próximo");
                }

                break;
            case "proximo":
                dia = getProximo();

                if(dia != null) {
                    bot.sendMessage(user, "Faltan " + diasFaltantes(dia.getFecha())  + " días para el " + (dia.getTipoDia() == TipoDia.ASUETO ? "día de asueto" : "pago de nómina"));
                } else {
                    bot.sendMessage(user, "No hay ningún día interesante.");
                }
                
                break;
        }
    }

    public long diasFaltantes(LocalDate date) {
        return ChronoUnit.DAYS.between(LocalDate.now(), date);
    }

    public String getDayName(LocalDate date) {
        return DateTimeFormatter.ofPattern("EEEE", locale).format(date);
    }

    public String getMonthName(LocalDate date) {
        return DateTimeFormatter.ofPattern("MMMM", locale).format(date);
    }

    public Dia getProximo() {
        return dias.stream()
            .filter((dia) -> (dia.getFecha().isAfter(LocalDate.now())))
            .findFirst().orElse(null);
    }

    public Dia getProximoFeriado() {
        return dias.stream()
            .filter((dia) -> (dia.getFecha().isAfter(LocalDate.now())) && dia.getTipoDia().equals(TipoDia.ASUETO))
            .findFirst().orElse(null);
    }

    public Dia getProximoPago() {
        return dias.stream()
            .filter((dia) -> (dia.getFecha().isAfter(LocalDate.now())) && dia.getTipoDia().equals(TipoDia.PAGO_NOMINA))
            .findFirst().orElse(null);
    }

    @Override
    public boolean isSelectable(String message) {
        message = message.toLowerCase();

        for(String cmd : this.commands) {
            if(message.equals(cmd)) {
                return true;
            }
        }

        return false;
    }
}

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
class Dia {
    private LocalDate fecha;
    private TipoDia tipoDia;
    private String descripcion;

    public Dia(LocalDate fecha, TipoDia tipoDia) {
        this.fecha = fecha;
        this.tipoDia = tipoDia;
    }
}

enum TipoDia {
    ASUETO, PAGO_NOMINA
}