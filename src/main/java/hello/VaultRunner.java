package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.vault.core.VaultSysOperations;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.core.VaultTransitOperations;
import org.springframework.vault.support.VaultMount;
import org.springframework.vault.support.VaultResponse;

@Component
public class VaultRunner implements CommandLineRunner{

	@Autowired
	private VaultTemplate vaultTemplate;
	
	@Override
	public void run(String... strings) throws Exception {

		// You usually would not print a secret to stdout
		VaultResponse response = vaultTemplate.read("secret/hello");
		System.out.println("Value of github.oauth2.key");
		System.out.println("-------------------------------");
		System.out.println(response.getData().get("password"));
		System.out.println("-------------------------------");
		System.out.println();

		// Let's encrypt some data using the Transit backend.
		VaultTransitOperations transitOperations = vaultTemplate.opsForTransit();

		// We need to setup transit first (assuming you didn't set up it yet).
		VaultSysOperations sysOperations = vaultTemplate.opsForSys();

		if (!sysOperations.getMounts().containsKey("transit/")) {

			sysOperations.mount("transit", VaultMount.create("transit"));

			transitOperations.createKey("foo-key");
		}

		// Encrypt a plain-text value
		String ciphertext = transitOperations.encrypt("foo-key", "Secure message");

		System.out.println("Encrypted value");
		System.out.println("-------------------------------");
		System.out.println(ciphertext);
		System.out.println("-------------------------------");
		System.out.println();

		// Decrypt

		String plaintext = transitOperations.decrypt("foo-key", ciphertext);

		System.out.println("Decrypted value");
		System.out.println("-------------------------------");
		System.out.println(plaintext);
		System.out.println("-------------------------------");
		System.out.println();
	}
	
}
