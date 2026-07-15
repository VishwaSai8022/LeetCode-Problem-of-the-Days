class Solution {
    public int gcdOfOddEvenSums(int n) {
        int sumOdd = 0;
        int sumEven = 0;
        for (int i = 1; i <= n; i++) {
            sumOdd += (2 * i - 1);
            sumEven += (2 * i);
        }
        int gcd = 1;
        for(int i = 1;i < Math.min(sumOdd,sumEven);i++)
        {
            if(sumOdd % i == 0 && sumEven % i == 0)
            {
                gcd = i;
            }
        }
        return gcd;
    }
}