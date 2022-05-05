/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        int n = 0;
        RandomizedQueue<String> raq = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            n++;
            String in = StdIn.readString();

            /** Reservoir Sampling
             * 1. Keep the first k items in memory.
             2. When the i-th item arrives (for $i>k$):
             * with probability $k/i$, keep the new item
             and discard an old one,
             selecting which to replace at random,
             each with chance $1/k$.
             * with probability $1-k/i$, ignore the new one
             */
            if (n > k && StdRandom.uniform(n) < k) {
                raq.dequeue();
            }
            else if (n > k) {
                continue;
            }

            raq.enqueue(in);
        }
        if (k == 0) {
            StdOut.println();
        }
        /* else {
            for (int i = 0; i < k; i++) {
                StdOut.println(raq.dequeue());
            }
        } */
        // while (raq.size() - k > 0) raq.dequeue();
        for (int i = 0; i < k; i++) {
            StdOut.println(raq.dequeue());
        }


    }
}
