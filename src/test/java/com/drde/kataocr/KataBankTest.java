package com.drde.kataocr;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Charito on 7/8/2016.
 */
public class KataBankTest {
    private KataBank kataBank;

    @Before
    public void setUp() {
        kataBank = new KataBank();
    }

    @Test
    public void testParseAccount() throws IOException {
        assertEquals("Could not parse account number", "123456789", kataBank.accountNumber());
    }

    @Test
    public void testIsValidAccount() {
        assertTrue(kataBank.isValidAccount("000000051"));
        assertFalse(kataBank.isValidAccount("664371495"));
    }
}
