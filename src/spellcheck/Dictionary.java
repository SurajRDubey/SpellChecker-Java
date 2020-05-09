package spellcheck;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException; 
import spellcheck.Dictionary.Bucket.Node;

public class Dictionary {
    private int M = 1319; 
    final private Bucket[] array;
    public Dictionary() {
        this.M = M;

      array = new Bucket[M];
       for (int i = 0; i < M; i++) {
          array[i] = new Bucket();
        }
    }

    private int hash(String key) {
        return (key.hashCode() & 0x7fffffff) % M;
    }

    /////////Here we are deciding which bucket to put it in, do it.
    public void add(String key) {
        array[hash(key)].put(key);
    }

    /////////find the bucket that contains hash
    public boolean contains(String input) {
        input = input.toLowerCase();
        return array[hash(input)].get(input);
    }

    public void build(String filePath) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                add(line);
        }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }
    /////////here we will generate random entries
    public String[] getRandomEntries(int num){
        String[] toRet = new String[num];
        for (int i = 0; i < num; i++){
            //pick a random bucket, go out a random number 
            Node n = array[(int)Math.random()*M].first;
            int rand = (int)Math.random()*(int)Math.sqrt(num);

            for(int j = 0; j<rand && n.next!= null; j++) n = n.next;
            toRet[i]=n.word;


        }
        return toRet;
    }

    class Bucket {

        private Node first;

        public boolean get(String in) {         ////////return key true if key exists
            Node next = first;
            while (next != null) {
                if (next.word.equals(in)) {
                    return true;
                }
                next = next.next;
            }
            return false;
        }

        public void put(String key) {
            for (Node curr = first; curr != null; curr = curr.next) {
                if (key.equals(curr.word)) {
                    return;                     ///////if found: return
                }
            }
            first = new Node(key, first); /////////is not found : add new node
        }

        class Node {

            String word;
            Node next;

            public Node(String key, Node next) {
                this.word = key;
                this.next = next;
            }

        }

    }
}