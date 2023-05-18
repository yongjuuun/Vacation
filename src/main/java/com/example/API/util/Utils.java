package com.example.API.util;

import com.example.API.exception.FailRequestVacationException;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

@Component
public class Utils {
    public double calculateVacationDaysExcludingHolidays(LocalDate startDate, LocalDate endDate) {
        List<LocalDate> holidays = Arrays.asList(
                LocalDate.of(2023, 1, 1),
                LocalDate.of(2023, 1, 21),
                LocalDate.of(2023, 1, 22),
                LocalDate.of(2023, 1, 23),
                LocalDate.of(2023, 1, 24),
                LocalDate.of(2023, 2, 12),
                LocalDate.of(2023, 3, 1),
                LocalDate.of(2023, 5, 5),
                LocalDate.of(2023, 6, 6),
                LocalDate.of(2023, 8, 15),
                LocalDate.of(2023, 9, 28),
                LocalDate.of(2023, 9, 28),
                LocalDate.of(2023, 10, 3),
                LocalDate.of(2023, 12, 25)
        );

        long totalDays = ChronoUnit.DAYS.between(startDate, endDate) + 1;

        long weekends = IntStream.range(0, (int) totalDays)
                .mapToObj(startDate::plusDays)
                .filter(date -> date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY)
                .count();

        long holidaysCount = holidays.stream()
                .filter(date -> !date.isBefore(startDate) && !date.isAfter(endDate))
                .count();

        if ((totalDays == 1L && weekends == 1L)
                || (totalDays == 1L && holidaysCount == 1L)) {
            throw new FailRequestVacationException("신청한 휴일이 주말이거나 공휴일입니다.");
        }

        long daysExcludingHolidays = totalDays - weekends - holidaysCount;

        return (double) daysExcludingHolidays;
    }
}
