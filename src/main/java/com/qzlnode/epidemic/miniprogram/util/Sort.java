package com.qzlnode.epidemic.miniprogram.util;

/**
 * <h2>排序的工具类</h2>
 * @author qzlzzz
 */
public class Sort {
    /**
     * 三向切分快速排序的Api,其内部调用{@link Sort#quick3way(int[], int, int)}
     * @param a 要排序的数组
     */
    public static void quick3waySort(int[] a){
        quick3way(a,0,a.length - 1);
    }

    /**
     * 插入排序的Api,其内部调用{@link Sort#insert(int[], int, int)}
     * @param a 要排序的数组
     */
    public static void insertSort(int[] a){
        insert(a,0,a.length - 1);
    }

    /**
     * 归并排序的Api,其内部调用{@link Sort#mergeSort(int[], int, int)}
     * @param a 要排序的数组
     */
    public static void mergeSort(int[] a){
        tmp = new int[a.length];
        mergeSort(a,0,a.length - 1);
    }

    private static int[] tmp;//应使用CAS

    /**
     * 三向切分的快速排序算法
     * @param a
     * @param lo
     * @param hi
     */
    private static void quick3way(int[] a,int lo,int hi){
        if(lo >= hi) return;
        int lt = lo,i = lo + 1,gt = hi;
        int val = a[lo];
        while(gt >= i){
            if(a[i] > val) exch(a,i++,lt++);
            else if(a[i] < val) exch(a,i,gt--);
            else i++;
        }
        quick3way(a,lo,lt - 1);
        quick3way(a,gt + 1,hi);
    }

    /**
     * 插入排序算法
     * @param a
     * @param lo
     * @param hi
     */
    private static void insert(int[] a,int lo,int hi){
        for(int i = lo + 1 ; i < hi ; i++){
            for(int j = i ; j > 0 && less(a[j],a[j - 1]) ; j--){
                exch(a,j,j-1);
            }
        }
    }

    /**
     * 自顶向下的归并排序算法
     * @param a
     * @param lo
     * @param hi
     */
    private static void mergeSort(int[] a,int lo,int hi){
        if(lo >= hi) return;
        int mid = (hi - lo) / 2;
        mergeSort(a,lo,mid);
        mergeSort(a,mid + 1,hi);
        merge(a,lo,hi);

    }

    /**
     * 原地归并操作
     * @param a
     * @param lo
     * @param hi
     */
    private static void merge(int[] a,int lo ,int hi){
        int i = lo,mid = (hi - lo)/2,j = mid + 1;
        for (int k = lo ; k <= hi ; k++){
            tmp[k] = a[k];
        }
        for(int k = lo ; k <= hi ; k++){
            if(i > mid) a[k] = tmp[j++];
            else if(j > hi) a[k] = tmp[i++];
            else if(less(tmp[j],tmp[i])) a[k] = tmp[j++];
            else a[k] = tmp[i++];
        }
    }

    /**
     * 交换操作
     * @param a
     * @param v
     * @param w
     */
    private static void exch(int[] a,int v,int w){
        int t = a[v];
        a[v] = a[w];
        a[w] = t;
    }

    /**
     * 比较操作
     * @param v
     * @param w
     * @return
     */
    private static boolean less(int v,int w){
        return v < w;
    }
}
