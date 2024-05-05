package com.esprit.cloudcraft.implement;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class BadwordsService {

    private final List<String> badWords = Arrays.asList(
            "ass", "arse", "asshole", "bastard", "bitch", "bollocks", "bugger", "cock", "crap", "cunt",
            "damn", "dick", "douche", "fanny", "fuck", "motherfucker", "piss", "prick", "shit", "slut", "tits", "twat", "wanker", "whore",
            "putain", "merde", "foutre", "salaud", "connard", "bite", "chatte", "enculé", "enculée", "niaiseux", "criss", "ostie", "idiot", "moron",
            "jackass", "douchebag", "loser", "dumbass", "schmuck", "bellend", "git", "tosser", "muppet", "numbnuts", "knobhead", "cockwomble",
            "shithead", "arsewipe", "prat", "cockhead", "dipshit", "twatwaffle", "fudgepacker", "assclown", "fuckwit", "wankstain", "bullshit",
            "arsehole", "pissant", "dickhead", "buttface", "fuckface", "pussy", "cockroach", "motherlicker", "suckit", "cockholster", "dickbag",
            "ballsack", "twatburger", "cumstain", "sacristi", "estie", "osti", "saint-ciboire", "tabarnak", "viarge", "maudit", "zab", "zebi", "zabour", "zok"
    );

    public String sanitizeText(String text) {
        // Replace bad words with stars
        for (String word : badWords) {
            text = text.replaceAll("(?i)\\b" + word + "\\b", "*".repeat(word.length()));
        }
        return text;
    }
}
