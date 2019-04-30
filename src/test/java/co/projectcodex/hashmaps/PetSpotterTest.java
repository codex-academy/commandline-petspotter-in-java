package co.projectcodex.hashmaps;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class PetSpotterTest {

    @BeforeEach
    void cleanDatabase() throws Exception {
        Connection conn = DriverManager.
                getConnection("jdbc:h2:./db/pet_spotter", "sa", "");
        conn.createStatement().execute("delete from pet_spot_count where pet_type = 'goldfish'");
    }

    @Test
    void shouldBeAbleToSpotPet() {

        PetSpotter petSpotter = new JdbcPetSpotter();

        petSpotter.spottedAtPet(Pet.parrot);
        petSpotter.spottedAtPet(Pet.goldfish);

        assertEquals(1, petSpotter.totalPetsSpotted(Pet.goldfish));

        System.out.println(petSpotter.totalPetsSpotted(Pet.parrot));

    }



}
