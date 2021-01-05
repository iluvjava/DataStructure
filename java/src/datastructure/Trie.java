package datastructure;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.lang.System;

/**
 * A trie class better querying from a set of strings. * This is not token
 * based, it's string based and each token is the character.
 *  * Characters focused Trie, this is a tree. 
 *  * For each trie, we can query by one character and then look for another Trie within the current Trie.
 *  * This is not going to support sequence deletion for the simplicity 
 *  * Sequences long than 3000 characters are banned from this data structure because of recursive depths.
 */
class Trie
{

    public static void main(String[] args)
    {
        {
            Trie TheTrie = new Trie();
            TheTrie.add("this");
            TheTrie.add("the");
            TheTrie.add("there");
            System.out.println(TheTrie.traverseFromRoot());
            TheTrie.addToQueryPrefix('t');
            System.out.println(TheTrie.traverseFromPrefix());
            TheTrie.addToQueryPrefix('h');
            System.out.println(TheTrie.traverseFromPrefix());
            TheTrie.addToQueryPrefix('e');
            System.out.println(TheTrie.traverseFromPrefix());
            TheTrie.deleteQueryPrefix();
            TheTrie.deleteQueryPrefix();
            System.out.println(TheTrie.traverseFromPrefix());
            TheTrie.add("there is");
            System.out.println(TheTrie.traverseFromRoot());
        }

        {

        }
    }

    protected Map<Character, Trie> ThisLayer = new TreeMap<>();

    protected Set<Character> TerminatingCharacter = new TreeSet<Character>(); 
    // there is a token that terminates right here with that character.

    protected Stack<Trie> InnerTree;  // Trie that started with the given QueryPrefix. 

    protected StringBuilder PrefixQuery;  // Only the root trie should have this field. 

    public void add(String sequence)
    {

        char[] CharArr = sequence.toCharArray();
        List<Character> CharList = IntStream.range(0, sequence.length())
                    .mapToObj((I)-> (Character)CharArr[I]).collect(Collectors.toList());
        add(CharList);
    }

    /**
     * Adds a new sequence of character to the Trie data structure.
     * @param sequence
     */
    public void add(List<Character> sequence)
    {
        if (sequence.size() == 0)return;
        if (sequence.size() > 3000) throw new RuntimeException();
        Iterator<Character> Itr = sequence.iterator();
        Trie NextTrie = this;
        Character TheChar = Itr.next();
        while (Itr.hasNext())
        {   
            if 
            (
                !NextTrie.ThisLayer.containsKey(TheChar)
            )
            {
                NextTrie.appendCharacter(TheChar);
            }

            NextTrie = NextTrie.ThisLayer.get(TheChar);
            TheChar = Itr.next();
        }

        if (!NextTrie.ThisLayer.containsKey(TheChar))
        {
            NextTrie.appendCharacter(TheChar);
        }
        NextTrie.markLastCharacter(TheChar);
    }


    /**
     * Add a character, but this character is terminating one of the sequence.
     * @param c
     */
    public void markLastCharacter(char c)
    {
        if (!ThisLayer.containsKey(c))
        {
           throw new RuntimeException("Can't mark character not in the trie");
        }
        TerminatingCharacter.add(c);
    }

    /**
     * Add just one character to the, it will delete all sub trie on that caracter too.
     * internal use and external use. 
     * @param c
     */
    protected void appendCharacter(char c)
    {
        if (!ThisLayer.containsKey(c))
        ThisLayer.put(c, new Trie());
    }

    /**
     * Get the current query prefix from this tree. 
     * @return
     */
    public String currentQueryPrefix()
    {
        return PrefixQuery.toString();
    }

    /**
     * And an additional character to the query string. 
     */
    public void addToQueryPrefix(Character c)
    {
        if (InnerTree == null)
        {
            InnerTree = new Stack<>();
        }
        if (PrefixQuery == null)
        {
            PrefixQuery = new StringBuilder();
        }
        PrefixQuery.append(c);
        Trie InnerTreeTop = InnerTree.size() == 0? this : InnerTree.peek();
        if (InnerTreeTop.ThisLayer.containsKey(c))
        {
            InnerTree.add(InnerTreeTop.ThisLayer.get(c));
        }
        else
        {
            InnerTree.add(null);
        }
       
    }

    /**
     * Delete the last character put into the Query prefix. 
     */
    public void deleteQueryPrefix()
    {
        if (InnerTree.size() == 0) return;
        PrefixQuery.deleteCharAt(PrefixQuery.length() - 1);
        InnerTree.pop();
    }

    public Set<String> traverseFromRoot()
    {
        return traverseFromRoot(Integer.MAX_VALUE);
    }

    public Set<String> traverseFromPrefix()
    {
        return traverseFromPrefix(Integer.MAX_VALUE);
    }
   
    public Set<String> traverseFromRoot(int maxSize)
    {
        Set<String> AllSequences = new TreeSet<>();
        traverseFromRoot(new StringBuilder(), AllSequences, maxSize);
        return AllSequences;
    }

    public Set<String> traverseFromPrefix(int maxSize)
    {
        Set<String> accumulator = new TreeSet<>();
        traverseFromPrefix(new StringBuilder(), accumulator, maxSize);
        return accumulator;
    }

    /**
     * Traverse the tree but starts with the stored prefix of the tree. 
     * @param stringPath
     * @param accumulator
     */
    protected void traverseFromPrefix
    (
        StringBuilder stringPath, 
        Set<String> accumulator, 
        int maxSize
    )
    {
        if (InnerTree.size() == 0)
        {
            traverseFromRoot(stringPath, accumulator, maxSize);
            return;
        }

        if (InnerTree.peek() == null) return;
        InnerTree.peek().traverseFromRoot(stringPath, accumulator, maxSize);
    }

    protected void traverseFromPrefix
    (
        StringBuilder stringPath, 
        Set<String> accumulator
    )
    {
        traverseFromPrefix(stringPath, accumulator, Integer.MAX_VALUE);
    }

    protected void traverseFromRoot
    (
        StringBuilder stringPath, 
        Set<String> accumulator
    )
    {
        traverseFromRoot(stringPath, accumulator,Integer.MAX_VALUE);
    }

    /**
     *  Start traversing the current Trie, starting without any prefix.
     */
    protected void traverseFromRoot
    (
        StringBuilder stringPath, 
        Set<String> accumulator,
        int maxSize
    )
    {
        if (accumulator.size() >= maxSize)return; // Ok, we have enough. 
        for (Map.Entry<Character, Trie> TheEntry : ThisLayer.entrySet())
        {
            Character C = TheEntry.getKey();
            Trie InnerTrie = TheEntry.getValue();
            stringPath.append(C);
            if (TerminatingCharacter.contains(C))  // Prefix is already an element. 
            {
                accumulator.add(stringPath.toString());
            }
            InnerTrie.traverseFromRoot(stringPath, accumulator, maxSize);
            stringPath.deleteCharAt(stringPath.length() - 1);
        }
        return;
    }

    @Override
    public String toString()
    {
        
        StringBuilder Sb = new StringBuilder();
        Sb.append("The query String is: \n");
        Sb.append(traverseFromPrefix().toString() + "\n");
        return Sb.toString();
    }
    
}