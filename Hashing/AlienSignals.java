package Hashing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

// demonstrates O(log n) range query on a java TreeSet
public class AlienSignals {
	public List<Integer> validSignals(int[] a, int[] b, int lag) {
		int m = a.length, n = b.length;
		if(m == 0 || n == 0) return new ArrayList<>();
		HashMap<Integer, TreeSet<Integer>> aMap = new HashMap<>();

		for(int i = 0; i < m; i++) {
		if(a[i] != 0) {
			aMap.putIfAbsent(a[i], new TreeSet<>());
			aMap.get(a[i]).add(i);
		}
		}

		Set<Integer> ans = new HashSet<>();
		for(int i = 0; i < b.length; i++) {
			if(b[i] == 0) continue;
			Integer candidate = null;
			if(aMap.containsKey(b[i])) candidate = aMap.get(b[i]).ceiling(i - lag);
			if(candidate != null && candidate <= i + lag) ans.add(candidate);
		}

		return new ArrayList<>(ans);
	}

    public static void main(String[] args) {
        AlienSignals as = new AlienSignals();

        int[] a = {0, 0, 20, 30, 38, 2, 0, 0, 0, 12}, b = {0, 0, 30, 0, 0, 0, 0, 0, 0, 0, 0, 0, 12};
        int lag = 2;
        List<Integer> ans = as.validSignals(a, b, lag);
        for(int i : ans) System.out.println(i);
    }
}
