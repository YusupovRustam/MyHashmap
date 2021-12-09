package com.company;

import java.util.*;

public class MyHashMap<K, V>implements Iterable<K> {
    private Array<K, V>[] arrays = new Array[15];
    private int size;

    /**
     * Berilgan key asosida value ni saqlab qo'yish kerak.
     * Agar  key (kiritilayotga) yangi bo'lsa  null return qiling.
     * Agar oldin mavjut bo'lgan key kelgan bo'lsa esgi value ni return qiling.
     */
    public V put(K key, V value) {

        int hashCode = key.hashCode();
        int index = hashCode % 16;
        Item<K, V> item = new Item(index, hashCode, key, value);
        if (arrays[index] == null) {
            arrays[index] = new Array();
            if (arrays[index].first == null) {
                arrays[index].first = item;
                arrays[index].last = item;
                size++;
            }
            return (V) item.getValue();
        } else {
            if (check(key) != null) {
                V old = (V) check(key).getValue();
                check(key).setValue(value);
                return old;
            }
            arrays[index].last.next = item;

            item.previos=arrays[index].last;
            arrays[index].last = item;
            size++;
        }
        return (V) item.getValue();
    }

    private Item<K, V> check(K key) {
        for (int i = 0; i < arrays.length; i++) {
            if (arrays[i] != null) {
                Item a = arrays[i].first;
                while (a != null) {
                    if (a.getKey().equals(key)) {
                        return a;
                    }
                    a = a.next;
                }
            }

        }
        return null;
    }

    private static int capacity=16; // sig'im

    /**
     * Capacity ga default  qiymat beradi. Default qiymat 16
     */
    public MyHashMap(MyHashMap<K,V> map) {
        putAll(map);
    }
    public MyHashMap() {
    }




    /**
     * Berilgan key MyHashMap da bo'lmasa  yanig key va value ni qo'shadi va value ni return qiladi.
     * Agar key bo'lsa null return qiladi
     */
    public V putIfAbsent(K key, V value) {
        if (check(key) != null) {
            return null;
        } else {
            size++;
            return put(key, value);
        }
    }

    /**
     * Berilgan key ga tegishli bo'lgan value ni return qiling.
     * Agar key bo'lmasa null retunr qiling.
     */
    public V get(K key) {
        int index=key.hashCode()%16;

            if (arrays[index] != null) {
                Item<K,V> a = arrays[index].first;
                while (a != null) {
                    if (a.getKey().equals(key)) {
                        return (V) a.getValue();
                    }
                    a = a.next;
                }
            }


        return null;
    }

    /**
     * Shunaqa key bormi yo'qmi shuni aniqlaydi.
     * Agar key bo'lsa ture bo'lmasa false return qiladi.
     */
    public Boolean containsKey(K key) {
        int index=key.hashCode()%16;
            if (arrays[index] != null) {
                Item<K, V> a = arrays[index].first;
                while (a != null) {
                    if (a.getKey().equals(key)) {
                        return true;
                    }
                    a = a.next;
                }
            }


        return false;
    }

    /**
     * Shunaqa value bormi yo'qmi shuni aniqlaydi.
     * Agar value bo'lsa ture bo'lmasa false return qiladi.
     */
    public Boolean containsValue(V value) {
        for (int i = 0; i < arrays.length; i++) {
            if (arrays[i] != null) {
                Item<K, V> a = arrays[i].first;
                while (a != null) {
                    if (a.getValue().equals(value)) {
                        return true;
                    }
                    a = a.next;
                }
            }

        }
        return false;
    }

    /**
     * Berilgan key va keyga tegishli bo'lgan value ni xotiradan  o'chirib tashlaydi.
     * O'chirilib yuborilayotgan value ni return qiling.
     */
    public V remove(K key) {
        int index=key.hashCode()%16;

            if (arrays[index] != null) {
                Item<K,V> a = arrays[index].first;
                int count=0;
                while (a != null) {

                    if (a.getKey().equals(key)) {
                        if (count==0) {
                            V value = (V) arrays[index].first.getValue();

                            arrays[index].first=arrays[index].first.next;
                            size--;
                            return value;
                        } else {
                            V s = (V) a.getValue();
                            if (a.next!=null){
                                a.next.previos=a.previos;
                                a.previos.next=a.next;
                                size--;
                                return s;
                            }else {
                                V x=a.value;
                                a.previos.next=null;
                                a=null;
                                size--;
                                return x;

                            }
                        }
                    }
                    a = a.next;
                    count++;
                }
            }


        return null;
    }

    /**
     * MyHashMap ni tozalab tashlaydi.
     * Umuman element qolmaydi.
     */
    public void clear() {
        for (Array<K,V> array : arrays) {
            if (array != null) {
                array.last = null;
                array.first = null;
                size = 0;
            }
        }
    }

    /**
     * Agar MyHashMap  bo'sh bo'lsa true bo'lmasa false return qiladi.
     */
    public Boolean isEmpty() {
        if (size != 0) {
            return false;
        }
        return true;
    }

    /**
     * MyHashMap dagi elementlar sonini return qiladi.
     */
    public int size() {
        return size;
    }

    /**
     * MyHashMap dagi barcha value larni ro'yxatini return qiladi.
     */
    public Collection<V> values() {
        List<V> list = new LinkedList<>();
        for (int i = 0; i < arrays.length; i++) {
            if (arrays[i] != null) {
                Item<K,V> a = arrays[i].first;

                while (a != null) {
                    list.add((V) a.getValue());
                    a = a.next;
                }
            }
        }
        return list;

    }

    /**
     * MyHashMap dagi barcha key larni  set ga joylab return qiladi.
     */
    public Set<K> keySet() {
        Set<K> set = new HashSet<>();
        for (int i = 0; i < arrays.length; i++) {
            if (arrays[i] != null) {
                Item<K,V> a = arrays[i].first;

                while (a != null) {
                    set.add((K) a.getKey());
                    a = a.next;
                }
            }
        }
        return set;
    }

    /**
     * toString metodi MyHashMap dagi elementlarni string ga ko'rinishi jo'natadi.
     * Yani: [{key1=value1},{key1=value1},.......]
     * Bunda key va value ni toString metodi chaqiriladi.
     */
    @Override
    public String toString() {
        String s = "";
        for (int i = 0; i < arrays.length; i++) {
            if (arrays[i] != null) {
                Item<K, V> a = arrays[i].first;

                while (a != null) {
                    s += a.toString() + ",";
                    a = a.next;
                }
            }
        }
        if (s.length() == 0) {
            return "[]";
        }

        s = s.substring(0, s.length() - 1);
        return "[" + s + "]";
    }

    /**
     * Berilgan myHashMap dagi barcha elementlarni qo'shadi.
     */
    public String putAll(MyHashMap<K,V> myHashMap) {
        for (K key : myHashMap.keySet()) {
            put((K) key, (V) myHashMap.get(key));
        }
        return toString();
    }

    @Override
    public Iterator<K> iterator() {
        return null;
    }

    private class Array<K,V> {
        public Item<K,V> first;
        public Item<K,V> last;

        public Array() {
        }
    }

    private class Item<K,V> {
        public Item<K,V> next;
        private int index;
        private Item<K,V> previos;
        private int hashCode;
        private K key;
        private V value;

        public Item(int index, int hashCode, K key, V value) {
            this.index = index;
            this.hashCode = hashCode;
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        @Override
        public String toString() {
            return key+"="+value;
        }

        public void setValue(V value) {
            this.value = value;
        }

        public V getValue() {
            return value;
        }
    }


}
