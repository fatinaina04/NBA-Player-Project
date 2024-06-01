
package nba2;

import java.util.*;

class Team {
    List<Player> players = new ArrayList<>();
    Stack<Player> injuryReserve = new Stack<>();
    
    double salaryCap = 20000.0;
    public void addPlayer(Player player) {
        if (players.size() >= 15) {
            System.out.println("Cannot add player. The team already has the maximum number of players (15).");
            return;
        }

        double currentSalary = players.stream().mapToDouble(p -> p.salary).sum();
        if (currentSalary + player.salary > salaryCap) {
            System.out.println("Cannot add player. The salary cap would be exceeded.");
            return;
        }

        if (player.pts > 20.0 && player.salary < 3000) {
            System.out.println("Cannot add player. Superstar player must have a minimum salary of $3000.");
            return;
        }

        if (player.pts <= 20.0 && player.salary < 1000) {
            System.out.println("Cannot add player. Non-superstar player must have a minimum salary of $1000.");
            return;
        }

        players.add(player);
        if (!validateRoster()) {
            players.remove(player);
            System.out.println("Cannot add player. Adding this player violates positional requirements.");
            return;
        }

        System.out.println("Player added successfully: " + player);
        printRoster();
    }

    public void removePlayer(String name) {
        Player playerToRemove = null;
        for (Player player : players) {
            if (player.name.equals(name)) {
                playerToRemove = player;
                break;
            }
        }

        if (playerToRemove == null) {
            System.out.println("Player not found: " + name);
            return;
        }

        players.remove(playerToRemove);
        if (!validateRoster()) {
            players.add(playerToRemove);
            System.out.println("Cannot remove player. Removing this player violates positional requirements.");
            return;
        }

        System.out.println("Player removed successfully: " + name);
        printRoster();
    }

    private boolean validateRoster() {
        if (players.size() < 10) {
            return false;
        }

        long guardCount = players.stream().filter(p -> p.position.equals("G")).count();
        long forwardCount = players.stream().filter(p -> p.position.equals("F")).count();
        long centerCount = players.stream().filter(p -> p.position.equals("C")).count();

        return guardCount >= 2 && forwardCount >= 2 && centerCount >= 2;
    }

    public void printRoster() {
        System.out.println("Team Roster:");
        for (Player player : players) {
            System.out.println(player);
        }
    }

    public void dynamicSearch(int height, int weight, String position, double minDefenseStat) {
        System.out.println("Searching for player with height: " + height + ", weight: " + weight + ", position: " + position + ", minimum defense stat: " + minDefenseStat);
        for (Player player : players) {
            if (player.height == height && player.weight == weight && player.position.equals(position) && player.blk + player.stl >= minDefenseStat) {
                System.out.println("Found matching player: " + player);
                return;
            }
        }
        System.out.println("No matching player found.");
    }
    public void addPlayerToInjuryReserve(String name) {
        Player playerToInjure = null;
        for (Player player : players) {
            if (player.name.equals(name)) {
                playerToInjure = player;
                break;
            }
        }

        if (playerToInjure == null) {
            System.out.println("Player not found: " + name);
            return;
        }

        players.remove(playerToInjure);
        injuryReserve.push(playerToInjure);

        System.out.println("Player added to injury reserve: " + name);
        printInjuryReserve();
    }

    public void removePlayerFromInjuryReserve() {
        if (injuryReserve.isEmpty()) {
            System.out.println("Injury reserve is empty.");
            return;
        }

        Player recoveredPlayer = injuryReserve.pop();
        players.add(recoveredPlayer);

        if (!validateRoster()) {
            players.remove(recoveredPlayer);
            injuryReserve.push(recoveredPlayer);
            System.out.println("Cannot move player from injury reserve. It would violate positional requirements.");
            return;
        }

        System.out.println("Player removed from injury reserve: " + recoveredPlayer.name);
        printRoster();
    }

    public void printInjuryReserve() {
        System.out.println("Injury Reserve:");
        for (Player player : injuryReserve) {
            System.out.println(player);
        }
    }
}

