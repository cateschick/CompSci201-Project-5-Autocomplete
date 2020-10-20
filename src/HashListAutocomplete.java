import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

public class HashListAutocomplete implements Autocompletor {
    // initialize given variables
    private static final int MAX_PREFIX = 10;
    private Map<String, List<Term>> myMap;
    private int mySize;

/**
 * Checks to see if either terms or weights are null.
 * If so, throws a Null Pointer Exception
 * @param terms
 * @param weights
 */
public HashListAutocomplete(String[] terms, double[] weights){
    if (terms == null || weights == null) {
        throw new NullPointerException("One or more arguments null");}
        // implement constructor call to initialize
        initialize(terms, weights);
}

    @Override
    public List<Term> topMatches(String prefix, int k) {

    if (prefix.length() > MAX_PREFIX) {
        prefix = prefix.substring(0, MAX_PREFIX);
    }
    if (myMap.containsKey(prefix)) {
        List<Term> all = myMap.get(prefix);
        List<Term> list = all.subList(0, Math.min(k, all.size()));

        return list;
    }
    return new ArrayList<>();
    }


    /**
     * Creates internal state needed to store Term objects
     * @param terms is array of Strings for words in each Term
     * @param weights is corresponding weight for word in terms
     */
    @Override
    public void initialize(String[] terms, double[] weights) {
        // initialize myMap
        myMap = new HashMap<>();

        // iterate through terms
        for (int i = 0; i < terms.length; i++) {
            for (int k = 0; k <= Math.min(terms[i].length(), MAX_PREFIX); k++) {
                String prefix = terms[i].substring(0, k);
                Term x = new Term(terms[i], weights[i]);
                myMap.putIfAbsent(prefix, new ArrayList<Term>());
                myMap.get(prefix).add(x);
            }
        }
        for (String s : myMap.keySet()) {
            List<Term> list = myMap.get(s);
            Collections.sort(list,Comparator.comparing(Term::getWeight).reversed());
        }

    }

    /**
     * Returns size in bytes of strings and doubles
     * used in implementing class
     * @return
     */
    @Override
    public int sizeInBytes() {
        if (mySize == 0) {
            // iterate through keys
            for (String s : myMap.keySet()) {
                // increment mySize
                mySize += s.length() * BYTES_PER_CHAR;
                List<Term> list = myMap.get(s);
                for (int i = 0; i < list.size(); i++) {
                    Term x = list.get(i);
                    mySize = mySize + BYTES_PER_DOUBLE + (BYTES_PER_CHAR * x.getWord().length());
                }
            }
        }
        return mySize;
    }
}