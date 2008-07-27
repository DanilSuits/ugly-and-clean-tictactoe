package model.patterns;

import java.util.ArrayList;


public class IndexListGroup {
	private ArrayList<IndexList> group;

	public IndexListGroup() {
		group = new ArrayList<IndexList>();
	}

	public IndexList get(int i) {
		return group.get(i);
	}

	public void add(IndexList list) {
		group.add(list);
		
	}

	public int size() {
		return group.size();
	}

}
