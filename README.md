# spring-vault

References: https://spring.io/blog/2016/06/24/managing-secrets-with-vault
```
touch vault.conf
vim vault.conf
vault server -config vault.conf
vault list secret/hello
vault list secret
export VAULT_ADDR=http://127.0.0.1:8200
vault init -key-shares=5 -key-threshold=2
vim vault-keys
vault unseal k0BLUdDFcBNC6JdWE+tdxrjgggKzhhFAGlCsRv9f/Dgc
vault unseal W/zI17NFv2vUeC+YY0GwGGbPAn54601FhJx2PB3Ct7SF
export VAULT_TOKEN=s.YupolRCUmVLd2wTlFR0UUfDq
vault write secret/hello password=H@rdT0Gu3ss
```
