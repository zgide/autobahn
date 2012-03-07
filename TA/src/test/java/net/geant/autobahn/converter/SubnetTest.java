package net.geant.autobahn.converter;

import junit.framework.TestCase;

import net.geant.autobahn.converter.Subnet;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SubnetTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test(expected=IllegalArgumentException.class)
	public void testWrongDataFormat() {
		@SuppressWarnings("unused")
        Subnet subnet = new Subnet("-- a.b.cxxx");
	}

	@Test(expected=IllegalArgumentException.class)
	public void testEmpty() {
		@SuppressWarnings("unused")
        Subnet subnet = new Subnet("");
	}

	@Test(expected=IllegalArgumentException.class)
	public void testIllegalMaskTooSmall() {
		@SuppressWarnings("unused")
        Subnet subnet = new Subnet("192.168.1.10/33");
	}

    @Test(expected=IllegalArgumentException.class)
    public void testIllegalMaskTooLarge() {
        @SuppressWarnings("unused")
        Subnet subnet = new Subnet("192.168.1.10/-1");
    }
    
	@Test(expected=IllegalArgumentException.class)
	public void testIllegalIpNumberTooLarge() {
		@SuppressWarnings("unused")
        Subnet subnet = new Subnet("10.300.1.1/24");
	}

	@Test(expected=IllegalArgumentException.class)
	public void testIllegalIpNumberMoreDotsTooLarge() {
		@SuppressWarnings("unused")
        Subnet subnet = new Subnet("10.300.1.1.6/24");
	}

    @Test(expected=IllegalArgumentException.class)
    public void testIllegalIpNumberMoreDots() {
        @SuppressWarnings("unused")
        Subnet subnet = new Subnet("10.10.1.1.1/24");
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testIllegalIpNumberWhitespaceStart() {
        @SuppressWarnings("unused")
        Subnet subnet = new Subnet(" 10.10.1.1/24 ");
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testIllegalIpNumberWhitespaceMiddle() {
        @SuppressWarnings("unused")
        Subnet subnet = new Subnet("10 .10.1.1/24");
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testIllegalIpNumberWhitespaceEnd() {
        @SuppressWarnings("unused")
        Subnet subnet = new Subnet("10.10.1.1/24 ");
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testIllegalIpNumber6() {
        @SuppressWarnings("unused")
        Subnet subnet = new Subnet("10.10.1/24");
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testIllegalIpNumberTwoMasks() {
        @SuppressWarnings("unused")
        Subnet subnet = new Subnet("10.10.1.1/23/24");
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testIllegalIpNumberWrongOrder() {
        @SuppressWarnings("unused")
        Subnet subnet = new Subnet("23/10.10.1.1");
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testIllegalIpNumberNoMask() {
        @SuppressWarnings("unused")
        Subnet subnet = new Subnet("10.10.1.1");
    }
    
	@Test(expected=IllegalStateException.class)
	public void testBasicSubnet() {
		Subnet subnet = new Subnet("192.168.1.10/24");
		
		for(int i = 10; i <= 255; i++) {
			TestCase.assertEquals("192.168.1." + i, subnet.nextValue());
		}
		
		TestCase.assertEquals(false, subnet.hasMoreValues());
		
		subnet.nextValue();
	}

    @Test
    public void testWholeInternetMask() {
        @SuppressWarnings("unused")
        Subnet subnet = new Subnet("192.168.1.10/0");
    }
    
    @Test(expected=IllegalStateException.class)
    public void testSingleHostMask() {
        Subnet subnet = new Subnet("192.168.1.10/32");
        TestCase.assertEquals(1, subnetSize(subnet));
        
        subnet.nextValue();
    }
    
    public int subnetSize(Subnet s) {
        int size = 0;
        while (s.hasMoreValues()) {
            s.nextValue();
            size++;
        }
        return size;
    }
    
}
