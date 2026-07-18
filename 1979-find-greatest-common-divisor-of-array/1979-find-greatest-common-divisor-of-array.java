class Solution {
    public int findGCD(int[] nums) {
        int largest = Integer.MIN_VALUE;
        int smallest = Integer.MAX_VALUE;
        for(int i = 0;i < nums.length;i++)
        {
            largest = Math.max(largest,nums[i]);
            smallest = Math.min(smallest,nums[i]);
        }
        int temp = 0;
        while(smallest != 0)
        {
            temp = smallest;
            smallest = largest % smallest;
            largest = temp; 
        }
        return largest;
    }
}