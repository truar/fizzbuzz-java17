package com.truaro.fizzbuzzjava17;

import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class FizzBuzz {

    static List<FizzBuzzPredicate> fizzBuzzPredicates = List.of(
            new FizzBuzzPredicate(i -> i % 15 == 0, MULTIPLE_OF_15::new),
            new FizzBuzzPredicate(i -> i % 3 == 0, MULTIPLE_OF_3::new),
            new FizzBuzzPredicate(i -> i % 5 == 0, MULTIPLE_OF_5::new)
    );

    record FizzBuzzPredicate(Predicate<Integer> predicate, Supplier<FizzBuzzNumber> supplier) {
        public boolean test(int number) {
            return predicate.test(number);
        }

        public FizzBuzzNumber get() {
            return supplier.get();
        }
    }

    sealed interface FizzBuzzNumber permits MULTIPLE_OF_15, MULTIPLE_OF_3, MULTIPLE_OF_5, REGULAR {

        String fizzBuzz();

        static FizzBuzzNumber from(int number) {
            return fizzBuzzPredicates.stream()
                    .filter(fizzBuzzPredicate -> fizzBuzzPredicate.test(number))
                    .findFirst()
                    .map(FizzBuzzPredicate::get)
                    .orElseGet(() -> new REGULAR(number));
        }

    }

    record MULTIPLE_OF_15() implements FizzBuzzNumber {
        @Override
        public String fizzBuzz() {
            return "fizzbuzz";
        }
    }

    record MULTIPLE_OF_3() implements FizzBuzzNumber {

        @Override
        public String fizzBuzz() {
            return "fizz";
        }
    }

    record MULTIPLE_OF_5() implements FizzBuzzNumber {
        @Override
        public String fizzBuzz() {
            return "buzz";
        }
    }

    record REGULAR(int number) implements FizzBuzzNumber {
        @Override
        public String fizzBuzz() {
            return String.valueOf(number);
        }
    }

    public static List<String> generatesFizzBuzzTo(int inclusiveEnd) {
        return IntStream.rangeClosed(1, inclusiveEnd)
                .mapToObj(FizzBuzzNumber::from)
                .map(FizzBuzzNumber::fizzBuzz)
                .collect(toList());
    }
}
