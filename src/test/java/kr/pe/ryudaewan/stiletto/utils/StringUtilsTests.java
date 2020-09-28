package kr.pe.ryudaewan.stiletto.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StringUtilsTests {
    @Test
    public void testIsEmpty() {
        String str = null;
        assertEquals(true, StringUtils.isEmpty(str));
        str = "    ";
        assertEquals(true, StringUtils.isEmpty(str));
        str = "abc x  ";
        assertEquals(false, StringUtils.isEmpty(str));
    }
}
