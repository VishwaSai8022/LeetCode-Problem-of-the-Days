class Solution {
    public int gcdOfOddEvenSums(int n) {
        int sumOdd = 0;
        int sumEven = 0;
        for (int i = 1; i <= n; i++) {
            sumOdd += (2 * i - 1);
            sumEven += (2 * i);
        }
        int Ln = Math.max(sumOdd,sumEven);
        int Sn = Math.min(sumOdd,sumEven);
        int temp = 0;
        while(Sn != 0)
        {
            temp = Sn;
            Sn = Ln % Sn;
            Ln = temp;
        }
        return Ln;
    }
}