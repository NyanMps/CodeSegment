package sorting;

import org.junit.Test;

/**
 * 复习，排序算法
 * Created by 冰封承諾Andy on 2017/11/3.
 */
public class Simple {
    public static void main(String[] args) {
        int[] data = {1, 2, 8, 45, 10, 6, 74, 14, -1, 55, -7, -45, 63, 102, 78, 44, 56, 55, 78, 45, 108, 145, -45, 78, 54, 78, 3, 0, 74, 9, -5};
        long start = System.nanoTime();

        // selectionSort(data);
        // bubbleSort(data);
        bubbleSort2(data);

        System.out.println("耗时：" + (System.nanoTime() - start));
        printData(data);
    }

    /**
     * 冒泡排序
     * 它重复地走访过要排序的数列，一次比较(相邻)两个元素，如果他们的顺序错误就把他们交换过来。
     * 走访数列的工作是重复地进行直到没有再需要交换，也就是说该数列已经排序完成。
     * 这个算法的名字由来是因为越小的元素会经由交换慢慢“浮”到数列的顶端
     * <p>
     * 对于包含大量的元素的数列排序是很没有效率的
     */
    private static void bubbleSort(int[] data) {
        int temp;
        for (int i = 0; i < data.length - 1; i++) {
            // -1 避免越界
            for (int j = 0; j < data.length - 1 - i; j++) {
                if (data[j] > data[j + 1]) {
                    temp = data[j];
                    data[j] = data[j + 1];
                    data[j + 1] = temp;
                }
            }
        }
    }

    /**
     * 冒泡的第二种形式（还可以把上面的改为 i-- 的形式，j < x）
     */
    private static void bubbleSort2(int[] data) {
        int temp;
        boolean flag;
        do {
            flag = false;
            for (int i = 0; i < data.length - 1; i++) {
                if (data[i] > data[i + 1]) {
                    temp = data[i];
                    data[i] = data[i + 1];
                    data[i + 1] = temp;
                    flag = true;  // 只要有交换就说明还没排序完
                }
            }
        } while (flag);
    }

    /**
     * 选择排序
     * 首先在未排序序列中找到最小（大）元素，存放到排序序列的起始位置，
     * 然后，再从剩余未排序元素中继续寻找最小（大）元素，然后放到已排序序列的末尾。
     * 以此类推，直到所有元素均排序完毕。
     * <p>
     * 选择排序的主要优点与数据移动有关。如果某个元素位于正确的最终位置上，则它不会被移动。
     * 选择排序每次交换一对元素，它们当中至少有一个将被移到其最终位置上，
     * 因此对n个元素的表进行排序总共进行至多n-1次交换。
     * 在所有的完全依靠交换去移动元素的排序方法中，选择排序属于非常好的一种
     */
    private static void selectionSort(int[] data) {
        int temp;
        // 只循环到倒数第二个即可，最后一个没数可比
        for (int i = 0; i < data.length - 1; i++) {
            for (int j = i + 1; j < data.length; j++) {
                if (data[i] > data[j]) {
                    temp = data[i];
                    data[i] = data[j];
                    data[j] = temp;
                }
            }
        }
    }


    private static void printData(int[] data) {
        for (int num : data) {
            System.out.print(num + " ");
        }
    }

    @Test
    private void test() {
        int x = 0;  // 存储在栈里的变量不会被初始化，如果定义在全局就是在堆里
        System.out.println(x);
    }
}
