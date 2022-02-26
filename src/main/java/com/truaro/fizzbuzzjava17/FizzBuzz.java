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

    sealed interface FizzBuzzNumber permits MULTIPLE_OF_15, MULTIPLE_OF_3, MULTIPLE_OF_5, NOT_A_MULTIPLE {
        static FizzBuzzNumber from(int number) {
            return fizzBuzzPredicates.stream()
                    .filter(fizzBuzzPredicate -> fizzBuzzPredicate.test(number))
                    .findFirst()
                    .map(FizzBuzzPredicate::get)
                    .orElseGet(() -> new NOT_A_MULTIPLE(number));
        }

    }

    record MULTIPLE_OF_15() implements FizzBuzzNumber {
    }

    record MULTIPLE_OF_5() implements FizzBuzzNumber {
    }

    record MULTIPLE_OF_3() implements FizzBuzzNumber {
    }

    record NOT_A_MULTIPLE(int number) implements FizzBuzzNumber {
    }

    public static List<String> generatesFizzBuzzTo(int inclusiveEnd) {
        return IntStream.rangeClosed(1, inclusiveEnd)
                .mapToObj(FizzBuzzNumber::from)
                .map(
                        fizzBuzzNumber -> switch (fizzBuzzNumber) {
                            case MULTIPLE_OF_15 value -> "fizzbuzz";
                            case MULTIPLE_OF_3 value -> "fizz";
                            case MULTIPLE_OF_5 value -> "buzz";
                            case NOT_A_MULTIPLE value -> String.valueOf(value.number);
                        }
                )
                .collect(toList());
    }

}
