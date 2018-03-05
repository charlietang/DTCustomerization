package com.datatalking.utility;



public class Sort {
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static synchronized void bubbleSort( Comparable[] args){
		if(args.length>0){
			int length=args.length;
			for(int i=1;i<length;i++){
				for(int j=0;j<length-i;j++){					
					int com=args[j].compareTo(args[j+1]);
					if(com>0){
						Comparable<?> temp=args[j];
						args[j]=args[j+1];
						args[j+1]=temp;
					}						
				}
			}
		}
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static synchronized void desc_bubbleSort( Comparable[] args){
		if(args.length>0){
			int length=args.length;
			for(int i=1;i<length;i++){
				for(int j=0;j<length-i;j++){					
					int com=args[j].compareTo(args[j+1]);
					if(com<0){
						Comparable<?> temp=args[j];
						args[j]=args[j+1];
						args[j+1]=temp;
					}						
				}
			}
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static synchronized void quickSort(Comparable[] args, int left, int right){
		Comparable strTemp;
		int i = left;
		int j = right;
		Comparable middle = args[(left+right)/2];
		do{
			while((args[i].compareTo(middle)<0) && (i<right))
				i++;
			while((args[j].compareTo(middle)>0) && (j>left))
				j--;
			if(i<=j){
				strTemp = args[i];
				args[i] = args[j];
				args[j] = strTemp;
				i++;
				j--;
			}
		}while(i<j);//如果两边扫描的下标交错，完成一次排序
		if(left<j)
			quickSort(args,left,j); //递归调用
		if(right>i)
			quickSort(args,i,right); //递归调用
	}
}
