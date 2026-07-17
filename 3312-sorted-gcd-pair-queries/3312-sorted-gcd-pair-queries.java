class Solution {
    public int[] gcdValues(int[] nums, long[] queries) {
        int max = 0;
        for (int x : nums)
            max = Math.max(max, x);

        int[] freq = new int[max + 1];

        for (int x : nums)
            freq[x]++;

        long[] pairsDiv = new long[max + 1];

        // Count pairs divisible by i
        for (int i = 1; i <= max; i++) {
            long cnt = 0;
            for (int j = i; j <= max; j += i)
                cnt += freq[j];
            pairsDiv[i] = cnt * (cnt - 1) / 2;
        }

        long[] exact = new long[max + 1];

        // Inclusion-Exclusion
        for (int i = max; i >= 1; i--) {
            exact[i] = pairsDiv[i];
            for (int j = i * 2; j <= max; j += i)
                exact[i] -= exact[j];
        }

        long[] prefix = new long[max + 1];
        for (int i = 1; i <= max; i++)
            prefix[i] = prefix[i - 1] + exact[i];

        int[] ans = new int[queries.length];

        for (int k = 0; k < queries.length; k++) {
            long target = queries[k] + 1;

            int l = 1, r = max;
            while (l < r) {
                int mid = (l + r) / 2;
                if (prefix[mid] >= target)
                    r = mid;
                else
                    l = mid + 1;
            }

            ans[k] = l;
        }

        return ans;
    }
}