package com.ot.concurrent.aqs.list;

import java.util.Arrays;
import java.util.Collection;

public class ArrayList implements List {

    private Object[] elementData;//底层数组,还没有确定长度
    private static final Object[] empty_elementData=new Object[0];
    private static final Object[] defaultcapacity_empty_elementdata=new Object[0];
    private int size;//不是分配的数组的长度，而是元素的个数

    public ArrayList() {
        //没有指定长度，默认长度为4
        //this(4);
        //没有指定长度，默认长度是0,在ArrayList中没有指定长度即为0
        elementData=defaultcapacity_empty_elementdata;
    }

    /**
     * 数组的初始长度
     *
     * @param initialCapacity
     */
    public ArrayList(int initialCapacity) {
        if (initialCapacity>0){
            //给数组分配空间
            elementData = new Object[initialCapacity];
        }else {
            if (initialCapacity!=0) throw new IllegalArgumentException("Illegal capacity:"+initialCapacity);

        }
        //指定顺序表的元素个数，默认是0
        size = 0;
    }
    public ArrayList(Collection var1){
        if ( var1==null) throw new NullPointerException("");
        elementData = var1.toArray();
        if ((this.size=this.elementData.length)!=0){
            if (this.elementData.getClass()!=Object[].class){
                this.elementData=Arrays.copyOf(this.elementData,this.size);
            }
        }else {
            this.elementData=empty_elementData;
        }
    }

    public int size() {
        return size;
    }

    public void add(Object e) {
        this.add(size, e);
    }
    public void add(int index, Object e) {
        //判断i,源码中使用方法判断下标是否有问题
        if (index < 0 || index > size) {
            throw new ArrayIndexOutOfBoundsException("数组索引异常" + index);
        }
        //如果数组满了，扩容，刚开始初始容量为0，必须扩容，
        ensureCapacity(size+1);
        //后移元素
//        for (int i=size;i>index;i--){
//            elementData[i]=elementData[i-1];
//        }
        //后移元素的第二种写法，源码写法，这样记忆，数组的下标就是指它前面有几个元素，比如说我们要插入的元素的下标是2，目前
        //elementData数组共有4个元素，所要插入元素位置前面有2个元素，故需要移动的长度是4-2
        System.arraycopy(elementData, index, elementData, index + 1, size - index);
        //给数组第index个元素赋值
        elementData[index] = e;
        //元素个数+1;
        size++;
    }
    public void ensureCapacity(int minCapacity){
        ensureExplicitCapacity(CalculateCapacity(elementData,minCapacity));
    }
    public void ensureExplicitCapacity(int minCapacity){
        if (minCapacity-elementData.length>0){
            grow(minCapacity);
        }
    }
    public int CalculateCapacity(Object[] elementData,int minCapacity){
        if (elementData==defaultcapacity_empty_elementdata){
            return Math.max(10,minCapacity);//空参构造，刚创建长度为0，初始化之后长度是10，之后在新增元素到10之前，长度不会改变，
                                            //当新增元素到10之后，每次新增确定最小数是当前size+1，然后对当前求出当前数组的新长度
                                            //默认1.5倍扩容，所以之后是按照1.5倍扩容。默认记着1.5倍扩容策略
        }
        return minCapacity;
    }
    private void grow(int minCapacity) {
        int oldCapacity = elementData.length;
        int newCapacity = oldCapacity + (oldCapacity >> 1);//
        if (newCapacity - minCapacity < 0){
            newCapacity = minCapacity;
        }
        elementData = Arrays.copyOf(elementData, newCapacity);
    }

    @Override
    public Object get(int index) {
        if (index < 0 || index >= size) {
            throw new ArrayIndexOutOfBoundsException("数组越界异常:" + index);
        }
        return elementData[index];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public String toString() {
        if (size == 0) return "[]";
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            if (i == size - 1) {
                sb.append(elementData[i]);
            } else {
                sb.append(elementData[i] + ",");
            }
        }
        sb = sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean remove(Object o) {
        if (o == null){
            for (int i = 0; i < elementData.length; i++) {
                if (elementData[i] == null){
                    remove(i);
                    return true;
                }
            }
        }else {
            for (int i = 0; i < elementData.length; i++) {
                if (o.equals(elementData[i])){
                    remove(i);
                    return true;
                }
            }
        }
        return false;
    }
    public Object remove(int index){
        Object o=elementData[index];
        System.arraycopy(elementData,index+1,elementData,index,size-index-1);

        //删除了之后需要将最后一个元素设置为null，防止内存泄漏,删除之后数组长度未改变，需要将末尾元素置为null
        elementData[--size]=null;
        return o;
    }

    public boolean contains(Object e) {
        if (e == null) {
            for (int i = 0; i < size; i++)
                if (elementData[i] == null)
                    return true;
        } else {
            for (int i = 0; i < size; i++)
                if (e.equals(elementData[i]))
                    return true;
        }
        return false;
    }
    private void trimToSize(){
        if (size<elementData.length){
            elementData=(size==0)?empty_elementData:Arrays.copyOf(elementData,size);
        }
    }
}
