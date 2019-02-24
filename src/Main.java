/*
    DSA Assignment 1, coding part,
    Author: Fedoseev Kirill, BS18-02,
    Codeforces submission: https://codeforces.com/group/lk8Ud0ZeBu/contest/238197/submission/49764362
 */

import java.util.Comparator;
import java.util.Scanner;
import java.util.function.IntSupplier;

//  BONUS QUESTION: We can easily modify selection sort, since at every iteration we select the i-th max element, we can get top K teams in O(k*n) = O(n), since k is a constants

public class Main {

    static class Team {
        String name;
        int points = 0, games = 0, wins = 0, ties = 0, losses = 0, goalsScored = 0, goalsAgainst = 0;

        Team(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return String.format("%s %dp, %dg (%d-%d-%d), %dgd (%d-%d)", name, points, games, wins, ties, losses, goalsScored - goalsAgainst, goalsScored, goalsAgainst);
        }
    }

    /**
     * @param function lambda to run
     * @return <code>True</code> if <code>function</code> raised an exception during execution
     */
    private static boolean throwsException(IntSupplier function) {
        try {
            function.getAsInt();
        } catch (Exception e) {
            return true;
        }
        return false;
    }


    /**
     * Checks given empty list for basic test cases via asserts, -ea flag during the compilation needed
     *
     * @param list Empty list
     */
    private static void checkList(List<Integer> list) {
        // []
        assert (list.size() == 0);
        assert list.isEmpty();
        list.add(0, 10);
        // [10]
        assert list.size() == 1;
        assert !list.isEmpty();
        assert list.get(0) == 10;
        assert throwsException(() -> list.get(1));
        assert throwsException(() -> list.get(2));
        assert throwsException(() -> list.get(-1));
        list.addLast(20);
        // [10, 20]
        assert list.size() == 2;
        assert list.get(0) == 10;
        assert list.get(1) == 20;
        list.addFirst(5);
        // [5, 10, 20]
        assert list.size() == 3;
        assert list.get(0) == 5;
        assert list.get(1) == 10;
        assert list.get(2) == 20;
        assert !list.delete(new Integer(11));
        assert list.delete(new Integer(10));
        // [5, 20]
        assert list.size() == 2;
        assert list.get(0) == 5;
        assert list.get(1) == 20;
        list.add(1, 15);
        // [5, 15, 20]
        assert list.size() == 3;
        assert list.get(0) == 5;
        assert list.get(1) == 15;
        assert list.get(2) == 20;
        assert list.set(1, 25) == 15;
        // [5, 25, 20]
        assert list.size() == 3;
        assert list.get(0) == 5;
        assert list.get(1) == 25;
        assert list.get(2) == 20;
        assert list.delete(1) == 25;
        // [5, 20]
        assert list.size() == 2;
        assert list.get(0) == 5;
        assert list.get(1) == 20;
        assert list.deleteLast() == 20;
        // [5]
        assert list.size() == 1;
        assert list.get(0) == 5;
        assert list.deleteFirst() == 5;
        // []
        assert list.size() == 0;
        assert list.isEmpty();
    }

    public static void main(String[] args) {
        List<Integer> list1 = new DynamicArrayList<>(1);
        List<Integer> list = new DoublyLinkedList<>();
        checkList(list1);
        checkList(list);

        list.addFirst(5);
        list.addFirst(6);
        list.addFirst(3);
        list.addFirst(2);
        list.addFirst(5);
        list.addFirst(7);
        list.addFirst(9);
        list.addFirst(1);
        list.addFirst(0);
        list.addFirst(8);
        list.sort(Comparator.comparingInt(a -> a));

        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        sc.nextLine();

        for (int i = 0; i < n; i++) {
            // Tournament name
            System.out.println(sc.nextLine());

            // Teams list
            List<Team> teams = new DynamicArrayList<>();
            int teamsNumber = sc.nextInt();
            sc.nextLine(); // skip '\n'

            for (int j = 0; j < teamsNumber; j++) {
                teams.addLast(new Team(sc.nextLine()));
            }

            int gamesNumber = sc.nextInt();
            sc.nextLine(); // skip '\n'

            for (int j = 0; j < gamesNumber; j++) {
                // Name1, Score1, Score2, Name2
                String[] data = sc.nextLine().split("[#:]");
                int score1 = Integer.parseInt(data[1]), score2 = Integer.parseInt(data[2]);

                // Get first team
                int index = 0;
                while (!teams.get(index).name.equals(data[0]))
                    index++;
                Team team1 = teams.get(index);

                // Get second team
                index = 0;
                while (!teams.get(index).name.equals(data[3]))
                    index++;
                Team team2 = teams.get(index);

                team1.games++;
                team2.games++;
                team1.goalsScored += score1;
                team1.goalsAgainst += score2;
                team2.goalsScored += score2;
                team2.goalsAgainst += score1;

                // Evaluate match results
                if (score1 > score2) {
                    team1.wins++;
                    team1.points += 3;
                    team2.losses++;
                } else if (score2 > score1) {
                    team2.wins++;
                    team2.points += 3;
                    team1.losses++;
                } else {
                    team1.ties++;
                    team2.ties++;
                    team1.points++;
                    team2.points++;
                }
            }

            teams.sort((a, b) -> {
                if (a.points != b.points)
                    return b.points - a.points;
                if (a.wins != b.wins)
                    return b.wins - a.wins;
                if (a.goalsScored - a.goalsAgainst != b.goalsScored - b.goalsAgainst)
                    return b.goalsScored - b.goalsAgainst - a.goalsScored + a.goalsAgainst;
                return b.name.toLowerCase().compareTo(a.name.toLowerCase());
            });

            for (int j = 0; j < teamsNumber; j++) {
                System.out.println(String.format("%d) ", j + 1) + teams.get(j).toString());
            }

            System.out.println();
        }
    }
}
