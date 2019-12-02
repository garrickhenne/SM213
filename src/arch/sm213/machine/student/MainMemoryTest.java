package arch.sm213.machine.student;

import machine.AbstractMainMemory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MainMemoryTest {

    MainMemory mm = new MainMemory(4);

    @Test
    void testIsAccessAligned() {
        //Testing whether modulo is correctly implemented
        assertFalse(mm.isAccessAligned(20,200));
        assertTrue(mm.isAccessAligned(4,2));
        assertTrue(mm.isAccessAligned(4,4));
    }

    @Test
    void testBytesToIntegerBaseCase() {
        //Basic case to see if anything other than 0 would be printed
        byte [] testByte = new byte[4];
        for (int i = 0; i < 4; i++) {
            testByte[i] = 0;
        }
        assertEquals(0, mm.bytesToInteger(testByte[0],testByte[1],testByte[2],testByte[3]));
    }

    @Test
    void testBytesToIntegerWithValues() {
        //Tests whether there are any flaws with negative numbers
        byte [] testByte = new byte[4];
        for (int i = 0; i < 4; i++) {
            testByte[i] = (byte) 0xFF;
        }
        assertEquals(-1, mm.bytesToInteger(testByte[0],testByte[1],testByte[2],testByte[3]));
    }

    @Test
    void testBytesToIntegerRandomNumber() {
        //Testing with bytes that are not the same, also using bits with "one digit"
        byte [] testByte = new byte[4];
        for (int i = 0; i < 4; i++) {
            testByte[i] = (byte) i;
        }
        assertEquals(Integer.parseInt("00010203",16), mm.bytesToInteger(testByte[0],testByte[1],testByte[2],testByte[3]));
    }

    @Test
    void testBytesToIntegerHexInt() {
        //Testing bits with hexadecimal representation
        byte [] testBytes = new byte[4];
        for (int i = 0; i < 4; i++) {
            testBytes[i] = (byte) (i+10);
        }
        assertEquals(Integer.parseInt("0A0B0C0D",16),mm.bytesToInteger(testBytes[0],testBytes[1],testBytes[2],testBytes[3]));
    }

    @Test
    void testBytesToIntegerHighInt() {
        //Testing bits with no zeros
        byte [] testBytes = new byte[4];
        for (int i = 0; i < 4; i++) {
            testBytes[i] = (byte) (i +20);
        }
        assertEquals(Integer.parseInt("14151617",16),mm.bytesToInteger(testBytes[0],testBytes[1],testBytes[2],testBytes[3]));
    }

    @Test
    void testIntegerToBytesBaseCase() {
        //Base case where int is 0
        byte [] testByte = mm.integerToBytes(0);
        for (int i = 0; i < 4; i++) {
            assertEquals(0,testByte[i]);
        }
    }

    //The remainder of tests for integerToBytes will be using numbers from bytesToInteger


    @Test
    void testIntegerToBytesNegativeOne() {
        //Testing 0xFFFFFFFF
        byte [] testByte = mm.integerToBytes(-1);
        for (int i = 0; i < 4; i++) {
            assertEquals(-1,testByte[i]);
        }
    }

    @Test
    void testIntegerToBytesRandomNumber() {
        //Testing 00010203
        byte [] testByte = mm.integerToBytes(Integer.parseInt("00010203",16));
        assertEquals(0x00,testByte[0]);
        assertEquals(0x01,testByte[1]);
        assertEquals(0x02,testByte[2]);
        assertEquals(0x03,testByte[3]);
    }

    @Test
    void testIntegerToBytesHexInt() {
        //Testing 0A0B0C0D
        byte [] testByte = mm.integerToBytes(Integer.parseInt("0A0B0C0D",16));
        assertEquals(0x0A,testByte[0]);
        assertEquals(0x0B,testByte[1]);
        assertEquals(0x0C,testByte[2]);
        assertEquals(0x0D,testByte[3]);
    }

    @Test
    void testIntegerToBytesHighInt() {
        //Testing 14151617
        byte [] testByte = mm.integerToBytes(Integer.parseInt("14151617",16));
        assertEquals(0x14,testByte[0]);
        assertEquals(0x15,testByte[1]);
        assertEquals(0x16,testByte[2]);
        assertEquals(0x17,testByte[3]);
    }

    //Testing get and set:

    @Test
    void testGetAndSet() {
        byte [] testByte = new byte[4];
        for (int i = 0; i < testByte.length; i++) {
            testByte[i] = (byte) i;
        }
        try {
            mm.set(0, testByte);
            byte [] mmCopy = mm.get(0,4);
            for (int i = 0; i < mm.length(); i++) {
                assertEquals(testByte[i], mmCopy[i]);
            }
        } catch (AbstractMainMemory.InvalidAddressException e) {
            fail();
        }
    }

    @Test
    void testExceptionHandlingGetNegativeAddress() {
        try {
            mm.get(-1,5);
            fail();
        } catch (AbstractMainMemory.InvalidAddressException e) {
            //
        }
    }

    @Test
    void testExceptionHandlingGetAddressOutOfBounds() {
        try {
            mm.get(100,5);
            fail();
        } catch (AbstractMainMemory.InvalidAddressException e) {
            //Success
        }
    }

    @Test
    void testExceptionHandlingSetNegativeAddress() {
        byte [] testByte = mm.integerToBytes(20);
        try {
            mm.set(-1, testByte);
            fail();
        } catch (AbstractMainMemory.InvalidAddressException e) {
            //Do nothing
        }
    }

    @Test
    void testExceptionHandlingSetOutOfBounds() {
        byte [] testByte = mm.integerToBytes(20);
        try {
            mm.set(5, testByte);
            fail();
        } catch (AbstractMainMemory.InvalidAddressException e) {
            //Do nothing
        }
    }

}
