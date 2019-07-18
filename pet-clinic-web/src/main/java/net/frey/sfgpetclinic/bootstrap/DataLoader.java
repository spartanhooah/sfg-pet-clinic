package net.frey.sfgpetclinic.bootstrap;

import net.frey.sfgpetclinic.service.OwnerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {
    private final OwnerService ownerService;

    public DataLoader() {
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
