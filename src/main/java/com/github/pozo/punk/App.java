package com.github.pozo.punk;

import org.apache.commons.cli.*;

import java.io.*;

/**
 * Created by pozo on 2016.07.16..
 */
public class App {
    public static final String APP_NAME = "punk";

    private static final CommandLineParser parser = new DefaultParser();
    private static final HelpFormatter formatter = new HelpFormatter();

    private static final String E = "e";
    private static final String D = "d";
    private static final String S = "s";
    private static final String I = "i";
    private static final String O = "o";

    public static void main(String[] args) throws IOException {
        Options options = new Options();

        OptionGroup optionGroup = new OptionGroup();
        optionGroup.addOption(new Option(E, "encode", false, "Encoding process."));
        optionGroup.addOption(new Option(D, "decode", false, "Decoding process."));
        options.addOptionGroup(optionGroup);

        options.addOption(S, "source-file", true, "The file location what you want to save into the target PNG.");
        options.addOption(I, "input-png", true, "The target PNG file location.");
        options.addOption(O, "output-png", true, "The output PNG file location.");

        CommandLine cmd = null;

        try {
            cmd = parser.parse(options, args);

            if (cmd.hasOption(E) || cmd.hasOption(D)) {
                Punk punk = new Punk();

                if(cmd.hasOption(E) && cmd.hasOption(I) && cmd.hasOption(S) && cmd.hasOption(O)) {
                    encode(cmd, punk);

                } else if(cmd.hasOption(D) && cmd.hasOption(I) && cmd.hasOption(O)) {
                    decode(cmd, punk);

                } else {
                    formatter.printHelp(APP_NAME, options );
                }

            } else {
                formatter.printHelp(APP_NAME, options );
            }
        } catch (ParseException e) {
            System.err.println(e.getMessage());
            System.out.println();
            System.out.println();
            formatter.printHelp(APP_NAME, options );
        }
    }

    private static void decode(CommandLine cmd, Punk punk) throws ParseException, IOException {
        String rawTargetFile = cmd.getOptionValue(I);
        String rawOutputPng = cmd.getOptionValue(O);

        final File targetFile = new File(rawTargetFile);
        final File outputFile = new File(rawOutputPng);

        if(!targetFile.isFile()) {
            throw new ParseException("'i, input-png' must be an existing file");
        }
        File absoluteFile = outputFile.getAbsoluteFile();
        if(!absoluteFile.getParentFile().isDirectory()) {
            throw new ParseException("'o, output-png' parent must be an existing directory");
        }

        FileInputStream fileInputStreamTarget = new FileInputStream(targetFile);
        FileOutputStream fileOutputStream = new FileOutputStream(outputFile);

        punk.decode(fileInputStreamTarget, fileOutputStream);
    }

    private static void encode(CommandLine cmd, Punk punk) throws ParseException, IOException {
        String rawTargetFile = cmd.getOptionValue(I);
        String rawOutputPng = cmd.getOptionValue(O);
        String rawPngFile = cmd.getOptionValue(S);

        final File targetFile = new File(rawTargetFile);
        final File outputFile = new File(rawOutputPng);
        final File pngFile = new File(rawPngFile);

        if(!pngFile.isFile()) {
            throw new ParseException("'s, source-file' must be an existing file");
        }
        File absoluteFile = outputFile.getAbsoluteFile();
        if(!absoluteFile.getParentFile().isDirectory()) {
            throw new ParseException("'o, output-png' parent must be an existing directory");
        }
        if(!targetFile.isFile()) {
            throw new ParseException("'i, input-png' must be an existing file");
        }

        FileInputStream fileInputStreamPng = new FileInputStream(pngFile);
        FileInputStream fileInputStreamTarget = new FileInputStream(targetFile);
        FileOutputStream fileOutputStream = new FileOutputStream(outputFile);

        punk.encode(fileInputStreamTarget,fileInputStreamPng, fileOutputStream);
    }
}
