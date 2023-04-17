import owner.Owner;
import owner.OwnerNotFoundException;
import owner.OwnerRepository;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        OwnerRepository ownerRepository = new OwnerRepository();
        try {
            System.out.println(ownerRepository.getAllOwners());
            System.out.println(ownerRepository.getOwnerByEmail("zino@sda.com"));
            System.out.println(ownerRepository.getOwnerByEmail("sabine@sda.com"));
            System.out.println(ownerRepository.getOwnerByEmail("doNotExist@gmail.com"));
        } catch (SQLException exception){
            exception.printStackTrace();
        } catch (OwnerNotFoundException e) {
            System.out.println(e.getMessage());
        }

        try {
            System.out.println("===Owners between 10 and 40 ====");
            ownerRepository.filterOwnerByAge(10, 40)
                    .forEach(owner -> System.out.println(owner.getOwnerName() + " - " + owner.getAge()));

            System.out.println("=== Create owner ====");
            String result = ownerRepository.createOwner(
                    new Owner(null, "Java Owner",34, "java@owner.com", null, null)
            );
            System.out.println(result);
            System.out.println(ownerRepository.createOwner(
                    new Owner(null, "Will Stay Owner",34, "willstay@owner.com", null, null)
            ));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            System.out.println("=== find owner by email and delete by id ====");
            Owner foundOwner = ownerRepository.getOwnerByEmail("java@owner.com");
            System.out.println(ownerRepository.deleteOwner(foundOwner.getId()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            System.out.println("=== update existing owner ====");
            Owner foundOwner = ownerRepository.getOwnerByEmail("zino@sda.com");
            foundOwner.setOwnerName("Zino Adidi");
            foundOwner.setAge(120);
            System.out.println(ownerRepository.updateOwner(foundOwner));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
