//package com.example.API.repository;
//
//import com.example.API.domain.user.User;
//
//import java.time.LocalDate;
//import java.time.Period;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Optional;
//
//public class MemoryUserVaction implements UserRepository {
//    private static Map<Long, User> store = new HashMap<>();
//    private static long sequence = 0L;
//
//    @Override
//    public User login(User user) {
//        user.setId(++sequence);
//        user.setRemainVaction(15);
//        store.put(user.getId(), user);
//
//        return user;
//    }
//
//    @Override
//    public Optional<User> dateReqVaction(int startMon, int endMon, int startDay, int endDay) {
//        LocalDate startDate = LocalDate.of(2023,startMon,endMon);
//        LocalDate endDate = LocalDate.of(2023,startDay,endDay);
//
//        Period period = Period.between(startDate, endDate); // 휴가 몇일 사용하는지
//
//        // period 를 저장하고 return ?
////        store.get()
//
//        return Optional.ofNullable();
//    }
//
//    @Override
//    public Optional<User> checkRestVaction(int restDay) {
//        return Optional.empty();
//    }
//
//    @Override
//    public Optional<User> comment(String comment) {
//        return Optional.empty();
//    }
//
//    @Override
//    public void clearStore() {
//
//    }
//}
