package leoliang.unitpricecompare.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MathEvaluatorTest {

    @Test
    public void test() {
        assertEquals(350.0, SimpleMathEvaluator.eval("100*3+50"), 0.00001);
        assertEquals(313.435, SimpleMathEvaluator.eval("12.4 + 100.345 * 3"), 0.00001);
        assertEquals(50.0, SimpleMathEvaluator.eval("100*0+50"), 0.00001);
        assertEquals(50.0, SimpleMathEvaluator.eval("0*3+50"), 0.00001);
        assertEquals(350.0, SimpleMathEvaluator.eval(".."), 0.00001);
        assertEquals(350.0, SimpleMathEvaluator.eval("100/ 1"), 0.00001);
    }

}
