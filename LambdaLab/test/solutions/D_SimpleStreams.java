package solutions;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * This set of exercises covers simple stream pipelines,
 * including intermediate operations and basic collectors.
 *
 * Some of these exercises use a BufferedReader variable
 * named "reader" that the test has set up for you.
 */
public class D_SimpleStreams {
    /**
     * Given a list of words, create an output list that contains
     * only the odd-length words, converted to upper case.
     */
    @Test
    public void ex06_upcaseOddLengthWords() {
        List<String> input = Arrays.asList(
            "alfa", "bravo", "charlie", "delta", "echo", "foxtrot");

        //TODO//List<String> result = null;
        //BEGINREMOVE
        List<String> result =
            input.stream()
                 .filter(w -> (w.length() & 1) == 1)
                 .map(String::toUpperCase)
                 .collect(Collectors.toList());
            // Alternatives:
            // Instead of w -> (w.length() & 1) == 1, use w -> (w.length() % 2) != 0
            // Instead of String::toUpperCase, use w -> w.toUpperCase()
        //ENDREMOVE

        assertEquals("[BRAVO, CHARLIE, DELTA, FOXTROT]", result.toString());
    }
    // Hint 1:
    // <editor-fold defaultstate="collapsed">
    // Use filter() and map().
    // </editor-fold>
    // Hint 2:
    // <editor-fold defaultstate="collapsed">
    // Use collect() to create the result list.
    // </editor-fold>


    /**
     * Take the third through fifth words of the list, extract the
     * second letter from each, and join them, separated by commas,
     * into a single string. Watch for off-by-one errors.
     */
    @Test
    public void ex07_joinStreamRange() {
        List<String> input = Arrays.asList(
            "alfa", "bravo", "charlie", "delta", "echo", "foxtrot");

        //TODO//String result = "";
        //BEGINREMOVE
        String result =
            input.stream()
                 .skip(2)
                 .limit(3)
                 .map(word -> word.substring(1, 2))
                 .collect(Collectors.joining(","));
        //ENDREMOVE

        assertEquals("h,e,c", result);
    }
    // Hint 1:
    // <editor-fold defaultstate="collapsed">
    // Use Stream.skip() and Stream.limit().
    // </editor-fold>
    // Hint 2:
    // <editor-fold defaultstate="collapsed">
    // Use Collectors.joining().
    // </editor-fold>


    /**
     * Count the number of lines in the text file. (Remember to
     * use the BufferedReader named "reader" that has already been
     * opened for you.)
     *
     * @throws IOException
     */
    @Test
    public void ex08_countLinesInFile() throws IOException {
        //TODO//long count = 0;
        //BEGINREMOVE
        long count = reader.lines()
                           .count();
        //ENDREMOVE

        assertEquals(14, count);
    }
    // Hint 1:
    // <editor-fold defaultstate="collapsed">
    // Use BufferedReader.lines() to get a stream of lines.
    // </editor-fold>
    // Hint 2:
    // <editor-fold defaultstate="collapsed">
    // Use Stream.count().
    // </editor-fold>


    /**
     * Find the length of the longest line in the text file.
     *
     * @throws IOException
     */
    @Test
    public void ex09_findLengthOfLongestLine() throws IOException {
        //TODO//int longestLength = 0;
        //BEGINREMOVE
        int longestLength =
            reader.lines()
                  .mapToInt(String::length)
                  .max()
                  .orElse(0);
        //ENDREMOVE

        assertEquals(53, longestLength);
    }
    // Hint 1:
    // <editor-fold defaultstate="collapsed">
    // Use Stream.mapToInt() to convert to IntStream.
    // </editor-fold>
    // Hint 2:
    // <editor-fold defaultstate="collapsed">
    // Look at java.util.OptionalInt to get the result.
    // </editor-fold>


    /**
     * Find the longest line in the text file.
     *
     * @throws IOException
     */
    @Test
    public void ex10_findLongestLine() throws IOException {
        //TODO//String longest = null;
        //BEGINREMOVE
        String longest =
            reader.lines()
                  .max(Comparator.comparingInt(String::length))
                  .orElse("");
            // Alternative:
            // Instead of Comparator.comparingInt(String::length), one could
            // use something like:
            //     (s1, s2) -> Integer.compare(s1.length(), s2.length())
        //ENDREMOVE

        assertEquals("Feed'st thy light's flame with self-substantial fuel,", longest);
    }
    // Hint 1:
    // <editor-fold defaultstate="collapsed">
    // Use Stream.max() with a Comparator.
    // </editor-fold>
    // Hint 2:
    // <editor-fold defaultstate="collapsed">
    // Use static methods on Comparator to help create a Comparator instance.
    // </editor-fold>


    /**
     * Select the longest words from the input list. That is, select the words
     * whose lengths are equal to the maximum word length.
     */
    @Test
    public void ex10a_selectLongestWords() {
        List<String> input = Arrays.asList(
            "alfa", "bravo", "charlie", "delta", "echo", "foxtrot", "golf", "hotel");

        //TODO//List<String> result = null;
        //BEGINREMOVE
        int max = input.stream()
                       .mapToInt(String::length)
                       .max()
                       .orElse(-1);

        List<String> result = input.stream()
                                   .filter(s -> s.length() == max)
                                   .collect(Collectors.toList());
        //ENDREMOVE

        assertEquals("[charlie, foxtrot]", result.toString());
    }
    // Hint:
    // <editor-fold defaultstate="collapsed">
    // Consider making two passes over the input stream.
    // </editor-fold>

    /**
     * Select the list of words from the input list whose length is greater than
     * the word's position in the list (starting from zero) .
     */
    @Test
    public void ex11_selectByLengthAndPosition() {
        List<String> input = Arrays.asList(
            "alfa", "bravo", "charlie", "delta", "echo", "foxtrot", "golf", "hotel");

        //TODO//List<String> result = null;
        //BEGINREMOVE
        List<String> result =
            IntStream.range(0, input.size())
                     .filter(pos -> input.get(pos).length() > pos)
                     .mapToObj(pos -> input.get(pos))
                     .collect(Collectors.toList());
        //ENDREMOVE

        assertEquals(Arrays.asList("alfa", "bravo", "charlie", "delta", "foxtrot"), result);
    }
    // Hint:
    // <editor-fold defaultstate="collapsed">
    // Instead of a stream of words (Strings), run an IntStream of indexes of
    // the input list, using index values to get elements from the input list.
    // </editor-fold>


// ========================================================
// END OF EXERCISES
// TEST INFRASTRUCTURE IS BELOW
// ========================================================


    private BufferedReader reader;

    @Before
    public void z_setUpBufferedReader() throws IOException {
        reader = Files.newBufferedReader(
                Paths.get("SonnetI.txt"), StandardCharsets.UTF_8);
    }

    @After
    public void z_closeBufferedReader() throws IOException {
        reader.close();
    }

}
