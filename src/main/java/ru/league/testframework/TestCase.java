package ru.league;

class TestCase {

    @Before
    public void preCondition() {
        System.out.println("HELLO WORLD");
    }

    @Test
    public void test1() {
        MyAssertLib.assertTrue(false);
    }

    @Test
    public void test0() {
        MyAssertLib.assertEquals(1, 2);
    }

    @Test
    public void test2() {
        MyAssertLib.assertNotNull(null);
    }

    @After
    public void postCondition() {
        System.out.println("BYE WORLD");
    }


}
