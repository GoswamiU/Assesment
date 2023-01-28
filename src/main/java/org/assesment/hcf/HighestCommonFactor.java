package org.assesment.hcf;

import java.util.Arrays;

public class HighestCommonFactor {


    public static void main(String[] args) {
        int[] nums=new int[]{12, 9, 6, 15};
        int num=highestCommonFactor(nums);
        System.out.println(num);
    }


    public static int highestCommonFactor(int[] numbers) {
        if (numbers==null||numbers.length==0){
            return 0;
        }
        Arrays.sort(numbers);
        int max=0;
        for (int i=numbers[0];i>0;i--){
            for (int j=0;j<numbers.length;j++){
                if (numbers[j]%i==0){
                    max=i;
                    continue;
                }else {
                    max=0;
                    break;
                }
            }
            if (max!=0){
                return max;
            }
        }
        return 0;
    }
}
