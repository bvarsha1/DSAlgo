package PriorityQueue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;

public class PriorityQueues {

    PriorityQueue<Integer> pq;

    public PriorityQueues(boolean min) {
        if(min) {
            pq = new PriorityQueue<>();
        }
        else {
            pq = new PriorityQueue<Integer>( (a, b) -> (b - a) );
        }
    }

    public static class Car {
        int id;
        int x, y;
        double d;

        public Car(int id, int a, int b) {
            this.id = id;
            x = a;
            y = b;
            d = getDistance();
        }

        public double getDistance() {
            return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        }
    }

    public PriorityQueue<Car> findKNearestCabs(ArrayList<Car> cars, int k) {
        // build a max heap of K elements
        PriorityQueue<Car> carsQ = new PriorityQueue<Car>((c1, c2) -> (Double.compare(c2.d, c1.d)));
        int i = 0;
        for(; i < k; i++) {
            carsQ.offer(cars.get(i));
        }

        // check the top element, and try to replace it if its greater than incoming element
        for(; i < cars.size(); i++) {
            Car topCar = carsQ.peek();
            Car currCar = cars.get(i);
            if(topCar.d > currCar.d) {
                carsQ.poll();
                carsQ.add(currCar);
            }
        }

        return carsQ;
    }

    public int mergeRopes(int[] ropes) {
        PriorityQueue<Integer> pq = new PriorityQueue<Integer>((a, b) -> a - b);

        for(int r : ropes) {
            pq.add(r);
        }

        int totalCost = 0;
        while(pq.size() > 1) {
            int cost = pq.poll();
            cost += pq.poll();

            totalCost += cost;
            pq.offer(cost);
        }

        return totalCost;
    }

    public double[] runningMedian(int[] nums) {
        // define left max heap and right min heap
        PriorityQueue<Integer> left = new PriorityQueue<Integer>((a, b) -> (b - a));
        PriorityQueue<Integer> right = new PriorityQueue<>();

        double[] med = new double[nums.length];
        left.offer(nums[0]);
        med[0] = nums[0];
        
        for(int i = 1; i < nums.length; i++) {
            int d = nums[i];
            double m = med[i - 1];
            if(left.size() > right.size()) {
                if(d < m) {
                    right.offer(left.poll());
                    left.offer(d);
                } else {
                    right.offer(d);
                }
                med[i] = (double) (left.peek() + right.peek()) / 2;
            } else if(left.size() < right.size()) {
                if(d < m) {
                    left.offer(d);
                } else {
                    left.offer(right.poll());
                    right.offer(d);
                }
                med[i] = (double) (left.peek() + right.peek()) / 2;
            } else {
                if(d < m) {
                    left.offer(d);
                    med[i] = left.peek();
                } else {
                    right.offer(d);
                    med[i] = right.peek();
                }
            }
        }

        return med;
    }
    
    public class Element {
        int e;
        int eId;
        int arrId;

        public Element(int e, int eId, int arrId) {
            this.e = e;
            this.eId = eId;
            this.arrId = arrId;
        }
    }

    public ArrayList<Integer> mergeKArrays(int[][] m) {
        int k = m.length;
        PriorityQueue<Element> pq = new PriorityQueue<Element>((e1, e2) -> e1.e - e2.e);
        for(int i = 0; i < k; i++) {
            pq.offer(new Element(m[i][0], 0, i));
        }
        ArrayList<Integer> result = new ArrayList<>();
        while (!pq.isEmpty()) {
            Element top = pq.poll();
            result.add(top.e);
            if(top.eId + 1 < m[top.arrId].length) {
                pq.offer(new Element(m[top.arrId][top.eId + 1], top.eId + 1, top.arrId));
            }
        }

        return result;
    }

    public static void main(String[] args) {
        // Intro: Creating heaps in java (min/max) and using them
        int[] arr = {20, 10, 4, 3, 24, 30};
        PriorityQueues pqs = new PriorityQueues(false);
        for(int a : arr) {
            pqs.pq.add(a);
        }
        // printing
        while (!pqs.pq.isEmpty()) {
            System.out.print(pqs.pq.poll() + " ");
        }
        System.out.println();

        // Problem 1: K nearest cars
        ArrayList<Car> inputCars = new ArrayList<Car>();
        inputCars.add(new Car(1, 1, 1));
        inputCars.add(new Car(2, 2, 1));
        inputCars.add(new Car(3, 3, 2));
        inputCars.add(new Car(4, 0, 1));
        inputCars.add(new Car(5, 2, 3));
        PriorityQueue<Car> cars = pqs.findKNearestCabs(inputCars, 3);

        System.out.println("Nearest K cars are: ");
        while (!cars.isEmpty()) {
            Car c = cars.poll();
            System.out.println("Car" + c.id + " x: " + c.x + " y: " + c.y);
        }

        // Problem 2: Merging ropes
        int[] ropes = {4, 3, 2, 6};
        System.out.println("Cost of merging ropes: " + pqs.mergeRopes(ropes));

        // Problem 3: Running median
        int[] nums = {10, 5, 2, 3, 0, 12, 18, 20, 22};
        double[] result = pqs.runningMedian(nums);
        for(double m : result) {
            System.out.print(m + " ");
        }
        System.out.println();

        // Problem 4: Merge k sorted arrays
        int[][] matrix = {
            {10, 15, 20, 30},
            {2, 5, 8, 14, 24},
            {0, 11, 60, 90}
        };
        ArrayList<Integer> list = pqs.mergeKArrays(matrix);
        System.out.println("Final sorted array is: ");
        for(int a : list) {
            System.out.print(a + " ");
        }
        System.out.println();
    }
}
