package com.personal.jcai;

/**
 * Created by jcai on 12/27/15.
 */

import java.util.*;

public class Trie {
    public static boolean DEBUG = false;
    public final static int NULL = -1;
    public final static int SPACE = 26;

    private class Node {
        //public int[] next = new int[26];
        // include space to support phrases
        public int[] next = new int[27];
        public int wordCount;
        public Node() {
            for(int i=0;i<27;i++) {
                next[i] = NULL;
            }
            wordCount = 0;
        }
        public int getNext(int idx) {
            return next[idx];
        }
        public void incCount(int n) {
            wordCount += n;
        }
    }

    private int curr;
    private ArrayList<Node> nodes;
    //private Node[] nodes;
    private List<String> allDistinctWords;
    private List<String> allSuggestedWords;

    public Trie() {
        //nodes = new Node[100000];
        // convert nodes to dynamic arrayList
        nodes = new ArrayList<Node>();
        Node tmp = new Node();
        nodes.add(tmp);
        curr = 1;
    }

    private int getIndex(char c) {
        if(c != ' ') {
            return (int) (c - 'a');
        } else {
            return SPACE;
        }
    }

    private void addDistinctWord(int x, String currWord) {
        for(int i=0;i<27;i++) {
            int p = nodes.get(x).next[i];
            if(p != NULL) {
                String word;
                if(i != 26) {
                    word = currWord + (char) (i + 'a');
                } else{
                    word = currWord + ' ';
                }
                if(nodes.get(p).wordCount > 0) {
                    allDistinctWords.add(word);
                }
                addDistinctWord(p, word);
            }
        }
    }

    public List<String> getAllDistinctWords() {
        allDistinctWords = new ArrayList<String>();
        addDistinctWord(0, "");
        return allDistinctWords;
    }

    private void depthSearchWord(String currWord) {
        int p = 0;
        int len = currWord.length();
        int j, i;
        String word;

        i = 0;
        while(p != NULL) {

            if (i < len) {
                if (DEBUG) {
                    System.out.println("i is " + i);
                    System.out.println(i + "-th char in currWord " + currWord + " is " + currWord.charAt(i));
                }
                j = getIndex(currWord.charAt(i));
                p = nodes.get(p).getNext(j);

            } else if (i == len) {
                allSuggestedWords.add(currWord);
            } else if (i > len) {
                int k;
                word = currWord;
                for (k = 0; k < 27; k++) {
                    int idx = p;
                    while (idx != NULL) {
                        if (k != 26) {
                            word = word + (char) (k + 'a');
                        } else {
                            word = word + ' ';
                        }
                        allSuggestedWords.add(word);
                        //depthSearchWord(p, word);
                        idx = nodes.get(idx).getNext(k);
                    }
                    if (p != NULL) {
                        p = nodes.get(p).getNext(k);
                    } else {
                        break;
                    }
                }
            }
            i++;
        }
    }

    public List<String> getAllSuggestedWords(String prefix) {
        allSuggestedWords = new ArrayList<String>();
        depthSearchWord(prefix);
        return allSuggestedWords;
    }

    public int getWordCount(String str) {
        int len = str.length();
        int p = 0;
        for(int i=0;i<len;i++) {
            int j = getIndex(str.charAt(i));
            if(nodes.get(p).getNext(j) == NULL) {
                return 0;
            }
            p = nodes.get(p).getNext(j);
        }
        return nodes.get(p).wordCount;
    }

    public boolean contains(String str) {
        int len = str.length();
        int p = 0;
        for(int i=0;i<len;i++) {
            int j = getIndex(str.charAt(i));
            if(nodes.get(p).getNext(j) == NULL) {
                return false;
            }
            p = nodes.get(p).getNext(j);
        }
        return nodes.get(p).wordCount > 0;
    }

    public void insert(String str) {
        int len = str.length();
        int p = 0;
        for(int i=0;i<len;i++) {
            int j = getIndex(str.charAt(i));

            if (DEBUG || false) {
                System.out.println("node index - " + p);
                System.out.println("  char index - " + j + " char: " + str.charAt(i));
                System.out.println("  next - " + nodes.get(p).getNext(j));
            }

            if(nodes.get(p).getNext(j) == NULL) {
                Node tmp = new Node();
                //tmp = nodes.get(curr);
                nodes.add(tmp);
                nodes.get(p).next[j] = curr;
                curr++;
            }
            p = nodes.get(p).getNext(j);
        }
        nodes.get(p).incCount(1);
    }


}