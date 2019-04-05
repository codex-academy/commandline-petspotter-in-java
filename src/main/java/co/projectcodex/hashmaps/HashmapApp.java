package co.projectcodex.hashmaps;

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

class PetSpotter {

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

public class HashmapApp {


    public static void main(String[] args) {


        PetSpotter petSpotter = new PetSpotter();
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
                String petTypeParam = params[1];
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
