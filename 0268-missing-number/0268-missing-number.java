class Solution {
    public int missingNumber(int[] nums) {
        ArrayList<Integer> list = new ArrayList<>();
        for(int i = 0; i < nums.length;i++)
        {
            list.add(nums[i]);
        }
        int i = 0;
        while(list.contains(i))
        {
            i++;
        }
        return i;
    }
}