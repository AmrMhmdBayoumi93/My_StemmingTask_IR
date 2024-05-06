package org.example;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;

import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Version;

import java.io.IOException;
import java.io.StringReader;
import java.net.URISyntaxException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException {
        Analyzer analyzer =new EnglishAnalyzer(Version.LUCENE_42);

        Path dir = Paths.get(Main.class.getResource("/dataset").toURI());

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (Path file : stream) {
                try (TokenStream tokenStream = analyzer.tokenStream("content", new StringReader(new String(Files.readAllBytes(file))))) {
                    CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);
                    tokenStream.reset();
                    while (tokenStream.incrementToken()) {
                        String token = charTermAttribute.toString();
                        System.out.println("Token: " + token);
                    }
                    tokenStream.end();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}