package com.qmino.Restlet_security;

import java.util.HashMap;
import java.util.Map;

import org.restlet.security.LocalVerifier;

public class ApplicationVerifier extends LocalVerifier {

    private Map<String, char[]> localSecrets = new HashMap<String, char[]>();

    public ApplicationVerifier() {
        this.localSecrets.put("fozzy", "fozzy".toCharArray());
        this.localSecrets.put("rizzo", "rizzo".toCharArray());
    }

    @Override
    public char[] getLocalSecret(String key) {
        if (this.localSecrets.containsKey(key))
            return this.localSecrets.get(key);

        return null;
    }
}