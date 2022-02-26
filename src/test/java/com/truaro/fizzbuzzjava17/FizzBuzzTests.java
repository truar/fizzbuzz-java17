package com.truaro.fizzbuzzjava17;

import org.junit.jupiter.api.Test;

import static com.truaro.fizzbuzzjava17.FizzBuzz.generatesFizzBuzzTo;
import static java.util.List.of;
import static org.assertj.core.api.Assertions.assertThat;

public class FizzBuzzTests {

    @Test
    void returns_the_input_as_string_when_no_special_case() {
        assertThat(generatesFizzBuzzTo(1)).isEqualTo(of("1"));
    }

    @Test
    void concats_from_1_to_number() {
        assertThat(generatesFizzBuzzTo(2)).isEqualTo(of("1", "2"));
    }

    @Test
    void returns_fizz_when_it_is_a_multiple_of_3() {
        assertThat(generatesFizzBuzzTo(3)).isEqualTo(of("1", "2", "fizz"));
    }

    @Test
    void returns_buzz_when_it_is_a_multiple_of_5() {
        assertThat(generatesFizzBuzzTo(5)).isEqualTo(of("1", "2", "fizz", "4", "buzz"));
    }

    @Test
    void returns_buzz_when_it_is_a_multiple_of_15() {
        assertThat(generatesFizzBuzzTo(15)).containsExactly("1", "2", "fizz", "4", "buzz", "fizz", "7", "8", "fizz", "buzz", "11", "fizz", "13", "14", "fizzbuzz");
    }
}
