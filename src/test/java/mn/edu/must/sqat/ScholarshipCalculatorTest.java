
package mn.edu.must.sqat;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ScholarshipCalculatorTest {

    private final ScholarshipCalculator calculator =
            new ScholarshipCalculator();

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
}
