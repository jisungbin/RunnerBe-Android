# 1. tar 압축

```
tar cvf secrets.tar presentation/google-services.json data/src/main/kotlin/team/applemango/runnerbe/data/secret/ buildSrc/src/main/kotlin/BuildConstants.kt local.properties keystore
```

# 2. GPG 암호화

gpg -c secrets.tar