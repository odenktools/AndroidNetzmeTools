package com.odenktools.netzmeodenktools;

import com.odenktools.netzmeodenktools.util.NetzmeEncryption;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void encryptData() {
        String encrypt = NetzmeEncryption.EncryptAES128("./n37tMe%^4geNt*", "3sdjHCmEBgwEib6j");
        assertEquals("RI0YpWghE+96sXX42liyNTdd7oQpPYgUjmsILwDjE1U=", encrypt);
    }

    @Test
    public void decryptData() {
        String encrypt = NetzmeEncryption.DecryptAES128("./n37tMe%^4geNt*", "RI0YpWghE+96sXX42liyNTdd7oQpPYgUjmsILwDjE1U=");
        assertEquals("3sdjHCmEBgwEib6j", encrypt);
    }
}
