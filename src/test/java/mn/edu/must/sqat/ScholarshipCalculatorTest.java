
package mn.edu.must.sqat;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ScholarshipCalculatorTest {

    private final ScholarshipCalculator calculator =
            new ScholarshipCalculator();

    // ХЭСЭГ a

    @Test
    void letterGradeA() {
        assertEquals("A", calculator.letterGrade(95));
    }

    @Test
    void gpaTwoGrades() {
        assertEquals(3.5,
                calculator.gpa(new double[]{95, 85}));
    }

    @Test
    void scholarship100() {
        assertEquals(100,
                calculator.scholarship(3.9, 16, false));
    }

    @Test
    void scholarship70() {
        assertEquals(70,
                calculator.scholarship(3.5, 12, false));
    }

    @Test
    void scholarship50() {
        assertEquals(50,
                calculator.scholarship(3.1, 8, false));
    }

    // ХЭСЭГ B1: LETTER GRADE (5 тест)

    @Test
    void letterGradeC() {
        assertEquals("C", calculator.letterGrade(75));
    }

    @Test
    void letterGradeD() {
        assertEquals("D", calculator.letterGrade(65));
    }

    @Test
    void letterGradeF() {
        assertEquals("F", calculator.letterGrade(55));
    }

    @Test
    void letterGradeRejectNegative() {
        assertThrows(IllegalArgumentException.class,
                () -> calculator.letterGrade(-1));
    }

    @Test
    void letterGradeRejectAbove100() {
        assertThrows(IllegalArgumentException.class,
                () -> calculator.letterGrade(101));
    }

    // ХЭСЭГ B2: GPA (3 тест)

    @Test
    void gpaAllGrades() {
        assertEquals(2.0,
                calculator.gpa(
                    new double[]{95, 85, 75, 65, 55}));
    }

    @Test
    void gpaRejectNull() {
        assertThrows(IllegalArgumentException.class,
                () -> calculator.gpa(null));
    }

    @Test
    void gpaRejectEmpty() {
        assertThrows(IllegalArgumentException.class,
                () -> calculator.gpa(new double[]{}));
    }

    // ХЭСЭГ B3: SCHOLARSHIP INPUT (3 тест)

    @Test
    void scholarshipRejectNegativeGpa() {
        assertThrows(IllegalArgumentException.class,
                () -> calculator.scholarship(-0.1, 15, false));
    }

    @Test
    void scholarshipRejectAboveMaxGpa() {
        assertThrows(IllegalArgumentException.class,
                () -> calculator.scholarship(4.1, 15, false));
    }

    @Test
    void scholarshipRejectNegativeCredits() {
        assertThrows(IllegalArgumentException.class,
                () -> calculator.scholarship(3.8, -1, false));
    }

    // ХЭСЭГ B4: SCHOLARSHIP BRANCHES (4 тест)

    @Test
    void scholarshipBlockedByViolation() {
        assertEquals(0,
                calculator.scholarship(3.9, 16, true));
    }

    @Test
    void scholarshipHighGpaLowCredits() {
        assertEquals(70,
                calculator.scholarship(3.9, 14, false));
    }

    @Test
    void scholarshipMidGpaLowCredits() {
        assertEquals(50,
                calculator.scholarship(3.5, 11, false));
    }

    @Test
    void scholarship0() {
        assertEquals(0,
                calculator.scholarship(2.9, 16, false));
    }
}
