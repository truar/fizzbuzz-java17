package com.truaro.fizzbuzzjava17;

import com.truaro.fizzbuzzjava17.FizzBuzzTests.MULTIPLE_OF_15;
import com.truaro.fizzbuzzjava17.FizzBuzzTests.MULTIPLE_OF_3;
import com.truaro.fizzbuzzjava17.FizzBuzzTests.MULTIPLE_OF_5;
import com.truaro.fizzbuzzjava17.FizzBuzzTests.NOT_A_MULTIPLE;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.IntStream;

import static java.util.List.of;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

public class FizzBuzzTests {

    sealed interface MULTIPLE_OF permits MULTIPLE_OF_15, MULTIPLE_OF_3, MULTIPLE_OF_5, NOT_A_MULTIPLE {
    }

    record MULTIPLE_OF_15() implements MULTIPLE_OF {
    }

    record MULTIPLE_OF_5() implements MULTIPLE_OF {
    }

    record MULTIPLE_OF_3() implements MULTIPLE_OF {
    }

    record NOT_A_MULTIPLE(int number) implements MULTIPLE_OF {
    }

    record FizzBuzzNumber(int i) {
        public MULTIPLE_OF multipleOf() {
            if (i % 15 == 0) {
                return new MULTIPLE_OF_15();
            } else if (i % 3 == 0) {
                return new MULTIPLE_OF_3();
            } else if (i % 5 == 0) {
                return new MULTIPLE_OF_5();
            } else {
                return new NOT_A_MULTIPLE(i);
            }
        }
    }

    private static List<String> fizzbuzz(int number) {
        return IntStream.rangeClosed(1, number)
                .mapToObj(
                        i -> switch (new FizzBuzzNumber(i).multipleOf()) {
                            case MULTIPLE_OF_15 value -> "fizzbuzz";
                            case MULTIPLE_OF_3 value -> "fizz";
                            case MULTIPLE_OF_5 value -> "buzz";
                            case NOT_A_MULTIPLE value -> String.valueOf(value.number);
                        }
                )
                .collect(toList());
    }

    @Test
    void returns_the_input_as_string_when_no_special_case() {
        assertThat(fizzbuzz(1)).isEqualTo(of("1"));
    }

    @Test
    void concats_from_1_to_number() {
        assertThat(fizzbuzz(2)).isEqualTo(of("1", "2"));
    }

    @Test
    void returns_fizz_when_it_is_a_multiple_of_3() {
        assertThat(fizzbuzz(3)).isEqualTo(of("1", "2", "fizz"));
    }

    @Test
    void returns_buzz_when_it_is_a_multiple_of_5() {
        assertThat(fizzbuzz(5)).isEqualTo(of("1", "2", "fizz", "4", "buzz"));
    }

    @Test
    void returns_buzz_when_it_is_a_multiple_of_15() {
        assertThat(fizzbuzz(15)).containsExactly("1", "2", "fizz", "4", "buzz", "fizz", "7", "8", "fizz", "buzz", "11", "fizz", "13", "14", "fizzbuzz");
    }
}
