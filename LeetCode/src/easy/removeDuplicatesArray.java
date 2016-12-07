package easy;

public class removeDuplicatesArray {
	public static void main(String[] args){
		int[] a = {1,1,2,3,4,5,5,7,8,8};
		System.out.println(removeDuplicates(a));
	}
	
    public static int removeDuplicates(int[] nums) {
        int count = 0;
        int index = 0;
        for(int next = 1; next < nums.length; next ++){
        	if(nums[index] != nums[next]){
        		count ++;
        		index = next;
        	}
        }
        return count + 1;
    }
}
