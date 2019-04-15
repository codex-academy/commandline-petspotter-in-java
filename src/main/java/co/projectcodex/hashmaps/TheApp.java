package co.projectcodex.hashmaps;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

enum Pet {
    cat,
    dog,
    ferret,
    parrot,
    rabbit,
    goldfish
}

interface PetSpotter {
    void spottedAtPet(Pet petSpotted);
    int totalPetsSpotted(Pet petType);
}

class JdbcPetSpotter implements PetSpotter {

    final String INSERT_PET_SQL = "insert into pet_spot_count (pet_type, pet_count) values(?, ?)";

    final String FIND_PET_TYPE_SQL = "select pet_count from pet_spot_count where pet_type = ?";

    final String UPDATE_PET_TYPE_SQL = "update pet_spot_count set pet_count = ? where pet_type = ?";

    Connection conn;
    PreparedStatement psCreateNewPetSpotted;
    PreparedStatement psFindPetCount;
    PreparedStatement psUpdatePetCount;

    public JdbcPetSpotter() {
        try {
            conn = DriverManager.
                    getConnection("jdbc:h2:./db/pet_spotter", "sa", "");
             psCreateNewPetSpotted = conn.prepareStatement(INSERT_PET_SQL);
             psFindPetCount = conn.prepareStatement(FIND_PET_TYPE_SQL);
             psUpdatePetCount = conn.prepareStatement(UPDATE_PET_TYPE_SQL);

        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    public void spottedAtPet(Pet petSpotted) {
        // insert / update the pet count for the given pet in the database...

        try {

            // find the counter for the current petType;

            psFindPetCount.setString(1, petSpotted.toString());
            ResultSet rsPetSpotted = psFindPetCount.executeQuery();

            if (!rsPetSpotted.next()) {
                // System.out.println("no count for " + petSpotted);
                // insert
                psCreateNewPetSpotted.setString(1, petSpotted.toString());
                psCreateNewPetSpotted.setInt(2, 1);
                System.out.println(psCreateNewPetSpotted.execute());

            } else {
                int petCount = rsPetSpotted.getInt("pet_count");
                psUpdatePetCount.setInt(1, ++petCount);
                psUpdatePetCount.setString(2, petSpotted.toString());
                psUpdatePetCount.execute();
            }

        } catch(SQLException ex) {
            ex.printStackTrace();
        }


        // insert
        // or
        // update
    }

    public int totalPetsSpotted(Pet petType) {
        // get the pet type count from the database - using a sql query...
        // select using count() where using petType

        try {
            psFindPetCount.setString(1, petType.toString());
            ResultSet rs = psFindPetCount.executeQuery();
            if (rs.next()) {
                return rs.getInt("pet_count");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        // if this pet wasn't greeted yet
        return 0;
    }
}

class PetSpotterUsingMap implements PetSpotter {

    Map<Pet, Integer> petsSpottedMap = new HashMap<Pet, Integer>();

    public void spottedAtPet(Pet petSpotted) {

        if (!petsSpottedMap.containsKey(petSpotted)) {
            petsSpottedMap.put(petSpotted, 0);
        }
        // increment the counter for this type of pet!!
        int petCounter = petsSpottedMap.get(petSpotted);
        petCounter++;
        petsSpottedMap.put(petSpotted, petCounter);
    }

    public int totalPetsSpotted() {
        // return petsSpottedMap.keySet().size();
        int totalPetsSpotted = 0;

        for (Integer petCount : petsSpottedMap.values()) {
            totalPetsSpotted += petCount;
        }

        return totalPetsSpotted;
    }

    public int totalPetsSpotted(Pet petType) {
        if (!petsSpottedMap.containsKey(petType)) {
            return 0;
        }
        return petsSpottedMap.get(petType);
    }

    public void clear() {
        petsSpottedMap.clear();
    }

    public void clear(Pet pet) {
        petsSpottedMap.put(pet, 0);
    }


}

public class TheApp {


    public static void main(String[] args) {


        PetSpotter petSpotter = new JdbcPetSpotter();

        // not changes below this line!!!

        Scanner scanner = new Scanner(System.in);

        while (true) {

            System.out.print("Which pet you spotted?");
            String commandString = scanner.nextLine();

            if ("exit".equals(commandString)) {
                break;
            }

            String[] params = commandString.trim().split(" ");

            if (params.length > 1) {
                String command = params[0].trim();
                String petTypeParam = params[1].toLowerCase();
                try {
                    Pet petType = Pet.valueOf(petTypeParam);
                    if ("spotted".equals(command)) {

                        System.out.println("We spotted a " + petType);
                        petSpotter.spottedAtPet(petType);

                    } else if ("info".equals(command)) {

                        Integer petCount = petSpotter.totalPetsSpotted(petType);
                        System.out.println(petCount + " " + petType + "(s) has been spotted.");

                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid pet : " + petTypeParam);
                }
            }


        }


//        petSpotter.spottedAtPet(Pet.cat);
//        petSpotter.spottedAtPet(Pet.dog);
//        petSpotter.spottedAtPet(Pet.dog);
//        petSpotter.spottedAtPet(Pet.ferret);
//        petSpotter.spottedAtPet(Pet.cat);
//        petSpotter.spottedAtPet(Pet.cat);
//        petSpotter.spottedAtPet(Pet.cat);
//        petSpotter.spottedAtPet(Pet.rabbit);
//        petSpotter.spottedAtPet(Pet.parrot);
//        petSpotter.spottedAtPet(Pet.cat);
//        petSpotter.spottedAtPet(Pet.goldfish);
//        petSpotter.spottedAtPet(Pet.ferret);


//        System.out.println("I have spotted so many pets: " + petSpotter.totalPetsSpotted());
//        System.out.println("I have spotted so many cats: " + petSpotter.totalPetsSpotted(Pet.cat));
//        System.out.println("I have spotted so many ferrets: " + petSpotter.totalPetsSpotted(Pet.ferret));
//        System.out.println("I have spotted so many goldfish: " + petSpotter.totalPetsSpotted(Pet.goldfish));


//        Map<String, Integer> userGreetCounter = new HashMap<String, Integer>();
//
//        userGreetCounter.put("Phumlani", 2);
//        System.out.println(userGreetCounter);
//
//        userGreetCounter.put("Phumlani", 1);
//        System.out.println(userGreetCounter);
//
//        userGreetCounter.put("Phumlani", 7);
//        System.out.println(userGreetCounter);
//
//        userGreetCounter.put("Peter", 7);
//
//        userGreetCounter.clear();
//        userGreetCounter = null;
//        System.out.println(userGreetCounter);



    }
}
