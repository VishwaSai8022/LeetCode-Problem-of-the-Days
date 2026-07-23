class Solution {
    public boolean isPalindrome(int x) {
        int original = x;
        int reverse = 0;
        if(x < 0)
        {
            return false;
        }
        while(x > 0)
        {
            int last = x % 10;
            x = x / 10;
            reverse = reverse * 10 + last;
        }
        if(original == reverse)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}