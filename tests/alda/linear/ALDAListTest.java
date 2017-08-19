package alda.linear;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * This class contains JUnit test cases that you can use to test your
 * implementation of the list.
 *
 * The reason most of the test cases are commented (i.e. hidden) is that it gets
 * too messy if you try to make all of them work at the same time. A better way
 * is to make one test case work, and the uncomment the next one, leaving the
 * ones already working in place to catch any bugs in already working code that
 * might sneek in.
 *
 * When all the tests go through you will *PROBABLY* have a solution that
 * passes, i.e. if you also fulfills the requirements that can't be tested, such
 * as usage of the correct data structure, etc. Note though that the test cases
 * doesn't cover every nook and cranny, so feel free to test it even more. If we
 * find anything wrong with the code that these tests doesn't cover, then this
 * usually means a failed assignment.
 *
 * Depending on settings you may get warnings for import statements that isn't
 * used. These are used by tests that orginally are commented out, so leave the
 * import statments in place.
 *
 * @author Henrik
 */
public class ALDAListTest {

    // These two methods are the only places in the code that mentions the name
    // of your class.
    private static ALDAList<String> createNewList() {
        return new MyALDAList<String>();
    }

    private static ALDAList<Integer> createIntegerList() {
        return new MyALDAList<Integer>();
    }

    private ALDAList<String> list = createNewList();

    private void testField(java.lang.reflect.Field f) {
        assertTrue("All attributes should (probably) be private ",
                java.lang.reflect.Modifier.isPrivate(f.getModifiers()));
        assertFalse("There is no reason to use any arrays on this assignment", f.getType().isArray());
        assertFalse("There is (probably) not any reason to use any static attributes",
                java.lang.reflect.Modifier.isStatic(f.getModifiers()));
        for (Class<?> i : f.getType().getInterfaces()) {
            assertFalse(
                    "You should implement the functionality yourself, not use any of the list implementations already available",
                    i.getName().startsWith("java.util.List"));
        }
    }

    // How you work on this assignment is up to you, but a recommendation is to
    // uncomment the test methods below one by one in the order they are
    // presented. Remember though that the tests only are intended to cover
    // obvious errors. Even if all of them works there may still be errors in
    // your code. You are responsible for finding those. You may add as many
    // tests as you like to the test suite, but do NOT remove or change any of
    // the existing ones unless you are absolutely certain that they are wrong.
    // If we find any problems with the tests we will publish information about
    // this in Moodle, and also update the tests there.

    @Test
    public void testObviousImplementationErrors() {
        for (java.lang.reflect.Field f : list.getClass().getDeclaredFields()) {
            testField(f);
        }
    }

    @Test
    public void testEmpty() {
        list = createNewList();
        assertEquals(0, list.size());
        assertEquals("[]", list.toString());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetOnEmptyList() {
        list = createNewList();
        list.get(0);
    }

    @Before
    public void setUp() {
        list.add("First");
        list.add("Second");
        list.add("Third");
        list.add("Fourth");
        list.add("Fifth");
    }

    @Test
    public void testSimpleMethodsOnDefaultList() {
        assertEquals(5, list.size());
        assertEquals("First", list.get(0));
        assertEquals("Third", list.get(2));
        assertEquals("Fifth", list.get(4));
        assertEquals("[First, Second, Third, Fourth, Fifth]", list.toString());

        list.add("Second");
        assertEquals(6, list.size());
        assertEquals("Second", list.get(5));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testIndexBelowZero() {
        list.get(-1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testIndexAboveMax() {
        list.get(5);
    }

    @Test
    public void addWithIndex() {
        list.add(0, "A");
        list.add(6, "B");
        list.add(2, "C");
        assertEquals(8, list.size());
        assertEquals("A", list.get(0));
        assertEquals("C", list.get(2));
        assertEquals("B", list.get(7));
        assertEquals("[A, First, C, Second, Third, Fourth, Fifth, B]", list.toString());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testAddIndexBelowZero() {
        list.add(-1, "ABC");
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testAddIndexAboveMax() {
        list.add(6, "ABC");
    }

    @Test
    public void testClear() {
        list.clear();
        assertEquals(0, list.size());
        list.add("First");
        list.add(0, "Second");
        assertEquals(2, list.size());
        assertEquals("First", list.get(1));
        assertEquals("Second", list.get(0));
    }

    @Test
    public void testContains() {
        assertTrue(list.contains("First"));
        assertTrue(list.contains("Third"));
        assertFalse(list.contains("ABC"));
        assertFalse(list.contains(""));
    }

    @Test
    public void testIndexOf() {
        assertEquals(0, list.indexOf("First"));
        assertEquals(4, list.indexOf("Fifth"));
        assertEquals(-1, list.indexOf("ABC"));
        list.add("Second");
        assertEquals(1, list.indexOf("Second"));
    }

    @Test
    public void testRemoveWithIndex() {
        assertEquals("Third", list.remove(2));
        assertEquals(4, list.size());
        assertEquals("Second", list.get(1));
        assertEquals("Fourth", list.get(2));

        assertEquals("First", list.remove(0));
        assertEquals(3, list.size());
        assertEquals("Second", list.get(0));

        assertEquals("Fifth", list.remove(2));
        assertEquals(2, list.size());
        assertEquals("Fourth", list.get(1));
    }

    @Test
    public void testRemoveAtEnd() {
        list.remove(4);
        assertEquals(4, list.size());
        list.remove(3);
        assertEquals(3, list.size());
        assertEquals("[First, Second, Third]", list.toString());

        list.add("A");
        assertEquals(4, list.size());
        assertEquals("[First, Second, Third, A]", list.toString());
        list.add("B");
        assertEquals(5, list.size());
        assertEquals("[First, Second, Third, A, B]", list.toString());

        list.remove(4);
        assertEquals(4, list.size());
        list.remove(3);
        assertEquals(3, list.size());
        assertEquals("[First, Second, Third]", list.toString());

        list.add(3, "A");
        assertEquals(4, list.size());
        assertEquals("[First, Second, Third, A]", list.toString());
        list.add(4, "B");
        assertEquals(5, list.size());
        assertEquals("[First, Second, Third, A, B]", list.toString());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoveIndexBelowZero() {
        list.remove(-1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoveIndexAboveMax() {
        list.remove(5);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoveOnEmptyList() {
        list.clear();
        list.remove(0);
    }

    @Test
    public void testRemoveObject() {
        assertTrue(list.remove("Third"));
        assertEquals("Second", list.get(1));
        assertEquals("Fourth", list.get(2));

        list.remove("First");
        assertEquals(3, list.size());
        assertEquals("Second", list.get(0));

        list.remove("Fifth");
        assertEquals(2, list.size());
        assertEquals("Fourth", list.get(1));

        list.remove("ABC");
        assertEquals(2, list.size());
        assertEquals("Second", list.get(0));
        assertEquals("Fourth", list.get(1));
    }

    @Test
    public void testRemoveDuplicates() {
        list.add("First");
        list.add("Third");
        list.add("Fifth");
        assertEquals("[First, Second, Third, Fourth, Fifth, First, Third, Fifth]", list.toString());

        list.remove("Third");
        assertEquals("[First, Second, Fourth, Fifth, First, Third, Fifth]", list.toString());
        list.remove("Third");
        assertEquals("[First, Second, Fourth, Fifth, First, Fifth]", list.toString());
        list.remove("First");
        assertEquals("[Second, Fourth, Fifth, First, Fifth]", list.toString());
        list.remove("Second");
        assertEquals("[Fourth, Fifth, First, Fifth]", list.toString());
        list.remove("Fifth");
        assertEquals("[Fourth, First, Fifth]", list.toString());
        list.remove("Fifth");
        assertEquals("[Fourth, First]", list.toString());

        list.add(0, "A");
        list.add("B");
        assertEquals("[A, Fourth, First, B]", list.toString());
    }

    private static final java.util.Random rnd = new java.util.Random();
    private static final String[] names = { "Adam", "Bertil", "Cesar", "David", "Erik", "Filip", "Gustav", "Helge",
            "Ivar", "Johan", "Kalle", "ludvig", "Martin", "Niklas" };

    private String randomName() {
        return names[rnd.nextInt(names.length)];
    }

    private void testBeforeAndAfterRandomOperation(java.util.List<String> oracle) {
        // Here you can put test code that should be executed before and after
        // each random operation in the test below.
        assertEquals(oracle.size(), list.size());
        for (int n = 0; n < oracle.size(); n++) {
            assertEquals(oracle.get(n), list.get(n));
        }
        assertEquals(oracle.toString(), list.toString());
    }

    @Test
    public void testMix() {
        list.clear();
        java.util.List<String> oracle = new java.util.ArrayList<String>();

        for (int n = 0; n < 1000; n++) {
            String name = randomName();

            // Random insert
            switch (rnd.nextInt(5)) {
                case 0:
                    testBeforeAndAfterRandomOperation(oracle);
                    list.add(name);
                    oracle.add(name);
                    testBeforeAndAfterRandomOperation(oracle);
                    break;
                case 1:
                    testBeforeAndAfterRandomOperation(oracle);
                    list.add(0, name);
                    oracle.add(0, name);
                    testBeforeAndAfterRandomOperation(oracle);
                    break;
                case 2:
                    testBeforeAndAfterRandomOperation(oracle);
                    list.add(list.size(), name);
                    oracle.add(oracle.size(), name);
                    testBeforeAndAfterRandomOperation(oracle);
                    break;
                case 3:
                case 4:
                    testBeforeAndAfterRandomOperation(oracle);
                    int index = list.size() == 0 ? 0 : rnd.nextInt(list.size());
                    list.add(index, name);
                    oracle.add(index, name);
                    testBeforeAndAfterRandomOperation(oracle);
                    break;
            }

            if (oracle.size() > 0) {

                // Random removal 70% of the times
                switch (rnd.nextInt(10)) {
                    case 3:
                        testBeforeAndAfterRandomOperation(oracle);
                        list.remove(0);
                        oracle.remove(0);
                        testBeforeAndAfterRandomOperation(oracle);
                        break;
                    case 4:
                        testBeforeAndAfterRandomOperation(oracle);
                        list.remove(list.size() - 1);
                        oracle.remove(oracle.size() - 1);
                        testBeforeAndAfterRandomOperation(oracle);
                        break;
                    case 5:
                    case 6:
                        testBeforeAndAfterRandomOperation(oracle);
                        int index = rnd.nextInt(list.size());
                        list.remove(index);
                        oracle.remove(index);
                        testBeforeAndAfterRandomOperation(oracle);
                        break;
                    case 7:
                    case 8:
                        testBeforeAndAfterRandomOperation(oracle);
                        name = randomName();
                        list.remove(name);
                        oracle.remove(name);
                        testBeforeAndAfterRandomOperation(oracle);
                        break;
                    case 9:
                        testBeforeAndAfterRandomOperation(oracle);
                        if (rnd.nextInt(10) < 2) {
                            list.clear();
                            oracle.clear();
                        }
                        testBeforeAndAfterRandomOperation(oracle);
                }
            }

            if (oracle.size() == 0) {
                assertEquals(0, list.size());
            } else {
                // Random check
                switch (rnd.nextInt(10)) {
                    case 0:
                        assertEquals(oracle.size(), list.size());
                        break;
                    case 1:
                        assertEquals(oracle.get(0), list.get(0));
                        break;
                    case 2:
                        assertEquals(oracle.get(oracle.size() - 1), list.get(list.size() - 1));
                        break;
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                    case 8:
                        int index = rnd.nextInt(list.size());
                        assertEquals(oracle.get(index), list.get(index));
                        break;
                    case 9:
                        assertEquals(oracle.toString(), list.toString());
                        break;
                }
            }
        }

    }

    @Test
    public void testIsItearble() {
        for (String s : list)
            // This code is not necessay byt removes a warning that s isn't
            // used.
            s.length();
    }

    @Test
    public void testBasicIteration() {
        Iterator<String> i = list.iterator();
        assertTrue(i.hasNext());
        assertEquals("First", i.next());
        assertTrue(i.hasNext());
        assertEquals("Second", i.next());
        assertTrue(i.hasNext());
        assertEquals("Third", i.next());
        assertTrue(i.hasNext());
        assertEquals("Fourth", i.next());
        assertTrue(i.hasNext());
        assertEquals("Fifth", i.next());
        assertFalse(i.hasNext());
    }

    @Test(expected = NoSuchElementException.class)
    public void testToLongIteration() {
        Iterator<String> i = list.iterator();
        for (int n = 0; n <= list.size(); n++) {
            i.next();
        }
    }

    @Test(expected = NoSuchElementException.class)
    public void testIterationOnEmptyList() {
        list.clear();
        Iterator<String> i = list.iterator();
        assertFalse(i.hasNext());
        i.next();
    }

    @Test
    public void testMultipleConcurrentIterators() {
        Iterator<String> i1 = list.iterator();
        assertTrue(i1.hasNext());
        assertEquals("First", i1.next());
        assertEquals("Second", i1.next());
        Iterator<String> i2 = list.iterator();
        assertTrue(i2.hasNext());
        assertEquals("First", i2.next());
        assertEquals("Third", i1.next());
        assertEquals("Second", i2.next());
        assertEquals("Fourth", i1.next());
        assertEquals("Third", i2.next());
        assertEquals("Fourth", i2.next());
        assertEquals("Fifth", i2.next());
        assertEquals("Fifth", i1.next());
        assertFalse(i1.hasNext());
        assertFalse(i2.hasNext());
    }

    @Test
    public void testRemoveOnIterator() {
        Iterator<String> i = list.iterator();
        assertEquals("First", i.next());
        i.remove();
        assertEquals(4, list.size());
        assertEquals("Second", list.get(0));
        assertEquals("Second", i.next());
        assertEquals("Third", i.next());
        i.remove();
        assertEquals(3, list.size());
        assertEquals("Second", list.get(0));
        assertEquals("Fourth", list.get(1));
        assertEquals("Fourth", i.next());
        assertEquals("Fifth", i.next());
        i.remove();
        assertEquals(2, list.size());
        assertEquals("Second", list.get(0));
        assertEquals("Fourth", list.get(1));
    }

    @Test(expected = IllegalStateException.class)
    public void testRemoveOnIteratorWithoutNext() {
        Iterator<String> i = list.iterator();
        i.remove();
    }

    @Test(expected = IllegalStateException.class)
    public void testRemoveOnIteratorTwice() {
        Iterator<String> i = list.iterator();
        i.next();
        i.remove();
        i.remove();
    }

    @Test
    public void testOtherTypesOfData() {
        ALDAList<Integer> l = createIntegerList();
        l.add(5);
        l.add(new Integer(10));
        assertEquals(new Integer(5), l.get(0));
        assertEquals(new Integer(10), l.get(1));
    }

}