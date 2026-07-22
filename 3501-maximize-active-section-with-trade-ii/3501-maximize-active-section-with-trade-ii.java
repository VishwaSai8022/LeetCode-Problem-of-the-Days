import java.util.*;

public class Solution {
    static class Group {
        int start;
        int length;
        Group(int start, int length) {
            this.start = start;
            this.length = length;
        }
    }

    static class SparseTable {
        int[][] st;
        int[] log;

        SparseTable(int[] nums) {
            int n = nums.length;
            if (n == 0) return;
            int maxLog = Integer.SIZE - Integer.numberOfLeadingZeros(n);
            st = new int[maxLog][n];
            log = new int[n + 1];

            for (int i = 0; i < n; i++) {
                st[0][i] = nums[i];
            }

            for (int j = 1; j < maxLog; j++) {
                for (int i = 0; i + (1 << j) <= n; i++) {
                    st[j][i] = Math.max(st[j - 1][i], st[j - 1][i + (1 << (j - 1))]);
                }
            }

            for (int i = 2; i <= n; i++) {
                log[i] = log[i / 2] + 1;
            }
        }

        int query(int l, int r) {
            if (l > r) return 0;
            int k = log[r - l + 1];
            return Math.max(st[k][l], st[k][r - (1 << k) + 1]);
        }
    }

    public List<Integer> maxActiveSectionsAfterTrade(String s, int[][] queries) {
        int n = s.length();
        int totalOnes = 0;
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '1') totalOnes++;
        }

        // 1. Extract all zero groups
        List<Group> zeroGroups = new ArrayList<>();
        int[] zeroGroupIndex = new int[n];
        Arrays.fill(zeroGroupIndex, -1);

        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '0') {
                if (i > 0 && s.charAt(i - 1) == '0') {
                    zeroGroups.get(zeroGroups.size() - 1).length++;
                } else {
                    zeroGroups.add(new Group(i, 1));
                }
                zeroGroupIndex[i] = zeroGroups.size() - 1;
            }
        }

        int m = zeroGroups.size();
        
        // Quick lookup arrays for boundary zero groups in O(1)
        int[] firstZeroGroupAtOrAfter = new int[n + 1];
        firstZeroGroupAtOrAfter[n] = m;
        int curr = m;
        for (int i = n - 1; i >= 0; i--) {
            if (s.charAt(i) == '0') {
                curr = zeroGroupIndex[i];
            }
            firstZeroGroupAtOrAfter[i] = curr;
        }

        int[] lastZeroGroupAtOrBefore = new int[n + 1];
        curr = -1;
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '0') {
                curr = zeroGroupIndex[i];
            }
            lastZeroGroupAtOrBefore[i] = curr;
        }

        // 2. Precompute adjacent zero-group merge gains for Sparse Table
        if (m < 2) {
            List<Integer> ans = new ArrayList<>(queries.length);
            for (int i = 0; i < queries.length; i++) {
                ans.add(totalOnes);
            }
            return ans;
        }

        int[] mergeGains = new int[m - 1];
        for (int i = 0; i < m - 1; i++) {
            mergeGains[i] = zeroGroups.get(i).length + zeroGroups.get(i + 1).length;
        }
        SparseTable st = new SparseTable(mergeGains);

        // 3. Process each query
        List<Integer> ans = new ArrayList<>(queries.length);

        for (int[] query : queries) {
            int l = query[0];
            int r = query[1];

            int minZeroIdx = firstZeroGroupAtOrAfter[l];
            int maxZeroIdx = lastZeroGroupAtOrBefore[r];

            // Need at least 2 zero groups in s[l...r] to bound a 1-group
            if (minZeroIdx >= maxZeroIdx || minZeroIdx >= m || maxZeroIdx < 0) {
                ans.add(totalOnes);
                continue;
            }

            // Left zero group clipped length
            int leftLen = (minZeroIdx == zeroGroupIndex[l])
                    ? (zeroGroups.get(minZeroIdx).start + zeroGroups.get(minZeroIdx).length - l)
                    : zeroGroups.get(minZeroIdx).length;

            // Right zero group clipped length
            int rightLen = (maxZeroIdx == zeroGroupIndex[r])
                    ? (r - zeroGroups.get(maxZeroIdx).start + 1)
                    : zeroGroups.get(maxZeroIdx).length;

            int maxGain = 0;

            if (minZeroIdx == maxZeroIdx - 1) {
                maxGain = leftLen + rightLen;
            } else {
                maxGain = Math.max(leftLen + zeroGroups.get(minZeroIdx + 1).length,
                                   zeroGroups.get(maxZeroIdx - 1).length + rightLen);

                if (minZeroIdx + 1 <= maxZeroIdx - 2) {
                    maxGain = Math.max(maxGain, st.query(minZeroIdx + 1, maxZeroIdx - 2));
                }
            }

            ans.add(totalOnes + maxGain);
        }

        return ans;
    }
}