package edu.school21.numbers;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;


public class NumberWorkerTest {
	private static NumberWorker numberWorker = new NumberWorker();

	@ParameterizedTest
	@ValueSource(ints = { 2, 3, 7, 11, 2147483647 })
	void isPrimeForPrimes(int argument) {
		assertTrue(numberWorker.isPrime(argument));
	}

	@ParameterizedTest
	@ValueSource(ints = { 4, 9, 15, 291, 2147483646 })
	void isPrimeForNotPrimes(int argument) {
		assertFalse(numberWorker.isPrime(argument));
	}

	@ParameterizedTest
	@ValueSource(ints = { 0, 1, -15, -2147483648 })
	void isPrimeForIncorrectNumbers(int argument) {
		assertThrows(IllegalNumberException.class, () -> numberWorker.isPrime(argument));
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/data.csv", numLinesToSkip = 1)
	void trueDigitSumm(int number, int summ) {
		assertEquals(summ, numberWorker.digitSum(number));
	}
}
