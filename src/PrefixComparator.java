import java.util.Comparator;

/**
 * Factor pattern for obtaining PrefixComparator objects
 * without calling new. Users simply use
 *
 *     Comparator<Term> comp = PrefixComparator.getComparator(size)
 *
 * @author owen astrachan
 * @date October 8, 2020
 */
public class PrefixComparator implements Comparator<Term> {

    private int myPrefixSize; // size of prefix

    /**
     * private constructor, called by getComparator
     * @param prefix is prefix used in compare method
     */
    private PrefixComparator(int prefix) {
        myPrefixSize = prefix;
    }


    /**
     * Factory method to return a PrefixComparator object
     * @param prefix is the size of the prefix to compare with
     * @return PrefixComparator that uses prefix
     */
    public static PrefixComparator getComparator(int prefix) {
       return new PrefixComparator(prefix);
    }


    @Override
    public int compare(Term v, Term w) {
        // change this to use myPrefixSize as specified
        int r = myPrefixSize;

        String a = v.getWord();
        String b = w.getWord();

        // if string lengths are greater than r, take a substring
        if (a.length() > r) {
            a = a.substring(0, r);
        }

        if (b.length() > r) {
            b = b.substring(0, r);
        }

        return a.compareTo(b);
    }
}
