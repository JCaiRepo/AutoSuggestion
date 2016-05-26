package com.personal.jcai;

public class Main {

    public static void main(String[] args) {
	// write your code here
        Trie trie = new Trie();
//        SimpleTrie trie = new SimpleTrie();

        trie.insert("abcde fgd");
        trie.insert("abc");
        trie.insert("sadas poiu");
        trie.insert("abc");
        trie.insert("wqwqd");
        System.out.println(trie.contains("abc"));
        System.out.println(trie.contains("abcd"));
        System.out.println(trie.contains("abcdefg"));
        System.out.println(trie.contains("ab"));
        System.out.println(trie.getWordCount("abc"));
        System.out.println(trie.contains("sadas poiu"));
        System.out.println(trie.getAllDistinctWords());
        System.out.println(trie.getAllSuggestedWords("ab"));
    }
}
