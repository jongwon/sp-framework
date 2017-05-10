package com.sp.web.menu;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

public class SortedMenuList<T extends OrderedMenu> extends LinkedList<T> {
	
    private static final long serialVersionUID = 1L;
    
    private Comparator<T> comparator = new Comparator<T>() {
		public int compare(T o1, T o2) {
			return o1.getOrder() - o2.getOrder();
		}
	};
    
    public SortedMenuList() {
    }
    
    public SortedMenuList(Comparator<T> comparator) {
        this.comparator = comparator;
    }
    
    @Override
    public boolean add(T paramT) {
        int insertionPoint = Collections.binarySearch(this, paramT, comparator);
        super.add((insertionPoint > -1) ? insertionPoint : (-insertionPoint) - 1, paramT);
        return true;
    }
    
    @Override
    public boolean addAll(Collection<? extends T> paramCollection) {
        boolean result = false;
        if (paramCollection.size() > 4) {
            result = super.addAll(paramCollection);
            Collections.sort(this, comparator);
        }
        else {
            for (T paramT:paramCollection) {
                result |= add(paramT);
            }
        }
        return result;
    }
    
    public boolean containsElement(T paramT) {
        return (Collections.binarySearch(this, paramT, comparator) > -1);
    }
    
}