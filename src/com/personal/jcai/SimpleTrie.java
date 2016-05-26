package com.personal.jcai;

/**
 * Created by jcai on 12/28/15.
 */
import java.util.*;

public class SimpleTrie {
    public final static int NULL = -1;
    public final static int SPACE = 26;

    private class Node {
        public int[] next = new int[27];
        public int wordCount;
        public Node() {
            for(int i=0;i<27;i++) {
                next[i] = NULL;
            }
            wordCount = 0;
        }
    }
    private int curr;
    private Node[] nodes;
    private List<String> allDistinctWords;

    public SimpleTrie() {
        nodes = new Node[100000];
        nodes[0] = new Node();
        curr = 1;
    }
    private int getIndex(char c) {
        if(c != ' ') {
            return (int) (c - 'a');
        } else {
            return SPACE;
        }
    }
    private void depthSearchWord(int x, String currWord) {
        for(int i=0;i<27;i++) {
            int p = nodes[x].next[i];
            if(p != NULL) {
                String word;
                if(i != 26) {
                    word = currWord + (char) (i + 'a');
                } else{
                    word = currWord + ' ';
                }
                if(nodes[p].wordCount > 0) {
                    allDistinctWords.add(word);
                }
                depthSearchWord(p, word);
            }
        }
    }
    public List<String> getAllDistinctWords() {
        allDistinctWords = new ArrayList<String>();
        depthSearchWord(0, "");
        return allDistinctWords;
    }
    public int getWordCount(String str) {
        int len = str.length();
        int p = 0;
        for(int i=0;i<len;i++) {
            int j = getIndex(str.charAt(i));
            if(nodes[p].next[j] == NULL) {
                return 0;
            }
            p = nodes[p].next[j];
        }
        return nodes[p].wordCount;
    }
    public boolean contains(String str) {
        int len = str.length();
        int p = 0;
        for(int i=0;i<len;i++) {
            int j = getIndex(str.charAt(i));
            if(nodes[p].next[j] == NULL) {
                return false;
            }
            p = nodes[p].next[j];
        }
        return nodes[p].wordCount > 0;
    }
    public void insert(String str) {
        int len = str.length();
        int p = 0;
        for(int i=0;i<len;i++) {
            int j = getIndex(str.charAt(i));
            if(nodes[p].next[j] == NULL) {
                nodes[curr] = new Node();
                nodes[p].next[j] = curr;
                curr++;
            }
            p = nodes[p].next[j];
        }
        nodes[p].wordCount++;
    }
}
