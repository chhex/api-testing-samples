///usr/bin/env jbang "$0" "$@" ; exit $?
//REPOS local=file:///Users/chhex/maven/testrepo
//DEPS com.apgsga.testing.sample:api-testing-sample-client:0.3.3-SNAPSHOT
import com.apgsga.testing.sample.client.rest.RestClient;
import com.apgsga.testing.sample.api.Address;

import java.util.List;

class simpletestscenario {

    public static void main(String[] args) {
       RestClient client = new RestClient("http://localhost:8080");
       client.deleteAll();
       Address result = client.create(Address.builder()
               .streetName("Im Bungert")
               .streetNumber("10")
               .postalCode("8306")
               .build());
        System.out.println(result.toString());
        result = client.create(Address.builder()
                .streetName("Widacker")
                .streetNumber("1")
                .postalCode("9000")
                .build());
        System.out.println(result.toString());
        List<Address> addresses = client.listAddresses();
        for (Address address : addresses) {
            System.out.println(address.toString());
        }
    }
}